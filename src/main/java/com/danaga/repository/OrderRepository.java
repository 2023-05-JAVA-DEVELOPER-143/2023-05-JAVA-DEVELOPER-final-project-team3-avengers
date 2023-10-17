package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Orders, Long>{


	
	public void selectByMember_MemberId(String memberId);
}
