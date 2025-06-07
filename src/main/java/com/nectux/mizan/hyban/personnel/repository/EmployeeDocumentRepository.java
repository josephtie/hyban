package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.EmployeeDocument;
import com.nectux.mizan.hyban.rh.personnel.entity.Conjoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {

    public List<EmployeeDocument> findByPersonnelId(Long idPersonnel);
}