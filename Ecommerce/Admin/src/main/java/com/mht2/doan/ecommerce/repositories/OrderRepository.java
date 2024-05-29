package com.mht2.doan.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mht2.doan.ecommerce.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
