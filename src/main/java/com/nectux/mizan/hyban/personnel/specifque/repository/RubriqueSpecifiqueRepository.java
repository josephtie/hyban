package com.nectux.mizan.hyban.personnel.specifque.repository;

import com.nectux.mizan.hyban.personnel.specifque.entity.RubriqueSpecifique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RubriqueSpecifiqueRepository extends JpaRepository<RubriqueSpecifique, Long> {

    Optional<RubriqueSpecifique> findByLibelle(String libelle);

    List<RubriqueSpecifique> findByActiveTrue();

    List<RubriqueSpecifique> findByPermanentTrue();
}
