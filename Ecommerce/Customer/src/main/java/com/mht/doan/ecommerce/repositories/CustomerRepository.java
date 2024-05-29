package com.mht.doan.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mht.doan.ecommerce.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    Customer findByUsername(String username);
}
