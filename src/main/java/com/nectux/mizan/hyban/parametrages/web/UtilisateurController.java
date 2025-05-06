package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.dto.UserDto;
import com.nectux.mizan.hyban.parametrages.dto.UtilisateurDTO;
import com.nectux.mizan.hyban.parametrages.dto.UtilisateurRoleDTO;
import com.nectux.mizan.hyban.parametrages.entity.*;
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
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

@Controller
@RequestMapping("/parametrages")
public class UtilisateurController {
	
	private static final Logger logger = LogManager.getLogger(UtilisateurController.class);
	
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@Autowired private SocieteService societeService;
	@RequestMapping("/utilisateur")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activeUser", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-users");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Utilisateurs");
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "utilisateurs";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listutilisateurjson", method = RequestMethod.GET)
	public @ResponseBody
    UtilisateurRoleDTO getUserListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                       @RequestParam(value="offset", required=false) Integer offset,
                                       @RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		// final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "nomComplet");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		//CustomerAdvisor customerAdvisor = customerAdvisorService.findByEmail(principal.getName());
		
		UtilisateurRoleDTO utilisateurRoleDTO = new UtilisateurRoleDTO();
		if(search == null || search == "")
			utilisateurRoleDTO = utilisateurService.loadUtilisateur(pageRequest);
		else
			utilisateurRoleDTO = utilisateurService.loadUtilisateur(pageRequest, search);
		
		return utilisateurRoleDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerutilisateur", method = RequestMethod.POST)
	public @ResponseBody UtilisateurRoleDTO saveUser(@RequestParam(value="id", required=false) Long id, 
																	@RequestParam(value="idRole", required=true) int idrole,  
																	@RequestParam(value="nom", required=true) String nomComplet, 
																	@RequestParam(value="naissance", required=false) String dateNaissance, 
																	@RequestParam(value="telephone", required=false) String telephone, 
																	@RequestParam(value="adresse", required=false) String adresse, 
																	@RequestParam(value="email", required=true) String email) {
		UserDto utilisateur=new UserDto();//UtilisateurRole utilisateurRole=new UtilisateurRole();
		utilisateur.setPassword("1234567L");
		utilisateur.setEmail("joseph.tie@gmail.com");
		utilisateur.setNomComplet(nomComplet);
		utilisateur.setUsername(dateNaissance);
		//RoleName name= Role.("ROLE_ADMIN");
		if(idrole == 1){
			utilisateur.setRoleName(RoleName.ADMIN);
		}
		if(idrole == 2){
			utilisateur.setRoleName(RoleName.DAF);
		}
		if(idrole == 3){
			utilisateur.setRoleName(RoleName.RH);
		}
		if(idrole == 4){
			utilisateur.setRoleName(RoleName.PTGE);
		}
		utilisateur.setRoleName(RoleName.ADMIN);
		//return utilisateurService.createUtilisateur(utilisateur);
		return utilisateurService.save(id, idrole, nomComplet, dateNaissance, telephone, adresse, email,"123456M");
	}
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/motdepasseoublie", method = RequestMethod.POST)
	public @ResponseBody UtilisateurDTO forgotPassword(@RequestParam(value="email", required=true) String email) {
		
		return utilisateurService.forgetPassword(email);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/choisirutilisateur", method = RequestMethod.POST)
	public @ResponseBody
    UtilisateurRole selectUserRole(@RequestParam(value="id", required=true) Long id) {
		
		UtilisateurRole utilisateurRole = utilisateurRoleService.findUtilisateurRole(id);
		return utilisateurRole;
	}
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchutilisateur", method = RequestMethod.POST)
	public @ResponseBody Utilisateur cherchutilisateur(@RequestParam(value="id", required=true) Long id) {
		
		Utilisateur utilisateurRole = utilisateurService.findUtilisateur(id);
		return utilisateurRole;
	}
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/chagermotdepasse", method = RequestMethod.POST)
	public @ResponseBody UtilisateurDTO changePassword(@RequestParam(value="id", required=true) Long id, @RequestParam(value="ancien", required=true) String ancien,  
															@RequestParam(value="nouveau", required=true) String nouveau,
															Principal principal) {
		
		Utilisateur utilisateur = utilisateurService.findUtilisateur(id);
		return utilisateurService.changePassword(Long.valueOf(utilisateur.getId()), ancien, nouveau);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerutilisateur", method = RequestMethod.POST)
	public @ResponseBody UtilisateurDTO deleteUser(@RequestParam(value="id", required=true) Long id) {
		
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		utilisateurDTO.setResult(utilisateurService.delete(id));
		return utilisateurDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/changerstatut", method = RequestMethod.POST)
	public @ResponseBody UtilisateurDTO changeStatus(@RequestParam(value="id", required=true) Long id) {
		
		UtilisateurDTO utilisateurDTO = utilisateurService.changeStstus(id);
		return utilisateurDTO;
	}

}
