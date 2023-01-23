package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.DTO.MarketUserDTO;
import com.example.springbootthymeleaftw.DTO.WarehouseUserDTO;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.RoleRepository;
import com.example.springbootthymeleaftw.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserValidatorService userValidatorService;
    private final MarketValidatorService marketValidatorService;
    private final WarehouseValidatorService warehouseValidatorService;
    private final WarehouseService warehouseService;
    private final UserService userService;
    private final MarketService marketService;
    private final RoleRepository roleRepository;

    @GetMapping("client")
    public String open(Model model) {
        System.out.println(model);
        model.addAttribute("userForm", new UserEntity());

        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult) {
        userValidatorService.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "register";

        userForm.setRole(roleRepository.getRoleEntityByName("Client"));
        userService.save(userForm);
        userService.login(userForm.getEmail(), userForm.getPassword());
        return "redirect:/";
    }

    @GetMapping("market")
    public String openMarketRegister(Model model) {
        System.out.println(model);
        model.addAttribute("marketUserForm", new MarketUserDTO());

        return "marketRegister";
    }

    @PostMapping("marketRegister")
    public String marketSignIn(@ModelAttribute("marketUserForm") MarketUserDTO marketUserForm, BindingResult bindingResult) {
        marketValidatorService.validate(marketUserForm,bindingResult);

        if (bindingResult.hasErrors()) {
            return "marketRegister";
        }

        if (!marketService.save(marketUserForm))
            return "marketRegister";

        userService.login(marketUserForm.getEmail(), marketUserForm.getPassword());
        return "redirect:/";
    }

    @GetMapping("warehouse")
    public String openWarehouseRegister(Model model) {
        System.out.println(model);
        model.addAttribute("warehouseUserForm", new WarehouseUserDTO());

        return "warehouseRegister";
    }

    @PostMapping("warehouseRegister")
    public String warehouseSignIn(@ModelAttribute("warehouseUserForm") WarehouseUserDTO warehouseUserForm, BindingResult bindingResult) {
        warehouseValidatorService.validate(warehouseUserForm,bindingResult);

        if (bindingResult.hasErrors()) {
            return "warehouseRegister";
        }

        if (!warehouseService.save(warehouseUserForm))
            return "warehouseRegister";

        userService.login(warehouseUserForm.getEmail(), warehouseUserForm.getPassword());
        return "redirect:/";
    }
}
