package org.rma.springmvcdemo.repository;


import org.rma.springmvcdemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}