package com.nectux.mizan.hyban.rh.absences.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.rh.absences.dto.AbsencesDTO;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.absences.service.AbsencesService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/absence")
public class AbsencesController {
	
	private static final Logger logger = LogManager.getLogger(AbsencesController.class);
	
   //     @Autowired private TypeSanctionService typeSanctionService;
	@Autowired private AbsencesService absenceService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;

	@Autowired private SocieteService societeService;
	@RequestMapping("/absences")
	public String viewSanction(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> absences");

		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activeAbsence", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-sitemap");
		modelMap.addAttribute("littleTitle", "Absence");
		modelMap.addAttribute("bigTitle", "Absence");
       
		Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "absences";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerabsences", method = RequestMethod.POST)
	public @ResponseBody
    AbsencesDTO saveAbsences(@RequestParam(value="id", required=false) Long id,
                             @RequestParam(value="faute", required=false) String faute,
                             @RequestParam(value="type", required=false) String type,
                             @RequestParam(value="commentaire", required=false) String commentaire) throws Exception {
		logger.info(">>> ENREGISTRER AFFECTATION");
		return absenceService.save(id, faute,type, commentaire);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverabsences", method = RequestMethod.POST)
	public @ResponseBody AbsencesDTO findAbsences(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER AFFECTATION");
		return absenceService.findAbsence(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerabsences", method = RequestMethod.POST)
	public @ResponseBody AbsencesDTO findAabsences() {
		logger.info(">>> LISTE AFFECTATIONS");
		return absenceService.findAbsences();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerabsences", method = RequestMethod.POST)
	public @ResponseBody AbsencesDTO deleteSanction(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER AFFECTATION");
		return absenceService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerabsences", method = RequestMethod.GET)
	public @ResponseBody AbsencesDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE AFFECTATIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		AbsencesDTO absenceDTO = new AbsencesDTO();
		if(search == null || search == "")
			absenceDTO = absenceService.loadAbsences(pageRequest);
		else
			absenceDTO = absenceService.loadAbsences(pageRequest, search);
		
		return absenceDTO;
	}

}
