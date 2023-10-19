package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Orders, Long>{


	/************************Custom Method******************/
	/*
	 * MemberId로 order찾기(회원)
	 */
	public void findByMember_UserName(String userName);
	
    // 사용자 정의 메서드: 회원 ID를 기반으로 주문 목록 조회
    List<Orders> findByUserName(String userName);


    // 비회원 : 주문 번호, 회원 이름, 회원 전화번호를 기반으로 주문 조회
    Orders findOrderByOrderNoAndNameAndPhoneNo(Long oNo, String memberName, String memberPhoneNo);	/*
	 * 
	 */
}
