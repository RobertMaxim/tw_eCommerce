package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import com.example.springbootthymeleaftw.repository.MarketRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import com.example.springbootthymeleaftw.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MarketRepository marketRepository;
    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public void marketRequestUpdate(String identificationCode, String newStatus) {
        Optional<Market> optionalMarket = marketRepository.findMarketByIdentificationCode(identificationCode);
        if (optionalMarket.isPresent()) {
            Market toBeUpdatedMarket = optionalMarket.get();
            toBeUpdatedMarket.setSignupStatus(newStatus);
            marketRepository.save(toBeUpdatedMarket);
        }
    }

    public void warehouseRequestUpdate(String identificationCode, String newStatus) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findWarehouseByIdentificationCode(identificationCode);
        if (optionalWarehouse.isPresent()) {
            Warehouse toBeUpdatedWarehouse = optionalWarehouse.get();
            toBeUpdatedWarehouse.setSignupStatus(newStatus);
            warehouseRepository.save(toBeUpdatedWarehouse);
        }
    }

    public void acceptMarketRequest(String marketIdentificationCode) {
        marketRequestUpdate(marketIdentificationCode, "Approved");
    }

    public void denyMarketRequest(String marketIdentificationCode) {
        marketRequestUpdate(marketIdentificationCode, "Denied");
    }

    public void acceptWarehouseRequest(String warehouseIdentificationCode){
        warehouseRequestUpdate(warehouseIdentificationCode,"Approved");
    }

    public void denyWarehouseRequest(String warehouseIdentificationCode){
        warehouseRequestUpdate(warehouseIdentificationCode,"Deny");
    }

    public void passwordReset(UserEntity newUserData)
    {
        Optional<UserEntity> userOpt=userRepository.findByEmail(newUserData.getEmail());
        if(userOpt.isPresent())
        {
            UserEntity user=userOpt.get();
            user.setPassword(newUserData.getPassword());

            userService.save(user);
        }
    }

}
