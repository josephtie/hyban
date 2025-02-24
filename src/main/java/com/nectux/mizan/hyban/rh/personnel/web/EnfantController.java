package com.nectux.mizan.hyban.rh.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.rh.personnel.service.EnfantService;
import com.nectux.mizan.hyban.utils.DateManager;
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
import com.nectux.mizan.hyban.rh.personnel.dto.EnfantDTO;

@Controller
@RequestMapping("/personnel")
public class EnfantController {
	
	private static final Logger logger = LogManager.getLogger(EnfantController.class);
	
	@Autowired private EnfantService enfantService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	 
	@RequestMapping("/enfants")
	public String viewEnfant(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Enfants");
		
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Enfants");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
		return "enfants";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerenfant", method = RequestMethod.POST)
	public @ResponseBody EnfantDTO saveEnfant(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="idPersonnel", required=true) Long idPersonnel, 
												@RequestParam(value="matricule", required=false) String matricule,  
												@RequestParam(value="nom", required=false) String nom,  
												@RequestParam(value="dateNaissance", required=false) String dateNaiss,  
												@RequestParam(value="lieuNaissance", required=false) String lieuNaissance, 
												@RequestParam(value="sexe", required=false) char sexe) throws Exception {
		logger.info(">>> ENREGISTRER ENFANT");
		Date dateNaissance = DateManager.stringToDate(dateNaiss, "dd/MM/yyyy");
		return enfantService.save(id, idPersonnel, matricule, nom, dateNaissance, lieuNaissance, sexe);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverenfant", method = RequestMethod.POST)
	public @ResponseBody EnfantDTO findEnfant(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER ENFANT");
		return enfantService.findEnfant(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerenfants", method = RequestMethod.POST)
	public @ResponseBody EnfantDTO findEnfants() {
		logger.info(">>> LISTE ENFANTS");
		return enfantService.findEnfants();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerenfantsparpersonnel", method = RequestMethod.POST)
	public @ResponseBody EnfantDTO findEnfants(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE ENFANTS PAR PERSONNEL");
		return enfantService.findEnfantsByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerenfant", method = RequestMethod.POST)
	public @ResponseBody EnfantDTO deleteEnfant(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER ENFANT");
		return enfantService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerenfants", method = RequestMethod.GET)
	public @ResponseBody EnfantDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE ENFANTS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		EnfantDTO enfantDTO = new EnfantDTO();
		if(search == null || search == "")
			enfantDTO = enfantService.loadEnfants(pageRequest);
		else
			enfantDTO = enfantService.loadEnfants(pageRequest);
		
		return enfantDTO;
	}

}
