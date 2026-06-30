package com.ecommerce.ecommerce_api_catalog.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_api_catalog.Repository.ProductRepository;
import com.ecommerce.ecommerce_api_catalog.entity.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final ProductRepository productRepository;

    @GetMapping("/status")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("Catalog API v1 is up prueba");
    }

    // Endpoint real para listar productos
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }
}
