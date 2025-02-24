package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.dto.ExerciceDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.parametrages.service.ExerciceService;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
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

import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/parametrages")
public class ExerciceController {

	
private static final Logger logger = LogManager.getLogger(ExerciceController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	//@Autowired private MoisService moisService;
	@Autowired private ExerciceRepository exerciceRepository;
	@Autowired private ExerciceService exerciceService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/exercice")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activeExercise", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-external-link");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Exercice");
		
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
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "exercices";
		

	}
	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/anneejson", method = RequestMethod.GET)
	public @ResponseBody ExerciceDTO getannee(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "name");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "name");
		ExerciceDTO exerciceDTO = new ExerciceDTO();
		if(search == null)
			exerciceDTO = exerciceService.loadExercice(pageRequest);
		else
			exerciceDTO = exerciceService.loadExercice(pageRequest, search);
		
		return exerciceDTO;
	}

		
		@ResponseStatus(HttpStatus.OK)
		@RequestMapping(value = "/Exerciceactif", method = RequestMethod.GET)
		public @ResponseBody ExerciceDTO activExercice( Principal principal) {
			
			ExerciceDTO exerciceDTO = new ExerciceDTO();
			Exercice monexercice=exerciceService.findExoactif();
			 List<Exercice> listexo = new ArrayList<Exercice>();
			if(monexercice!=null)
			listexo.add(monexercice);
			exerciceDTO.setRow(monexercice);
			exerciceDTO.setRows(listexo);
			exerciceDTO.setResult("success");
			return exerciceDTO;
		}
		@ResponseStatus(HttpStatus.OK)
		@RequestMapping(value = "/exercicearecuperer", method = RequestMethod.GET)
		public @ResponseBody  List<Exercice> activRecupExercice( Principal principal) {
			
			
			return exerciceService.findArecuperer();
		}
		
		//*********/
		// cloturer l'exercice 
		//*******/
		@ResponseStatus(HttpStatus.OK)
		@RequestMapping(value = "/clotureExo", method = RequestMethod.POST)
		public @ResponseBody ExerciceDTO closeExo(@RequestParam(value="id", required=true) Long id, Principal principal) {
			
			//Utilisateur currentUser = userService.findByEmail(principal.getName());
			ExerciceDTO exerciceDTO = new ExerciceDTO();
			
			Exercice maperiode=exerciceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			Exercice maperiodenew=exerciceRepository.findById(id+1L).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id+1L));
			if(maperiode!=null)
				maperiode.setCloture(true);
			maperiode= exerciceRepository.save(maperiode);
			
			maperiodenew.setCloture(false);
			maperiodenew= exerciceRepository.save(maperiodenew);
			
			exerciceDTO.setRow(maperiode);
			return exerciceDTO;
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
