package com.nectux.mizan.hyban.personnel.web;

import com.nectux.mizan.hyban.personnel.entity.DocumentType;
import com.nectux.mizan.hyban.personnel.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/personnel/document-types")
public class DocumentTypeController {
    @Autowired
    private DocumentTypeRepository repository;

    @GetMapping
    public List<DocumentType> all() {
        return repository.findAll();
    }

    @PostMapping
    public DocumentType create(@RequestBody DocumentType docType) {
        return repository.save(docType);
    }
}