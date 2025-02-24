package com.nectux.mizan.hyban.rh.personnel.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.rh.personnel.service.PersonnePrevenirService;
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
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.personnel.dto.PersonnePrevenirDTO;

@Controller
@RequestMapping("/personnel")
public class PersonnePrevenirController {
	
	private static final Logger logger = LogManager.getLogger(PersonnePrevenirController.class);
	
	@Autowired private PersonnePrevenirService personnePrevenirService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	@RequestMapping("/personnesprevenir")
	public String viewEnfant(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Personne a Prevenir");
		
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Personne &agrave; Prevenir");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
		return "enfants";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerpersonneprevenir", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO savePersonnePrevenir(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="idPersonnel", required=true) Long idPersonnel, 
												@RequestParam(value="filiation", required=false) String filiation,  
												@RequestParam(value="nom", required=false) String nom,  
												@RequestParam(value="telephone", required=false) String telephone, 
												@RequestParam(value="email", required=false) String email, 
												@RequestParam(value="sexe", required=false) char sexe) throws Exception {
		logger.info(">>> ENREGISTRER PERSONNE A PREVENIR");
		return personnePrevenirService.save(id, idPersonnel, filiation, nom, telephone, email, sexe);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverpersonneprevenir", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO findEnfant(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER PERSONNE A PREVENIR");
		return personnePrevenirService.findPersonnePrevenir(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpersonnesprevenir", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO findPersonnePrevenirs() {
		logger.info(">>> LISTE PERSONNES A PREVENIR");
		return personnePrevenirService.findPersonnesPrevenir();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpersonnesprevenirparpersonnel", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO findPersonnePrevenirs(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE PERSONNES A PREVENIR");
		return personnePrevenirService.findPersonnesPrevenirByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/activerpersonneprevenir", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO enablePersonnePrevenir(@RequestParam(value="id", required=false) Long id, 
													@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> ACTIVER PERSONNE A PREVENIR");
		return personnePrevenirService.enable(id, idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/desactiverpersonneprevenir", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO disablePersonnePrevenir(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> DESACTIVER PERSONNE A PREVENIR");
		return personnePrevenirService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerpersonneprevenir", method = RequestMethod.POST)
	public @ResponseBody PersonnePrevenirDTO deletePersonnePrevenir(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER PERSONNE A PREVENIR");
		return personnePrevenirService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerpersonnesprevenir", method = RequestMethod.GET)
	public @ResponseBody PersonnePrevenirDTO getPersonnePrevenirListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE PERSONNES A PREVENIR AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
	//	final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PersonnePrevenirDTO enfantDTO = new PersonnePrevenirDTO();
		if(search == null || search == "")
			enfantDTO = personnePrevenirService.loadPersonnesPrevenir(pageRequest);
		else
			enfantDTO = personnePrevenirService.loadPersonnesPrevenir(pageRequest);
		
		return enfantDTO;
	}

}
