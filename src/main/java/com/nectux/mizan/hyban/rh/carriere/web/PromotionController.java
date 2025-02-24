package com.nectux.mizan.hyban.rh.carriere.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.rh.carriere.dto.PromotionDTO;
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

import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.service.PromotionService;

@Controller
@RequestMapping("/carriere")
public class PromotionController {
	
	private static final Logger logger = LogManager.getLogger(PromotionController.class);
	
	@Autowired private PromotionService promotionService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private SocieteService societeService;
	 
	@RequestMapping("/promotions")
	public String viewPromotion(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Promotions");
		
		modelMap.addAttribute("activeCareer", "active");
		modelMap.addAttribute("blockCareer", "block");
		modelMap.addAttribute("activePromotion", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-sitemap");
		modelMap.addAttribute("littleTitle", "Carri&egrave;re");
		modelMap.addAttribute("bigTitle", "Promotion");
		
		   Societe mysociete=null;
			  java.util.List<Societe> malist=societeService.findtsmois();
			  if(malist.size()>0)
				{	mysociete=malist.get(0);			
				modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		
		return "promotions";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerpromotion", method = RequestMethod.POST)
	public @ResponseBody
    PromotionDTO savePromotion(@RequestParam(value="id", required=false) Long id,
                               @RequestParam(value="libelle", required=false) String libelle,
                               @RequestParam(value="description", required=false) String description) throws Exception {
		logger.info(">>> ENREGISTRER PROMOTION");
		return promotionService.save(id, libelle, description);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverpromotion", method = RequestMethod.POST)
	public @ResponseBody PromotionDTO findPromotion(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER PROMOTION");
		return promotionService.findPromotion(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpromotions", method = RequestMethod.POST)
	public @ResponseBody PromotionDTO findPromotions() {
		logger.info(">>> LISTE PROMOTIONS");
		return promotionService.findPromotions();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerpromotion", method = RequestMethod.POST)
	public @ResponseBody PromotionDTO deletePromotion(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER PROMOTION");
		return promotionService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerpromotions", method = RequestMethod.GET)
	public @ResponseBody PromotionDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE PROMOTIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PromotionDTO promotionDTO = new PromotionDTO();
		if(search == null || search == "")
			promotionDTO = promotionService.loadPromotions(pageRequest);
		else
			promotionDTO = promotionService.loadPromotions(pageRequest, search, search);
		
		return promotionDTO;
	}

}