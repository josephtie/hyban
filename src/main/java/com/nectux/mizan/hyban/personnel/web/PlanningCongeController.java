package com.nectux.mizan.hyban.personnel.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.PlanningCongeService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.web.UtilisateurController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nectux.mizan.hyban.parametrages.dto.PlanningCongeDTO;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.utils.DateManager;

@Controller
@RequestMapping("/personnels")
public class PlanningCongeController {
	
	private static final Logger logger = LogManager.getLogger(UtilisateurController.class);
	
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PlanningCongeService planningCongeService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/planningconges")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Planning Conges");
		
		modelMap.addAttribute("activeEmployers", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activeHoliday", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Personnels");
		modelMap.addAttribute("bigTitle", "Planning Cong&eacute;s");
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
		return "planningconges";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listplanningcongejson", method = RequestMethod.GET)
	public @ResponseBody PlanningCongeDTO getPlanningCongeListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		
		 if(offset == null) offset = 0;
		 if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "dateDepart");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "dateDepart");
		PlanningCongeDTO planningCongeDTO = new PlanningCongeDTO();
		if(search == null)
			planningCongeDTO = planningCongeService.loadPlanningConge(pageRequest);
		else
			planningCongeDTO = planningCongeService.loadPlanningConge(pageRequest, search);
		
		return planningCongeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/choisirplanningconge", method = RequestMethod.POST)
	public @ResponseBody PlanningConge selectPlanningConge(@RequestParam(value="id", required=true) Long id) { 
		
		return planningCongeService.findPlanningConge(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerplanningconge", method = RequestMethod.POST)
	public @ResponseBody PlanningCongeDTO savePlanningConge(@RequestParam(value="id", required=true) Long id,  
													@RequestParam(value="dateDepart", required=true) String dateDepart) throws Exception { 
		
		return planningCongeService.save(id, DateManager.stringToDate(dateDepart, "dd/MM/yyyy"));
	}

}
