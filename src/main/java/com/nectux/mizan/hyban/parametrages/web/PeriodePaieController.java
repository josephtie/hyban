package com.nectux.mizan.hyban.parametrages.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.paie.repository.BulletinPaieRepository;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.repository.PrimePersonnelRepository;
import com.nectux.mizan.hyban.parametrages.dto.PeriodePaieDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.ExerciceService;
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
@RequestMapping("/parametrages")
public class PeriodePaieController {

	
private static final Logger logger = LogManager.getLogger(PeriodePaieController.class);
	
	@Autowired private UtilisateurService userService;
	
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	@Autowired private PeriodePaieRepository PeriodePaieRepository;
	@Autowired private PrimePersonnelRepository primePersonnelRepository;
	@Autowired private ExerciceService exerciceService;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@RequestMapping("/periode")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("activeSetting", "active");
		modelMap.addAttribute("blockSetting", "block");
		modelMap.addAttribute("activePeriod", "active");
		modelMap.addAttribute("icon", "fa fa-gavel");
		modelMap.addAttribute("littleTitle", "Parametrages");
		modelMap.addAttribute("bigTitle", "Periode de paie");
		
	    PeriodePaie maperiode=periodePaieService.findPeriodeactive();
	    if(maperiode==null){}
	    else{
	    	modelMap.addAttribute("activeMois", maperiode.getMois().getMois()+ maperiode.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", maperiode.getId());
	    }
	    Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "periode";
	}
	//afficher toutes les periodes
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/periodejson", method = RequestMethod.GET)
	public @ResponseBody PeriodePaieDTO getperiode(@RequestParam(value="limit", required=false) Integer limit, 
																		@RequestParam(value="offset", required=false) Integer offset, 
																		@RequestParam(value="search", required=false) String search) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest page = new PageRequest(offset/10, limit, Direction.DESC, "id");
		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		if(search == null)
			periodeDTO = periodePaieService.loadPeriodePaie(pageRequest);
		else
			periodeDTO = periodePaieService.loadPeriodePaie(pageRequest, search);
		
		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveperiodepaie", method = RequestMethod.POST)
	public @ResponseBody PeriodePaieDTO savePeriode(@RequestParam(value="libelle", required=true) String libelle, @RequestParam(value="mois", required=true) Long mois, Principal principal) {
		
		//Utilisateur currentUser = userService.findByEmail(principal.getName());
		
		return periodePaieService.genererPeriode(mois,libelle);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/periodactif", method = RequestMethod.GET)
	public @ResponseBody PeriodePaieDTO activPeriode( Principal principal) {
		
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		 List<PeriodePaie> listperiod = new ArrayList<PeriodePaie>();
		PeriodePaie maperiode=periodePaieService.findPeriodeactive();
		if(maperiode!=null)
			listperiod.add(maperiode);
			periodeDTO.setRow(maperiode);
		    periodeDTO.setRows(listperiod);
		    periodeDTO.setTotal(1L);
		    periodeDTO.setRows(listperiod);
		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/cloturePeriode", method = RequestMethod.POST)
	public @ResponseBody PeriodePaieDTO closeUserc(@RequestParam(value="id", required=true) Long id, Principal principal) {
		
		//Utilisateur currentUser = userService.findByEmail(principal.getName());
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		PeriodePaie maperiode=PeriodePaieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		PeriodePaie maperiodenew=PeriodePaieRepository.findById(id+1L).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id+1L));
		if(maperiode!=null){
		 List<BulletinPaie> listbulletin = new ArrayList<BulletinPaie>();
		 List<PrimePersonnel> listprimePers = new ArrayList<PrimePersonnel>();
		 List<PrimePersonnel> listprimePersNew = new ArrayList<PrimePersonnel>();
		 //List<Gratification> listgratif = new ArrayList<Gratification>();
		 listprimePers=primePersonnelRepository.findByPeriodePaieId(maperiode.getId());
	    listbulletin=bulletinPaieService.rechercherBulletinMoisCalculer(maperiode,true);
	    logger.info("###########################################################################"+listbulletin.size());
	    if(listbulletin.size()>0){
	    	
//	    		for(int k = 0; k < listbulletin.size(); k++){
//	    			BulletinPaie monBull= new BulletinPaie();
//	    			monBull=listbulletin.get(k);
//	    			monBull.setCloture(true);
//	    			bulletinPaieService.save(monBull);
//	    		}
			listbulletin.forEach(b -> b.setCloture(true));
			bulletinPaieRepository.saveAll(listbulletin);

			for (PrimePersonnel myprime : listprimePers) {
				if (Boolean.TRUE.equals(myprime.getPrime().getPermanent())) { // plus sûr que == true
					PrimePersonnel cloned = new PrimePersonnel();

					// Copier les champs nécessaires sauf l'id
					cloned.setPrime(myprime.getPrime());
					cloned.setContratPersonnel(myprime.getContratPersonnel());
					cloned.setMontant(myprime.getMontant());
					cloned.setPeriode(maperiodenew);

					// Ajouter d’autres champs si besoin…

					listprimePersNew.add(cloned);
				}
			}
		maperiodenew.setCloture(false);
		maperiodenew= PeriodePaieRepository.save(maperiodenew);
	   primePersonnelRepository.saveAll(listprimePersNew)	;
		maperiode.setCloture(true);
		maperiode= PeriodePaieRepository.save(maperiode);
		

		
	   
	      if(maperiode.getMois().getId()==12L){
	    	Exercice monexo=exerciceService.findExo(maperiode.getAnnee().getId());
			Exercice monexonew=exerciceService.findExo(maperiode.getAnnee().getId()+1L);
			if(monexo!=null)
				monexo.setCloture(true);
			   monexo= exerciceService.save(monexo);
			
			   monexonew.setCloture(false);
			   monexonew= exerciceService.save(monexonew);
	        }
	    periodeDTO.setResult("success");

	    }
	    else{
	    	periodeDTO.setResult("error");
	      }
		}
		periodeDTO.setRow(maperiode);

		return periodeDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/periodecloturee", method = RequestMethod.POST)
	public @ResponseBody List<PeriodePaie> listPeriodePaie() {
		
		 return periodePaieService.findPeriodeCloture();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/periodeouverte", method = RequestMethod.POST)
	public @ResponseBody List<PeriodePaie> listPeriodePaieOUverte() {
		
		 return periodePaieService.findPeriodeOuverte();
	}


	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/periodeall", method = RequestMethod.POST)
	public @ResponseBody List<PeriodePaie> listPeriodePaieAll() {
		
		 return periodePaieService.listAllperiode();
	}

	
}
