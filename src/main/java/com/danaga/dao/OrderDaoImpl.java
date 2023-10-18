package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.config.OrderStateMsg;
import com.danaga.dto.OrderItemDto;
import com.danaga.entity.Delivery;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Orders;
import com.danaga.repository.DeliveryRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OrderItemRepository;
import com.danaga.repository.OrderRepository;

import jakarta.persistence.criteria.Order;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	
	
	
	@Override
	public Orders insert(Orders order) {
		
		/*
		 * 1.Order를 저장
		 * 2.OrderItemList를 저장
		 * 3.Delivery저장 --service에서 처리
		 * 4.비회원 Member저장--service에서 처리
		 */
		Orders insertOrder = orderRepository.save(order);
		List<OrderItem> itemList = insertOrder.getOrderItems();
		
		for (OrderItem orderItem : itemList) {
			orderItem.setOrders(order);
			orderItemRepository.save(orderItem);
		}
		
		return insertOrder; // 저장된 주문 객체 반환
	}
	
	@Override
	public Orders updateOrderByOState(String oState) {
		if(OrderStateMsg.msg1.equals(oState)) {
			
		}else if (OrderStateMsg.msg2.equals(oState)) {
			
		}else if (OrderStateMsg.msg3.equals(oState)) {
			
		}else if (OrderStateMsg.msg4.equals(oState)) {
			
		}else if (OrderStateMsg.msg5.equals(oState)) {
			
		}else if (OrderStateMsg.msg6.equals(oState)) {
			
		}
		return null;
	}

	@Override
	public List<Orders> findOrderByMemberId(Long memberId) {
	//	Member findMember= memberRepository.findByMemberId(memberId); //memberRepository에 findbyMemberId 메서드 추가
		
	//	return orderRepository.findByMemberIdCode(findMember.getMemberIdCode);
		
		return null;
	}

	@Override
	public List<Orders> findOrderWithOrderItemByMemberId(Long memberId) {
		// 주어진 memberId를 사용하여 해당 회원의 주문 목록을 조회하고, 주문 항목을 함께 로드하려면 적절한 로직을 추가
		// orderRepository와 JOIN 또는 FetchType.LAZY를 사용하여 관련된 주문 항목을 함께 로드
		return null;
	}

	@Override
	public Orders findOrderByOrderNo(Long oNo) {
		// 주어진 주문 번호 (oNo)를 사용하여 해당 주문을 조회하려면 적절한 로직을 추가
		// orderRepository를 사용하여 데이터베이스에서 주문을 조회
		return orderRepository.findById(oNo).get();
	}

	@Override
	public Orders findOrderByOrderNoAndMemberNameandMemberPhoneNo(Long oNo, String MemberName, String memberPhoneNo) {
		// 주어진 주문 번호 (oNo), 회원 이름 (MemberName), 회원 전화번호 (memberPhoneNo)를 사용하여 주문을 조회하려면 적절한 로직을 추가
		// orderRepository를 사용하여 데이터베이스에서 주문을 조회할 수 있으며, 필요한 정보를 조건에 따라 조회
		return orderRepository.findOrderByOrderNoAndMemberNameAndMemberPhoneNo(oNo, MemberName, memberPhoneNo);
	}
	

	
	
}
