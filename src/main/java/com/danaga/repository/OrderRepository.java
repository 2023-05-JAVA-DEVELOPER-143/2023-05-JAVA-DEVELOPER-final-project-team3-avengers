package com.danaga.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Orders, Long> {

	// ************************ Custom Method ******************//

    // 사용자 정의 메서드: 회원 ID를 기반으로 주문 목록 조회
	
	//  주문전체(특정사용자)
	List<Orders> findOrdersByMember_UserName(String userName);
	
//	//  주문이 일정시간경과되면 자동삭제(지금은 임의로 보여주기위해 3분으로 지정)
//	void deleteByCreatedAtBefore(LocalDateTime timeAgo);

	// 비회원 : 주문 번호, 회원 이름, 회원 전화번호를 기반으로 주문 조회
	Orders findOrdersByIdAndMember_NameAndMember_PhoneNo(Long orderNo, String userName, String phoneNo);

}
