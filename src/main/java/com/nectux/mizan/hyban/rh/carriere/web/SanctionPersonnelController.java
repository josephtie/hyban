package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.rh.carriere.dto.SanctionPersonnelDTO;
import com.nectux.mizan.hyban.rh.carriere.service.SanctionPersonnelService;
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
@RequestMapping("/carriere")
public class SanctionPersonnelController {
	
	private static final Logger logger = LogManager.getLogger(SanctionPersonnelController.class);
	
	@Autowired private SanctionPersonnelService sanctionPersonnelService;
	@Autowired private UtilisateurService utilisateurService;
	 
	@RequestMapping("/sanctionpersonnel")
	public String viewSanctionPersonnel(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Sanction Personnel");
		
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Sanction Personnel");
		
		return "sanctionpersonnel";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrersanctionpersonnel", method = RequestMethod.POST)
	public @ResponseBody
    SanctionPersonnelDTO saveSanctionPersonnel(@RequestParam(value="id", required=false) Long id,
                                               @RequestParam(value="idPersonnel", required=false) Long idPersonnel,
                                               @RequestParam(value="idSanction", required=false) Long idSanction,
                                               @RequestParam(value="dateDebut", required=false) String dateDebut,
                                               @RequestParam(value="dateFin", required=false) String dateFin,
                                               @RequestParam(value="observation", required=false) String observation) throws Exception {
		logger.info(">>> ENREGISTRER SANCTION PERSONNEL");
		return sanctionPersonnelService.save(id, idPersonnel, idSanction, dateDebut, dateFin, observation);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouversanctionpersonnel", method = RequestMethod.POST)
	public @ResponseBody SanctionPersonnelDTO findSanctionPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER SANCTION PERSONNEL");
		return sanctionPersonnelService.findSanctionPersonnel(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listersanctionpersonnels", method = RequestMethod.POST)
	public @ResponseBody SanctionPersonnelDTO findSanctionPersonnels() {
		logger.info(">>> LISTE SANCTION PERSONNELS");
		return sanctionPersonnelService.findSanctionPersonnels();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listersanctionpersonnelsparpersonnel", method = RequestMethod.POST)
	public @ResponseBody SanctionPersonnelDTO findSanctionPersonnelsByPersonnel(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE SANCTION PERSONNEL PAR PERSONNEL");
		return sanctionPersonnelService.findSanctionPersonnelsByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listersanctionpersonnelsparsanction", method = RequestMethod.POST)
	public @ResponseBody SanctionPersonnelDTO findSanctionPersonnelsByPoste(@RequestParam(value="idSanction", required=true) Long idSanction) {
		logger.info(">>> LISTE SANCTION PERSONNELS PAR PERSONNEL");
		return sanctionPersonnelService.findSanctionPersonnelsBySanction(idSanction);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimersanctionpersonnel", method = RequestMethod.POST)
	public @ResponseBody SanctionPersonnelDTO deleteSanctionPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER SANCTION PERSONNEL");
		return sanctionPersonnelService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginersanctionPersonnels", method = RequestMethod.GET)
	public @ResponseBody SanctionPersonnelDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE SANCTION PERSONNELS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		SanctionPersonnelDTO sanctionPersonnelDTO = new SanctionPersonnelDTO();
		if(search == null || search == "")
			sanctionPersonnelDTO = sanctionPersonnelService.loadSanctionPersonnels(pageRequest);
		else
			sanctionPersonnelDTO = sanctionPersonnelService.loadSanctionPersonnels(pageRequest, search, search, search, search);
		
		return sanctionPersonnelDTO;
	}

}
