package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.paie.repository.RubriqVariableRepository;
import com.nectux.mizan.hyban.paie.dto.RubriqVariableDTO;
import com.nectux.mizan.hyban.paie.entity.RubriqVariable;
import com.nectux.mizan.hyban.paie.service.RubriqVariableService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
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
@RequestMapping("/paie")
public class RubriqVariableController {

	
private static final Logger logger = LogManager.getLogger(RubriqVariableController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PeriodePaieRepository PeriodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private RubriqVariableRepository rubriqvariableRepository;
	@Autowired private RubriqVariableService rubriqvariableService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/rubrique")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		/*modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activePayrollBook", "active");*/
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Acceuil");
		modelMap.addAttribute("bigTitle", "RH PAIE - CGECI");
	    
	    List<PeriodePaie> listPeriodepaie;
	  //  List<Pret> listPrets;
	    try {
	    	//listPrets = pretService.listdesPret();
	    	listPeriodepaie = periodePaieService.listperiodesupAuPret();
		} catch (Exception e) {
			//listPrets = new ArrayList<Pret>();
			listPeriodepaie = new ArrayList<PeriodePaie>();
		}
	  //  modelMap.addAttribute("listPrets", listPrets);
	    modelMap.addAttribute("listPeriodepaie", listPeriodepaie);
	
		
		return "rubrique";
	}

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/rubriquejson", method = RequestMethod.GET)
	public @ResponseBody RubriqVariableDTO getrubrique(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		
		RubriqVariableDTO echelonnDTO = new RubriqVariableDTO();
		if(search == null || search == "")
			echelonnDTO = rubriqvariableService.loadRubriqVariable(pageRequest);
		else
			echelonnDTO = rubriqvariableService.loadRubriqVariable(pageRequest, search);
		
		return echelonnDTO;
	}
	
	
	/*@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateEchelonnmt", method = RequestMethod.POST)
	public @ResponseBody EchelonnementDTO closeUserc(@RequestParam(value="idechel", required=true) Long id, @RequestParam(value="periodPaie", required=true) Long idperiod,Principal principal) {
		
		Utilisateur currentUser = userService.findByEmail(principal.getName());
		EchelonnementDTO echelonnDTO = new EchelonnementDTO();
		Echelonnement maechelonn= echelonnementRepository.findOne(id);
		if(maechelonn!=null)
		echelonnDTO.setRow(maechelonn);
		maechelonn=echelonnementService.update(id,idperiod);
		echelonnDTO.setRow(maechelonn);
		if(maechelonn.getMessage().contentEquals("succes"))
		    echelonnDTO.setResult("success");
		else
			echelonnDTO.setResult(maechelonn.getMessage());
		
		return echelonnDTO;
	}*/
@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saverubrique", method = RequestMethod.POST)
	public @ResponseBody RubriqVariableDTO saverubriquef( @RequestParam(value="cn", required=true) Double cn,
															@RequestParam(value="igr", required=true) Double igr,
															@RequestParam(value="amao", required=true) Double amao,															
															@RequestParam(value="synaoni", required=true) Double synaoni,
															@RequestParam(value="mugefci", required=true) Double mugefci,
															@RequestParam(value="diversgains", required=true) Double diversgains,
															@RequestParam(value="diversgainsImp", required=true) Double diversgainsImp,
															@RequestParam(value="ivoirePrev", required=true) Double ivoirePrev,
															@RequestParam(value="ivoireSante", required=true) Double ivoireSante,
															@RequestParam(value="regularisation", required=true) Double regularisation,
															@RequestParam(value="idpers", required=true) Long idPers,
															Principal principal) {
		
		return rubriqvariableService.saver( cn,igr, amao, synaoni, mugefci,  ivoireSante, ivoirePrev,  diversgainsImp, diversgains,regularisation, idPers);
	}
	
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchrubrique", method = RequestMethod.GET)
	public @ResponseBody RubriqVariable activerubrique(@RequestParam(value="idPersonnel", required=true) Long idPers,										
			Principal principal) {
		
		//EchelonnementDTO periodeDTO = new EchelonnementDTO();
		Personnel personnel = new Personnel();
		//  PeriodePaie periodpaie = PeriodePaieRepository.findOne(idPeriodDep);
		  personnel=personnelRepository.findById(idPers).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPers));

		//tempsDTO.setRow();
		return rubriqvariableRepository.findByPersonnelId(personnel.getId());
	}
	

	

}
