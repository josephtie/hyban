package com.nectux.mizan.hyban.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.personnel.service.FonctionService;

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

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.dto.FonctionDTO;
import com.nectux.mizan.hyban.personnel.entity.Fonction;

@Controller
@RequestMapping("/personnels")
public class FonctionController {
	
	private static final Logger logger = LogManager.getLogger(FonctionController.class);
	
	@Autowired private FonctionService fonctionService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	   @Autowired private SocieteService societeService;
	   @Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/fonction")
    public String viewFonction(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Fonction");
		
		modelMap.addAttribute("activeEmployers", "active");
		modelMap.addAttribute("blockEmployer", "block");
		modelMap.addAttribute("activeFunction", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-table");
		modelMap.addAttribute("littleTitle", "Gestion RH");
		modelMap.addAttribute("littleTitleform", "Liste des emplois & fonctions");
		modelMap.addAttribute("bigTitle", "Emploi / Fonction");
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
		return "fonctions";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listfonctionjson", method = RequestMethod.GET)
	public @ResponseBody FonctionDTO getCategoryListJSON(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
	//	final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "libelle");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "libelle");
		FonctionDTO FonctionDTO = new FonctionDTO();
		if(search == null)
			FonctionDTO = fonctionService.loadFonction(pageRequest);
		else
			FonctionDTO = fonctionService.loadFonction(pageRequest, search);
		
		return FonctionDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregisterfonction", method = RequestMethod.POST)
	public @ResponseBody FonctionDTO saveCategory(@RequestParam(value="id", required=false) Long id, @RequestParam(value="libelle", required=true) String libelle) { 
		
		return fonctionService.save(id, libelle);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerfonction", method = RequestMethod.POST)
	public @ResponseBody FonctionDTO deleteCategory(@RequestParam(value="id", required=true) Long id) {
		
		FonctionDTO FonctionDTO = new FonctionDTO();
		FonctionDTO.setResult(fonctionService.delete(id));
		return FonctionDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listfonction", method = RequestMethod.GET)
	public @ResponseBody List<Fonction> getFonctionList() {
		
		return fonctionService.findFonctions();
	}


}
