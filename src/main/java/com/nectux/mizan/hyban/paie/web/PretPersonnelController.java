package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.dto.LivreDePaieSimulationDTO;
import com.nectux.mizan.hyban.paie.dto.PretDTO;
import com.nectux.mizan.hyban.paie.dto.PretPersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.*;
import com.nectux.mizan.hyban.paie.service.PretPersonnelService;
import com.nectux.mizan.hyban.paie.service.PretService;
import com.nectux.mizan.hyban.parametrages.dto.PeriodePaieDTO;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.service.CategorieService;
import com.nectux.mizan.hyban.utils.DifferenceDate;
import com.nectux.mizan.hyban.utils.Utils;
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


@Controller
@RequestMapping("/paie")
public class PretPersonnelController {

	

private static final Logger logger = LogManager.getLogger(PretPersonnelController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PretService pretService;
	@Autowired private CategorieService categorieService;
	@Autowired private PretPersonnelService pretPersonnelService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;

	@RequestMapping("/pretpersonnel")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeLend", "active");
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Prets personnels");
		
	    List<PeriodePaie> listPeriodepaie;
	    List<Pret> listPrets;
	    try {
	    	listPrets = pretService.listdesPret();
	    	listPeriodepaie = periodePaieService.listperiodesupAuPret();
		} catch (Exception e) {
			listPrets = new ArrayList<Pret>();
			listPeriodepaie = new ArrayList<PeriodePaie>();
		}
	    modelMap.addAttribute("listPrets", listPrets);
	    modelMap.addAttribute("listPeriodepaie", listPeriodepaie);
	    
	    
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
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "pretpersonnels";
	}
	@RequestMapping("/simulPaie")
	public String viewsimulPaieType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");

		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeSimulPaie", "active");
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Simulation de calcul");

		List<PeriodePaie> listPeriodepaie;
		List<Pret> listPrets;
		try {
			listPrets = pretService.listdesPret();
			listPeriodepaie = periodePaieService.listperiodesupAuPret();
		} catch (Exception e) {
			listPrets = new ArrayList<Pret>();
			listPeriodepaie = new ArrayList<PeriodePaie>();
		}
//		modelMap.addAttribute("listPrets", listPrets);
		modelMap.addAttribute("listeCategorie", categorieService.findCategories());


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
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "simulPaie";
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
		tab[2] = (double)partieApresVirg;



		return tab;
	}
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/simulcalculalenvers", method = RequestMethod.GET)
	public @ResponseBody
	LivreDePaieSimulationDTO cherchBullpersonnel(@RequestParam(value="categorie", required=true) Long categorie ,
												 @RequestParam(value="montantTransp", required=true) Double montantTransp ,
												 @RequestParam(value="situationmatrimoniale", required=true) Integer situationmatrimoniale ,
												 @RequestParam(value="nombreenfant", required=true) Integer nombreenfant ,
												 @RequestParam(value="salaireNet", required=true) Double salaireNet ,
												 Principal principal) {
		LivreDePaieSimulation livredePaie = null;
		LivreDePaieSimulationDTO livreDePaieSimulationDTO=new LivreDePaieSimulationDTO();
		Categorie categorie1=null;
		categorie1=categorieService.findCategorie(categorie);
	List<LivreDePaieSimulation> livreDePaieSimulations = new ArrayList<LivreDePaieSimulation>();
		//ContratPersonnel ctratpersonnel=contratPersonnelRepository.findByPersonnelIdAndStatut(id, true);
		//PlanningConge planningConge = planningCongeRepository.findByContratPersonnelAndStatut(ctratpersonnel,true);
		//TempEffectif tpeff;
	//	tpeff=tempeffectifRepository.findByPersonnelAndPeriodePaie(ctratpersonnel.getPersonnel(), maperiode);

//		Double[]  ancienete=calculAnciennete(categorie1.getSalaireDeBase(),ctratpersonnel.getPersonnel().getDateArrivee());
//		double newancienete;
//		if(ctratpersonnel.getAncienneteInitial()!=0) {
//			newancienete=ancienete[1] +ctratpersonnel.getAncienneteInitial();
//		}else{
//			newancienete=ancienete[1];
//		}
//		int anc=(int)newancienete;
		int op=0;
//		if(anc < 2) op=0;
//		if(anc>=2 && anc<=25) op=anc;
//		if(anc>25) op=25;
		Float nbpart=calculNbrepart(nombreenfant,situationmatrimoniale);
		List<PrimePersonnel> listIndemniteBrut=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listIndemniteNonBrut=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listRetenueMutuelle=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listGainsNet=new ArrayList<PrimePersonnel>();
		List<PrimePersonnel> listIndemnite  =new ArrayList<PrimePersonnel>();
//		listIndemnite =  primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnel.getPersonnel().getId(), maperiode.getId());
//		if(listIndemnite.size()>0){
//			for(PrimePersonnel kprme:listIndemnite){
//				if(kprme.getPrime().getEtatImposition()==1)
//				{
//					listIndemniteBrut.add(kprme);
//				}
//				if(kprme.getPrime().getEtatImposition()==2)
//				{
//					listIndemniteNonBrut.add(kprme);
//				}
//				if(kprme.getPrime().getEtatImposition()==3)
//				{
//					if(kprme.getPrime().getMtExedent()!=null)
//					{listIndemniteBrut.add(kprme);
//						listIndemniteNonBrut.add(kprme);}
//				}
//				if(kprme.getPrime().getEtatImposition()==4)
//				{
//					listRetenueMutuelle.add(kprme);
//				}
//				if(kprme.getPrime().getEtatImposition()==5)
//				{
//					listGainsNet.add(kprme);
//				}
//			}

		//}

		livredePaie= new LivreDePaieSimulation("MatTEST","nomTest"+" "+" PrenomTest", nbpart ,op, categorie1.getSalaireDeBase(),5000d, 0d,montantTransp, 0d,0d,true,null,null,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet);
		try {
			int i=0;
			for( i = 0; i < 20; i++){
				//(livredePaie.getNetPayer()==valued)
				Double nouvSursal = 0d;Double nouvDiff= 0d;Double nouvMontantBrutImp= 0d;
				nouvMontantBrutImp=Math.rint(salaireNet*livredePaie.getBrutImposable()/livredePaie.getNetPayer());
				nouvDiff=nouvMontantBrutImp-livredePaie.getBrutImposable();
				nouvSursal=nouvDiff+livredePaie.getSursalaire();
				livredePaie = new LivreDePaieSimulation("MatTEST","nomTest"+" "+" PrenomTest", nbpart, op, categorie1.getSalaireDeBase(),nouvSursal, 0d,montantTransp, 0d,0d,true,null,null,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet);
				//	System.out.println("*********************SECOND BULLETIN*"+i);
				System.out.println("*SECOND BULLETIN#############-----------"+livredePaie.toString());
				// i++;
			}


		} catch (Exception e) {
			System.out.println("FINISH"+ e.getMessage());
		}
		livreDePaieSimulations.add(livredePaie);
		livreDePaieSimulationDTO.setResult(true);
		livreDePaieSimulationDTO.setRows(livreDePaieSimulations);
		return livreDePaieSimulationDTO;
	}

	public Float calculNbrepart( Integer nbEnfant, Integer persSituationMatrimoniale){

		Float nbPart = 0F;


		if((persSituationMatrimoniale == 2 || persSituationMatrimoniale == 3 || persSituationMatrimoniale == 4) && nbEnfant == 0)
			nbPart = (float) 1;


		if( (persSituationMatrimoniale == 2 || persSituationMatrimoniale == 3) && nbEnfant > 0){
			nbPart = (float) (1.5 + (nbEnfant * 0.5));

			if(nbPart>5)
				nbPart = (float) 5;
		}

		if(persSituationMatrimoniale == 1 && nbEnfant == 0)
			nbPart = (float) 2;


		if((persSituationMatrimoniale == 1 || persSituationMatrimoniale== 4) && nbEnfant > 0){
			nbPart = (float) (2 + (nbEnfant * 0.5));

			if(nbPart>5)
				nbPart = (float) 5;
		}
		return nbPart;
	}
	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pretPersonneljson", method = RequestMethod.GET)
	public @ResponseBody
    PretPersonnelDTO getperiodepret(@RequestParam(value="limit", required=false) Integer limit,
                                    @RequestParam(value="offset", required=false) Integer offset,
                                    @RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		
		PretPersonnelDTO periodeDTO = new PretPersonnelDTO();
		if(search == null || search == "")
			periodeDTO = pretPersonnelService.loadPretPersonnel(pageRequest);
		else
			periodeDTO = pretPersonnelService.loadPretPersonnel(pageRequest, search);
		
		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savepretPersonnel", method = RequestMethod.POST)
	public @ResponseBody PretPersonnelDTO savePretpersonnel(@RequestParam(value="montant", required=true) Double montant,
															@RequestParam(value="echelonage", required=true) Long echelonage,
															@RequestParam(value="pret", required=true) Long idPret,
															@RequestParam(value="idpers", required=true) Long idPers,
															@RequestParam(value="dEmprunt", required=true) String dEmprunt,
															@RequestParam(value="periodepaie", required=true) Long idPeriodDep,
															Principal principal) {
		
		
		return pretPersonnelService.saver(montant,echelonage,idPret, idPers, dEmprunt,idPeriodDep);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updatepretPersonnel", method = RequestMethod.GET)
	public @ResponseBody PretPersonnelDTO updatePretpersonnel(@RequestParam(value="idpret", required=true) Long idpret,@RequestParam(value="montant1", required=true) Double montant,
															@RequestParam(value="echelonage1", required=true) Long echelonage,
															@RequestParam(value="pret1", required=true) Long idPret,
															@RequestParam(value="idpers1", required=true) Long idPers,
															@RequestParam(value="dEmprunt1", required=true) String dEmprunt,
															@RequestParam(value="periodepaie1", required=true) Long idPeriodDep,
															Principal principal) {
		
		//Utilisateur currentUser = userService.findByEmail(principal.getName());
		
		return pretPersonnelService.update(idpret,montant,echelonage,idPret, idPers, dEmprunt,idPeriodDep);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listperiodesupAuPret", method = RequestMethod.GET)
	public @ResponseBody
    PeriodePaieDTO activPeriode(Principal principal) {
		
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		List<PeriodePaie> listPeriodepaie;
		listPeriodepaie = periodePaieService.listperiodesupAuPret();
		if(listPeriodepaie.size()>0)
			periodeDTO.setRows(listPeriodepaie);
		    periodeDTO.setResult("success");
		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listPret", method = RequestMethod.GET)
	public @ResponseBody
    PretDTO activPret(Principal principal) {
		
		PretDTO pretDTO = new PretDTO();
		 List<Pret> listPrets;
		 listPrets = pretService.listdesPret();
		if(listPrets.size()>0)
		pretDTO.setRows(listPrets);
		pretDTO.setResult("success");
		return pretDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pretIndividuel", method = RequestMethod.POST)
	public @ResponseBody PretPersonnelDTO ptretUserc(@RequestParam(value="id", required=true) Long idp, Principal principal) {
		
		return pretPersonnelService.PretPersonneldupersonnel(idp);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/delpretIndividuel", method = RequestMethod.POST)
	public @ResponseBody PretPersonnelDTO delptretUserc(@RequestParam(value="idPretperso", required=true) Long idp, Principal principal) {
		
		PretPersonnelDTO pretpersonnelDTO = new PretPersonnelDTO();
		pretpersonnelDTO.setResult(pretPersonnelService.delete(idp));
		return pretpersonnelDTO;
		
	
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchpretIndividuel", method = RequestMethod.POST)
	public @ResponseBody
    PretPersonnel searchptretUserc(@RequestParam(value="id", required=true) Long idp, Principal principal) {
		return pretPersonnelService.findpret(idp);
	}
}
