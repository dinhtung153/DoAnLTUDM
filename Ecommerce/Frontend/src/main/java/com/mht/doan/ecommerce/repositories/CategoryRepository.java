package com.mht.doan.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mht.doan.ecommerce.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
