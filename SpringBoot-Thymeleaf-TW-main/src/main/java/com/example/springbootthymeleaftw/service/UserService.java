package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.config.CurrentUser;
import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import com.example.springbootthymeleaftw.repository.MarketRepository;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import com.example.springbootthymeleaftw.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final WarehouseRepository warehouseRepository;
    private final MarketRepository marketRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optUser = userRepository.findByEmail(email);
        if (optUser.isPresent()) {
            UserEntity appUser = optUser.get();
            Object business = null;

            if (appUser.getRole() == null) {
                throw new UsernameNotFoundException("User " + appUser.getUsername() + " has no authorities");
            }
            if (appUser.getRole().getName().equals(roleRepository.getRoleEntityByName("Warehouse_Admin").getName())) {
                business = warehouseRepository.getWarehouseByAdminEmail(appUser.getEmail());
            } else if (appUser.getRole().getName().equals(roleRepository.getRoleEntityByName("Market_Admin").getName())) {
                business = marketRepository.findMarketByAdminEmail(appUser.getEmail());
            }
            return new CurrentUser(appUser, business);
        }
        throw new UsernameNotFoundException(email);
    }

    public boolean save(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void login(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        if (Objects.isNull(userDetails))
            return;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
