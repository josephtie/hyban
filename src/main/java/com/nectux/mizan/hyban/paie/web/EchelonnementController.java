package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.paie.dto.EchelonnementDTO;
import com.nectux.mizan.hyban.paie.entity.Echelonnement;
import com.nectux.mizan.hyban.paie.repository.EchelonnementRepository;
import com.nectux.mizan.hyban.paie.service.EchelonnementService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
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
@RequestMapping("/paie")
public class EchelonnementController {

	
private static final Logger logger = LogManager.getLogger(EchelonnementController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private EchelonnementRepository echelonnementRepository;
	@Autowired private EchelonnementService echelonnementService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	
	private PeriodePaie maperiode;
	@RequestMapping("/echelonnement")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeModalite", "active");
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Echelonnemnt");
		
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
	    maperiode=periodePaieService.findPeriodeactive();
	    if(maperiode==null){}
	    else{
	    	modelMap.addAttribute("activeMois", maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", maperiode.getId());
			 modelMap.addAttribute("periode",  maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
	    }
	    Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "echelonnements";
	}

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/echelonnjson", method = RequestMethod.GET)
	public @ResponseBody EchelonnementDTO getechelonn(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		
		EchelonnementDTO echelonnDTO = new EchelonnementDTO();
		if(search == null || search == "")
			echelonnDTO = echelonnementService.loadEchelonnement(page);
		else
			echelonnDTO = echelonnementService.loadEchelonnement(page, search);
		
		return echelonnDTO;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateEchelonnmt", method = RequestMethod.POST)
	public @ResponseBody EchelonnementDTO closeUserc(@RequestParam(value="idechel", required=true) Long id, @RequestParam(value="periodPaie", required=true) Long idperiod,Principal principal) {
		
		EchelonnementDTO echelonnDTO = new EchelonnementDTO();
		Echelonnement maechelonn= echelonnementRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(maechelonn!=null)
		echelonnDTO.setRow(maechelonn);
		maechelonn=echelonnementService.update(id,idperiod);
		echelonnDTO.setRow(maechelonn);
		if(maechelonn.getMessage().contains("succes"))
		    echelonnDTO.setResult("success");
		else
			echelonnDTO.setResult(maechelonn.getMessage());
		
		return echelonnDTO;
	}
	/*@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savepretPersonnel", method = RequestMethod.POST)
	public @ResponseBody PretPersonnelDTO savePretpersonnel(@RequestParam(value="montant", required=true) Double montant,
															@RequestParam(value="echelonage", required=true) Long echelonage,
															@RequestParam(value="idPret", required=true) Long idPret,
															@RequestParam(value="idPers", required=true) Long idPers,
															@RequestParam(value="dEmprunt", required=true) String dEmprunt,
															@RequestParam(value="idPeriodDep", required=true) Long idPeriodDep,
															Principal principal) {
		
		Utilisateur currentUser = userService.findByEmail(principal.getName());
		
		return pretPersonnelService.saver(montant,echelonage,idPret, idPers, dEmprunt,idPeriodDep);
	}*/
	
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/echelonndupret", method = RequestMethod.GET)
	public @ResponseBody EchelonnementDTO activecheloPret(@RequestParam(value="idpretperso", required=true) Long idpretperso, Principal principal) {
		
		//EchelonnementDTO periodeDTO = new EchelonnementDTO();
	
		return echelonnementService.findechelondupret(idpretperso);
	}
	

	

}
