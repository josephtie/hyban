package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.paie.entity.Pret;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.paie.dto.PretDTO;
import com.nectux.mizan.hyban.paie.dto.PrimePersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.service.PretService;
import com.nectux.mizan.hyban.paie.service.PrimePersonnelService;
import com.nectux.mizan.hyban.parametrages.dto.PeriodePaieDTO;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.RubriqueService;
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


@Controller
@RequestMapping("/paie")
public class PrimePersonnelController {

	

private static final Logger logger = LogManager.getLogger(PrimePersonnelController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PretService pretService;
	@Autowired private PrimePersonnelService primePersonnelService;
	@Autowired private SocieteService societeService;
	@Autowired private RubriqueService rubriqueService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	
	@RequestMapping("/primespersonnel")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeLend", "active");
		modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Primes personnels");
		
	    List<PeriodePaie> listPeriodepaie;
	    List<Pret> listPrets;
	    try {
	    	listPrets = pretService.listdesPret();
	    	listPeriodepaie = periodePaieService.listperiodesupAuPret();
		} catch (Exception e) {
			listPrets = new ArrayList<Pret>();
			listPeriodepaie = new ArrayList<PeriodePaie>();
		}
	    modelMap.addAttribute("listPrets", listPrets);
	    modelMap.addAttribute("listPeriodepaie", listPeriodepaie);
	    
	    modelMap.addAttribute("listePrimes", rubriqueService.getRubriquesActives());
	    PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
	    if(periodePaie != null){
	    	modelMap.addAttribute("activeMois", periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", periodePaie.getId());
			 modelMap.addAttribute("periode",  periodePaie.getMois().getMois()+" "+ periodePaie.getAnnee().getAnnee());
	    }
	    Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
			mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); 
		return "primespersonnel";
	}
	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/primepersonneljson", method = RequestMethod.GET)
	public @ResponseBody PrimePersonnelDTO getperiodepret(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
	//	final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		PrimePersonnelDTO periodeDTO = new PrimePersonnelDTO();
		if(search == null || search == "")
			periodeDTO = primePersonnelService.loadPrimePersonnel(pageRequest);
		else
			periodeDTO = primePersonnelService.loadPrimePersonnel(pageRequest, search);
		
		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveprimepersonnel", method = RequestMethod.POST)
	public @ResponseBody PrimePersonnelDTO savePretpersonnel(@RequestParam(value="id", required=true) Long id,
															@RequestParam(value="montantop", required=true) Double montant,
															@RequestParam(value="valeurop", required=true) Integer valeur,
															@RequestParam(value="idAbsence", required=true) Long idPrime,
															@RequestParam(value="idPersonnel", required=true) Long idCtrat,
															 @RequestParam(value="idCtrat", required=true) Long ctrat,
															Principal principal) {
		
		PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
		if(idCtrat==null)idCtrat=ctrat;
		return primePersonnelService.saver( id, montant,  valeur,
				 idPrime, idCtrat, periodePaie.getId());
	}
	
	
	/*@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateprimepersonnel", method = RequestMethod.GET)
	public @ResponseBody PrimePersonnelDTO updatePretpersonnel(@RequestParam(value="idpret", required=true) Long idpret,@RequestParam(value="montant1", required=true) Double montant,
															@RequestParam(value="echelonage1", required=true) Long echelonage,
															@RequestParam(value="pret1", required=true) Long idPret,
															@RequestParam(value="idpers1", required=true) Long idPers,
															@RequestParam(value="dEmprunt1", required=true) String dEmprunt,
															@RequestParam(value="periodepaie1", required=true) Long idPeriodDep,
															Principal principal) {
		
		//Utilisateur currentUser = userService.findByEmail(principal.getName());
		
		return primePersonnelService.update(idpret,montant,echelonage,idPret, idPers, dEmprunt,idPeriodDep);
	}*/
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listperiodesupAuPret1", method = RequestMethod.GET)
	public @ResponseBody PeriodePaieDTO activPeriode( Principal principal) {
		
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		List<PeriodePaie> listPeriodepaie;
		listPeriodepaie = periodePaieService.listperiodesupAuPret();
		if(listPeriodepaie.size()>0)
			periodeDTO.setRows(listPeriodepaie);
		    periodeDTO.setResult("success");
		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listPret1", method = RequestMethod.GET)
	public @ResponseBody PretDTO activPret( Principal principal) {
		
		PretDTO pretDTO = new PretDTO();
		 List<Pret> listPrets;
		 listPrets = pretService.listdesPret();
		if(listPrets.size()>0)
		pretDTO.setRows(listPrets);
		pretDTO.setResult("success");
		return pretDTO;
	}
	
/*	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/pretIndividuel1", method = RequestMethod.POST)
	public @ResponseBody PrimePersonnelDTO ptretUserc(@RequestParam(value="id", required=true) Long idp, Principal principal) {
		
		return primePersonnelService.(idp);
	}
	*/
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/delpretIndividuel1", method = RequestMethod.POST)
	public @ResponseBody PrimePersonnelDTO delptretUserc(@RequestParam(value="id", required=true) Long idp, Principal principal) {
		
		PrimePersonnelDTO pretpersonnelDTO = new PrimePersonnelDTO();
		//pretpersonnelDTO.setResult();
		return primePersonnelService.delete(idp);
		
	
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchprimeIndividuel1", method = RequestMethod.POST)
	public @ResponseBody PrimePersonnel searchptretUserc(@RequestParam(value="id", required=true) Long idp, Principal principal) {		
		return primePersonnelService.findprimepersonnel(idp);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/primeIndividuel", method = RequestMethod.GET)
	public @ResponseBody  List<PrimePersonnel> searchprimePersonnel(@RequestParam(value="idPrime", required=true) Long idPrime,@RequestParam(value="idPeriode", required=true) Long idPeriode,@RequestParam(value="idCtrat", required=true) Long idCtrat, Principal principal) {
		return primePersonnelService.listdesprimepersonnelPeriodePrime(idPeriode,idPrime,idCtrat);
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/searchlesprimeIndividuel1Mois", method = RequestMethod.GET)
	public @ResponseBody PrimePersonnelDTO searchptretUsercmoisEncours(@RequestParam(value="id", required=true) Long idp, @RequestParam(value="limit", required=false) Integer limit, 
			@RequestParam(value="offset", required=false) Integer offset, 
			@RequestParam(value="search", required=false) String search) {	
	//	PrimePersonnelDTO pretpersonnelDTO = new PrimePersonnelDTO();
	    PeriodePaie periodePaie = periodePaieService.findPeriodeactive();
	    if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		return primePersonnelService.listdesprimepersonnelMoisEnPrime(pageRequest,periodePaie.getId(), idp);
	}
	
}
