package com.danaga.dao;

import java.util.List;

import org.aspectj.weaver.ast.Or;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;

public interface OrderDao {
	
	//  주문생성 (주문생성시 order,delivery,orderItem insert가 동시에 이뤄져야함) (비회원 주문생성시에는
	//  order,delivery,orderItem,member insert가 동시에이뤄져야함) (비회원 member insert시에는
	//  member가 있는지 validate하고 있으면 안만들구 없으면 만듬)
	  
//	  public Orders insert(Orders order);
	  
	// 주문 save 
	  Orders save(Orders orders);
	
	//  주문상태업데이트(특정주문)
	  // 1.정상주문
	  Orders updateStatementByNormalOrder(Long orderNo);
	  // 2.취소주문
	  Orders updateStatementByCancleOrder(Long orderNo);
	  // 3.환불주문
	  Orders updateStatementByRefundOrder(Long orderNo);
	  
	//  주문전체(특정사용자)
	  
	  List<Orders> findOrdersByMember_UserName(String userName);
	  
	//  주문1개보기(주문상세리스트)
	  
	  Orders findById(Long id)throws Exception;
	  
	//  비회원 주문1개보기(주문상세리스트)
	  
	  Orders findOrdersByIdAndNameAndPhoneNo(Long orderNo, String userName,String phoneNo)throws Exception;
	
}
