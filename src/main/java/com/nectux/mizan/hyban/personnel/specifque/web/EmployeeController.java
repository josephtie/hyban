package com.nectux.mizan.hyban.personnel.specifque.web;

import com.nectux.mizan.hyban.personnel.dto.CategorieDTO;

import com.nectux.mizan.hyban.personnel.dto.PersonnelDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.EmployeeDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.SpecialContractDTO;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import com.nectux.mizan.hyban.personnel.specifque.services.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/personnels/specifique")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/enregisteremployee", method = RequestMethod.POST)
    public @ResponseBody SpecialContractDTO saveEmployeeWithContract(

            // ========= EMPLOYEE =========
            @RequestParam(value = "idEmpl", required = false) Long employeeId,
            @RequestParam("matricule") String matricule,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("sexe") String sexe,
            @RequestParam("situationMatrimoniale") Integer situationMatrimoniale,
            @RequestParam("nationalite") Long  nationalite,
            @RequestParam(value = "lieuHabitation", required = false) String lieuHabitation,
            @RequestParam(value = "dofbrid", required = false) String dateNaissance,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,

            // ========= CONTRAT =========
            @RequestParam("typeContrat") SpecialContractType typeContrat,
            @RequestParam("dDeb") String dateDebut,
            @RequestParam(value = "dFin", required = false) String dateFin,
            @RequestParam("fonction") Long fonction,
            @RequestParam("site") Long site,
            @RequestParam("modepaiement") String modePaiement,
            @RequestParam("paiementNumber") String paiementNumber,
            @RequestParam("netpayer") BigDecimal netAPayer
    )  {
        return service.saveEmployeeWithContract(
                employeeId,
                matricule,
                nom,
                prenom,
                sexe,
                situationMatrimoniale,
                nationalite,
                lieuHabitation,
                dateNaissance,
                phoneNumber,
                typeContrat,
                fonction,
                site,
                dateDebut,
                dateFin,
                modePaiement,
                paiementNumber,
                netAPayer
        );
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/listemployeejson", method = RequestMethod.GET)
    public @ResponseBody EmployeeDTO getPersonnelListJSON(@RequestParam(value="limit", required=false) Integer limit,
                                                           @RequestParam(value="offset", required=false) Integer offset,
                                                           @RequestParam(value="search", required=false)  String search, Principal principal) {

        if(offset == null) offset = 0;
        if(limit == null) limit = 20;

        //final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "nom");
        PageRequest pageRequest = PageRequest.of(offset / limit, limit, Sort.Direction.DESC, "id");
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if(search == null )
            employeeDTO = service.loadPersonnel(pageRequest);
        else
            employeeDTO = service.loadPersonnel(pageRequest,search,search);

        return employeeDTO;

    }

    @GetMapping
    public List<Employee> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable Long id) {
        return service.findById(id);
    }

//    @PostMapping()
//    public void deactivate(@RequestParam(value="id", required=true)  Long id ){
//        service.deactivate(id);
//    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/deactivate", method = RequestMethod.POST)
    public @ResponseBody EmployeeDTO deactivate(@RequestParam(value="idemp", required=true) Long id) {

        EmployeeDTO categorieDTO = new EmployeeDTO();
        categorieDTO.setResult(service.deactivate(id));
        return categorieDTO;
    }
}
