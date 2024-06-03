package com.mht.doan.ecommerce.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mht.doan.ecommerce.dtos.CustomerDto;
import com.mht.doan.ecommerce.models.Customer;
import com.mht.doan.ecommerce.models.Order;
import com.mht.doan.ecommerce.models.ShoppingCart;
import com.mht.doan.ecommerce.services.CustomerService;
import com.mht.doan.ecommerce.services.OrderService;
import com.mht.doan.ecommerce.services.ShoppingCartService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final CustomerService customerService;
    private final OrderService orderService;

    @SuppressWarnings("unused")
    private final ShoppingCartService cartService;

    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.getCustomer(principal.getName());
            ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();
            model.addAttribute("customer", customer);
            model.addAttribute("shoppingCart", cart);
            model.addAttribute("total", Math.ceil(cart.getTotalPrice()));
            model.addAttribute("ship", Math.ceil(cart.getTotalPrice() * 0.1));
            model.addAttribute("grandTotal", Math.ceil(cart.getTotalPrice() * 1.1));
            return "checkout";
        }
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            List<Order> orderList = customer.getOrders();
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            return "order-detail";
        }
    }

    // @RequestMapping(value = "/cancel-order", method = { RequestMethod.PUT, RequestMethod.GET })
    // public String cancelOrder(int id, RedirectAttributes attributes) {
    //     orderService.cancelOrder(id);
    //     attributes.addFlashAttribute("success", "Cancel order successfully!");
    //     return "redirect:/orders";
    // }

    @RequestMapping(value = "/add-order", method = { RequestMethod.POST })
    public String createOrder(Principal principal,
            Model model,
            HttpSession session, 
            @RequestParam("payment") String paymentMethod) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getCart();
            orderService.save(cart, paymentMethod);
            List<Order> orders = orderService.findALlOrders();
            session.removeAttribute("totalItems");
            model.addAttribute("orders", orders);
            model.addAttribute("title", "Order Detail");
            model.addAttribute("page", "Order Detail");
            model.addAttribute("success", "Add order successfully");
            return "redirect:/orders";
        }
    }

}