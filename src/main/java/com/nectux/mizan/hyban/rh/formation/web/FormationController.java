package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.web.PosteController;
import com.nectux.mizan.hyban.rh.formation.dto.FormationDTO;
import com.nectux.mizan.hyban.rh.formation.repository.CabinetFormationRepository;
import com.nectux.mizan.hyban.rh.formation.service.FormationService;
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
public class FormationController {
	
	private static final Logger logger = LogManager.getLogger(PosteController.class);
	
	@Autowired
    private FormationService formationService;
	@Autowired
    private SocieteService societeService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	@Autowired
    private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired
    private CabinetFormationRepository cabinetFormationRepository;

	@RequestMapping("/formations")
	public String viewPoste(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Formations");
		
		modelMap.addAttribute("activeTraining", "active");
		modelMap.addAttribute("blockTraining", "block");
		modelMap.addAttribute("activeTrainingTraining", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formation");
		modelMap.addAttribute("bigTitle", "Formation");
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
		modelMap.addAttribute("listeContrat",  contratPersonnelRepository.findByStatutTrueAndDepartFalseOrderByPersonnelNomAscPersonnelPrenomAsc());
		modelMap.addAttribute("listeCabinet",  cabinetFormationRepository.findAll());

		return "formations";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerformation", method = RequestMethod.POST)
	public @ResponseBody
    FormationDTO saveFormation(@RequestParam(value="id", required=false) Long id,
                               @RequestParam(value="idTheme", required=false) Long idTheme,
                               @RequestParam(value="idDemandeFormation", required=false) Long idDemandeFormation,
                               @RequestParam(value="idCabinetFormation", required=false) Long idCabinetFormation,
                               @RequestParam(value="intitule", required=false) String intitule,
                               @RequestParam(value="dateDebut", required=false) String dateDebut,
                               @RequestParam(value="dateFin", required=false) String dateFin,
                               @RequestParam(value="datePrevue", required=false) String datePrevue,
                               @RequestParam(value="lieu", required=false) String lieu,
                               @RequestParam(value="participant", required=false) int participant,
                               @RequestParam(value="planFormation", required=false) String planFormation,
                               @RequestParam(value="objectif", required=false) String objectif,
                               @RequestParam(value="commentaire", required=false) String commentaire) throws Exception {
		logger.info(">>> ENREGISTRER FORMATION");
		return formationService.save(id, idTheme, idDemandeFormation,idCabinetFormation, intitule, dateDebut, dateFin, datePrevue, lieu, participant, planFormation, objectif, commentaire);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverformation", method = RequestMethod.POST)
	public @ResponseBody
    FormationDTO findFormation(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER FORMATION");
		return formationService.findFormation(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerformations", method = RequestMethod.POST)
	public @ResponseBody
    FormationDTO findPostes() {
		logger.info(">>> LISTE FORMATIONS");
		return formationService.findFormations();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerformation", method = RequestMethod.POST)
	public @ResponseBody
    FormationDTO deletePoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER FORMATION");
		return formationService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerformations", method = RequestMethod.GET)
	public @ResponseBody
    FormationDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                 @RequestParam(value="offset", required=false) Integer offset,
                                 @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE FORMATIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		FormationDTO cabinetFormationDTO = new FormationDTO();
		if(search == null || search == "")
			cabinetFormationDTO = formationService.loadFormations(pageRequest);
		else
			cabinetFormationDTO = formationService.loadFormations(pageRequest, search, search, search, search);
		
		return cabinetFormationDTO;
	}

}
