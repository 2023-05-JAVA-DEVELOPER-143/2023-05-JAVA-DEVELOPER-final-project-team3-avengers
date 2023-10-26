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
	
	//주문SAVE
	public Orders save(Orders orders) {
	
		return orderRepository.save(orders);
	}
	
	
	
	//주문상태업데이트
	// 1.정상주문
	@Override
	public Orders updateStatementByNormalOrder(Long orderNo) {

		
		Orders findOrder = orderRepository.findById(orderNo).get();
		
		if(findOrder.getStatement()==OrderStateMsg.입금대기중) {
			findOrder.setStatement(OrderStateMsg.배송중);
		}else if(findOrder.getStatement()==OrderStateMsg.배송중) {
			findOrder.setStatement(OrderStateMsg.배송완료);
		}
		return findOrder;
	    }
	
	// 2.취소주문
	public Orders updateStatementByCancleOrder(Long orderNo) {

		Orders findOrder = orderRepository.findById(orderNo).get();
		
		if(findOrder.getStatement()==OrderStateMsg.입금대기중) {
			findOrder.setStatement(OrderStateMsg.취소);
		}
		return findOrder;
	}
	// 3.환불주문(client)
	public Orders updateStatementByClientRefundOrder(Long orderNo) {

		Orders findOrder = orderRepository.findById(orderNo).get();
		if(findOrder.getStatement()==OrderStateMsg.배송완료) {
			findOrder.setStatement(OrderStateMsg.환불대기중);
		}
		
		return findOrder;
	}
	// 4.환불주문(admin)
	public Orders updateStatementByAdminRefundOrder(Long orderNo) {
		
		Orders findOrder = orderRepository.findById(orderNo).get();
		if(findOrder.getStatement()==OrderStateMsg.환불대기중) {
			findOrder.setStatement(OrderStateMsg.환불완료);
		}
		
		return findOrder;
	}
	// 5.상태리셋
	public Orders updateStatementByResetOrder(Long orderNo) {
		Orders findOrder = orderRepository.findById(orderNo).get();
		findOrder.setStatement(OrderStateMsg.입금대기중);
		return findOrder;
	}
	
	

	//Id로 주문전체(특정사용자)
	
	@Override
	public List<Orders> findOrdersByMember_UserName(String userName){
	
		List<Orders> orders = orderRepository.findOrdersByMember_UserName(userName);

		return orders;

	}
	

	//주문 번호로 1개보기(주문상세리스트)
	//주문번호는 id+10000이므로 받을때 id-10000으로 받아야함 --컨트롤러에서!!
	@Override
	public Orders findById(Long id)throws Exception {
		Orders ordersDetail= orderRepository.findById(id).get();
		if(ordersDetail!=null) {
			return ordersDetail;
		}else {
			throw new Exception("일치하는 주문번호가 없습니다.");
		}
	}


	//비회원(주문번호,회원이름,회원전화번호) 로 주문1개보기
	@Override
	public Orders findOrdersByIdAndNameAndPhoneNo(Long orderNo, String userName, String phoneNo){
		
		Orders guestFindOrder= orderRepository.findOrdersByIdAndMember_NameAndMember_PhoneNo(orderNo, userName, phoneNo);
		 
		 return guestFindOrder;
	}


}
