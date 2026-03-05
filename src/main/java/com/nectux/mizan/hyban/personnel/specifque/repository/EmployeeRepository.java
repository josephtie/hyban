package com.nectux.mizan.hyban.personnel.specifque.repository;

import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByMatricule(String matricule);

    List<Employee> findByCategorieSpeciale(SpecialCategory categorie);

    List<Employee> findByActifTrue();

    List<Employee> findByCategorieSpecialeAndActifTrue(SpecialCategory categorie);

    @Query("""
SELECT DISTINCT sc.employee
FROM SpecialContract sc
WHERE sc.actif = true
""")
    List<Employee> findPersonnelSpecifique();




    Page<Employee> findByActifTrueOrderByNomCompletAsc(Pageable pageable);
    Page<Employee> findByActifTrue(Pageable pageable);

    Page<Employee> findByActifTrueAndNomCompletIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(Pageable pageable, String search,String search1);

    List<Employee> findByActifOrderByNomCompletAsc(boolean b);


    @Query("""
       SELECT e
       FROM Employee e
       WHERE e.actif = true
       AND (
            LOWER(e.nomComplet) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(e.matricule) LIKE LOWER(CONCAT('%', :search, '%'))
       )
       """)
    Page<Employee> searchActiveEmployees(
            @Param("search") String search,
            Pageable pageable
    );
}
