package com.mht.doan.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer id;

    private ShoppingCartDto cart;

    private ProductDto product;

    private int quantity;

    private double unitPrice;

}