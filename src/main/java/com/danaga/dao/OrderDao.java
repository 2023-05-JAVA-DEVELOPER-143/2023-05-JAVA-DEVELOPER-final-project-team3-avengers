package com.danaga.dao;

import java.util.List;

import org.aspectj.weaver.ast.Or;

import com.danaga.entity.Orders;

import jakarta.persistence.criteria.Order;

public interface OrderDao {
	/*
	 * 주문생성 
	 */
	public Orders insert(Orders order);
	
	/*
	 * 주문전체삭제
	 */
	public void deleteByUserId(String memberId) throws Exception;
	
	/*
	 * 주문1건삭제
	 */
	public void deleteByOrderNo(Long o_no);
	
	/*
	 * 주문전체(특정사용자)
	 */
	public List<Orders> findOrderByUserId(Long memberIdCode);
	
	/*
	 * 주문+주문아이템 전체(특정사용자)
	 */
	public List<Orders> findOrderWithOrderItemByUserId(Long memberIdCode);
	
	/*
	 * 주문1개보기(주문상세리스트)
	 */
	public Orders findbyOrderNo(Long memberIdCode);
	
}
