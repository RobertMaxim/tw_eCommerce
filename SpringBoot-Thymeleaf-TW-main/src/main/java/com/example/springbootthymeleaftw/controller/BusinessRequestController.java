package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.Warehouse;
import com.example.springbootthymeleaftw.service.AdminService;
import com.example.springbootthymeleaftw.service.BusinessRequestService;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/businessRequest")
@RequiredArgsConstructor
public class BusinessRequestController {
    @Autowired
    private final BusinessRequestService businessRequestService;
    @Autowired
    private final AdminService adminService;

    @GetMapping()
    public String open(Model model) {

        Optional<ArrayList<Market>> marketsOpt = businessRequestService.getAllPendingMarketRequests();
        Optional<ArrayList<Warehouse>> warehousesOpt = businessRequestService.getAllPendingWarehouseRequests();
        if (marketsOpt.isPresent()) {
            ArrayList<Market> markets = marketsOpt.get();
            if (markets.isEmpty()) {
                model.addAttribute("emptyMarketList", "No market sign-up requests");
            } else {
                model.addAttribute("marketList", markets);
                model.addAttribute("selectedMarket", new Market());

            }
        }
        if (warehousesOpt.isPresent()) {
            ArrayList<Warehouse> warehouses = warehousesOpt.get();
            if (warehouses.isEmpty()) {
                model.addAttribute("emptyWarehouseList", "No warehouse sign-up requests");
            } else {
                model.addAttribute("warehouseList", warehouses);
                model.addAttribute("selectedWarehouse", new Warehouse());
            }
        }
        return "businessRequest";
    }

    @PostMapping("approveMarket")
    public String approveMarketRequest(@ModelAttribute("selectedMarket") Market market, BindingResult bindingResult) {
        System.out.println(market.getName());
        adminService.acceptMarketRequest(market.getIdentificationCode());
        return "redirect:/businessRequest";
    }

    @PostMapping("denyMarket")
    public String denyMarketRequest(@ModelAttribute("selectedMarket") Market market, BindingResult bindingResult) {

        System.out.println(market.getName());
        adminService.denyMarketRequest(market.getIdentificationCode());
        return "redirect:/businessRequest";
    }

    @PostMapping("approveWarehouse")
    public String approveWarehouseRequest(@ModelAttribute("selectedWarehouse") Warehouse warehouse, BindingResult bindingResult) {
        System.out.println(warehouse.getName());
        adminService.acceptWarehouseRequest(warehouse.getIdentificationCode());
        return "redirect:/businessRequest";
    }

    @PostMapping("denyWarehouse")
    public String denyWarehouseRequest(@ModelAttribute("selectedWarehouse") Warehouse warehouse, BindingResult bindingResult) {
        System.out.println(warehouse.getName());
        adminService.denyWarehouseRequest(warehouse.getIdentificationCode());
        return "redirect:/businessRequest";
    }
}
