package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Orders;
import com.danaga.repository.OrderRepository;

import jakarta.persistence.criteria.Order;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	OrderRepository orderRepository;
	
	/*
	 * order insert
	 */
	
	
	@Override
	public Orders insert(Orders order) {
		
		Orders insertOrder= orderRepository.save(order);
		 List<OrderItem> itemList = insertOrder.getOrderItems();
		 for (OrderItem orderItem : itemList) {
			orderItem.setOrders(order);
			OptionSet optionSet= orderItem.getOptionSet();
			
			
		}
		
		return null;
	}

	@Override
	public Orders updateOrderByOState(String oState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> findOrderByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> findOrderWithOrderItemByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Orders findOrderByOrderNo(Long oNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Orders findOrderByOrderNoAndMemberNameandMemberPhoneNo(Long oNo, String MemberName, String memberPhoneNo) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
