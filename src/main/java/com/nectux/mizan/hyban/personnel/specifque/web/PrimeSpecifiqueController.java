package com.nectux.mizan.hyban.personnel.specifque.web;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.RubriqueService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.parametrages.web.RubriqueController;
import com.nectux.mizan.hyban.personnel.specifque.entity.PrimeSpecifique;
import com.nectux.mizan.hyban.personnel.specifque.services.PrimeSpecifiqueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/parametrages")
public class PrimeSpecifiqueController {


    private static final Logger logger = LogManager.getLogger(PrimeSpecifiqueController.class);

    @Autowired
    private RubriqueService rubriqueService;
    @Autowired private UtilisateurService utilisateurService;
    @Autowired private SocieteService societeService;
    @Autowired private UtilisateurRoleService utilisateurRoleService;


    @RequestMapping("/speciales")
    public String viewAccountType(ModelMap modelMap, Principal principal) throws IOException {
        logger.info(">>>>> Types Documents");
        modelMap.addAttribute("activeSetting", "active");
        modelMap.addAttribute("blockSetting", "block");
        modelMap.addAttribute("activePRubrique", "active");
        modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
        Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
        System.out.println("utilisateur    " +utilisateur.toString());

        modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
                .map(utilisateurRole -> utilisateurRole.getRole().getName().name())
                .findFirst().orElse(""));
        modelMap.addAttribute("icon", "fa fa-shopping-cart");
        modelMap.addAttribute("littleTitle", "Parametrages");
        modelMap.addAttribute("bigTitle", "Rubrique speciale");
        Societe mysociete=null;
        java.util.List<Societe> malist=societeService.findtsmois();
        if(malist.size()>0)
        {	mysociete=malist.get(0);
            modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }

        return "speciales";
    }

    private final PrimeSpecifiqueService service;

    public PrimeSpecifiqueController(PrimeSpecifiqueService service) {
        this.service = service;
    }

    @PostMapping
    public PrimeSpecifique create(@RequestBody PrimeSpecifique prime) {
        return service.save(prime);
    }

    @GetMapping("/contrat/{id}")
    public List<PrimeSpecifique> byContrat(@PathVariable Long id) {
        return service.findByContract(id);
    }

    @GetMapping("/periode/{id}")
    public List<PrimeSpecifique> byPeriode(@PathVariable Long id) {
        return service.findByPeriode(id);
    }
}

