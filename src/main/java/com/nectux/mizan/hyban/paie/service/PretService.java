package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.entity.Pret;
import com.nectux.mizan.hyban.paie.dto.PretDTO;

import org.springframework.data.domain.Pageable;

public interface PretService {
	
	public Pret save(Pret pret);
	public Boolean delete(Long id);
	public java.util.List<Pret> listdesPret();
	public int count();
	public PretDTO loadPret(Pageable pageable);
	public PretDTO loadPret(Pageable pageable, String search);

}
