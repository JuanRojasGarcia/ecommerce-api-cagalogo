package com.ecommerce.ecommerce_api_catalog.Repository;

import com.ecommerce.ecommerce_api_catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{
    // Spring Data JPA ya nos regala métodos como save(), findAll(), findById() automáticamente.

    
}
