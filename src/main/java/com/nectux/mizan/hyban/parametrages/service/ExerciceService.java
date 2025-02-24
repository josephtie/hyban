package com.nectux.mizan.hyban.parametrages.service;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.dto.ExerciceDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import org.springframework.data.domain.Pageable;

public interface ExerciceService {
	
	public Exercice save(Exercice exercice);
	
	
	public Exercice findExoactif();
	
	public Exercice findExo(Long id);
	
	public ExerciceDTO save(Long id, String name);
	
	public Boolean delete(Long id);
	
	public int count();
	public List<Exercice> findArecuperer();
	public ExerciceDTO loadExercice(Pageable pageable);
	
	public ExerciceDTO loadExercice(Pageable pageable, String search);

}
