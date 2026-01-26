package com.nectux.mizan.hyban.personnel.specifque.web;

import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.services.SpecialContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/special-contracts")
public class SpecialContractController {

    private final SpecialContractService service;

    public SpecialContractController(SpecialContractService service) {
        this.service = service;
    }

    @PostMapping
    public SpecialContract create(@RequestBody SpecialContract contract) {
        return service.save(contract);
    }

    @GetMapping("/employee/{employeeId}")
    public List<SpecialContract> byEmployee(@PathVariable Long employeeId) {
        return service.findByEmployee(employeeId);
    }
}
