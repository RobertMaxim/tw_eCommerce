package com.example.springbootthymeleaftw.config;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrentUser implements UserDetails {
    private UserEntity userEntity;
    private Market marketDetails = null;
    private Warehouse warehouseDetails = null;

    public CurrentUser(UserEntity user, Object business) {
        this.userEntity = user;
        if (business != null && business.getClass().equals(Market.class))
            this.marketDetails = (Market) business;
        else if (business != null && business.getClass().equals(Warehouse.class))
            this.warehouseDetails = (Warehouse) business;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList(List.of(new SimpleGrantedAuthority(userEntity.getRole().getName())));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Market getMarketDetails() {
        return marketDetails;
    }

    public void setMarketDetails(Market marketDetails) {
        this.marketDetails = marketDetails;
    }

    public Warehouse getWarehouseDetails() {
        return warehouseDetails;
    }

    public void setWarehouseDetails(Warehouse warehouseDetails) {
        this.warehouseDetails = warehouseDetails;
    }
}
