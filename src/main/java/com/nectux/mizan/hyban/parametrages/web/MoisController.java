package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.MoisService;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.dto.MoisDTO;
import com.nectux.mizan.hyban.parametrages.entity.Mois;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

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


@Controller
@RequestMapping("/parametrages")
public class MoisController {

	
private static final Logger logger = LogManager.getLogger(MoisController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private MoisService moisService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	 
	@RequestMapping("/mois")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-gavel");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Periode de paie");
		
		modelMap.addAttribute("activeWelcome", "active");
		modelMap.addAttribute("activePaies", "");
		modelMap.addAttribute("activePersonnels", "");
		modelMap.addAttribute("activeParametrages", "");

		modelMap.addAttribute("blockPaies", "");
		modelMap.addAttribute("blockPersonnels", "");
		modelMap.addAttribute("blockParametrages", "");
		// PAIE
		modelMap.addAttribute("activeLivrePaie", "");
		modelMap.addAttribute("activeLivreConge", "");
	    modelMap.addAttribute("activeLivreGratification", "");
	    modelMap.addAttribute("activePret", "");
	    // PERSONNEL
		modelMap.addAttribute("activePersonnel", "");
		modelMap.addAttribute("activeContrat", "");
		modelMap.addAttribute("activeCategorie", "");
	    modelMap.addAttribute("activeFonction", "");
		// PARAMETRAGE
		modelMap.addAttribute("activeUtilisateur", "");
	    modelMap.addAttribute("activeExercice", "");
	    modelMap.addAttribute("activePeriode", "");
	    modelMap.addAttribute("activeMois", "");
		
	    PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
	    if(periodePaie != null){
	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", periodePaie.getId());
			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
	    }
		
		return "periode";
	}
	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/moisjson", method = RequestMethod.GET)
	public @ResponseBody MoisDTO getmois(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "name");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "name");
		MoisDTO moisDTO = new MoisDTO();
		if(search == null)
			moisDTO = moisService.loadMois(pageRequest);
		else
			moisDTO = moisService.loadMois(pageRequest, search);
		
		return moisDTO;
	}
	//*********/
	//Afficher tous les mois
	//*******/
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listmois", method = RequestMethod.GET)
	public @ResponseBody MoisDTO listMois(Principal principal) {
		
		//Utilisateur currentUser = userService.findByEmail(principal.getName());
		MoisDTO moisDTO = new MoisDTO();
		java.util.List<Mois> malist=moisService.findtsmois();
		moisDTO.setRows(malist);
		moisDTO.setTotal(malist.size());
		return moisDTO;
	}
	
	
	
	
	/*
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveuserc", method = RequestMethod.POST)
	public @ResponseBody PeriodePaieDTO saveUserc(@RequestParam(value="id", required=false) Long id,@RequestParam(value="name", required=true) String name,  
											@RequestParam(value="dateDeb", required=true) String dateDeb,	@RequestParam(value="dateFin", required=false) String dateFin,	 Principal principal) {
		
		User currentUser = userService.findByEmail(principal.getName());
		
		return opcommercialeService.save(id,name, dateDeb, dateFin, currentUser);
	}
	/*	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loaduser", method = RequestMethod.POST)
	public @ResponseBody User loadUser(@RequestParam(value="id", required=true) Long id) {
		return userService.findUser(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadusers", method = RequestMethod.POST)
	public @ResponseBody List<User> loadUsers() {
		return userService.findUsers();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public @ResponseBody UserDTO deleteBranch(@RequestParam(value="id", required=true) Long id, Principal principal) {
		
		User currentUser = userService.findByEmail(principal.getName());
		
		UserDTO userDTO = new UserDTO();
		userDTO.setResult(userService.delete(id, currentUser));
		return userDTO;
	}
*/
}
