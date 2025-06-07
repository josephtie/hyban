package com.nectux.mizan.hyban.personnel.web;

import com.nectux.mizan.hyban.personnel.entity.StorageLocation;
import com.nectux.mizan.hyban.personnel.repository.StorageLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/storage-locations")
public class StorageLocationController {
    @Autowired
    private StorageLocationRepository repository;

    @GetMapping
    public List<StorageLocation> all() {
        return repository.findAll();
    }

    @PostMapping
    public StorageLocation create(@RequestBody StorageLocation location) {
        return repository.save(location);
    }
}