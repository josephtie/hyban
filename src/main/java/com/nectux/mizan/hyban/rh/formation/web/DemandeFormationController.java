package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.web.PosteController;
import com.nectux.mizan.hyban.rh.formation.dto.DemandeFormationDTO;
import com.nectux.mizan.hyban.rh.formation.service.DemandeFormationService;
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
public class DemandeFormationController {
	
	private static final Logger logger = LogManager.getLogger(PosteController.class);
	@Autowired private SocieteService societeService;
	@Autowired
    private DemandeFormationService demandeFormationService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	@Autowired
    private ExerciceRepository exerciceRepository;


	@RequestMapping("/demandeformation")
	public String viewPoste(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Postes");
		
		modelMap.addAttribute("activeTraining", "active");
		modelMap.addAttribute("blockTraining", "block");
		modelMap.addAttribute("activeTrainingRequest", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formation");
		modelMap.addAttribute("bigTitle", "Demande Formation");
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
        modelMap.addAttribute("listeAnnee", exerciceRepository.findAll());


		return "demandeformation";
	}

	@RequestMapping("/demandeformValide")
	public String viewdemandeformValide(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Postes");

		modelMap.addAttribute("activeTraining", "active");
		modelMap.addAttribute("blockTraining", "block");
		modelMap.addAttribute("activeTrainingRequest", "active");
		modelMap.addAttribute("user", utilisateurService.findByEmail(principal.getName()));
		modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formation");
		modelMap.addAttribute("bigTitle", "Demande Formation Valid&eacute;");
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
        modelMap.addAttribute("listeAnnee", exerciceRepository.findAll());


		return "demandeformValide";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerdemandeformation", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO saveDemandeFormation(@RequestParam(value="id", required=false) Long id,
                                             @RequestParam(value="idService", required=false) Long idService,
                                             @RequestParam(value="idAnnee", required=false) Long idAnnee,
                                             @RequestParam(value="objet", required=false) String objet,
                                             @RequestParam(value="commentaire", required=false) String commentaire,
                                             @RequestParam(value="dateDemande", required=false) String dateDemande) throws Exception {
		logger.info(">>> ENREGISTRER POSTE");
		return demandeFormationService.save(id, idService,idAnnee, objet, commentaire, dateDemande);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverdemandeformation", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO findPoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER POSTE");
		return demandeFormationService.findDemandeFormation(id);
	}
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/validerdemandeformation", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO findformation(@RequestParam(value="id", required=false) Long id, @RequestParam(value="dateValide", required=false) String dateValide, @RequestParam(value="jalios", required=false) Integer EtatDde) {
		logger.info(">>> TROUVER POSTE");
		return demandeFormationService.findDemandeFormationValide(id,dateValide,EtatDde);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/refuserdemandeformation", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO refuserdemandeformation(@RequestParam(value="id", required=false) Long id, @RequestParam(value="dateValide", required=false) String dateValide, @RequestParam(value="touche", required=false) Integer EtatDde) {
		logger.info(">>> TROUVER POSTE");
		return demandeFormationService.findDemandeFormationValide(id,dateValide,EtatDde);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerdemandeformations", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO findPostes() {
		logger.info(">>> LISTE POSTES");
		return demandeFormationService.findDemandeFormations();
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerdemandeformationsValide", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO findPostesvaides() {
		logger.info(">>> LISTE POSTES");
		return demandeFormationService.findDemandeFormationsValiid();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerdemandeformation", method = RequestMethod.POST)
	public @ResponseBody
    DemandeFormationDTO deletePoste(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER POSTE");
		return demandeFormationService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerdemandeformations", method = RequestMethod.GET)
	public @ResponseBody
    DemandeFormationDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                        @RequestParam(value="offset", required=false) Integer offset,
                                        @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE POSTES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/limit, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		DemandeFormationDTO cabinetFormationDTO = new DemandeFormationDTO();
		if(search == null || search == "")
			cabinetFormationDTO = demandeFormationService.loadDemandeFormations(pageRequest);
		else
			cabinetFormationDTO = demandeFormationService.loadDemandeFormations(pageRequest, search, search, search);
		
		return cabinetFormationDTO;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerdemandeformationsValide", method = RequestMethod.GET)
	public @ResponseBody
    DemandeFormationDTO getUserListlJSON(@RequestParam(value="limit", required=false) Integer limit,
                                         @RequestParam(value="offset", required=false) Integer offset,
                                         @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE POSTES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/limit, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		DemandeFormationDTO cabinetFormationDTO = new DemandeFormationDTO();
		if(search == null || search == "")
			cabinetFormationDTO = demandeFormationService.loadDemandeFormationsValid(pageRequest,1);
		else
			cabinetFormationDTO = demandeFormationService.loadDemandeFormationsValid(pageRequest,1 ,search, search, search);

		return cabinetFormationDTO;
	}

}
