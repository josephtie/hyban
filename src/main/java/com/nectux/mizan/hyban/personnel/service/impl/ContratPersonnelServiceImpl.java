package com.nectux.mizan.hyban.personnel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nectux.mizan.hyban.paie.repository.PrimePersonnelRepository;
import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.parametrages.repository.TypeContratRepository;
import com.nectux.mizan.hyban.personnel.dto.CategorieDTO;
import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.utils.DifferenceDate;

import com.nectux.mizan.hyban.utils.GenericSpecifications;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.LivreDePaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.repository.BulletinPaieRepository;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.CategorieRepository;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.personnel.service.ContratPersonnelService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Utils;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("contratPersonnelService")
public class ContratPersonnelServiceImpl implements ContratPersonnelService {
	
	private static final Logger logger = LogManager.getLogger(ContratPersonnelServiceImpl.class);
	
	@Autowired FonctionRepository fonctionRepository;
	@Autowired CategorieRepository categorieRepository;
	@Autowired PersonnelRepository personnelRepository;
	@Autowired
    TypeContratRepository typeContratRepository;
	@Autowired ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PrimePersonnelRepository primePersonnelRepository;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ContratPersonnel save(ContratPersonnel contratPersonnel) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.save(contratPersonnel);
	}

	@Override
	public ContratPersonnelDTO save(Long id, Long idPersonnel, Long idCategorie, Long idFonction, Long idTypeContrat,
									String dateDebut, String dateFin, Double netAPayer, Double indemniteLogement, int ancienete, boolean statut,Double sursalaire,Double indemnitetransport,Double indemniterespons,Double indemniterepresent) {
		// TODO Auto-generated method stub
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try{
			ContratPersonnel contratPersonnel = new ContratPersonnel();
			if(id != null)
				contratPersonnel = contratPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));
			contratPersonnel.setFonction(fonctionRepository.findById(idFonction).orElseThrow(() -> new EntityNotFoundException("Fonction not found for id " + idFonction)));
			contratPersonnel.setCategorie(categorieRepository.findById(idCategorie).orElseThrow(() -> new EntityNotFoundException("Categorie not found for id " + idCategorie)));
			contratPersonnel.setPersonnel(personnelRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPersonnel)));
			contratPersonnel.setTypeContrat(typeContratRepository.findById(idTypeContrat).orElseThrow(() -> new EntityNotFoundException("Type contrat not found for id " + idTypeContrat)));
			
			contratPersonnel.setDateDebut(Utils.stringToDate(dateDebut, "dd/MM/yyyy"));
				if(contratPersonnel.getTypeContrat().getId()==1L)
				   contratPersonnel.setDateFin(null);
				else
					contratPersonnel.setDateFin(Utils.stringToDate(dateFin, "dd/MM/yyyy"));

			contratPersonnel.setNetAPayer(netAPayer);
			contratPersonnel.setIndemniteLogement(indemniteLogement);
			contratPersonnel.setIndemniteRepresent(indemniterepresent);
			contratPersonnel.setIndemniteTransport(indemnitetransport);
			//contratPersonnel.setIndemniteResp(indemniterespons);
			contratPersonnel.setSursalaire(sursalaire);
			contratPersonnel.setAncienneteInitial(ancienete);
			//contratPersonnel.setStatut(statut);
			contratPersonnel.setStatut(true);
			contratPersonnel.setDepart(false);
			
			contratPersonnel = contratPersonnelRepository.save(contratPersonnel);
			if(contratPersonnel.getTypeContrat().getId()==4L){
				contratPersonnel.getPersonnel().setStage(true);
				contratPersonnel.getPersonnel().setFonctionnaire(false);
				contratPersonnel.getPersonnel().setConsultant(false);

			}

			if(contratPersonnel.getTypeContrat().getId()==5L){
				contratPersonnel.getPersonnel().setConsultant(true);
				contratPersonnel.getPersonnel().setFonctionnaire(false);
				contratPersonnel.getPersonnel().setStage(false);
			}

			if(contratPersonnel.getTypeContrat().getId()==6L){
				contratPersonnel.getPersonnel().setConsultant(false);
				contratPersonnel.getPersonnel().setFonctionnaire(true);
				contratPersonnel.getPersonnel().setStage(false);
			}
			 personnelRepository.save(contratPersonnel.getPersonnel());
			contratPersonnelDTO.setRow(contratPersonnel);
			contratPersonnelDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(contratPersonnel.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			contratPersonnelDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT CONTRAT PERSONNEL").toString());
			ex.getStackTrace();
			System.out.println("#######################################################################");
			System.out.println(ex.getMessage());
		}
		return contratPersonnelDTO;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		ContratPersonnel contratPersonnel = contratPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(contratPersonnel == null)
			return false;
		contratPersonnelRepository.delete(contratPersonnel);
		return true;
	}

	@Override
	public ContratPersonnel findContratPersonnel(Long id) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<ContratPersonnel> findByPersonnel(Personnel personnel) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findByPersonnel(personnel);
	}

	@Override
	public List<ContratPersonnel> findByTypeContrat(TypeContrat typeContrat) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findByTypeContrat(typeContrat);
	}

	@Override
	public List<ContratPersonnel> findByCategorie(Categorie categorie) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findByCategorie(categorie);
	}

	@Override
	public List<ContratPersonnel> findByFonction(Fonction fonction) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findByFonction(fonction);
	}

	@Override
	public List<ContratPersonnel> findContratPersonnels() {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) contratPersonnelRepository.count();
	}

	@Override
	public ContratPersonnel findByPersonnelContratActif(Long idPers) {
		// TODO Auto-generated method stub
		return contratPersonnelRepository.findByPersonnelId(idPers);
	}

	@Override
	public List<ContratPersonnel> findExpireContract() throws Exception {
		// TODO Auto-generated method stub
		Date dateExpire = DateManager.addingDate(30);
		return contratPersonnelRepository.findByDateFinBetween(new Date(), dateExpire);
	}

	@Override
	public List<ContratPersonnel> findExpireContract(int delay) throws Exception {
		// TODO Auto-generated method stub
		Date dateExpire = DateManager.addingDate(delay);
		return contratPersonnelRepository.findByDateFinBetween(new Date(), dateExpire);
	}

	@Override
	public ContratPersonnelDTO findContratPersonnelk(Long id) {
		// TODO Auto-generated method stub
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		contratPersonnelDTO.setRow(contratPersonnelRepository.findByPersonnelIdAndStatut(id, true));
		contratPersonnelDTO.setResult(true);
		contratPersonnelDTO.setStatus(true);
		return contratPersonnelDTO;
	}
	
	@Override
	public ContratPersonnelDTO loadContratByPersonnel(Personnel personnel, Pageable pageable) {
		// TODO Auto-generated method stub
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Page<ContratPersonnel> page = contratPersonnelRepository.findByPersonnel(personnel, pageable);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO findAllfilter(Map<String, String> filters, Pageable pageable) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Specification<ContratPersonnel> specification = GenericSpecifications.fromMap(filters);
		Page<ContratPersonnel> page = contratPersonnelRepository.findAll(specification, pageable);
		contratPersonnelDTO.setResult(true);
		contratPersonnelDTO.setStatus(true);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO loadContratByPersonnel(Personnel personnel, Pageable pageable, String search) {
		// TODO Auto-generated method stub
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Page<ContratPersonnel> page = contratPersonnelRepository.findByPersonnel(personnel, pageable);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO loadContratPersonnelActif(Pageable pageable) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Page<ContratPersonnel> page = contratPersonnelRepository.findByStatut(true, pageable);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}
	
	/*@Override
	public ContratPersonnelDTO loadContratExpieredumois(Pageable pageable,Long IdTypctr, Date  dfin ) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Page<ContratPersonnel> page = contratPersonnelRepository.findByStatut(true, pageable);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}*/

	@Override
	public ContratPersonnelDTO loadContratPersonnelActif(Pageable pageable,	String search,	String search1) {
		// TODO Auto-generated method stub
		/*ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Page<ContratPersonnel> page = contratPersonnelRepository.findByStatutAndPersonnelMatriculeIgnoreCaseContainingOrPersonnelNomIgnoreCaseContaining(pageable,true,search,search );
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;*/
		
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		List<ContratPersonnel> myList2=new ArrayList<ContratPersonnel>();
		List<ContratPersonnel> myList=contratPersonnelRepository.findByStatutTrueAndDepartFalseAndPersonnelNomIgnoreCaseContaining(search);
		for(ContratPersonnel contratpersonnel1 : myList){
			if(contratpersonnel1.getPersonnel().getRetraitEffect()==false && contratpersonnel1.getDepart()==false && contratpersonnel1.getStatut()==true)
				myList2.add(contratpersonnel1);
			else{}
		}
		int start =(int) pageable.getOffset();
		int end = (start + (int) pageable.getPageSize()) > myList2.size() ? myList2.size() : (start + pageable.getPageSize());
		Page<ContratPersonnel>	pageImpianto=new PageImpl<ContratPersonnel>(myList2.subList(start, end), pageable,myList2.size());
		//Page<ContratPersonnel> pageImpianto = new PageImpl<ContratPersonnel>(myList);
		contratPersonnelDTO.setRows(pageImpianto.getContent());
		contratPersonnelDTO.setTotal(pageImpianto.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}

	@Override
	public List<ContratPersonnel> rechercherBytypeContrat(TypeContrat annee) {
		return null;
	}

	@Override
	public ContratPersonnelDTO loadContratActif(Pageable pageable) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try {
			Page<ContratPersonnel> page = contratPersonnelRepository.findByStatutTrueAndDepartFalseOrderByPersonnelNomAscPersonnelPrenomAsc(pageable);
			contratPersonnelDTO.setRows(page.getContent());
			contratPersonnelDTO.setTotal(page.getTotalElements());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO loadContratDepart(Pageable pageable) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try {
			Page<ContratPersonnel> page = contratPersonnelRepository.findByStatutTrueAndDepartTrueAndSoldeCalculeFalseOrderByPersonnelNomAscPersonnelPrenomAsc(pageable);
			contratPersonnelDTO.setRows(page.getContent());
			contratPersonnelDTO.setTotal(page.getTotalElements());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO loadContratDepart(Pageable pageable, String search) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try {
			Page<ContratPersonnel> page = contratPersonnelRepository.findByStatutTrueAndDepartTrueAndSoldeCalculeFalseAndPersonnelNomIgnoreCaseContainingOrderByPersonnelNomAscPersonnelPrenomAsc(pageable,search);
			contratPersonnelDTO.setRows(page.getContent());
			contratPersonnelDTO.setTotal(page.getTotalElements());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO loadContratActif(Pageable pageable, String search) {
		//TODO Implementer la recherche
		/*ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Page<ContratPersonnel> page = contratPersonnelRepository.findByStatutAndPersonnelMatriculeIgnoreCaseContainingOrPersonnelNomIgnoreCaseContaining(pageable,true, search, search);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		//logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;*/
		
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();Page<ContratPersonnel> pageImpianto=null;
        ;List<ContratPersonnel> myList2=new ArrayList<ContratPersonnel>();

        Page<ContratPersonnel> page =contratPersonnelRepository.searchContrat(search,pageable);
	/*	for(ContratPersonnel contrtpersonnel : myList){
			if(contrtpersonnel.getPersonnel().getRetraitEffect()==false && contrtpersonnel.getDepart()==false && contrtpersonnel.getStatut()==true)
			{
                myList2.add(contrtpersonnel);
            }else
			{	}
		}
		int start = (int)pageable.getOffset();
		int end = (start + (int) pageable.getPageSize()) > myList2.size() ? myList2.size() : (start + pageable.getPageSize());
		pageImpianto=new PageImpl<ContratPersonnel>(myList2.subList(start, end), pageable,myList2.size());
		//pageImpianto = new PageImpl<ContratPersonnel>(myList2);*/

		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		//logger.info(new StringBuilder().append(">>>>> CATEGORIES CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO endContract(Long id, String dateFin,String dateMod,Boolean depart,String ObservCtrat) {
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try{
			if(dateFin == ""){
				throw new Exception("Date de fin du contrat invalide.");
			}
			ContratPersonnel contratPersonnel = contratPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			contratPersonnel.setDateMod(DateManager.stringToDate(dateMod, "dd/MM/yyyy"));
			contratPersonnel.setDateFin(DateManager.stringToDate(dateFin, "dd/MM/yyyy"));

            contratPersonnel.setObservCtrat(ObservCtrat);
			contratPersonnel.setDepart(false);
			contratPersonnel.setStatut(false);
			contratPersonnel.setSoldeCalcule(false);
			if(Boolean.TRUE.equals(depart)){
				contratPersonnel.setDepart(true);
                contratPersonnel.setStatut(true);
				contratPersonnel.getPersonnel().setRetraitEffect(false);
				contratPersonnel.getPersonnel().setStatut(true);
				contratPersonnel.setSoldeCalcule(false);
				personnelRepository.save(contratPersonnel.getPersonnel());
			}
			contratPersonnel = contratPersonnelRepository.save(contratPersonnel);

			contratPersonnelDTO.setRow(contratPersonnel);
			contratPersonnelDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(contratPersonnel.toString()).append(" MAJ AVEC SUCCES").toString());
		} catch(Exception ex){
			contratPersonnelDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR FIN CONTRAT PERSONNEL").toString());
			ex.getStackTrace();
		}
		return contratPersonnelDTO;
	}

	@Override
	public ContratPersonnelDTO departDefinitif(Long contratId, String dateFinEffective) throws Exception {

		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try{
		ContratPersonnel contrat = contratPersonnelRepository.findById(contratId)
				.orElseThrow(() -> new RuntimeException("Contrat non trouvé"));


		contrat.setStatut(false);
		contrat.setDepart(true);
		contrat.setSoldeCalcule(true);
		contrat.getPersonnel().setStatut(false);
		contrat.getPersonnel().setRetraitEffect(true);
		contrat.setDateFin(Utils.stringToDate(dateFinEffective,"dd/MM/yyyy"));
		personnelRepository.save(contrat.getPersonnel());
			contrat=contratPersonnelRepository.save(contrat);
		contratPersonnelDTO.setRow(contrat);
		contratPersonnelDTO.setResult(true);
		contratPersonnelDTO.setResult("success");
		} catch(Exception ex){
			contratPersonnelDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR FIN CONTRAT PERSONNEL").toString());
			ex.getStackTrace();
		}
		return contratPersonnelDTO;

	}



	@Override
	public ContratPersonnelDTO updateContractSursalaire(Long id,Double sursalaire) {
		// TODO Auto-generated method stub
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		try{
			
			ContratPersonnel contratPersonnel = contratPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			contratPersonnel.setSursalaire(sursalaire);
			//contratPersonnel.setStatut(false);
			contratPersonnel = contratPersonnelRepository.save(contratPersonnel);			
			contratPersonnelDTO.setRow(contratPersonnel);
			contratPersonnelDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(contratPersonnel.toString()).append(" MAJ AVEC SUCCES").toString());
		} catch(Exception ex){
			contratPersonnelDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR FIN CONTRAT PERSONNEL").toString());
			ex.getStackTrace();
		}
		return contratPersonnelDTO;
	}	
	
	

//	@Override
//	public ContratPersonnelDTO loadContratExpieredumois(Long IdTypctr,String  ddeb,String  dfin) {
//		// TODO Auto-generated method stub
//		Date dateDeb;Date dateFin;
//		java.sql.Date dateDebE = null ;
//		java.sql.Date dateFinE = null ;
//		try {
//			dateDeb = DateManager.stringToDate(ddeb, "dd/MM/yyyy");
//			dateFin = DateManager.stringToDate(dfin, "dd/MM/yyyy");
//			dateDebE= new java.sql.Date(dateDeb.getTime());
//			dateFinE= new java.sql.Date(dateFin.getTime());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		//java.sql.Date dateFinE = new java.sql.Date(dateFin.getTime());
//		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
//		Page<ContratPersonnel> page = contratPersonnelRepository.findByTypeContratIdAndDateFinBetween(Pageable pageable, 2L, dateDebE, dateFinE);
//		contratPersonnelDTO.setRows(page.getContent());
//		contratPersonnelDTO.setTotal(page.getTotalElements());
//		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
//		return contratPersonnelDTO;
//	}

	public LivreDePaie calculbullFirst(ContratPersonnel ctratpersonnellz,PeriodePaie periodePaieActif){
		
		Double[]  ancienete=calculAnciennete(ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getPersonnel().getDateArrivee());
		double newancienete;
		if(ctratpersonnellz.getAncienneteInitial()!=0) {
			 newancienete=ancienete[1] +ctratpersonnellz.getAncienneteInitial();
		}else{
			newancienete=ancienete[1];
		}
		int anc=(int)newancienete;
		 int op=0;
		 if(anc < 2) op=0;		    		 
		 if(anc>=2 && anc<=25) op=anc;
		 if(anc>25) op=25;
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
			LivreDePaie	 livrePaiecalpm = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),5000d, ctratpersonnellz.getIndemniteLogement(),0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
			try { 
			 int pi=0;
				while (livrePaiecalpm.getNetPayer()!=ctratpersonnellz.getNetAPayer() || pi==3) {		 				
					 Double nouvSursal = 0d;Double nouvDiff= 0d;Double nouvMontantBrutImp=0d;
					nouvMontantBrutImp=Math.rint(ctratpersonnellz.getNetAPayer()*livrePaiecalpm.getBrutImposable()/livrePaiecalpm.getNetPayer());
					nouvDiff=nouvMontantBrutImp-livrePaiecalpm.getBrutImposable();						
					nouvSursal=nouvDiff+livrePaiecalpm.getSursalaire();						
					livrePaiecalpm = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnellz.getIndemniteLogement(), 0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
			//	 logger.info("*********************SECOND BULLETIN********************############## SECOND BULLETIN #############-----------"+livrePaiecal.toString());	
					pi=pi+1;
			  }
			
			 
			} catch (Exception e) {
				System.out.println("FINISH"+ e.getMessage());
			} 
			 return livrePaiecalpm;
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

	@Override
	public ContratPersonnelDTO loadContratExpieredumois(Pageable pageable,Long IdTypctr,
			String ddeb, String dfin) {
		// TODO Auto-generated method stub
		Date dateDeb;Date dateFin;
		java.sql.Date dateDebE = null ;
		java.sql.Date dateFinE = null ;
		try {
			dateDeb = DateManager.stringToDate(ddeb, "dd/MM/yyyy");
			dateFin = DateManager.stringToDate(dfin, "dd/MM/yyyy");
			dateDebE= new java.sql.Date(dateDeb.getTime());
			dateFinE= new java.sql.Date(dateFin.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//java.sql.Date dateFinE = new java.sql.Date(dateFin.getTime());
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();

		Page<ContratPersonnel> page = contratPersonnelRepository.findByTypeContratIdAndDateFinBetween( pageable,2L, dateDebE, dateFinE);
		contratPersonnelDTO.setRows(page.getContent());
		contratPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONTRATS PERSONNELS CHARGES AVEC SUCCES").toString());
		return contratPersonnelDTO;
		
	}




	
}
