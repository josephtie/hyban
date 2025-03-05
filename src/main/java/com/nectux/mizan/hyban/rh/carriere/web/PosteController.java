package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.rh.carriere.dto.PosteDTO;
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
import com.nectux.mizan.hyban.rh.carriere.service.PosteService;

@Controller
@RequestMapping("/carriere")
public class PosteController {
	
	private static final Logger logger = LogManager.getLogger(PosteController.class);
	
	@Autowired private PosteService posteService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private SocieteService societeService;
	@RequestMapping("/postes")
	public String viewPoste(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Postes");
		
		modelMap.addAttribute("activeCareer", "active");
		modelMap.addAttribute("blockCareer", "block");
		modelMap.addAttribute("activePosition", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-sitemap");
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Poste");
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
	
		
		return "postes";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerposte", method = RequestMethod.POST)
	public @ResponseBody
    PosteDTO savePoste(@RequestParam(value="id", required=false) Long id,
                       @RequestParam(value="libelle", required=false) String libelle,
                       @RequestParam(value="description", required=false) String description) throws Exception {
		logger.info(">>> ENREGISTRER POSTE");
		return posteService.save(id, libelle, description);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverposte", method = RequestMethod.POST)
	public @ResponseBody PosteDTO findPoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER POSTE");
		return posteService.findPoste(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpostes", method = RequestMethod.POST)
	public @ResponseBody PosteDTO findPostes() {
		logger.info(">>> LISTE POSTES");
		return posteService.findPostes();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerposte", method = RequestMethod.POST)
	public @ResponseBody PosteDTO deletePoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER POSTE");
		return posteService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerpostes", method = RequestMethod.GET)
	public @ResponseBody PosteDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE POSTES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PosteDTO posteDTO = new PosteDTO();
		if(search == null || search == "")
			posteDTO = posteService.loadPostes(pageRequest);
		else
			posteDTO = posteService.loadPostes(pageRequest, search, search);
		
		return posteDTO;
	}

}
