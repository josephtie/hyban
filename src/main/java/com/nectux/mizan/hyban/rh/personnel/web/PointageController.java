package com.nectux.mizan.hyban.rh.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.rh.personnel.dto.PointageDTO;
import com.nectux.mizan.hyban.rh.personnel.service.PointageService;

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
public class PointageController {
	
	private static final Logger logger = LogManager.getLogger(PointageController.class);
	
	@Autowired private PointageService pointageService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/pointages")
	public String viewPointage(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Pointages");
		
		modelMap.addAttribute("activeTicking", "active");
		modelMap.addAttribute("blockEmployer", "block");
		modelMap.addAttribute("activePointage", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-group");
		modelMap.addAttribute("littleTitle", "Personnel");
		modelMap.addAttribute("bigTitle", "Pointage");
		
		
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
 	   Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "pointages";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerpointage", method = RequestMethod.POST)
	public @ResponseBody
    PointageDTO savePointage(@RequestParam(value="id", required=false) Long id,
                             @RequestParam(value="idPersonnel", required=false) Long idPersonnel,
                             @RequestParam(value="datePointage", required=false) String datePointage,
                             @RequestParam(value="heureArrivee", required=false) String heureArrivee,
                             @RequestParam(value="heureDepart", required=false) String heureDepart,
                             @RequestParam(value="heurePause", required=false) String heurePause,
                             @RequestParam(value="heureReprise", required=false) String heureReprise,
                             @RequestParam(value="description", required=false) String description) throws Exception {
		logger.info(">>> ENREGISTRER PROMOTION");
		return pointageService.save(id, idPersonnel, datePointage, heureArrivee, heureDepart, heurePause, heureReprise, description);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverpointage", method = RequestMethod.POST)
	public @ResponseBody PointageDTO findPointage(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER PROMOTION");
		return pointageService.findPointage(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverpointageparperiode", method = RequestMethod.POST)
	public @ResponseBody PointageDTO findPointageperiode(@RequestParam(value="datePointage", required=false) String datePointage,@RequestParam(value="datePointage1", required=false) String datePointage1,
			@RequestParam(value="limit", required=false) Integer limit, 
			@RequestParam(value="offset", required=false) Integer offset
			) {
		logger.info(">>> TROUVER PROMOTION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		
		PointageDTO pointageDTO = new PointageDTO();
	
			pointageDTO = pointageService.findPointagesByDatePointageBetween(pageRequest,datePointage,datePointage1);
	
		
		return pointageDTO;
		
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverpointagePersonneparperiode", method = RequestMethod.POST)
	public @ResponseBody PointageDTO findPointagePersonneperiode(@RequestParam(value="idPersonnel", required=false) Long idPersonnel,@RequestParam(value="datePointage", required=false) String datePointage,@RequestParam(value="datePointage1", required=false) String datePointage1,
			@RequestParam(value="limit", required=false) Integer limit, 
			@RequestParam(value="offset", required=false) Integer offset
			) {
		logger.info(">>> TROUVER PROMOTION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PointageDTO pointageDTO = new PointageDTO();
	
			pointageDTO = pointageService.findPointagesByPersonnelAndDatePointageBetween(pageRequest,idPersonnel,datePointage,datePointage1);
		
		return pointageDTO;
		
	}
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpointages", method = RequestMethod.POST)
	public @ResponseBody PointageDTO findPointages() {
		logger.info(">>> LISTE PROMOTIONS");
		return pointageService.findPointages();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerpointagesparlistpersonneletdate", method = RequestMethod.POST)
	public @ResponseBody PointageDTO findPointagesByPersonnelsAndDate(@RequestParam(value="id", required=false) Long id, 
			@RequestParam(value="listSize", required=false) int listSize, 
			@RequestParam(value="listPersonnel", required=false) String list, 
			@RequestParam(value="datePointage", required=false) String datePointage) throws Exception {
		
		String[] output = list.split(" ");
		List<Long> listPersonnel = new ArrayList<Long>();
		
		for(int i = 0; i < listSize; i++){
			listPersonnel.add(Long.valueOf(output[i]));
		}
		return pointageService.findPointagesByPersonnelsAndDate(listPersonnel, datePointage);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerpointage", method = RequestMethod.POST)
	public @ResponseBody PointageDTO deletePointage(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER PROMOTION");
		return pointageService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerpointages", method = RequestMethod.GET)
	public @ResponseBody PointageDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE PROMOTIONS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PointageDTO pointageDTO = new PointageDTO();
		if(search == null || search == "")
			pointageDTO = pointageService.loadPointages(pageRequest);
		else
			pointageDTO = pointageService.loadPointages(pageRequest, search, search);
		
		return pointageDTO;
	}
}
