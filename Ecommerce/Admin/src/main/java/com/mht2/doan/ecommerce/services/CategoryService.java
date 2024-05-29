package com.mht2.doan.ecommerce.services;

import com.mht2.doan.ecommerce.dtos.CategoryDto;
import com.mht2.doan.ecommerce.models.Category;
import com.mht2.doan.ecommerce.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public List<Category> findALl() {
        return categoryRepository.findAll();
    }

    public List<CategoryDto> categories() {
        return transferData(categoryRepository.findAll());
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category save(CategoryDto categoryDto) {
        Category category = new Category();
        try {
            category.setName(categoryDto.getName());
            return categoryRepository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Category update(CategoryDto categoryDto) {
        try {
            Category categoryUpdate = categoryRepository.getReferenceById(categoryDto.getId());
            categoryUpdate.setId(categoryDto.getId());
            categoryUpdate.setName(categoryDto.getName());
            return categoryRepository.save(categoryUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CategoryDto getById(Integer id) {
        CategoryDto categoryDto = new CategoryDto();
        Category category = this.findById(id);
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    private List<CategoryDto> transferData(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }
}
