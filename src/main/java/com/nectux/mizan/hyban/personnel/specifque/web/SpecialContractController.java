package com.nectux.mizan.hyban.personnel.specifque.web;

import com.nectux.mizan.hyban.paie.web.BulletinPaieController;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.specifque.dto.EmployeeDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.SpecialContractDTO;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.services.SpecialContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/personnels/specifique/special-contracts")
public class SpecialContractController {
    private static final Logger logger = LoggerFactory.getLogger(SpecialContractController.class);
    private final SpecialContractService service;
    @Autowired
    private UtilisateurService userService;
    @Autowired private SocieteService societeService;
    @Autowired private PeriodePaieService periodePaieService;
    private PeriodePaie maperiode;

    public SpecialContractController(SpecialContractService service) {
        this.service = service;
    }



    @PostMapping
    public SpecialContract create(@RequestBody SpecialContract contract) {
        return service.save(contract);
    }

    @GetMapping("/employee/{employeeId}")
    public SpecialContractDTO byEmployee(@PathVariable Long employeeId) {
        SpecialContractDTO specialContractDTO=new SpecialContractDTO();
        specialContractDTO.setRow(service.findByEmployee(employeeId).get(0));
        specialContractDTO.setRows(service.findByEmployee(employeeId));
        specialContractDTO.setResult("success");
        specialContractDTO.setStatus(true);
        return specialContractDTO;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/listcontratspecjson", method = RequestMethod.GET)
    public @ResponseBody SpecialContractDTO getPersonnelListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                                          @RequestParam(value="offset", required=false) Integer offset,
                                                          @RequestParam(value="search", required=false)  String search, Principal principal) {

        if(offset == null) offset = 0;
        if(limit == null) limit = 20;

        //final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "nom");
        PageRequest pageRequest = PageRequest.of(offset / limit, limit, Sort.Direction.DESC, "id");
        SpecialContractDTO specialContractDTO = new SpecialContractDTO();
        if(search == null )
            specialContractDTO = service.loadPersonnel(pageRequest);
        else
            specialContractDTO = service.loadPersonnel(pageRequest,search);

        return specialContractDTO;

    }
}
