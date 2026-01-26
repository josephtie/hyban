package com.nectux.mizan.hyban.personnel.specifque.services;

import com.nectux.mizan.hyban.personnel.specifque.entity.PrimeSpecifique;

import java.util.List;

public interface PrimeSpecifiqueService {

    PrimeSpecifique save(PrimeSpecifique prime);

    List<PrimeSpecifique> findByContract(Long contractId);

    List<PrimeSpecifique> findByPeriode(Long periodeId);
}
