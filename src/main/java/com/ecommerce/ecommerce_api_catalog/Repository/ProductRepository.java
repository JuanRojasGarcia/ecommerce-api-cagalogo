package com.ecommerce.ecommerce_api_catalog.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce_api_catalog.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
 
}
