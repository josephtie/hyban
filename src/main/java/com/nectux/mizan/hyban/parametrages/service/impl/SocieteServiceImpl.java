package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.dto.SocieteDTO;
import com.nectux.mizan.hyban.parametrages.repository.SocieteRepository;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("societeService")
public class SocieteServiceImpl implements SocieteService {
	
	private static final Logger logger = LogManager.getLogger(SocieteServiceImpl.class);
	
	@Autowired private SocieteRepository societeRepository;
	@Override
	public Societe save(Societe societe) {
		// TODO Auto-generated method stub
		societe = societeRepository.save(societe);
		
		return societe;
	}
	@Override
	public SocieteDTO save(Long id,String raisonsoc,String sigle,String activitepp,String adress,String formjuri,String telephone,String bp,String commune, 
			String quartier,String rue,String lot,String sectpartiell,String centreImpot,String codeEts,String codeActivite, String 
			codeEmployeur,String cpteContrib,String nomcomptable,String telcomptable,String adrcomptable,Double txprest,Double txacctr,Double txretraite, Double txgratif,Long gratification) {
		// TODO Auto-generated method stub,,
		SocieteDTO societeDTO = new SocieteDTO();
		try{
			Societe societe = new Societe();
           if(societeRepository.count()>0){
			   if(id != null)
				societe = societeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			}
			else
				societe = new Societe();
           
			societe.setRaisonsoc(raisonsoc);
			societe.setSigle(sigle);
			societe.setAdress(adress);
			societe.setFormjuri(formjuri);
			societe.setTelephone(telephone);
			societe.setBp(bp);
			societe.setCommune(commune);
			societe.setQuartier(quartier);
			societe.setRue(rue);
			societe.setLot(lot);
			societe.setSectpartiell(sectpartiell);
			societe.setCentreImpot(centreImpot);
			societe.setCodeEts(codeEts);
			societe.setCodeEmployeur(codeEmployeur);
			societe.setCpteContrib(cpteContrib);
			societe.setNomcomptable(nomcomptable);
			societe.setTelcomptable(telcomptable);
			societe.setAdrcomptable(adrcomptable);
			societe.setActivitepp(activitepp);
			societe.setCodeActivite(codeActivite);
			societe.setTxgratif(txgratif);
			societe.setGratification(gratification);
			societe.setTxprest(txprest);
			societe.setTxacctr(txacctr);
			societe.setTxretraite(txretraite);
			
			societe=societeRepository.save(societe);
			societeDTO.setRow(societe);			
			societeDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(societe.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			societeDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(raisonsoc).toString());
			ex.getStackTrace();
		}
		return societeDTO;

	}
	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Societe societe = societeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(societe == null)
			return false;
		
		societeRepository.delete(societe);
		return true;
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) societeRepository.count();
	}
	@Override
	public SocieteDTO loadSociete(Pageable pageable) {
		// TODO Auto-generated method stub
		SocieteDTO societeDTO = new SocieteDTO();
		Page<Societe> page = societeRepository.findAll(pageable);
		societeDTO.setRows(page.getContent());
		societeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return societeDTO;
	}
	@Override
	public SocieteDTO loadSociete(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		/*Page<Societe> page = societeRepository.findBySociete(pageable,search);
		societeDTO.setRows(page.getContent());
		societeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());*/
		return null;
	}
	@Override
	public Societe findbyRaisoncoc(String mois) {
		// TODO Auto-generated method stub
		return societeRepository.findByRaisonsoc(mois);
	}
	@Override
	public List<Societe> findtsmois() {
		// TODO Auto-generated method stub
		return societeRepository.findAll();
	}
	
	
}
