package com.nectux.mizan.hyban.personnel.specifque.repository;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.specifque.entity.PrimeSpecifique;
import com.nectux.mizan.hyban.personnel.specifque.entity.RubriqueSpecifique;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimeSpecifiqueRepository extends JpaRepository<PrimeSpecifique, Long> {

    List<PrimeSpecifique> findBySpecialContract(SpecialContract contract);

    List<PrimeSpecifique> findByPeriodePaie(PeriodePaie periodePaie);

    List<PrimeSpecifique> findByPrime(RubriqueSpecifique rubrique);
}

