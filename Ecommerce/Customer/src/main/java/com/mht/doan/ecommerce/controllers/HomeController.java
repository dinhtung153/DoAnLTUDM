package com.mht.doan.ecommerce.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mht.doan.ecommerce.dtos.ProductDto;
import com.mht.doan.ecommerce.models.Category;
import com.mht.doan.ecommerce.models.Customer;
import com.mht.doan.ecommerce.models.ShoppingCart;
import com.mht.doan.ecommerce.services.CategoryService;
import com.mht.doan.ecommerce.services.CustomerService;
import com.mht.doan.ecommerce.services.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private final ProductService productService;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final CustomerService customerService;

    @GetMapping("")
    public String getProductsList(Model model, Principal principal, HttpSession session) {
        List<ProductDto> products = productService.products();
        List<Category> categories = categoryService.findALl();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
         if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getUsername());
            ShoppingCart shoppingCart = customer.getCart();
            if (shoppingCart != null) {
                session.setAttribute("totalItems", shoppingCart.getTotalItems());
            }
        }
        return "index";
    }
}
