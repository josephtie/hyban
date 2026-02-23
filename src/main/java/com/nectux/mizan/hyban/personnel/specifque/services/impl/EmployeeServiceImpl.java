package com.nectux.mizan.hyban.personnel.specifque.services.impl;


import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
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
import com.nectux.mizan.hyban.rh.carriere.repository.PosteRepository;
import com.nectux.mizan.hyban.rh.carriere.repository.SiteWorkRepository;
import com.nectux.mizan.hyban.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelController.class);
    private final EmployeeRepository repository;
    private final SpecialContractRepository contractRepository;
    private final NationnaliteRepository nationnaliteRepository;
    private final FonctionRepository fonctionRepository;
    private final SiteWorkRepository siteWorkRepository;

    public EmployeeServiceImpl(EmployeeRepository repository, SpecialContractRepository contractRepository, NationnaliteRepository nationnaliteRepository, FonctionRepository fonctionRepository, SiteWorkRepository siteWorkRepository) {
        this.repository = repository;
        this.contractRepository = contractRepository;
        this.nationnaliteRepository = nationnaliteRepository;
        this.fonctionRepository = fonctionRepository;

        this.siteWorkRepository = siteWorkRepository;
    }

    @Override
    @Transactional
    public SpecialContractDTO saveEmployeeWithContract(
            Long employeeId,
            String matricule,
            String nom,
            String prenom,
            String sexe,
            Integer situationMatrimoniale,
            Long nationalite,
            String lieuHabitation,
            String dateNaissance,
            String phoneNumber,
            SpecialContractType typeContrat,
            Long fonction,
            Long site,
            String dateDebut,
            String dateFin,
            String modePaiement,
            String paiementNumber,
            Double netAPayer
    ) {

        SpecialContractDTO dto = new SpecialContractDTO();

        try {

            // =======================
            // 1️⃣ EMPLOYEE
            // =======================

            Employee employee = (employeeId != null)
                    ? repository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employé introuvable"))
                    : new Employee();

            employee.setMatricule(matricule);
            employee.setNom(nom);
            employee.setPrenom(prenom);
            employee.setNomComplet(nom + " " + prenom);
            employee.setSexe(sexe);
            employee.setSituationMatrimoniale(situationMatrimoniale);
            employee.setLieuHabitation(lieuHabitation);
            employee.setPhoneNumber(phoneNumber);
            employee.setActif(true);

            if (dateNaissance != null && !dateNaissance.trim().isEmpty()) {
                employee.setDateofbrid(Utils.stringToDate(dateNaissance, "dd/MM/yyyy"));
            }

            employee.setNationnalite(
                    nationnaliteRepository.findById(nationalite)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Nationalité introuvable : " + nationalite))
            );

            employee.setCategorieSpeciale(SpecialCategory.valueOf(typeContrat.name()));

            employee = repository.save(employee);

            // =======================
            // 2️⃣ GESTION CONTRAT ACTIF
            // =======================

            Optional<SpecialContract> oldContractOpt =
                    contractRepository.findActiveContractByEmployeeId(employee.getId());

            if (oldContractOpt.isPresent()) {
                SpecialContract oldContract = oldContractOpt.get();

                // Vérifier si modification du net à payer
                if (oldContract.getRemunerationForfaitaire().compareTo(netAPayer) != 0) {

                    // Désactiver ancien contrat
                    oldContract.setActif(false);
                    oldContract.setDateFin(new Date());
                    contractRepository.save(oldContract);

                } else {
                    // Aucun changement important → on retourne le contrat existant
                    dto.setRow(oldContract);
                    dto.setStatus(true);
                    dto.setResult("succes");
                    return dto;
                }
            }

            // =======================
            // 3️⃣ CREATION NOUVEAU CONTRAT
            // =======================

            SpecialContract newContract = new SpecialContract();
            newContract.setEmployee(employee);
            newContract.setTypeContrat(typeContrat);

            newContract.setFonction(
                    fonctionRepository.findById(fonction)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Fonction introuvable : " + fonction))
            );

            newContract.setSite(
                    siteWorkRepository.findById(site)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Site introuvable : " + site))
            );

            if (dateDebut != null && !dateDebut.trim().isEmpty()) {
                newContract.setDateDebut(Utils.stringToDate(dateDebut, "dd/MM/yyyy"));
            }

            if (dateFin != null && !dateFin.trim().isEmpty()) {
                newContract.setDateFin(Utils.stringToDate(dateFin, "dd/MM/yyyy"));
            }

            newContract.setModepaiement(modePaiement);
            newContract.setPaiementNumber(paiementNumber);
            newContract.setRemunerationForfaitaire(netAPayer);
            newContract.setActif(true);

            newContract = contractRepository.save(newContract);

            // =======================
            // 4️⃣ RETOUR DTO
            // =======================

            dto.setRow(newContract);
            dto.setStatus(true);
            dto.setResult("succes");

        } catch (Exception ex) {
            ex.printStackTrace();
            dto.setStatus(false);
            dto.setResult("echec");
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
    public  Boolean deactivate(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        employee.setActif(false);

        // Désactiver aussi les contrats spécifiques
       contractRepository
                .findByEmployeeAndActifTrue(employee)
                .forEach(contract -> contract.setActif(false));

       repository.save(employee);
       return true;
    }



    @Override
    public EmployeeDTO loadPersonnel(Pageable pageable) {
        // TODO Auto-generated method stub
        EmployeeDTO personnelDTO = new EmployeeDTO();
        Page<Employee> page = repository.findByActifTrue(pageable);
        List<Employee> personnelsWithContract = page.getContent().stream().map(p -> {
            SpecialContract contrat = contractRepository
                    .findFirstByEmployeeIdAndActifTrue(p.getId());

            if (contrat != null) {
                p.setNetapayer(contrat.getRemunerationForfaitaire().toString());
                p.setFonction(contrat.getFonction().getLibelle());
            }
            return p;
        }).toList();
        personnelDTO.setRows(personnelsWithContract);
        personnelDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
        return personnelDTO;
    }

    @Override
    public EmployeeDTO loadPersonnel(Pageable pageable, String search,String search1) {
        // TODO Auto-generated method stub
        EmployeeDTO personnelDTO = new EmployeeDTO();
        Page<Employee> page = repository.findByActifTrueAndNomCompletIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(pageable, search,search1);
        List<Employee> personnelsWithContract = page.getContent().stream().map(p -> {
            SpecialContract contrat = contractRepository
                    .findFirstByEmployeeIdAndActifTrue(p.getId());

            if (contrat != null) {
                p.setNetapayer(contrat.getRemunerationForfaitaire().toString());
                p.setFonction(contrat.getFonction().getLibelle());
            }
            return p;
        }).toList();


        personnelDTO.setRows(personnelsWithContract);
        personnelDTO.setTotal(page.getTotalElements());
        logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
        return personnelDTO;
    }
}




