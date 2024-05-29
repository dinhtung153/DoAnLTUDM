package com.mht.doan.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mht.doan.ecommerce.models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "delete from cart_items shopping_cart_id = ?1", nativeQuery = true)
    void deleteCartItemById(Integer cartId);
}