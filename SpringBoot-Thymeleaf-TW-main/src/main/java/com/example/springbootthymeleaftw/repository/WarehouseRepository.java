package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findWarehouseByIdentificationCode(String identificationCode);
    Warehouse getWarehouseByAdminEmail(String email);
    Optional<ArrayList<Warehouse>> findAllBySignupStatus(String signupStatus);
    Optional<Warehouse> findWarehouseByAdminEmailAndSignupStatus(String email, String signupStatus);
}