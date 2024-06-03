package com.mht.doan.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mht.doan.ecommerce.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1")
    List<Product> findAllByCategory(String category);
}
