package com.nectux.mizan.hyban.personnel.specifque.services.impl;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.personnel.specifque.entity.PrimeSpecifique;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.repository.PrimeSpecifiqueRepository;
import com.nectux.mizan.hyban.personnel.specifque.repository.SpecialContractRepository;
import com.nectux.mizan.hyban.personnel.specifque.services.PrimeSpecifiqueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrimeSpecifiqueServiceImpl implements PrimeSpecifiqueService {

    private final PrimeSpecifiqueRepository repository;
    private final SpecialContractRepository contractRepository;
    private final PeriodePaieRepository periodePaieRepository;

    public PrimeSpecifiqueServiceImpl(PrimeSpecifiqueRepository repository,
                                      SpecialContractRepository contractRepository,
                                      PeriodePaieRepository periodePaieRepository) {
        this.repository = repository;
        this.contractRepository = contractRepository;
        this.periodePaieRepository = periodePaieRepository;
    }

    @Override
    public PrimeSpecifique save(PrimeSpecifique prime) {
        return repository.save(prime);
    }

    @Override
    public List<PrimeSpecifique> findByContract(Long contractId) {
        SpecialContract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));
        return repository.findBySpecialContract(contract);
    }

    @Override
    public List<PrimeSpecifique> findByPeriode(Long periodeId) {
        PeriodePaie periode = periodePaieRepository.findById(periodeId)
                .orElseThrow(() -> new RuntimeException("Période introuvable"));
        return repository.findByPeriodePaie(periode);
    }
}

