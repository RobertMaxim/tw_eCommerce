package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.MarketSupplierRequest;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface MarketSupplierRequestRepository extends JpaRepository<MarketSupplierRequest, Integer> {
    Optional<ArrayList<MarketSupplierRequest>> findAllByWarehouseAndStatus(Warehouse warehouse,String status);
    Optional<MarketSupplierRequest> findMarketSupplierRequestById(int Id);
}