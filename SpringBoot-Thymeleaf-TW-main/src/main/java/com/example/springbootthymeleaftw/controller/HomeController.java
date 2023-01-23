package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.config.CurrentUser;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/") // this is default
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;

    @GetMapping()
    public String open(Model model, String error, String logout) {
        if (!securityService.isAuthenticated()) {
            return "login";
        }

        CurrentUser user = (CurrentUser) securityService.getAuthenticated().getPrincipal();

        if (user.getUserEntity().getRole().getName().equals("Admin")) {
            return "redirect:/adminHomepage";
        }

        if (user.getMarketDetails() != null) {
            if (user.getMarketDetails().getSignupStatus().equals("Pending")) {
                model.addAttribute("accountInvalid", "This account hasn't been approved by an admin");
                return "login";
            }
        } else if (user.getWarehouseDetails() != null) {
            if (user.getWarehouseDetails().getSignupStatus().equals("Pending")) {
                model.addAttribute("accountInvalid", "This account hasn't been approved by an admin");
                return "login";
            }
        }

        model.addAttribute("name", securityService.getAuthenticated().getName());
        return "index";
    }
}
