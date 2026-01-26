package com.nectux.mizan.hyban.personnel.specifque.services;

import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;

import java.util.List;

public interface SpecialContractService {

    SpecialContract save(SpecialContract contract);

    List<SpecialContract> findByEmployee(Long employeeId);

    List<SpecialContract> findAllActifs();
}

