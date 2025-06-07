package com.nectux.mizan.hyban.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

import com.nectux.mizan.hyban.paie.entity.Conge;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.paie.service.CongeService;
import com.nectux.mizan.hyban.parametrages.entity.*;
import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.repository.TypeContratRepository;
import com.nectux.mizan.hyban.parametrages.service.*;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.dto.PersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.entity.Service;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.DocumentTypeRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.StorageLocationRepository;
import com.nectux.mizan.hyban.personnel.service.ContratPersonnelService;
import com.nectux.mizan.hyban.personnel.service.FonctionService;
import com.nectux.mizan.hyban.personnel.service.PersonnelService;
import com.nectux.mizan.hyban.personnel.service.ServiceService;
import com.nectux.mizan.hyban.rh.absences.service.AbsencesService;
import com.nectux.mizan.hyban.rh.carriere.service.PosteService;
import com.nectux.mizan.hyban.rh.carriere.service.PromotionService;
import com.nectux.mizan.hyban.rh.carriere.service.SanctionService;
import com.nectux.mizan.hyban.utils.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/personnels")
public class PersonnelController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonnelController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PersonnelService personnelService;
	@Autowired private ExerciceRepository exerciceRepository;
	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	@Autowired
	private StorageLocationRepository storageLocationRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private MoisService moisService;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private ServiceService serviceService;
	@Autowired private CongeService congeService;
	@Autowired private PlanningCongeService planningCongeService;
	@Autowired private ContratPersonnelService contratPersonnelService;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private TypeContratRepository typeContratRepository;
        //Carriere
        @Autowired private SanctionService sanctionService;
		@Autowired private FonctionService fonctionService;
        @Autowired private PosteService posteService;
        @Autowired private PromotionService promotionService;
        @Autowired private PeriodePaieService periodePaieService;
        @Autowired private BanqueService banqueService;
        @Autowired private AbsencesService absenceService;
        @Autowired private PeriodePaieRepository periodePaieRepository;
        @Autowired private SocieteService societeService;
        @Autowired private UtilisateurRoleService utilisateurRoleService;
	public MethodsShared methodsShared;
	public DifferenceDate differenceDate;
	@RequestMapping("/personnel")
    public String viewPersonnel(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeEmployers", "active");
		modelMap.addAttribute("blockEmployer", "block");
		modelMap.addAttribute("activeEmployer", "active");
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-group");
		modelMap.addAttribute("littleTitle", "Personnel");
		modelMap.addAttribute("bigTitle", "Personnel");

		//modelMap.addAttribute("listeSanctions", sanctionService.getSanctions()); 

                modelMap.addAttribute("listePostes", fonctionService.findFonctions());
            modelMap.addAttribute("listeSanctions", sanctionService.getSanctions());
            modelMap.addAttribute("listePromotions", fonctionService.findFonctions());
            modelMap.addAttribute("listeBanques", banqueService.getBanques());
            modelMap.addAttribute("listeAbsences", absenceService.getAbsences());
			modelMap.addAttribute("listeDocuments",documentTypeRepository.findAll());
			modelMap.addAttribute("listeStockages",storageLocationRepository.findAll());
            PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
    	    if(periodePaie != null){
    	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
    			modelMap.addAttribute("activeMoisId", periodePaie.getId());
    			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
    	    }
    	    Societe mysociete=null;
  		  List<Societe> malist=societeService.findtsmois();
  		if(malist.size()>0)
		{	mysociete=malist.get(0);			
  			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "personnels";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listpersonneljson", method = RequestMethod.GET)
	public @ResponseBody PersonnelDTO getPersonnelListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "nom");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		PersonnelDTO personnelDTO = new PersonnelDTO();
		if(search == null || search == "")
			personnelDTO = personnelService.loadPersonnel(pageRequest);
		else
			personnelDTO = personnelService.loadPersonnel(pageRequest, search,search,search);
		
		return personnelDTO;
	}
	
	/*@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listpersctratjson", method = RequestMethod.GET)
	public @ResponseBody PersonnelDTO getPersonnelctratListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "nom");
		
		PersonnelDTO personnelDTO = new PersonnelDTO();
		
			 List<Personnel> personnelList = new ArrayList<Personnel>();
		  List<Personnel> personnelListTrt = new ArrayList<Personnel>();
		  ContratPersonnel ctratpersonnel = new ContratPersonnel();
		//  PeriodePaie  periodePaieActif=periodePaieRepository.findOne(idPeriode);
		//  personnel=personnelRepository.findOne(idPers);	
		// verif contrat actif
		  personnelList=personnelRepository.findAll();
		  for(int i = 0; i < personnelList.size(); i++){
		  ctratpersonnel=contratPersonnelRepository.findByPersonnelId(personnelList.get(i).getId());
		  	if(ctratpersonnel!=null){	
		  		if(ctratpersonnel.getStatut()==true)
		         personnelListTrt.add(ctratpersonnel.getPersonnel());
		  	}
		  }
			
			personnelDTO.setRows(personnelListTrt);
		else
			personnelDTO = personnelService.loadPersonnel(pageRequest, search);
		
		return personnelDTO;
	}*/
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/choisirpersonnel", method = RequestMethod.POST)
	public @ResponseBody Personnel selectPersonnel(@RequestParam(value="id", required=true) Long id) { 
		
		return personnelService.findPersonnel(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/choisirpersonnelpret", method = RequestMethod.POST)
	public @ResponseBody Personnel selectPersonnelpret(@RequestParam(value="id", required=true) Long id,ModelMap modelmap) { 
		Personnel personne = new Personnel();
		personne= personnelService.findPersonnel(id);
		modelmap.addAttribute("personnel", personne);
		 return personne;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerpersonnel", method = RequestMethod.POST)
	public @ResponseBody ContratPersonnelDTO saveMersonnel(@RequestParam(value="id", required=false) Long id,  
													@RequestParam(value="nom", required=false) String nom, 
													@RequestParam(value="prenom", required=false) String prenom, 
													@RequestParam(value="nationalite", required=false) Long nationalite, 
													@RequestParam(value="service", required=false) Long service, 
													@RequestParam(value="categorie", required=false) Long categorie, 
													@RequestParam(value="fonction", required=false) Long fonction, 
													@RequestParam(value="typecontrat", required=false) Long typeContrat, 
													@RequestParam(value="matricule", required=false) String matricule, 
													@RequestParam(value="sexe", required=false) String sexe, 
													@RequestParam(value="datenaissance", required=false) String dateNaissance, 
													@RequestParam(value="lieunaissance", required=false) String lieuNaissance, 
													@RequestParam(value="email", required=false) String email, 
													@RequestParam(value="residence", required=false) String residence, 
													@RequestParam(value="situationmatrimoniale", required=false) int situationMatrimoniale, 
													@RequestParam(value="nombreenfant", required=false) int nombreEnfant, 
													@RequestParam(value="datearrivee", required=false) String dateArrivee, 
													@RequestParam(value="numerocnps", required=false) String numeroCNPS, 
													@RequestParam(value="adresse", required=false) String adresse, 
													@RequestParam(value="datedebut", required=false) String dateDebut, 
													@RequestParam(value="datefin", required=false) String dateFin,
													@RequestParam(value="modepaiement", required=false) String modePaiement, 
													@RequestParam(value="banque", required=false) Long idBanque, 
													@RequestParam(value="numerocompte", required=false) String numeroCompte, 
													@RequestParam(value="numeroguichet", required=false) String numeroGuichet, 
													@RequestParam(value="rib", required=false) String rib, 
													@RequestParam(value="salairenet", required=false) Double salaireNet, 
													@RequestParam(value="indemnitelogement", required=false) Double indemnitelogement, 
													@RequestParam(value="ancienneteinitial", required=false) int ancienneteInitial,												
													@RequestParam(value="carec", required=false) Boolean carec,												
													@RequestParam(value="typeEmp", required=false) String typemp,
													@RequestParam(value="telephone", required=false) String telephone,
													@RequestParam(value="situationMedaille", required=false) int situationMedaille,
													@RequestParam(value="situationEmploie", required=false) int situationEmploie,
													@RequestParam(value="dateRetourcg", required=false) String dateRetourcg,
													@RequestParam(value="sursalaire", required=false) Double sursalaire, 
													@RequestParam(value="indemnitetransport", required=false) Double indemniteTransport,
													@RequestParam(value="indemniteResp", required=false) Double indemniteRespons,													
													@RequestParam(value="indemniteRepresent", required=false) Double  indemniteRepresent) { 
		
	/*	String uploadPath =  request.getSession().getServletContext().getRealPath("") + File.separator + "resources\\personnel\\photo";
		System.out.println(">>> CHEMIN UPLOAD >>>" + uploadPath);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		// GESTION PHOTO
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();
		 String fileName ="";
		if(((MultipartRequest) uploadfile).getFileNames()!=null){
			
			
		
		Iterator<String> itr =  ((MultipartRequest) uploadfile).getFileNames();
		 
	     MultipartFile mpf = ((MultipartRequest) uploadfile).getFile(itr.next());
	     //System.out.println(mpf.getOriginalFilename() +" uploaded!");
	     
	     fileName= mpf.getOriginalFilename();
	     String directory = uploadPath;
	     String filepath = Paths.get(directory, fileName).toString();
  
	     // Save the file locally
	     BufferedOutputStream stream;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	
				stream.write(mpf.getBytes());
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
			
		
		}*/
		return personnelService.save(id, nom,prenom, nationalite, service, categorie, fonction, typeContrat, matricule, 
				 sexe, dateNaissance, lieuNaissance, email, residence, situationMatrimoniale, nombreEnfant, 
				 dateArrivee, numeroCNPS, adresse, dateDebut, dateFin, salaireNet,indemnitelogement,
				 modePaiement, idBanque, numeroCompte,numeroGuichet, rib, ancienneteInitial,carec,typemp,telephone,situationMedaille,situationEmploie,dateRetourcg,indemniteRespons,indemniteRepresent,indemniteTransport,sursalaire);		
		
	

		}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/modifierpersonnel", method = RequestMethod.POST)
	public @ResponseBody PersonnelDTO updatePersonnel(@RequestParam(value="id", required=false) Long id,
															@RequestParam(value="nationalite", required=false) Long nationalite, 
															@RequestParam(value="nom", required=false) String nom, 
															@RequestParam(value="prenom", required=false) String prenom, 
															@RequestParam(value="service", required=false) Long service, 
															@RequestParam(value="matricule", required=false) String matricule, 
															@RequestParam(value="sexe", required=false) String sexe, 
															@RequestParam(value="datenaissance", required=false) String dateNaissance, 
															@RequestParam(value="lieunaissance", required=false) String lieuNaissance, 
															@RequestParam(value="email", required=false) String email, 
															@RequestParam(value="residence", required=false) String residence, 
															@RequestParam(value="situationmatrimoniale", required=false) int situationMatrimoniale, 
															@RequestParam(value="nombreenfant", required=false) int nombreEnfant, 
															@RequestParam(value="datearrivee", required=false) String dateArrivee, 														
															@RequestParam(value="numerocnps", required=false) String numeroCNPS, 
															@RequestParam(value="adresse", required=false) String adresse, 															
															@RequestParam(value="modepaiement", required=false) String modePaiement, 
															@RequestParam(value="banque", required=false) Long idBanque,
															@RequestParam(value="numerocompte", required=false) String numeroCompte, 
															@RequestParam(value="numeroguichet", required=false) String numeroGuichet, 
															@RequestParam(value="rib", required=false) String rib, 
															@RequestParam(value="statut", required=false) Boolean statut, 
															@RequestParam(value="carec", required=false) Boolean carec,
															@RequestParam(value="typeEmp", required=false) String typemp,
															@RequestParam(value="telephone", required=false) String telephone,
															@RequestParam(value="situationMedaille", required=false) int situationMedaille,
															@RequestParam(value="situationEmploie", required=false) int situationEmploie,
													       @RequestParam(value="dateRetourcg", required=false) String dateRetourcg) throws Exception {
		String sdate;
		if(dateRetourcg!=null){			
		     sdate=dateRetourcg;
		}else{		  
		    sdate=dateArrivee;
		}
		System.out.println("********************jourRET))))))"+dateRetourcg);
		System.out.println("********************jourARR))))))"+dateArrivee);
		System.out.println("********************jourARR))))))"+sdate);
		String jour = sdate.substring(0, 2); 
	     String mois= sdate.substring(3, 5); 
		String annee = sdate.substring(6, 10);
		System.out.println("****************jour  MOIS annnee)))))))))))))))))))))"+jour+"MOIS"+mois+"kpiulkhhg"+annee);
		
	    Date dDate=Utils.stringToDate(sdate, "dd/MM/yyyy");
	
		List<PeriodePaie> listper=periodePaieRepository.findByAnneeAnnee(annee);
		System.out.println("****************************************************oooooooooooooo"+listper.size());
		PeriodePaie perPaiev=new PeriodePaie();
		for(PeriodePaie perPaie : listper){
			if(perPaie.getMois().getId()==Long.valueOf(mois)){
			 perPaiev=perPaie;				
				System.out.println("****************************************************ooooooooooooooMois"+perPaie.getMois().getId());		 
			}
		}
		
		return personnelService.save(id,  nom,  prenom,  nationalite,service, matricule, sexe, dateNaissance,  lieuNaissance,  email, 
				residence, situationMatrimoniale,  nombreEnfant, dateArrivee, numeroCNPS, adresse, statut, 
				 modePaiement,idBanque, numeroCompte, numeroGuichet,  rib,carec,typemp,telephone,situationMedaille, situationEmploie, dateRetourcg);
		
		
	}
	
	/*@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/editerdonneespaiepersonnel", method = RequestMethod.POST)
	public @ResponseBody PersonnelDTO editerDonneesPaiePersonnel(@RequestParam(value="id", required=false) Long id,
															@RequestParam(value="situationmatrimoniale", required=false) int situationMatrimoniale, 
															@RequestParam(value="nombreenfant", required=false) int nombreEnfant, 
															@RequestParam(value="statut", required=false) String statut) { 
		
		return personnelService.save(id, situationMatrimoniale, nombreEnfant, statut);
	}*/
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/editerpersonnel", method = RequestMethod.POST)
	public @ResponseBody PersonnelDTO updatePersonnel(@RequestParam(value="idPersonnel", required=false) Long idPersonnel,
															@RequestParam(value="situationmatrimoniale", required=false) int situationMatrimoniale, 
															@RequestParam(value="nombreenfant", required=false) int nombreEnfant,  
															@RequestParam(value="statut", required=false) Boolean statut) { 
		
		
		   return personnelService.save(idPersonnel, situationMatrimoniale, nombreEnfant, statut);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchpersonnel", method = RequestMethod.GET)
	public @ResponseBody PersonnelDTO cherchPersonnel(@RequestParam(value="idPersonnel", required=false) Long idPersonnel) { 
		
		return personnelService.findPersonneldto(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchpersonMatricule", method = RequestMethod.GET)
	public @ResponseBody PersonnelDTO cherchpersonMatricule(@RequestParam(value="matri", required=false) String matri) { 
		
		return personnelService.findByMatricules(matri);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchpersonNumCnps", method = RequestMethod.GET)
	public @ResponseBody PersonnelDTO cherchpersonNumCnps(@RequestParam(value="cnps", required=false) String cnps) { 
		
		return personnelService.findByNumeroCnpss(cnps);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerpersonnel", method = RequestMethod.POST)
	public @ResponseBody PersonnelDTO deleteCategory(@RequestParam(value="id", required=true) Long id) {
		
		PersonnelDTO personnelDTO = new PersonnelDTO();
		personnelDTO.setResult(personnelService.delete(id));
		return personnelDTO;
	}

//	@ResponseStatus(HttpStatus.OK)
//	@RequestMapping(value = "/effectifPersonnel", method = RequestMethod.GET)
//	public @ResponseBody 	PrintLsDTO effectifPersonnelCategory(@RequestParam(value="id", required=true) Long id) {
//
//		return  personnelService.RechercherListPersonnelParAnnee(id);
//	}
	@RequestMapping(value = "/effectifPersonnel", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUse(@RequestParam(value="id", required=false) Long id, ModelMap modelMap) throws IOException {
		Exercice anneeRecup = new Exercice();
		if(id != null){
			try {
				anneeRecup = exerciceRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" annee :::::: "+anneeRecup.toString());
		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();
		if(anneeRecup.getId() != null){
			Integer anneeEnCours = Integer.valueOf(anneeRecup.getAnnee());
			for (int i = 0; i < 5; i++) {
				//System.out.println(" annee :::::: "+anneeEnCours);

				String annee = String.valueOf(anneeEnCours);
				java.sql.Date dateAnDeb = null;
				java.sql.Date dateAnFin = null;
				Date dateAnDeb1 = null;
				Date dateAnFin1 = null;
				try {
					dateAnDeb1 =Utils.stringToDate("01/01/"+annee, "dd/MM/yyyy");
					dateAnFin1 = Utils.stringToDate("31/12/"+annee, "dd/MM/yyyy");
				     dateAnDeb = new java.sql.Date(dateAnDeb1.getTime());
					 dateAnFin = new java.sql.Date(dateAnFin1.getTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<Personnel> listPersonnelHomme = new ArrayList<Personnel>();
				try{
					listPersonnelHomme = personnelService.RechercherListPersonnelParAnnee(dateAnDeb, dateAnFin, "Masculin");
					System.out.println("list Personnel : "+listPersonnelHomme.size());
				} catch(Exception ex){
					logger.error(ex.getMessage());
					logger.error(Arrays.toString(ex.getStackTrace()));
					logger.error("une erreur a ete dectectee lors de la suppression du categorie Personnel");
				}

				List<Personnel> listPersonnelFemme = new ArrayList<Personnel>();
				try{
					listPersonnelFemme = personnelService.RechercherListPersonnelParAnnee( dateAnDeb, dateAnFin, "Feminin");
					System.out.println("list Personnel femme : "+listPersonnelFemme.size());

				} catch(Exception ex){

				}
				PrintLs printDTO = new PrintLs();
				printDTO.setI1(listPersonnelHomme.size());
				printDTO.setS1(annee);
				printDTO.setTitle1("Homme");
				printDTO.setI2(listPersonnelFemme.size());
				printDTO.setS2(annee);
				printDTO.setTitle2("Femme");
				listPrintDTO.add(printDTO);

				anneeEnCours = anneeEnCours - 1;
			}

		}
		return toJson(listPrintDTO);
	}

	@RequestMapping(value = "/stat/conge", method = RequestMethod.GET)
	@ResponseBody
	public String statConge( @RequestParam(value="id", required=false) Long aid, ModelMap modelMap, Principal principal) throws IOException {


		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();

		Exercice annee = new Exercice();
		if(aid != null){
			try {
				annee = exerciceRepository.findById(aid) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + aid));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" annee :::::: "+annee.toString());
		if(annee.getId() != null){

			List<Mois> listMois = new ArrayList<Mois>();
			try {
				listMois = moisService.findtsmois();
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (Mois mois : listMois) {

				//Recherche de la liste des congées d'un mois
				List<Conge> listconge = new ArrayList<Conge>();
				try {
					listconge = congeService.rechercherByAgenceMoisAnnee( mois, annee);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("nb conge mois : "+mois.getMois()+" annee : "+annee.getAnnee() +" ::::: "+listconge.size());

				//Recherche de la liste des plagning de congé d'un mois
				List<PlanningConge> listPlanning = new ArrayList<PlanningConge>();
				try {
					listPlanning = planningCongeService.rechercherByAgenceMoisAnnee(mois, annee);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("nb conge mois : "+mois.getMois()+" annee : "+annee.getAnnee() +" ::::: "+listconge.size());

				PrintLs printDTO = new PrintLs();
				printDTO.setI1(listconge.size());
				printDTO.setS1(mois.getMois());
				printDTO.setTitle1(annee.getAnnee());
				printDTO.setI2(listPlanning.size());
				printDTO.setS2(mois.getMois());
				printDTO.setTitle2(annee.getAnnee());

				listPrintDTO.add(printDTO);
			}

		}


		//return new ModelAndView("redirect:../../../rhp/personnel/processing/listpersonnal?uid="+utilisateurCourant.getUid());
		return toJson(listPrintDTO);
	}

	@RequestMapping(value = "/stat/effectifPersonnelRetraite", method = RequestMethod.GET)
	@ResponseBody
	public String SatRetraite( @RequestParam(value="id", required=false) Long aid, ModelMap modelMap) throws IOException {



		 Exercice  anneeRecup = new Exercice();
		if(aid != null){
			try {
				anneeRecup = exerciceRepository.findById(aid) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + aid));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" annee :::::: "+anneeRecup.toString());

		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();

		if(anneeRecup.getId() != null){

			Integer anneeEnCours = Integer.valueOf(anneeRecup.getAnnee());

			Integer nbHommeRetraitAn1 = 0;
			Integer nbFemmeRetraitAn1 = 0;

			Integer nbHommeRetraitAn2 = 0;
			Integer nbFemmeRetraitAn2 = 0;

			Integer nbHommeRetraitAn3 = 0;
			Integer nbFemmeRetraitAn3 = 0;

			Integer nbHommeRetraitAn4 = 0;
			Integer nbFemmeRetraitAn4 = 0;

			Integer nbHommeRetraitAn5 = 0;
			Integer nbFemmeRetraitAn5 = 0;

			String annee = String.valueOf(anneeEnCours);
			java.sql.Date dateAnDeb = null;	dateAnDeb = null;
			try {
				Date dateAnDeb11 = null;

				 dateAnDeb11= Utils.stringToDate("01/01/"+annee, "dd/MM/yyyy");
				dateAnDeb =new java.sql.Date(dateAnDeb11.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<Personnel> listPersonnelHomme = new ArrayList<Personnel>();
			try{
				listPersonnelHomme = personnelService.RechercherListPersonnelParAnnee( "Masculin");
				System.out.println("list Personnel homme : "+listPersonnelHomme.size());
			} catch(Exception ex){
				logger.error(ex.getMessage());
			}
			for (Personnel personnel : listPersonnelHomme) {
				Date datNaiss = personnel.getDateNaissance();
				double age = differenceDate.valAge(dateAnDeb, datNaiss);
				System.out.println("Age de homme est : "+age);

				if(age > 59){
					nbHommeRetraitAn1 = nbHommeRetraitAn1 + 1;
				}else{

					if(age > 58){
						nbHommeRetraitAn2 = nbHommeRetraitAn2 + 1;
					}else{

						if(age > 57){
							nbHommeRetraitAn3 = nbHommeRetraitAn3 + 1;
						}else{
							if(age > 56){
								nbHommeRetraitAn4 = nbHommeRetraitAn4 + 1;
							}else{
								if(age > 55){
									nbHommeRetraitAn5 = nbHommeRetraitAn5 + 1;
								}
							}
						}
					}
				}

			}

			List<Personnel> listPersonnelFemme = new ArrayList<Personnel>();
			try{
				listPersonnelFemme = personnelService.RechercherListPersonnelParAnnee( "Feminin");
				System.out.println("list Personnel femme : "+listPersonnelFemme.size());
			} catch(Exception ex){
				logger.error(ex.getMessage());
			}
			for (Personnel personnel : listPersonnelFemme) {
				Date datNaiss = personnel.getDateNaissance();
				double age = differenceDate.valAge(dateAnDeb, datNaiss);
				System.out.println("Age de femme est : "+age);

				if(age > 59){
					nbFemmeRetraitAn1 = nbFemmeRetraitAn1 + 1;
				}else{

					if(age > 58){
						nbFemmeRetraitAn2 = nbFemmeRetraitAn2 + 1;
					}else{

						if(age > 57){
							nbFemmeRetraitAn3 = nbFemmeRetraitAn3 + 1;
						}else{
							if(age > 56){
								nbFemmeRetraitAn4 = nbFemmeRetraitAn4 + 1;
							}else{
								if(age > 55){
									nbFemmeRetraitAn5 = nbFemmeRetraitAn5 + 1;
								}
							}
						}
					}
				}

			}

			PrintLs printDTO = new PrintLs();
			printDTO.setI1(nbHommeRetraitAn1);
			printDTO.setS1(annee);
			printDTO.setTitle1("Homme");
			printDTO.setI2(nbFemmeRetraitAn1);
			printDTO.setS2(annee);
			printDTO.setTitle2("Femme");

			listPrintDTO.add(printDTO);

			PrintLs printDTO2 = new PrintLs();
			printDTO2.setI1(nbHommeRetraitAn2);
			printDTO2.setS1(String.valueOf(anneeEnCours+1));
			printDTO2.setTitle1("Homme");
			printDTO2.setI2(nbFemmeRetraitAn2);
			printDTO2.setS2(String.valueOf(anneeEnCours+1));
			printDTO2.setTitle2("Femme");

			listPrintDTO.add(printDTO2);

			PrintLs printDTO3 = new PrintLs();
			printDTO3.setI1(nbHommeRetraitAn3);
			printDTO3.setS1(String.valueOf(anneeEnCours+2));
			printDTO3.setTitle1("Homme");
			printDTO3.setI2(nbFemmeRetraitAn3);
			printDTO3.setS2(String.valueOf(anneeEnCours+2));
			printDTO3.setTitle2("Femme");

			listPrintDTO.add(printDTO3);

			PrintLs printDTO4 = new PrintLs();
			printDTO4.setI1(nbHommeRetraitAn4);
			printDTO4.setS1(String.valueOf(anneeEnCours+3));
			printDTO4.setTitle1("Homme");
			printDTO4.setI2(nbFemmeRetraitAn4);
			printDTO4.setS2(String.valueOf(anneeEnCours+3));
			printDTO4.setTitle2("Femme");

			listPrintDTO.add(printDTO4);

			PrintLs printDTO5 = new PrintLs();
			printDTO5.setI1(nbHommeRetraitAn5);
			printDTO5.setS1(String.valueOf(anneeEnCours+4));
			printDTO5.setTitle1("Homme");
			printDTO5.setI2(nbFemmeRetraitAn5);
			printDTO5.setS2(String.valueOf(anneeEnCours+4));
			printDTO5.setTitle2("Femme");

			listPrintDTO.add(printDTO5);

		}



		//return new ModelAndView("redirect:../../../rhp/personnel/processing/listpersonnal?uid="+utilisateurCourant.getUid());
		return toJson(listPrintDTO);
	}
	@RequestMapping(value = "/stat/moyenAge", method = RequestMethod.GET)
	@ResponseBody
	public String statMoyenneAge( @RequestParam(value="aid", required=false) Long aid, ModelMap modelMap, Principal principal) throws IOException {


		Exercice anneeRecup = new Exercice();
		if(aid != null){
			try {
				anneeRecup = exerciceRepository.findById(aid) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + aid));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" annee :::::: "+anneeRecup.toString());

		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();

		if(anneeRecup.getId() != null){

			Integer anneeEnCours = Integer.valueOf(anneeRecup.getAnnee());
			String annee = String.valueOf(anneeEnCours);
			java.sql.Date dateAnDeb = null;

			try {
				Date dateAnDeb11 = null;

				dateAnDeb11= Utils.stringToDate("01/01/"+annee, "dd/MM/yyyy");
				dateAnDeb =new java.sql.Date(dateAnDeb11.getTime());
				//dateAnDeb = Utils.stringToDate().stringToDateSql("01/01/"+annee, "dd/MM/yyyy");
			} catch (Exception e) {
				e.printStackTrace();
			}

			//Recherche du nombre de direction
			List<Service> listDirection = new ArrayList<Service>();
			try {
				listDirection = serviceService.findByTypeServiceId(1L);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("####### Nb direction :::::: "+listDirection.size());

			for (Service direction : listDirection) {
				//Recherche de la liste du personnel pour une direction
				List<Personnel> listPers = new ArrayList<Personnel>();
				try {
					listPers = personnelService.RechercherListPersonnelParDirection(direction);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("####### Nb personnel par direction :::::: "+direction.getLibelle()+" nbre :"+listPers.size());

				double somAge = 0;
				//calculer l'age de chaque personnel
				for (Personnel personnel : listPers) {
					Date datNaiss = personnel.getDateNaissance();
					double age = differenceDate.valAge(dateAnDeb, datNaiss);
					System.out.println("Age de homme est : "+age);
					somAge = somAge + age;
				}
				double moyAge = 0;

				if(listPers.size() != 0)
					moyAge = somAge / listPers.size();

				PrintLs printDTO = new PrintLs();
				printDTO.setI1((int)moyAge);
				printDTO.setS1(anneeRecup.getAnnee());
				printDTO.setTitle1(direction.getLibelle());

				listPrintDTO.add(printDTO);
			}


		}

		//return new ModelAndView("redirect:../../../rhp/personnel/processing/listpersonnal?uid="+utilisateurCourant.getUid());
		return toJson(listPrintDTO);
	}

//	@RequestMapping(value = "/stat/massesalariale", method = RequestMethod.GET)
//	@ResponseBody
//	public String statmassesalarialetypContrat( @RequestParam(value="id", required=false) Long aid, ModelMap modelMap, Principal principal) throws IOException {
//
//
//		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();
//
//		Exercice annee = new Exercice();
//		if (aid != null) {
//			try {
//				annee = exerciceRepository.findById(aid).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + aid));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		List<TypeContrat> listtypeCtrat = new ArrayList<TypeContrat>();
//		List<Personnel> listpersCtrat = new ArrayList<Personnel>();
//		listpersCtrat = personnelRepository.findByRetraitEffectFalse();
//		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
//		listtypeCtrat = typeContratRepository.findAll();
//		Double ctratuel = 0d;
//		Double stagiairel = 0d;
//		Double consultant = 0d;
//		Double fonctionnaire = 0d;
//		for (Personnel typctrat : listpersCtrat) {
//			if (contratPersonnelRepository.findByPersonnelIdAndStatut(typctrat.getId(), true) != null) {
//
//				if (Boolean.TRUE.equals(typctrat.getCarec())) {
//					if (bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat) != null)
//						ctratuel = ctratuel + bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat).getTotalmassesalarial();
//				} else if (Boolean.TRUE.equals(typctrat.getStage())) {
//					if (bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat) != null)
//						stagiairel = stagiairel + bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat).getTotalmassesalarial();
//				} else if (Boolean.TRUE.equals(typctrat.getConsultant())) {
//					if (bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat) != null)
//						consultant = consultant + bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat).getTotalmassesalarial();
//				} else if (Boolean.TRUE.equals(typctrat.getFonctionnaire())) {
//					if (bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat) != null)
//						fonctionnaire = fonctionnaire + bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat).getTotalmassesalarial();
//				} else {
//					if (bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat) != null)
//						ctratuel = ctratuel + bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaie, typctrat).getTotalmassesalarial();
//
//				}
//			}
//
//	   }
//		PrintLs printDTO = new PrintLs();
//		printDTO.setI1(ctratuel.intValue());
//		printDTO.setS1("Contractuel");
//		printDTO.setTitle1("Contractuels");
//		listPrintDTO.add(printDTO);
//
//		PrintLs printDTO1 = new PrintLs();
//		printDTO1.setI1(stagiairel.intValue());
//		printDTO1.setS1("Stagiaire");
//		printDTO1.setTitle1("Stagiaires");
//		listPrintDTO.add(printDTO1);
//
//		PrintLs printDTO2 = new PrintLs();
//		printDTO2.setI1(consultant.intValue());
//		printDTO2.setS1("Consultant");
//		printDTO2.setTitle1("Consultant");
//		listPrintDTO.add(printDTO2);
//
//		PrintLs printDTO3 = new PrintLs();
//		printDTO3.setI1(fonctionnaire.intValue());
//		printDTO3.setS1("Fonctionnaire");
//		printDTO3.setTitle1("Fonctionnaire");
//		listPrintDTO.add(printDTO3);
//
//		//return new ModelAndView("redirect:../../../rhp/personnel/processing/listpersonnal?uid="+utilisateurCourant.getUid());
//		return toJson(listPrintDTO);
//	}


	@RequestMapping(value = "/stat/massesalariale", method = RequestMethod.GET)
	@ResponseBody
	public String statmassesalarialetypContrat(@RequestParam(value = "id", required = false) Long aid) throws IOException {
		Exercice exercice = null;
		if (aid != null) {
			exercice = exerciceRepository.findById(aid)
					.orElseThrow(() -> new EntityNotFoundException("Exercice not found for id " + aid));
		}

		PeriodePaie periode = periodePaieService.findPeriodeactive();
		if (periode == null) {
			return toJson(Collections.emptyList());
		}

		List<PrintLs> masseSalariale = bulletinPaieService.calculerMasseSalarialeParTypeContrat(periode);
		return toJson(masseSalariale);
	}


	@RequestMapping(value = "/stat/typecontrat", method = RequestMethod.GET)
	@ResponseBody
	public String stattypContrat( @RequestParam(value="id", required=false) Long aid, ModelMap modelMap, Principal principal) throws IOException {


		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();

		Exercice annee = new Exercice();
		if(aid != null){
			try {
				annee = exerciceRepository.findById(aid) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + aid));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<TypeContrat> listtypeCtrat = new ArrayList<TypeContrat>();
		List<Personnel> listpersCtrat = new ArrayList<Personnel>();
		listpersCtrat= personnelRepository.findByRetraitEffectFalse();

		listtypeCtrat=typeContratRepository.findAll();
		Integer ctratuel=0;Integer stagiairel=0;Integer consultant=0;Integer fonctionnaire=0;
		for (Personnel typctrat : listpersCtrat) {
			if(Boolean.TRUE.equals(typctrat.getCarec()))
				ctratuel=ctratuel+1;
			else{
				if(Boolean.TRUE.equals(typctrat.getStage())){stagiairel = stagiairel+1;}
				//if(typctrat.getStage()==null){ctratuel = ctratuel+1;}
				if(Boolean.TRUE.equals(typctrat.getConsultant())){consultant = consultant+1;}
				if(Boolean.TRUE.equals(typctrat.getFonctionnaire())){fonctionnaire = fonctionnaire+1;}
			}
		}

		PrintLs printDTO = new PrintLs();
		printDTO.setI1(ctratuel);
		printDTO.setS1("Contractuel");
		printDTO.setTitle1("Contractuels");
		listPrintDTO.add(printDTO);

		PrintLs printDTO1 = new PrintLs();
		printDTO1.setI1(stagiairel);
		printDTO1.setS1("Stagiaire");
		printDTO1.setTitle1("Stagiaires");
		listPrintDTO.add(printDTO1);

		PrintLs printDTO2 = new PrintLs();
		printDTO2.setI1(consultant);
		printDTO2.setS1("Consultant");
		printDTO2.setTitle1("Consultant");
		listPrintDTO.add(printDTO2);

		PrintLs printDTO3 = new PrintLs();
		printDTO3.setI1(fonctionnaire);
		printDTO3.setS1("Fonctionnaire");
		printDTO3.setTitle1("Fonctionnaire");
		listPrintDTO.add(printDTO3);

		return toJson(listPrintDTO);
	}

	private String toJson(List<PrintLs> listPrintDTO) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String value = mapper.writeValueAsString(listPrintDTO);
			return value;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
