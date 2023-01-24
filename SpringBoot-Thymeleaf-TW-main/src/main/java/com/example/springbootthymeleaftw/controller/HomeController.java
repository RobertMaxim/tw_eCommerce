package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.config.CurrentUser;
import com.example.springbootthymeleaftw.service.SecurityService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/") // this is default
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;
    private final UserValidatorService userValidatorService;

    @GetMapping()
    public String open(Model model, String error, String logout) {
        if (!securityService.isAuthenticated()) {
            return "login";
        }

        CurrentUser user = (CurrentUser) securityService.getAuthenticated().getPrincipal();

        if (user.getUserEntity().getRole().getName().equals("Admin")) {
            return "redirect:/adminHomepage";
        }
        else if(user.getUserEntity().getRole().getName().equals("Client")){
            return "clientHomepage";
        }

        if (user.getMarketDetails() != null) {

            String attributeValue = userValidatorService.checkIfSignupStatusIsAccepted(user.getMarketDetails().getSignupStatus());
            if (!attributeValue.isEmpty()) {
                model.addAttribute("accountInvalid", attributeValue);
                return "login";
            }
            else{
                return "marketHomepage";
            }
        } else if (user.getWarehouseDetails() != null) {
            String attributeValue = userValidatorService.checkIfSignupStatusIsAccepted(user.getWarehouseDetails().getSignupStatus());
            if (!attributeValue.isEmpty()) {
                model.addAttribute("accountInvalid", attributeValue);
                return "login";
            } else {
                return "redirect:/warehouseHomepage";
            }
        }

        model.addAttribute("name", securityService.getAuthenticated().getName());
        return "index";
    }
}
