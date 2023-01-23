package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    Optional<Market> findMarketByIdentificationCode(String identificationCode);
    Optional<ArrayList<Market>> findAllBySignupStatus(String signupStatus);
    Market findMarketByAdminEmail(String email);
}