package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.rh.carriere.service.SanctionService;
import com.nectux.mizan.hyban.rh.carriere.service.TypeSanctionService;
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

import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.dto.SanctionDTO;

@Controller
@RequestMapping("/carriere")
public class SanctionController {
	
	private static final Logger logger = LogManager.getLogger(SanctionController.class);
	
        @Autowired private TypeSanctionService typeSanctionService;
	@Autowired private SanctionService sanctionService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;

	@RequestMapping("/sanctions")
	public String viewSanction(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Sanctions");

		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockCareer", "block");
		modelMap.addAttribute("activeSanction", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-sitemap");
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Sanction");
                
		modelMap.addAttribute("listeTypeSanction", typeSanctionService.findTypesSanction());
		Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		
		return "sanctions";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrersanction", method = RequestMethod.POST)
	public @ResponseBody SanctionDTO saveSanction(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="idTypeSanction", required=false) Long idTypeSanction,  
												@RequestParam(value="faute", required=false) String faute,  
												@RequestParam(value="commentaire", required=false) String commentaire) throws Exception {
		logger.info(">>> ENREGISTRER AFFECTATION");
		return sanctionService.save(id, idTypeSanction, faute, commentaire);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouversanction", method = RequestMethod.POST)
	public @ResponseBody SanctionDTO findSanction(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER AFFECTATION");
		return sanctionService.findSanction(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listersanctions", method = RequestMethod.POST)
	public @ResponseBody SanctionDTO findSanctions() {
		logger.info(">>> LISTE AFFECTATIONS");
		return sanctionService.findSanctions();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimersanction", method = RequestMethod.POST)
	public @ResponseBody SanctionDTO deleteSanction(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER AFFECTATION");
		return sanctionService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginersanctions", method = RequestMethod.GET)
	public @ResponseBody SanctionDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE AFFECTATIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		SanctionDTO sanctionDTO = new SanctionDTO();
		if(search == null || search == "")
			sanctionDTO = sanctionService.loadSanctions(pageRequest);
		else
			sanctionDTO = sanctionService.loadSanctions(pageRequest, search, search);
		
		return sanctionDTO;
	}

}
