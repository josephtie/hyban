package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



import com.nectux.mizan.hyban.parametrages.dto.CpteVirmtBanqueDTO;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.BanqueService;
import com.nectux.mizan.hyban.parametrages.service.CpteVirmtBanqueService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

@Controller
@RequestMapping("/parametrages")
public class CpteVirmtBanqueController {
	
	//private static final Logger logger = LoggerFactory.getLogger().(CpteVirmtBanqueController.class);
	private static final Logger logger = LoggerFactory.getLogger(CpteVirmtBanqueController.class);
	@Autowired private CpteVirmtBanqueService cpteVirmtBanqueService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private BanqueService banqueService;
	@Autowired private SocieteService societeService;
//	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;		
		
	@RequestMapping("/cptevirement")
	public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Types Documents");
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activecptviremt", "active");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-fighter-jet");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Compte de virements");
		  modelMap.addAttribute("listeBanques", banqueService.getBanques());
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); } 
		  
		return "cptevirement";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrercptevirmtbank", method = RequestMethod.POST)
	public @ResponseBody CpteVirmtBanqueDTO saveTypeCpteDocumentbank(@RequestParam(value="id", required=false) Long id,
														@RequestParam(value="idbank", required=false) Long idbank,  
														@RequestParam(value="ribCpteVirmt", required=false) Integer ribCpteVirmt, 
														@RequestParam(value="numguichCpteVirmt", required=false) String numguichCpteVirmt,
														@RequestParam(value="numcpteCpteVirmt", required=false) String numcpteCpteVirmt,  
														@RequestParam(value="donneurOrdCpteVirmt", required=false) String donneurOrdCpteVirmt, 
														@RequestParam(value="codEtablVirmt", required=false) String codEtablVirmt) {
		logger.info(">>> ENREGISTRER TYPE DOCUMENT");
		return cpteVirmtBanqueService.saver(id, codEtablVirmt, donneurOrdCpteVirmt, idbank, numcpteCpteVirmt, numguichCpteVirmt, ribCpteVirmt);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/supprimercptevirmtbank", method = RequestMethod.POST)
	public @ResponseBody CpteVirmtBanqueDTO deleteTypeCpteDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> SUPPRIMER TYPE DOCUMENT");
		return cpteVirmtBanqueService.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/trouvercptevirmtbank", method = RequestMethod.POST)
	public @ResponseBody CpteVirmtBanqueDTO findTypeCpteDocument(@RequestParam(value="id", required=false) Long id) {
		logger.info(">>> TROUVER TYPE DOCUMENT");
		return cpteVirmtBanqueService.findCpteVirmtBanque(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listercptevirmtbanks", method = RequestMethod.POST)
	public @ResponseBody CpteVirmtBanqueDTO findTypesCpteDocuments() {
		logger.info(">>> LISTE TYPES DOCUMENTS");
		return cpteVirmtBanqueService.findCpteVirmtBanques();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/paginercptevirmtbanks", method = RequestMethod.GET)
	public @ResponseBody CpteVirmtBanqueDTO getUserListCpteJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) {
		logger.info(">>> LISTE TYPES DOCUMENTS AVEC PAGINATION");
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		
		CpteVirmtBanqueDTO typeDocumentDTO = new CpteVirmtBanqueDTO();
		if(search == null || search == "")
			typeDocumentDTO = cpteVirmtBanqueService.loadCpteVirmtBanques(pageRequest);
		else
			typeDocumentDTO = cpteVirmtBanqueService.loadCpteVirmtBanques(pageRequest, search);
		
		return typeDocumentDTO;
	}

}
