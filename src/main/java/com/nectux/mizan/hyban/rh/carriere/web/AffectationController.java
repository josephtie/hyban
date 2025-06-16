package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
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
import com.nectux.mizan.hyban.rh.carriere.dto.AffectationDTO;
import com.nectux.mizan.hyban.rh.carriere.service.AffectationService;

@Controller
@RequestMapping("/carriere")
public class AffectationController {
	
	private static final Logger logger = LogManager.getLogger(AffectationController.class);
	
	@Autowired private AffectationService affectationService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	 
	@RequestMapping("/affectations")
	public String viewAffectation(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Affectations");

		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Affectations");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
 	    
		return "affectations";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistreraffectation", method = RequestMethod.POST)
	public @ResponseBody AffectationDTO saveAffectation(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="idPersonnel", required=true) Long idPersonnel, 
												@RequestParam(value="idPoste", required=true) Long idPoste,  
												@RequestParam(value="idSite", required=true) Long idSite,
												@RequestParam(value="statutAffect", required=false) Boolean statutAffect,
												@RequestParam(value="dateDebut", required=false) String dateDebut,
												@RequestParam(value="dateFin", required=false) String dateFin,  
												@RequestParam(value="observation", required=false) String observation) throws Exception {
		logger.info(">>> ENREGISTRER AFFECTATION");
		return affectationService.save(id, idPersonnel, idPoste,idSite ,statutAffect,dateDebut, dateFin, observation);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouveraffectation", method = RequestMethod.POST)
	public @ResponseBody AffectationDTO findAffectation(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER AFFECTATION");
		return affectationService.findAffectation(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listeraffectations", method = RequestMethod.POST)
	public @ResponseBody AffectationDTO findAffectations() {
		logger.info(">>> LISTE AFFECTATIONS");
		return affectationService.findAffectations();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listeraffectationsparpersonnel", method = RequestMethod.POST)
	public @ResponseBody AffectationDTO findAffectationsByPersonnel(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE AFFECTATIONS PAR PERSONNEL");
		return affectationService.findAffectationsByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listeraffectationsparposte", method = RequestMethod.POST)
	public @ResponseBody AffectationDTO findAffectationsByPoste(@RequestParam(value="idPoste", required=true) Long idPoste) {
		logger.info(">>> LISTE AFFECTATIONS PAR PERSONNEL");
		return affectationService.findAffectationsByPoste(idPoste);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimeraffectation", method = RequestMethod.POST)
	public @ResponseBody AffectationDTO deleteAffectation(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER AFFECTATION");
		return affectationService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pagineraffectations", method = RequestMethod.GET)
	public @ResponseBody AffectationDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE AFFECTATIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		AffectationDTO affectationDTO = new AffectationDTO();
		if(search == null || search == "")
			affectationDTO = affectationService.loadAffectations(pageRequest);
		else
			affectationDTO = affectationService.loadAffectations(pageRequest, search, search, search);
		
		return affectationDTO;
	}

}
