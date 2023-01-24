package com.example.springbootthymeleaftw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {
    private static final String[] METHODS_ALLOWED = {
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
    };


    @Bean
    SecurityFilterChain resources (HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                //.antMatchers( "/**").permitAll()
                //.antMatchers("/adminHomepage").hasRole("Admin")
                .antMatchers("/adminHomepage/**").hasAuthority("Admin")
                .antMatchers("/adminHomepage").hasAuthority("Admin")
                .antMatchers("/businessRequest").hasAuthority("Admin")
                .antMatchers("/warehouseHomepage").hasAuthority("Warehouse_Admin")
                .antMatchers("/warehouseHomepage/inventory").hasAuthority("Warehouse_Admin")
                .antMatchers("/warehouseHomepage/supplyRequests").hasAuthority("Warehouse_Admin")
                .antMatchers("/marketHomepage").hasAuthority("Market_Admin")
                .antMatchers("/clientHomepage").hasAuthority("Client")
                .antMatchers("/").authenticated()
                .antMatchers("/register/market").anonymous()
                .antMatchers("/register/client").anonymous()
                .antMatchers("/register/warehouse").anonymous()
                .and()
                .formLogin()
                .loginPage("/login")
                //.failureUrl("/login-error") //if you want a separate page for failed auth.
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(METHODS_ALLOWED)
                .allowedOrigins("*");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
