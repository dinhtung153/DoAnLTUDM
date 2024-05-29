package com.mht.doan.ecommerce.controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mht.doan.ecommerce.dtos.ProductDto;
import com.mht.doan.ecommerce.models.Category;
import com.mht.doan.ecommerce.services.CategoryService;
import com.mht.doan.ecommerce.services.ProductService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/shop")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final CategoryService categoryService;

    @GetMapping("")
    public String getProductsList(Model model) {
        List<ProductDto> products = productService.products();
        List<Category> categories = categoryService.findALl();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        return "shop";
    }

    @GetMapping("/product-detail/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        ProductDto product = productService.getById(id);
        List<ProductDto> productDtoList = productService.findAllByCategory(product.getCategory().getName());
        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Product Detail");
        model.addAttribute("page", "Product Detail");
        model.addAttribute("productDetail", product);
        return "detail";
    }

}
