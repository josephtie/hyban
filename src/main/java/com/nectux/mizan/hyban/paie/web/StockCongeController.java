package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.paie.dto.StockCongeDTO;
import com.nectux.mizan.hyban.paie.service.StockCongeService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
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

import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

@Controller
@RequestMapping("/personnel")
public class StockCongeController {
	
	private static final Logger logger = LogManager.getLogger(StockCongeController.class);
	
	@Autowired private StockCongeService stockCongeService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private UtilisateurService utilisateurService;
	 
	@RequestMapping("/stockconges")
	public String viewEnfant(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> StockConges");

		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Stock Conge");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
	    if(periodePaie != null){
	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", periodePaie.getId());
			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
	    }
		return "stockconges";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerstockconge", method = RequestMethod.POST)
	public @ResponseBody
    StockCongeDTO saveStockConge(@RequestParam(value="id", required=false) Long id,
                                 @RequestParam(value="idConge", required=true) Long idConge,
                                 @RequestParam(value="dateDepart", required=false) String dateDepart,
                                 @RequestParam(value="dateRetour", required=false) String dateRetour,
                                 @RequestParam(value="montantVerse", required=false) Double montantVerse) throws Exception {
		logger.info(">>> ENREGISTRER STOCK CONGE");
		return stockCongeService.save(id, idConge, dateDepart, dateRetour, montantVerse);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverstockconge", method = RequestMethod.POST)
	public @ResponseBody StockCongeDTO findEnfant(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER STOCK CONGE");
		return stockCongeService.findStockConge(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerstockconges", method = RequestMethod.POST)
	public @ResponseBody StockCongeDTO findStockConges() {
		logger.info(">>> LISTE STOCK CONGES");
		return stockCongeService.findStockConges();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerstockcongesparpersonnel", method = RequestMethod.POST)
	public @ResponseBody StockCongeDTO findStockConges(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE STOCK CONGES");
		return stockCongeService.findStockCongesByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerstockconge", method = RequestMethod.POST)
	public @ResponseBody StockCongeDTO deleteStockConge(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER STOCK CONGE");
		return stockCongeService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerstockconges", method = RequestMethod.GET)
	public @ResponseBody StockCongeDTO getStockCongeListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE STOCK CONGES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		
		StockCongeDTO enfantDTO = new StockCongeDTO();
		if(search == null || search == "")
			enfantDTO = stockCongeService.loadStockConges(pageRequest);
		else
			enfantDTO = stockCongeService.loadStockConges(pageRequest);
		
		return enfantDTO;
	}

}
