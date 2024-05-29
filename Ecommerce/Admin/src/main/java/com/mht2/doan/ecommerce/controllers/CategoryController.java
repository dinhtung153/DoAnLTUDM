package com.mht2.doan.ecommerce.controllers;

import com.mht2.doan.ecommerce.dtos.CategoryDto;
import com.mht2.doan.ecommerce.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping("")
    public String showCategoryList(Model model) {
        model.addAttribute("title", "Manage Category");
        List<CategoryDto> categories = categoryService.categories();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        return "categories";
    }

    @GetMapping("/add-category")
    public String addNew(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        model.addAttribute("title", "Add new category");
        return "add-category";
    }

    @PostMapping("/save-category")
    public String saveCategory(@ModelAttribute("categoryDto") CategoryDto category,
            RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(category);
            System.out.println("save successfully");
            redirectAttributes.addFlashAttribute("success", "Add new category successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new category!");
        }
        return "redirect:/categories";
    }

    @GetMapping("/update-category/{id}")
    public String updateCategoryForm(@PathVariable("id") Integer id, Model model) {
        CategoryDto categoryDto = categoryService.getById(id);
        model.addAttribute("title", "Update Category");
        model.addAttribute("categoryDto", categoryDto);
        return "update-category";
    }

    @PostMapping("/update-category/{id}")
    public String updateCategory(@ModelAttribute("categoryDto") CategoryDto categoryDto,
            RedirectAttributes redirectAttributes) {
        try {
            categoryService.update(categoryDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = { RequestMethod.GET, RequestMethod.PUT })
    public String delete(@RequestParam int id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Delete successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/categories";
    }
}
