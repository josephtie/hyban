package com.nectux.mizan.hyban.paie.web;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

import com.nectux.mizan.hyban.paie.entity.ImprimBulletinPaie;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.paie.service.JasperReportService;
import com.nectux.mizan.hyban.parametrages.entity.*;
import com.nectux.mizan.hyban.parametrages.repository.PlanningCongeRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.utils.*;
import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import com.nectux.mizan.hyban.paie.dto.LivreDePaieDTO;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.LivreDePaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.paie.repository.BulletinPaieRepository;
import com.nectux.mizan.hyban.paie.repository.PrimePersonnelRepository;
import com.nectux.mizan.hyban.paie.repository.TempEffectifRepository;
import com.nectux.mizan.hyban.parametrages.service.BanqueService;
import com.nectux.mizan.hyban.parametrages.service.CpteVirmtBanqueService;
import com.nectux.mizan.hyban.parametrages.service.RubriqueService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.utils.DifferenceDate;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/paie")
public class BulletinPaieController {

	
private static final Logger logger = LoggerFactory.getLogger(BulletinPaieController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	@Autowired private PrimePersonnelRepository primePersonnelRepository;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private SocieteService societeService;
	@Autowired private RubriqueService rubriqueService;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private BanqueService banqueService;
	@Autowired private CpteVirmtBanqueService cpteVirmtBanqueService;
	@Autowired private TempEffectifRepository tempeffectifRepository;
	@Autowired private PlanningCongeRepository planningCongeRepository;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@Autowired private JasperReportService jasperReportService;
	private final ResourceLoader resourceLoader;
	MethodsShared methodsShared = new MethodsShared();
	List<LivreDePaie> livredepaieList=null;
	private PeriodePaie maperiode;
	private String promotion;

	public BulletinPaieController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}



	@RequestMapping("/livrepaie")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		//modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activePayrollBook", "active");
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		//modelMap.addAttribute("bigTitle", "RH PAIE - CGECI");
		
	    maperiode=periodePaieService.findPeriodeactive();
	    if(maperiode==null){}
	    else{
	    	 modelMap.addAttribute("activeMois", maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
			 modelMap.addAttribute("activeMoisId", maperiode.getId());
			 modelMap.addAttribute("periode",  maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
	    }
	    modelMap.addAttribute("listeBanquesEmp", banqueService.getBanquesEntprise());
	    modelMap.addAttribute("listeBanques", banqueService.getBanques());
	    Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
			modelMap.addAttribute("bigTitle", "Livre de paie");
		    modelMap.addAttribute("listePrimes", rubriqueService.getRubriquesActives());
		    modelMap.addAttribute("listePrimesImp", rubriqueService.getRubriquesActivesType(1));
		    modelMap.addAttribute("listePrimesNonImpos", rubriqueService.getRubriquesActivesType(2));
            modelMap.addAttribute("listePrimesImposetNon", rubriqueService.getRubriquesActivesType(3));
		    modelMap.addAttribute("listePrimesMutuelle", rubriqueService.getRubriquesActivesType(4));
		    modelMap.addAttribute("listePrimesSociale", rubriqueService.getRubriquesActivesType(6));
		    modelMap.addAttribute("listePrimesGains", rubriqueService.getRubriquesActivesType(5));


		return "livrepaie";
	}

	@RequestMapping("/histobull")
	public String viewAccountTypehisto(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");

		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activePayrollBook", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-credit-card");
		modelMap.addAttribute("littleTitle", "Livre de paie");
		modelMap.addAttribute("bigTitle", "Historique des bulletins");
		modelMap.addAttribute("listePrimesImp", rubriqueService.getRubriquesActivesType(1));
		modelMap.addAttribute("listePrimesNonImpos", rubriqueService.getRubriquesActivesType(2));
		modelMap.addAttribute("listePrimesImposetNon", rubriqueService.getRubriquesActivesType(3));
		modelMap.addAttribute("listePrimesMutuelle", rubriqueService.getRubriquesActivesType(4));
		modelMap.addAttribute("listePrimesGains", rubriqueService.getRubriquesActivesType(5));
		maperiode=periodePaieService.findPeriodeactive();
		if(maperiode==null){}
		else{
			modelMap.addAttribute("activeMois", maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", maperiode.getId());
			modelMap.addAttribute("periode",  maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
		}
		Societe mysociete=null;
		List<Societe> malist=societeService.findtsmois();
		if(malist.size()>0)
		{mysociete=malist.get(0);
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
	/*    modelMap.addAttribute("listeBanquesEmp", banqueService.getBanquesEntprise());
	    modelMap.addAttribute("listeBanques", banqueService.getBanques());
	    modelMap.addAttribute("listeBanques1", banqueService.getBanques());*/
		return "histobull";
	}


	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/bulletinperiodeactifjson", method = RequestMethod.GET)
	public @ResponseBody BulletinPaieDTO getbulletine(@RequestParam(value="id", required=true) Long idperiod,@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		 //final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
	     PeriodePaie	 maperiode=periodePaieService.findPeriodePaie(idperiod);
		BulletinPaieDTO bulletinDTO = new BulletinPaieDTO();
		if(search == null || search == "" )
			bulletinDTO = bulletinPaieService.loadBulletinPaie(page,maperiode);
		else
			bulletinDTO = bulletinPaieService.loadBulletinPaie(page,maperiode ,search);
		
		return bulletinDTO;
	}


	@SuppressWarnings("unused")
	@RequestMapping(value="/chargerbulletinparperiode", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody BulletinPaieDTO chargerGratificationParPeriode(@RequestParam(value="id", required=true) Long id,
																		@RequestParam(value="search1", required=false) String search1,
																		@RequestParam(value="limit", required=false) Integer limit,
																		@RequestParam(value="offset", required=false) Integer offset,
																		@RequestParam(value="search", required=false) String search) {

		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		search=search1+search;
		System.out.println("loup loup loup"+search);
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.ASC, "id");
		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		PeriodePaie	 maperiode=periodePaieService.findPeriodePaie(id);
		BulletinPaieDTO bulletinDTO=new BulletinPaieDTO();
		if (search != null && !search.trim().isEmpty())

			bulletinDTO = bulletinPaieService.loadBulletinPaie(page,maperiode ,search);
		else
			bulletinDTO = bulletinPaieService.loadBulletinPaie(page,maperiode);

		return bulletinDTO ;
	}
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savebullPersonnel", method = RequestMethod.GET)
	public @ResponseBody LivreDePaieDTO saveBullpersonnel(@RequestParam(value="id", required=true) Long id,@RequestParam(value="limit", required=false) Integer limit,
                                                          @RequestParam(value="offset", required=false) Integer offset,
															Principal principal,ModelMap modelMap) {
		LivreDePaieDTO LivredePaieDTO = new LivreDePaieDTO();
		if(id==null ){
			PeriodePaie maperiode=periodePaieService.findPeriodeactive();
			id=maperiode.getId();
		}
        if(offset == null) offset = 0;
        if(limit == null) limit = 10;
		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
       // final PageRequest page = new PageRequest(offset/10, limit, Direction.ASC, "id");
		 // List<LivreDePaie> bulletinList = new ArrayList<LivreDePaie>();
       // Pageable pageable;
		  LivredePaieDTO=bulletinPaieService.genererMois1(page,id);

		  livredepaieList = new ArrayList<LivreDePaie>();
		  livredepaieList=LivredePaieDTO.getRows();
		  System.out.println("ppppppppppppppppppppppppppppppppppppppppppppp"+rubriqueService.getRubriquesActives().toString());
		modelMap.addAttribute("rubriques",rubriqueService.getRubriquesActives());
		LivredePaieDTO.setResult("success");
		 return LivredePaieDTO;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/calculalenvers-liste", method = RequestMethod.POST)
	public @ResponseBody List<LivreDePaie> calculalenversListe(@RequestParam(value="id", required=false) Long id,Principal principal,ModelMap modelMap) {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Démarrage du calcul à l'envers pour tous les contrats actifs.");
		maperiode=periodePaieService.findPeriodeactive();
		List<LivreDePaie> resultats = new ArrayList<>();
		List<ContratPersonnel> contratsActifs = contratPersonnelRepository.findByStatut(true);

		logger.info("{} contrats actifs récupérés.", contratsActifs.size());

		for (ContratPersonnel ctratpersonnel : contratsActifs) {
			Double netAPayer = ctratpersonnel.getNetAPayer();
			if (netAPayer == null || netAPayer == 0d) {
				logger.warn("Net à payer manquant pour le personnel : {}", ctratpersonnel.getPersonnel().getNom());
				continue;
			}

			logger.info("Traitement du personnel {} (matricule: {}, net à payer: {})",
					ctratpersonnel.getPersonnel().getNom(),
					ctratpersonnel.getPersonnel().getMatricule(),
					netAPayer
			);
			LivreDePaie livredePaie = null;
			try {
				//ContratPersonnel ctratpersonnelh=contratPersonnelRepository.findByPersonnelIdAndStatut(id, true);
				PlanningConge     planningConge = planningCongeRepository.findByContratPersonnelAndStatut(ctratpersonnel,true);
				TempEffectif tpeff;
				tpeff=tempeffectifRepository.findByPersonnelAndPeriodePaie(ctratpersonnel.getPersonnel(), maperiode);
				long anneesAnciennete = ChronoUnit.YEARS.between(ctratpersonnel.getPersonnel().getDateArrivee().toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate(), LocalDate.now() );
				//Double[]  ancienete=calculAnciennete(ctratpersonnel.getCategorie().getSalaireDeBase(),ctratpersonnel.getPersonnel().getDateArrivee());
				double newancienete;
				if(ctratpersonnel.getAncienneteInitial()!=0) {
					newancienete=anneesAnciennete +ctratpersonnel.getAncienneteInitial();
				}else{
					newancienete=anneesAnciennete;
				}
				int anc=(int)newancienete;
				int op=0;
				if(anc < 2) op=0;
				if(anc>=2 && anc<=25) op=anc;
				if(anc>25) op=25;
				Float nbpart=calculNbrepart(ctratpersonnel.getPersonnel().getNombrEnfant(),ctratpersonnel.getPersonnel());
				List<PrimePersonnel> listIndemniteBrut=new ArrayList<PrimePersonnel>();
				List<PrimePersonnel> listIndemniteNonBrut=new ArrayList<PrimePersonnel>();
				List<PrimePersonnel> listRetenueMutuelle=new ArrayList<PrimePersonnel>();
				List<PrimePersonnel> listRetenueSociale=new ArrayList<PrimePersonnel>();
				List<PrimePersonnel> listGainsNet=new ArrayList<PrimePersonnel>();
				List<PrimePersonnel> listIndemnite  =new ArrayList<PrimePersonnel>();
				listIndemnite =  primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnel.getPersonnel().getId(), maperiode.getId());
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
							{listIndemniteBrut.add(kprme);
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
						//listRetenueSociale
					}

				}

				livredePaie= new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), nbpart ,op, ctratpersonnel.getCategorie().getSalaireDeBase(),5000d, ctratpersonnel.getIndemniteLogement(),0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
				try {
					int i=0;
					for( i = 0; i < 20; i++){
						//(livredePaie.getNetPayer()==valued)
						Double nouvSursal = (double) 0;Double nouvDiff= (double) 0;Double nouvMontantBrutImp= (double) 0;
						nouvMontantBrutImp=Math.rint(ctratpersonnel.getNetAPayer()*livredePaie.getBrutImposable()/livredePaie.getNetPayer());
						nouvDiff=nouvMontantBrutImp-livredePaie.getBrutImposable();
						nouvSursal=nouvDiff+livredePaie.getSursalaire();
						livredePaie= new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), nbpart ,op, ctratpersonnel.getCategorie().getSalaireDeBase(),5000d, ctratpersonnel.getIndemniteLogement(),0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
						livredePaie = new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), ctratpersonnel.getPersonnel().getNombrePart(), op, ctratpersonnel.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnel.getIndemniteLogement(), 0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
						//	System.out.println("*********************SECOND BULLETIN*"+i);
						System.out.println("*SECOND BULLETIN#############-----------"+livredePaie.toString());
						// i++;
					}


				} catch (Exception e) {
					System.out.println("FINISH"+ e.getMessage());
				}

				ctratpersonnel.setSursalaire(livredePaie.getSursalaire());
				contratPersonnelRepository.save(ctratpersonnel);
				resultats.add(livredePaie);

				logger.info("Calcul réussi pour {}. Net final: {}, brut final: {}",
						ctratpersonnel.getPersonnel().getNom(), livredePaie.getNetPayer(), livredePaie.getBrutImposable());

			} catch (Exception e) {
				logger.error("Erreur pour le personnel {} : {}",
						ctratpersonnel.getPersonnel().getNom(), e.getMessage(), e);
			}
		}

		logger.info("Fin du traitement. {} bulletins générés.", resultats.size());
		return resultats;
	}



	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/calculalenvers", method = RequestMethod.POST)
	public @ResponseBody LivreDePaie calculalenvers(@RequestParam(value="idPersonnel", required=true) Long id,@RequestParam(value="netApayer", required=true) Double valued,	Principal principal) {
		LivreDePaie livredePaie = null;
		// List<ContratPersonnel> personnelListTrt = new ArrayList<ContratPersonnel>();
		 ContratPersonnel ctratpersonnel=contratPersonnelRepository.findByPersonnelIdAndStatut(id, true);
		    PlanningConge     planningConge = planningCongeRepository.findByContratPersonnelAndStatut(ctratpersonnel,true);
		     TempEffectif tpeff;
			 tpeff=tempeffectifRepository.findByPersonnelAndPeriodePaie(ctratpersonnel.getPersonnel(), maperiode);
		     long anneesAnciennete = ChronoUnit.YEARS.between(ctratpersonnel.getPersonnel().getDateArrivee().toInstant()
					 .atZone(ZoneId.systemDefault())
					 .toLocalDate(), LocalDate.now() );
			// Double[]  ancienete=calculAnciennete(ctratpersonnel.getCategorie().getSalaireDeBase(),ctratpersonnel.getPersonnel().getDateArrivee());
		    	double newancienete;
		    	if(ctratpersonnel.getAncienneteInitial()!=0) {
		    		 newancienete=anneesAnciennete + ctratpersonnel.getAncienneteInitial();
		    	}else{
		    		newancienete=anneesAnciennete;
		    	}
		    	int anc=(int)newancienete;
			 int op=0;
    		 if(anc < 2) op=0;		    		 
    		 if(anc>=2 && anc<=25) op=anc;
    		 if(anc>25) op=25;
			 Float nbpart=calculNbrepart(ctratpersonnel.getPersonnel().getNombrEnfant(),ctratpersonnel.getPersonnel());
			 List<PrimePersonnel> listIndemniteBrut=new ArrayList<PrimePersonnel>();
			 List<PrimePersonnel> listIndemniteNonBrut=new ArrayList<PrimePersonnel>();
		     List<PrimePersonnel> listRetenueMutuelle=new ArrayList<PrimePersonnel>();
		     List<PrimePersonnel> listRetenueSociale=new ArrayList<PrimePersonnel>();
		   List<PrimePersonnel> listGainsNet=new ArrayList<PrimePersonnel>();
			 List<PrimePersonnel> listIndemnite  =new ArrayList<PrimePersonnel>();
			 listIndemnite =  primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnel.getPersonnel().getId(), maperiode.getId());
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
							 {listIndemniteBrut.add(kprme);
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
						//listRetenueSociale
					}
					
				} 
			 
		 livredePaie= new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), nbpart ,op, ctratpersonnel.getCategorie().getSalaireDeBase(),5000d, ctratpersonnel.getIndemniteLogement(),0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
				try {
					int i=0;
						for( i = 0; i < 20; i++){
							//(livredePaie.getNetPayer()==valued)
							 Double nouvSursal = (double) 0;Double nouvDiff= (double) 0;Double nouvMontantBrutImp= (double) 0;
							nouvMontantBrutImp=Math.rint(valued*livredePaie.getBrutImposable()/livredePaie.getNetPayer());
							nouvDiff=nouvMontantBrutImp-livredePaie.getBrutImposable();
							nouvSursal=nouvDiff+livredePaie.getSursalaire();
							livredePaie= new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), nbpart ,op, ctratpersonnel.getCategorie().getSalaireDeBase(),5000d, ctratpersonnel.getIndemniteLogement(),0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
							livredePaie = new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), ctratpersonnel.getPersonnel().getNombrePart(), op, ctratpersonnel.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnel.getIndemniteLogement(), 0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
						//	System.out.println("*********************SECOND BULLETIN*"+i);
							System.out.println("*SECOND BULLETIN#############-----------"+livredePaie.toString());
					// i++;
						}
				
				 
				} catch (Exception e) {
					System.out.println("FINISH"+ e.getMessage());
				} 
		 
		 
		 return livredePaie;
	}
	


















	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savebullOnePersonnel", method = RequestMethod.POST)
	public @ResponseBody BulletinPaieDTO saveBullOnepersonnel(@RequestParam(value="id", required=true) Long id,@RequestParam(value="idPersonnel", required=true) Long idPersonnel,
															Principal principal) {
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		/*//Utilisateur currentUser = userService.findByEmail(principal.getName());
		  List<BulletinPaie> bulletinList = new ArrayList<BulletinPaie>();
		 bulletinList=bulletinPaieService.genererMois1Personnel(id,idPersonnel);
		 bulletinPaieDTO.setRows(bulletinList);
		 bulletinPaieDTO.setTotal(bulletinList.size());
		 bulletinPaieDTO.setResult("success");*/
		 return bulletinPaieDTO;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savelivrepaie", method = RequestMethod.GET)
	public @ResponseBody BulletinPaieDTO enregistrerLivredePaie(@RequestParam(value="limit", required=false) Integer limit,
                                                                @RequestParam(value="offset", required=false) Integer offset) {
        if(offset == null) offset = 0;
        if(limit == null) limit = 10;

        //final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		return bulletinPaieService.generateLivreDePaie(page);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listbulletinMoisActif", method = RequestMethod.GET)
	public @ResponseBody BulletinPaieDTO listbulletinMoisActif(@RequestParam(value="limit", required=false) Integer limit, 
			@RequestParam(value="offset", required=false) Integer offset, 
			@RequestParam(value="search", required=false) String search) { 
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "id");
		
		
		return bulletinPaieService.BulletinMoisCalculer(page, maperiode);
		
		
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/personneldePeriodactif", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO personneldePeriodactif( Principal principal) {
		
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		
		  List<Personnel> personnelList = new ArrayList<Personnel>();
		  List<ContratPersonnel> personnelListTrt = new ArrayList<ContratPersonnel>();
	     PeriodePaie	periode=periodePaieService.findPeriodeactive();
		if(periode!=null)
		personnelList=personnelRepository.findAll();
		  for(int i = 0; i < personnelList.size(); i++){
			  ContratPersonnel ctratpersonnel = new ContratPersonnel();
		  ctratpersonnel=contratPersonnelRepository.findByPersonnelId(personnelList.get(i).getId());
		  	if(ctratpersonnel!=null){
		  		if(ctratpersonnel.getStatut()==true)
		         personnelListTrt.add(ctratpersonnel);
		  	}
		  }
		  contratPersonnelDTO.setRows(personnelListTrt);
		  contratPersonnelDTO.setResult("success");
		return contratPersonnelDTO;
	}

	@RequestMapping(value = "/jrBulletinPersonnels", method = RequestMethod.GET)
	public ResponseEntity<byte[]> generatePayrollPDF(@RequestParam("idbul") Long idCV) {
		try {
			// 1. Récupérer le bulletin de paie depuis la base de données
			BulletinPaie bulletin = bulletinPaieRepository.findById(idCV)
					.orElseThrow(() -> new EntityNotFoundException("Bulletin not found for id " + idCV));

			// 2. Charger le fichier de rapport Jasper
			InputStream reportStream = getClass().getResourceAsStream("/reports/bulletinPaie.jasper");
			if (reportStream == null) {
				throw new FileNotFoundException("Fichier bulletinPaie.jasper non trouvé.");
			}

			// 3. Préparer les paramètres à envoyer au rapport
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("idBulletin", idCV);
			parameters.put("nomEmploye", bulletin.getContratPersonnel().getPersonnel().getNom());
			parameters.put("salaireBase", bulletin.getSalairbase());
			parameters.put("totalBrut", bulletin.getBrutImposable());

			// 4. Source de données pour JasperReports
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(bulletin));

			// 5. Générer le rapport Jasper
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource);

			// 6. Exporter en PDF
			byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

			// 7. Configurer la réponse HTTP
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "Bulletin_" + idCV + ".pdf");

			return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (FileNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Génère un fichier PDF du bulletin de paie en utilisant JasperReports.
	 */
//	public byte[] generatePayslipPdf(BulletinPaie bulletinData) throws Exception {
//		// Vérification et compilation du rapport principal
//		File mainReportFile = new File("/reports/JRbulletin.jrxml");
//		if (!mainReportFile.exists()) {
//			throw new FileNotFoundException("Le fichier JasperReport principal est introuvable !");
//		}
//
//		// Vérification et compilation du sous-rapport
//		File subReportFile = new File("/reports/JRbulletn_subreportDetailBull.jrxml");
//		if (!subReportFile.exists()) {
//			throw new FileNotFoundException("Le fichier JasperReport du sous-rapport est introuvable !");
//		}
//
//		// Compilation des fichiers .jrxml en .jasper
//		String mainReportPath = mainReportFile.getAbsolutePath().replace(".jrxml", ".jasper");
//		String subReportPath = subReportFile.getAbsolutePath().replace(".jrxml", ".jasper");
//
//		JasperCompileManager.compileReportToFile(subReportFile.getAbsolutePath(), subReportPath);
//		JasperCompileManager.compileReportToFile(mainReportFile.getAbsolutePath(), mainReportPath);
//
//		// Compilation du rapport principal
//		try (InputStream reportStream = new FileInputStream(mainReportPath)) {
//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
//
//			// Paramètres du rapport
//			Map<String, Object> parameters = new HashMap<>();
//			parameters.put("SUBREPORT_DIR", subReportFile.getParent() + "/"); // Passer le dossier du sous-rapport
//			parameters.put("logo", "static/logo/logodefis1.png"); // Passer le dossier du sous-rapport
//
//			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(bulletinData));
//
//			// Remplissage du rapport Jasper avec les données
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//			// Génération du PDF
//			return JasperExportManager.exportReportToPdf(jasperPrint);
//		} catch (JRException | IOException e) {
//			throw new RuntimeException("Erreur lors de la génération du rapport Jasper: " + e.getMessage(), e);
//		}
//	}

//	public byte[] generatePayslipPdf(BulletinPaie bulletinData) throws Exception {
//	System.out.println("Début de la génération du PDF du bulletin de paie");
//
//
//		String reportsPath;
//		if (new File("src/main/resources/reports").exists()) {
//			reportsPath = "src/main/resources/reports/";
//		} else {
//			reportsPath = "/webapps/hyban/reports/";
//		}
//		// Vérification et compilation du rapport principal
//		File mainReportFile = new File(reportsPath + "JRbulletin.jrxml");
//		File subReportFile = new File(reportsPath + "JRbulletn_subreportDetailBull.jrxml");
//
//		if (!mainReportFile.exists() || !subReportFile.exists()) {
//			throw new FileNotFoundException("Fichiers de rapport introuvables.");
//		}
//		if (!mainReportFile.exists()) {
//			logger.error("Le fichier JasperReport principal est introuvable : {}" ,  mainReportFile.getAbsolutePath());
//			throw new FileNotFoundException("Le fichier JasperReport principal est introuvable !");
//		}
//		logger.info("Fichier principal trouvé : {}", mainReportFile.getAbsolutePath());
//
//		// Vérification et compilation du sous-rapport
//		//File subReportFile = new File("src/main/resources/reports/JRbulletn_subreportDetailBull.jrxml");
//		if (!subReportFile.exists()) {
//			logger.error("Le fichier JasperReport du sous-rapport est introuvable : {} ", subReportFile.getAbsolutePath());
//			throw new FileNotFoundException("Le fichier JasperReport du sous-rapport est introuvable !");
//		}
//		logger.info("Fichier sous-rapport trouvé : {} " ,subReportFile.getAbsolutePath());
//
//		// Compilation des fichiers .jrxml en .jasper
//		String mainReportPath = mainReportFile.getAbsolutePath().replace(".jrxml", ".jasper");
//		String subReportPath = subReportFile.getAbsolutePath().replace(".jrxml", ".jasper");
//
//		logger.info("Compilation du sous-rapport : {} ",  subReportPath);
//		JasperCompileManager.compileReportToFile(subReportFile.getAbsolutePath(), subReportPath);
//
//		logger.info("Compilation du rapport principal : {}" , mainReportPath);
//		JasperCompileManager.compileReportToFile(mainReportFile.getAbsolutePath(), mainReportPath);
//
//		// Compilation du rapport principal
//		try (InputStream reportStream = new FileInputStream(mainReportPath)) {
//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
//			logger.info("Rapport principal chargé avec succès");
//
//			// Paramètres du rapport
//			Map<String, Object> parameters = new HashMap<>();
//			parameters.put("SUBREPORT_DIR", subReportFile.getParent() + "/");
//
//			parameters.put("logo", "/webapps/hyban/static/logo/logodefis1.png");
//
//			logger.info("Paramètres du rapport définis : {}", parameters);
//
//			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(bulletinData));
//			logger.info("Source de données créée avec succès");
//
//			// Remplissage du rapport Jasper avec les données
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//			logger.info("Rapport rempli avec succès");
//
//			// Génération du PDF
//			byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
//			logger.info("PDF généré avec succès (taille : {} octets) ", pdfData.length);
//
//			return pdfData;
//		} catch (JRException | IOException e) {
//			System.out.println("Erreur lors de la génération du rapport Jasper"+ e);
//			throw new RuntimeException("Erreur lors de la génération du rapport Jasper: " + e.getMessage(), e);
//		}
//	}

	public byte[] generatePayslipPdf(BulletinPaie bulletinData,HttpServletRequest request) throws Exception {
		logger.info("Début de la génération du PDF du bulletin de paie");

		// Détection de l'environnement (local vs Linux)
		String reportsPath;
		if (Files.exists(Paths.get("src/main/resources/reports"))) {
			// Mode développement : accès direct au répertoire des ressources
			reportsPath = "src/main/resources/reports";
		} else {
			reportsPath = request.getSession().getServletContext().getRealPath( "/reports");
		}

		Path reportsDir = Paths.get(reportsPath).toAbsolutePath();
		logger.info("Chemin des rapports utilisé : {}", reportsDir);

		// Détection des fichiers
		Path mainReportFile = reportsDir.resolve("JRbulletin.jrxml");
		Path subReportFile = reportsDir.resolve("JRbulletn_subreportDetailBull.jrxml");

		if (!Files.exists(mainReportFile) || !Files.exists(subReportFile)) {
			throw new FileNotFoundException("Fichiers de rapport introuvables : " + mainReportFile + ", " + subReportFile);
		}
		logger.info("Fichier principal trouvé : {}", mainReportFile);
		logger.info("Fichier sous-rapport trouvé : {}", subReportFile);

		// Compilation
		Path mainReportPath = Paths.get(mainReportFile.toString().replace(".jrxml", ".jasper"));
		Path subReportPath = Paths.get(subReportFile.toString().replace(".jrxml", ".jasper"));

		logger.info("Compilation du sous-rapport : {}", subReportPath);
		JasperCompileManager.compileReportToFile(subReportFile.toString(), subReportPath.toString());

		logger.info("Compilation du rapport principal : {}", mainReportPath);
		JasperCompileManager.compileReportToFile(mainReportFile.toString(), mainReportPath.toString());

		try (InputStream reportStream = Files.newInputStream(mainReportPath)) {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
			logger.info("Rapport principal chargé avec succès");

			// Paramètres du rapport
			//String reportsPathlogo = request.getSession().getServletContext().getRealPath( "/static/logo/");
			List<Societe> malist=societeService.findtsmois();
			String cheminComplet = malist.get(0).getUrlLogo();
			String logoRep;
			Path logoPath = null;
			String cheminRelatif;

			if (Files.exists(Paths.get("src/main/resources/static"))) {
				// Mode développement : accès direct au répertoire des ressources
				logoRep = "src/main/resources/";
				cheminRelatif = cheminComplet.startsWith("/") ? cheminComplet.substring(1) : cheminComplet;
				logger.info("Chemin relatif du logo : {}", cheminComplet);
				logoPath = Paths.get(logoRep, cheminRelatif).toAbsolutePath();

			} else {
				logoRep = request.getSession().getServletContext().getRealPath( "/static");
				cheminRelatif = cheminComplet.startsWith("/") ? cheminComplet.substring(6) : cheminComplet;
				logger.info("Chemin relatif du logo : {}", cheminComplet);
				logoPath = Paths.get(request.getSession().getServletContext().getRealPath(cheminRelatif)).toAbsolutePath();
			}

			if (!Files.exists(logoPath)) {
				throw new FileNotFoundException("Le logo est introuvable : " + logoPath);
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("SUBREPORT_DIR", subReportFile.getParent() + "/");
			parameters.put("logo", logoPath.toString());

			logger.info("Paramètres du rapport définis : {}", parameters);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(bulletinData));
			logger.info("Source de données créée avec succès");

			// Génération du rapport
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			logger.info("Rapport rempli avec succès");

			// Génération du PDF
			byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("PDF généré avec succès (taille : {} octets)", pdfData.length);

			return pdfData;
		} catch (JRException | IOException e) {
			logger.error("Erreur lors de la génération du rapport Jasper", e);
			throw new RuntimeException("Erreur lors de la génération du rapport Jasper: " + e.getMessage(), e);
		}
	}

	public byte[] generateMoisBulllipPdf(List<BulletinPaie> bulletinData,HttpServletRequest request) throws Exception {
		logger.info("Début de la génération du PDF du bulletin de paie");
		logger.info("nombre de rapports utilisé : {}", bulletinData.size());
		// Détection de l'environnement (local vs Linux)
		String reportsPath;
		if (Files.exists(Paths.get("src/main/resources/reports"))) {
			// Mode développement : accès direct au répertoire des ressources
			reportsPath = "src/main/resources/reports";
		} else {
			reportsPath = request.getSession().getServletContext().getRealPath( "/reports");
		}

		Path reportsDir = Paths.get(reportsPath).toAbsolutePath();
		logger.info("Chemin des rapports utilisé : {}", reportsDir);

		// Détection des fichiers
		Path mainReportFile = reportsDir.resolve("JRbulletin.jrxml");
		Path subReportFile = reportsDir.resolve("JRbulletn_subreportDetailBull.jrxml");

		if (!Files.exists(mainReportFile) || !Files.exists(subReportFile)) {
			throw new FileNotFoundException("Fichiers de rapport introuvables : " + mainReportFile + ", " + subReportFile);
		}
		logger.info("Fichier principal trouvé : {}", mainReportFile);
		logger.info("Fichier sous-rapport trouvé : {}", subReportFile);

		// Compilation
		Path mainReportPath = Paths.get(mainReportFile.toString().replace(".jrxml", ".jasper"));
		Path subReportPath = Paths.get(subReportFile.toString().replace(".jrxml", ".jasper"));

		logger.info("Compilation du sous-rapport : {}", subReportPath);
		JasperCompileManager.compileReportToFile(subReportFile.toString(), subReportPath.toString());

		logger.info("Compilation du rapport principal : {}", mainReportPath);
		JasperCompileManager.compileReportToFile(mainReportFile.toString(), mainReportPath.toString());

		try (InputStream reportStream = Files.newInputStream(mainReportPath)) {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
			logger.info("Rapport principal chargé avec succès");

			// Paramètres du rapport
			//String reportsPathlogo = request.getSession().getServletContext().getRealPath( "/static/logo/");
			List<Societe> malist=societeService.findtsmois();
			String cheminComplet = malist.get(0).getUrlLogo();
			String logoRep;
			Path logoPath = null;
			String cheminRelatif;

			if (Files.exists(Paths.get("src/main/resources/static"))) {
				// Mode développement : accès direct au répertoire des ressources
				logoRep = "src/main/resources/";
				cheminRelatif = cheminComplet.startsWith("/") ? cheminComplet.substring(1) : cheminComplet;
				logger.info("Chemin relatif du logo : {}", cheminComplet);
				logoPath = Paths.get(logoRep, cheminRelatif).toAbsolutePath();

			} else {
				logoRep = request.getSession().getServletContext().getRealPath( "/static");
				cheminRelatif = cheminComplet.startsWith("hyban/") ? cheminComplet.substring(6) : cheminComplet;
				logger.info("Chemin relatif du logo : {}", cheminComplet);
				logoPath = Paths.get(request.getSession().getServletContext().getRealPath(cheminRelatif)).toAbsolutePath();
			}

			if (!Files.exists(logoPath)) {
				throw new FileNotFoundException("Le logo est introuvable : " + logoPath);
			}

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("SUBREPORT_DIR", subReportFile.getParent() + "/");
			parameters.put("logo", logoPath.toString());

			logger.info("Paramètres du rapport définis : {}", parameters);
			//List<BulletinPaie> singleBulletinList = Collections.singletonList(bulletinData); // Récupérer un seul bulletin
			//JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singleBulletinList);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bulletinData);
			logger.info("Source de données créée avec succès");

			// Génération du rapport
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			logger.info("Rapport rempli avec succès");

			// Génération du PDF
			byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("PDF généré avec succès (taille : {} octets)", pdfData.length);

			return pdfData;
		} catch (JRException | IOException e) {
			logger.error("Erreur lors de la génération du rapport Jasper", e);
			throw new RuntimeException("Erreur lors de la génération du rapport Jasper: " + e.getMessage(), e);
		}
	}

	@GetMapping("/export")
	public ResponseEntity<byte[]> exportBulletinsJulaya(HttpServletResponse response) throws IOException {

		// Chargement du fichier modèle
		ClassPathResource templateFile = new ClassPathResource("templates/FICHIERTYPEJULAYA.xlsm");
		PeriodePaie maperiode = periodePaieService.findPeriodeactive();

		try (InputStream inputStream = templateFile.getInputStream();
			 Workbook workbook = WorkbookFactory.create(inputStream);
			 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			Sheet sheet = workbook.getSheetAt(0); // première feuille

			List<BulletinPaie> bulletins = bulletinPaieRepository.findByPeriodePaieIdAndCalculerTrue(maperiode.getId());
			int rowNum = 1; // ligne 2

			for (BulletinPaie bulletin : bulletins) {
				ContratPersonnel contrat = bulletin.getContratPersonnel();
				Personnel personnel = contrat.getPersonnel();
				String modePaiement = personnel.getModePaiement(); // Assure-toi que ce champ existe

				String reference;

				if ("virement-bancaire".equalsIgnoreCase(modePaiement)) {
					reference = (personnel.getNumeroCompte() != null ? personnel.getNumeroCompte() : "");
				} else {
					reference = (personnel.getTelephone() != null ? personnel.getTelephone() : "");
				}

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(reference);
				row.createCell(1).setCellValue(bulletin.getNetapayer().doubleValue());
				row.createCell(2).setCellValue(personnel.getNom() + " " + personnel.getPrenom());
				row.createCell(3).setCellValue("Salaire " + bulletin.getPeriodePaie().getMois().getMois() + " " + bulletin.getPeriodePaie().getAnnee().getAnnee());
				row.createCell(4).setCellValue(modePaiement); // Ex : "virement-bancaire", "transfert-wave"
			}


			// Génération du nom de fichier
			String fileName = "julaya_export_" +
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsm";

			// Enregistrement local
			Path exportDir = Paths.get("exports");
			Files.createDirectories(exportDir);
			Path filePath = exportDir.resolve(fileName);
			try (OutputStream fileOut = Files.newOutputStream(filePath)) {
				workbook.write(fileOut);
			}

			// Écriture dans le flux pour téléchargement
			workbook.write(out);
			byte[] fileContent = out.toByteArray();

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel.sheet.macroEnabled.12"))
					.contentLength(fileContent.length)
					.body(fileContent);
		}
	}


	// Méthode utilitaire pour créer un objet ImprimBulletinPaie et l'ajouter à la liste
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

	@GetMapping("/generate-payslip")
	public ResponseEntity<byte[]> generatePayslip(@RequestParam("idbul") Long employeeId,HttpServletRequest request) {
		try {
			BulletinPaie bulletin = new BulletinPaie();

			try {
				bulletin = bulletinPaieRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + employeeId));;
			} catch (Exception e) {
				e.printStackTrace();
			}

			BulletinPaie bulletinData = getPayslipData(employeeId,bulletin);
			byte[] pdfBytes = generatePayslipPdf(bulletinData,request);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "Bulletin_Paie.pdf");

			return ResponseEntity.ok().headers(headers).body(pdfBytes);
		} catch (Exception e) {
			e.printStackTrace(); // Remplacer par un logger
			return ResponseEntity.internalServerError().build();
		}
	}
	public BulletinPaie getPayslipData(Long employeeId,BulletinPaie bulletin){
		List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		double taux = bulletin.getJourTravail();

		listImprimBulletinPaie.add(ajouterImprimBulletinPaie( "SALAIRE DE BASE CATEGORIE", taux, bulletin.getSalairbase(), bulletin.getSalairbase(),0D,null,null));
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
		int tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge, bulletin.getPeriodePaie().getDatefin());
		bulletin.setNbcongedu(String.valueOf(bulletin.getTempsOfpresence()));
		bulletin.setTpsdepresence(String.valueOf(bulletin.getMoisOfpresence()));




		return bulletin;
	}



	@RequestMapping(value = "/jrBulletinPersonnel", method = RequestMethod.GET)
	public ResponseEntity<byte[]>  ImprimerBulletinPersonnelCV(ModelMap modelMap,@RequestParam(value="idbul", required=true) Long idCV, HttpServletRequest request) throws Exception {
			
		String view="livrepaie";
		List<BulletinPaie> listImprimebulletin=  new ArrayList<BulletinPaie>();
		BulletinPaie bulletin = new BulletinPaie();
		
		try {
				bulletin = bulletinPaieRepository.findById(idCV).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idCV));;

				List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
				List<ImprimBulletinPaie> listImprimBulletinPaieEngt = new ArrayList<ImprimBulletinPaie>();
				List<ImprimBulletinPaie> listImprimBulletinPaieIndem = new ArrayList<ImprimBulletinPaie>();
	
		if(bulletin.getId() != null) {

			view = "bulletinpdf";
			ImprimBulletinPaie imprimBulletinPaieSB = new ImprimBulletinPaie();
			imprimBulletinPaieSB.setLibelle("SALAIRE DE BASE CATEGORIE");
			imprimBulletinPaieSB.setTaux(bulletin.getJourTravail());
			//calcul de la base
			//	if(bulletin.getJourTravail() == 30){
			imprimBulletinPaieSB.setBase(bulletin.getSalairbase());
			imprimBulletinPaieSB.setGain(bulletin.getSalairbase());
			listImprimBulletinPaie.add(imprimBulletinPaieSB);

			ImprimBulletinPaie imprimBulletinPaieSs = new ImprimBulletinPaie();
			imprimBulletinPaieSs.setLibelle("SURSALAIRE");
			imprimBulletinPaieSs.setTaux(bulletin.getJourTravail());
			imprimBulletinPaieSs.setBase(bulletin.getSursalaire());
			imprimBulletinPaieSs.setGain(bulletin.getSursalaire());
			listImprimBulletinPaie.add(imprimBulletinPaieSs);


			ImprimBulletinPaie imprimBulletinPaieanc = new ImprimBulletinPaie();
			imprimBulletinPaieanc.setLibelle("PRIME D'ANCIENNETE");
			imprimBulletinPaieanc.setBase(bulletin.getPrimeanciennete());
			imprimBulletinPaieanc.setGain(bulletin.getPrimeanciennete());
			listImprimBulletinPaie.add(imprimBulletinPaieanc);

			ImprimBulletinPaie imprimBulletinPaiePanc = new ImprimBulletinPaie();
			imprimBulletinPaiePanc.setLibelle("INDEMNITE DE RESIDENCE");
			imprimBulletinPaiePanc.setTaux(bulletin.getJourTravail());

			imprimBulletinPaiePanc.setBase(bulletin.getIndemnitelogement());
			imprimBulletinPaiePanc.setGain(bulletin.getIndemnitelogement());
			listImprimBulletinPaie.add(imprimBulletinPaiePanc);

			ImprimBulletinPaie imprimBulletinPaieLog = new ImprimBulletinPaie();
			imprimBulletinPaieLog.setLibelle("IND. DE TRANSPORT IMP ");
			imprimBulletinPaieLog.setTaux(bulletin.getJourTravail());
			//calcul de la base
			//	if(bulletin.getJourTravail() == 30){
			imprimBulletinPaieLog.setBase(bulletin.getIndemniteTransportImp());
			imprimBulletinPaieLog.setGain(bulletin.getIndemniteTransportImp());
			listImprimBulletinPaie.add(imprimBulletinPaieLog);

			ImprimBulletinPaie imprimBulletinPaieDivImp1 = new ImprimBulletinPaie();
			imprimBulletinPaieDivImp1.setLibelle("PRIMES IMPOSABLES ");
			imprimBulletinPaieDivImp1.setTaux(bulletin.getJourTravail());
			//calcul de la base
			//	if(bulletin.getJourTravail() == 30){
			imprimBulletinPaieDivImp1.setBase(bulletin.getAutreIndemImposable());
			imprimBulletinPaieDivImp1.setGain(bulletin.getAutreIndemImposable());
			listImprimBulletinPaie.add(imprimBulletinPaieDivImp1);

			List<Rubrique> listPrimeImp = new ArrayList<Rubrique>();
			listPrimeImp = rubriqueService.getRubriquesActivesType(1);
			for (Rubrique rubrique : listPrimeImp) {
				List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
				listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
				if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
					ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
					imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
					if (listprimepersonnel.get(0).getPrime().getTaux() != null)
						imprimBulletinPaieDivImp.setTaux(listprimepersonnel.get(0).getValeur().doubleValue());
					else
						imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
					imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
					imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
					listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
				}
			}
			List<Rubrique> listPrimeImp1 = new ArrayList<Rubrique>();
			listPrimeImp1 = rubriqueService.getRubriquesActivesType(3);
			for (Rubrique rubrique : listPrimeImp1) {
				List<PrimePersonnel> listprimepersonnel1 = new ArrayList<PrimePersonnel>();
				listprimepersonnel1 = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
				if (listprimepersonnel1.size() > 0 && listprimepersonnel1.get(0) != null) {
					ImprimBulletinPaie imprimBulletinPaieDivImp10 = new ImprimBulletinPaie();
					imprimBulletinPaieDivImp10.setLibelle(listprimepersonnel1.get(0).getPrime().getLibelle());
					imprimBulletinPaieDivImp10.setTaux(bulletin.getJourTravail());
					imprimBulletinPaieDivImp10.setBase(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
					imprimBulletinPaieDivImp10.setGain(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
					listImprimBulletinPaie.add(imprimBulletinPaieDivImp10);
				}
			}

			ImprimBulletinPaie imprimBulletinPaieBrutImpos = new ImprimBulletinPaie();
			imprimBulletinPaieBrutImpos.setLibelle("***** Salaire Brut Imposable ******");
			imprimBulletinPaieBrutImpos.setGain(bulletin.getBrutImposable());
			listImprimBulletinPaie.add(imprimBulletinPaieBrutImpos);

			ImprimBulletinPaie imprimBulletinPaieTransport = new ImprimBulletinPaie();
			imprimBulletinPaieTransport.setLibelle("IND. DE TRANSPORT NON IMPOS");
			//imprimBulletinPaieTransport.setTaux(bulletin.getJourTravail());
			imprimBulletinPaieTransport.setGain(bulletin.getIndemniteTransport());
			listImprimBulletinPaie.add(imprimBulletinPaieTransport);

//			ImprimBulletinPaie imprimBulletinPaieRESP= new ImprimBulletinPaie();
//			imprimBulletinPaieRESP.setLibelle("AUTRES NON IMPOSABLES ");
//			//imprimBulletinPaieRESP.setTaux(bulletin.getJourTravail());
//			imprimBulletinPaieRESP.setGain(bulletin.getAutreNonImposable());
//			listImprimBulletinPaie.add(imprimBulletinPaieRESP);

			List<Rubrique> listPrimeImpnon = new ArrayList<Rubrique>();
			listPrimeImpnon = rubriqueService.getRubriquesActivesType(2);
			for (Rubrique rubrique : listPrimeImpnon) {
				List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
				listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
				if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
					ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
					imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
					imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
					imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
					imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
					listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
				}
			}
			List<Rubrique> listPrimeImp12 = new ArrayList<Rubrique>();
			listPrimeImp12 = rubriqueService.getRubriquesActivesType(3);
			for (Rubrique rubrique : listPrimeImp12) {
				List<PrimePersonnel> listprimepersonnel1 = new ArrayList<PrimePersonnel>();
				listprimepersonnel1 = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
				if (listprimepersonnel1.size() > 0 && listprimepersonnel1.get(0) != null) {
					ImprimBulletinPaie imprimBulletinPaieDivImp10 = new ImprimBulletinPaie();
					imprimBulletinPaieDivImp10.setLibelle(listprimepersonnel1.get(0).getPrime().getLibelle());
					imprimBulletinPaieDivImp10.setTaux(bulletin.getJourTravail());
					imprimBulletinPaieDivImp10.setBase(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
					imprimBulletinPaieDivImp10.setGain(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
					listImprimBulletinPaie.add(imprimBulletinPaieDivImp10);
				}
			}

			ImprimBulletinPaie imprimBulletinPaieSalBrutNon = new ImprimBulletinPaie();
			imprimBulletinPaieSalBrutNon.setLibelle("***** Salaire Brut Non Imposable ******");
			imprimBulletinPaieSalBrutNon.setTaux(bulletin.getJourTravail());
			imprimBulletinPaieSalBrutNon.setGain(bulletin.getBrutNonImposable());
			listImprimBulletinPaie.add(imprimBulletinPaieSalBrutNon);

			List<Rubrique> listPrimeG = new ArrayList<Rubrique>();
			listPrimeG = rubriqueService.getRubriquesActivesType(5);
			for (Rubrique rubrique : listPrimeG) {
				List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
				listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
				if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
					ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
					imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
					//imprimBulletinPaieDivImp.setTaux(null);
					imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
					imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
					listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
				}
			}


			ImprimBulletinPaie imprimBulletinPaieIts = new ImprimBulletinPaie();
			imprimBulletinPaieIts.setLibelle("I.T.S (nouveau Barême)");
			imprimBulletinPaieIts.setTaux(null);
			imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieIts.setRetenue(bulletin.getIts());
			listImprimBulletinPaie.add(imprimBulletinPaieIts);

			//ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
			////imprimBulletinPaieIgr.setLibelle("I.G.R");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			//imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
			//////listImprimBulletinPaie.add(imprimBulletinPaieIgr);

//			ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
//			imprimBulletinPaieCn.setLibelle("CONTRIBUTION NATIONALE");
//			//imprimBulletinPaieCn.setTaux(1.2D);
//			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//			imprimBulletinPaieCn.setRetenue(bulletin.getCn());
//			listImprimBulletinPaie.add(imprimBulletinPaieCn);


			ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
			imprimBulletinPaieCnps.setLibelle("RETRAITE CNPS ");
			imprimBulletinPaieCnps.setTaux(6.30);
			imprimBulletinPaieCnps.setBase(bulletin.getBasecnps());
			imprimBulletinPaieCnps.setRetenue(bulletin.getCnps());
			listImprimBulletinPaie.add(imprimBulletinPaieCnps);

			ImprimBulletinPaie imprimBulletinPaieIS = new ImprimBulletinPaie();
			imprimBulletinPaieIS.setLibelle("IMPOTS/SALAIRE LOCAL");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieIS.setRetenuePatron(bulletin.getIts());
			listImprimBulletinPaie.add(imprimBulletinPaieIS);

			ImprimBulletinPaie imprimBulletinPaieCnpsPATRON = new ImprimBulletinPaie();
			imprimBulletinPaieCnpsPATRON.setLibelle("RETRAITE CNPS/PART PATRONAL ");
		//SSSS	imprimBulletinPaieCnpsPATRON.setTauxPatron(7.70);
			imprimBulletinPaieCnpsPATRON.setBase(bulletin.getBasecnps());
			imprimBulletinPaieCnpsPATRON.setRetenuePatron(bulletin.getRetraite());
			listImprimBulletinPaie.add(imprimBulletinPaieCnpsPATRON);


			ImprimBulletinPaie imprimBulletinPaiePF = new ImprimBulletinPaie();
			imprimBulletinPaiePF.setLibelle("PRESTATION FAMILIALE");
			//imprimBulletinPaiePF.setTauxPatron(5.75);
			imprimBulletinPaiePF.setBase(70000d);
			imprimBulletinPaiePF.setRetenuePatron(bulletin.getPrestationFamiliale());
			listImprimBulletinPaie.add(imprimBulletinPaiePF);

			ImprimBulletinPaie imprimBulletinPaieAC = new ImprimBulletinPaie();
			imprimBulletinPaieAC.setLibelle("ACCIDENT DE TRAVAIL");
			imprimBulletinPaieAC.setTauxPatron(2.0);
			imprimBulletinPaieAC.setBase(70000d);
			imprimBulletinPaieAC.setRetenuePatron(bulletin.getAccidentTravail());
			listImprimBulletinPaie.add(imprimBulletinPaieAC);

			ImprimBulletinPaie imprimBulletinPaieTA = new ImprimBulletinPaie();
			imprimBulletinPaieTA.setLibelle("FDFP -TAXE APPRENTISAGE ");
			imprimBulletinPaieTA.setTauxPatron(0.40d);
			imprimBulletinPaieTA.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieTA.setRetenuePatron(bulletin.getTa());
			listImprimBulletinPaie.add(imprimBulletinPaieTA);

			ImprimBulletinPaie imprimBulletinPaieFDFP = new ImprimBulletinPaie();
			imprimBulletinPaieFDFP.setLibelle("FDFP -TAXE A LA FPC ");
			imprimBulletinPaieFDFP.setTauxPatron(0.6D);
			imprimBulletinPaieFDFP.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieFDFP.setRetenuePatron(bulletin.getFpc());
			listImprimBulletinPaie.add(imprimBulletinPaieFDFP);

			ImprimBulletinPaie imprimBulletinPaieFDFPRegul = new ImprimBulletinPaie();
			imprimBulletinPaieFDFPRegul.setLibelle("FDFP -TAXE A LA FPC  REGUL");
			imprimBulletinPaieFDFPRegul.setTauxPatron(0.6D);
			imprimBulletinPaieFDFPRegul.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieFDFPRegul.setRetenuePatron(bulletin.getFpcregul());
			listImprimBulletinPaie.add(imprimBulletinPaieFDFPRegul);


			ImprimBulletinPaie imprimBulletinPaieAMAO = new ImprimBulletinPaie();
			imprimBulletinPaieAMAO.setLibelle("AVANCES/ACOMPTES ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieAMAO.setRetenue(bulletin.getAvanceetacompte());
			listImprimBulletinPaie.add(imprimBulletinPaieAMAO);

			ImprimBulletinPaie imprimBulletinPaieAMAO1 = new ImprimBulletinPaie();
			imprimBulletinPaieAMAO1.setLibelle("AUTRES PRETS ");
			imprimBulletinPaieAMAO1.setRetenue(bulletin.getPretaloes());
			listImprimBulletinPaie.add(imprimBulletinPaieAMAO1);
			List<Rubrique> listPrimeM = new ArrayList<Rubrique>();
			listPrimeM = rubriqueService.getRubriquesActivesType(4);
			for (Rubrique rubrique : listPrimeM) {
				List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
				listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
				if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
					ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
					imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
					//imprimBulletinPaieDivImp.setTaux(null);
					//	imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
					imprimBulletinPaieDivImp.setRetenue(listprimepersonnel.get(0).getMontant());
					listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
				}
			}


			bulletin.setListImprimBulletinPaie(listImprimBulletinPaie);


			Date dateRetourDernierConge = null;
			if (bulletin.getContratPersonnel().getPersonnel().getDateRetourcge() == null) {
				logger.info("**********************************************g suiiiiiiiiiiiiii");
				dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateArrivee();
			} else {
				dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateRetourcge();
			}
			int tps = ProvisionConge.calculerTempsPresence(dateRetourDernierConge, bulletin.getPeriodePaie().getDatefin());
			bulletin.setNbcongedu(String.valueOf(bulletin.getTempsOfpresence()));
			bulletin.setTpsdepresence(String.valueOf(bulletin.getMoisOfpresence()));
			JRDataSource jrDatasource = null;

			listImprimebulletin.add(bulletin);
			//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
			//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
			List<Societe> malist = societeService.findtsmois();

			modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath(malist.get(0).getUrlLogo()));
			modelMap.addAttribute("mtcumulSalnet", Utils.formattingAmount(bulletin.getCumulSalaireNet()));


			//BulletinPaie bulletinData = getPayslipData(bulletin.getId(),bulletin);
			byte[] pdfBytes = generatePayslipPdf(bulletin,request);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("filename", "Bulletin_Paie.pdf");

			return ResponseEntity.ok().headers(headers).body(pdfBytes);
		}
	} catch (Exception e) {
		e.printStackTrace(); // Remplacer par un logger
		return ResponseEntity.internalServerError().build();
	}

	//	System.out.println("Vue retournée : " + view);
	//return view; //mav;


        return null;
    }
	
	
	
	@RequestMapping(value = "/bulletinMoisPersonnel", method = RequestMethod.GET)
	public ResponseEntity<byte[]> postAfficherBulletin(ModelMap modelMap,@RequestParam(value="idbul", required=true) Long idCV, HttpServletRequest request) throws Exception {
		
		
			
		String view="livrepaie";
		
		
	
			PeriodePaie  periodePaie = periodePaieService.findPeriodePaie(idCV);
			System.out.println("########## periode : "+periodePaie.getMois().getMois());
			
		List<BulletinPaie> listImprimebulletin=  new ArrayList<BulletinPaie>();
		
		List<BulletinPaie> listBull = new ArrayList<BulletinPaie>();
		//listBull = bulletinService.findAllBulletinByPeriodePaie(periodePaie);
		listBull = bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie, true);
		System.out.println("nb bulletin : "+listBull.size());
		logger.info("nb bulletin : "+listBull.size());
		for (int r = 0; r<listBull.size(); r++){
		
			BulletinPaie bulletin = new BulletinPaie();
			
			try {
				int finalR = r;
				bulletin = bulletinPaieRepository.findById(listBull.get(r).getId())  .orElseThrow(() -> new EntityNotFoundException("Bulletin not found for id " ));
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
			List<ImprimBulletinPaie> listImprimBulletinPaieEngt = new ArrayList<ImprimBulletinPaie>();
			List<ImprimBulletinPaie> listImprimBulletinPaieIndem = new ArrayList<ImprimBulletinPaie>();
			DecimalFormat formatter = new DecimalFormat("#,###");
			formatter.setGroupingSize(3);
			formatter.setGroupingUsed(true);
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(' ');
			formatter.setDecimalFormatSymbols(symbols);
			if(bulletin.getId() != null){
				ImprimBulletinPaie imprimBulletinPaieSB = new ImprimBulletinPaie();
				imprimBulletinPaieSB.setLibelle("SALAIRE DE BASE CATEGORIE");
				imprimBulletinPaieSB.setTaux(bulletin.getJourTravail());
				//calcul de la base
				//	if(bulletin.getJourTravail() == 30){
				imprimBulletinPaieSB.setBase(bulletin.getSalairbase());
				imprimBulletinPaieSB.setGain(bulletin.getSalairbase());
				listImprimBulletinPaie.add(imprimBulletinPaieSB);

				ImprimBulletinPaie imprimBulletinPaieSs = new ImprimBulletinPaie();
				imprimBulletinPaieSs.setLibelle("SURSALAIRE");
				imprimBulletinPaieSs.setTaux(bulletin.getJourTravail());
				imprimBulletinPaieSs.setBase(bulletin.getSursalaire());
				imprimBulletinPaieSs.setGain(bulletin.getSursalaire());
				listImprimBulletinPaie.add(imprimBulletinPaieSs);


				ImprimBulletinPaie imprimBulletinPaieanc = new ImprimBulletinPaie();
				imprimBulletinPaieanc.setLibelle("PRIME D'ANCIENNETE");
				imprimBulletinPaieanc.setBase(bulletin.getPrimeanciennete());
				imprimBulletinPaieanc.setGain(bulletin.getPrimeanciennete());
				listImprimBulletinPaie.add(imprimBulletinPaieanc);

				ImprimBulletinPaie imprimBulletinPaiePanc = new ImprimBulletinPaie();
				imprimBulletinPaiePanc.setLibelle("INDEMNITE DE RESIDENCE");
				imprimBulletinPaiePanc.setTaux(bulletin.getJourTravail());

				imprimBulletinPaiePanc.setBase(bulletin.getIndemnitelogement());
				imprimBulletinPaiePanc.setGain(bulletin.getIndemnitelogement());
				listImprimBulletinPaie.add(imprimBulletinPaiePanc);

				ImprimBulletinPaie imprimBulletinPaieLog = new ImprimBulletinPaie();
				imprimBulletinPaieLog.setLibelle("IND. DE TRANSPORT IMP ");
				imprimBulletinPaieLog.setTaux(bulletin.getJourTravail());
				//calcul de la base
				//	if(bulletin.getJourTravail() == 30){
				imprimBulletinPaieLog.setBase(bulletin.getIndemniteTransportImp());
				imprimBulletinPaieLog.setGain(bulletin.getIndemniteTransportImp());
				listImprimBulletinPaie.add(imprimBulletinPaieLog);

				ImprimBulletinPaie imprimBulletinPaieDivImp1 = new ImprimBulletinPaie();
				imprimBulletinPaieDivImp1.setLibelle("PRIMES IMPOSABLES ");
				imprimBulletinPaieDivImp1.setTaux(bulletin.getJourTravail());
				//calcul de la base
				//	if(bulletin.getJourTravail() == 30){
				imprimBulletinPaieDivImp1.setBase(bulletin.getAutreIndemImposable());
				imprimBulletinPaieDivImp1.setGain(bulletin.getAutreIndemImposable());
				listImprimBulletinPaie.add(imprimBulletinPaieDivImp1);

				List<Rubrique> listPrimeImp=new ArrayList<Rubrique>();
				listPrimeImp=rubriqueService.getRubriquesActivesType(1);
				for(Rubrique rubrique : listPrimeImp){
					List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
					listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
					if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
						ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
						imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
						if(listprimepersonnel.get(0).getPrime().getTaux()!=null)
							imprimBulletinPaieDivImp.setTaux(listprimepersonnel.get(0).getValeur().doubleValue());
						else
							imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
						imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
						imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
						listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
					}
				}
				List<Rubrique> listPrimeImp1=new ArrayList<Rubrique>();
				listPrimeImp1=rubriqueService.getRubriquesActivesType(3);
				for(Rubrique rubrique : listPrimeImp1){
					List<PrimePersonnel> listprimepersonnel1=new ArrayList<PrimePersonnel>() ;
					listprimepersonnel1=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
					if(listprimepersonnel1.size()>0 &&listprimepersonnel1.get(0) !=null){
						ImprimBulletinPaie imprimBulletinPaieDivImp10 = new ImprimBulletinPaie();
						imprimBulletinPaieDivImp10.setLibelle(listprimepersonnel1.get(0).getPrime().getLibelle());
						imprimBulletinPaieDivImp10.setTaux(bulletin.getJourTravail());
						imprimBulletinPaieDivImp10.setBase(listprimepersonnel1.get(0).getMontant()-listprimepersonnel1.get(0).getPrime().getMtExedent());
						imprimBulletinPaieDivImp10.setGain(listprimepersonnel1.get(0).getMontant()-listprimepersonnel1.get(0).getPrime().getMtExedent());
						listImprimBulletinPaie.add(imprimBulletinPaieDivImp10);
					}
				}

				ImprimBulletinPaie imprimBulletinPaieBrutImpos = new ImprimBulletinPaie();
				imprimBulletinPaieBrutImpos.setLibelle("***** Salaire Brut Imposable ******");
				imprimBulletinPaieBrutImpos.setGain(bulletin.getBrutImposable());
				listImprimBulletinPaie.add(imprimBulletinPaieBrutImpos);

				ImprimBulletinPaie imprimBulletinPaieTransport = new ImprimBulletinPaie();
				imprimBulletinPaieTransport.setLibelle("IND. DE TRANSPORT NON IMPOS");
				//imprimBulletinPaieTransport.setTaux(bulletin.getJourTravail());
				imprimBulletinPaieTransport.setGain(bulletin.getIndemniteTransport());
				listImprimBulletinPaie.add(imprimBulletinPaieTransport);

//			ImprimBulletinPaie imprimBulletinPaieRESP= new ImprimBulletinPaie();
//			imprimBulletinPaieRESP.setLibelle("AUTRES NON IMPOSABLES ");
//			//imprimBulletinPaieRESP.setTaux(bulletin.getJourTravail());
//			imprimBulletinPaieRESP.setGain(bulletin.getAutreNonImposable());
//			listImprimBulletinPaie.add(imprimBulletinPaieRESP);

				List<Rubrique> listPrimeImpnon=new ArrayList<Rubrique>();
				listPrimeImpnon=rubriqueService.getRubriquesActivesType(2);
				for(Rubrique rubrique : listPrimeImpnon){
					List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
					listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
					if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
						ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
						imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
						imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
						imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
						imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
						listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
					}
				}
				List<Rubrique> listPrimeImp12=new ArrayList<Rubrique>();
				listPrimeImp12=rubriqueService.getRubriquesActivesType(3);
				for(Rubrique rubrique : listPrimeImp12){
					List<PrimePersonnel> listprimepersonnel1=new ArrayList<PrimePersonnel>() ;
					listprimepersonnel1=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
					if(listprimepersonnel1.size()>0 &&listprimepersonnel1.get(0) !=null){
						ImprimBulletinPaie imprimBulletinPaieDivImp10 = new ImprimBulletinPaie();
						imprimBulletinPaieDivImp10.setLibelle(listprimepersonnel1.get(0).getPrime().getLibelle());
						imprimBulletinPaieDivImp10.setTaux(bulletin.getJourTravail());
						imprimBulletinPaieDivImp10.setBase(listprimepersonnel1.get(0).getMontant()-listprimepersonnel1.get(0).getPrime().getMtExedent());
						imprimBulletinPaieDivImp10.setGain(listprimepersonnel1.get(0).getMontant()-listprimepersonnel1.get(0).getPrime().getMtExedent());
						listImprimBulletinPaie.add(imprimBulletinPaieDivImp10);
					}
				}

				ImprimBulletinPaie imprimBulletinPaieSalBrutNon	 = new ImprimBulletinPaie();
				imprimBulletinPaieSalBrutNon.setLibelle("***** Salaire Brut Non Imposable ******");
				imprimBulletinPaieSalBrutNon.setTaux(bulletin.getJourTravail());
				imprimBulletinPaieSalBrutNon.setGain(bulletin.getBrutNonImposable());
				listImprimBulletinPaie.add(imprimBulletinPaieSalBrutNon);

				List<Rubrique> listPrimeG=new ArrayList<Rubrique>();
				listPrimeG=rubriqueService.getRubriquesActivesType(5);
				for(Rubrique rubrique : listPrimeG){
					List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
					listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
					if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
						ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
						imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
						//imprimBulletinPaieDivImp.setTaux(null);
						imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
						imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
						listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
					}
				}



				ImprimBulletinPaie imprimBulletinPaieIts = new ImprimBulletinPaie();
				imprimBulletinPaieIts.setLibelle("I.T.S (Nouveau Barème)");
				imprimBulletinPaieIts.setTaux(null);
				imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieIts.setRetenue(bulletin.getIts());
				listImprimBulletinPaie.add(imprimBulletinPaieIts);

				//ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
				//imprimBulletinPaieIgr.setLibelle("I.G.R");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				//imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
				//listImprimBulletinPaie.add(imprimBulletinPaieIgr);

				//ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
				//imprimBulletinPaieCn.setLibelle("CONTRIBUTION NATIONALE");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				//imprimBulletinPaieCn.setRetenue(bulletin.getCn());
				//listImprimBulletinPaie.add(imprimBulletinPaieCn);


				ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
				imprimBulletinPaieCnps.setLibelle("RETRAITE CNPS ");
				//imprimBulletinPaieCn.setTaux(1.2D);
				imprimBulletinPaieCnps.setBase(bulletin.getBasecnps());
				imprimBulletinPaieCnps.setRetenue(bulletin.getCnps());
				listImprimBulletinPaie.add(imprimBulletinPaieCnps);

				ImprimBulletinPaie imprimBulletinPaieIS= new ImprimBulletinPaie();
				imprimBulletinPaieIS.setLibelle("IMPOTS/SALAIRE LOCAL");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieIS.setRetenuePatron(bulletin.getIts());
				listImprimBulletinPaie.add(imprimBulletinPaieIS);

				ImprimBulletinPaie imprimBulletinPaieCnpsPATRON = new ImprimBulletinPaie();
				imprimBulletinPaieCnpsPATRON.setLibelle("RETRAITE CNPS/PART PATRONAL ");
				//imprimBulletinPaieCn.setTaux(1.2D);
				imprimBulletinPaieCnpsPATRON.setBase(bulletin.getBasecnps());
				imprimBulletinPaieCnpsPATRON.setRetenuePatron(bulletin.getRetraite());
				listImprimBulletinPaie.add(imprimBulletinPaieCnpsPATRON);


				ImprimBulletinPaie imprimBulletinPaiePF= new ImprimBulletinPaie();
				imprimBulletinPaiePF.setLibelle("PRESTATION FAMILIALE");
				imprimBulletinPaiePF.setTaux(5.75);

				imprimBulletinPaiePF.setBase(70000d);
				imprimBulletinPaiePF.setRetenuePatron(bulletin.getPrestationFamiliale());
				listImprimBulletinPaie.add(imprimBulletinPaiePF);

				ImprimBulletinPaie imprimBulletinPaieAC= new ImprimBulletinPaie();
				imprimBulletinPaieAC.setLibelle("ACCIDENT DE TRAVAIL");
				imprimBulletinPaieAC.setTaux(2d);
				imprimBulletinPaieAC.setBase(70000d);
				imprimBulletinPaieAC.setRetenuePatron(bulletin.getAccidentTravail());
				listImprimBulletinPaie.add(imprimBulletinPaieAC);

				ImprimBulletinPaie imprimBulletinPaieTA= new ImprimBulletinPaie();
				imprimBulletinPaieTA.setLibelle("FDFP -TAXE APPRENTISAGE ");
				imprimBulletinPaieTA.setTaux(0.4);
				imprimBulletinPaieTA.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieTA.setRetenuePatron(bulletin.getTa());
				listImprimBulletinPaie.add(imprimBulletinPaieTA);

				ImprimBulletinPaie imprimBulletinPaieFDFP= new ImprimBulletinPaie();
				imprimBulletinPaieFDFP.setLibelle("FDFP -TAXE A LA FPC ");
				imprimBulletinPaieFDFP.setTaux(0.6);
				imprimBulletinPaieFDFP.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieFDFP.setRetenuePatron(bulletin.getFpc());
				listImprimBulletinPaie.add(imprimBulletinPaieFDFP);

				ImprimBulletinPaie imprimBulletinPaieFDFPREG= new ImprimBulletinPaie();
				imprimBulletinPaieFDFPREG.setLibelle("FDFP -TAXE A LA FPC  REGUL");
				imprimBulletinPaieFDFPREG.setTaux(0.6);
				imprimBulletinPaieFDFPREG.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieFDFPREG.setRetenuePatron(bulletin.getFpcregul());
				listImprimBulletinPaie.add(imprimBulletinPaieFDFPREG);

				ImprimBulletinPaie imprimBulletinPaieAMAO= new ImprimBulletinPaie();
				imprimBulletinPaieAMAO.setLibelle("AVANCES/ACOMPTES ");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieAMAO.setRetenue(bulletin.getAvanceetacompte());
				listImprimBulletinPaie.add(imprimBulletinPaieAMAO);

				ImprimBulletinPaie imprimBulletinPaieAMAO1= new ImprimBulletinPaie();
				imprimBulletinPaieAMAO1.setLibelle("AUTRES PRETS ");
				imprimBulletinPaieAMAO1.setRetenue(bulletin.getPretaloes());
				listImprimBulletinPaie.add(imprimBulletinPaieAMAO1);
				List<Rubrique> listPrimeM=new ArrayList<Rubrique>();
				listPrimeM=rubriqueService.getRubriquesActivesType(4);
				for(Rubrique rubrique : listPrimeM){
					List<PrimePersonnel> listprimepersonnel=new ArrayList<PrimePersonnel>() ;
					listprimepersonnel=	primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(),bulletin.getPeriodePaie().getId(),rubrique.getId());
					if(listprimepersonnel.size()>0 &&listprimepersonnel.get(0) !=null){
						ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
						imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
						//imprimBulletinPaieDivImp.setTaux(null);
						//	imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
						imprimBulletinPaieDivImp.setRetenue(listprimepersonnel.get(0).getMontant());
						listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
					}
				}





				bulletin.setListImprimBulletinPaie(listImprimBulletinPaie);


				Date dateRetourDernierConge = null;
				if(bulletin.getContratPersonnel().getPersonnel().getDateRetourcge()==null)
				{logger.info("**********************************************g suiiiiiiiiiiiiii");
					dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateArrivee();}

				else
				{dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateRetourcge();}
				int tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge, bulletin.getPeriodePaie().getDatefin());
				bulletin.setNbcongedu(String.valueOf(bulletin.getTempsOfpresence()));
				bulletin.setTpsdepresence(String.valueOf(bulletin.getMoisOfpresence()));
				
//
			//	oldCongeList = congeRepository.findTop1ByContratPersonnel(bulletin.getContratPersonnel());
			//	if(oldCongeList != null && oldCongeList.isEmpty()) // Si le resultat est null :: 
					// Alors on cherche la date d'arrive du personnel
					    if(bulletin.getContratPersonnel().getPersonnel().getDateRetourcge()==null)
					        {
					        	System.out.println("********************** Date de conge ************************** Date arrivee*");
					        	dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateArrivee();
					        }
					        else{
					        	System.out.println("********************** Date de conge ********************* Date de retour******");
					        	dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateRetourcge();
					        }
					       
				//else
				//	dateRetourDernierConge = oldCongeList.get(0).getDateRetour();
				 tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge, bulletin.getPeriodePaie().getDatefin());
				bulletin.setNbcongedu(String.valueOf(bulletin.getTempsOfpresence()));
				bulletin.setTpsdepresence(String.valueOf(bulletin.getMoisOfpresence()));
			/*	bulletin.setNbcongedu(String.valueOf(tps*2.2*1.25));
				bulletin.setTpsdepresence(String.valueOf(tps));*/
			//	modelMap.addAttribute("congacquis",Double.parseDouble(bulletin.getNbcongedu()));
			//	modelMap.addAttribute("tpsPresence",bulletin.getTpsdepresence());	
				view="bulletinpdf";

			}


			listImprimebulletin.add(bulletin);

		}
        logger.info("nombre de bulletin à imprimer: {}" , listImprimebulletin.size());
		byte[] pdfBytes = generateMoisBulllipPdf(listImprimebulletin,request);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("filename", "Bulletin_M.pdf");
		return ResponseEntity.ok().headers(headers).body(pdfBytes);
		//return null; //mav;
		
		
	}
	
//	@RequestMapping(value="/chargerbulletinparperiode", method = RequestMethod.GET)
//	@ResponseStatus(HttpStatus.OK)
//    public @ResponseBody BulletinPaieDTO chargerGratificationParPeriode(@RequestParam(value="id", required=true) Long id) {
//		BulletinPaieDTO BulletinPaieDTO=new BulletinPaieDTO();
//		List<BulletinPaie> bulletinPaieList = new ArrayList<BulletinPaie>();
//		try {
//			bulletinPaieList = bulletinPaieService.findBulletinByPeriodePaie(id);
//			logger.info("success");
//		} catch (Exception ex) {
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//			logger.info("fail");
//		}
//		BulletinPaieDTO.setRows(bulletinPaieList);
//		BulletinPaieDTO.setResult("succes");
//		return BulletinPaieDTO ;
//    }
	@RequestMapping(value="/imprimerbulletinpaie", method = RequestMethod.GET)
    public ModelAndView imprimerBulletinGratification(@RequestParam(value="idbull", required=true) Long idbull, ModelAndView modelAndView){
 
        logger.debug("--------------generate PDF report----------");
 
        Map<String,Object> parameterMap = new HashMap<String, Object>();
        
        List<BulletinPaie> gratificationList = new ArrayList<BulletinPaie>();
        BulletinPaie gratification = bulletinPaieRepository.findById(idbull).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idbull));
        gratificationList.add(gratification);
     //  JRDataSource JRdataSource = new JRBeanCollectionDataSource(new ArrayList<BulletinPaie>());
 
        
       // parameterMap.put("periodePaie", gratification.getPeriodePaie().getMois().getMois());
     ///.put("situationMatrimoniale", gratification.getContratPersonnel().getPersonnel().getSituationMatri());
      //  parameterMap.put("numeroCNPS", gratification.getContratPersonnel().getPersonnel().getNumeroCnps());
  //      parameterMap.put("datasource", JRdataSource);
 
        //pdfReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("pdfReport", parameterMap);
 
        return modelAndView;
	}
	@RequestMapping(value={"/postVirementBulletin"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody BulletinPaieDTO imprOrdreVirmt (ModelMap modelMap, @RequestParam(value="banque", required=true) Long banque , @RequestParam(value="banqueEmp", required=true) Long banqueEmp ,HttpServletRequest request){

		String view="livrepaie";

		BulletinPaieDTO BulletinPaieDTO=new BulletinPaieDTO();
	//	if(printDTO.getVal1() != null && printDTO.getVal2() != null){
			PeriodePaie  periodePaie = periodePaieService.findPeriodeactive();
			System.out.println("########## periode : "+periodePaie.getMois().getMois());
			Banque tbanqueAvirmt =banqueService.findBanquesID(banqueEmp);
			Banque tbanque =banqueService.findBanquesID(banque);
			System.out.println("########## banque : "+tbanque.getCodbanq() );
			String codbanqr=tbanqueAvirmt.getCodbanq() ;
		List<BulletinPaie> listbulletin=  new ArrayList<BulletinPaie>();

		List<BulletinPaie> listBull = new ArrayList<BulletinPaie>();
	// creer un repertoire

	//	String uploadPath =  "resources/user/agence"+File.separator;
		String uploadPath =  request.getSession().getServletContext().getRealPath("") + File.separator + "resources"+File.separator+"virement"+File.separator;

//////////////////////////////////////////////////////////////////////////////////////////////////////

		// GESTION PHOTO
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
		//	System.out.println(">>> CHEMIN UPLOAD >>>" + uploadPath);
			uploadDir.mkdirs();
			//System.out.println(">>> CHEMIN Creer >>>" + uploadDir);
		}
	//	File monfichier = new File(uploadDir,"/resources/virement"+File.separator+banque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt");
		String monfichier =tbanque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt";
		File fichier = new File(uploadDir+File.separator+monfichier);

		    try{
		    	//Writer out = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream("fileDir"), "UTF8"));
		    	//"/resources/agence/"+periodePaie.getAgence().getId()+"/"+fichier
		    	CpteVirmtBanque moncpteVirmt=new CpteVirmtBanque();
		    	moncpteVirmt=cpteVirmtBanqueService.findCpteVirmtoFBanques(tbanqueAvirmt);
		    //	System.out.println("######################################################### banque compte de virement societe : "+moncpteVirmt.toString() );
		    	 FileWriter fw = new FileWriter(fichier,false);
				String SGBCI="";
		    	 if(moncpteVirmt!=null)
		    	  SGBCI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+moncpteVirmt.getBank().getCodbanq()+moncpteVirmt.getNumguichCpteVirmt()+moncpteVirmt.getNumcpteCpteVirmt()+moncpteVirmt.getRibCpteVirmt()+moncpteVirmt.getDonneurOrdCpteVirmt()+moncpteVirmt.getCodEtablVirmt();
		           else
					 SGBCI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy");
		    	 //	 String BICICI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+"0060000000000000000                    00153";

		    	// String SIB="02100001"+methodsShared.dateToString(new Date(), "dd/MM/yy")+"008000000000000000                      00153";
		    	// if(Tbanque.getId()==11L)
		    	 fw.write(SGBCI);  // ecrire une ligne dans le fichier resultat.txt

		    	 fw.write(System.getProperty("line.separator"));
		    	 //fw.write("\n");
		    	 listBull = bulletinPaieService.findAllBulletinByvirementforBanque(periodePaie.getId(),tbanque.getId()) ;
		    	 System.out.println("88888888888888888888888888888888888888888888888888888888888 bulletin periode "+listbulletin.size());

		    	 Float cumSal=0f;int c=0,ty=0;String numsek="",numsekC="";
		    	 for (int r = 0; r<listBull.size(); r++){
						//This data needs to be written (Object[])
		    		 System.out.println("55555555555555555555555555555555555555555555555555555 debut virement "+listbulletin.size());
							//Personnel bankpersonnel= listBull.get(r).getContratPersonnel().getPersonnel().getBanquek());
							String codop="",typenr="",datop="",codbanq="",codbanqA="",numguich="",numguichA="",matricul="",matriculA="",nocpte="",nocpteA="",nom="",nomA="",libelop="",libelopA="";Float mtvirem=0f;
							double  mtLen=0d;String mtnet="";String banque1="";
							c=r+2;ty=r+3;
							 if(listBull.get(r).getContratPersonnel().getPersonnel().getBanquek()==null){

							 }else{

								 codop="02";
								 typenr="2";
								 if(c<10)numsek="0000"+c;
								 if(c>=10 && c<=99)numsek="000"+c;
								 if(c>=100 && c<=999)numsek="00"+c;

								 if(ty<10)numsekC="0000"+ty;
								 if(ty>=10 && ty<=99)numsekC="000"+ty;
								 if(ty>=100 && ty<=999)numsekC="00"+ty;

								// if(r>=1000 && r<=9999)numsek="0"+c;
								 datop=methodsShared.dateToString(new Date(),"ddMMyy");

								 int n = 11; // nbre de caracteres
								  codbanq=listBull.get(r).getContratPersonnel().getPersonnel().getBanquek().getCodbanq().substring(2, 5);
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 code de banqe "+codbanq);
								 int numGuich= listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet().length();
								 if(numGuich==5)
							         numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
						         if(numGuich==4 )
							        numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();

								 int leng = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().length();

								 if(leng==11 )
							      nocpte=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
						         if(leng==12 )
							      nocpte=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(1, 12);
						         if(leng==13 )
							     nocpte=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(2, 13);
						         if(leng==15 )
						         	nocpte=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(4, 15);
						         if(leng==16 )
							      nocpte=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);


								 System.out.println("88888888888888888888888888888888888888888888888888888888888 NOCPTE "+nocpte);
								 int nMatr = 7;

								 String jp	=""; String matru="";
								 //  StringBuilder	 sb = new StringBuilder();
								    matru=listBull.get(r).getContratPersonnel().getPersonnel().getMatricule().replace("-","");
							        matricul=matru.replace(" ", "").toUpperCase();
							          int lengthMatr=matricul.length(); int diff=0;String blanc=new String();
								 if(lengthMatr==nMatr){
									 jp=matricul;
								      //sb.append(matricul);
									 }
								 else if(lengthMatr>nMatr) {
									 jp=matricul.substring(0,7);

								 }else{
									 diff=nMatr-lengthMatr;
									 if(diff==1)blanc="      ";
									 if(diff==2)blanc="     ";
									 if(diff==3)blanc="    ";
									 if(diff==4)blanc="   ";
									 if(diff==5)blanc="  ";
									 if(diff==6)blanc=" ";
									 jp=padRight(matricul,7,' ');
								 }


								 nom=listBull.get(r).getContratPersonnel().getPersonnel().getNom() +" "+listBull.get(r).getContratPersonnel().getPersonnel().getPrenom();
								 int maxNom=24;String nom1="";
								  StringBuilder	 snom = new StringBuilder();
								int tailNom=nom.length();
								 if(tailNom==maxNom){
								  nom1=nom.toUpperCase();
								//  snom.append(nom1);
								  }else if(tailNom>maxNom) {
									 nom1=nom.substring(0,24);

								 }else
								 {
									 nom1= padRight(nom,24,' ');
								 }
							//	 System.out.println("88888888888888888888888888888888888888888888888888888888888 MATRICULE "+jp.length()+"****** :"+jp);
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 MATRICULE "+jp.length()+"****** :"+jp);
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 NOM "+nom1.length()+"****** :"+nom1);
								 int sigleB=17;
								 String uba="";
							     banque1=tbanque.getSigle();
							   //  StringBuilder	 snombk = new StringBuilder();
							     if(banque1.length()==sigleB){
							         /* jp	=listBull.get(r).getPersonnel().getMatricule().substring(0, 9).replace("-", "");
							          matricul=jp.replace(" ", "");*/
							    	 uba=banque1;
							     }
								 else if(banque1.length()>sigleB) {

									 uba = banque1.substring(0, 17);
								 }else
									 {
										 uba= padRight(banque1,17,' ');
									 }



							     System.out.println("88888888888888888888888888888888888888888888888888888888888 Banque sigle "+uba);
								 if(listBull.get(r).getContratPersonnel().getPersonnel().getCarec()==true)
							     libelop="VIREMENT SALAIRES"+" "+ periodePaie.getMois().getMois()+" "+periodePaie.getAnnee().getAnnee().substring(2,4);
						         else
						     	libelop="VIREMENT INDEMNITES ";

							    // libelop="VIREMENT SALAIRES"+" "+ periodePaie.getMois().getMois()+" "+periodePaie.getAnnee().getAnnee().substring(2,4);


							     int lIBopB=30;
							     String opBank="";
							     if(libelop.length()==lIBopB){

							    	 opBank=libelop;}
								 else if(libelop.length()>lIBopB) {
									 opBank = libelop.substring(0, 30);

								 }else{
									 opBank= padRight(libelop,30,' ');
								 }
							     System.out.println("88888888888888888888888888888888888888888888888888888888888 libelop Banque "+opBank);

							     cumSal=(float) (cumSal+listBull.get(r).getNetapayer());
							      mtLen=listBull.get(r).getNetapayer();
							      String okmt=Utils.formattingAmount(listBull.get(r).getNetapayer());
							      int imt = (int) mtLen;
							     if(mtLen<10f)mtnet=00000000000+String.valueOf(mtLen);
								 if(mtLen>=10d && mtLen<=99d) mtnet="0000000000"+String.valueOf(mtLen);
								 if(mtLen>=100d && mtLen<=999d) mtnet="000000000"+String.valueOf(mtLen);
								 if(mtLen>=1000d && mtLen<=9999d) mtnet="00000000"+String.valueOf(mtLen);
								 if(mtLen>=10000d && mtLen<=99999d) mtnet="0000000"+String.valueOf(mtLen);
								 if(mtLen>=100000d && mtLen<=999999d) mtnet="000000"+String.valueOf(mtLen);
								 if(mtLen>=1000000d && mtLen<=9999999d) mtnet="00000"+String.valueOf(mtLen);
								 if(mtLen>=10000000d && mtLen<=99999999d) mtnet="0000"+String.valueOf(mtLen);
								 if(mtLen>=100000000d && mtLen<=999999999d) mtnet="000"+String.valueOf(mtLen);
								 if(mtLen>=1000000000d && mtLen<=9999999999d) mtnet="00"+String.valueOf(mtLen);
								 if(mtLen>=10000000000d && mtLen<=99999999999d) mtnet="0"+String.valueOf(mtLen);


							     fw.write(codop+typenr+numsek+datop+codbanq+numguichA+nocpte+jp+nom1+uba+opBank+ mtnet.substring(0,12)+"     ");  // ecrire une ligne dans le fichier resultat.txt
						    	 fw.write(System.getProperty("line.separator"));

							 }
						}
		    	 String codopC="",typenrC="",datopC="",blanck="";
		    	 codopC="02";typenrC="9";double valnet=cumSal;

				 blanck="        ";
				 datopC=methodsShared.dateToString(new Date(),"ddMMyy");
				 int imtTT = (int) valnet;
				  fw.write(codopC+typenrC+numsekC+datopC+blanck+imtTT);  // ecrire une ligne dans le fichier resultat.txt
			    	 fw.write(System.getProperty("line.separator"));
		    	  fw.close(); // fermer le fichier a la fin des traitements


		    	}catch(IOException e){

		    	}

				    promotion = null;

						try {
							promotion = "/resources/virement/"+tbanque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt";

						} catch (Exception e) {
							e.printStackTrace();
						}
						//view=promotion;
						modelMap.addAttribute("promotion", promotion);


				    System.out.println(fichier.getName()+" written successfully on disk.");

				    BulletinPaieDTO.setMessage(promotion);
		return BulletinPaieDTO;
	}

//	@RequestMapping(value={"/postVirementBulletin"}, method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.OK)
//	public @ResponseBody BulletinPaieDTO imprOrdreVirmt (ModelMap modelMap, @RequestParam(value="banque", required=true) Long banque , @RequestParam(value="banqueEmp", required=true) Long banqueEmp ,HttpServletRequest request) throws ParseException {
//
//		String view="livrepaie";
//		//int  val=DateManager.deadlineDifference();
//		BulletinPaieDTO BulletinPaieDTO=new BulletinPaieDTO();
//		//if(val==0 || val<0){}else{
//
//			//	if(printDTO.getVal1() != null && printDTO.getVal2() != null){
//			PeriodePaie  periodePaie = periodePaieService.findPeriodeactive();
//			System.out.println("########## periode : "+periodePaie.getMois().getMois());
//			Banque tbanqueAvirmt =banqueService.findBanquesID(banqueEmp);
//			Banque tbanque =banqueService.findBanquesID(banque);
//			System.out.println("########## banque : "+tbanque.getCodbanq() );
//			String codbanqr=tbanqueAvirmt.getCodbanq() ;
//			List<BulletinPaie> listbulletin=  new ArrayList<BulletinPaie>();
//
//			List<BulletinPaie> listBull = new ArrayList<BulletinPaie>();
//			// creer un repertoire
//
//			//	String uploadPath =  "resources/user/agence"+File.separator;
//			String uploadPath =  request.getSession().getServletContext().getRealPath("") + File.separator + "resources"+File.separator+"virement"+File.separator;
//
////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//			// GESTION PHOTO
//			// creates the directory if it does not exist
//			File uploadDir = new File(uploadPath);
//			if (!uploadDir.exists()) {
//				//	System.out.println(">>> CHEMIN UPLOAD >>>" + uploadPath);
//				uploadDir.mkdirs();
//				//System.out.println(">>> CHEMIN Creer >>>" + uploadDir);
//			}
//			//	File monfichier = new File(uploadDir,"/resources/virement"+File.separator+banque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt");
//			String monfichier =tbanque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt";
//			File fichier = new File(uploadDir+File.separator+monfichier);
//
//			try{
//				//Writer out = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream("fileDir"), "UTF8"));
//				//"/resources/agence/"+periodePaie.getAgence().getId()+"/"+fichier
//				CpteVirmtBanque moncpteVirmt=new CpteVirmtBanque();
//				moncpteVirmt=cpteVirmtBanqueService.findCpteVirmtoFBanques(tbanqueAvirmt);
//				System.out.println("######################################################### banque compte de virement societe : "+moncpteVirmt.toString() );
//				FileWriter fw = new FileWriter(fichier,false);
//				String SGBCI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00153";
//				String NSIA="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0123131272132419ONI"+"                     "+"00153";
//				String SIB="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00153";
//				String CNCE="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00153";
//				String TRESOR="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00000";
//				String BICICI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00153";
//				String BHCI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00153";
//				String BACI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq().substring(2, 5)+"0000000000000000ONI"+"                     "+"00153";
//				String ECOBANK="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+tbanque.getCodbanq()+"XXXXXXXXXXXXXXXXXXXONI"+"                     "+"00000";
//
//				if(tbanque.getId()==11L)
//					fw.write(SGBCI);  // ecrire une ligne dans le fichier resultat.txt
//				if(tbanque.getId()==8L)
//					fw.write(NSIA);
//				if(tbanque.getId()==2L)
//					fw.write(SIB);
//				if(tbanque.getId()==6L)
//					fw.write(CNCE);
//				if(tbanque.getId()==16L)
//					fw.write(TRESOR);
//				if(tbanque.getId()==4L)
//					fw.write(BICICI);
//				if(tbanque.getId()==5L)
//					fw.write(BHCI);
//				if(tbanque.getId()==12L)
//					fw.write(BACI);
//				if(tbanque.getId()==3L)
//					fw.write(ECOBANK);
//				fw.write(System.getProperty("line.separator"));
//				//fw.write("\n");
//				listBull = bulletinPaieService.findAllBulletinByvirementforBanque(periodePaie.getId(),tbanque.getId()) ;
//				System.out.println("88888888888888888888888888888888888888888888888888888888888 bulletin periode "+listbulletin.size());
//
//				Float cumSal=0f;int c=0,ty=0;String numsek="",numsekC="";Double cumulnocpteA=0D;
//				for (int r = 0; r<listBull.size(); r++){
//					//This data needs to be written (Object[])
//					System.out.println("55555555555555555555555555555555555555555555555555555 debut virement "+listbulletin.size());
//					//Personnel bankpersonnel= listBull.get(r).getContratPersonnel().getPersonnel().getBanquek());
//					String codop="",typenr="",datop="",codbanq="",codbanqA="",numguich="",numguichA="",matricul="",matriculA="",nocpte="",nocpteA="",nom="",nomA="",cumA="",libelop="",libelopA="";Float mtvirem=0f;
//					double  mtLen=0d;String mtnet="";String banque1="";
//					c=r+2;ty=r+3;
//					if(listBull.get(r).getContratPersonnel().getPersonnel().getBanquek()==null){
//
//					}else{
//
//						codop="02";
//						typenr="2";
//						if(c<10)numsek="0000"+c;
//						if(c>=10 && c<=99)numsek="000"+c;
//						if(c>=100 && c<=999)numsek="00"+c;
//
//						if(ty<10)numsekC="0000"+ty;
//						if(ty>=10 && ty<=99)numsekC="000"+ty;
//						if(ty>=100 && ty<=999)numsekC="00"+ty;
//
//						// if(r>=1000 && r<=9999)numsek="0"+c;
//						datop=methodsShared.dateToString(new Date(),"ddMMyy");
//
//						//check codbank
//						if(tbanque.getId()!=3L)
//							codbanq=listBull.get(r).getContratPersonnel().getPersonnel().getBanquek().getCodbanq().substring(2, 5);
//						else
//							codbanq=listBull.get(r).getContratPersonnel().getPersonnel().getBanquek().getCodbanq();
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 code de banqe "+codbanq);
//
//						//check numgiuichet
//						//sgbci
//						int numGuich= listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet().length();
//						if(numGuich==5 && tbanque.getId()==11L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==11L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						//NSIA
//						if(numGuich==5 && tbanque.getId()==8L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==8L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						//SIB
//						if(numGuich==5 && tbanque.getId()==2L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==2L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==2 && tbanque.getId()==2L)
//							numguichA="010"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						//CNCE
//						if(numGuich==5 && tbanque.getId()==6L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==6L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==3 && tbanque.getId()==6L)
//							numguichA="00"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						//TRESOR
//						if(numGuich==5 && tbanque.getId()==16L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==16L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==3 && tbanque.getId()==16L)
//							numguichA="01"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//
//						//BICICI
//						if(numGuich==5 && tbanque.getId()==4L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==4L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						//BHCI
//						if(numGuich==5 && tbanque.getId()==5L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==5L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						//BACI
//						if(numGuich==5 && tbanque.getId()==12L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==12L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						//ECOBANK
//						if(numGuich==5 && tbanque.getId()==3L)
//							numguichA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//						if(numGuich==4 && tbanque.getId()==3L)
//							numguichA="0"+listBull.get(r).getContratPersonnel().getPersonnel().getNumeroGuichet();
//
//						////////		check numero compte //////////
//						int n = 11; // nbre de caracteres
//						int leng = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().length();
//
//						//	sgbci
//						if(leng==11 && tbanque.getId()==11L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==12 && tbanque.getId()==11L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(1, 12);
//						if(leng==13 && tbanque.getId()==11L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(2, 13);
//						if(leng==16 && tbanque.getId()==11L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//
//						//NSIA
//						if(leng==11 && tbanque.getId()==8L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==12 && tbanque.getId()==8L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(1, 12);
//						if(leng==15 && tbanque.getId()==8L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(4, 15);
//						if(leng==16 && tbanque.getId()==8L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//
//						//SIB
//						if(leng==11 && tbanque.getId()==2L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==12 && tbanque.getId()==2L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(0, 11);
//
//						//CNCE
//						if(leng==16 && tbanque.getId()==6L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//						if(leng==11 && tbanque.getId()==6L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==12 && tbanque.getId()==6L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(1, 12);;
//
//						//TRESOR
//						if(leng==17 && tbanque.getId()==16L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(6, 17);
//						if(leng==16 && tbanque.getId()==16L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//						if(leng==12 && tbanque.getId()==16L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(1, 12);
//						if(leng==11 && tbanque.getId()==16L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//
//
//						//BICICI
//						if(leng==16 && tbanque.getId()==4L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//						if(leng==11 && tbanque.getId()==4L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==15 && tbanque.getId()==4L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(4, 15);
//						//BHCI
//						if(leng==16 && tbanque.getId()==5L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(4, 16);
//						if(leng==12 && tbanque.getId()==5L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(0, 12);
//						if(leng==17 && tbanque.getId()==5L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 17);
//						//BACI
//						if(leng==11 && tbanque.getId()==12L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==15 && tbanque.getId()==12L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(4, 15);
//						if(leng==16 && tbanque.getId()==12L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//
//						//ECOBANK
//						if(leng==11 && tbanque.getId()==3L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//						if(leng==15 && tbanque.getId()==3L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(4, 15);
//						if(leng==16 && tbanque.getId()==3L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(5, 16);
//						if(leng==17 && tbanque.getId()==3L)
//							nocpteA=listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().substring(6, 17);
//
//
//
//
//
//						if(leng>=12)
//							nocpte = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().toString();
//						else
//							nocpte = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte();
//
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 NOCPTE "+nocpte);
//						int nMatr = 7;
//
//						String jp	=""; String matru="";
//						//  StringBuilder	 sb = new StringBuilder();
//						matru=listBull.get(r).getContratPersonnel().getPersonnel().getMatricule().replace("-","");
//						matricul=matru.replace(" ", "").toUpperCase();
//						int lengthMatr=matricul.length(); int diff=0;String blanc=new String();
//						if(lengthMatr==nMatr){
//							jp=matricul;
//							//sb.append(matricul);
//						}
//						else if(lengthMatr>nMatr) {
//							jp=matricul.substring(0,7);
//
//						}else{
//							diff=nMatr-lengthMatr;
//							if(diff==1)blanc="      ";
//							if(diff==2)blanc="     ";
//							if(diff==3)blanc="    ";
//							if(diff==4)blanc="   ";
//							if(diff==5)blanc="  ";
//							if(diff==6)blanc=" ";
//							jp=matricul.concat(blanc);
//						}
//
//
//						// nom=listBull.get(r).getContratPersonnel().getPersonnel().getNom() +""+listBull.get(r).getContratPersonnel().getPersonnel().getPrenom();
//						nom=listBull.get(r).getContratPersonnel().getPersonnel().getNomComplet();
//						int maxNom=24;String nom1="";
//						StringBuilder	 snom = new StringBuilder();
//						int tailNom=nom.length();
//						if(tailNom>maxNom){
//
//							nom1=nom.substring(0,24).toUpperCase();
//							//  snom.append(nom1);
//						}
//						else{
//							//nom1=nom;
//							String vide1n="                        ";
//							nom1=nom.toUpperCase().concat(vide1n).substring(0,24);
//
//
//						}
//
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 MATRICULE "+jp.length()+"****** :"+jp);
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 NOM "+nom1.length()+"****** :"+nom1);
//						int sigleB=17;
//						String uba="";
//						banque1=tbanque.getSigle();
//						//  StringBuilder	 snombk = new StringBuilder();
//						if(banque1.length()>=sigleB){
//							         /* jp	=listBull.get(r).getPersonnel().getMatricule().substring(0, 9).replace("-", "");
//							          matricul=jp.replace(" ", "");*/
//							uba=banque1.substring(0, 17);}
//						else {
//							// jp=listBull.get(r).getPersonnel().getMatricule().replace("-", "");
//							// matricul=jp.replace(" ", "");
//							String vide1="                 ";
//							uba=banque1.concat(vide1).substring(0, 17);
//
//
//
//						}
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 Banque sigle "+uba);
//
//						if(listBull.get(r).getContratPersonnel().getPersonnel().getCarec()==false)
//							libelop="VIREMENT SALAIRES"+" "+ periodePaie.getMois().getMois()+" "+periodePaie.getAnnee().getAnnee().substring(2,4);
//						else
//							libelop="VIREMENT INDEMNITES ";
//
//						int lIBopB=30;
//						String opBank="";
//						if(libelop.length()>=lIBopB){
//
//							opBank=libelop.substring(0, lIBopB);}
//						else {
//							opBank=libelop.concat("                              ").substring(0, 30);
//
//						}
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 libelop Banque "+opBank);
//
//						cumSal=(float) (cumSal+listBull.get(r).getNetapayer());
//						mtLen=listBull.get(r).getNetapayer();
//						String okmt=Utils.formattingAmount(listBull.get(r).getNetapayer());
//						int imt = (int) mtLen;
//						if(mtLen<10f)mtnet="00000000000"+String.valueOf(mtLen);
//						if(mtLen>=10d && mtLen<=99d) mtnet="0000000000"+String.valueOf(mtLen);
//						if(mtLen>=100d && mtLen<=999d) mtnet="000000000"+String.valueOf(mtLen);
//						if(mtLen>=1000d && mtLen<=9999d) mtnet="00000000"+String.valueOf(mtLen);
//						if(mtLen>=10000d && mtLen<=99999d) mtnet="0000000"+String.valueOf(mtLen);
//						if(mtLen>=100000d && mtLen<=999999d) mtnet="000000"+String.valueOf(mtLen);
//						if(mtLen>=1000000d && mtLen<=9999999d) mtnet="00000"+String.valueOf(mtLen);
//						if(mtLen>=10000000d && mtLen<=99999999d) mtnet="0000"+String.valueOf(mtLen);
//						if(mtLen>=100000000d && mtLen<=999999999d) mtnet="000"+String.valueOf(mtLen);
//						if(mtLen>=1000000000d && mtLen<=9999999999d) mtnet="00"+String.valueOf(mtLen);
//						if(mtLen>=10000000000d && mtLen<=99999999999d) mtnet="0"+String.valueOf(mtLen);
//
//						if(tbanque.getId()==5L)
//							fw.write(codop+typenr+numsek+datop+codbanq+numguichA+nocpteA+jp+nom1.substring(0, 23)+uba+opBank+ mtnet.substring(0,12)+"     ");
//						else if(tbanque.getId()==6L)
//							fw.write(codop+typenr+numsek+datop+codbanq+numguichA+nocpteA+jp+nom1+"     "+uba.substring(0, 12)+opBank+ mtnet.substring(0,12)+"     ");
//						else
//							fw.write(codop+typenr+numsek+datop+codbanq+numguichA+nocpteA+jp+nom1+uba+opBank+ mtnet.substring(0,12)+"     ");
//						// ecrire une ligne dans le fichier resultat.txt
//						fw.write(System.getProperty("line.separator"));
//						//	 cumulnocpteA=cumulnocpteA + Double.parseDouble(nocpteA) ;
//						System.out.println("88888888888888888888888888888888888888888888888888888888888 cumul nocpte cumul nocpte "+Utils.formattingAmount(cumulnocpteA));
//
//
//					}
//				}
//				String codopC="",typenrC="",datopC="",blanck="";
//				codopC="02";typenrC="9";double valnet=cumSal;
//
//				blanck="        ";String mtnet="";
//				datopC=methodsShared.dateToString(new Date(),"ddMMyy");
//				int imtTT = (int) valnet;
//				if(imtTT>=10000d && imtTT<=99999d) mtnet="0000000"+String.valueOf(imtTT);
//				if(imtTT>=100000d && imtTT<=999999d) mtnet="000000"+String.valueOf(imtTT);
//				if(imtTT>=1000000d && imtTT<=9999999d) mtnet="00000"+String.valueOf(imtTT);
//				if(imtTT>=10000000d && imtTT<=99999999d) mtnet="0000"+String.valueOf(imtTT);
//				if(imtTT>=100000000d && imtTT<=999999999d) mtnet="000"+String.valueOf(imtTT);
//				if(imtTT>=1000000000d && imtTT<=9999999999d) mtnet="00"+String.valueOf(imtTT);
//				if(imtTT>=10000000000d && imtTT<=99999999999d) mtnet="0"+String.valueOf(imtTT);
//				if( tbanque.getId()==11L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"1450347162"+"                                                                               "+mtnet.substring(0,12)+"     ");  //ecrire une ligne dans le fichier resultat.txt
//				if( tbanque.getId()==8L)
//					fw.write(codopC+typenrC+numsekC+datopC+"                                                                                                 "+mtnet.substring(0,12)+"     ");
//				if( tbanque.getId()==2L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"6580078215"+"                                                                               "+mtnet.substring(0,12)+"     ");
//				if( tbanque.getId()==6L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"2365419992"+"                                                                               "+mtnet.substring(0,12)+"     ");
//				if( tbanque.getId()==16L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"1450347162"+"                                                                               "+mtnet.substring(0,12)+"     ");
//				if( tbanque.getId()==4L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"8841491809"+"                                                                               "+mtnet.substring(0,12)+"     ");
//				if( tbanque.getId()==5L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"5793622478"+"                                                                               "+mtnet.substring(0,12)+"     ");
//				if( tbanque.getId()==12L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"1450347162"+"                                                                               "+mtnet.substring(0,12)+"      ");
//				if( tbanque.getId()==3L)
//					fw.write(codopC+typenrC+numsekC+datopC+blanck+"256686524362"+"                                                                            "+mtnet.substring(0,12)+"      ");
//
//				fw.write(System.getProperty("line.separator"));
//				fw.close(); // fermer le fichier a la fin des traitements
//
//
//			}catch(IOException e){
//
//			}
//
//			promotion = null;
//
//			try {
//				promotion = "/resources/virement/"+tbanque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt";
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//view=promotion;
//			modelMap.addAttribute("promotion", promotion);
//
//
//			System.out.println(fichier.getName()+" written successfully on disk.");
//
//			BulletinPaieDTO.setMessage(promotion);
//		//}
//		return BulletinPaieDTO;
//	}
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
		tab[2] = (double)partieApresVirg;
		
	
		
		return tab;
	}

public Float calculNbrepart( Integer nbEnfant, Personnel pers){

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
	public static String padRight(String original, int padToLength, char padWith) {
		if (original.length() >= padToLength) {
			return original;
		}
		StringBuilder sb = new StringBuilder(padToLength);
		sb.append(original);
		for (int i = original.length(); i < padToLength; ++i) {
			sb.append(padWith);
		}
		return sb.toString();
	}
}
