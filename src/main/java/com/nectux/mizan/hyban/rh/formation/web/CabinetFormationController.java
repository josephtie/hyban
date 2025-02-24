package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.web.PosteController;
import com.nectux.mizan.hyban.rh.formation.dto.CabinetFormationDTO;
import com.nectux.mizan.hyban.rh.formation.service.CabinetFormationService;
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
public class CabinetFormationController {
	
	private static final Logger logger = LogManager.getLogger(CabinetFormationController.class);
	@Autowired private SocieteService societeService;
	@Autowired
    private CabinetFormationService cabinetFormationService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/cabinetformation")
	public String viewPoste(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Postes");
		
		modelMap.addAttribute("activeTraining", "active");
		modelMap.addAttribute("blockTraining", "block");
		modelMap.addAttribute("activeFirmTraning", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));

		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formation");
		modelMap.addAttribute("bigTitle", "Cabinet Formation");
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
		return "cabinetformations";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrercabinetformation", method = RequestMethod.POST)
	public @ResponseBody
    CabinetFormationDTO saveCabinetFormation(@RequestParam(value="id", required=false) Long id,
                                             @RequestParam(value="nom", required=false) String nom,
                                             @RequestParam(value="email", required=false) String email,
                                             @RequestParam(value="adresse", required=false) String adresse,
                                             @RequestParam(value="telephone", required=false) String telephone) throws Exception {
		logger.info(">>> ENREGISTRER POSTE");
		return cabinetFormationService.save(id, nom, adresse, email, telephone);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouvercabinetformation", method = RequestMethod.POST)
	public @ResponseBody
    CabinetFormationDTO findPoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER POSTE");
		return cabinetFormationService.findCabinetFormation(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listercabinetformations", method = RequestMethod.POST)
	public @ResponseBody
    CabinetFormationDTO findPostes() {
		logger.info(">>> LISTE POSTES");
		return cabinetFormationService.findCabinetFormations();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimercabinetformation", method = RequestMethod.POST)
	public @ResponseBody
    CabinetFormationDTO deletePoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER POSTE");
		return cabinetFormationService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginercabinetformations", method = RequestMethod.GET)
	public @ResponseBody
    CabinetFormationDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                        @RequestParam(value="offset", required=false) Integer offset,
                                        @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE POSTES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		if(search == null || search == "")
			cabinetFormationDTO = cabinetFormationService.loadCabinetFormations(pageRequest);
		else
			cabinetFormationDTO = cabinetFormationService.loadCabinetFormations(pageRequest, search, search, search, search);
		
		return cabinetFormationDTO;
	}

}
