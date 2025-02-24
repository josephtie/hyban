package com.nectux.mizan.hyban.personnel.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.personnel.dto.ServiceDTO;
import com.nectux.mizan.hyban.personnel.entity.Service;

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

import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.service.ServiceService;

@Controller
@RequestMapping("/personnels")
public class ServiceController {
	
	private static final Logger logger = LogManager.getLogger(ServiceController.class);
	@Autowired private ServiceService serviceService;
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	  
	@RequestMapping("/services")
    public String viewService(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeOrganisation", "active");
		modelMap.addAttribute("blockEmployer", "block");
		modelMap.addAttribute("activeService", "active");
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-users");
		modelMap.addAttribute("littleTitle", "Personnel");
		modelMap.addAttribute("bigTitle", "Servicess");
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
 	    
	    //Liste des directions
	    modelMap.addAttribute("listeDirection", serviceService.findByTypeServiceId(1L));
	    
		return "services";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listservicejson", method = RequestMethod.GET)
	public @ResponseBody
    ServiceDTO getServiceListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                  @RequestParam(value="offset", required=false) Integer offset,
                                  @RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "libelle");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "libelle");
		ServiceDTO serviceDTO = new ServiceDTO();
		if(search == null)
			serviceDTO = serviceService.loadService(pageRequest);
		else
			serviceDTO = serviceService.loadService(pageRequest, search);
		
		return serviceDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listdepartementbydirection", method = RequestMethod.GET)
	public @ResponseBody List<Service> getDepartementByDirection(@RequestParam(value="id", required=false) Long idDirection, Principal principal) {
		return serviceService.findByServiceParent(idDirection);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listservicepartype", method = RequestMethod.POST)
	public @ResponseBody List<Service> listeServiceParType(@RequestParam(value="idType", required=false) Long idType) {
		return serviceService.findByTypeServiceId(idType);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregisterservice", method = RequestMethod.POST)
	public @ResponseBody ServiceDTO saveService(@RequestParam(value="id", required=false) Long id, @RequestParam(value="libelle", required=true) String libelle,
			@RequestParam(value="idDepartement", required=false) Long idDepartement, @RequestParam(value="idDirection", required=false) Long idDirection, 
			@RequestParam(value="idTypeService", required=false) Long idTypeService) { 
		
		//, Long idDepartement, Long , Long 
		return serviceService.save(id, libelle, idDepartement, idDirection, idTypeService);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimerservice", method = RequestMethod.POST)
	public @ResponseBody ServiceDTO deleteService(@RequestParam(value="id", required=true) Long id) {
		
		ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.setResult(serviceService.delete(id));
		return serviceDTO;
	}
}
