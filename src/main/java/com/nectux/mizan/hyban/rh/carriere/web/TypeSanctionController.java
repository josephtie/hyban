package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
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

import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.dto.TypeSanctionDTO;

@Controller
@RequestMapping("/carriere")
public class TypeSanctionController {
	
	private static final Logger logger = LogManager.getLogger(TypeSanctionController.class);
	
	@Autowired private TypeSanctionService typeSanctionService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@Autowired private SocieteService societeService;
	@RequestMapping("/typesanction")
	public String viewTypeSanction(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> TypeSanctions");

		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockCareer", "block");
		modelMap.addAttribute("activeSanctionType", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-sitemap");
		modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Type Sanction");
		
		Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{		mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		
		return "typeSanctions";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrertypesanction", method = RequestMethod.POST)
	public @ResponseBody TypeSanctionDTO saveTypeSanction(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="libelle", required=false) String libelle,  
												@RequestParam(value="description", required=false) String description) throws Exception {
		logger.info(">>> ENREGISTRER TYPE SANCTION");
		return typeSanctionService.save(id, libelle, description);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouvertypesanction", method = RequestMethod.POST)
	public @ResponseBody TypeSanctionDTO findTypeSanction(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER TYPE SANCTION");
		return typeSanctionService.findTypeSanction(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listertypesanctions", method = RequestMethod.POST)
	public @ResponseBody TypeSanctionDTO findTypeSanctions() {
		logger.info(">>> LISTE TYPE SANCTIONS");
		return typeSanctionService.findTypeSanctions();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimertypesanction", method = RequestMethod.POST)
	public @ResponseBody TypeSanctionDTO deleteTypeSanction(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER TYPE SANCTION");
		return typeSanctionService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginertypesanctions", method = RequestMethod.GET)
	public @ResponseBody TypeSanctionDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE TYPE SANCTIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		TypeSanctionDTO typeSanctionDTO = new TypeSanctionDTO();
		if(search == null || search == "")
			typeSanctionDTO = typeSanctionService.loadTypeSanctions(pageRequest);
		else
			typeSanctionDTO = typeSanctionService.loadTypeSanctions(pageRequest, search, search);
		
		return typeSanctionDTO;
	}

}
