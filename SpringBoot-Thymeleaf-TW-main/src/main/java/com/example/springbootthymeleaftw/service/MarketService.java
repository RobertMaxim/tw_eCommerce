package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.DTO.MarketUserDTO;
import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.MarketRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private final UserService userService;

    public boolean save(MarketUserDTO marketUserDTO) {
        UserEntity user=new UserEntity();
        user.setUsername(marketUserDTO.getUsername());
        user.setPassword(marketUserDTO.getPassword());
        user.setEmail(marketUserDTO.getEmail());
        user.setRole(roleRepository.getRoleEntityByName("Market_Admin"));

        if (marketRepository.findMarketByIdentificationCode(marketUserDTO.getIdentificationCode()).isPresent()) {
            return false;
        }
        if (userService.save(user)) {
            Market market=new Market();
            market.setAdmin(user);
            market.setAddress(marketUserDTO.getAddress());
            market.setSignupStatus("Pending");
            market.setName(marketUserDTO.getMarketName());
            market.setIdentificationCode(marketUserDTO.getIdentificationCode());
            marketRepository.save(market);
            return true;
        }
        return false;
    }

}
