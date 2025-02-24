package com.nectux.mizan.hyban.rh.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

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

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.personnel.dto.ConjointDTO;
import com.nectux.mizan.hyban.rh.personnel.service.ConjointService;
import com.nectux.mizan.hyban.utils.DateManager;

@Controller
@RequestMapping("/personnel")
public class ConjointController {
	
	private static final Logger logger = LogManager.getLogger(ConjointController.class);
	
	@Autowired private ConjointService conjointService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	 
	@RequestMapping("/conjoints")
	public String viewEnfant(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Conjoints");
		
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Conjoints");
		
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
		
		return "enfants";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerconjoint", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO saveConjoint(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="idPersonnel", required=true) Long idPersonnel, 
												@RequestParam(value="matricule", required=false) String matricule,  
												@RequestParam(value="nom", required=false) String nom,  
												@RequestParam(value="dateNaissance", required=false) String dateNaiss,  
												@RequestParam(value="lieuNaissance", required=false) String lieuNaissance, 
												@RequestParam(value="lieuResidence", required=false) String lieuResidence, 
												@RequestParam(value="telephone", required=false) String telephone, 
												@RequestParam(value="email", required=false) String email, 
												@RequestParam(value="sexe", required=false) char sexe) throws Exception {
		logger.info(">>> ENREGISTRER CONJOINT");
		Date dateNaissance = DateManager.stringToDate(dateNaiss, "dd/MM/yyyy");
		return conjointService.save(id, idPersonnel, matricule, nom, dateNaissance, lieuNaissance, lieuResidence, telephone, email, sexe);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverconjoint", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO findEnfant(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER CONJOINT");
		return conjointService.findConjoint(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerconjoints", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO findConjoints() {
		logger.info(">>> LISTE CONJOINTS");
		return conjointService.findConjoints();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerconjointsparpersonnel", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO findConjoints(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE CONJOINTS");
		return conjointService.findConjointsByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/activerconjoint", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO enableConjoint(@RequestParam(value="id", required=false) Long id, 
													@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> ACTIVER CONJOINT");
		return conjointService.enable(id, idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/desactiverconjoint", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO disableConjoint(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> DESACTIVER CONJOINT");
		return conjointService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerconjoint", method = RequestMethod.POST)
	public @ResponseBody ConjointDTO deleteConjoint(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER CONJOINT");
		return conjointService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerconjoints", method = RequestMethod.GET)
	public @ResponseBody ConjointDTO getConjointListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE CONJOINTS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		ConjointDTO enfantDTO = new ConjointDTO();
		if(search == null || search == "")
			enfantDTO = conjointService.loadConjoints(pageRequest);
		else
			enfantDTO = conjointService.loadConjoints(pageRequest);
		
		return enfantDTO;
	}

}
