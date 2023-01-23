package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.DTO.MarketUserDTO;
import com.example.springbootthymeleaftw.DTO.WarehouseUserDTO;
import com.example.springbootthymeleaftw.model.entity.*;
import com.example.springbootthymeleaftw.repository.MarketRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final MarketRepository marketRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private final UserService userService;
    private final WarehouseRepository warehouseRepository;

    public boolean save(WarehouseUserDTO warehouseUserDTO) {
        UserEntity user=new UserEntity();
        user.setUsername(warehouseUserDTO.getUsername());
        user.setPassword(warehouseUserDTO.getPassword());
        user.setEmail(warehouseUserDTO.getEmail());
        user.setRole(roleRepository.getRoleEntityByName("Warehouse_Admin"));

        if (warehouseRepository.findWarehouseByIdentificationCode(warehouseUserDTO.getIdentificationCode()).isPresent()) {
            return false;
        }
        if (userService.save(user)) {
            Warehouse warehouse=new Warehouse();
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

}
