package com.nectux.mizan.hyban.controllers;

import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.parametrages.web.BanqueController;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.service.ContratPersonnelService;
import com.nectux.mizan.hyban.personnel.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.io.IOException;
import java.security.Principal;

@Controller
//@RequestMapping("/main")
public class MainController {
	
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private SocieteService societeService;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private PersonnelService personnelService;
	@Autowired private ContratPersonnelService contratPersonnelService;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	private PeriodePaie maperiode;
	private static final Logger logger = LogManager.getLogger(MainController.class);
	@RequestMapping(path = {"/","/login"}, method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
    	   Societe mysociete=null;
 		  java.util.List<Societe> malist=societeService.findtsmois();
 		 if(malist.size()>0)
			{	mysociete=malist.get(0);			
 			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
        return "login";
    }
	
	@RequestMapping("/welcome")
    public String welcome(ModelMap modelMap, Principal principal) throws IOException {
		logger.info("utilisateur connecté" +principal.getName());
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name())
    .findFirst().orElse(""));



		modelMap.addAttribute("icon", "fa fa-dashboard");
		modelMap.addAttribute("littleTitle", "Accueil");
		modelMap.addAttribute("bigTitle", "Tableau de bord ");
		
		modelMap.addAttribute("activeWelcome", "active");
		modelMap.addAttribute("activePaies", "");
		modelMap.addAttribute("activePersonnels", "");
		modelMap.addAttribute("activeParametrages", "");

		modelMap.addAttribute("blockPaies", "");
		modelMap.addAttribute("blockPersonnels", "");
		modelMap.addAttribute("blockParametrages", "");
		// PAIE
		modelMap.addAttribute("activeLivrePaie", "");
		modelMap.addAttribute("activeLivreConge", "");
	    modelMap.addAttribute("activeLivreGratification", "");
	    modelMap.addAttribute("activePret", "");
	    // PERSONNEL
		modelMap.addAttribute("activePersonnel", "");
		modelMap.addAttribute("activeContrat", "");
		modelMap.addAttribute("activeCategorie", "");
	    modelMap.addAttribute("activeFonction", "");
		// PARAMETRAGE
		modelMap.addAttribute("activeUtilisateur", "");
	    modelMap.addAttribute("activeExercice", "");
	    modelMap.addAttribute("activePeriode", "");
	    modelMap.addAttribute("activeMois", "");
		DecimalFormat formatter = new DecimalFormat("#,###");
		formatter.setGroupingSize(3);
		formatter.setGroupingUsed(true);
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		formatter.setDecimalFormatSymbols(symbols);

		//String formatted = formatter.format(number); // "14 377 088"

		maperiode=periodePaieService.findPeriodeactive();
	    if(maperiode==null){}
	    else{
	    	modelMap.addAttribute("activeMois", maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", maperiode.getId());
			modelMap.addAttribute("anneId", maperiode.getAnnee().getId());
			modelMap.addAttribute("ctratTrue", contratPersonnelRepository.findByStatutTrueAndDepartFalseOrderByPersonnelNomAscPersonnelPrenomAsc().size());
			modelMap.addAttribute("nbrEmpl", personnelService.count());
			modelMap.addAttribute("massSalar",formatter.format(Math.ceil(bulletinPaieService.MasseSalarialMois(maperiode).doubleValue())));
			modelMap.addAttribute("periode",  maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
	    }
	    Societe mysociete=null;
		java.util.List<Societe> malist=societeService.findtsmois();
			if(malist.size()>0)
			{ mysociete=malist.get(0);			
			  modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
			//modelMap.addAttribute("bigTitle", "SMART PAIE ");
		return "welcome";
	}
}
