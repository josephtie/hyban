package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.dto.RubriqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.RubriqueService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

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
@RequestMapping("/parametrages")
public class RubriqueController {
	
	private static final Logger logger = LogManager.getLogger(RubriqueController.class);
	
	@Autowired private RubriqueService rubriqueService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/rubriques")
	public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Types Documents");
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activebanque", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-shopping-cart");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Rubrique de paie");
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
		{	mysociete=malist.get(0);
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "rubriques";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerrubrique", method = RequestMethod.POST)
	public @ResponseBody RubriqueDTO saveTypeDocument(@RequestParam(value="idR", required=false) Long id,
														@RequestParam(value="libelle", required=false) String libelle, 
														@RequestParam(value="etatImposition", required=false) Integer etatImposition, 
														@RequestParam(value="taux", required=false) Double taux, 
														@RequestParam(value="mtExedent", required=false) Double mtExedent, 
														@RequestParam(value="active", required=false) Boolean active,
                                                      @RequestParam(value="permanent", required=false) Boolean permanent,
                                                      @RequestParam(value="speciale", required=false) Boolean speciale) {
		logger.info(">>> ENREGISTRER TYPE DOCUMENT");
		return rubriqueService.save(id,libelle,etatImposition, taux, mtExedent,active,permanent,speciale);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerrubrique", method = RequestMethod.POST)
	public @ResponseBody RubriqueDTO deleteTypeDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER TYPE DOCUMENT");
		return rubriqueService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverrubrique", method = RequestMethod.POST)
	public @ResponseBody RubriqueDTO findTypeDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER TYPE DOCUMENT");
		return rubriqueService.findRubrique(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerrubriques", method = RequestMethod.POST)
	public @ResponseBody RubriqueDTO findTypesDocuments() {
		logger.info(">>> LISTE TYPES DOCUMENTS");
		return rubriqueService.findRubriques();
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadrubriques", method = RequestMethod.GET)
	public @ResponseBody RubriqueDTO getRubriqueListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE TYPES DOCUMENTS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		RubriqueDTO rubriqueDTO = new RubriqueDTO();
		
		//if(search == null || search == "")
			rubriqueDTO = rubriqueService.loadRubriques(pageRequest);
	//	else
			//rubriqueDTO = rubriqueService.loadRubriques(pageRequest, search);
		return rubriqueDTO;
	}

}
