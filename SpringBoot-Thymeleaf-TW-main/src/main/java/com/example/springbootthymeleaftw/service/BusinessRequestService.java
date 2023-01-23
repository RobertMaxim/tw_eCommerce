package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import com.example.springbootthymeleaftw.repository.MarketRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessRequestService {
    private final MarketRepository marketRepository;
    private final WarehouseRepository warehouseRepository;

    public Optional<ArrayList<Market>> getAllPendingMarketRequests() {
        Optional<ArrayList<Market>> markets = marketRepository.findAllBySignupStatus("Pending");
        return markets;
    }
    public Optional<ArrayList<Warehouse>> getAllPendingWarehouseRequests() {
        Optional<ArrayList<Warehouse>> warehouses = warehouseRepository.findAllBySignupStatus("Pending");
        return warehouses;
    }

}
