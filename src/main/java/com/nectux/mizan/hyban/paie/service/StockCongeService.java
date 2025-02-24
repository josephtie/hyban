package com.nectux.mizan.hyban.paie.service;

import com.nectux.mizan.hyban.paie.dto.StockCongeDTO;
import com.nectux.mizan.hyban.paie.entity.StockConge;
import org.springframework.data.domain.Pageable;

public interface StockCongeService {
	
	public StockConge save(StockConge stockConge);
	
	public StockCongeDTO save(Long id, Long idConge, String dateDepart, String dateRetour, Double montantVerse);
	
	public StockCongeDTO delete(Long id);
	
	public StockCongeDTO findStockConge(Long id);
	
	public StockCongeDTO findStockConges();
	
	public StockCongeDTO findStockCongesByConge(Long idConge);
	
	public StockCongeDTO findStockCongesByPersonnel(Long idPersonnel);
	
	public int count();
	
	public StockCongeDTO loadStockConges(Pageable pageable);

}
