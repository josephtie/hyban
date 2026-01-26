package com.nectux.mizan.hyban.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.service.CategorieService;

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
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.parametrages.web.UtilisateurController;
import com.nectux.mizan.hyban.personnel.dto.CategorieDTO;

@Controller
@RequestMapping("/personnels")
public class CategorieController {
	
	private static final Logger logger = LogManager.getLogger(UtilisateurController.class);
	
	@Autowired private CategorieService categorieService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/categorie")
    public String viewCategory(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Categorie");
		
		modelMap.addAttribute("activeEmployers", "active");
		modelMap.addAttribute("blockEmployer", "block");
		modelMap.addAttribute("activeCategory", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-group");
		modelMap.addAttribute("littleTitle", "Personnel");
		modelMap.addAttribute("bigTitle", "Categorie");
		
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
		return "categories";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcategoriejson", method = RequestMethod.GET)
	public @ResponseBody CategorieDTO getCategoryListJSON(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset,

																		@RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.ASC, "salaireDeBase");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.ASC, "salaireDeBase");
		CategorieDTO categorieDTO = new CategorieDTO();
	   //Map<String,String> filters= search.;
		if(search == null)
			categorieDTO = categorieService.loadCategorie(pageRequest);
		else
			categorieDTO = categorieService.loadCategorie(pageRequest,search );

		System.out.println("les categories " +categorieDTO.getRows());
		return categorieDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistercategorie", method = RequestMethod.POST)
	public @ResponseBody CategorieDTO saveCategory(@RequestParam(value="id", required=false) Long id, 
														@RequestParam(value="libelle", required=true) String libelle,  
														@RequestParam(value="salairedebase", required=true) Double salaireDeBase,  
														@RequestParam(value="indemniteLogement", required=true) Double indemniteLogement) { 
		
		return categorieService.save(id, libelle, salaireDeBase, indemniteLogement);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimercategorie", method = RequestMethod.POST)
	public @ResponseBody CategorieDTO deleteCategory(@RequestParam(value="id", required=true) Long id) {
		
		CategorieDTO categorieDTO = new CategorieDTO();
		categorieDTO.setResult(categorieService.delete(id));
		return categorieDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcategorie", method = RequestMethod.GET)
	public @ResponseBody List<Categorie> getCategoryList() {
		
		return categorieService.findCategories();
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/affichcategorie", method = RequestMethod.POST)
	public @ResponseBody Categorie getCategory(@RequestParam(value="id", required=true) Long id) {
		
		return categorieService.findCategorie(id);
	}
}
