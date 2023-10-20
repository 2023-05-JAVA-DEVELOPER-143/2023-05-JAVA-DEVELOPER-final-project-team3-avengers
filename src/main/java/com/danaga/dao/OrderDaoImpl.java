package com.danaga.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
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

	//주문생성
	
//	@Override
//	public Orders insert(Orders order) {
//
//		// 1.Order를 저장 2.OrderItemList를 저장
//		// 4.Delivery저장 --service에서 처리 5.비회원 Member저장--service에서 처리
//
//		Orders insertOrder = orderRepository.save(order);
//
//		List<OrderItem> itemList = insertOrder.getOrderItems();
//
//		for (OrderItem orderItem : itemList) {
//			orderItem.setOrders(order);
//			orderItemRepository.save(orderItem);
//		}
//
//		return insertOrder; // 저장된 주문 객체 반환
//
//	}
	
	//주문상태업데이트
	
	@Override
	public Orders updateOrdersByStatement(Orders orders) {

		Orders findOrder = orderRepository.findById(orders.getId()).get();

		if (findOrder.getStatement().ordinal() == 3) {
			return findOrder;
		} else {

			findOrder.setStatement(OrderStateMsg.get(findOrder.getStatement().ordinal() + 1));
			return findOrder;
		}

	}

	//Id로 주문전체(특정사용자)
	
	@Override
	public List<Orders> findOrdersByMember_UserName(String userName) {

		List<Orders> orders = orderRepository.findOrdersByMember_UserName(userName);

		return orders;

	}
	
	//Id로 주문+주문아이템 전체(특정사용자)
	@Override
	public List<Orders> findOrderWithOrderByMember_UserName(String userName) {
		
		return orderRepository.findOrdersWithOrderItemByMember_UserName(userName);
	}

	//주문 번호로 1개보기(주문상세리스트)
	//주문번호는 id+10000이므로 받을때 id-10000으로 받아야함 --컨트롤러에서!!
	@Override
	public Orders findOrdersById(Long id) {
		// 주어진 주문 번호 (oNo)를 사용하여 해당 주문을 조회하려면 적절한 로직을 추가
		// orderRepository를 사용하여 데이터베이스에서 주문을 조회
		return orderRepository.findById(id).get();
	}

	
	//비회원(주문번호,회원이름,회원전화번호) 로 주문1개보기
//	@Override
//	public Orders findOrdersByOrderNoAndNameAndPhoneNo(Long orderNo, String userName, String phoneNo) {
//		// 주어진 주문 번호 (oNo), 회원 이름 (MemberName), 회원전화번호 (memberPhoneNo)를 사용하여 주문을 조회하려면
//		// 적절한 로직을 추가
//		// orderRepository를 사용하여 데이터베이스에서 주문을 조회할 수 있으며, 필요한 정보를 조건에 따라 조회
//		return orderRepository.findOrdersByOrderNoAndMember_NameAndMember_PhoneNo(orderNo, userName, phoneNo);
//
//	}


}
