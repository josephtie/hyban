package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.paie.entity.Pret;
import com.nectux.mizan.hyban.paie.service.PretPersonnelService;
import com.nectux.mizan.hyban.paie.service.PretService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.service.CategorieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/histpaie")
public class PaieController {


	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PretService pretService;
	@Autowired private CategorieService categorieService;
	@Autowired private PretPersonnelService pretPersonnelService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
private static final Logger logger = LogManager.getLogger(PaieController.class);
	
	@Autowired private UtilisateurService userService;
	 
	@RequestMapping("/paie")
    public String viewLivrepaie(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		/*modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activePayrollBook", "active");*/
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Acceuil");
		modelMap.addAttribute("bigTitle", "RH PAIE - CGECI");
	    
		return "paie";
	}
	@RequestMapping("/departpersonnel")
	public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");

		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeLend", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Soldes de tous Comptes CDD/CDI");

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
		return "departpersonnel";
	}
}
