package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.ContratDateFinHistorique;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContratDateFinHistoriqueRepository extends CrudRepository<ContratDateFinHistorique, Long> {

    List<ContratDateFinHistorique> findByContratPersonnelIdOrderByCreatedAtDesc(Long contratPersonnelId);
}
