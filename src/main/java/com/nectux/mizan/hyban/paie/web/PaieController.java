package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/histpaie")
public class PaieController {

	
private static final Logger logger = LogManager.getLogger(PaieController.class);
	
	@Autowired private UtilisateurService userService;
	 
	@RequestMapping("/paie")
    public String viewLivrepaie(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		/*modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activePayrollBook", "active");*/
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Acceuil");
		modelMap.addAttribute("bigTitle", "RH PAIE - CGECI");
	    
		return "paie";
	}
}
