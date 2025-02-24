package com.nectux.mizan.hyban.parametrages.service.impl;

import com.nectux.mizan.hyban.parametrages.dto.MoisDTO;
import com.nectux.mizan.hyban.parametrages.repository.MoisRepository;
import com.nectux.mizan.hyban.parametrages.service.MoisService;
import com.nectux.mizan.hyban.parametrages.entity.Mois;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("moisService")
public class MoisServiceImpl implements MoisService {
	
	private static final Logger logger = LogManager.getLogger(MoisServiceImpl.class);
	
	@Autowired private MoisRepository moisRepository;
	@Override
	public Mois save(Mois mois) {
		// TODO Auto-generated method stub
		mois = moisRepository.save(mois);
		
		return mois;
	}
	@Override
	public MoisDTO save(Long id, String moiss) {
		// TODO Auto-generated method stub
		MoisDTO exerciceDTO = new MoisDTO();
		try{
			Mois mois = new Mois();
		
			if(id != null)
				mois = moisRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			mois.setMois(moiss);
			mois=moisRepository.save(mois);
			exerciceDTO.setRow(mois);
			exerciceDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(mois.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			exerciceDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(moiss).toString());
			ex.getStackTrace();
		}
		return exerciceDTO;

	}
	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Mois mois = moisRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(mois == null)
			return false;
		
		moisRepository.delete(mois);
		return true;
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) moisRepository.count();
	}
	@Override
	public MoisDTO loadMois(Pageable pageable) {
		// TODO Auto-generated method stub
		MoisDTO moisDTO = new MoisDTO();
		Page<Mois> page = moisRepository.findAll(pageable);
		moisDTO.setRows(page.getContent());
		moisDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return moisDTO;
	}
	@Override
	public MoisDTO loadMois(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		/*Page<Mois> page = moisRepository.findByMois(pageable,search);
		moisDTO.setRows(page.getContent());
		moisDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());*/
		return null;
	}
	@Override
	public Mois findbymois(String mois) {
		// TODO Auto-generated method stub
		return moisRepository.findByMois(mois);
	}
	@Override
	public java.util.List<Mois> findtsmois() {
		// TODO Auto-generated method stub
		return moisRepository.findAll();
	}

	
}
