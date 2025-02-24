package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Banque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BanqueRepository extends CrudRepository<Banque, Long> {
	
	public List<Banque> findAll();
	
	public List<Banque> findByStatutTrue();
	
	public Banque findByLibelle(String libelle);
	
	public Banque findBySigle(String libelle);
	
	public Banque findByCodbanq(String libelle);
	
	public Banque findByIdNotAndLibelle(Long id, String libelle);
	
	public Banque findByIdNotAndSigle(Long id, String libelle);
	
	public Banque findByIdNotAndCodbanq(Long id, String libelle);
	
	public Page<Banque> findAll(Pageable pageable);
	
	public Page<Banque> findByLibelleLike(Pageable pageable, String libelle);

}
