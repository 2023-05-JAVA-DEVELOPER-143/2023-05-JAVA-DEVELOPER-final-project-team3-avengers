package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
