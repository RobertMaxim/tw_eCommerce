package com.example.springbootthymeleaftw.DTO;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.UserEntity;

public class MarketUserDTO {
    private Market market;
    private UserEntity user;

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
