package com.nectux.mizan.hyban.personnel.specifque.services;

import com.nectux.mizan.hyban.personnel.specifque.entity.RubriqueSpecifique;

import java.util.List;

public interface RubriqueSpecifiqueService {

    RubriqueSpecifique save(RubriqueSpecifique rubrique);

    List<RubriqueSpecifique> findAll();

    List<RubriqueSpecifique> findActives();
}
