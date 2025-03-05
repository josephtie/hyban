package com.nectux.mizan.hyban.paie.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/paie")
public class BulletinPaieController {

	
private static final Logger logger = LogManager.getLogger(BulletinPaieController.class);
	
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
	MethodsShared methodsShared = new MethodsShared();
	List<LivreDePaie> livredepaieList=null;
	private PeriodePaie maperiode;
	private String promotion;
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
	public @ResponseBody BulletinPaieDTO chargerGratificationParPeriode(@RequestParam(value="id", required=true) Long id,@RequestParam(value="search1", required=false) String search1,@RequestParam(value="limit", required=false) Integer limit,
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
		if(search == null && search == "")
			bulletinDTO = bulletinPaieService.loadBulletinPaie(page,maperiode);
		else
			bulletinDTO = bulletinPaieService.loadBulletinPaie(page,maperiode ,search);
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
	@RequestMapping(value = "/calculalenvers", method = RequestMethod.POST)
	public @ResponseBody LivreDePaie cherchBullpersonnel(@RequestParam(value="idPersonnel", required=true) Long id,@RequestParam(value="netApayer", required=true) Double valued,	Principal principal) {
		LivreDePaie livredePaie = null;
		// List<ContratPersonnel> personnelListTrt = new ArrayList<ContratPersonnel>();
		 ContratPersonnel ctratpersonnel=contratPersonnelRepository.findByPersonnelIdAndStatut(id, true);
		    PlanningConge     planningConge = planningCongeRepository.findByContratPersonnelAndStatut(ctratpersonnel,true);
		     TempEffectif tpeff;
			 tpeff=tempeffectifRepository.findByPersonnelAndPeriodePaie(ctratpersonnel.getPersonnel(), maperiode);
			 
			 Double[]  ancienete=calculAnciennete(ctratpersonnel.getCategorie().getSalaireDeBase(),ctratpersonnel.getPersonnel().getDateArrivee());
		    	double newancienete;
		    	if(ctratpersonnel.getAncienneteInitial()!=0) {
		    		 newancienete=ancienete[1] +ctratpersonnel.getAncienneteInitial();
		    	}else{
		    		newancienete=ancienete[1];
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
					}
					
				} 
			 
		 livredePaie= new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), nbpart ,op, ctratpersonnel.getCategorie().getSalaireDeBase(),5000d, ctratpersonnel.getIndemniteLogement(),0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet);
		 try { 
				int i=0;
					for( i = 0; i < 20; i++){	
						//(livredePaie.getNetPayer()==valued)
						 Double nouvSursal = (double) 0;Double nouvDiff= (double) 0;Double nouvMontantBrutImp= (double) 0;
						nouvMontantBrutImp=Math.rint(valued*livredePaie.getBrutImposable()/livredePaie.getNetPayer());
						nouvDiff=nouvMontantBrutImp-livredePaie.getBrutImposable();						
						nouvSursal=nouvDiff+livredePaie.getSursalaire();						
						livredePaie = new LivreDePaie(ctratpersonnel.getPersonnel().getMatricule(),ctratpersonnel.getPersonnel().getNom()+" "+ctratpersonnel.getPersonnel().getPrenom(), ctratpersonnel.getPersonnel().getNombrePart(), op, ctratpersonnel.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnel.getIndemniteLogement(), 0d, 0d,ctratpersonnel,null,maperiode,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet);
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
	
	
	
	
	@RequestMapping(value = "/jrBulletinPersonnel", method = RequestMethod.GET)
	public String ImprimerBulletinPersonnelCV(ModelMap modelMap,@RequestParam(value="idbul", required=true) Long idCV, HttpServletRequest request) {
			
		String view="livrepaie";
		List<BulletinPaie> listImprimebulletin=  new ArrayList<BulletinPaie>();
		BulletinPaie bulletin = new BulletinPaie();
		
		try {
			bulletin = bulletinPaieRepository.findById(idCV).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idCV));;
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		List<ImprimBulletinPaie> listImprimBulletinPaieEngt = new ArrayList<ImprimBulletinPaie>();
		List<ImprimBulletinPaie> listImprimBulletinPaieIndem = new ArrayList<ImprimBulletinPaie>();
	
		if(bulletin.getId() != null){
		
			view="bulletinpdf";
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
			imprimBulletinPaieIts.setLibelle("I.T.S");
			imprimBulletinPaieIts.setTaux(1.2D);					
			imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieIts.setRetenue(bulletin.getIts());
			listImprimBulletinPaie.add(imprimBulletinPaieIts);
			
			ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
			imprimBulletinPaieIgr.setLibelle("I.G.R");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
			listImprimBulletinPaie.add(imprimBulletinPaieIgr);
			
			ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
			imprimBulletinPaieCn.setLibelle("CONTRIBUTION NATIONALE");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieCn.setRetenue(bulletin.getCn());
			listImprimBulletinPaie.add(imprimBulletinPaieCn);
			
							
			ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
			imprimBulletinPaieCnps.setLibelle("RETRAITE CNPS ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			imprimBulletinPaieCn.setBase(bulletin.getBasecnps());		
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
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaiePF.setRetenuePatron(bulletin.getPrestationFamiliale());
			listImprimBulletinPaie.add(imprimBulletinPaiePF);
			
			ImprimBulletinPaie imprimBulletinPaieAC= new ImprimBulletinPaie();
			imprimBulletinPaieAC.setLibelle("ACCIDENT DE TRAVAIL");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieAC.setRetenuePatron(bulletin.getAccidentTravail());
			listImprimBulletinPaie.add(imprimBulletinPaieAC);
			
			ImprimBulletinPaie imprimBulletinPaieTA= new ImprimBulletinPaie();
			imprimBulletinPaieTA.setLibelle("FDFP -TAXE APPRENTISAGE ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieTA.setRetenuePatron(bulletin.getTa());
			listImprimBulletinPaie.add(imprimBulletinPaieTA);
			
			ImprimBulletinPaie imprimBulletinPaieFDFP= new ImprimBulletinPaie();
			imprimBulletinPaieFDFP.setLibelle("FDFP -TAXE A LA FPC ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieFDFP.setRetenuePatron(bulletin.getFpc());
			listImprimBulletinPaie.add(imprimBulletinPaieFDFP);
			
			
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

		/*	ImprimBulletinPaie imprimBulletinPaieMUGEFCI= new ImprimBulletinPaie();
			imprimBulletinPaieMUGEFCI.setLibelle("PRELEVEMENT MUGEFCI ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieMUGEFCI.setRetenue(bulletin.getMugefci());
			listImprimBulletinPaie.add(imprimBulletinPaieMUGEFCI);
			
			ImprimBulletinPaie imprimBulletinPaieSYNA= new ImprimBulletinPaie();
			imprimBulletinPaieSYNA.setLibelle("PRELEVEMENT SYNAONI ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieSYNA.setRetenue(bulletin.getSynaoni());
			listImprimBulletinPaie.add(imprimBulletinPaieSYNA);
			
			ImprimBulletinPaie imprimBulletinPaieREGUL= new ImprimBulletinPaie();
			imprimBulletinPaieREGUL.setLibelle("REGULARISATION ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieREGUL.setGain(bulletin.getRegularisation());
			listImprimBulletinPaie.add(imprimBulletinPaieREGUL);*/
			
			
		
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
		JRDataSource jrDatasource = null;
		
		listImprimebulletin.add(bulletin);
		//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
		//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
		List<Societe> malist=societeService.findtsmois();
		
	  	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath(malist.get(0).getUrlLogo()));
	  	modelMap.addAttribute("mtcumulSalnet",Utils.formattingAmount(bulletin.getCumulSalaireNet()));
		//modelMap.addAttribute("congacquis",tps*2.2*1.25);
		//modelMap.addAttribute("tpsPresence",String.valueOf(tps));
		GenericDataSource<BulletinPaie> dsStudent =  new GenericDataSource<BulletinPaie>(BulletinPaie.class);
		try {
			jrDatasource = dsStudent.create(null, listImprimebulletin);
			//System.out.println("----------- jr data source "+jrDatasource.toString());
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	//	oldCongeList = congeRepository.findTop1ByContratPersonnel(bulletin.getContratPersonnel());
		//if(oldCongeList != null && oldCongeList.isEmpty()) // Si le resultat est null :: 
			// Alors on cherche la date d'arrive du personnel
			       
			       
	//	else
	//		dateRetourDernierConge = oldCongeList.get(0).getDateRetour();
			
		
		  String url = request.getSession().getServletContext().getRealPath(DeploimentUtils.RecupSubReportChem("../reports"));
		  System.out.println("----------- url url url "+url);
		  String url1 = DeploimentUtils.RecupSubReportChem(url);
		  System.out.println("----------- url1 url1 url1 "+url1);
		  modelMap.addAttribute("SUBREPORT_DIR", url1);
		  modelMap.addAttribute("SUBREPORT_DIR1", url1);
		  modelMap.addAttribute("SUBREPORT_DIR2", url1);
		/*modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
		modelMap.addAttribute("SUBREPORT_DIR1", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
		modelMap.addAttribute("SUBREPORT_DIR2", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));*/
		modelMap.addAttribute("datasource", jrDatasource);
		modelMap.addAttribute("format", "pdf");
		}
	
	
	return view; //mav;
	
	
		
	}
	
	
	
	@RequestMapping(value = "/bulletinMoisPersonnel", method = RequestMethod.GET)
	public String postAfficherBulletin(ModelMap modelMap,@RequestParam(value="idbul", required=true) Long idCV, HttpServletRequest request) {
		
		
			
		String view="livrepaie";
		
		
	
			PeriodePaie  periodePaie = periodePaieService.findPeriodePaie(idCV);
			System.out.println("########## periode : "+periodePaie.getMois().getMois());
			
		List<BulletinPaie> listImprimebulletin=  new ArrayList<BulletinPaie>();
		
		List<BulletinPaie> listBull = new ArrayList<BulletinPaie>();
		//listBull = bulletinService.findAllBulletinByPeriodePaie(periodePaie);
		listBull = bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie, true);
		System.out.println("nb bulletin : "+listBull.size());
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
				imprimBulletinPaieIts.setLibelle("I.T.S");
				imprimBulletinPaieIts.setTaux(1.2D);
				imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieIts.setRetenue(bulletin.getIts());
				listImprimBulletinPaie.add(imprimBulletinPaieIts);

				ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
				imprimBulletinPaieIgr.setLibelle("I.G.R");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
				listImprimBulletinPaie.add(imprimBulletinPaieIgr);

				ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
				imprimBulletinPaieCn.setLibelle("CONTRIBUTION NATIONALE");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieCn.setRetenue(bulletin.getCn());
				listImprimBulletinPaie.add(imprimBulletinPaieCn);


				ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
				imprimBulletinPaieCnps.setLibelle("RETRAITE CNPS ");
				//imprimBulletinPaieCn.setTaux(1.2D);
				imprimBulletinPaieCn.setBase(bulletin.getBasecnps());
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
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaiePF.setRetenuePatron(bulletin.getPrestationFamiliale());
				listImprimBulletinPaie.add(imprimBulletinPaiePF);

				ImprimBulletinPaie imprimBulletinPaieAC= new ImprimBulletinPaie();
				imprimBulletinPaieAC.setLibelle("ACCIDENT DE TRAVAIL");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieAC.setRetenuePatron(bulletin.getAccidentTravail());
				listImprimBulletinPaie.add(imprimBulletinPaieAC);

				ImprimBulletinPaie imprimBulletinPaieTA= new ImprimBulletinPaie();
				imprimBulletinPaieTA.setLibelle("FDFP -TAXE APPRENTISAGE ");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieTA.setRetenuePatron(bulletin.getTa());
				listImprimBulletinPaie.add(imprimBulletinPaieTA);

				ImprimBulletinPaie imprimBulletinPaieFDFP= new ImprimBulletinPaie();
				imprimBulletinPaieFDFP.setLibelle("FDFP -TAXE A LA FPC ");
				//imprimBulletinPaieCn.setTaux(1.2D);
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieFDFP.setRetenuePatron(bulletin.getFpc());
				listImprimBulletinPaie.add(imprimBulletinPaieFDFP);


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

		/*	ImprimBulletinPaie imprimBulletinPaieMUGEFCI= new ImprimBulletinPaie();
			imprimBulletinPaieMUGEFCI.setLibelle("PRELEVEMENT MUGEFCI ");
			//imprimBulletinPaieCn.setTaux(1.2D);
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieMUGEFCI.setRetenue(bulletin.getMugefci());
			listImprimBulletinPaie.add(imprimBulletinPaieMUGEFCI);

			ImprimBulletinPaie imprimBulletinPaieSYNA= new ImprimBulletinPaie();
			imprimBulletinPaieSYNA.setLibelle("PRELEVEMENT SYNAONI ");
			//imprimBulletinPaieCn.setTaux(1.2D);
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieSYNA.setRetenue(bulletin.getSynaoni());
			listImprimBulletinPaie.add(imprimBulletinPaieSYNA);

			ImprimBulletinPaie imprimBulletinPaieREGUL= new ImprimBulletinPaie();
			imprimBulletinPaieREGUL.setLibelle("REGULARISATION ");
			//imprimBulletinPaieCn.setTaux(1.2D);
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
			imprimBulletinPaieREGUL.setGain(bulletin.getRegularisation());
			listImprimBulletinPaie.add(imprimBulletinPaieREGUL);*/



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
				
//				ImprimBulletinPaie imprimBulletinPaieSB = new ImprimBulletinPaie();
//				imprimBulletinPaieSB.setLibelle("SALAIRE DE BASE CATEGORIE");
//				imprimBulletinPaieSB.setTaux(bulletin.getJourTravail());
//				//calcul de la base
//			//	if(bulletin.getJourTravail() == 30){
//				imprimBulletinPaieSB.setBase(bulletin.getSalairbase());
//				imprimBulletinPaieSB.setGain(bulletin.getSalairbase());
//			//	}else{
//				//	imprimBulletinPaie.setBase(arrondi((salaireCategoriel*30)/bulletin.getJourTravail(), nbArrondi));
//
//				//	}
//				  //    imprimBulletinPaie.setGain(salaireCategoriel);
//				 listImprimBulletinPaie.add(imprimBulletinPaieSB);
//
//				ImprimBulletinPaie imprimBulletinPaieSs = new ImprimBulletinPaie();
//				imprimBulletinPaieSs.setLibelle("SURSALAIRE");
//				imprimBulletinPaieSs.setTaux(bulletin.getJourTravail());
//					//calcul de la base
//				//	if(bulletin.getJourTravail() == 30){
//				imprimBulletinPaieSs.setBase(bulletin.getSursalaire());
//				imprimBulletinPaieSs.setGain(bulletin.getSursalaire());
//				listImprimBulletinPaie.add(imprimBulletinPaieSs);
//
//
//				ImprimBulletinPaie imprimBulletinPaieanc = new ImprimBulletinPaie();
//				imprimBulletinPaieanc.setLibelle("PRIME D'ANCIENNETE");
//				//imprimBulletinPaieSs.setTaux(bulletin.getJourTravail());
//					//calcul de la base
//				//	if(bulletin.getJourTravail() == 30){
//				imprimBulletinPaieanc.setBase(bulletin.getPrimeanciennete());
//				imprimBulletinPaieanc.setGain(bulletin.getPrimeanciennete());
//				listImprimBulletinPaie.add(imprimBulletinPaieanc);
//
//				ImprimBulletinPaie imprimBulletinPaiePanc = new ImprimBulletinPaie();
//				imprimBulletinPaiePanc.setLibelle("INDEMNITE DE RESIDENCE");
//				imprimBulletinPaiePanc.setTaux(bulletin.getJourTravail());
//
//				imprimBulletinPaiePanc.setBase(bulletin.getIndemnitelogement());
//				imprimBulletinPaiePanc.setGain(bulletin.getIndemnitelogement());
//				listImprimBulletinPaie.add(imprimBulletinPaiePanc);
//
//				ImprimBulletinPaie imprimBulletinPaieLog = new ImprimBulletinPaie();
//				imprimBulletinPaieLog.setLibelle("IND. DE TRANSPORT IMP ");
//				imprimBulletinPaieLog.setTaux(bulletin.getJourTravail());
//					//calcul de la base
//				//	if(bulletin.getJourTravail() == 30){
//				imprimBulletinPaieLog.setBase(bulletin.getIndemniteTransportImp());
//				imprimBulletinPaieLog.setGain(bulletin.getIndemniteTransportImp());
//				listImprimBulletinPaie.add(imprimBulletinPaieLog);
//
//				ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
//				imprimBulletinPaieDivImp.setLibelle("AUTRES IMPOSABLES ");
//				imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
//					//calcul de la base
//				//	if(bulletin.getJourTravail() == 30){
//				imprimBulletinPaieDivImp.setBase(bulletin.getAutreImposable());
//				imprimBulletinPaieDivImp.setGain(bulletin.getAutreImposable());
//				listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
//
//				ImprimBulletinPaie imprimBulletinPaieBrutImpos = new ImprimBulletinPaie();
//				imprimBulletinPaieBrutImpos.setLibelle("***** Salaire Brut Imposable ******");
//				//imprimBulletinPaieLog.setTaux(0D);
//					//calcul de la base
//				//	if(bulletin.getJourTravail() == 30){
//				//imprimBulletinPaieLog.setBase(bulletin.getIndemnitelogement());
//				imprimBulletinPaieBrutImpos.setGain(bulletin.getBrutImposable());
//				listImprimBulletinPaie.add(imprimBulletinPaieBrutImpos);
//
//
//			/*	ImprimBulletinPaie imprimBulletinPaieSujet = new ImprimBulletinPaie();
//				imprimBulletinPaieSujet.setLibelle("SUJETION");
//				imprimBulletinPaieSujet.setTaux(bulletin.getJourTravail());
//				imprimBulletinPaieSujet.setGain(bulletin.getSujetion());
//				listImprimBulletinPaie.add(imprimBulletinPaieSujet);*/
//
//				ImprimBulletinPaie imprimBulletinPaieTransport = new ImprimBulletinPaie();
//				imprimBulletinPaieTransport.setLibelle("IND. DE TRANSPORT NON IMPOS");
//				//imprimBulletinPaieTransport.setTaux(bulletin.getJourTravail());
//				imprimBulletinPaieTransport.setGain(bulletin.getIndemniteTransport());
//				listImprimBulletinPaie.add(imprimBulletinPaieTransport);
//
//
//				ImprimBulletinPaie imprimBulletinPaieRESP= new ImprimBulletinPaie();
//				imprimBulletinPaieRESP.setLibelle("AUTRES NON IMPOSABLES ");
//				//imprimBulletinPaieRESP.setTaux(bulletin.getJourTravail());
//				imprimBulletinPaieRESP.setGain(bulletin.getAutreNonImposable());
//				listImprimBulletinPaie.add(imprimBulletinPaieRESP);
//
//		/*		ImprimBulletinPaie imprimBulletinPaieREPRES= new ImprimBulletinPaie();
//				imprimBulletinPaieREPRES.setLibelle("IND. DE REPRESENTATION");
//				//imprimBulletinPaieRESP.setTaux(bulletin.getJourTravail());
//				imprimBulletinPaieREPRES.setGain(bulletin.getIndemniteRepresentation());
//				listImprimBulletinPaie.add(imprimBulletinPaieREPRES);
//
//				ImprimBulletinPaie imprimBulletinPaieDivG = new ImprimBulletinPaie();
//				imprimBulletinPaieDivG.setLibelle("DIVERS GAINS NON IMPOS");
//				imprimBulletinPaieDivG.setTaux(bulletin.getJourTravail());
//				imprimBulletinPaieDivG.setGain(bulletin.getDiversgains());
//				listImprimBulletinPaie.add(imprimBulletinPaieDivG);*/
//
//
//				ImprimBulletinPaie imprimBulletinPaieSalBrutNon	 = new ImprimBulletinPaie();
//				imprimBulletinPaieSalBrutNon.setLibelle("***** Salaire Brut Non Imposable ******");
//				imprimBulletinPaieSalBrutNon.setTaux(bulletin.getJourTravail());
//				imprimBulletinPaieSalBrutNon.setGain(bulletin.getBrutNonImposable());
//				listImprimBulletinPaie.add(imprimBulletinPaieSalBrutNon);
//
//
//
//
//
//				ImprimBulletinPaie imprimBulletinPaieIts = new ImprimBulletinPaie();
//				imprimBulletinPaieIts.setLibelle("I.T.S");
//				imprimBulletinPaieIts.setTaux(1.2D);
//				imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieIts.setRetenue(bulletin.getIts());
//				listImprimBulletinPaie.add(imprimBulletinPaieIts);
//
//				ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
//				imprimBulletinPaieIgr.setLibelle("I.G.R");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
//				listImprimBulletinPaie.add(imprimBulletinPaieIgr);
//
//				ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
//				imprimBulletinPaieCn.setLibelle("CONTRIBUTION NATIONALE");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieCn.setRetenue(bulletin.getCn());
//				listImprimBulletinPaie.add(imprimBulletinPaieCn);
//
//
//				ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
//				imprimBulletinPaieCnps.setLibelle("RETRAITE CNPS ");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				imprimBulletinPaieCn.setBase(bulletin.getBasecnps());
//				imprimBulletinPaieCnps.setRetenue(bulletin.getCnps());
//				listImprimBulletinPaie.add(imprimBulletinPaieCnps);
//
//				ImprimBulletinPaie imprimBulletinPaieIS= new ImprimBulletinPaie();
//				imprimBulletinPaieIS.setLibelle("IMPOTS/SALAIRE LOCAL");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieIS.setRetenuePatron(bulletin.getIts());
//				listImprimBulletinPaie.add(imprimBulletinPaieIS);
//
//				ImprimBulletinPaie imprimBulletinPaieCnpsPATRON = new ImprimBulletinPaie();
//				imprimBulletinPaieCnpsPATRON.setLibelle("RETRAITE CNPS/PART PATRONAL ");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				imprimBulletinPaieCnpsPATRON.setBase(bulletin.getBasecnps());
//				imprimBulletinPaieCnpsPATRON.setRetenuePatron(bulletin.getRetraite());
//				listImprimBulletinPaie.add(imprimBulletinPaieCnpsPATRON);
//
//
//				ImprimBulletinPaie imprimBulletinPaiePF= new ImprimBulletinPaie();
//				imprimBulletinPaiePF.setLibelle("PRESTATION FAMILIALE");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaiePF.setRetenuePatron(bulletin.getPrestationFamiliale());
//				listImprimBulletinPaie.add(imprimBulletinPaiePF);
//
//				ImprimBulletinPaie imprimBulletinPaieAC= new ImprimBulletinPaie();
//				imprimBulletinPaieAC.setLibelle("ACCIDENT DE TRAVAIL");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieAC.setRetenuePatron(bulletin.getAccidentTravail());
//				listImprimBulletinPaie.add(imprimBulletinPaieAC);
//
//				ImprimBulletinPaie imprimBulletinPaieTA= new ImprimBulletinPaie();
//				imprimBulletinPaieTA.setLibelle("FDFP -TAXE APPRENTISAGE ");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieTA.setRetenuePatron(bulletin.getTa());
//				listImprimBulletinPaie.add(imprimBulletinPaieTA);
//
//				ImprimBulletinPaie imprimBulletinPaieFDFP= new ImprimBulletinPaie();
//				imprimBulletinPaieFDFP.setLibelle("FDFP -TAXE A LA FPC ");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieFDFP.setRetenuePatron(bulletin.getFpc());
//				listImprimBulletinPaie.add(imprimBulletinPaieFDFP);
//
//
//				ImprimBulletinPaie imprimBulletinPaieAMAO= new ImprimBulletinPaie();
//				imprimBulletinPaieAMAO.setLibelle("AUTRES PRELEVEMENTS ");
//				//imprimBulletinPaieCn.setTaux(1.2D);
//				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//				imprimBulletinPaieAMAO.setRetenue(bulletin.getAvanceetacompte());
//				listImprimBulletinPaie.add(imprimBulletinPaieAMAO);
//
				bulletin.setListImprimBulletinPaie(listImprimBulletinPaie);
				//bulletin.setListImprimBulletinPaieEngagement(listImprimBulletinPaieEngt);
				//bulletin.setListImprimBulletinPaieIndemniteNonImp(listImprimBulletinPaieIndem);
				//Date dateRetourDernierConge = null;
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
			listImprimebulletin.add(bulletin);
			}
			
			//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
			//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
			JRDataSource jrDatasource = null;
			GenericDataSource<BulletinPaie> dsStudent =  new GenericDataSource<BulletinPaie>(BulletinPaie.class);
			try {
				jrDatasource = dsStudent.create(null, listImprimebulletin);
				//System.out.println("----------- jr data source "+jrDatasource.toString());
			} catch (JRException e) {
				e.printStackTrace();
			}
			//"resources/front-end/images/CGECI.jpg" 
			Societe mysociete=null;
			List<Societe> malist=societeService.findtsmois();
			
		  	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath(malist.get(0).getUrlLogo()));
			//	sting
			//modelMap.addAttribute("logo",request.getSession().getServletContext().getRealPath("resources/front-end/images/logodefis1.png"));
			
			
			/*String url = request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech());
			  String url1 = DeploimentUtils.RecupSubReportChem(url);
			  modelMap.addAttribute("SUBREPORT_DIR", url1);
			  modelMap.addAttribute("SUBREPORT_DIR1", url1);
			  modelMap.addAttribute("SUBREPORT_DIR2", url1);*/
			modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
			modelMap.addAttribute("SUBREPORT_DIR1", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
			modelMap.addAttribute("SUBREPORT_DIR2", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
			modelMap.addAttribute("datasource", jrDatasource);
			modelMap.addAttribute("format", "pdf");
			}
		
		
		return view; //mav;
		
		
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
