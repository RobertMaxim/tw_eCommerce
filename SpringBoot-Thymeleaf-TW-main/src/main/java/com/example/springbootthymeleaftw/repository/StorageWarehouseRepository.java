package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.StorageWarehouse;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface StorageWarehouseRepository extends JpaRepository<StorageWarehouse, Integer> {
    Optional<ArrayList<StorageWarehouse>> findAllByWarehouse(Warehouse warehouse);
    Optional<StorageWarehouse> findById(int id);
}