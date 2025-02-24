package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.CpteVirmtBanque;
import com.nectux.mizan.hyban.parametrages.entity.Banque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CpteVirmtBanqueRepository extends CrudRepository<CpteVirmtBanque, Long> {
	
	public List<CpteVirmtBanque> findAll();
	
	
	
	public CpteVirmtBanque findByBank(Banque banque);
	
	
	
	public Page<CpteVirmtBanque> findAll(Pageable pageable);



	public Page<CpteVirmtBanque> findByNumguichCpteVirmtLike(Pageable pageable,	String search);
	
	//public Page<CpteVirmtBanque> findByLibelleLike(Pageable pageable, String libelle);

}
