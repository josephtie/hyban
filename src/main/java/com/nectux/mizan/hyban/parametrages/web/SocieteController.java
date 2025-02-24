package com.nectux.mizan.hyban.parametrages.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Iterator;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.BanqueService;
import com.nectux.mizan.hyban.parametrages.service.MoisService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.dto.MoisDTO;
import com.nectux.mizan.hyban.parametrages.dto.SocieteDTO;
import com.nectux.mizan.hyban.parametrages.entity.Mois;
import com.nectux.mizan.hyban.parametrages.repository.SocieteRepository;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
@RequestMapping("/parametrages")
public class SocieteController {

	
private static final Logger logger = LogManager.getLogger(SocieteController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private SocieteService societeService;
	@Autowired private SocieteRepository societeRepository;
	@Autowired private MoisService moisService;
	 @Autowired private BanqueService banqueService;
	 @Autowired private UtilisateurRoleService utilisateurRoleService;
	 
	 public SocieteDTO excelAttribdto =null;
		public static int maxFileSize 		= 1024 * 1024 * 2; // 40MB 
		public static String imageType = new String(".jpg");
	@RequestMapping("/societe")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activeSociety", "active");
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "fa fa-html5");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Societe");
		  modelMap.addAttribute("listeBanques", banqueService.getBanques());
	
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());}
		return "societes";
	}
	
	@RequestMapping("/societe1")
    public String viewAccountTyped(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activeSociety", "active");
		modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("icon", "iconfa-wrench");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "RH PAIE - CGECI");
		  modelMap.addAttribute("listeBanques", banqueService.getBanques());
		  Societe mysociete=null;
		  java.util.List<Societe> malist=societeService.findtsmois();
			mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo());
		    System.out.println("PERIODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + mysociete.getUrlLogo());
		return "societesp";
	}
	
	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/societejson", method = RequestMethod.GET)
	public @ResponseBody SocieteDTO getsocieteju(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "raisonsoc");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		SocieteDTO societDTO = new SocieteDTO();
		if(search == null)
			societDTO = societeService.loadSociete(pageRequest);
		else
			societDTO = societeService.loadSociete(pageRequest, search);
		
		return societDTO;
	}
	//*********/
	//Afficher tous les mois
	//*******/
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/societlistmois", method = RequestMethod.GET)
	public @ResponseBody MoisDTO listMoisyy(Principal principal) {
		
		//Utilisateur currentUser = userService.findByEmail(principal.getName());
		MoisDTO moisDTO = new MoisDTO();
		java.util.List<Mois> malist=moisService.findtsmois();
		moisDTO.setRows(malist);
		moisDTO.setTotal(malist.size());
		return moisDTO;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/enregistrersociete", method = RequestMethod.POST)
	public @ResponseBody SocieteDTO saveSociete(@RequestParam(value="idp", required=false) Long id,  
													@RequestParam(value="raisonsoc", required=false) String raisonsoc, 
													@RequestParam(value="sigle", required=false) String sigle, 
													@RequestParam(value="activitepp", required=false) String activitepp, 
													@RequestParam(value="adress", required=false) String adress, 
													@RequestParam(value="formjuri", required=false) String formjuri, 
													@RequestParam(value="telephone", required=false) String telephone, 
													@RequestParam(value="bp", required=false) String bp, 
													@RequestParam(value="commune", required=false) String commune, 
													@RequestParam(value="quartier", required=false) String quartier, 
													@RequestParam(value="rue", required=false) String rue, 
													@RequestParam(value="lot", required=false) String lot, 
													@RequestParam(value="sectpartiell", required=false) String sectpartiell, 
													@RequestParam(value="centreImpot", required=false) String centreImpot, 
													@RequestParam(value="codeEts", required=false) String codeEts, 
													@RequestParam(value="codeActivite", required=false) String codeActivite, 
													@RequestParam(value="codeEmployeur", required=false) String codeEmployeur, 
													@RequestParam(value="cpteContrib", required=false) String cpteContrib, 
													@RequestParam(value="nomcomptable", required=false) String nomcomptable, 
													@RequestParam(value="telcomptable", required=false) String telcomptable, 
													@RequestParam(value="adrcomptable", required=false) String adrcomptable,
													@RequestParam(value="txprest", required=false) Double txprest, 
													@RequestParam(value="txacctr", required=false) Double txacctr, 
													@RequestParam(value="txretraite", required=false) Double txretraite,
												@RequestParam(value="txgratif", required=false) Double txgratif,
												@RequestParam(value="gratification", required=false) Long gratification
												) {
	//	Double mtprest=(double) txprest ;
		return societeService.save(id, raisonsoc,sigle, activitepp, adress, formjuri, telephone, bp, commune, 
				quartier, rue, lot, sectpartiell, centreImpot, codeEts, codeActivite, 
				codeEmployeur, cpteContrib, nomcomptable, telcomptable, adrcomptable,txprest,txacctr,txretraite,txgratif,gratification);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/choisirsociete", method = RequestMethod.POST)
	public @ResponseBody Societe selectSociete(@RequestParam(value="id", required=true) Long id) {
		
		Societe utilisateurRole = societeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		return utilisateurRole;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/deletesociete", method = RequestMethod.POST)
	public @ResponseBody SocieteDTO deleteSociete(@RequestParam(value="id", required=true) Long id, Principal principal) {
		
	//	User currentUser = userService.findByEmail(principal.getName());
		
		SocieteDTO userDTO = new SocieteDTO();
		userDTO.setResult(societeService.delete(id));
		return userDTO;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveattrib", method = RequestMethod.POST)
	@ResponseBody
	public  SocieteDTO uploadFile(MultipartHttpServletRequest uploadfile, HttpServletRequest request)  {
	
		//User currentUser1 = userService.findUser(idUser);+ File.separator + "soxieteLog"
		try {
			Societe mysociete=null;
		String uploadPath =  request.getSession().getServletContext().getClass().getResource("")+ File.separator + "\\static\\logo\\";

		System.out.println(">>> CHEMIN UPLOAD >>>" + uploadPath);
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
	
		Iterator<String> itr =  uploadfile.getFileNames();
		 
	     MultipartFile mpf = uploadfile.getFile(itr.next());
	     //System.out.println(mpf.getOriginalFilename() +" uploaded!");
	     
	     String filename = mpf.getOriginalFilename();
	     String directory = uploadPath;
	     String filepath = Paths.get(directory, filename).toString();
	 	java.util.List<Societe> malist=societeService.findtsmois();
		mysociete=malist.get(0);
		mysociete.setUrlLogo("/static/logo/"+filename);
		societeService.save(mysociete);
	     // Save the file locally
	     BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	     
			stream.write(mpf.getBytes());
			stream.close();
		
	     
	     System.out.println("PERIODE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + filename);
	   
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
		return excelAttribdto;
	    
		
	/*	Contactattrib	Event=new Contactattrib();
		Event.setDatDeb(datDeb);Event.setDatFin(datFin);
		Event.setUser(currentUser);
		Event.setOpcommercial(opcommercialeRepository.findOne(Idopcommercial));*/
		
		
	}
	
	/*
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveuserc", method = RequestMethod.POST)
	public @ResponseBody PeriodePaieDTO saveUserc(@RequestParam(value="id", required=false) Long id,@RequestParam(value="name", required=true) String name,  
											@RequestParam(value="dateDeb", required=true) String dateDeb,	@RequestParam(value="dateFin", required=false) String dateFin,	 Principal principal) {
		
		User currentUser = userService.findByEmail(principal.getName());
		
		return opcommercialeService.save(id,name, dateDeb, dateFin, currentUser);
	}
	/*	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loaduser", method = RequestMethod.POST)
	public @ResponseBody User loadUser(@RequestParam(value="id", required=true) Long id) {
		return userService.findUser(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadusers", method = RequestMethod.POST)
	public @ResponseBody List<User> loadUsers() {
		return userService.findUsers();
	}
	

*/
}
