package com.nectux.mizan.hyban.personnel.specifque.repository;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialContractRepository extends JpaRepository<SpecialContract, Long> {



    List<SpecialContract> findByActifTrue();
    Page<SpecialContract> findByActifTrue(Pageable  pageable);
   // Page<SpecialContract> findByActifTrueAndEmployeeNomCompletIgnoreCaseContainingOrEmployeeMatriculeIgnoreCaseContaining(Pageable  pageable,String search);
    List<SpecialContract> findByTypeContrat(SpecialContractType typeContrat);

    boolean existsByEmployeeAndActifTrue(Employee employee);

    List<SpecialContract> findByEmployeeAndActifTrue(Employee employee);

    SpecialContract findFirstByEmployeeIdAndActifTrue(Long id);



    @Query("""
SELECT s FROM SpecialContract s
WHERE s.actif = true
AND (
      LOWER(s.employee.nomComplet) LIKE LOWER(CONCAT('%', :keyword, '%'))
   OR LOWER(s.employee.matricule) LIKE LOWER(CONCAT('%', :keyword, '%'))
)
""")
    Page<SpecialContract> searchActiveContractslike(
            @Param("keyword") String keyword,
            Pageable pageable
    );


    @Query(
            value = "SELECT * FROM special_contracts sc " +
                    "WHERE sc.employee_id = :employeeId " +
                    "AND sc.actif = true " +
                    "LIMIT 1",
            nativeQuery = true
    )
    Optional<SpecialContract> findActiveContractByEmployeeId(
            @Param("employeeId") Long employeeId
    );


    //  Optional<SpecialContract> findByEmployeeAndActifTrue(Employee employee);
   // Optional<SpecialContract> findByEmployeeIdAndActifTrue(Employee employee);
}

