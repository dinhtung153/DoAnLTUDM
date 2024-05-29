package com.mht.doan.ecommerce.dtos;


import com.mht.doan.ecommerce.models.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private int id;

    @NotEmpty(message = "The name is required")
    private String name;

    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size(max = 100, message = "The description cannot exceed 100 characters")
    private String description;

    @Min(0)
    private int currentQuantity;

    @Min(0)
    private double costPrice;

    @Min(0)
    private double salePrice;

    private String image;

    private Category category;
    
    private String currentPage;
}