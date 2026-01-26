package com.nectux.mizan.hyban.personnel.specifque.repository;

import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialContractRepository extends JpaRepository<SpecialContract, Long> {



    List<SpecialContract> findByActifTrue();

    List<SpecialContract> findByTypeContrat(SpecialContractType typeContrat);

    boolean existsByEmployeeAndActifTrue(Employee employee);

    List<SpecialContract> findByEmployee(Employee employee);
}

