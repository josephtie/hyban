package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.web.PosteController;
import com.nectux.mizan.hyban.rh.formation.dto.FormationPersonnelDTO;
import com.nectux.mizan.hyban.rh.formation.service.FormationPersonnelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/formation")
public class FormationPersonnelController {
	
	private static final Logger logger = LogManager.getLogger(PosteController.class);


	@Autowired
    private FormationPersonnelService formationPersonnelService;
	@Autowired
    private SocieteService societeService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/formationpersonnel")
	public String viewPoste(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Formation Personnel");

		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formations");
		modelMap.addAttribute("bigTitle", "Participants Formation");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }

		Societe mysociete=null;
		java.util.List<Societe> malist=societeService.findtsmois();
		if(malist.size()>0)
		{	mysociete=malist.get(0);
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "formationpersonnel";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerformationpersonnel", method = RequestMethod.POST)
	public @ResponseBody
    FormationPersonnelDTO saveFormationPeronnel(@RequestParam(value="id", required=false) Long id,
                                                @RequestParam(value="idFormation", required=false) Long idFormation,
                                                @RequestParam(value="idPersonnel", required=false) Long idPersonnel) throws Exception {
		logger.info(">>> ENREGISTRER FORMATION PERSONNEL");
		return formationPersonnelService.save(id, idFormation, idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerformationpersonnellist", method = RequestMethod.POST)
	public @ResponseBody
    FormationPersonnelDTO savePointagesByPersonnelsAndDate(
			@RequestParam(value="listSize", required=false) int listPersonnelSize,
			@RequestParam(value="listPersonnel", required=false) String listPersonnel,
			@RequestParam(value="id", required=false) Long idFormation) throws Exception {
		System.out.println("variable personnel  "+listPersonnel);
		return formationPersonnelService.save(listPersonnel, listPersonnelSize, idFormation);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverformationpersonnel", method = RequestMethod.POST)
	public @ResponseBody
    FormationPersonnelDTO findFormationPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER FORMATION PERSONNEL");
		return formationPersonnelService.findFormationPersonnel(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerformationpersonnels", method = RequestMethod.POST)
	public @ResponseBody
    FormationPersonnelDTO findPostes() {
		logger.info(">>> LISTE FORMATION PERSONNELS");
		return formationPersonnelService.findFormationPersonnels();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerformationpersonnelsparlistpersonneletidformation", method = RequestMethod.POST)
	public @ResponseBody
    FormationPersonnelDTO findPointagesByPersonnelsAndDate(@RequestParam(value="id", required=false) Long id,
                                                           @RequestParam(value="listSize", required=false) int listSize,
                                                           @RequestParam(value="listPersonnel", required=false) String list,
                                                           @RequestParam(value="idFormation", required=false) Long idFormation) throws Exception {
		
		return formationPersonnelService.findFormationPersonnel(list, listSize, idFormation);
	}



	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerformationpersonnel", method = RequestMethod.POST)
	public @ResponseBody
    FormationPersonnelDTO deletePoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER FORMATION PERSONNEL");
		return formationPersonnelService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerformationpersonnels", method = RequestMethod.GET)
	public @ResponseBody
    FormationPersonnelDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                          @RequestParam(value="offset", required=false) Integer offset,
                                          @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE FORMATION PERSONNELS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/limit, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		if(search == null || search == "")
			formationPersonnelDTO = formationPersonnelService.loadFormationPersonnels(pageRequest);
		else
			formationPersonnelDTO = formationPersonnelService.loadFormationPersonnels(pageRequest, search, search, search);
		
		return formationPersonnelDTO;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerformationpersonnelsduneformation", method = RequestMethod.GET)
	public @ResponseBody
    FormationPersonnelDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                          @RequestParam(value="offset", required=false) Integer offset,
                                          @RequestParam(value="search", required=false) String search,
                                          @RequestParam(value="id", required=false) Long id, Principal principal) {
		logger.info(">>> LISTE FORMATION PERSONNELS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/limit, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		FormationPersonnelDTO formationPersonnelDTO = new FormationPersonnelDTO();
		if(search == null || search == "")
			formationPersonnelDTO = formationPersonnelService.loadFormationPersonnelsduneFormation(pageRequest,id);
		else
			formationPersonnelDTO = formationPersonnelService.loadFormationPersonnelsduneFormation(pageRequest,id, search, search, search);

		return formationPersonnelDTO;
	}

}
