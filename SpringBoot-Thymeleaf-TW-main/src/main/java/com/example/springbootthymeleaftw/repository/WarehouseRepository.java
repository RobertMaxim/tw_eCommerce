package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findWarehouseByIdentificationCode(String identificationCode);
}