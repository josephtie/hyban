package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.BulletinSpeciale;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BulletinSpecialeRepository extends CrudRepository<BulletinSpeciale, Long> {
	
	
	
	public java.util.List<BulletinSpeciale> findAll();
	
	public Page<BulletinSpeciale> findAll(Pageable pageable);
	public Page<BulletinSpeciale> findByPeriodePaieId(Pageable pageable,Long idptret);
	
	public java.util.List<BulletinSpeciale> findByPeriodePaieId(Long idptret);
	

	
	public java.util.List<BulletinSpeciale> findBySpecialContractEmployeeIdAndPeriodePaieId(Long idpers,Long idperiode);
	
//	public BulletinSpeciale findByPersonnelIdAndPeriodePaie(Long idpers,PeriodePaie idperiode);
	
	public BulletinSpeciale findBySpecialContractEmployeeIdAndPeriodePaie(Personnel idpers, PeriodePaie idperiode);
	
	public Page<BulletinSpeciale> findBySpecialContractEmployeeNomCompletLike(Pageable pageable,String search);


  //  Optional<BulletinSpeciale> findFirstByPersonnelIdAndPeriodePaieId(Long idPers, Long idPeriodDep);

    BulletinSpeciale findBySpecialContractEmployeeAndPeriodePaie(Employee employee, PeriodePaie periodpaie);

//    Optional<BulletinSpeciale> findFirstByPersonnelIdAndPeriodePaieId(Long personnelId, Long periodeId);

    Optional<BulletinSpeciale> findFirstBySpecialContractEmployeeIdAndPeriodePaieId(Long employeeId, Long periodeId);


    public final static String find_OneBulletin_Periods_Personnel = "select p from BulletinSpeciale p, SpecialContract  u  " +
            " where p.specialContract.id = u.id " +
            " and u.employee.id = :idPers and p.calculer= true  and p.periodePaie.id = :idperiode";

    @Query(find_OneBulletin_Periods_Personnel)
    public BulletinSpeciale findByBulletinAndPersonnelCalcultrue(@Param("idPers") Long idPers, @Param("idperiode") Long idperiode);

    Page<BulletinSpeciale> findByPeriodePaieIdAndSpecialContractEmployeeNomCompletIgnoreCaseContaining(Pageable pageable, Long id, String search);

    Page<BulletinSpeciale> findByPeriodePaieAndCalculerTrue(Pageable pageable, PeriodePaie periodePaie);

    List<BulletinSpeciale> findByPeriodePaieIdAndCalculerTrue(Long id);
}
