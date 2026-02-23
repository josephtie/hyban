package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nectux.mizan.hyban.paie.dto.TempEffectifDTO;
import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.paie.repository.TempEffectifRepository;
import com.nectux.mizan.hyban.paie.service.TempEffectifService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;

import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("tempeffectifService")
public class TempEffectifServiceImpl implements TempEffectifService {
	
	private static final Logger logger = LogManager.getLogger(TempEffectifServiceImpl.class);
	
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private TempEffectifRepository tempEffectifRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TempEffectif save(TempEffectif tempEffectif) {
		// TODO Auto-generated method stub
		tempEffectif = tempEffectifRepository.save(tempEffectif);
	
		return tempEffectif;
	}

	@Override
	public List<TempEffectif> veriftempeffectif(Long idpers, Long periodId) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempEffectifDTO = new TempEffectifDTO();
		List<TempEffectif> list = tempEffectifRepository.findByPersonnelIdAndPeriodePaieId(idpers, periodId);
		tempEffectifDTO.setRows(list);
		tempEffectifDTO.setTotal(list.size());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return list;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		TempEffectif tempEffectif = tempEffectifRepository.findById(id)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(tempEffectif == null)
			return false;
		
	
		tempEffectifRepository.delete(tempEffectif);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) tempEffectifRepository.count();
	}

	@Override
	public TempEffectifDTO loadTempEffectif(Pageable pageable) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempEffectifDTO = new TempEffectifDTO();
		Page<TempEffectif> page = tempEffectifRepository.findAll(pageable);
		tempEffectifDTO.setRows(page.getContent());
		tempEffectifDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return tempEffectifDTO;
	}

	@Override
	public TempEffectifDTO loadTempEffectif(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		TempEffectifDTO tempEffectifDTO = new TempEffectifDTO();
		Page<TempEffectif> page = tempEffectifRepository.findByPersonnelNomLike(pageable, "%"+search+"%");
		tempEffectifDTO.setRows(page.getContent());
		tempEffectifDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return tempEffectifDTO;
	}



    @Override
    @Transactional
    public TempEffectifDTO saver(Double temptravail,
                                 Double jourtravail,
                                 Long idPers,
                                 Long idPeriodDep) {

        TempEffectifDTO dto = new TempEffectifDTO();
        Personnel personnel=new Personnel();
        PeriodePaie periodePaie=new PeriodePaie();

        try {
            final double JOURS_LEGAUX = 30d;
            final double HEURES_LEGALES = 173.33d;

             personnel = personnelRepository.findById(idPers)
                    .orElseThrow(() -> new EntityNotFoundException("Personnel introuvable"));

            periodePaie = periodePaieRepository.findById(idPeriodDep)
                    .orElseThrow(() -> new EntityNotFoundException("Période introuvable"));

            TempEffectif tempeffectif =
                    tempEffectifRepository
                            .findFirstByPersonnelIdAndPeriodePaieId(idPers, idPeriodDep)
                            .orElse(new TempEffectif());

            tempeffectif.setPersonnel(personnel);
            tempeffectif.setPeriodePaie(periodePaie);
            tempeffectif.setDatedesaisie(new Date());

            double jours;
            double heures;

            if (jourtravail != null) {
                jours = jourtravail;
                heures = (jours * HEURES_LEGALES) / JOURS_LEGAUX;
            }
            else if (temptravail != null) {
                heures = temptravail;
                jours = (heures * JOURS_LEGAUX) / HEURES_LEGALES;
            }
            else {
                jours = JOURS_LEGAUX;
                heures = HEURES_LEGALES;
            }

            // Arrondis métier
            jours = Math.ceil(jours);
            heures = Math.ceil(heures * 100) / 100;

            tempeffectif.setJourspresence(jours);
            tempeffectif.setHeurspresence(heures);

            tempeffectif = tempEffectifRepository.save(tempeffectif);

            dto.setRow(tempeffectif);
            dto.setResult("success");

        } catch (Exception e) {
            dto.setResult("erreur");
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    @Transactional
    public TempEffectifDTO saverEmp(Double temptravail,
                                 Double jourtravail,
                                 String idPersonne,
                                 Long idPeriodDep) {

        TempEffectifDTO dto = new TempEffectifDTO();

        try {

            final double JOURS_LEGAUX = 30d;
            final double HEURES_LEGALES = 173.33d;

            PeriodePaie periodePaie = periodePaieRepository.findById(idPeriodDep)
                    .orElseThrow(() -> new EntityNotFoundException("Période introuvable"));

            // ==========================
            // 1️⃣ Identifier le type
            // ==========================

          //  Optional<Personnel> personnelOpt = personnelRepository.findById(idPersonne);
            Optional<Employee> employeeOpt = employeeRepository.findByMatricule(idPersonne);


            TempEffectif tempeffectif =new TempEffectif();

            // ==========================
            // 2️⃣ Charger existant ou créer
            // ==========================

            if (employeeOpt.isPresent()) {


                tempeffectif = tempEffectifRepository
                        .findFirstByEmployeeIdAndPeriodePaieId(employeeOpt.get().getId(), idPeriodDep)
                        .orElse(new TempEffectif());

                tempeffectif.setEmployee(employeeOpt.get());
                tempeffectif.setPersonnel(null);
            }

            tempeffectif.setPeriodePaie(periodePaie);
            tempeffectif.setDatedesaisie(new Date());

            // ==========================
            // 3️⃣ Calcul métier
            // ==========================

            double jours;
            double heures;

            if (jourtravail != null) {
                jours = jourtravail;
                heures = (jours * HEURES_LEGALES) / JOURS_LEGAUX;
            }
            else if (temptravail != null) {
                heures = temptravail;
                jours = (heures * JOURS_LEGAUX) / HEURES_LEGALES;
            }
            else {
                jours = JOURS_LEGAUX;
                heures = HEURES_LEGALES;
            }

            // Arrondis
            jours = Math.ceil(jours);
            heures = Math.ceil(heures * 100) / 100;

            tempeffectif.setJourspresence(jours);
            tempeffectif.setHeurspresence(heures);

            tempeffectif = tempEffectifRepository.save(tempeffectif);

            dto.setRow(tempeffectif);
            dto.setResult("success");

        } catch (Exception e) {
            dto.setResult("erreur");
            e.printStackTrace();
        }

        return dto;
    }

    @Override
	public TempEffectif findtempeffectif(Long idpret) {
	
		return tempEffectifRepository.findById(idpret)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idpret));
	}

	@Override
	public TempEffectif findtempsAgentPeriode(Long idpers, PeriodePaie period) {
		// TODO Auto-generated method stub
		return tempEffectifRepository.findByPersonnelIdAndPeriodePaie(idpers, period);
	}



	
}
