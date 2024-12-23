package com.example.products.repository;

import com.example.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ===========================
// Repository: ProductRepository
// ===========================
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}