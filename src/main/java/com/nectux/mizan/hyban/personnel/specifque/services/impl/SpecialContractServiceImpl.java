package com.nectux.mizan.hyban.personnel.specifque.services.impl;

import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.repository.EmployeeRepository;
import com.nectux.mizan.hyban.personnel.specifque.repository.SpecialContractRepository;
import com.nectux.mizan.hyban.personnel.specifque.services.SpecialContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecialContractServiceImpl implements SpecialContractService {

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
        return repository.findByEmployee(employee);
    }

    @Override
    public List<SpecialContract> findAllActifs() {
        return repository.findByActifTrue();
    }
}

