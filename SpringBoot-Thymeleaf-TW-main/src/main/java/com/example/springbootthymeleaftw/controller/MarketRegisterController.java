package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.DTO.MarketUserDTO;
import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.UserService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/market_register")
@RequiredArgsConstructor
public class MarketRegisterController {
    private final UserValidatorService userValidatorService;
    private final UserService userService;

    @GetMapping()
    public String open(Model model){
        System.out.println(model);
        model.addAttribute("marketUserForm", new MarketUserDTO());

        return "market_register";
    }

    @PostMapping()
    public String register(@ModelAttribute("marketUserForm") MarketUserDTO marketUserForm, BindingResult bindingResult) {
        userValidatorService.validate(marketUserForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "market_register";
        }
        userService.save(marketUserForm.getUser());
        //introduce user in bd -> trb introdus si market in bd daca e introdus cu succes(cautare dupa email)
        userService.login(marketUserForm.getUser().getEmail(), marketUserForm.getUser().getPassword());
        return "login";
    }
}