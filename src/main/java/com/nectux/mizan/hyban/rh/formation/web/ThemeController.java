package com.nectux.mizan.hyban.rh.formation.web;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.rh.carriere.web.PosteController;
import com.nectux.mizan.hyban.rh.formation.dto.ThemeDTO;
import com.nectux.mizan.hyban.rh.formation.service.ThemeService;
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
public class ThemeController {
	
	private static final Logger logger = LogManager.getLogger(PosteController.class);
	
	@Autowired
    private ThemeService themeService;
	@Autowired
    private UtilisateurService utilisateurService;
	@Autowired
    private PeriodePaieService periodePaieService;
	@Autowired
    private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/themes")
	public String viewPoste(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Theme");
		
		modelMap.addAttribute("activeTraining", "active");
		modelMap.addAttribute("blockTraining", "block");
		modelMap.addAttribute("activeTopic", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-book");
		modelMap.addAttribute("littleTitle", "Formation");
		modelMap.addAttribute("bigTitle", "Th&egrave;me");
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
 	    if(periodePaie != null){
 	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 			modelMap.addAttribute("activeMoisId", periodePaie.getId());
 			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
 	    }
		return "themes";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrertheme", method = RequestMethod.POST)
	public @ResponseBody
    ThemeDTO saveTheme(@RequestParam(value="id", required=false) Long id,
                       @RequestParam(value="intitule", required=false) String intitule,
                       @RequestParam(value="description", required=false) String description) throws Exception {
		logger.info(">>> ENREGISTRER POSTE");
		return themeService.save(id, intitule, description);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouvertheme", method = RequestMethod.POST)
	public @ResponseBody
    ThemeDTO findTheme(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER THEME");
		return themeService.findTheme(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerthemes", method = RequestMethod.POST)
	public @ResponseBody
    ThemeDTO findThemes() {
		logger.info(">>> LISTE THEMES");
		return themeService.findThemes();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimertheme", method = RequestMethod.POST)
	public @ResponseBody
    ThemeDTO deleteTheme(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER THEME");
		return themeService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerthemes", method = RequestMethod.GET)
	public @ResponseBody
    ThemeDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                             @RequestParam(value="offset", required=false) Integer offset,
                             @RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE THEMES AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		///final PageRequest pageRequest = new PageRequest(offset/10, limit);
	//	PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		ThemeDTO themeDTO = new ThemeDTO();
		if(search == null || search == "")
			themeDTO = themeService.loadThemes(pageRequest);
		else
			themeDTO = themeService.loadThemes(pageRequest, search, search);
		
		return themeDTO;
	}

}
