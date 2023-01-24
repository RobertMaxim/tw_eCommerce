package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.config.CurrentUser;
import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.MarketSupplierRequest;
import com.example.springbootthymeleaftw.model.entity.StorageWarehouse;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.StorageWarehouseRepository;
import com.example.springbootthymeleaftw.service.AdminService;
import com.example.springbootthymeleaftw.service.SecurityService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import com.example.springbootthymeleaftw.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.error.Mark;

import java.util.ArrayList;
import java.util.Optional;

@Controller
//@RequestMapping("/") // this is default
@RequestMapping("/warehouseHomepage")
@RequiredArgsConstructor
public class WarehouseController {
    private final SecurityService securityService;
    private final WarehouseService warehouseService;

    @GetMapping()
    public String open(Model model, String error, String logout) {
        if (!securityService.isAuthenticated()) {
            return "login";
        }
        CurrentUser currentUser = (CurrentUser) securityService.getAuthenticated().getPrincipal();

        model.addAttribute("name", securityService.getAuthenticated().getName());

        return "warehouseHomepage";
    }

    @GetMapping("inventory")
    public String openWarehouseInventory(Model model) {
        CurrentUser currentWarehouseUser = (CurrentUser) securityService.getAuthenticated().getPrincipal();

        Optional<ArrayList<StorageWarehouse>> productsList = warehouseService.getAllProductsForWarehouse(currentWarehouseUser.getWarehouseDetails());

        if (productsList.isPresent()) {
            ArrayList<StorageWarehouse> products = productsList.get();
            if (products.isEmpty()) {
                model.addAttribute("emptyProductsList", "No products in current warehouse storage inventory");
            } else {
                model.addAttribute("productsList", products);
                model.addAttribute("selectedProduct", new StorageWarehouse());
            }
        }

        return "warehouseInventory";
    }

    @PostMapping("saveQuantity")
    public String saveProductQuantity(@ModelAttribute("selectedProduct") StorageWarehouse productFromStorage, Model model) {
        warehouseService.updateQuantityOnProduct(productFromStorage);
        return "redirect:/warehouseHomepage/inventory";
    }

    @GetMapping("supplyRequests")
    public String openSupplyRequests(Model model) {
        CurrentUser currentWarehouseUser = (CurrentUser) securityService.getAuthenticated().getPrincipal();

        Optional<ArrayList<MarketSupplierRequest>> supplierRequests = warehouseService.getAllPendingSupplierRequestsFromMarket(currentWarehouseUser.getWarehouseDetails());
        if (supplierRequests.isPresent()) {
            ArrayList<MarketSupplierRequest> requests = supplierRequests.get();
            if (requests.isEmpty()) {
                model.addAttribute("emptySupplierRequestsList", "No supply requests available at the moment");
            } else {
                model.addAttribute("supplierRequests", requests);
                model.addAttribute("selectedRequest", new MarketSupplierRequest());
            }
        }
        return "marketSupplyRequest";
    }

    @PostMapping("approveSupplyRequest")
    public String approveRequest(@ModelAttribute("selectedMarket") MarketSupplierRequest request, Model model) {
        warehouseService.updateSupplierRequest(request, "Approved");
        return "redirect:/warehouseHomepage/supplyRequests";
    }

    @PostMapping("denySupplyRequest")
    public String denyRequest(@ModelAttribute("selectedMarket") MarketSupplierRequest request, Model model) {
        warehouseService.updateSupplierRequest(request, "Denied");
        return "redirect:/warehouseHomepage/supplyRequests";
    }
}