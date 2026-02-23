package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nectux.mizan.hyban.paie.dto.TempEffectifDTO;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.paie.repository.TempEffectifRepository;
import com.nectux.mizan.hyban.paie.service.TempEffectifService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;

import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.repository.EmployeeRepository;
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
public class TempeffectifController {

	
private static final Logger logger = LogManager.getLogger(TempeffectifController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PeriodePaieRepository PeriodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private TempEffectifRepository tempffectifRepository;
	@Autowired private TempEffectifService tempeffectifService;
	 
	@RequestMapping("/tempeffectif")
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
	
		
		return "tempeffectif";
	}

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/tempeffectifjson", method = RequestMethod.GET)
	public @ResponseBody TempEffectifDTO gettempeffectif(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		TempEffectifDTO echelonnDTO = new TempEffectifDTO();
		if(search == null || search == "")
			echelonnDTO = tempeffectifService.loadTempEffectif(pageRequest);
		else
			echelonnDTO = tempeffectifService.loadTempEffectif(pageRequest, search);
		
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
	@RequestMapping(value = "/savetempeffectif", method = RequestMethod.POST)
	public @ResponseBody TempEffectifDTO savetempeffectif(@RequestParam(value="temptravail", required=true) Double temptravail,
															@RequestParam(value="jourtravail", required=true) Double jourtravail,
															@RequestParam(value="idPers", required=true) Long idPers,															
															@RequestParam(value="idPeriodDep", required=true) Long idPeriodDep,
															Principal principal) {
		
		return tempeffectifService.saver(temptravail,jourtravail,idPers,idPeriodDep);
	}

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/savetempeffectifEmp", method = RequestMethod.POST)
    public @ResponseBody TempEffectifDTO savetempeffectifEmp(@RequestParam(value="temptravail", required=true) Double temptravail,
                                                          @RequestParam(value="jourtravail", required=true) Double jourtravail,
                                                          @RequestParam(value="idPers", required=true) String idPers,
                                                          @RequestParam(value="idPeriodDep", required=true) Long idPeriodDep,
                                                          Principal principal) {

        return tempeffectifService.saverEmp(temptravail,jourtravail,idPers,idPeriodDep);
    }
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchtempeffectif", method = RequestMethod.GET)
	public @ResponseBody TempEffectif activetempeffectif(@RequestParam(value="idPersonnel", required=true) Long idPers,
			@RequestParam(value="idPeriodDep", required=true) Long idPeriodDep, Principal principal) {

		//EchelonnementDTO periodeDTO = new EchelonnementDTO();
		Personnel personnel = new Personnel();
		  PeriodePaie periodpaie = PeriodePaieRepository.findById(idPeriodDep).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPeriodDep));
		  personnel=personnelRepository.findById(idPers).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPers));
		//tempsDTO.setRow();
		return tempffectifRepository.findByPersonnelAndPeriodePaie(personnel, periodpaie);
	}

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/cherchtempeffectifEmp", method = RequestMethod.GET)
    public @ResponseBody TempEffectif activetempeffectifEmp(
            @RequestParam(value="idPersonnel", required=true) String idPers,
			@RequestParam(value="idPeriodDep", required=true) Long idPeriodDep,
            Principal principal) {

        PeriodePaie periodpaie = PeriodePaieRepository.findById(idPeriodDep)
                .orElseThrow(() -> new EntityNotFoundException("Période introuvable : " + idPeriodDep));



        // 2️⃣ Sinon chercher côté Employee
        Optional<Employee> employeeOpt = employeeRepository.findByMatricule(idPers);

        if (employeeOpt.isPresent()) {
            return tempffectifRepository
                    .findByEmployeeAndPeriodePaie(employeeOpt.get(), periodpaie);
        }

        throw new EntityNotFoundException("Aucune personne trouvée avec id : " + idPers);
    }

	

}
