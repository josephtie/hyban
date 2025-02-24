package com.nectux.mizan.hyban.paie.service;

import java.util.List;

import com.nectux.mizan.hyban.paie.dto.GratificationDTO;
import com.nectux.mizan.hyban.paie.entity.Gratification;
import org.springframework.data.domain.Pageable;

public interface GratificationService {
	
	public Gratification save(Gratification gratification);
	
	public Boolean delete(Long id);
	
	public int count();
	
	public GratificationDTO chargerFichierExcel(String matricule, String nomComplet, int nombrePart, int ancienete, Double salaireBase,
                                                Double sursalaire, Double indemniteLogement, Double avance, Double alios, Double carec);
	
	public GratificationDTO loadGratificationProvisional(Pageable pageable);
	
	public GratificationDTO loadGratificationProvisional(Pageable pageable, String search);
	
	
	//public GratificationDTO loadGratificationProvisional(Pageable pageable, String search);
	
	public GratificationDTO loadGratification(Pageable pageable);
	
	public GratificationDTO loadGratification(Pageable pageable, String search);

	public GratificationDTO generateGratification();

	public List<Gratification> findByPeriodePaieAndCTRAT(Long idperiod,Long idctrat);
	public List<Gratification>	findAllBulletinByvirementforBanque(Long idPeriodePaie, Long idBanque);
	public List<Gratification> findByPeriodePaie(Long id);

	public Gratification findGratification(Long id);

}
