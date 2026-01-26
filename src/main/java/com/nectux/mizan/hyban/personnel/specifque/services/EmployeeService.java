package com.nectux.mizan.hyban.personnel.specifque.services;


import com.nectux.mizan.hyban.personnel.dto.PersonnelDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.EmployeeDTO;
import com.nectux.mizan.hyban.personnel.specifque.dto.SpecialContractDTO;
import com.nectux.mizan.hyban.personnel.specifque.entity.Employee;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {

    SpecialContractDTO saveEmployeeWithContract(
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
    );
    Employee update(Long id, Employee employee);

    Employee findById(Long id);

    List<Employee> findAll();

    List<Employee> findPersonnelSpecifique();

    void deactivate(Long id);

    EmployeeDTO loadPersonnel( Pageable pageable);

    EmployeeDTO loadPersonnel(Pageable pageRequest, String search,String search1);
}

