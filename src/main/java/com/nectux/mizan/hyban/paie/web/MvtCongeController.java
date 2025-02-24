package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.paie.dto.MvtCongeDTO;
import com.nectux.mizan.hyban.paie.service.MvtCongeService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
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
public class MvtCongeController {
	
	private static final Logger logger = LogManager.getLogger(MvtCongeController.class);
	
	@Autowired private MvtCongeService mvtCongeService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private SocieteService societeService;
	@RequestMapping("/mvtconges")
	public String viewEnfantmvt(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> StockConges");
		
		modelMap.addAttribute("user", utilisateurService.findByEmail(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Stock Conge");
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
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "mvtconges";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrermvtconge", method = RequestMethod.POST)
	public @ResponseBody
    MvtCongeDTO savemvtConge(@RequestParam(value="id", required=false) Long id,
                             @RequestParam(value="idPersonnel", required=true) Long idPersonnel,
                             @RequestParam(value="dateDepart", required=false) String dateDepart,
                             @RequestParam(value="dateRetour", required=false) String dateRetour,
                             @RequestParam(value="montantVerse", required=false) Double montantVerse) throws Exception {
		logger.info(">>> ENREGISTRER STOCK CONGE");
		return mvtCongeService.save(id, idPersonnel, dateDepart, dateRetour, montantVerse);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouvermvtconge", method = RequestMethod.POST)
	public @ResponseBody MvtCongeDTO findEnfantmvt(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER STOCK CONGE");
		return mvtCongeService.findMvtConge(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listermvtconges", method = RequestMethod.POST)
	public @ResponseBody MvtCongeDTO findmvtConges() {
		logger.info(">>> LISTE STOCK CONGES");
		return mvtCongeService.findMvtConges();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listermvtcongesparpersonnel", method = RequestMethod.POST)
	public @ResponseBody MvtCongeDTO findStockConges(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE STOCK CONGES");
		return mvtCongeService.findMvtCongesByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimermvtconge", method = RequestMethod.POST)
	public @ResponseBody MvtCongeDTO deleteMvtConge(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER STOCK CONGE");
		return mvtCongeService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginermvtconges", method = RequestMethod.GET)
	public @ResponseBody MvtCongeDTO getMvtCongeListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE STOCK CONGES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		
		MvtCongeDTO enfantDTO = new MvtCongeDTO();
		if(search == null || search == "")
			enfantDTO = mvtCongeService.loadMvtConges(pageRequest);
		else
			enfantDTO = mvtCongeService.loadMvtConges(pageRequest);
		
		return enfantDTO;
	}

}
