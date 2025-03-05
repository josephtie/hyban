package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.rh.carriere.dto.PromotionPersonnelDTO;
import com.nectux.mizan.hyban.rh.carriere.service.PromotionPersonnelService;
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
public class PromotionPersonnelController {
	
	private static final Logger logger = LogManager.getLogger(PromotionPersonnelController.class);
	
	@Autowired private PromotionPersonnelService promotionPersonnelService;
	@Autowired private UtilisateurService utilisateurService;
	 
	@RequestMapping("/promotionpersonnel")
	public String viewPromotionPersonnel(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> PromotionPersonnel");

		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Promotion Personnel");
		
		return "promotionPersonnel";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerpromotionpersonnel", method = RequestMethod.POST)
	public @ResponseBody
    PromotionPersonnelDTO savePromotionPersonnel(@RequestParam(value="id", required=false) Long id,
                                                 @RequestParam(value="idPersonnel", required=true) Long idPersonnel,
                                                 @RequestParam(value="idPromotion", required=true) Long idPromotion,
                                                 @RequestParam(value="datePromotion", required=false) String datePromotion,
                                                 @RequestParam(value="commentaire", required=false) String commentaire) throws Exception {
		logger.info(">>> ENREGISTRER PROMOTION PERSONNEL");
		return promotionPersonnelService.save(id, idPersonnel, idPromotion, datePromotion, commentaire);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverpromotionpersonnel", method = RequestMethod.POST)
	public @ResponseBody PromotionPersonnelDTO findPromotionPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER PROMOTION PERSONNEL");
		return promotionPersonnelService.findPromotionPersonnel(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpromotionpersonnels", method = RequestMethod.POST)
	public @ResponseBody PromotionPersonnelDTO findPromotionPersonnels() {
		logger.info(">>> LISTE PROMOTION PERSONNELS");
		return promotionPersonnelService.findPromotionPersonnels();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpromotionpersonnelsparpersonnel", method = RequestMethod.POST)
	public @ResponseBody PromotionPersonnelDTO findPromotionPersonnelsByPersonnel(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE PROMOTION PERSONNELS PAR PERSONNEL");
		return promotionPersonnelService.findPromotionPersonnelsByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpromotionpersonnelsparpromotion", method = RequestMethod.POST)
	public @ResponseBody PromotionPersonnelDTO findPromotionPersonnelsByPoste(@RequestParam(value="idPromotion", required=true) Long idPromotion) {
		logger.info(">>> LISTE PROMOTION PERSONNELS PAR PERSONNEL");
		return promotionPersonnelService.findPromotionPersonnelsByPromotion(idPromotion);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerpromotionpersonnel", method = RequestMethod.POST)
	public @ResponseBody PromotionPersonnelDTO deletePromotionPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER PROMOTION PERSONNEL");
		return promotionPersonnelService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerpromotionpersonnels", method = RequestMethod.GET)
	public @ResponseBody PromotionPersonnelDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE PROMOTION PERSONNELS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PromotionPersonnelDTO promotionPersonnelDTO = new PromotionPersonnelDTO();
		if(search == null || search == "")
			promotionPersonnelDTO = promotionPersonnelService.loadPromotionPersonnels(pageRequest);
		else
			promotionPersonnelDTO = promotionPersonnelService.loadPromotionPersonnels(pageRequest, search, search, search);
		
		return promotionPersonnelDTO;
	}

}
