package com.nectux.mizan.hyban.paie.service;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import com.nectux.mizan.hyban.paie.dto.LivreDePaieDTO;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;

import com.nectux.mizan.hyban.utils.PrintLs;
import org.springframework.data.domain.Pageable;

public interface BulletinPaieService {
	
	public BulletinPaie save(BulletinPaie bulletinPaie);
	
	public LivreDePaieDTO   genererMois1(Pageable pageable,Long idPeriode);
	public BulletinPaieDTO generateLivreDePaie(Pageable pageable);
	
//	public BulletinPaieDTO generateBulletinDePaie(List<LivreDePaie> monlivre);
//	public List<BulletinPaie>   genererMois1Personnel(Long idPeriode,Long idPersonnel);
	public List<BulletinPaie>   rechercherBulletinMois(PeriodePaie periodePaie);
	public List<BulletinPaie>   rechercherBulletinMoisCalculer(PeriodePaie periodePaie,boolean etat);
	
	public List<BulletinPaie>   rechercherBulletinAnneeCalculer(Long idanne,boolean etat);
	public List<BulletinPaie>   rechercherBulletinAnnuel(Long anneeId,Long Idpers);
	public List<BulletinPaie>   rechercherBulletinAnnuelglobal(Long anneeId);
	public List<BulletinPaie>   findBulletinByPeriodePaie(Long idPeriode);
	public BulletinPaieDTO   BulletinMoisCalculer(Pageable pageable,PeriodePaie periodePaie);
	public Boolean delete(Long id);
	public int count();
	public BulletinPaie findBulletinByPeriodePaieAndPersonnel(PeriodePaie periodePaie, Personnel personnel);
	public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode);
	//public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode, String search);
	public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode, String search);
	
	public Double salaireMoyenMensuel(ContratPersonnel contratPersonnel);

	public Double indemniteMoyenMensuel(ContratPersonnel contratPersonnel);
	
	public List<BulletinPaie>   findBulletinByPeriodePaieContract(Long idPeriode);
	
	//public List<BulletinPaie> findAllBulletinByPeriodePaieForLivrePaieforBanque(Long idPeriodePaie) ;
	
	public Double[]  MasseSalarialdeLexo(PeriodePaie maperiode);
	public Double  MasseSalarialMois(PeriodePaie maperiode);
	public List<BulletinPaie> findAllBulletinByvirementforBanque(Long idPeriodePaie, Long idBanque);
	
	public Double MensuelCn(PeriodePaie maperiode);
	
	public Double MensuelIgr(PeriodePaie maperiode);
	
	public Double MensuelBrut(PeriodePaie maperiode);
	public Double[]  MensuelBaseCnpsSup(PeriodePaie maperiode);
	
	public Double[]  MensuelBaseCnpsSup70000(PeriodePaie maperiode);
	
	
	public Double MensuelBrutImposable(PeriodePaie maperiode);
	
	
	public Double MensuelIs(PeriodePaie maperiode);
	
	
	public Double MensuelJourtravail(PeriodePaie maperiode);
	
	public Double MensuelIgrPatron(PeriodePaie maperiode);
	
	public Integer Nbrebulletin(Long idcontratPersonnel);
	
	
	public Double MensuelCnAnne(Long anneeId);
	
	public Double MensuelIgrAnne(Long anneeId);
	
	public Double MensuelBrutAnne(Long anneeId);
	
	
	public Double MensuelIsAnne(Long anneeId);
	
	public Double MensuelIgrPatronAnne(Long anneeId);
	
	
	public Double MensuelBrutImpAnne(Long anneeId);

	public Double[] MensuelBaseCnpsInf(PeriodePaie periodePaie);

	public List<PrintLs> calculerMasseSalarialeParTypeContrat(PeriodePaie periode);
}
