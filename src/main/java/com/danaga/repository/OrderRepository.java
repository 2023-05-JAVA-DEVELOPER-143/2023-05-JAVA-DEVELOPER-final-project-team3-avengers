package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Orders, Long>{


	/************************Custom Method******************/
	/*
	 * MemberId로 order찾기(회원)
	 */
	public void findByMember_MemberId(String memberId);
	
	/*
	 * 
	 */
}
