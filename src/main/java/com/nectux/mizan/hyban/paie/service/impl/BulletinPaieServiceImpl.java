package com.nectux.mizan.hyban.paie.service.impl;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.nectux.mizan.hyban.paie.dto.*;
import com.nectux.mizan.hyban.paie.entity.*;
import com.nectux.mizan.hyban.paie.repository.*;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.repository.PlanningCongeRepository;
import com.nectux.mizan.hyban.parametrages.service.RubriqueService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.repository.EmployeeRepository;
import com.nectux.mizan.hyban.personnel.specifque.repository.SpecialContractRepository;
import com.nectux.mizan.hyban.utils.DifferenceDate;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
//import com.nectux.mizan.hyban.utils.GenericSpecifications;
import com.nectux.mizan.hyban.utils.PrintLs;
import com.nectux.mizan.hyban.utils.ProvisionConge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.nectux.mizan.hyban.utils.CalculRICF.getRICF;
import static com.nectux.mizan.hyban.utils.ITSCalculator.calculerITS;

@Transactional
@Service("bulletinPaieService")
public class BulletinPaieServiceImpl implements BulletinPaieService {
	
	private static final Logger logger = LogManager.getLogger(BulletinPaieServiceImpl.class);
	
	@Autowired private EchelonnementRepository echelonnementRepository;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	@Autowired private BulletinSpecialeRepository bulletinSpecialeRepository;
	@Autowired private PrimePersonnelRepository primePersonnelRepository;
	@Autowired private RubriqueService rubriqueService;
	//@Autowired private LivreDePaieRepository livreDePaieRepository;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private SpecialContractRepository specialContractRepository;
	@Autowired private SocieteService societeService;
	@Autowired private TempEffectifRepository tempeffectifRepository;
	@Autowired private PlanningCongeRepository planningCongeRepository;
	
	List<LivreDePaie> livredepaieList=null;
    List<LivreDePaieSpeciale> livreDePaieSpeciales=null;
	List<LivreDePaieV2> livredepaieListV2=null;
	List<BulletinPaie> bulletinpaieList;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BulletinPaie save(BulletinPaie bulletinPaie) {
		// TODO Auto-generated method stub
		bulletinPaie = bulletinPaieRepository.save(bulletinPaie);
	
		return bulletinPaie;
	}


	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		BulletinPaie bulletinPaie = bulletinPaieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));

		bulletinPaieRepository.delete(bulletinPaie);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) bulletinPaieRepository.count();
	}

	@Override
	public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		List<BulletinPaie> firstlist=new ArrayList<BulletinPaie>();

		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieOrderByContratPersonnelPersonnelNomAsc(pageable,maperiode);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}
	public BulletinPaieDTO getCurrentYearBulletins(Long personnelId,String annee) {
		int currentYear = Year.now().getValue();
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		List<BulletinPaie> bulletins = bulletinPaieRepository
				.findByContratPersonnelPersonnelIdAndPeriodePaieAnneeAnnee(personnelId, annee);
		bulletinPaieDTO.setRows(bulletins);
		bulletinPaieDTO.setTotal(bulletins.size());
		return bulletinPaieDTO ;
	}


	public List<ImprimBulletinPaie> getlistImpData(BulletinPaie bulletin){
		List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		double taux = bulletin.getJourTravail();

		listImprimBulletinPaie.add(ajouterImprimBulletinPaie( "SALAIRE DE BASE CATEGORIEL", taux, bulletin.getSalairbase(), bulletin.getSalairbase(),0D,null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie( "SURSALAIRE", taux, bulletin.getSursalaire(), bulletin.getSursalaire(),0D,null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie("PRIME D'ANCIENNETE", 0, bulletin.getPrimeanciennete(), bulletin.getPrimeanciennete(),0D,null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie( "INDEMNITE DE RESIDENCE", taux, bulletin.getIndemnitelogement(), bulletin.getIndemnitelogement(),0D,null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie("IND. DE TRANSPORT IMP", taux, bulletin.getIndemniteTransportImp(), bulletin.getIndemniteTransportImp(),0D,null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie( "PRIMES IMPOSABLES", taux, bulletin.getAutreIndemImposable(), bulletin.getAutreIndemImposable(),0D,null,null));

		List<Rubrique> listPrimeImp=new ArrayList<Rubrique>();
		listPrimeImp=rubriqueService.getRubriquesActivesType(1);
		for(Rubrique rubrique : listPrimeImp){
			List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
			listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
			if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
				if(listprimepersonnel.get(0).getPrime().getTaux()!=null)
					listImprimBulletinPaie.add(ajouterImprimBulletinPaie(listprimepersonnel.get(0).getPrime().getLibelle(),listprimepersonnel.get(0).getValeur().doubleValue(),listprimepersonnel.get(0).getMontant(),listprimepersonnel.get(0).getMontant(),0D,null,null));
				else
					listImprimBulletinPaie.add(ajouterImprimBulletinPaie(listprimepersonnel.get(0).getPrime().getLibelle(),taux,listprimepersonnel.get(0).getMontant(),listprimepersonnel.get(0).getMontant(),0D,null,null));
			}
		}

		List<Rubrique> listPrimeImp1=new ArrayList<Rubrique>();
		listPrimeImp1=rubriqueService.getRubriquesActivesType(3);
		for(Rubrique rubrique : listPrimeImp1){
			List<PrimePersonnel> listprimepersonnel1=new ArrayList<PrimePersonnel>() ;
			listprimepersonnel1=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
			if(listprimepersonnel1.size()>0 &&listprimepersonnel1.get(0) !=null){
				listImprimBulletinPaie.add(ajouterImprimBulletinPaie(listprimepersonnel1.get(0).getPrime().getLibelle(),taux,listprimepersonnel1.get(0).getMontant() -listprimepersonnel1.get(0).getPrime().getMtExedent(),listprimepersonnel1.get(0).getMontant() -listprimepersonnel1.get(0).getPrime().getMtExedent(),0D,null,null));
			}
		}

		listImprimBulletinPaie.add(ajouterImprimBulletinPaie("***** Salaire Brut Imposable ******",0d,0d,bulletin.getBrutImposable(),0D,null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie("IND. DE TRANSPORT NON IMPOS*",0d,0d,bulletin.getIndemniteTransport(),0D,null,null));



		List<Rubrique> listPrimeImpnon=new ArrayList<Rubrique>();
		listPrimeImpnon=rubriqueService.getRubriquesActivesType(2);
		for(Rubrique rubrique : listPrimeImpnon){
			List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
			listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
			if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
				listImprimBulletinPaie.add(ajouterImprimBulletinPaie(listprimepersonnel.get(0).getPrime().getLibelle(),taux,listprimepersonnel.get(0).getMontant(),bulletin.getBrutImposable(),0D ,null,null));

			}
		}
		List<Rubrique> listPrimeImp12=new ArrayList<Rubrique>();
		listPrimeImp12=rubriqueService.getRubriquesActivesType(3);
		for(Rubrique rubrique : listPrimeImp12){
			List<PrimePersonnel> listprimepersonnel1=new ArrayList<PrimePersonnel>() ;
			listprimepersonnel1=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
			if(listprimepersonnel1.size()>0 &&listprimepersonnel1.get(0) !=null){
				listImprimBulletinPaie.add(ajouterImprimBulletinPaie(listprimepersonnel1.get(0).getPrime().getLibelle(),taux,listprimepersonnel1.get(0).getMontant()-listprimepersonnel1.get(0).getPrime().getMtExedent(),listprimepersonnel1.get(0).getMontant()-listprimepersonnel1.get(0).getPrime().getMtExedent(),0D,null,null));

			}
		}
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie("***** Salaire Brut Non Imposable ******",taux,bulletin.getBrutNonImposable(),bulletin.getBrutNonImposable(),0D,null,null));

		List<Rubrique> listPrimeG=new ArrayList<Rubrique>();
		listPrimeG=rubriqueService.getRubriquesActivesType(5);
		for(Rubrique rubrique : listPrimeG){
			List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
			listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
			if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
				listImprimBulletinPaie.add(ajouterImprimBulletinPaie(listprimepersonnel.get(0).getPrime().getLibelle(),0d,listprimepersonnel.get(0).getMontant(),listprimepersonnel.get(0).getMontant(),0D,null,null));
			}
		}
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie(" I.T.S ",1.2D,bulletin.getBrutImposable(),0D,bulletin.getIts(),null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie(" I.G.R ",0d,0d,0D,bulletin.getIgr(),null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie(" CONTRIBUTION NATIONALE ",0d,0d,0D,bulletin.getCn(),null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie(" RETRAITE CNPS  ",0d,bulletin.getBasecnps(),0D,bulletin.getCn(),null,null));
		listImprimBulletinPaie.add(ajouterImprimBulletinPaie(" IMPOTS/SALAIRE LOCAL ",0d,bulletin.getBasecnps(),0D,bulletin.getCn(),null,null));

		bulletin.setListImprimBulletinPaie(listImprimBulletinPaie);


		Date dateRetourDernierConge = null;
		if(bulletin.getContratPersonnel().getPersonnel().getDateRetourcge()==null)
		{logger.info("**********************************************g suiiiiiiiiiiiiii");
			dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateArrivee();}

		else
		{dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateRetourcge();}
		int tps= ProvisionConge.calculerTempsPresence(dateRetourDernierConge, bulletin.getPeriodePaie().getDatefin());
		bulletin.setNbcongedu(String.valueOf(bulletin.getTempsOfpresence()));
		bulletin.setTpsdepresence(String.valueOf(bulletin.getMoisOfpresence()));




		return listImprimBulletinPaie;
	}

	private ImprimBulletinPaie ajouterImprimBulletinPaie(String libelle,
														 double taux, double base, Double gain,Double retenue,Double tauxPatron,Double retenuePatron) {
		ImprimBulletinPaie imprimBulletinPaie = new ImprimBulletinPaie();
		imprimBulletinPaie.setLibelle(libelle);
		imprimBulletinPaie.setTaux(taux);
		imprimBulletinPaie.setBase(base);
		// Si gain ou retenue est null, mettre 0.0 comme valeur par défaut
		imprimBulletinPaie.setGain((gain != null) ? gain : 0.0);
		imprimBulletinPaie.setRetenue((retenue != null) ? retenue : 0.0);
		imprimBulletinPaie.setTauxPatron((tauxPatron != null) ? tauxPatron : 0.0);
		imprimBulletinPaie.setRetenuePatron((retenuePatron != null) ? retenuePatron : 0.0);
		return imprimBulletinPaie;
	}


	@Override

	public BulletinPaieDTO findbulletin(Long payrollId) {
		BulletinPaieDTO response = new BulletinPaieDTO();

		Optional<BulletinPaie> opt = bulletinPaieRepository.findById(payrollId);

		if (!opt.isPresent()) {
			response.setRow(null);
			response.setTotal(0L);
			response.setResult(false);
			response.setStatus(false);
			response.setMessage("Aucun bulletin trouvé pour l'id " + payrollId);
			return response;
		}

		BulletinPaie bulletinPaie = opt.get();
		response.setRow(bulletinPaie);
		response.setTotal(1L);
		response.setResult(true);
		response.setStatus(true);
		return response;
	}

	public BulletinPaieDTO loadBulletinPaiechearch(Pageable pageable, String valeurarechercher) {
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		List<BulletinPaie> firstlist=new ArrayList<BulletinPaie>();

		Page<BulletinPaie> page = bulletinPaieRepository.findByContratPersonnelPersonnelNomContainingIgnoreCase(pageable,valeurarechercher);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}


//	@Override
//	public BulletinPaieDTO loadBulletinPaie(Pageable pageable, PeriodePaie maperiode, String search) {
//		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
//		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContainingOrderByContratPersonnelPersonnelNomAsc(pageable, maperiode.getId(), search);
//		bulletinPaieDTO.setRows(page.getContent());
//		bulletinPaieDTO.setTotal(page.getTotalElements());
//		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
//		return bulletinPaieDTO;
//	}

	@Override
	public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode, String search) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContaining(pageable, maperiode.getId(), search);
		//Page<BulletinPaie> page = bulletinPaieRepository.chercherParNom(maperiode.getId(), "",pageable);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}

    @Override
    public BulletinSpecialeDTO loadBulletinSpeciale(Pageable pageable, PeriodePaie maperiode, String search) {
        BulletinSpecialeDTO bulletinPaieDTO = new BulletinSpecialeDTO();
        Page<BulletinSpeciale> page = bulletinSpecialeRepository.findByPeriodePaieIdAndSpecialContractEmployeeNomCompletIgnoreCaseContaining(pageable, maperiode.getId(), search);
        //Page<BulletinPaie> page = bulletinPaieRepository.chercherParNom(maperiode.getId(), "",pageable);
        bulletinPaieDTO.setRows(page.getContent());
        bulletinPaieDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
        return bulletinPaieDTO;
    }

    @Override
    public BulletinSpecialeDTO loadBulletinSpeciale(Pageable pageable, PeriodePaie maperiode) {
        BulletinSpecialeDTO bulletinPaieDTO = new BulletinSpecialeDTO();
        Page<BulletinSpeciale> page = bulletinSpecialeRepository.findByPeriodePaieId(pageable, maperiode.getId());
        //Page<BulletinPaie> page = bulletinPaieRepository.chercherParNom(maperiode.getId(), "",pageable);
        bulletinPaieDTO.setRows(page.getContent());
        bulletinPaieDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
        return bulletinPaieDTO;
    }

//	@Override
//	public BulletinPaieDTO findAllfilter(Map<String, String> filters, Pageable pageable) {
//		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
//		Specification<BulletinPaie> specification = GenericSpecifications.fromMap(filters);
//		Page<BulletinPaie> page = bulletinPaieRepository.findAll(specification, pageable);
//	//	Page<BulletinPaie> page = bulletinPaieRepository.chercherParNom(maperiode.getId(), "",pageable);
//		bulletinPaieDTO.setRows(page.getContent());
//		bulletinPaieDTO.setTotal(page.getTotalElements());
//		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
//		return bulletinPaieDTO;
//		//return null;
//	}

	/*@Override
	public List<BulletinPaie> findAllBulletinByPeriodePaieForLivrePaieforBanque(Long idPeriodePaie, boolean idBanque) {
		// TODO Auto-generated method stub
		List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
		try{
			listBulletin = bulletinPaieRepository.findByPeriodePaieIdAndCalculerTrue(idPeriodePaie,idBanque,sortByPersonnelAsc());
			logger.info(listBulletin.size() + " Bulletin(s) pa periode actif(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			System.out.println("----------------------liste des bulletins" +ex.getMessage());
		   
		}
		return listBulletin;
	}*/
	

	private Sort sortByPersonnelAsc() {
		return Sort.by(Sort.Direction.ASC, "personnel.id");
	}
	@Override
	public BulletinPaie findBulletinByPeriodePaieAndPersonnel(PeriodePaie periodePaie, Personnel personnel) {
		// TODO Auto-generated method stub
		BulletinPaie bulletin = new BulletinPaie();
		try{
			bulletin =bulletinPaieRepository.findByBulletinAndPersonnel(personnel.getId(), periodePaie.getId());
			logger.info(bulletin.getId() + " Bulletin(s) pa periode actif(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			System.out.println("----------------------liste des bulletins" +ex.getMessage());
		    
			logger.error("une erreur a ete detectee lors de la recherche des bulletins par periode");
		}
		return bulletin;
	}

	//@SuppressWarnings({ "unused", "null" })
	@Override
	public LivreDePaieDTO  genererMois1(Pageable pageable,Long idPeriode) {
		logger.error(new StringBuilder().append(">>>>>  PREPARATION DE CALCUL BULLETIN [PERIODE : ").append(idPeriode).toString());
		// TODO Auto-generated method stub
		
		  FileOutputStream fileOut=null;
		  StringBuilder sb = new StringBuilder();
		  BulletinPaieDTO bulletinPaiedto = new BulletinPaieDTO();
		  LivreDePaieDTO livreDEPaieList = new LivreDePaieDTO();
		  livredepaieList = new ArrayList<LivreDePaie>();
		  bulletinpaieList = new ArrayList<BulletinPaie>();
		  PeriodePaie periodePaieActif = new PeriodePaie(); PeriodePaie periodePaiefind= new PeriodePaie();
		  Personnel personnel = new Personnel();
		  List<Personnel> personnelList = new ArrayList<Personnel>();
		  List<Personnel> personnelListTrt = new ArrayList<Personnel>();
		  List<ContratPersonnel> ctratpersonnelListTrt = new ArrayList<ContratPersonnel>();
		  ContratPersonnel ctratpersonnel = new ContratPersonnel();
		  ContratPersonnel ctratpersonnelFind = new ContratPersonnel();
		  periodePaieActif=periodePaieRepository.findById(idPeriode).orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + idPeriode));

		    personnelList = personnelRepository.findByStatutAndRetraitEffectOrderByNomAsc(true,false);
		    for(int i = 0; i < personnelList.size(); i++){

			  ctratpersonnelFind=contratPersonnelRepository.findByPersonnelIdAndStatut(personnelList.get(i).getId(),true);
			   if(ctratpersonnelFind!=null)
			    personnelListTrt.add(ctratpersonnelFind.getPersonnel());
			  logger.info("*****************************************personnel list******"+ctratpersonnelListTrt.toString());
		 
		   }
		  
	        for(Personnel person : personnelListTrt){
	        	        	
	        	BulletinPaie detailsbull = new BulletinPaie();
	             float nbrepart =calculNbrepart(person.getNombrEnfant(),person);		    	
		    	
		    	 ContratPersonnel ctratpersonnellz = new ContratPersonnel();	   
		    	ctratpersonnellz=contratPersonnelRepository.findByPersonnelIdAndStatut(person.getId(),true);
		    	if(ctratpersonnellz.getStatut()==false)ctratpersonnellz=null;
				long anneesAnciennete = ChronoUnit.YEARS.between(ctratpersonnellz.getPersonnel().getDateArrivee().toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate(), LocalDate.now() );
		    		
		    	//Double[]  ancienete=calculAnciennete(ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getPersonnel().getDateArrivee());
		    	double newancienete;
		    	if(ctratpersonnellz.getAncienneteInitial()!=0) {
		    		 newancienete=anneesAnciennete +ctratpersonnellz.getAncienneteInitial();
		    	}else{
		    		newancienete=anneesAnciennete;
		    	}
		    	int anc=(int)newancienete;
		 
		    	if(ctratpersonnellz.getCategorie().getSalaireDeBase()!=null){
		          ctratpersonnellz.getCategorie().getSalaireDeBase();
		    	}
		    	
		 
		    	Double indemLogt=0d;
		    	if(ctratpersonnellz.getCategorie().getIndemniteLogement()!=null){
		    		indemLogt=ctratpersonnellz.getCategorie().getIndemniteLogement();
		    	}else{
		    		indemLogt=0d;
		    	}
		    			    	
		  
		    
		    	List<Echelonnement> monEchelonnment= echelonnementRepository.findByPretPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId());
		    	Double somavsAcpte=0D;Double somalios=0D;
		    	if(monEchelonnment.size()>0){
		    		for(int k = 0; k < monEchelonnment.size(); k++){
		    			Echelonnement monechel= new Echelonnement();
		    			if(monEchelonnment.get(k).getPretPersonnel().getPret().getId()==2L){
							int finalK1 = k;
							monechel=echelonnementRepository.findById(monEchelonnment.get(k).getId()).orElseThrow(() -> new EntityNotFoundException("monEchelonnment not found for id " + monEchelonnment.get(finalK1).getId()));
		    			    monechel.setPaye(true);
		    			    monechel=echelonnementRepository.save(monechel);
		    				somavsAcpte=monEchelonnment.get(k).getMontant()+somavsAcpte;
		    			}
		    			// PRET ALOES
		    			if(monEchelonnment.get(k).getPretPersonnel().getPret().getId()==1L ||monEchelonnment.get(k).getPretPersonnel().getPret().getId()==3L ){
							int finalK = k;
							monechel=echelonnementRepository.findById(monEchelonnment.get(k).getId()).orElseThrow(() -> new EntityNotFoundException("monEchelonnment not found for id " + monEchelonnment.get(finalK).getId()));
	    			       monechel.setPaye(true);
	    			       monechel=echelonnementRepository.save(monechel);
		    				somalios=monEchelonnment.get(k).getMontant()+somalios;
		    			}
		    		}
		    	}
		    	
		    	   BulletinPaie	 bulletinpaiePrec=new BulletinPaie();			     
			     bulletinpaiePrec=bulletinPaieRepository.findByBulletinAndPersonnelCloture(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId()-1L);
			     int countbull=0;
			     countbull=bulletinPaieRepository.findNbrebulletinTrueCount(ctratpersonnellz.getId());
			     
			      PlanningConge     planningConge = planningCongeRepository.findByContratPersonnelAndStatut(ctratpersonnellz,true);
			     TempEffectif tpeff;
				 tpeff=tempeffectifRepository.findByPersonnelAndPeriodePaie(ctratpersonnellz.getPersonnel(), periodePaieActif);
				
				/* LivreDePaie livrePaiecalY = new LivreDePaie();
				 livrePaiecalY =calculbullFirst(ctratpersonnellz, periodePaieActif,op);*/
		    	  LivreDePaie livrePaiecalR = new LivreDePaie();
		    	  LivreDePaie livrePaiecal = null;  LivreDePaie livrePaiecalNow = null;
		    	
		    //		 livrePaiecal = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),5000d, ctratpersonnellz.getIndemniteLogement(),0d, 0d,ctratpersonnellz,null,periodePaieActif);	 
				     List<PrimePersonnel> listIndemniteBrut=new ArrayList<PrimePersonnel>();
	    			 List<PrimePersonnel> listIndemniteNonBrut=new ArrayList<PrimePersonnel>();
				     List<PrimePersonnel> listRetenueMutuelle=new ArrayList<PrimePersonnel>();
				     List<PrimePersonnel> listRetenueSociale=new ArrayList<PrimePersonnel>();
				     List<PrimePersonnel> listGainsNet=new ArrayList<PrimePersonnel>();
	    			 List<PrimePersonnel> listIndemnite  =new ArrayList<PrimePersonnel>();
	    			 listIndemnite =  primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId());
	    				if(listIndemnite.size()>0){
	    					for(PrimePersonnel kprme:listIndemnite){
	    						 if(kprme.getPrime().getEtatImposition()==1)
	    						 {
	    							 listIndemniteBrut.add(kprme);
	    						 }
	    						 if(kprme.getPrime().getEtatImposition()==2)
	    						 {
	    							 listIndemniteNonBrut.add(kprme);
	    						 }
	    						 if(kprme.getPrime().getEtatImposition()==3)
	    						 {
	    							 if(kprme.getPrime().getMtExedent()!=null)	    							 
	    							     listIndemniteNonBrut.add(kprme);
	    								 listIndemniteBrut.add(kprme);
	    						 }
								if(kprme.getPrime().getEtatImposition()==4)
								{
									listRetenueMutuelle.add(kprme);
								}
								if(kprme.getPrime().getEtatImposition()==5)
								{
									listGainsNet.add(kprme);
								}
								if(kprme.getPrime().getEtatImposition()==6)
								{
									listRetenueSociale.add(kprme);
								}
	    					}
	    					
	    				}
				int op=0;
				if(anc < 2) op=0;
				if(anc>=2 && anc<=25) op=anc;
				if(anc>25) op=25;
		    	  if(countbull==0){
		    			 

		    			 Float nbpart=calculNbrepart(person.getNombrEnfant(),person);
		    			
		    		
		                  livrePaiecalR = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(),nbpart , op, ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getSursalaire(), ctratpersonnellz.getIndemniteLogement(),0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		    
		    		 	if ( tpeff==null){
		    		 		livrePaiecalR = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), nbpart ,op, ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getSursalaire(), ctratpersonnellz.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		    		 	}else{
		    		 		livrePaiecalR = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(),nbpart, op, ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getSursalaire(), ctratpersonnellz.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,tpeff,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		    		 	}
				
		    	}else{
		    			
		    			 System.out.println("########################################### DATE D'ARRIVEE ###########################"+ctratpersonnellz.getPersonnel().getDateArrivee());
		    			 int op1= Anciennet(ctratpersonnellz.getPersonnel().getDateArrivee()) +ctratpersonnellz.getAncienneteInitial();
		    			 System.out.println("########################################### Anciennete ###########################"+op1);
		    			Float nbpart1=calculNbrepart(person.getNombrEnfant(),person);
		    	 		List<BulletinPaie> monbull=bulletinPaieRepository.findTop1ByContratPersonnelOrderByIdDesc(ctratpersonnellz);
		    	 		if(monbull.size()==0){} else{		    		
		    	 			if ( tpeff==null){		    		 
		    	 				livrePaiecalR = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), nbpart1, op, ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getSursalaire(), ctratpersonnellz.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
							}else{
						
								livrePaiecalR = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(),  nbpart1, op, ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getSursalaire(), ctratpersonnellz.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,tpeff,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
					
		    	 	}
		    	 
		           } 
		        }
				livrePaiecalR.setPeriodePaie(periodePaieActif);
				livrePaiecalR.setCMUPatronal(livrePaiecalR.getCMUPatronal());
				livrePaiecalR.setCMUSalarial(livrePaiecalR.getCMUPatronal());
				livrePaiecalR.setContratPersonnel(ctratpersonnellz);
				livrePaiecalR.setJourTravail(livrePaiecalR.getJourTravail());
				livrePaiecalR.setTemptravail(livrePaiecalR.getTemptravail());
				detailsbull.setNombrePart(livrePaiecalR.getNombrePart());
			   	logger.info("########################################### BULLETIN EXISTANT NBRE PART ###########################"+detailsbull.getNombrePart());
			     detailsbull.setAnciennete(livrePaiecalR.getAnciennete());
			     detailsbull.setSalairbase(livrePaiecalR.getSalaireBase());
			     detailsbull.setSursalaire(livrePaiecalR.getSursalaire());
			     detailsbull.setPrimeanciennete(livrePaiecalR.getPrimeAnciennete());
			     detailsbull.setIndemnitelogement(livrePaiecalR.getIndemniteLogement());			     
			     detailsbull.setBrutImposable(livrePaiecalR.getBrutImposable());
			     detailsbull.setBasecnps(livrePaiecalR.getBasecnps());
			     detailsbull.setIts(livrePaiecalR.getIts());
			     detailsbull.setCn(livrePaiecalR.getCn()); 
			     detailsbull.setIgr(livrePaiecalR.getIgr());						     
			     detailsbull.setTotalretenuefiscal(livrePaiecalR.getTotalRetenueFiscale());
			     detailsbull.setCnps(livrePaiecalR.getCnps());
			    detailsbull.setAutreIndemImposable(livrePaiecalR.getAutreIndemImposable());
			     detailsbull.setAutreImposable(livrePaiecalR.getAutreImposable());
			     detailsbull.setCongeAc(false);
			     
			     if(bulletinpaiePrec==null){
			    	 
			     detailsbull.setTempsOfpresence(livrePaiecalR.getTempspresence());
			     detailsbull.setMoisOfpresence(livrePaiecalR.getMoisdepresence());
			     BulletinPaie	 bulletinpaiePrecLev2=new BulletinPaie();
			    bulletinpaiePrecLev2=bulletinPaieRepository.findByBulletinAndPersonnelCloture(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId()-2L);
			    if(bulletinpaiePrecLev2==null){

				}
			    else{
			    		 if(bulletinpaiePrecLev2.getCongeAc()==true){
				    		 	detailsbull.setTempsOfpresence(0);
				    		 	detailsbull.setMoisOfpresence(0);
				    	 }
			    		 
			    	 }
			    	 
			    
			     }else{
			    	 
			    	 if(bulletinpaiePrec.getCongeAc()==true){
			    		 	detailsbull.setTempsOfpresence(0);
			    		 	detailsbull.setMoisOfpresence(0);
			    	 }else{
			    		 	detailsbull.setTempsOfpresence(livrePaiecalR.getTempspresence());
			    		 	detailsbull.setMoisOfpresence((livrePaiecalR.getMoisdepresence()));
				     }
			     }
				Double cumulIgr = 0D;
				Double cumulIts = 0D;
				Double cumulCn = 0D;
				Double cumulCnps = 0D;
				Double cumulRetenuNet = 0D;
				Double salaireNet = 0D;

// récupération des bulletins de la période en cours et antérieurs
				List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeIdAndClotureTrueAndContratPersonnelPersonnelId(periodePaieActif.getAnnee().getId(),ctratpersonnellz.getPersonnel().getId());

				for (BulletinPaie bulletinPaie : bulletinPaieList) {
					cumulIgr += (bulletinPaie.getIgr() != null ? bulletinPaie.getIgr() : 0D);
					cumulIts += (bulletinPaie.getIts() != null ? bulletinPaie.getIts() : 0D);
					cumulCn += (bulletinPaie.getCn() != null ? bulletinPaie.getCn() : 0D);
					cumulCnps += (bulletinPaie.getCnps() != null ? bulletinPaie.getCnps() : 0D);
					salaireNet += (bulletinPaie.getNetapayer() != null ? bulletinPaie.getNetapayer() : 0D);
				}

				// Ajout du mois courant
				cumulIgr += (livrePaiecalR.getIgr() != null ? livrePaiecalR.getIgr() : 0D);
				cumulIts += (livrePaiecalR.getIts() != null ? livrePaiecalR.getIts() : 0D);
				cumulCn += (livrePaiecalR.getCn() != null ? livrePaiecalR.getCn() : 0D);
				cumulCnps += (livrePaiecalR.getCnps() != null ? livrePaiecalR.getCnps() : 0D);
				salaireNet += (livrePaiecalR.getNetPayer() != null ? livrePaiecalR.getNetPayer() : 0D);
				// Calcul de la retenue nette
				cumulRetenuNet = safeSum(cumulIgr, cumulIts, cumulCn, cumulCnps);

// Mise à jour des champs du bulletin
				detailsbull.setCumulIgr(cumulIgr);
				detailsbull.setCumulIts(cumulIts);
				detailsbull.setCumulCn(cumulCn);
				detailsbull.setCumulCnpsSal(cumulCnps);
				detailsbull.setCumulSalaireNet(salaireNet);
				detailsbull.setCumulRetenueNet(cumulRetenuNet);
				 detailsbull.setAvanceetacompte(livrePaiecalR.getAvceAcpte());
			     detailsbull.setPretaloes(livrePaiecalR.getPretAlios());
			     detailsbull.setCMUPatronal(livrePaiecalR.getCMUPatronal());
			     detailsbull.setCMUSalarial(livrePaiecalR.getCMUSalarial());
			     detailsbull.setCarec(livrePaiecalR.getCarec());
			     detailsbull.setTotalretenue(livrePaiecalR.getTotalRetenue());
			     detailsbull.setIndemniteRepresentation(livrePaiecalR.getIndemniteRepresentation());
			     detailsbull.setIndemniteTransport(livrePaiecalR.getIndemniteTransport()); 
			     detailsbull.setAutreNonImposable(livrePaiecalR.getAutreNonImposable());
			     detailsbull.setBrutNonImposable(livrePaiecalR.getBrutNonImposable()); 
			     detailsbull.setIndemniteRespons(livrePaiecalR.getIndemniteResponsabilte());
			     detailsbull.setIndemniteTransportImp(livrePaiecalR.getIndemniteTransportImp());
				 detailsbull.setRegularisation(livrePaiecalR.getRegularisation());
				 detailsbull.setAutrePrelevement(livrePaiecalR.getAutrePrelevment());
			     detailsbull.setNetapayer(livrePaiecalR.getNetPayer());
			     detailsbull.setTotalbrut(livrePaiecalR.getTotalBrut());
			     detailsbull.setImpotSalaire(livrePaiecalR.getIs());
			     detailsbull.setTa(livrePaiecalR.getTa()); 
			     detailsbull.setJourTravail(livrePaiecalR.getJourTravail());
			     detailsbull.setTemptravail(livrePaiecalR.getTemptravail());
			     detailsbull.setFpc(livrePaiecalR.getFpc()); 
			     detailsbull.setFpcregul(livrePaiecalR.getFpcregul());
			     detailsbull.setPrestationFamiliale(livrePaiecalR.getPrestationFamiliale());
			     detailsbull.setAccidentTravail(livrePaiecalR.getAccidentTravail());
			     detailsbull.setRetraite(livrePaiecalR.getRetraite());
			     detailsbull.setTotalpatronal(livrePaiecalR.getTotalPatronal());
			     detailsbull.setTotalmassesalarial(livrePaiecalR.getTotalMasseSalariale());
			     detailsbull.setRetenueSociiale(livrePaiecalR.getRetenueSociiale());
			     detailsbull.setCalculer(true);
			     detailsbull.setCloture(false);
				 detailsbull.setIsPatronal(livrePaiecalR.getIs());
			     detailsbull.setPeriodePaie(livrePaiecalR.getPeriodePaie());
			     detailsbull.setContratPersonnel(livrePaiecalR.getContratPersonnel());
			     detailsbull.setJourTravail(livrePaiecalR.getJourTravail());
			    // detailsbull.set(livrePaiecalD.getJourTravail());
			     livrePaiecalR.setBullpaie(detailsbull);
				
			    // bulletinpaie= bulletinPaieRepository.save(bulletinpaie);
			    
			    // livrePaiecal.setDetailsbull(bulletinpaie);
				//LivreDePaie livrePaie = new LivreDePaie(person.getMatricule(),person.getNom(), nbrepart, anc, ctratpersonnellz.getCategorie().getSalaireDeBase(),20000d, indemLogt, somavsAcpte, somalios);	
		    	 logger.info("*****************************************BULLETIN BULLETIN BULLETIN******************"+livrePaiecalR.toString()); 
		    	 System.out.println("*****************************************BULLETIN BULLETIN BULLETIN******************"+livrePaiecalR.toString());
		    	 livredepaieList.add(livrePaiecalR); 
		    	// bulletinpaieList.add(bulletinpaie);
	        }
	    	
	       
	        logger.info("*********************LISTE DES  BULLETIN********************############## LISTE DES  BULLETIN #############-----------"+livredepaieList.toString());	
		/*} catch (Exception e) {
			// TODO: handle exception
			 livreDEPaieList.setResult(e.getMessage());
			 livreDEPaieList.setRows(livredepaieList);
		        livreDEPaieList.setResult("error");
		        livreDEPaieList.setTotal(livredepaieList.size());
		}*/
		int start = (int) pageable.getOffset();Page<LivreDePaie> pageImpianto=null;
		int end = (start + (int) pageable.getPageSize()) > livredepaieList.size() ? livredepaieList.size() : (start + pageable.getPageSize());
		pageImpianto=new PageImpl<LivreDePaie>(livredepaieList.subList(start, end), pageable,livredepaieList.size());
		//pageImpianto = new PageImpl<ContratPersonnel>(myList2);

		livreDEPaieList.setRows(pageImpianto.getContent());
		livreDEPaieList.setTotal(pageImpianto.getTotalElements());
		    	
		    		// livreDEPaieList.setRows(livredepaieList);
	       // livreDEPaieList.setResult("success");
	       // livreDEPaieList.setTotal(livredepaieList.size());
	        
        System.out.println("FINISH"+ bulletinPaiedto.getRows());
		return livreDEPaieList;
	
	}

	private double safeSum(Double... values) {
		return Arrays.stream(values)
				.filter(Objects::nonNull)
				.mapToDouble(Double::doubleValue)
				.sum();
	}




	@Transactional
	public LivreDePaieDTO genererOptimiseMois1(Pageable pageable, Long idPeriode) {
		logger.info(">>>>> DÉBUT DE GÉNÉRATION DU BULLETIN [PÉRIODE : {}]", idPeriode);
		long startTime = System.currentTimeMillis();

		LivreDePaieDTO livreDEPaieList = new LivreDePaieDTO();
	   livredepaieList = new ArrayList<LivreDePaie>();

		// 1️⃣ Précharger la période
		PeriodePaie periodePaieActif = periodePaieRepository.findById(idPeriode)
				.orElseThrow(() -> new EntityNotFoundException("PériodePaie not found: " + idPeriode));

		// 2️⃣ Précharger tout le personnel actif
		List<Personnel> personnelList = personnelRepository.findByStatutAndRetraitEffectOrderByNomAsc(true, false);

		// 3️⃣ Précharger tous les contrats actifs
		List<ContratPersonnel> contrats = contratPersonnelRepository.findByStatutTrue();
		Map<Long, ContratPersonnel> contratParPersonnel = contrats.stream()
				.collect(Collectors.toMap(c -> c.getPersonnel().getId(), c -> c));

		// 4️⃣ Précharger les primes
		List<PrimePersonnel> primes = primePersonnelRepository.findByPeriodePaieId(idPeriode);
		Map<Long, List<PrimePersonnel>> primesParPersonnel = primes.stream()
				.collect(Collectors.groupingBy(p -> p.getContratPersonnel().getPersonnel().getId()));

		// 5️⃣ Précharger les echelonnements
		List<Echelonnement> echelonnements = echelonnementRepository.findByPeriodePaieId(idPeriode);
		Map<Long, List<Echelonnement>> echelParPersonnel = echelonnements.stream()
				.collect(Collectors.groupingBy(e -> e.getPretPersonnel().getPersonnel().getId()));

		// 6️⃣ Précharger les bulletins existants pour cumul
		List<BulletinPaie> bulletinsExistants = bulletinPaieRepository
				.findByPeriodePaieAnneeIdAndClotureTrue(periodePaieActif.getAnnee().getId());
		Map<Long, List<BulletinPaie>> bulletinsParPersonnel = bulletinsExistants.stream()
				.collect(Collectors.groupingBy(b -> b.getContratPersonnel().getPersonnel().getId()));

		// 7️⃣ Précharger les temps effectifs
		List<TempEffectif> allTempEffectif = tempeffectifRepository.findByPeriodePaieId(idPeriode);
		Map<Long, TempEffectif> tempEffectifParPersonnel = allTempEffectif.stream()
				.collect(Collectors.toMap(t -> t.getPersonnel().getId(), t -> t));


            // Récupérer tous les plannings actifs pour les contrats
		List<PlanningConge> allPlanningConge = planningCongeRepository.findByStatutTrue();
		Map<Long, PlanningConge> planningParContrat = allPlanningConge.stream()
				.collect(Collectors.toMap(p -> p.getContratPersonnel().getId(), p -> p));


		// 7️⃣ Traiter chaque personnel
		for (Personnel person : personnelList) {

			ContratPersonnel contrat = contratParPersonnel.get(person.getId());
			if (contrat == null) continue;

			LivreDePaie livrePaie = new LivreDePaie();

			// 🔹 Ancienneté
			long anneesAnciennete = ChronoUnit.YEARS.between(
					contrat.getPersonnel().getDateArrivee().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					LocalDate.now()
			);
			int anciennete = (int) (anneesAnciennete + contrat.getAncienneteInitial());
			int op = anciennete < 2 ? 0 : Math.min(anciennete, 25);

			// 🔹 Nombre de parts
			float nbPart = calculNbrepart(person.getNombrEnfant(), person);

			// 🔹 Primes et retenues
			List<PrimePersonnel> primesPers = primesParPersonnel.getOrDefault(person.getId(), Collections.emptyList());
			List<PrimePersonnel> listIndemniteBrut = new ArrayList<>();
			List<PrimePersonnel> listIndemniteNonBrut = new ArrayList<>();
			List<PrimePersonnel> listRetenueMutuelle = new ArrayList<>();
			List<PrimePersonnel> listRetenueSociale = new ArrayList<>();
			List<PrimePersonnel> listGainsNet = new ArrayList<>();

			for (PrimePersonnel kprme : primesPers) {
				switch (kprme.getPrime().getEtatImposition()) {
					case 1 -> listIndemniteBrut.add(kprme);
					case 2 -> listIndemniteNonBrut.add(kprme);
					case 3 -> {
						listIndemniteBrut.add(kprme);
						listIndemniteNonBrut.add(kprme);
					}
					case 4 -> listRetenueMutuelle.add(kprme);
					case 5 -> listGainsNet.add(kprme);
					case 6 -> listRetenueSociale.add(kprme);
				}
			}

			// 🔹 Echelonnements
			List<Echelonnement> echelsPers = echelParPersonnel.getOrDefault(person.getId(), Collections.emptyList());
			double somAvance = 0D;
			double somPretAlios = 0D;

          // Marquer les échelonnements comme payés directement en mémoire
			for (Echelonnement e : echelsPers) {
				long pretId = e.getPretPersonnel().getPret().getId();
				if (pretId == 2L) { // AVANCE
					somAvance += e.getMontant();
					e.setPaye(true);
				} else if (pretId == 1L || pretId == 3L) { // PRET ALOES
					somPretAlios += e.getMontant();
					e.setPaye(true);
				}
			}
			if (!echelsPers.isEmpty()) {
				echelonnementRepository.saveAll(echelsPers);
			}
//			double somAvance = echelsPers.stream()
//					.filter(e -> e.getPretPersonnel().getPret().getId() == 2L)
//					.mapToDouble(Echelonnement::getMontant).sum();
//			double somPretAlios = echelsPers.stream()
//					.filter(e -> e.getPretPersonnel().getPret().getId() == 1L || e.getPretPersonnel().getPret().getId() == 3L)
//					.mapToDouble(Echelonnement::getMontant).sum();

			int countbull=0;
			countbull=bulletinPaieRepository.findNbrebulletinTrueCount(contrat.getId());

			PlanningConge planningConge = planningParContrat.get(contrat.getId());

			TempEffectif tpeff=tempEffectifParPersonnel.get(person.getId());


			BulletinPaie	 bulletinpaiePrec=new BulletinPaie();
			bulletinpaiePrec=bulletinPaieRepository.findByBulletinAndPersonnelCloture(contrat.getPersonnel().getId(), periodePaieActif.getId()-1L);

			LivreDePaie livrePaiecalR = new LivreDePaie();

			if(countbull==0){

				Float nbpart=calculNbrepart(person.getNombrEnfant(),person);

				 livrePaiecalR = new LivreDePaie(contrat.getPersonnel().getMatricule(),contrat.getPersonnel().getNom()+" "+contrat.getPersonnel().getPrenom(),nbpart , op, contrat.getCategorie().getSalaireDeBase(),contrat.getSursalaire(), contrat.getIndemniteLogement(),0d, 0d,contrat,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);

				if ( tpeff==null){
					livrePaiecalR = new LivreDePaie(contrat.getPersonnel().getMatricule(),contrat.getPersonnel().getNom()+" "+contrat.getPersonnel().getPrenom(), nbpart ,op, contrat.getCategorie().getSalaireDeBase(),contrat.getSursalaire(), contrat.getIndemniteLogement(),somAvance, somPretAlios,contrat,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
				}else{
					livrePaiecalR = new LivreDePaie(contrat.getPersonnel().getMatricule(),contrat.getPersonnel().getNom()+" "+contrat.getPersonnel().getPrenom(),nbpart, op, contrat.getCategorie().getSalaireDeBase(),contrat.getSursalaire(), contrat.getIndemniteLogement(),somAvance, somPretAlios,contrat,tpeff,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
				}

			}else{

				System.out.println("########################################### DATE D'ARRIVEE ###########################"+contrat.getPersonnel().getDateArrivee());
				int op1= Anciennet(contrat.getPersonnel().getDateArrivee()) +contrat.getAncienneteInitial();
				System.out.println("########################################### Anciennete ###########################"+op1);
                System.out.println("########################################### Personnel ###########################"+person.toString());
				Float nbpart1=calculNbrepart(person.getNombrEnfant(),person);
                System.out.println("########################################### Personnel nbre de part  ########################### "+nbpart1);

				List<BulletinPaie> monbull=bulletinPaieRepository.findTop1ByContratPersonnelOrderByIdDesc(contrat);
				if(monbull.size()==0){} else{
					if ( tpeff==null){
						livrePaiecalR = new LivreDePaie(contrat.getPersonnel().getMatricule(),contrat.getPersonnel().getNom()+" "+contrat.getPersonnel().getPrenom(), nbpart1, op, contrat.getCategorie().getSalaireDeBase(),contrat.getSursalaire(), contrat.getIndemniteLogement(),somAvance, somPretAlios,contrat,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
					}else{

						livrePaiecalR = new LivreDePaie(contrat.getPersonnel().getMatricule(),contrat.getPersonnel().getNom()+" "+contrat.getPersonnel().getPrenom(),  nbpart1, op, contrat.getCategorie().getSalaireDeBase(),contrat.getSursalaire(), contrat.getIndemniteLogement(),somAvance, somPretAlios,contrat,tpeff,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);

					}

				}
			}

			livrePaiecalR.setPeriodePaie(periodePaieActif);
			livrePaiecalR.setCMUPatronal(livrePaiecalR.getCMUPatronal());
			livrePaiecalR.setCMUSalarial(livrePaiecalR.getCMUPatronal());
			livrePaiecalR.setContratPersonnel(contrat);
			livrePaiecalR.setJourTravail(livrePaiecalR.getJourTravail());
			livrePaiecalR.setTemptravail(livrePaiecalR.getTemptravail());

			// 🔹 Bulletin
			BulletinPaie detailsBull = new BulletinPaie();
			detailsBull.setJourTravail(livrePaiecalR.getJourTravail());
			detailsBull.setTemptravail(livrePaiecalR.getTemptravail());
			detailsBull.setAnciennete(livrePaiecalR.getAnciennete());
			detailsBull.setSalairbase(livrePaiecalR.getSalaireBase());
			detailsBull.setSursalaire(livrePaiecalR.getSursalaire());
			detailsBull.setPrimeanciennete(livrePaiecalR.getPrimeAnciennete());
			detailsBull.setIndemnitelogement(livrePaiecalR.getIndemniteLogement());
			detailsBull.setBrutImposable(livrePaiecalR.getBrutImposable());
			detailsBull.setBasecnps(livrePaiecalR.getBasecnps());
			detailsBull.setIts(livrePaiecalR.getIts());
			detailsBull.setCn(livrePaiecalR.getCn());
			detailsBull.setIgr(livrePaiecalR.getIgr());
			detailsBull.setTotalretenuefiscal(livrePaiecalR.getTotalRetenueFiscale());
			detailsBull.setCnps(livrePaiecalR.getCnps());
			detailsBull.setAutreIndemImposable(livrePaiecalR.getAutreIndemImposable());
			detailsBull.setAutreImposable(livrePaiecalR.getAutreImposable());
			detailsBull.setCongeAc(false);

			if(bulletinpaiePrec==null){

				detailsBull.setTempsOfpresence(livrePaiecalR.getTempspresence());
				detailsBull.setMoisOfpresence(livrePaiecalR.getMoisdepresence());
				BulletinPaie	 bulletinpaiePrecLev2=new BulletinPaie();
				bulletinpaiePrecLev2=bulletinPaieRepository.findByBulletinAndPersonnelCloture(contrat.getPersonnel().getId(), periodePaieActif.getId()-2L);
				if(bulletinpaiePrecLev2==null){}
				else{
					if(bulletinpaiePrecLev2.getCongeAc()==true){
						detailsBull.setTempsOfpresence(0);
						detailsBull.setMoisOfpresence(0);
					}

				}


			}else{

				if(bulletinpaiePrec.getCongeAc()==true){
					detailsBull.setTempsOfpresence(0);
					detailsBull.setMoisOfpresence(0);
				}else{
					detailsBull.setTempsOfpresence(livrePaiecalR.getTempspresence());
					detailsBull.setMoisOfpresence((livrePaiecalR.getMoisdepresence()));
				}
			}
			detailsBull.setNombrePart(nbPart);
			detailsBull.setAnciennete(op);
			detailsBull.setSalairbase(contrat.getCategorie().getSalaireDeBase());
			detailsBull.setSursalaire(contrat.getSursalaire());
			detailsBull.setIndemnitelogement(contrat.getIndemniteLogement());
			detailsBull.setAvanceetacompte(somAvance);
			detailsBull.setPretaloes(somPretAlios);

			// 🔹 Cumul des bulletins précédents
			List<BulletinPaie> bulletinsPers = bulletinsParPersonnel.getOrDefault(person.getId(), Collections.emptyList());
			double cumulIgr = bulletinsPers.stream().mapToDouble(b -> safeDouble(b.getIgr())).sum();
			double cumulIts = bulletinsPers.stream().mapToDouble(b -> safeDouble(b.getIts())).sum();
			double cumulCn = bulletinsPers.stream().mapToDouble(b -> safeDouble(b.getCn())).sum();
			double cumulCnps = bulletinsPers.stream().mapToDouble(b -> safeDouble(b.getCnps())).sum();
			double cumulNet = bulletinsPers.stream().mapToDouble(b -> safeDouble(b.getNetapayer())).sum();

			// 🔹 Ajouter le mois courant


			cumulIgr += safeDouble(livrePaiecalR.getIgr() );
			cumulIts += safeDouble(livrePaiecalR.getIts());
			cumulCn += safeDouble(livrePaiecalR.getCn());
			cumulCnps += safeDouble(livrePaiecalR.getCnps());
			cumulNet += safeDouble(livrePaiecalR.getNetPayer());
			double cumulRetenuNet = safeSum(cumulIgr, cumulIts, cumulCn, cumulCnps);




			detailsBull.setCumulIgr(cumulIgr);
			detailsBull.setCumulIts(cumulIts);
			detailsBull.setCumulCn(cumulCn);
			detailsBull.setCumulCnpsSal(cumulCnps);
			detailsBull.setCumulSalaireNet(cumulNet);
			detailsBull.setCumulRetenueNet(cumulRetenuNet);
			detailsBull.setAvanceetacompte(livrePaiecalR.getAvceAcpte());
			detailsBull.setPretaloes(livrePaiecalR.getPretAlios());
			detailsBull.setCMUPatronal(livrePaiecalR.getCMUPatronal());
			detailsBull.setCMUSalarial(livrePaiecalR.getCMUSalarial());
			detailsBull.setCarec(livrePaiecalR.getCarec());
			detailsBull.setTotalretenue(livrePaiecalR.getTotalRetenue());
			detailsBull.setIndemniteRepresentation(livrePaiecalR.getIndemniteRepresentation());
			detailsBull.setIndemniteTransport(livrePaiecalR.getIndemniteTransport());
			detailsBull.setAutreNonImposable(livrePaiecalR.getAutreNonImposable());
			detailsBull.setBrutNonImposable(livrePaiecalR.getBrutNonImposable());
			detailsBull.setIndemniteRespons(livrePaiecalR.getIndemniteResponsabilte());
			detailsBull.setIndemniteTransportImp(livrePaiecalR.getIndemniteTransportImp());
			detailsBull.setRegularisation(livrePaiecalR.getRegularisation());
			detailsBull.setAutrePrelevement(livrePaiecalR.getAutrePrelevment());
			detailsBull.setNetapayer(livrePaiecalR.getNetPayer());
			detailsBull.setTotalbrut(livrePaiecalR.getTotalBrut());
			detailsBull.setImpotSalaire(livrePaiecalR.getIs());
			detailsBull.setTa(livrePaiecalR.getTa());
			detailsBull.setJourTravail(livrePaiecalR.getJourTravail());
			detailsBull.setTemptravail(livrePaiecalR.getTemptravail());
			detailsBull.setFpc(livrePaiecalR.getFpc());
			detailsBull.setFpcregul(livrePaiecalR.getFpcregul());
			detailsBull.setPrestationFamiliale(livrePaiecalR.getPrestationFamiliale());
			detailsBull.setAccidentTravail(livrePaiecalR.getAccidentTravail());
			detailsBull.setRetraite(livrePaiecalR.getRetraite());
			detailsBull.setTotalpatronal(livrePaiecalR.getTotalPatronal());
			detailsBull.setTotalmassesalarial(livrePaiecalR.getTotalMasseSalariale());
			detailsBull.setRetenueSociiale(livrePaiecalR.getRetenueSociiale());
			detailsBull.setCalculer(true);
			detailsBull.setCloture(false);
			detailsBull.setPeriodePaie(livrePaiecalR.getPeriodePaie());
			detailsBull.setContratPersonnel(livrePaiecalR.getContratPersonnel());
			detailsBull.setJourTravail(livrePaiecalR.getJourTravail());
			livrePaiecalR.setBullpaie(detailsBull);
			livrePaie.setBullpaie(detailsBull);
			//monechel=echelonnementRepository.findById(monEchelonnment.get(k).getId()).orElseThrow(() -> new EntityNotFoundException("monEchelonnment not found for id " + monEchelonnment.get(finalK1).getId()));
			//monechel.setPaye(true);
			//monechel=echelonnementRepository.save(monechel);
			livrePaie = livrePaiecalR;
			livredepaieList.add(livrePaie);
		}

		// 8️⃣ Pagination
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), livredepaieList.size());
		Page<LivreDePaie> pageResult = new PageImpl<>(livredepaieList.subList(start, end), pageable, livredepaieList.size());

		livreDEPaieList.setRows(pageResult.getContent());
		livreDEPaieList.setTotal(pageResult.getTotalElements());

		long endTime = System.currentTimeMillis();
		logger.info("✅ GÉNÉRATION TERMINÉE EN {} secondes", (endTime - startTime) / 1000.0);

		return livreDEPaieList;
	}

    @Override
    public LivreDePaieSpecialeDTO genererOptimiseEmployeeMois1(Pageable pageable, Long idPeriode) {
        logger.info(">>>>> DÉBUT DE GÉNÉRATION DU BULLETIN [PÉRIODE : {}]", idPeriode);
        long startTime = System.currentTimeMillis();

        LivreDePaieSpecialeDTO livreDEPaieList = new LivreDePaieSpecialeDTO();
        livreDePaieSpeciales = new ArrayList<LivreDePaieSpeciale>();

        // 1️⃣ Précharger la période
        PeriodePaie periodePaieActif = periodePaieRepository.findById(idPeriode)
                .orElseThrow(() -> new EntityNotFoundException("PériodePaie not found: " + idPeriode));

        // 2️⃣ Précharger tout le Personnel standart actif
        List<Employee> personnelList = employeeRepository.findByActifOrderByNomCompletAsc(true);

        // 3️⃣ Précharger tous les contrats actifs
        List<SpecialContract> contrats = specialContractRepository.findByActifTrue();
        Map<Long, SpecialContract> contratParPersonnel = contrats.stream()
                .collect(Collectors.toMap(c -> c.getEmployee().getId(), c -> c));

        // 4️⃣ Précharger les primes
//        List<PrimePersonnel> primes = primePersonnelRepository.findByPeriodePaieId(idPeriode);
//        Map<Long, List<PrimePersonnel>> primesParPersonnel = primes.stream()
//                .collect(Collectors.groupingBy(p -> p.getContratPersonnel().getPersonnel().getId()));

        // 5️⃣ Précharger les echelonnements
        List<Echelonnement> echelonnements = echelonnementRepository.findByPeriodePaieId(idPeriode);
        Map<Long, List<Echelonnement>> echelParPersonnel = echelonnements.stream()
                .collect(Collectors.groupingBy(e -> e.getPretPersonnel().getEmployee().getId()));


        // 7️⃣ Précharger les temps effectifs
        List<TempEffectif> allTempEffectif = tempeffectifRepository.findByPeriodePaieId(idPeriode);
        Map<Long, TempEffectif> tempEffectifParPersonnel = allTempEffectif.stream()
                .collect(Collectors.toMap(t -> t.getEmployee().getId(), t -> t));



        for (Employee person : personnelList) {

            SpecialContract contrat = contratParPersonnel.get(person.getId());
            if (contrat == null) continue;

            LivreDePaieSpeciale livrePaie = new LivreDePaieSpeciale();


            // 🔹 Echelonnements
            List<Echelonnement> echelsPers = echelParPersonnel.getOrDefault(person.getId(), Collections.emptyList());
            double somAvance = 0D;
            double somPretAlios = 0D;

            // Marquer les échelonnements comme payés directement en mémoire
            for (Echelonnement e : echelsPers) {
                long pretId = e.getPretPersonnel().getPret().getId();
                if (pretId == 2L) { // AVANCE
                    somAvance += e.getMontant();
                    e.setPaye(true);
                } else if (pretId == 1L || pretId == 3L) { // PRET ALOES
                    somPretAlios += e.getMontant();
                    e.setPaye(true);
                }
            }
            if (!echelsPers.isEmpty()) {
                echelonnementRepository.saveAll(echelsPers);
            }
            int countbull=0;
            countbull=bulletinPaieRepository.findNbrebulletinTrueCount(contrat.getId());
            TempEffectif tpeff=tempEffectifParPersonnel.get(person.getId());


            BulletinSpeciale	 bulletinpaiePrec=new BulletinSpeciale();
            //  bulletinpaiePrec=bulletinPaieRepository.findByBulletinAndPersonnelCloture(contrat.getEmployee().getId(), periodePaieActif.getId()-1L);

            LivreDePaieSpeciale livrePaiecalR = new LivreDePaieSpeciale();



            // Float nbpart=calculNbrepart(person.getNombrEnfant(),person);

          //  livrePaiecalR = new LivreDePaieSpeciale(contrat.getEmployee().getMatricule(),contrat.getEmployee().getNomComplet(),somAvance,somPretAlios,contrat,tpeff,periodePaieActif);

            if ( tpeff==null){
                livrePaiecalR = new LivreDePaieSpeciale(contrat.getEmployee().getMatricule(),contrat.getEmployee().getNomComplet(),somAvance,somPretAlios,contrat,null,periodePaieActif);
            }else{
                livrePaiecalR = new LivreDePaieSpeciale(contrat.getEmployee().getMatricule(),contrat.getEmployee().getNomComplet(),somAvance,somPretAlios,contrat,tpeff,periodePaieActif);
            }


            livrePaiecalR.setPeriodePaie(periodePaieActif);

            livrePaiecalR.setContratPersonnel(contrat);
            livrePaiecalR.setJourTravail(livrePaiecalR.getJourTravail());
            livrePaiecalR.setTemptravail(livrePaiecalR.getTemptravail());

            // 🔹 Bulletin
            BulletinSpeciale detailsBull = new BulletinSpeciale();
            detailsBull= toBulletinSpeciale(livrePaiecalR);
//            detailsBull.setJourTravail(livrePaiecalR.getJourTravail());
//            detailsBull.setTemptravail(livrePaiecalR.getTemptravail());
//
//
//
//            detailsBull.setAvceAcpte(somAvance);
//            detailsBull.setPretAlios(somPretAlios);

            // 🔹 Cumul des bulletins précédents
            //List<BulletinSpeciale> bulletinsPers = bulletinsParPersonnel.getOrDefault(person.getId(), Collections.emptyList());

            //   double cumulNet = bulletinsPers.stream().mapToDouble(b -> safeDouble(b.getNetPayer())).sum();

            // 🔹 Ajouter le mois courant


            //   cumulNet += safeDouble(livrePaiecalR.getNetPayer());

//            detailsBull.setMatricule(livrePaiecalR.getMatricule());
//            detailsBull.setNomPrenom(livrePaiecalR.getNomPrenom());
//            detailsBull.setRegularisation(livrePaiecalR.getRegularisation());
//
//            detailsBull.setNetPayer(livrePaiecalR.getNetPayer());
//
//            detailsBull.setJourTravail(livrePaiecalR.getJourTravail());
//            detailsBull.setTemptravail(livrePaiecalR.getTemptravail());

            detailsBull.setCalculer(true);
            detailsBull.setCloture(false);
            detailsBull.setPeriodePaie(livrePaiecalR.getPeriodePaie());
            detailsBull.setSpecialContract(livrePaiecalR.getContratPersonnel());
//            detailsBull.setJourTravail(livrePaiecalR.getJourTravail());
//            livrePaiecalR.setBulletinSpeciale(detailsBull);
           // livrePaie.setBulletinSpeciale(detailsBull);
            //monechel=echelonnementRepository.findById(monEchelonnment.get(k).getId()).orElseThrow(() -> new EntityNotFoundException("monEchelonnment not found for id " + monEchelonnment.get(finalK1).getId()));
            //monechel.setPaye(true);
            //monechel=echelonnementRepository.save(monechel);
            livrePaie = livrePaiecalR;
            livrePaie.setBulletinSpeciale(detailsBull);
            livreDePaieSpeciales.add(livrePaie);
        }

        // 8️⃣ Pagination
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), livreDePaieSpeciales.size());
        Page<LivreDePaieSpeciale> pageResult = new PageImpl<>(livreDePaieSpeciales.subList(start, end), pageable, livreDePaieSpeciales.size());

        livreDEPaieList.setRows(pageResult.getContent());
        livreDEPaieList.setTotal(pageResult.getTotalElements());

        long endTime = System.currentTimeMillis();
        logger.info("✅ GÉNÉRATION TERMINÉE EN {} secondes", (endTime - startTime) / 1000.0);

        return livreDEPaieList;


    }





    @Transactional
    public LivreDePaieDTOV2 genererOptimiseMoisVersion2(Pageable pageable, Long idPeriode) {

        long start = System.currentTimeMillis();

        LivreDePaieDTOV2 dto = new LivreDePaieDTOV2();
        livredepaieListV2 = new ArrayList<>();

        PeriodePaie periode = periodePaieRepository.findById(idPeriode)
                .orElseThrow(() -> new EntityNotFoundException("Période introuvable"));

        // 🔹 Personnel actif
        List<Personnel> personnelList =
                personnelRepository.findByStatutAndRetraitEffectOrderByNomAsc(true, false);

        // 🔹 Contrats
        Map<Long, ContratPersonnel> contratParPersonnel =
                contratPersonnelRepository.findByStatutTrue()
                        .stream()
                        .collect(Collectors.toMap(
                                c -> c.getPersonnel().getId(),
                                c -> c
                        ));

        // 🔹 Primes
        Map<Long, List<PrimePersonnel>> primesParPersonnel =
                primePersonnelRepository.findByPeriodePaieId(idPeriode)
                        .stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getContratPersonnel().getPersonnel().getId()
                        ));

        // 🔹 Échelonnements (prêts / avances)
        Map<Long, List<Echelonnement>> echelsParPersonnel =
                echelonnementRepository.findByPeriodePaieId(idPeriode)
                        .stream()
                        .collect(Collectors.groupingBy(
                                e -> e.getPretPersonnel().getPersonnel().getId()
                        ));

        // 🔹 Temps effectif
        Map<Long, TempEffectif> tempEffectifParPersonnel =
                tempeffectifRepository.findByPeriodePaieId(idPeriode)
                        .stream()
                        .collect(Collectors.toMap(
                                t -> t.getPersonnel().getId(),
                                t -> t
                        ));

        // 🔹 Congés
        Map<Long, PlanningConge> planningParContrat =
                planningCongeRepository.findByStatutTrue()
                        .stream()
                        .collect(Collectors.toMap(
                                p -> p.getContratPersonnel().getId(),
                                p -> p
                        ));

        // 🔹 Bulletins précédents (CUMUL)
        Map<Long, List<BulletinPaie>> bulletinsParPersonnel =
                bulletinPaieRepository.findByPeriodePaieBefore(periode)
                        .stream()
                        .collect(Collectors.groupingBy(
                                b -> b.getContratPersonnel().getId()
                        ));

        // ================== BOUCLE PAIE ==================
        for (Personnel person : personnelList) {

            ContratPersonnel contrat = contratParPersonnel.get(person.getId());
            if (contrat == null) continue;

            LivreDePaieV2 livre = construireLivreDePaie(
                    person,
                    contrat,
                    periode,
                    primesParPersonnel.getOrDefault(person.getId(), Collections.emptyList()),
                    echelsParPersonnel.getOrDefault(person.getId(), Collections.emptyList()),
                    tempEffectifParPersonnel.get(person.getId()),
                    planningParContrat.get(contrat.getId()),
                    bulletinsParPersonnel.getOrDefault(person.getId(), Collections.emptyList())
            );
            livre.setBullpaie(chargerBulletinDepuisLivre(livre));
            livredepaieListV2.add(livre);
        }

        // 🔹 Pagination
        int startRow = (int) pageable.getOffset();
        int endRow = Math.min(startRow + pageable.getPageSize(), livredepaieListV2.size());
        Page<LivreDePaieV2> page =
                new PageImpl<>(livredepaieListV2.subList(startRow, endRow), pageable, livredepaieListV2.size());

        dto.setRows(page.getContent());
        dto.setTotal(page.getTotalElements());
        dto.setResult("success");
        logger.info("Paie générée en {} ms", System.currentTimeMillis() - start);
        return dto;
    }



    private LivreDePaieV2 construireLivreDePaie(
            Personnel person,
            ContratPersonnel contrat,
            PeriodePaie periode,
            List<PrimePersonnel> primesPers,
            List<Echelonnement> echelsPers,
            TempEffectif tpeff,
            PlanningConge plconge,
            List<BulletinPaie> bulletinsPrec) {

        LivreDePaieV2 livre = new LivreDePaieV2();
        final int JOURS_OUVRABLES_MOIS = 30;
        livre.setMatricule(person.getMatricule());
        livre.setNomPrenom(person.getNom() + " " + person.getPrenom());
        livre.setPeriodePaie(periode);
        livre.setContratPersonnel(contrat);

        // ================== ANCIENNETÉ ==================
        int anciennete = (int) ChronoUnit.YEARS.between(
                person.getDateArrivee().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()
        ) + contrat.getAncienneteInitial();
        livre.setAnciennete(Math.max(0, anciennete));

        // ================== NOMBRE DE PARTS ==================
        livre.setNombrePart(calculNbrepart(person.getNombrEnfant(), person));

        // ================== TEMPS EFFECTIF ==================
        //int joursPresence = tpeff != null ?  tpeff.getJourspresence() : JOURS_OUVRABLES_MOIS;
        int joursPresence = tpeff != null ? (int) Math.ceil(tpeff.getJourspresence()) : JOURS_OUVRABLES_MOIS;
        livre.setJourTravail(joursPresence);
        livre.setTemptravail(joursPresence);

        livre.setMoisdepresence(
                ProvisionConge.calculerTempsPresence(
                        person.getDateRetourcge(),
                        periode.getDatefin()
                )
        );

        // ================== SALAIRE DE BASE PRORATISÉ ==================
        double salaireCategorie = Optional.ofNullable(contrat.getCategorie())
                .map(c -> c.getSalaireDeBase())
                .orElse(0D);

//        if (salaireCategorie <= 0) {
//            throw new IllegalStateException(
//                    "Salaire de base non défini pour le contrat : " + contrat.getId()
//            );
//        }

        double salaireBase = Math.ceil(
                salaireCategorie * joursPresence / JOURS_OUVRABLES_MOIS
        );

        livre.setSalaireBase(salaireBase);
        double sursalaire = Optional.ofNullable(contrat.getSursalaire()).orElse(0D);

        double sarsalaire = Math.ceil(
                sursalaire * joursPresence / JOURS_OUVRABLES_MOIS
        );

        livre.setSurSalaire(sarsalaire);

        double transport = Optional.ofNullable(contrat.getIndemniteTransport()).orElse(0D);

        double transportImposable = Math.ceil(
                transport * joursPresence / JOURS_OUVRABLES_MOIS
        );

        livre.setIndemniteTransportImposable(transportImposable);


        livre.setPrimeAnciennete(salaireBase*livre.getAnciennete()/100);
        // ================== PRIMES ==================
        classerPrimes(primesPers, livre);

        // ================== PRÊTS / AVANCES ==================
        double somAvance = 0D;
        double somPretAlios = 0D;
        for (Echelonnement e : echelsPers) {
            long pretId = e.getPretPersonnel().getPret().getId();
            if (pretId == 2L) { // AVANCE
                somAvance += e.getMontant();
                e.setPaye(true);
            } else if (pretId == 1L || pretId == 3L) { // PRET ALOES
                somPretAlios += e.getMontant();
                e.setPaye(true);
            }
        }
        livre.setAvance(somAvance);
        livre.setPretAlios(somPretAlios);
       // calculerEchelonnements(echelsPers, livre);

        // ================== BRUT & RETENUES ==================
        double brutImposable = calculerBrutImposable(livre);
        double totalRetenue = calculerTotalRetenue(livre);

        livre.setBrutImposable(brutImposable);
        livre.setBrutNonImposable(calculerBrutNonImposable(livre) );

        livre.setTotalRetenue(totalRetenue);

        // ================== TYPE EMPLOYÉ ==================
        boolean estSalarie = Boolean.TRUE.equals(contrat.getPersonnel().getCarec());

        if (estSalarie) {
            calculerChargesSalariales(livre);
            calculerChargesPatronales(livre);
        } else {
            annulerCharges(livre);
        }

        // ================== NET À PAYER ==================
        double regularisation = 0D;
        livre.setNetPayer(Math.ceil(
                brutImposable
                        + sommeIndemnites(livre)
                        + regularisation
                        - totalRetenue
        ));

        // ================== CUMUL ==================
        calculerCumuls(bulletinsPrec, livre,livre.getPeriodePaie().getDatefin().getYear());

        // ================== MASSE SALARIALE ==================
        livre.setTotalMasseSalariale(Math.ceil(
                brutImposable + sommeIndemnites(livre) + livre.getTotalPatronal()
        ));

        return livre;
    }


    private BulletinPaie chargerBulletinDepuisLivre(
            LivreDePaieV2 livre
    ) {

        BulletinPaie bull = new BulletinPaie();

        // 🔹 Cumuls annuels (année civile uniquement)
        bull.setCumulIgr(livre.getCumulIgr());
        bull.setCumulIts(livre.getCumulIts());
        bull.setCumulCn(livre.getCumulCn());
        bull.setCumulCnpsSal(livre.getCumulCnpsSalariale());
        bull.setCumulSalaireNet(livre.getCumulSalaireNet());
        bull.setCumulRetenueNet(bull.getCumulIts() + bull.getCumulCnpsSal());

        // 🔹 Avances & prêts
        bull.setAvanceetacompte(livre.getAvance());
        bull.setPretaloes(livre.getPretAlios());

        // 🔹 Indemnités
        bull.setIndemniteRepresentation(livre.getIndemniteRepresentation());
        bull.setIndemniteTransport(livre.getIndemniteTransport());
        bull.setIndemniteTransportImp(livre.getIndemniteTransportImposable());
        bull.setIndemniteRespons(livre.getIndemniteResponsabilite());
        bull.setAutreNonImposable(livre.getAutreNonImposable());
        bull.setBrutNonImposable(livre.getBrutNonImposable());
        bull.setRegularisation(livre.getRegularisation());
        bull.setSalairbase(livre.getSalaireBase());
        bull.setSursalaire(livre.getSurSalaire());
        bull.setPrimeanciennete(livre.getPrimeAnciennete());
        bull.setIndemnitelogement(livre.getIndemniteLogement());
        bull.setIndemniteRepresentation(livre.getIndemniteRepresentation());
        bull.setAutreIndemImposable(livre.getAutreIndemImposable());
        bull.setBrutImposable(livre.getBrutImposable());
        // 🔹 Bruts / nets
        bull.setTotalbrut(livre.getTotalBrut());
        bull.setTotalretenue(livre.getTotalRetenue());
        bull.setNetapayer(livre.getNetPayer());

        // 🔹 Charges salariales
        bull.setImpotSalaire(livre.getIs());
        bull.setIts(livre.getIts());
        bull.setTotalretenuefiscal(livre.getTotalRetenueFiscale());
        bull.setBasecnps(livre.getBaseCnps());
        bull.setRetenueSociiale(livre.getRetenueSociiale());
        bull.setCnps(livre.getCnpsSalariale());
        bull.setTotalretenue(livre.getTotalRetenue());
        // 🔹 Charges patronales
        bull.setTa(livre.getTa());
        bull.setFpc(livre.getFpc());
        bull.setFpcregul(livre.getFpcregul());
        bull.setPrestationFamiliale(livre.getPrestationFamiliale());
        bull.setAccidentTravail(livre.getAccidentTravail());
        bull.setRetraite(livre.getRetraite());
        bull.setTotalpatronal(livre.getTotalPatronal());
        bull.setTotalmassesalarial(livre.getTotalMasseSalariale());

        // 🔹 Temps de travail
//        bull.setJourTravail(livre.getJourTravail());
//        bull.setTemptravail(livre.getTemptravail());

        // 🔹 CAREC / CMU (sécurisé)
        bull.setCarec(livre.getCarec());
//        bull.setCMUPatronal(livre.getCarec() ? livre.getCMUPatronal() : 0D);
//        bull.setCMUSalarial(livre.getCarec() ? livre.getCMUSalarial() : 0D);

        // 🔹 Métadonnées
        bull.setCalculer(true);
        bull.setCloture(false);
        bull.setPeriodePaie(livre.getPeriodePaie());
        bull.setContratPersonnel(livre.getContratPersonnel());

        // 🔹 Liaison bidirectionnelle
        livre.setBullpaie(bull);

        return bull;
    }

    private void annulerCharges(LivreDePaieV2 livre) {

        livre.setTotalRetenue(0D);
        //livre.setTotalEchelonnement(0D);
        livre.setTotalPatronal(0D);
        livre.setNetPayer(0);
    }
    private void calculerChargesPatronales(LivreDePaieV2 livre) {

        double base = livre.getBrutImposable();

        livre.setTa(Math.ceil(base * 0.4 / 100));
        livre.setFpc(Math.ceil(base * 0.6 / 100));
        livre.setRetraite(Math.ceil(base * 7.7 / 100));

        livre.setTotalPatronal(
                livre.getTa()
                        + livre.getFpc()
                        + livre.getRetraite()
                        + livre.getPrestationFamiliale()
                        + livre.getAccidentTravail()
        );
    }
    private void calculerChargesSalariales(LivreDePaieV2 livre) {

        double base = livre.getBrutImposable(); // ⚠️ UNIQUEMENT imposable

        livre.setCnpsSalariale(Math.ceil(calculCNPS(base)));
        Double ricf = getRICF(livre.getNombrePart());
        double itsbrut =Math.ceil(calculerITS(base,true));
        livre.setIts(Math.max(0, itsbrut - ricf / 12));

    }
    private double calculerBrutNonImposable(LivreDePaieV2 livre) {

        double total = 0;

        for (PrimePersonnel p : livre.getIndemniteNonBrut()) {
            total += safeDouble(p.getMontant());
        }
        return Math.ceil(total);

    }
    private void classerPrimes(List<PrimePersonnel> primes, LivreDePaieV2 livre) {

        List<PrimePersonnel> brut = new ArrayList<>();
        List<PrimePersonnel> nonBrut = new ArrayList<>();
        List<PrimePersonnel> retenue = new ArrayList<>();
        List<PrimePersonnel> gainsNet = new ArrayList<>();

        for (PrimePersonnel p : primes) {
            switch (p.getPrime().getEtatImposition()) {
                case 1 -> brut.add(p);
                case 2 -> nonBrut.add(p);
                case 3 -> { brut.add(p); nonBrut.add(p); }
                case 4 -> retenue.add(p);
                case 5 -> gainsNet.add(p);
            }
        }

        livre.setIndemniteBrut(brut);
        livre.setIndemniteNonBrut(nonBrut);
        livre.setRetenueMutuelle(retenue);
        livre.setGainsNet(gainsNet);
    }
//    private double calculerBrutImposable(LivreDePaieV2 livre) {
//        return livre.getSalaireBase()
//                + sommePrimes(livre.getIndemniteBrut());
//    }
    private double calculerBrutImposable(LivreDePaieV2 livre) {

        double brut = 0D;

        // Salaire de base
        brut += livre.getSalaireBase();

        // Sur-salaire
        brut += livre.getSurSalaire();

        // Indemnité logement
        brut += livre.getIndemniteLogement();
        // Indemnité de representation
        brut += livre.getIndemniteRepresentation();

        // Indemnité de representation
        brut += livre.getIndemniteTransportImposable();
        // Indemnités brutes
        if (livre.getIndemniteBrut() != null) {
            brut += livre.getIndemniteBrut()
                    .stream()
                    .mapToDouble(PrimePersonnel::getMontant)
                    .sum();
        }

        return Math.ceil(brut);
    }
    private double calculerTotalRetenue(LivreDePaieV2 livre) {
        return safeDouble(livre.getIts())
                + safeDouble(livre.getCnpsSalariale())
                + sommePrimes(livre.getRetenueMutuelle());
    }
    private double sommeIndemnites(LivreDePaieV2 livre) {
        return sommePrimes(livre.getIndemniteNonBrut())
                + sommePrimes(livre.getGainsNet());
    }

//    private void calculerCumuls(List<BulletinPaie> prec, LivreDePaieV2 livre) {
//
//        double cumulNet = prec.stream().mapToDouble(b -> safeDouble(b.getNetapayer())).sum();
//        cumulNet += safeDouble(livre.getNetPayer());
//
//        livre.setCumulNet(cumulNet);
//    }
//    private void calculerCumuls(List<BulletinPaie> prec, LivreDePaieV2 livre) {
//
//        double cumulNet = 0D;
//        double cumulIts = 0D;
//        double cumulCnpsSalariale = 0D;
//        double cumulBrutImposable = 0D;
//        double cumulCnpsPatronale= 0D;
//
//        List<BulletinPaie> bulletinsAnnee = tousLesBulletins.stream()
//                .filter(b -> b.getDatePaie() != null)
//                .filter(b -> b.getDatePaie().getYear() == anneeCourante)
//                .collect(Collectors.toList());
//
//        if (prec != null && !prec.isEmpty()) {
//
//            cumulNet = prec.stream()
//                    .mapToDouble(b -> safeDouble(b.getNetapayer()))
//                    .sum();
//
//            cumulIts = prec.stream()
//                    .mapToDouble(b -> safeDouble(b.getIts()))
//                    .sum();
//
//            cumulCnpsSalariale = prec.stream()
//                    .mapToDouble(b -> safeDouble(b.getCnps()))
//                    .sum();
//
//            cumulBrutImposable = prec.stream()
//                    .mapToDouble(b -> safeDouble(b.getBrutImposable()))
//                    .sum();
//
//            cumulCnpsPatronale = prec.stream()
//                    .mapToDouble(b -> safeDouble(b.getCnps()))
//                    .sum();
//
//
//        }
//
//        // 🔹 Ajouter le mois courant
//        cumulNet += safeDouble(livre.getNetPayer());
//        cumulIts += safeDouble(livre.getIts());
//        cumulCnpsSalariale += safeDouble(livre.getCnpsSalariale());
//        cumulBrutImposable+= safeDouble(livre.getBrutImposable());
//        // 🔹 Affectation au livre
//        livre.setCumulNet(cumulNet);
//        livre.setCumulIts(cumulIts);
//        livre.setCumulCnpsSalariale(cumulCnpsSalariale);
//        livre.setCumulCnpsPatronale();
//        livre.setCumulBrutImposable();
//    }

    private void calculerCumuls(
            List<BulletinPaie> tousLesBulletins,
            LivreDePaieV2 livre,
            int anneeCourante
    ) {

        double cumulNet = 0D;
        double cumulIts = 0D;
        double cumulCnpsSalariale = 0D;

        List<BulletinPaie> bulletinsAnnee = tousLesBulletins.stream()
                .filter(b -> b.getCloture() == true)
                .filter(b -> b.getPeriodePaie().getDatefin().getYear() == anneeCourante)
                .collect(Collectors.toList());

        cumulNet = bulletinsAnnee.stream()
                .mapToDouble(b -> safeDouble(b.getNetapayer()))
                .sum();

        cumulIts = bulletinsAnnee.stream()
                .mapToDouble(b -> safeDouble(b.getIts()))
                .sum();

        cumulCnpsSalariale = bulletinsAnnee.stream()
                .mapToDouble(b -> safeDouble(b.getCnps()))
                .sum();

        // 🔹 Ajouter le bulletin courant
        cumulNet += safeDouble(livre.getNetPayer());
        cumulIts += safeDouble(livre.getIts());
        cumulCnpsSalariale += safeDouble(livre.getCnpsSalariale());

        livre.setCumulNet(cumulNet);
        livre.setCumulIts(cumulIts);
        livre.setCumulCnpsSalariale(cumulCnpsSalariale);
    }


    private double sommePrimes(List<PrimePersonnel> primes) {

        if (primes == null || primes.isEmpty()) {
            return 0D;
        }

        double total = 0D;

        for (PrimePersonnel pp : primes) {

            if (pp == null || pp.getMontant() == null) continue;

            double montant = pp.getMontant();
            double valeur = pp.getValeur() != null ? pp.getValeur() : 1D;

            // 🔹 Prime avec taux (%)
            if (pp.getPrime() != null && pp.getPrime().getTaux() != null) {
                total += valeur * (montant + (montant * pp.getPrime().getTaux() / 100));
            }

            // 🔹 Prime avec plafond exonéré
            else if (pp.getPrime() != null && pp.getPrime().getMtExedent() != null) {
                total += Math.max(0, montant - pp.getPrime().getMtExedent());
            }

            // 🔹 Prime simple
            else {
                total += valeur * montant;
            }
        }

        return Math.ceil(total);
    }



    public int countnbreJrdu(Date dateRetourDernierConge, Date dateDepartConge, ContratPersonnel Contratp) {
        // TODO Auto-generated method stub

        int tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge,dateDepartConge);
        int rf=(int) (tps*2.2*1.25);
        Double[]ancienete= calculAnciennete(Contratp.getCategorie().getSalaireDeBase(),Contratp.getPersonnel().getDateArrivee());
        double newancienete;
        if(Contratp.getAncienneteInitial()!=0) {
            newancienete=ancienete[1] +Contratp.getAncienneteInitial();
        }else{
            newancienete=ancienete[1];
        }
        double anc=(int)newancienete ;

        int jourSuppAnc=0; int jourSuppDam = 0;int jourSuppMed = 0;

        if(anc>5 && anc<=10)  jourSuppAnc=1;
        if(anc>10 && anc<=15) jourSuppAnc=2;
        if(anc>15 && anc<=20) jourSuppAnc=3;
        if(anc>20 && anc<=25) jourSuppAnc=5;
        if(anc>25 && anc<=30) jourSuppAnc=7;
        if(anc>30) jourSuppAnc=8;

        Double age= DifferenceDate.valAge(new Date(), Contratp.getPersonnel().getDateNaissance());
        if(Contratp.getPersonnel().getSexe().equals("Feminin") && age<=21 && Contratp.getPersonnel().getNombrEnfant()>0){
            jourSuppDam=2*Contratp.getPersonnel().getNombrEnfant();
        }
        if(Contratp.getPersonnel().getSexe().equals("Feminin") && age>21 && Contratp.getPersonnel().getNombrEnfant()>0){

            if(Contratp.getPersonnel().getNombrEnfant()>=4)jourSuppDam=2*1;
            if(Contratp.getPersonnel().getNombrEnfant()>=5)jourSuppDam=2*2;
            if(Contratp.getPersonnel().getNombrEnfant()>=6)jourSuppDam=2*3;
            if(Contratp.getPersonnel().getNombrEnfant()>=7)jourSuppDam=2*4;
            if(Contratp.getPersonnel().getNombrEnfant()>=8)jourSuppDam=2*5;
            if(Contratp.getPersonnel().getNombrEnfant()>=9)jourSuppDam=2*6;
        }

        if(Contratp.getPersonnel().getSituationMedaille()==1 ){
            jourSuppMed=1;
        }
        int rfp=(int) (jourSuppAnc+jourSuppDam+jourSuppMed);
        return (int) rfp+rf;
    }



    // 🔹 Méthode utilitaire pour éviter les nulls
	private double safeDouble(Double val) {
		return val != null ? val : 0D;
	}

	//@SuppressWarnings("null")
	public LivreDePaie calculbullFirst(ContratPersonnel ctratpersonnellz,PeriodePaie periodePaieActif,int anc){
		List<PrimePersonnel> listIndemniteBrut=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listIndemniteNonBrut=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listRetenueMutuelle=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listRetenueSociale=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listGainsNet=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listIndemnite =  primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId());
		if(listIndemnite.size()>0){
			for(PrimePersonnel kprme:listIndemnite){
				 if(kprme.getPrime().getEtatImposition()==1)
				 {
					 listIndemniteBrut.add(kprme);
				 }
				 if(kprme.getPrime().getEtatImposition()==2)
				 {
					 listIndemniteNonBrut.add(kprme);
				 }
				 if(kprme.getPrime().getEtatImposition()==3)
				 {
					 if(kprme.getPrime().getMtExedent()!=null)
					 {listIndemniteNonBrut.add(kprme);
					 listIndemniteNonBrut.add(kprme);}
				 }
				if(kprme.getPrime().getEtatImposition()==4)
				{
					listRetenueMutuelle.add(kprme);
				}
				if(kprme.getPrime().getEtatImposition()==5)
				{
					listGainsNet.add(kprme);
				}
				if(kprme.getPrime().getEtatImposition()==6)
				{
					listRetenueSociale.add(kprme);
				}
			}
			
		}
		
		LivreDePaie	 livrePaiecalpm = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), anc, ctratpersonnellz.getCategorie().getSalaireDeBase(),5000d, ctratpersonnellz.getIndemniteLogement(),0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		try { 
		
			while (livrePaiecalpm.getNetPayer()!=ctratpersonnellz.getNetAPayer()) {		 				
				 Double nouvSursal=0d;Double nouvDiff=0d;Double nouvMontantBrutImp= 0d;
				nouvMontantBrutImp=Math.rint(ctratpersonnellz.getNetAPayer()*livrePaiecalpm.getBrutImposable()/livrePaiecalpm.getNetPayer());
				nouvDiff=nouvMontantBrutImp-livrePaiecalpm.getBrutImposable();						
				nouvSursal=nouvDiff+livrePaiecalpm.getSursalaire();						
				livrePaiecalpm = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), anc, ctratpersonnellz.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnellz.getIndemniteLogement(), 0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		//	 logger.info("*********************SECOND BULLETIN********************############## SECOND BULLETIN #############-----------"+livrePaiecal.toString());	
		  }
		
		 
		} catch (Exception e) {
			System.out.println("FINISH"+ e.getMessage());
		} 
		 return livrePaiecalpm;
	}

    public Float calculNbrepart(Integer nbEnfant, Personnel pers) {

        // Sécurité absolue
        if (pers == null) {
            return 0F;
        }

        Integer situation = pers.getSituationMatrimoniale();
        if (situation == null) {
            return 0F;
        }

        int enfants = (nbEnfant != null) ? nbEnfant : 0;

        float nbPart = 0F;

        // Célibataire (2), Divorcé (3), Veuf (4) sans enfant
        if ((situation == 2 || situation == 3 || situation == 4) && enfants == 0) {
            nbPart = 1F;
        }

        // Célibataire (2), Divorcé (3) avec enfants
        else if ((situation == 2 || situation == 3) && enfants > 0) {
            nbPart = 1.5F + (enfants * 0.5F);
        }

        // Marié (1) sans enfant
        else if (situation == 1 && enfants == 0) {
            nbPart = 2F;
        }

        // Marié (1) ou Veuf (4) avec enfants
        else if ((situation == 1 || situation == 4) && enfants > 0) {
            nbPart = 2F + (enfants * 0.5F);
        }

        // Plafond fiscal
        return Math.min(nbPart, 5F);
    }


//    public Float calculNbrepart44(Integer nbEnfant, Personnel pers) {
//
//        if (pers == null || pers.getSituationMatrimoniale()== null) {
//            return 0F;
//        }
//
//        if (nbEnfant == null) {
//            nbEnfant = 0;
//        }
//
//        Float nbPart = 0F;
//
//        Integer situation = pers.getSituationMatrimoniale();
//
//        if ((situation == 2 || situation == 3 || situation == 4) && nbEnfant == 0) {
//            nbPart = 1F;
//        }
//
//        else if ((situation == 2 || situation == 3) && nbEnfant > 0) {
//            nbPart = 1.5F + (nbEnfant * 0.5F);
//        }
//
//        else if (situation == 1 && nbEnfant == 0) {
//            nbPart = 2F;
//        }
//
//        else if ((situation == 1 || situation == 4) && nbEnfant > 0) {
//            nbPart = 2F + (nbEnfant * 0.5F);
//        }
//
//        if (nbPart > 5F) {
//            nbPart = 5F;
//        }
//
//        return nbPart;
//    }

    public Float calculNbrepartOld( Integer nbEnfant, Personnel pers){

		Float nbPart = 0F;
			
	
			if((pers.getSituationMatrimoniale() == 2 || pers.getSituationMatrimoniale() == 3 || pers.getSituationMatrimoniale() == 4) && nbEnfant == 0)
				nbPart = (float) 1;
			
		
			if((pers.getSituationMatrimoniale() == 2 || pers.getSituationMatrimoniale() == 3) && nbEnfant > 0){
				nbPart = (float) (1.5 + (nbEnfant * 0.5));
				
				if(nbPart>5)
					nbPart = (float) 5;
			}
		
			if(pers.getSituationMatrimoniale() == 1 && nbEnfant == 0)
				nbPart = (float) 2;
				
	
			if((pers.getSituationMatrimoniale() == 1 || pers.getSituationMatrimoniale() == 4) && nbEnfant > 0){
				nbPart = (float) (2 + (nbEnfant * 0.5));
						
				if(nbPart>5)
					nbPart = (float) 5;
			}
			return nbPart;
	}
	public  String dateToString(Date date, String sFormat){
		return new SimpleDateFormat(sFormat).format(date);
	}
	
	public  Double[] contratPersonnel(Double salaireCategoriel, Date dateEntree){
		
		Double[] tab = new Double[5];
		
		Double anciennete = (double) 0;
		
		
		double age = DifferenceDate.valAge(new Date(), dateEntree);
		
		int partieEntiere = (int) age; 
		int partieApresVirg = (int)((age - partieEntiere) * 12); 
		
		
		if(age>=2)
			anciennete = (double) (salaireCategoriel*partieEntiere/100);
		
		tab[0] = anciennete;
		
		
		tab[1] = (double) partieEntiere;
		tab[2] = (double)(partieApresVirg);
		
	
		
		return tab;
	}
	public Double calculerAccidentTravail(double brutImposable,double indemniteRepresentation){
		Double pf = brutImposable + indemniteRepresentation;
		if(pf > 70000)
			pf = 70000 * 2.0 / 100;
		else if(pf > 0)
			pf = brutImposable * 2 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calcalerPrestationFamilial(double brutImposable,double indemniteRepresentation){
		Double pf = brutImposable + indemniteRepresentation;
		if(pf > 70000)
			pf = 70000 * 5.75 / 100;
		else if(pf > 0)
			pf = brutImposable * 5.75 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calculerIndemniteTransport(double brutImposable){
		Double it = brutImposable * 10 / 100 - 25000.0;
		if(it > 0)
			it = 25000.0;
		else
			it = 0.0;
		return it;
	}
	
	public Double calculerIndemniterRepresentation(double brutImposable){
		Double ir = brutImposable * 10 / 100 - 25000.0;
		if(ir > 0)
			ir = brutImposable * 10 / 100 - 25000.0;
		else 
			ir = 0.0;
		return ir;
	}
	
	public Double calculerCN(double brutImposable){
		Double cn;
		if(brutImposable > 250000.0)
			cn = (brutImposable - 250000.0) * 8 / 100 + 4700;
		else if(brutImposable > 162500.0)
			cn = (brutImposable - 162500.0) * 4 / 100 + 1200;
		else if(brutImposable > 62500.0)
			cn = brutImposable * 1.2 / 100 - 750;
		else
			cn = 0.0;
		return cn;
	}
	
	
	
public int Anciennet(Date dateEntree)	{
	
	double age = DifferenceDate.valAge(new Date(), dateEntree);
	
	int partieEntiere = (int) age; 
	int partieApresVirg = (int)((age - partieEntiere) * 12); 
	return partieEntiere;
}
	
public  Double[] calculAnciennete(Double salaireCategoriel, Date dateEntree){
		
		Double[] tab = new Double[5];
		
		Double anciennete = (double) 0;
		
		
		double age = DifferenceDate.valAge(new Date(), dateEntree);
		
		int partieEntiere = (int) age; 
		int partieApresVirg = (int)((age - partieEntiere) * 12); 
		
		
		if(age>=2)
			anciennete = (double) (salaireCategoriel*partieEntiere/100);
		
		tab[0] = anciennete;
		
		
		tab[1] = (double) partieEntiere;
		tab[2] = (double) partieApresVirg;
		
	
		
		return tab;
	}
	
	public Double calculerIGR(double brutImposable,double its,double cn,double nombrePart){
		Double igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100;
		if(igr > 842167.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 60 / 160 - 98633.0 * nombrePart;
		else if(igr > 389084.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 45 / 145 - 44181.0 * nombrePart;
		else if(igr > 220334.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 35 / 135 - 24306.0 * nombrePart;
		else if(igr > 126584.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 25 / 125 - 11250.0 * nombrePart;
		else if(igr > 81584.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 20 / 120 - 7031.0 * nombrePart;
		else if(igr > 45584.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 115 - 4076.0 * nombrePart;
		else if(igr > 25000.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 110 - 2273.0 * nombrePart;
		else 
			igr = 0.0;
		return igr;
	}
    public Double calculCNPS(Double basecnps){
        Double cnps = (basecnps ); //3000000
        if(cnps < 3375000.0)
            cnps = (basecnps ) * 6.3 / 100;
        else
            cnps = 3375000.0 * 6.3 / 100;
        return cnps;
    }
//	public Double calculCNPS(double brutImposable,double indemniteRepresentation){
//		Double cnps = (brutImposable + indemniteRepresentation);
//		if(cnps < 1647315.0)
//			cnps = (brutImposable + indemniteRepresentation) * 6.3 / 100;
//		else
//			cnps = 1647315 * 6.3 / 100;
//		return cnps;
//	}

	@Override
	public List<BulletinPaie> rechercherBulletinMois(PeriodePaie periodePaie) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaie(periodePaie);
	}



	@Override
	public List<BulletinPaie> rechercherBulletinMoisCalculer(
			PeriodePaie periodePaie, boolean etat) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAndCalculer(periodePaie,etat);
	}

	@Override
	public BulletinPaieDTO generateLivreDePaie(Pageable pageable) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinDTO = new BulletinPaieDTO();
		List<BulletinPaie> bullList = new ArrayList<BulletinPaie>();
//		for(LivreDePaieV2 livre : livredepaieListV2)

            for(LivreDePaie livre : livredepaieList){
                BulletinPaie bull=  bulletinPaieRepository.findByBulletinAndPersonnelCalcultrue(livre.getContratPersonnel().getPersonnel().getId(),livre.getPeriodePaie().getId());
                if(bull==null){

                }else{
                    if(!bull.getContratPersonnel().getPersonnel().getStatut())
                        bulletinPaieRepository.delete(bull);
                    else
                        livre.getBullpaie().setId(bull.getId());
                }

                bullList.add(livre.getBullpaie());
            }

        bulletinPaieRepository.saveAll(bullList);
		int start =(int) pageable.getOffset();Page<BulletinPaie> pageImpianto=null;
		int end = (start + (int) pageable.getPageSize()) > bullList.size() ? bullList.size() : (start + pageable.getPageSize());
		pageImpianto=new PageImpl<BulletinPaie>(bullList.subList(start, end), pageable,bullList.size());

		bulletinDTO.setRows(pageImpianto.getContent());
		bulletinDTO.setTotal(pageImpianto.getTotalElements());


		bulletinDTO.setResult("Bulletin generes avec succes");
		return bulletinDTO;
	}

    @Override
    public BulletinSpecialeDTO generateEmployeLivreDePaie(Pageable pageable) {
        BulletinSpecialeDTO bulletinDTO = new BulletinSpecialeDTO();
        List<BulletinSpeciale> bullList = new ArrayList<BulletinSpeciale>();
//		for(LivreDePaieV2 livre : livredepaieListV2)

        for(LivreDePaieSpeciale livre : livreDePaieSpeciales){
            BulletinSpeciale bull=  bulletinSpecialeRepository.findByBulletinAndPersonnelCalcultrue(livre.getContratPersonnel().getEmployee()             .getId(),livre.getPeriodePaie().getId());
            if(bull==null){

            }else{
                if(!bull.getSpecialContract().getActif())
                    bulletinSpecialeRepository.delete(bull);
                else
                    livre.getBulletinSpeciale().setId(bull.getId());
            }

            bullList.add(livre.getBulletinSpeciale());
        }

        bulletinSpecialeRepository.saveAll(bullList);
        int start =(int) pageable.getOffset();Page<BulletinSpeciale> pageImpianto=null;
        int end = (start + (int) pageable.getPageSize()) > bullList.size() ? bullList.size() : (start + pageable.getPageSize());
        pageImpianto=new PageImpl<BulletinSpeciale>(bullList.subList(start, end), pageable,bullList.size());

        bulletinDTO.setRows(pageImpianto.getContent());
        bulletinDTO.setTotal(pageImpianto.getTotalElements());


        bulletinDTO.setResult("Bulletin generes avec succes");
        return bulletinDTO;
    }

    @Override
	public BulletinPaieDTO BulletinMoisCalculer(Pageable pageable,PeriodePaie periodePaie) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieAndCalculerTrue(pageable,periodePaie);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		if(page.getTotalElements()>0){bulletinPaieDTO.setResult("success");}else{bulletinPaieDTO.setResult("errors");}
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}

    @Override
    public BulletinSpecialeDTO BulletinMoisSpecialeCalculer(Pageable pageable, PeriodePaie periodePaie) {
        BulletinSpecialeDTO bulletinPaieDTO = new BulletinSpecialeDTO();
        Page<BulletinSpeciale> page = bulletinSpecialeRepository.findByPeriodePaieAndCalculerTrue(pageable,periodePaie);
        bulletinPaieDTO.setRows(page.getContent());
        bulletinPaieDTO.setTotal(page.getTotalElements());
        if(page.getTotalElements()>0){bulletinPaieDTO.setResult("success");}else{bulletinPaieDTO.setResult("errors");}
        logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
        return bulletinPaieDTO;
    }


    @Override
	public List<BulletinPaie> findBulletinByPeriodePaie(Long idPeriode) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieId(idPeriode);
	}
	@Override
	public List<BulletinPaie> rechercherBulletinAnnuel(Long anneeId,Long Idpers) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAnneeIdAndContratPersonnelPersonnelIdAndCalculer(anneeId,Idpers,true);
	}

	@Override
	public List<BulletinPaie> rechercherBulletinAnnuelglobal(Long anneeId) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
	}
	
	@Override
	public Double salaireMoyenMensuel(ContratPersonnel contratPersonnel) {
		// TODO Auto-generated method stub
		Double salairMoyenMensuelle = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findTop12ByContratPersonnel(contratPersonnel);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			salairMoyenMensuelle = bulletinPaie.getTotalbrut() - bulletinPaie.getIndemniteTransport() + salairMoyenMensuelle;
		}
	
		salairMoyenMensuelle = salairMoyenMensuelle / bulletinPaieList.size();
		return salairMoyenMensuelle;
	}

	@Override
	public Double indemniteMoyenMensuel(ContratPersonnel contratPersonnel) {
		// TODO Auto-generated method stub
		Double indemniteRepresentationMoyenMensuelle = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findTop12ByContratPersonnel(contratPersonnel);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			indemniteRepresentationMoyenMensuelle = bulletinPaie.getIndemniteRepresentation() +bulletinPaie.getAutreNonImposable() + indemniteRepresentationMoyenMensuelle;
		}
		indemniteRepresentationMoyenMensuelle = indemniteRepresentationMoyenMensuelle / bulletinPaieList.size();
		return indemniteRepresentationMoyenMensuelle;
	}


	@Override
	public Double MensuelCn(PeriodePaie maperiode) {		// TODO Auto-generated method stub
		
		Double MensuelCn = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelCn = bulletinPaie.getCn()  + MensuelCn;
		}
		
		return MensuelCn;
		
	}


	
	@Override
	public Double MensuelIgr(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgr = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgr = bulletinPaie.getIgr()  + MensuelIgr;
		}
		
		return MensuelIgr;
	}


	@Override
	public Double MensuelIs(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIs = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIs = bulletinPaie.getIts()  + MensuelIs;
		}
		
		return MensuelIs;
	}


	@Override
	public Double MensuelIgrPatron(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getImpotSalaire()  + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrut(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getTotalbrut() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelCnAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getCn() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelIgrAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getIgr()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrutAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getTotalbrut()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}
	@Override
	public Double[] MensuelBaseCnpsSup(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double[] tab = new Double[2];
		Double countff = 0.0;Double Mensuelbasecnps = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			 if(bulletinPaie.getBasecnps()>=1647315d ){
				 countff=countff+1;
			       Mensuelbasecnps = bulletinPaie.getBasecnps() + Mensuelbasecnps;
			 }
		}
		tab[0] = countff;
		tab[1] = Mensuelbasecnps;
		return tab;
	}


	@Override
	public Double[] MensuelBaseCnpsSup70000(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double[] tab = new Double[2];
		Double countff = 0.0;Double Mensuelbasecnps = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			 if(bulletinPaie.getBasecnps()>70000d  && bulletinPaie.getBasecnps()<1647315d  ){
				 countff=countff+1;
			       Mensuelbasecnps = bulletinPaie.getBasecnps() + Mensuelbasecnps;
			 }
		}
		tab[0] = countff;
		tab[1] = Mensuelbasecnps;
		return tab;
	}

	@Override
	public Double MensuelIsAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getIts()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelIgrPatronAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getImpotSalaire()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrutImpAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getBrutImposable()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}

	@Override
	public Double[] MensuelBaseCnpsInf(PeriodePaie periodePaie) {
		Double[] tab = new Double[2];
		Double countff = 0.0;Double Mensuelbasecnps = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(periodePaie);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			if( bulletinPaie.getBasecnps()>0d  &&bulletinPaie.getBasecnps()<70000d ){
				countff=countff+1;
				Mensuelbasecnps = bulletinPaie.getBasecnps() + Mensuelbasecnps;
			}
		}
		tab[0] = countff;
		tab[1] = Mensuelbasecnps;
		return tab;
	}


	@Override
	public Double MensuelJourtravail(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getJourTravail() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrutImposable(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getBrutImposable() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public List<BulletinPaie> rechercherBulletinAnneeCalculer(Long idanne,boolean etat) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAnneeIdAndCalculer(idanne, etat);
	}


	@Override
	public List<BulletinPaie> findAllBulletinByvirementforBanque(
			Long idPeriodePaie, Long idBanque) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByContratPersonnelPersonnelBanquekIdAndPeriodePaieIdAndCalculerTrue(idBanque,idPeriodePaie);
	}
	public Double [] MasseSalarialdeLexo(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double[] tab = new Double[2];
		Double countff = 0d;Double Massesalariale = 0d;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeIdAndCalculer(maperiode.getAnnee().getId(),true);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			//if(bulletinPaie.getBasecnps()>=1647315d ){
			if(bulletinPaie.getContratPersonnel().getTypeContrat().getId()==1L ||bulletinPaie.getContratPersonnel().getTypeContrat().getId()==2L )
			{	countff=countff+1;
            Massesalariale = bulletinPaie.getTotalmassesalarial() + Massesalariale;
			}
		}
		tab[0] = countff;
		tab[1] = Massesalariale;
		return tab;
	}

	@Override
	public Double MasseSalarialMois(PeriodePaie maperiode) {
		Double Massesalariale = 0d;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieIdAndCalculer(maperiode.getId()-1L,true);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			Massesalariale = bulletinPaie.getTotalmassesalarial() + Massesalariale;

		}
		return Massesalariale;
	}

	@Override
	public Integer Nbrebulletin(Long idcontratPersonnel) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findNbrebulletinTrueCount(idcontratPersonnel);
	}


	@Override
	public List<BulletinPaie> findBulletinByPeriodePaieContract(Long idPeriode) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieIdAndContratPersonnelPersonnelCarecTrue(idPeriode);
	}




    public BulletinSpeciale toBulletinSpeciale(LivreDePaieSpeciale source) {

        if (source == null) {
            return null;
        }

        BulletinSpeciale target = new BulletinSpeciale();

        target.setMatricule(source.getMatricule());
        target.setNomPrenom(source.getNomPrenom());

        target.setAvceAcpte(source.getAvceAcpte());
        target.setPretAlios(source.getPretAlios());
        target.setRegularisation(source.getRegularisation());

        target.setJourTravail(source.getJourTravail());
        target.setTemptravail(source.getTemptravail());

        target.setNetPayer(source.getNetPayer());

        target.setSpecialContract(source.getContratPersonnel());
        target.setPeriodePaie(source.getPeriodePaie());

        // si tu veux aussi garder la relation
        // target.setBulletinSpeciale(source.getBulletinSpeciale());

        return target;
    }

	public List<PrintLs> calculerMasseSalarialeParTypeContrat(PeriodePaie periode) {
		List<Object[]> resultats = bulletinPaieRepository.getMasseSalarialeParTypeContrat(periode.getId());
		List<PrintLs> listPrint = new ArrayList<>();

		for (Object[] row : resultats) {
			String type = (String) row[0];
			Double total = (Double) row[1];

			PrintLs dto = new PrintLs();
			dto.setS1(type);
			dto.setTitle1(type + "s"); // ex : Contractuels
			dto.setI1(total != null ? total.intValue() : 0);
			listPrint.add(dto);
		}
		return listPrint;
	}


	public List<PrintLs> calculerMasseSalarialeParSite(PeriodePaie periode) {
		List<Object[]> resultats = bulletinPaieRepository.getMasseSalarialeParSite(periode.getId());
		List<PrintLs> listPrint = new ArrayList<>();

		for (Object[] row : resultats) {
			String site = (String) row[0];
			Double total = (Double) row[1];

			PrintLs dto = new PrintLs();
			dto.setS1(site);
			dto.setTitle1(site ); // ex : Contractuels
			dto.setValue1(total != null ? total : 0);
			listPrint.add(dto);
		}
		return listPrint;
	}


	public List<PrintLs> calculerEffectifParSiteAlaPaie(PeriodePaie periode) {
		List<Object[]> resultats = bulletinPaieRepository.getEffectifParSite(periode.getId());
		  List<PrintLs> listPrint = new ArrayList<>();

		for (Object[] row : resultats) {
			String site = (String) row[0];
			Long total = ((Number) row[1]).longValue();


			PrintLs dto = new PrintLs();
			dto.setS1(site);
			dto.setTitle1(site ); // ex : Contractuels
			dto.setI1(total != null ? total.intValue() : 0);
			listPrint.add(dto);
		}
		return listPrint;
	}

	@Override
	public BulletinPaieDTO loadBulletinPaieSearch(Pageable pageable, String criteria) {
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		//Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContaining(pageable, maperiode.getId(), search);
		Page<BulletinPaie> page = bulletinPaieRepository.findByContratPersonnelPersonnelNomContainingIgnoreCase( pageable, criteria);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}

	@Override
	public BulletinPaieDTO finImprimbulletin(Long payrollId) {
		BulletinPaieDTO response = new BulletinPaieDTO();

		Optional<BulletinPaie> opt = bulletinPaieRepository.findById(payrollId);
		java.util.List<Societe> malist=societeService.findtsmois();

			if (!opt.isPresent()) {
				response.setRow(null);
				response.setTotal(0L);
				response.setResult(false);
				response.setStatus(false);
				response.setMessage("Aucun bulletin trouvé pour l'id " + payrollId);
				return response;
			}

		BulletinPaie bulletinPaie = opt.get();
		 bulletinPaie.setListImprimBulletinPaie(getlistImpData(bulletinPaie));
		response.setRow(bulletinPaie);
		response.setTotal(1L);
		if(malist.size()>0)
		{
		response.setResult(malist.get(0).getUrlLogo());
		}else{
			response.setResult(true);
		}
		response.setStatus(true);
		return response;
	}



}
