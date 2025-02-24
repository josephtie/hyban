package com.nectux.mizan.hyban.rh.absences.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.paie.repository.TempEffectifRepository;
import com.nectux.mizan.hyban.paie.service.TempEffectifService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.absences.dto.AbsencesPersonnelDTO;
import com.nectux.mizan.hyban.rh.absences.service.AbsencesPersonnelService;

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

@Controller
@RequestMapping("/absence")
public class AbsencesPersonnelController {
	
	private static final Logger logger = LogManager.getLogger(AbsencesPersonnelController.class);
	
	@Autowired private AbsencesPersonnelService absencesPersonnelService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private TempEffectifService  tempEffectifService;
	@Autowired private TempEffectifRepository  tempEffectifRepository;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PersonnelRepository personnelRepository;
	
	
	@RequestMapping("/absencepersonnel")
	public String viewAbsencePersonnel(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Sanction Personnel");
		
		modelMap.addAttribute("user", utilisateurService.findByEmail(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-user");
		modelMap.addAttribute("littleTitle", "Absence");
		modelMap.addAttribute("bigTitle", "Personnel");
		
		return "absencepersonnel";
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrerabsencepersonnel", method = RequestMethod.POST)
	public @ResponseBody AbsencesPersonnelDTO saveAbsencesPersonnel(@RequestParam(value="id", required=false) Long id, 
												@RequestParam(value="idPersonnel", required=false) Long idPersonnel, 
												@RequestParam(value="idAbsence", required=false) Long idAbsence,  
												@RequestParam(value="dateDebut", required=false) String dateDebut,  
												@RequestParam(value="dateFin", required=false) String dateFin,  
												@RequestParam(value="heursabsence", required=false) Double heursabsence,
												@RequestParam(value="joursabsence", required=false) Double jourabsence,
												@RequestParam(value="observation", required=false) String observation,  
												@RequestParam(value="statut", required=false) Boolean justifier,
												@RequestParam(value="sanctionsalaire", required=false) int sanctSal) throws Exception {
		logger.info(">>> ENREGISTRER SANCTION PERSONNEL");
		AbsencesPersonnelDTO absencesPersonnelDTO=new AbsencesPersonnelDTO();
		absencesPersonnelDTO= absencesPersonnelService.save(id, idPersonnel, idAbsence, dateDebut, dateFin,heursabsence,jourabsence, observation, justifier, sanctSal);
		PeriodePaie myperiodePaie=periodePaieService.findPeriodeactive();
		  if(absencesPersonnelDTO.getRow().getSanctionsalaire()==4){
			  System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			     TempEffectif tpeffop= new TempEffectif();
				tpeffop=tempEffectifRepository.findByPersonnelAndPeriodePaie(absencesPersonnelDTO.getRow().getPersonnel(), myperiodePaie);
				   if(tpeffop==null){
					 //  tpeff.setDatedesaisie(DateManager.stringToDate(dateDebut, "dd/MM/yyyy"));
					   tpeffop.setHeurspresence(173.33-heursabsence);
					   tpeffop.setJourspresence(30-jourabsence);
					   tpeffop.setPersonnel(absencesPersonnelDTO.getRow().getPersonnel());
					   tpeffop.setPeriodePaie(myperiodePaie);
					   tpeffop=tempEffectifService.save(tpeffop);
					   System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+tpeffop.toString());
				   }else{
					   tpeffop.setDatedesaisie(DateManager.stringToDate(dateDebut, "dd/MM/yyyy"));
					   tpeffop.setHeurspresence(tpeffop.getHeurspresence()-heursabsence);
					   tpeffop.setJourspresence(tpeffop.getJourspresence()-jourabsence);
					   tpeffop.setPersonnel(absencesPersonnelDTO.getRow().getPersonnel());
					   tpeffop.setPeriodePaie(myperiodePaie);
					   tpeffop=tempEffectifService.save(tpeffop);
					   
				   }
					   
					
		  }
		  
		 /* if(absencesPersonnelDTO.getRow().getSanctionsalaire()==2){
			     Personnel personn;
			     personn=personnelRepository.findOne(absencesPersonnelDTO.getRow().getPersonnel().getId());
				   if(personn==null){
					   tpeff.setDatedesaisie(DateManager.stringToDate(dateDebut, "dd/MM/yyyy"));
					   tpeff.setHeurspresence(173.33-tempabsence);
					   tpeff.setJourspresence(30-jourabsence);
					   tpeff.setPersonnel(absencesPersonnel.getPersonnel());
					   tpeff.setPeriodePaie(myperiodePaie);
					   tempEffectifRepository.save(tpeff);
				   }else{
					   
					  
					   personn.setNombreJourdu((int)(30-jourabsence));
					
					   personnelRepository.save(personn);
					   
				   }
					   
					
		  }*/
		  return  absencesPersonnelDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouverabsencepersonnel", method = RequestMethod.POST)
	public @ResponseBody AbsencesPersonnelDTO findAbsencesPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER SANCTION PERSONNEL");
		return absencesPersonnelService.findAbsencesPersonnel(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerabsencepersonnels", method = RequestMethod.POST)
	public @ResponseBody AbsencesPersonnelDTO findAbsencesPersonnels() {
		logger.info(">>> LISTE SANCTION PERSONNELS");
		return absencesPersonnelService.findAbsencesPersonnels();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerabsencepersonnelsparpersonnel", method = RequestMethod.POST)
	public @ResponseBody AbsencesPersonnelDTO findAbsencePersonnelsByPersonnel(@RequestParam(value="idPersonnel", required=true) Long idPersonnel) {
		logger.info(">>> LISTE SANCTION PERSONNEL PAR PERSONNEL");
		return absencesPersonnelService.findAbsencesPersonnelsByPersonnel(idPersonnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listerabsencepersonnelsparabsence", method = RequestMethod.POST)
	public @ResponseBody AbsencesPersonnelDTO findAbsencePersonnelsByAbsence(@RequestParam(value="idSanction", required=true) Long idSanction) {
		logger.info(">>> LISTE SANCTION PERSONNELS PAR PERSONNEL");
		return absencesPersonnelService.findAbsencesPersonnelsByAbsence(idSanction);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerabsencepersonnel", method = RequestMethod.POST)
	public @ResponseBody AbsencesPersonnelDTO deleteAbsencesPersonnel(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER SANCTION PERSONNEL");
		return absencesPersonnelService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginerabsencePersonnels", method = RequestMethod.GET)
	public @ResponseBody AbsencesPersonnelDTO getUserListabsenceJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE SANCTION PERSONNELS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		//final PageRequest pageRequest = new PageRequest(offset/10, limit);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		if(search == null || search == "")
			absencesPersonnelDTO = absencesPersonnelService.loadAbsencesPersonnels(pageRequest);
		else
			absencesPersonnelDTO = absencesPersonnelService.loadAbsencesPersonnels(pageRequest, search, search, search, search);
		
		return absencesPersonnelDTO;
	}

}
