package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.formation.dto.FactureFormationDTO;
import com.nectux.mizan.hyban.rh.formation.service.FactureFormationService;
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
public class FactureFormationController {
	
	private static final Logger logger = LogManager.getLogger(FactureFormationController.class);
	
	@Autowired
    private FactureFormationService factureFormationService;
	@Autowired
    private SocieteService societeService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	 
	@RequestMapping("/factureformation")
	public String viewFactureFormation(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> FactureFormations");

		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formations");
		modelMap.addAttribute("bigTitle", "Facture Formation");
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
		return "factureformation";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerfactureformation", method = RequestMethod.POST)
	public @ResponseBody
    FactureFormationDTO saveFactureFormation(@RequestParam(value="id", required=false) Long id,
                                             @RequestParam(value="idCabinet", required=false) Long idCabinet,
                                             @RequestParam(value="idFormation", required=false) Long idFormation,
                                             @RequestParam(value="reference", required=false) String reference,
                                             @RequestParam(value="emission", required=false) String emission,
                                             @RequestParam(value="montant", required=false) Double montant,
                                             @RequestParam(value="statut", required=false) Boolean statut) throws Exception {
		logger.info(">>> ENREGISTRER FACTURE FORMATION");
		return factureFormationService.save(id, idCabinet, idFormation, reference, emission, montant, statut);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverfactureformation", method = RequestMethod.POST)
	public @ResponseBody
    FactureFormationDTO findFactureFormation(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER FACTURE FORMATION");
		return factureFormationService.findFactureFormation(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerfactureformations", method = RequestMethod.POST)
	public @ResponseBody
    FactureFormationDTO findFactureFormations() {
		logger.info(">>> LISTE FACTURE FORMATIONS");
		return factureFormationService.findFactureFormations();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerfactureformationsparformation", method = RequestMethod.POST)
	public @ResponseBody
    FactureFormationDTO findFactureFormationsByFormation(@RequestParam(value="id", required=false) Long idFormation) {
		logger.info(">>> LISTE FACTURE FORMATIONS");
		return factureFormationService.findFactureFormationByFormation(idFormation);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerfactureformation", method = RequestMethod.POST)
	public @ResponseBody
    FactureFormationDTO deleteFactureFormation(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER FACTURE FORMATION");
		return factureFormationService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerfactureformations", method = RequestMethod.GET)
	public @ResponseBody
    FactureFormationDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                        @RequestParam(value="offset", required=false) Integer offset,
                                        @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE FACTURE FORMATIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		FactureFormationDTO factureFormationDTO = new FactureFormationDTO();
		if(search == null || search == "")
			factureFormationDTO = factureFormationService.loadFactureFormations(pageRequest);
		else
			factureFormationDTO = factureFormationService.loadFactureFormations(pageRequest, search, search, search);
		
		return factureFormationDTO;
	}

}
