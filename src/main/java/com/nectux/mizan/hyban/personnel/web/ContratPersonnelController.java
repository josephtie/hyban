package com.nectux.mizan.hyban.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.service.ContratPersonnelService;
import com.nectux.mizan.hyban.personnel.service.PersonnelService;

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
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/personnels")
public class ContratPersonnelController {
	
	private static final Logger logger = LogManager.getLogger(PersonnelController.class);
	
	@Autowired private PersonnelService personnelService;
	@Autowired private ContratPersonnelService contratPersonnelService;
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired PeriodePaieRepository periodePaieRepository;
    @Autowired private SocieteService societeService;
    @Autowired private UtilisateurRoleService utilisateurRoleService;
    @Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@RequestMapping("/contrat")
    public String viewService(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeEmployers", "active");
		modelMap.addAttribute("blockEmployer", "block");
		modelMap.addAttribute("activeContract", "active");
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-group");
		modelMap.addAttribute("littleTitle", "Personnel");
		modelMap.addAttribute("bigTitle", "Contrat");
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

		return "contrat";
	}
	
	

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratparpersonneljson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratPersonnelListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, 
															@RequestParam(value="idpersonnel", required=true) Long idPersonnel, 
															Principal principal) {
		logger.info("");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		Personnel personnel = personnelService.findPersonnel(idPersonnel);
		
		//PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "statut");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "statut");
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		if(search == null )
			contratPersonnelDTO = contratPersonnelService.loadContratByPersonnel(personnel, pageRequest);
		else
			contratPersonnelDTO = contratPersonnelService.loadContratByPersonnel(personnel, pageRequest, search);
		
		return contratPersonnelDTO;
	}
	
	
	

	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratpersonnelActifjson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratPersonnelListActifJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search,															
															Principal principal) {
		logger.info("");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//Personnel personnel = personnelService.findPersonnel(idPersonnel);
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "id");
		
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		if(search == null || search == "")
			contratPersonnelDTO = contratPersonnelService.loadContratPersonnelActif(pageRequest);
		else
			contratPersonnelDTO = contratPersonnelService.loadContratPersonnelActif(pageRequest, search,search);
		
		return contratPersonnelDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratparpersonnel", method = RequestMethod.POST)
	public @ResponseBody List<ContratPersonnel> listContratParPersonnel(@RequestParam(value="idpersonnel", required=true) Long idPersonnel) { 
		
		Personnel personnel = personnelService.findPersonnel(idPersonnel);
		return contratPersonnelService.findByPersonnel(personnel);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratpersonneljson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratListJSON(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		if(search == null)
			contratPersonnelDTO = contratPersonnelService.loadContratActif(pageRequest);
		else
			contratPersonnelDTO = contratPersonnelService.loadContratActif(pageRequest, search);
		
		return contratPersonnelDTO;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratpersonnelDepartjson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratListDepartJSON(@RequestParam(value="limit", required=false) Integer limit,
																@RequestParam(value="offset", required=false) Integer offset,
																@RequestParam(value="search", required=false) String search, Principal principal) {

		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		if(search == null)
			contratPersonnelDTO = contratPersonnelService.loadContratDepart(pageRequest);
		else
			contratPersonnelDTO = contratPersonnelService.loadContratDepart(pageRequest, search);

		return contratPersonnelDTO;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savecontratpersonnel", method = RequestMethod.POST)
	public @ResponseBody ContratPersonnelDTO saveContratpers(@RequestParam(value="id", required=false) Long id, 
																	@RequestParam(value="idPersonnel", required=true) Long idPersonnel,  
																	@RequestParam(value="idCategorie", required=true) Long idCategorie,  
																	@RequestParam(value="idFonction", required=true) Long idFonction, 
																	@RequestParam(value="idTypeContrat", required=true) Long idTypeContrat, 
																	@RequestParam(value="dateDebut", required=true) String dateDebut, 
																	@RequestParam(value="dateFin", required=false) String dateFin, 
																	@RequestParam(value="netAPayer", required=false) Double netAPayer, 
																	@RequestParam(value="indemniteLogement", required=false) Double indemniteLogement,																
																	@RequestParam(value="ancienete", required=true) int ancienete,
																	@RequestParam(value="sursalaire", required=false) Double sursalaire,
																	@RequestParam(value="indemnitetransport", required=false) Double indemnitetransport,
																	@RequestParam(value="indemniteResp", required=false) Double indemniterespons,															
																
																	@RequestParam(value="indemniteRepresent", required=false) Double indemniterepresent) {
		
		return contratPersonnelService.save(id, idPersonnel,idCategorie,idFonction,idTypeContrat,dateDebut,  dateFin, netAPayer, indemniteLogement,  ancienete, true, sursalaire,indemnitetransport,indemniterespons,indemniterepresent);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/endcontratpersonnel", method = RequestMethod.POST)
	public @ResponseBody ContratPersonnelDTO updateContratPersonnel(@RequestParam(value="id", required=true) Long id,
																	@RequestParam(value="dateFin", required=true) String dateFin,
																	@RequestParam(value="permanent", required=true) Boolean depart,
																	@RequestParam(value="ObservCtrat", required=false) String ObservCtrat) {
		String [] part =dateFin.split("-");
		String date=part[2]+"/"+part[1]+"/"+part[0];
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh   "+date);
		return contratPersonnelService.endContract(id, date,depart,ObservCtrat);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updatecontratpersonnelSursal", method = RequestMethod.POST)
	public @ResponseBody ContratPersonnelDTO updateContratPersonnelsursal(@RequestParam(value="idPersonnel", required=true) Long id,@RequestParam(value="sursalaire", required=true) Double valued) {
		 ContratPersonnel ctratpersonnel=contratPersonnelRepository.findByPersonnelIdAndStatut(id, true);
		return contratPersonnelService.updateContractSursalaire(ctratpersonnel.getId(), valued);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deletecontratpersonnel", method = RequestMethod.POST)
	public @ResponseBody ContratPersonnelDTO deletecontratpers(@RequestParam(value="id", required=true) Long id) {
		
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		contratPersonnelDTO.setResult(contratPersonnelService.delete(id));
		return contratPersonnelDTO;
	}
	


	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratpersonnelExpjson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratListExpiredJSON(@RequestParam(value="id", required=true) Long id,@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PeriodePaie period =periodePaieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		String  dateDeb=period.getDdeb();
		String  dateFin=period.getDfin();
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
	//	if(search == null)
			contratPersonnelDTO = contratPersonnelService.loadContratExpieredumois(pageRequest, 2L,dateDeb ,dateFin);
		//else
		//	contratPersonnelDTO = contratPersonnelService.loadContratActif(pageRequest, search);
		
		return contratPersonnelDTO;
	}	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratpersonnelExpPeriodejson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratListExpiredPeriodeJSON(@RequestParam(value="dateDeb", required=true) String dateDeb,@RequestParam(value="dateFin", required=true) String dateFin,@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
	//	if(search == null)
			contratPersonnelDTO = contratPersonnelService.loadContratExpieredumois(pageRequest, 2L,dateDeb ,dateFin);
		//else
		//	contratPersonnelDTO = contratPersonnelService.loadContratActif(pageRequest, search);
		
		return contratPersonnelDTO;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listcontratpersonnelExpDatejson", method = RequestMethod.GET)
	public @ResponseBody ContratPersonnelDTO getContratListExpiredDateJSON(@RequestParam(value="dateDebw", required=true) String dateDeb,@RequestParam(value="dateFinw", required=true) String dateFin,@RequestParam(value="limit", required=false) Integer limit,
																			  @RequestParam(value="offset", required=false) Integer offset, Principal principal) {

		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		//	if(search == null)
		contratPersonnelDTO = contratPersonnelService.loadContratExpieredumois(pageRequest, 2L,convertirDate(dateDeb) ,convertirDate(dateFin));
		//else
		//	contratPersonnelDTO = contratPersonnelService.loadContratActif(pageRequest, search);

		return contratPersonnelDTO;
	}
	

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listexpirecontratpersonnel", method = RequestMethod.GET)
	public @ResponseBody List<ContratPersonnel> getExpireContract(Principal principal) throws Exception {
		
		return contratPersonnelService.findExpireContract();
		
		
		
	}
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listexpirecontratpersonneldays", method = RequestMethod.GET)
	public @ResponseBody List<ContratPersonnel> getExpireContractday15(Principal principal) throws Exception {

		return contratPersonnelService.findExpireContract(14);



	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cherchcontratpersonnelActif", method = RequestMethod.POST)
	public @ResponseBody ContratPersonnelDTO getChearchContract(@RequestParam(value="id", required=false) Long ictrat,Principal principal) throws Exception {
		
		return contratPersonnelService.findContratPersonnelk(ictrat);
		
		
		
	}
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listexpirecontratpersonnelDelai", method = RequestMethod.GET)
	public @ResponseBody List<ContratPersonnel> getExpireContractDelai(@RequestParam(value="nbre", required=true) int nbre,Principal principal) throws Exception {
		
		return contratPersonnelService.findExpireContract(nbre);
	}


	public static String convertirDate(String dateIso) {
		if (dateIso == null || !dateIso.matches("\\d{4}-\\d{2}-\\d{2}")) {
			throw new IllegalArgumentException("Format de date invalide. Attendu : AAAA-MM-JJ");
		}

		String[] parties = dateIso.split("-");
		String annee = parties[0];
		String mois = parties[1];
		String jour = parties[2];

		return jour + "/" + mois + "/" + annee;
	}

}
