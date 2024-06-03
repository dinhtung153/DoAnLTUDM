package com.mht2.doan.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mht2.doan.ecommerce.models.Category;
import com.mht2.doan.ecommerce.models.Order;
import com.mht2.doan.ecommerce.services.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @GetMapping("")
    public String showOrderList(Model model) {
        model.addAttribute("title", "Manage Order");
        List<Order> orders = orderService.findALl();
        model.addAttribute("orders", orders);
        model.addAttribute("size", orders.size());
        model.addAttribute("categoryNew", new Category());
        return "orders";
    }

    @GetMapping("/update-order/{id}")
    public String updateOrderForm(@PathVariable("id") Integer id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("title", "Update Order");
        model.addAttribute("order", order);
        return "update-order";
    }

    @PostMapping("/update-order/{id}")
    public String updateOrder(@ModelAttribute("order") Order order,
            RedirectAttributes redirectAttributes) {
        try {
            orderService.update(order);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/orders";
    }


}
