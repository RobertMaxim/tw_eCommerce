package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.DTO.MarketUserDTO;
import com.example.springbootthymeleaftw.DTO.WarehouseUserDTO;
import com.example.springbootthymeleaftw.model.entity.*;
import com.example.springbootthymeleaftw.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final MarketRepository marketRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private final UserService userService;
    private final WarehouseRepository warehouseRepository;
    private final MarketSupplierRequestRepository supplierRequestRepository;
    private final StorageWarehouseRepository storageWarehouseRepository;

    public boolean save(WarehouseUserDTO warehouseUserDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(warehouseUserDTO.getUsername());
        user.setPassword(warehouseUserDTO.getPassword());
        user.setEmail(warehouseUserDTO.getEmail());
        user.setRole(roleRepository.getRoleEntityByName("Warehouse_Admin"));

        if (warehouseRepository.findWarehouseByIdentificationCode(warehouseUserDTO.getIdentificationCode()).isPresent()) {
            return false;
        }
        if (userService.save(user)) {
            Warehouse warehouse = new Warehouse();
            warehouse.setAdmin(user);
            warehouse.setAddress(warehouseUserDTO.getAddress());
            warehouse.setSignupStatus("Pending");
            warehouse.setName(warehouseUserDTO.getWarehouseName());
            warehouse.setIdentificationCode(warehouseUserDTO.getIdentificationCode());
            warehouseRepository.save(warehouse);
            return true;
        }
        return false;
    }

    public Optional<ArrayList<MarketSupplierRequest>> getAllPendingSupplierRequestsFromMarket(Warehouse warehouse) {
        Optional<ArrayList<MarketSupplierRequest>> markets = supplierRequestRepository.findAllByWarehouseAndStatus(warehouse, "Pending");
        return markets;

    }

    public Optional<ArrayList<StorageWarehouse>> getAllProductsForWarehouse(Warehouse warehouse)
    {
        Optional<ArrayList<StorageWarehouse>> productsList= storageWarehouseRepository.findAllByWarehouse(warehouse);
        return productsList;
    }

    public void updateSupplierRequest(MarketSupplierRequest request, String updatedStatus) {
        Optional<MarketSupplierRequest> dbSupplierRequest = supplierRequestRepository.findMarketSupplierRequestById(request.getId());
        if (dbSupplierRequest.isPresent()) {
            MarketSupplierRequest actualRequest = dbSupplierRequest.get();
            actualRequest.setStatus(updatedStatus);
            supplierRequestRepository.save(actualRequest);
        }
    }

    public void updateQuantityOnProduct(StorageWarehouse storageWarehouse){
        Optional<StorageWarehouse> dbStorageWarehouse=storageWarehouseRepository.findById(storageWarehouse.getId());

        if(dbStorageWarehouse.isPresent())
        {
            StorageWarehouse actualInventory= dbStorageWarehouse.get();
            actualInventory.setQuantity(storageWarehouse.getQuantity());
            storageWarehouseRepository.save(actualInventory);
        }
    }

}
