package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.formation.dto.FormateurDTO;
import com.nectux.mizan.hyban.rh.formation.service.FormateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/formation")
public class FormateurController {
	
	private static final Logger logger = LogManager.getLogger(FormateurController.class);
	
	@Autowired
    private FormateurService formateurService;@Autowired
    private SocieteService societeService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	 
	@RequestMapping("/formateurs")
	public String viewFormateur(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Formateur");
		
		modelMap.addAttribute("activeTraining", "active");
		modelMap.addAttribute("blockTraining", "block");
		modelMap.addAttribute("activeTeacher", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formation");
		modelMap.addAttribute("bigTitle", "Formateur");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
		Societe mysociete=null;
		java.util.List<Societe> malist=societeService.findtsmois();
		if(malist.size()>0)
		{	mysociete=malist.get(0);
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "formateurs";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerformateur", method = RequestMethod.POST)
	public @ResponseBody
    FormateurDTO saveFormateur(@RequestParam(value="id", required=false) Long id,
                               @RequestParam(value="cabinetFormationId", required=false) Long cabinetFormationId,
                               @RequestParam(value="nom", required=false) String nom,
                               @RequestParam(value="civilite", required=false) String civilite,
                               @RequestParam(value="sexe", required=false) char sexe,
                               @RequestParam(value="situationMatrimonial", required=false) int situationMatrimonial,
                               @RequestParam(value="dateNaissance", required=false) String dateNaissance,
                               @RequestParam(value="lieuNaissance", required=false) String lieuNaissance,
                               @RequestParam(value="fixe", required=false) String fixe,
                               @RequestParam(value="mobile", required=false) String mobile) throws Exception {
		logger.info(">>> ENREGISTRER FORMATEUR");
		return formateurService.save(id, cabinetFormationId, nom, civilite, sexe, situationMatrimonial, dateNaissance, lieuNaissance, fixe, mobile);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverformateur", method = RequestMethod.POST)
	public @ResponseBody
    FormateurDTO findFormateur(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER FORMATEUR");
		return formateurService.findFormateur(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerformateurs", method = RequestMethod.POST)
	public @ResponseBody
    FormateurDTO findFormateurs() {
		logger.info(">>> LISTE FORMATEURS");
		return formateurService.findFormateurs();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerformateur", method = RequestMethod.POST)
	public @ResponseBody
    FormateurDTO deleteFormateur(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER FORMATEUR");
		return formateurService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerformateurs", method = RequestMethod.GET)
	public @ResponseBody
    FormateurDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                 @RequestParam(value="offset", required=false) Integer offset,
                                 @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE FORMATEURS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		FormateurDTO cabinetFormationDTO = new FormateurDTO();
		if(search == null || search == "")
			cabinetFormationDTO = formateurService.loadFormateurs(pageRequest);
		else
			cabinetFormationDTO = formateurService.loadFormateurs(pageRequest, search, search, search, search, search, search, search);
		
		return cabinetFormationDTO;
	}

}
