package com.nectux.mizan.hyban.personnel.specifque.services;

import com.nectux.mizan.hyban.personnel.specifque.dto.SpecialContractDTO;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface SpecialContractService {

    SpecialContract save(SpecialContract contract);

    List<SpecialContract> findByEmployee(Long employeeId);

    List<SpecialContract> findAllActifs();

    SpecialContractDTO loadPersonnel(PageRequest pageRequest);
    SpecialContractDTO loadPersonnel(PageRequest pageRequest,String search);
}

