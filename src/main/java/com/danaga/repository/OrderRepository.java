package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
