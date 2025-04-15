package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BulletinPaieRepository extends JpaRepository<BulletinPaie, Long> {
	
	
	
	public List<BulletinPaie> findAll();
	
	
	public List<BulletinPaie> findByPeriodePaieId(Long idperiode);
	
	public List<BulletinPaie> findByPeriodePaieAnneeIdAndContratPersonnelPersonnelIdAndCalculer(Long idAN,Long idPers,boolean stat);
	
	public List<BulletinPaie> findByPeriodePaieAnneeId(Long idAN);
	
	public List<BulletinPaie> findByPeriodePaieAnneeIdAndCalculer(Long idAN,boolean stat);
	
	public List<BulletinPaie> findByPeriodePaie(PeriodePaie periodePaie);
	
	public List<BulletinPaie>	findByPeriodePaieIdAndCalculerTrue(Long idAN);
	public List<BulletinPaie> findByPeriodePaieAndCalculer(PeriodePaie periodePaie,boolean stat);
	public Page<BulletinPaie> findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContainingOrderByContratPersonnelPersonnelNomAsc(Pageable pageable,Long idperiode,String search);
	public Page<BulletinPaie> findByPeriodePaie(Pageable pageable,PeriodePaie periodePaie);
	public Page<BulletinPaie> findByPeriodePaieOrderByContratPersonnelPersonnelNomAsc(Pageable pageable,PeriodePaie periodePaie);
	public Page<BulletinPaie> findAllOrderByContratPersonnelPersonnel(Pageable pageable,PeriodePaie periodePaie);

	//public java.util.List<BulletinPaie> findByContratPersonnelAndCalculerAndClotureTrue(PeriodePaie periodePaie,boolean stat);
	
	public Page<BulletinPaie> findByPeriodePaieAndCalculerTrue(Pageable pageable,PeriodePaie periodePaie);
	
	public Page<BulletinPaie> findByPeriodePaieAndContratPersonnelPersonnelNomIgnoreCaseContainingOrContratPersonnelPersonnelPrenomIgnoreCaseContaining(Pageable pageable,PeriodePaie periodePaie,String search,String search1);
	
	//public Page<BulletinPaie> findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContainingOrContratPersonnelPersonnelPrenomIgnoreCaseContaining(Pageable pageable,Long idperiode,String search);
	@Query("SELECT b FROM BulletinPaie b " +
			"JOIN b.periodePaie p " +
			"JOIN b.contratPersonnel c " +
			"WHERE p.id = :idperiode " +
			"AND (LOWER(c.personnel.nom) LIKE LOWER(CONCAT('%', :search, '%')) " +
			"OR LOWER(c.personnel.prenom) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<BulletinPaie> findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContainingOrContratPersonnelPersonnelPrenomIgnoreCaseContaining(Pageable pageable,
																																				   @Param("idperiode") Long idperiode,
																																				   @Param("search") String search);





	public Page<BulletinPaie> findByPeriodePaieIdAndContratPersonnelPersonnelNomIgnoreCaseContaining(Pageable pageable,Long idperiode,String search);

	@Query("SELECT b FROM BulletinPaie b WHERE b.periodePaie.id = :periodeId AND LOWER(b.contratPersonnel.personnel.nom) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<BulletinPaie> chercherParNom(
			@Param("periodeId") Long periodeId,
			@Param("search") String search,
			Pageable pageable
	);

	public Page<BulletinPaie> findAll(Pageable pageable);
	
	
	public final static String find_Bulletin_Personnel = "select p from BulletinPaie p, ContratPersonnel u  " + 
            " where p.contratPersonnel.id = u.id " +
            " and u.personnel.id = :idPers and p.periodePaie.id = :idperiode";

	@Query(find_Bulletin_Personnel)
	public BulletinPaie findByBulletinAndPersonnel(@Param("idPers") Long idPers, @Param("idperiode") Long idperiode);
	
	
	public List<BulletinPaie>	findByPeriodePaieAnneeIdAndClotureTrueAndContratPersonnelPersonnelId(Long idanne,Long idctat);
	
	public final static String find_Bulletin_Ts_Personnel = "select p from BulletinPaie p, ContratPersonnel u  " + 
            " where p.contratPersonnel.id = u.id " +
            " and u.personnel.id = :idPers and p.calculer= true and p.cloture= true and p.periodePaie.id = :idperiode";

	@Query(find_Bulletin_Ts_Personnel)
	public BulletinPaie findByBulletinAndPersonnelCloture(@Param("idPers") Long idPers, @Param("idperiode") Long idperiode);
	
	
	
	public final static String find_OneBulletin_Periods_Personnel = "select p from BulletinPaie p, ContratPersonnel u  " + 
            " where p.contratPersonnel.id = u.id " +
            " and u.personnel.id = :idPers and p.calculer= true  and p.periodePaie.id = :idperiode";
 
	@Query(find_OneBulletin_Periods_Personnel)
	public BulletinPaie findByBulletinAndPersonnelCalcultrue(@Param("idPers") Long idPers, @Param("idperiode") Long idperiode);


	public List<BulletinPaie> findTop12ByContratPersonnel(ContratPersonnel contratPersonnel);
	
	public List<BulletinPaie> findTop1ByContratPersonnelOrderByIdDesc(ContratPersonnel contratPersonnel);
	
	public final static String findNbrebulletinTrueCount = "select  count(*) from BulletinPaie p, ContratPersonnel u  " + 
            " where p.contratPersonnel.id = u.id " +
            " and p.calculer= true and u.id = :idCtrat ";
	@Query(findNbrebulletinTrueCount)
	public Integer findNbrebulletinTrueCount(@Param("idCtrat") Long idPers);
	
	public List<BulletinPaie> findByPeriodePaieIdAndContratPersonnelPersonnelCarecTrue(@Param("idperiode") Long idperiode);
	
	/*public final static String find_Bulletin_banque_LivrePaie = "select p from Bulletin p, Personnel u ,BanquePersonnel m " + 
            " where p.personnel.id = u.id and u.sommeil = false and p.delete = false and p.calculer= true and p.personnel.id = m.personnel.id " +
            " and p.periodepaie.id = :idperiode and m.banque.id = :idbanque";

	@Query(find_Bulletin_banque_LivrePaie)*/
//	public List<Bulletin> findByDeleteFalseAndPeriodepaieLivrParbanque(@Param("idperiode") Long idperiode,@Param("idbanque") Long idbanque, org.springframework.data.domain.Sort sort);
	//public Double cumulCN (ContratPersonnel contratPersonnel);
	
	
	/*public final static String find_Bulletin_banque_LivrePaie = "select p from BulletinPaie p, ContratPersonnel u ,Banque m " + 
            " where p.contratPersonnel.id = u.id and u.statut = true and p.calculer= true and u.personnel.banquek.id = m.id " +
            " and p.periodePaie.id = :idperiode and m.id = :idbanque";

	@Query(find_Bulletin_banque_LivrePaie)*/
	public List<BulletinPaie> findByContratPersonnelPersonnelBanquekIdAndPeriodePaieIdAndCalculerTrue(@Param("idbanque") Long idbanque,@Param("idperiode") Long idperiode);

    List<BulletinPaie> findByPeriodePaieIdAndCalculer(Long id, boolean b);
}
