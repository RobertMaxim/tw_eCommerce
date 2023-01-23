package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.config.CurrentUser;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.AdminService;
import com.example.springbootthymeleaftw.service.SecurityService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/") // this is default
@RequestMapping("/adminHomepage")
@RequiredArgsConstructor
public class AdminController {
    private final SecurityService securityService;
    private final AdminService adminService;
    private final UserValidatorService userValidatorService;

    @GetMapping()
    public String open(Model model, String error, String logout) {
        if (!securityService.isAuthenticated()) {
            return "login";
        }

        model.addAttribute("name", securityService.getAuthenticated().getName());

        return "adminHomepage";
    }

    @GetMapping("resetPassword")
    public String openResetPassword(Model model){
        CurrentUser user=(CurrentUser) securityService.getAuthenticated().getPrincipal();
        model.addAttribute("adminAccount", user.getUserEntity());
        return "resetPassword";
    }

    @PostMapping("resetPassword")
    public String resetPassword(@ModelAttribute("adminAccount") UserEntity account, BindingResult bindingResult)
    {
        userValidatorService.resetPasswordValidate(account,bindingResult);
        if (!bindingResult.hasErrors()) {
            adminService.passwordReset(account);
            return "resetPassword";
        }

        return "resetPassword";
    }
}