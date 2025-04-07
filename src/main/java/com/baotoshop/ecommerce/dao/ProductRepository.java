package com.baotoshop.ecommerce.dao;

import com.baotoshop.ecommerce.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
