package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.StorageLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageLocationRepository extends JpaRepository<StorageLocation, Long> {
}