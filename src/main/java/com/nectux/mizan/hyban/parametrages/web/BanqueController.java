package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.dto.BanqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.BanqueService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;

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

@Controller
@RequestMapping("/parametrages")
public class BanqueController {
	
	private static final Logger logger = LogManager.getLogger(BanqueController.class);
	
	@Autowired private BanqueService banqueService;
//	@Autowired private UtilisateurService utilisateurService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/banques")
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
		modelMap.addAttribute("icon", "fa fa-money");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Banques");
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "banques";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerbanque", method = RequestMethod.POST)
	public @ResponseBody
    BanqueDTO saveTypeDocument(@RequestParam(value="id", required=false) Long id,
                               @RequestParam(value="sigle", required=false) String sigle,
                               @RequestParam(value="libelle", required=false) String libelle,
                               @RequestParam(value="codbanq", required=false) String codbanq,
                               @RequestParam(value="statut", required=false) Boolean statut) {
		logger.info(">>> ENREGISTRER TYPE DOCUMENT");
		return banqueService.save(id, sigle,libelle,codbanq,statut);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerbanque", method = RequestMethod.POST)
	public @ResponseBody BanqueDTO deleteTypeDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER TYPE DOCUMENT");
		return banqueService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverbanque", method = RequestMethod.POST)
	public @ResponseBody BanqueDTO findTypeDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER TYPE DOCUMENT");
		return banqueService.findBanque(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerbanques", method = RequestMethod.POST)
	public @ResponseBody BanqueDTO findTypesDocuments() {
		logger.info(">>> LISTE TYPES DOCUMENTS");
		return banqueService.findBanques();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerbanques", method = RequestMethod.GET)
	public @ResponseBody BanqueDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE TYPES DOCUMENTS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");

		
		BanqueDTO typeDocumentDTO = new BanqueDTO();
		if(search == null || search == "")
			typeDocumentDTO = banqueService.loadBanques(pageRequest);
		else
			typeDocumentDTO = banqueService.loadBanques(pageRequest, search);
		
		return typeDocumentDTO;
	}

}
