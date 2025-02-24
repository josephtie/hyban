package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.dto.TypeDocumentDTO;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.TypeDocumentService;
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
public class TypeDocumentController {
	
	private static final Logger logger = LogManager.getLogger(TypeDocumentController.class);
	
	@Autowired private TypeDocumentService typeDocumentService;
	@Autowired private UtilisateurService utilisateurService;
	 
	@RequestMapping("/typedocument")
	public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Types Documents");

		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Types Documents");
		
		return "typedocument";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrertypedocument", method = RequestMethod.POST)
	public @ResponseBody
    TypeDocumentDTO saveTypeDocument(@RequestParam(value="id", required=false) Long id,
                                     @RequestParam(value="libelle", required=false) String libelle) {
		logger.info(">>> ENREGISTRER TYPE DOCUMENT");
		return typeDocumentService.save(id, libelle);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimertypedocument", method = RequestMethod.POST)
	public @ResponseBody TypeDocumentDTO deleteTypeDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER TYPE DOCUMENT");
		return typeDocumentService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouvertypedocument", method = RequestMethod.POST)
	public @ResponseBody TypeDocumentDTO findTypeDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER TYPE DOCUMENT");
		return typeDocumentService.findTypeDocument(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listertypesdocuments", method = RequestMethod.POST)
	public @ResponseBody TypeDocumentDTO findTypesDocuments() {
		logger.info(">>> LISTE TYPES DOCUMENTS");
		return typeDocumentService.findTypesDocumments();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginertypesdocuments", method = RequestMethod.GET)
	public @ResponseBody TypeDocumentDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE TYPES DOCUMENTS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		TypeDocumentDTO typeDocumentDTO = new TypeDocumentDTO();
		if(search == null || search == "")
			typeDocumentDTO = typeDocumentService.loadTypesDocuments(pageRequest);
		else
			typeDocumentDTO = typeDocumentService.loadTypesDocuments(pageRequest, search);
		
		return typeDocumentDTO;
	}

}
