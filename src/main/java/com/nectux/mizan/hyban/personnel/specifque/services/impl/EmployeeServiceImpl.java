package com.nectux.mizan.hyban.personnel.specifque.services.impl;


import com.nectux.mizan.hyban.personnel.dto.PersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.NationnaliteRepository;
import com.nectux.mizan.hyban.personnel.specifque.dto.EmployeeDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.SpecialContractDTO;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.entity.SpecialContract;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialCategory;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import com.nectux.mizan.hyban.personnel.specifque.repository.EmployeeRepository;
import com.nectux.mizan.hyban.personnel.specifque.repository.SpecialContractRepository;
import com.nectux.mizan.hyban.personnel.specifque.services.EmployeeService;
import com.nectux.mizan.hyban.personnel.web.PersonnelController;
import com.nectux.mizan.hyban.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelController.class);
    private final EmployeeRepository repository;
    private final SpecialContractRepository contractRepository;
    private final NationnaliteRepository nationnaliteRepository;
    public EmployeeServiceImpl(EmployeeRepository repository, SpecialContractRepository contractRepository, NationnaliteRepository nationnaliteRepository) {
        this.repository = repository;
        this.contractRepository = contractRepository;
        this.nationnaliteRepository = nationnaliteRepository;
    }

    @Override
    public SpecialContractDTO saveEmployeeWithContract(
            Long employeeId,
            String matricule,
            String nom,
            String prenom,
            String sexe,
            String situationMatrimoniale,
            Long nationalite,
            String lieuHabitation,
            String dateNaissance,
            String phoneNumber,
            Boolean actif,
            SpecialContractType typeContrat,
            String dateDebut,
            String dateFin,
            String modePaiement,
            String paiementNumber,
            BigDecimal netAPayer
    )  {
        SpecialContractDTO dto = new SpecialContractDTO();
        // =======================
        // EMPLOYEE
        // =======================
        try{
        Employee employee = (employeeId != null)
                ? repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"))
                : new Employee();

        employee.setMatricule(matricule);
        employee.setNom(nom);
        employee.setPrenom(prenom);
        employee.setNomComplet(nom + " " + prenom);
        employee.setSexe(sexe);
        employee.setNationnalite(nationnaliteRepository.findById(nationalite).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + nationalite)));
        employee.setSituationMatrimoniale(situationMatrimoniale);
        employee.setLieuHabitation(lieuHabitation);
        employee.setDateofbrid(Utils.stringToDate(dateNaissance,"dd/MM/yyyy"));
        employee.setPhoneNumber(phoneNumber);
        employee.setActif(actif);
        String categorie=typeContrat.name();
        employee.setCategorieSpeciale(SpecialCategory.valueOf(categorie));

        employee = repository.save(employee);

        // =======================
        // CONTRAT SPÉCIFIQUE
        // =======================
        SpecialContract contract = new SpecialContract();
        contract.setEmployee(employee);
        contract.setTypeContrat(typeContrat);
        contract.setDateDebut(Utils.stringToDate(dateDebut,"dd/MM/yyyy"));
        contract.setDateFin(Utils.stringToDate(dateFin,"dd/MM/yyyy"));
        contract.setModepaiement(modePaiement);
        contract.setPaiementNumber(paiementNumber);
        contract.setRemunerationForfaitaire(netAPayer);
        contract.setActif(true);

        contract = contractRepository.save(contract);

        // =======================
        // DTO RETOUR
        // =======================

        dto.setRow(contract);
        dto.setStatus(true);
        dto.setResult("succes");
        } catch (Exception ex){
            ex.printStackTrace();
            dto.setResult("echec");
            dto.setStatus(false);
        }
        return dto;
    }

    // ==========================
    // UPDATE
    // ==========================
    @Override
    public Employee update(Long id, Employee employee) {

        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        existing.setMatricule(employee.getMatricule());
        existing.setNom(employee.getNom());
        existing.setPrenom(employee.getPrenom());
        existing.setNomComplet(employee.getNom() + " " + employee.getPrenom());
        existing.setSexe(employee.getSexe());
        existing.setSituationMatrimoniale(employee.getSituationMatrimoniale());
        existing.setLieuHabitation(employee.getLieuHabitation());
        existing.setDateofbrid(employee.getDateofbrid());
        existing.setPiece(employee.getPiece());
        existing.setNuperopiece(employee.getNuperopiece());
        existing.setNumeroCompte(employee.getNumeroCompte());
        existing.setPhoneNumber(employee.getPhoneNumber());
        existing.setCategorieSpeciale(employee.getCategorieSpeciale());
        existing.setActif(employee.getActif());

        return repository.save(existing);
    }

    // ==========================
    // FIND BY ID
    // ==========================
    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
    }

    // ==========================
    // FIND ALL
    // ==========================
    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return repository.findAll();
    }

    // ==========================
    // PERSONNEL SPÉCIFIQUE
    // ==========================
    @Override
    @Transactional(readOnly = true)
    public List<Employee> findPersonnelSpecifique() {

        return repository.findAll()
                .stream()
                .filter(e ->
                        contractRepository
                                .existsByEmployeeAndActifTrue(e)
                )
                .toList();
    }

    // ==========================
    // DESACTIVER
    // ==========================
    @Override
    public void deactivate(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        employee.setActif(false);

        // Désactiver aussi les contrats spécifiques
       contractRepository
                .findByEmployee(employee)
                .forEach(contract -> contract.setActif(false));

       repository.save(employee);
    }



    @Override
    public EmployeeDTO loadPersonnel(Pageable pageable) {
        // TODO Auto-generated method stub
        EmployeeDTO personnelDTO = new EmployeeDTO();
        Page<Employee> page = repository.findByActifTrue(pageable);
        personnelDTO.setRows(page.getContent());
        personnelDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
        return personnelDTO;
    }

    @Override
    public EmployeeDTO loadPersonnel(Pageable pageable, String search,String search1) {
        // TODO Auto-generated method stub
        EmployeeDTO personnelDTO = new EmployeeDTO();
        Page<Employee> page = repository.findByActifTrueAndNomCompletIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(pageable, search,search1);
        personnelDTO.setRows(page.getContent());
        personnelDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
        return personnelDTO;
    }
}




