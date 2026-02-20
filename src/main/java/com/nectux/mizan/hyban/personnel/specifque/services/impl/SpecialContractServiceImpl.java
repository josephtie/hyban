package com.nectux.mizan.hyban.personnel.specifque.services.impl;

import com.nectux.mizan.hyban.paie.web.TempeffectifController;
import com.nectux.mizan.hyban.personnel.specifque.dto.EmployeeDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.SpecialContractDTO;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.repository.EmployeeRepository;
import com.nectux.mizan.hyban.personnel.specifque.repository.SpecialContractRepository;
import com.nectux.mizan.hyban.personnel.specifque.services.SpecialContractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecialContractServiceImpl implements SpecialContractService {
    private static final Logger logger = LogManager.getLogger(SpecialContractServiceImpl.class);
    private final SpecialContractRepository repository;
    private final EmployeeRepository employeeRepository;

    public SpecialContractServiceImpl(SpecialContractRepository repository,
                                      EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public SpecialContract save(SpecialContract contract) {
        return repository.save(contract);
    }

    @Override
    public List<SpecialContract> findByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
        return repository.findByEmployeeAndActifTrue(employee);
    }

    @Override
    public List<SpecialContract> findAllActifs() {
        return repository.findByActifTrue();
    }

    @Override
    public SpecialContractDTO loadPersonnel(PageRequest pageRequest) {
        SpecialContractDTO personnelDTO = new SpecialContractDTO();
        Page<SpecialContract> page = repository.findByActifTrue(pageRequest);

        personnelDTO.setRows(page.getContent());
        personnelDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
        return personnelDTO;
    }

    @Override
    public SpecialContractDTO loadPersonnel(PageRequest pageRequest, String search) {
        SpecialContractDTO personnelDTO = new SpecialContractDTO();
        Page<SpecialContract> page = repository.searchActiveContractslike( search,pageRequest);


        personnelDTO.setRows(page.getContent());
        personnelDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
        return personnelDTO;
    }



}

