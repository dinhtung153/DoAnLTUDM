package com.mht.doan.ecommerce.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mht.doan.ecommerce.dtos.ProductDto;
import com.mht.doan.ecommerce.models.Customer;
import com.mht.doan.ecommerce.models.ShoppingCart;
import com.mht.doan.ecommerce.services.CustomerService;
import com.mht.doan.ecommerce.services.ProductService;
import com.mht.doan.ecommerce.services.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;

    @SuppressWarnings("null")
    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getCart();
        if (cart == null) {
            model.addAttribute("check");

        }
        if (cart != null) {
            model.addAttribute("total", Math.ceil(cart.getTotalPrice()));
            model.addAttribute("ship", Math.ceil(cart.getTotalPrice() * 0.1));
            model.addAttribute("grandTotal", Math.ceil(cart.getTotalPrice() * 1.1));
        }
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("title", "Cart");
        session.setAttribute("totalItems", cart.getTotalItems());
        return "cart";

    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") int id,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            HttpServletRequest request,
            Model model,
            Principal principal,
            HttpSession session) {

        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    // @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    // public String updateCart(@RequestParam("id") int id,
    //         @RequestParam("quantity") int quantity,
    //         Model model,
    //         Principal principal,
    //         HttpSession session) {
    //     if (principal == null) {
    //         return "redirect:/login";
    //     }
    //     ProductDto productDto = productService.getById(id);
    //     String username = principal.getName();
    //     ShoppingCart shoppingCart = cartService.updateCart(productDto, quantity, username);
    //     model.addAttribute("shoppingCart", shoppingCart);
    //     session.setAttribute("totalItems", shoppingCart.getTotalItems());
    //     return "redirect:/cart";

    // }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") int id,
            Model model,
            Principal principal,
            HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }
}