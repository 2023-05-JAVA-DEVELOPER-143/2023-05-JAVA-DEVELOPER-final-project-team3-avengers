package com.danaga.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.danaga.config.OrderStateMsg;
import com.danaga.dao.CartDao;
import com.danaga.dao.DeliveryDao;
import com.danaga.dao.MemberDao;
import com.danaga.dao.OrderDao;

import com.danaga.dto.CartCreateDto;
import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.MemberResponseDto;
import com.danaga.dto.OrderItemDto;

import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OrderItemRepository;
import com.danaga.repository.OrderRepository;
import com.danaga.service.product.OptionSetService;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

	private final OrderItemRepository orderItemRepository;
	private final MemberService memberService;
	private final MemberDao memberDao;
	private final OptionSetDao optionSetDao;
	private final DeliveryService deliveryService;
	private final OrderDao orderDao;
	private final CartService cartService;



	/*
	 * 상품에서 직접주문
	 */
	@Transactional
	public OrdersDto memberProductOrderSave(String sUserId,OrdersProductDto ordersProductDto) throws Exception {
		

		OptionSet optionSet = optionSetDao.findById(ordersProductDto.getOptionSetId());// 상품 찾고
		List<OrderItem> orderItemList = new ArrayList<>();
		
		OrderItem orderItem = OrderItem.builder()
				.qty(ordersProductDto.getOrderItem_qty())
				.optionSet(optionSet)
				.build();//orderItem찾음
		
		orderItemList.add(orderItem);//상품에서 직접 주문하는거니까 orderItem하나만 들어간다
		
		Delivery delivery = Delivery.builder()
				.name(ordersProductDto.getDelivaryName())
				.phoneNumber(ordersProductDto.getDelivaryPhoneNumber())
				.address(ordersProductDto.getDelivaryAddress())
				.build();
		
		System.out.println("@@@@@@@@@@@@@@@delivery= "+delivery);
	      
	    Orders orders= orderDao.save(Orders.builder()
	                            .description(optionSet.getProduct().getName())
	                            .price(optionSet.getProduct().getPrice()*ordersProductDto.getOrderItem_qty())
	                            .statement(OrderStateMsg.입금대기중)
	                            .member(Member.toResponseEntity(memberService.getMemberBy(sUserId)))
	                            .delivery(delivery)
	                            .orderItems(orderItemList)
	                            .build());
		
	    delivery.setOrders(orders);
	    orderItem.setOrders(orders); //transactional

//	    MemberResponseDto memberResponseDto =memberService.getMemberBy(sUserId);
//	      
//	      memberResponseDto.setGradePoint((int)((orders.getPrice())*0.1));
//	      memberService.updateGrade(Member.builder()
//	                           .id(memberResponseDto.getId())
//	                           .userName(memberResponseDto.getUserName())
//	                           .password(memberResponseDto.getPassword())
//	                           .email(memberResponseDto.getEmail())
//	                           .nickname(memberResponseDto.getNickname())
//	                           .address(memberResponseDto.getAddress())
//	                           .phoneNo(memberResponseDto.getPhoneNo())
//	                           .joinDate(memberResponseDto.getJoinDate())
//	                           .birthday(memberResponseDto.getBirthday())
//	                           .role(memberResponseDto.getRole())
//	                           .grade(memberResponseDto.getGrade())
//	                           .build(),memberResponseDto.getGradePoint());
	      
	      
	    return OrdersDto.orderDto(orders);
	    
	    
	    
//	    Member member =memberDao.findMember(sUserId);
//	    
//	    OrdersDto ordersInsertDto= OrdersDto.builder()
//	             .userName(member.getUserName())
//	             .build();
//		
//	    Orders orders= orderDao.save(Orders.toResponseEntity(ordersInsertDto));
//	    
//		OptionSet optionSet = optionSetDao.findById(ordersProductDto.getOptionSetId());// 상품 찾고
//		System.out.println("@@@@@@@@@@@@@@@optionSet = "+optionSet);
//		List<OrderItemDto> orderItemList = new ArrayList<>();
//		
//		OrderItemDto orderItemDto = OrderItemDto.builder()
//										.qty(ordersProductDto.getOrderItem_qty())
//										.optionSetId(optionSet.getId())
//										.orderId(orders.getId())
//										.build();//orderItem찾음
//		
//		orderItemList.add(orderItemDto);//상품에서 직접 주문하는거니까 orderItem하나만 들어간다
//		
//		OrderItem orderItemEntity = OrderItem.builder()
//												.qty(ordersProductDto.getOrderItem_qty())
//												.optionSet(optionSet)
//												.orders(orders)
//												.build();//orderItem찾음
//		
//		orderItemRepository.save(orderItemEntity);
//		
//		
//		DeliveryDto delivery = DeliveryDto.builder()
//				.name(ordersProductDto.getDelivaryName())
//				.phoneNumber(ordersProductDto.getDelivaryPhoneNumber())
//				.address(ordersProductDto.getDelivaryAddress())
//				.build();
//		
//		System.out.println("@@@@@@@@@@@@@@@delivery= "+delivery);
//	      
//	    orderDao.save(Orders.builder()
//	    		 				.id(orders.getId())
//	                            .description(optionSet.getProduct().getName())
//	                            .price(optionSet.getProduct().getPrice()*ordersProductDto.getOrderItem_qty())
//	                            .statement(OrderStateMsg.입금대기중)
//	                            .member(Member.toResponseEntity(memberService.getMemberBy(sUserId)))
//	                            .build());
//		
//		deliveryService.saveDeliveryByOrdersId(delivery, orders.getId());
//		
//		MemberResponseDto memberResponseDto =memberService.getMemberBy(sUserId);
//	      
//	      memberResponseDto.setGradePoint((int)((orders.getPrice())*0.1));
//	      memberService.updateGrade(Member.builder()
//	                           .id(memberResponseDto.getId())
//	                           .userName(memberResponseDto.getUserName())
//	                           .password(memberResponseDto.getPassword())
//	                           .email(memberResponseDto.getEmail())
//	                           .nickname(memberResponseDto.getNickname())
//	                           .address(memberResponseDto.getAddress())
//	                           .phoneNo(memberResponseDto.getPhoneNo())
//	                           .joinDate(memberResponseDto.getJoinDate())
//	                           .birthday(memberResponseDto.getBirthday())
//	                           .role(memberResponseDto.getRole())
//	                           .grade(memberResponseDto.getGrade())
//	                           .build(),memberResponseDto.getGradePoint());
//		
//		
//		return OrdersDto.orderDto(orders);

	}

		/*
	    * cart에서 주문(회원)
	    */
	   @Transactional
	   public OrdersDto memberCartOrderSave(String sUserId,DeliveryDto deliveryDto) throws Exception {
	      
	      List<Cart> cartList= cartService.findCartList(sUserId);
	      
	      int o_tot_price = 0;
	      int oi_tot_count = 0;
	      List<OrderItem> orderItemList = new ArrayList<>();
	      for (Cart cart : cartList) {
	         
	         o_tot_price+=(cart.getOptionSet().getTotalPrice())*(cart.getQty());
	         oi_tot_count+=cart.getQty();
	      
	         
	         OrderItem inputOIEntity = OrderItem.builder()
		               .qty(cart.getQty())
		               .optionSet(cart.getOptionSet())
		               .build();
	         
	         orderItemRepository.save(inputOIEntity);
	         
	      }
	      
	       OptionSet optionSet= optionSetDao.findById(orderItemList.get(0).getOptionSet().getId());
	      
	       String o_desc = optionSet.getProduct().getName() + "외" + (oi_tot_count - 1) + "개";
	       if (oi_tot_count == 1) {
	    	   o_desc = optionSet.getProduct().getName();
	       }
	   	Delivery delivery = Delivery.builder()
									.name(deliveryDto.getName())
									.phoneNumber(deliveryDto.getPhoneNumber())
									.address(deliveryDto.getAddress())
									.build();
						      
	       
	      Orders orders = orderDao.save(Orders.builder()
	                            .description(o_desc)
	                            .price(o_tot_price)
	                            .statement(OrderStateMsg.입금대기중)
	                            .member(Member.toResponseEntity(memberService.getMemberBy(sUserId)))
	                            .delivery(delivery)
	                            .orderItems(orderItemList)
	                            .build());
	      
	      for (OrderItem orderItem : orderItemList) {
			orderItem.setOrders(orders);
		}
	      delivery.setOrders(orders);
	      
	      
//	      MemberResponseDto memberResponseDto =memberService.getMemberBy(sUserId);
//	      
//	      memberResponseDto.setGradePoint((int)((orders.getPrice())*0.1));
//	      memberService.updateGrade(Member.builder()
//	                           .id(memberResponseDto.getId())
//	                           .userName(memberResponseDto.getUserName())
//	                           .password(memberResponseDto.getPassword())
//	                           .email(memberResponseDto.getEmail())
//	                           .nickname(memberResponseDto.getNickname())
//	                           .address(memberResponseDto.getAddress())
//	                           .phoneNo(memberResponseDto.getPhoneNo())
//	                           .joinDate(memberResponseDto.getJoinDate())
//	                           .birthday(memberResponseDto.getBirthday())
//	                           .role(memberResponseDto.getRole())
//	                           .grade(memberResponseDto.getGrade())
//	                           .build(),memberResponseDto.getGradePoint());
	      
	      return OrdersDto.orderDto(orders);
	   }

	/*
	 * cart에서 선택주문(회원)
	 */

	public OrdersDto memberCartSelectOrderSave(String sUserId,DeliveryDto deliveryDto,List<Long> cartItemNoArray) {

//
//		Member member =memberDao.findMember(sUserId);
//	    OrdersDto ordersInsertDto= OrdersDto.builder()
//	             .userName(member.getUserName())
//	             .build();
//	    Orders orders= orderDao.save(Orders.toResponseEntity(ordersInsertDto)); 
//	    
//		ArrayList<OrderItemDto> orderItemList = new ArrayList<>();
//		
//		int o_tot_price = 0;
//		int oi_tot_count = 0;
//		
//		for (int i = 0; i < cartItemNoArray.size(); i++) {
//			Cart cart = cartRepository.findById(cartItemNoArray.get(i)).get();
//			
//		    o_tot_price+=(cart.getOptionSet().getTotalPrice())*(cart.getQty());
//		    oi_tot_count+=cart.getQty();
//			
//		    OrderItemDto inputOIDto = OrderItemDto.builder()
//								                  .qty(cart.getQty())
//								                  .orderId(orders.getId())
//								                  .optionSetId(cart.getOptionSet().getId())
//								                  .build();
//		    orderItemList.add(inputOIDto);
//            OrderItem inputOIEntity = OrderItem.builder()
//							                   .qty(cart.getQty())
//							                   .orders(orders)
//							                   .optionSet(cart.getOptionSet())
//							                   .build();
//	        orderItemRepository.save(inputOIEntity);
//
//		}
//		 
//	       OptionSet optionSet= optionSetDao.findById(orderItemList.get(0).getOptionSetId());
//	      
//	       String o_desc = optionSet.getProduct().getName() + "외" + (oi_tot_count - 1) + "개";
//	       if (oi_tot_count == 1) {
//	    	   o_desc = optionSet.getProduct().getName();
//	       }
//	       orders = orderDao.save(Orders.builder()
//                   .id(orders.getId())
//                   .description(o_desc)
//                   .price(o_tot_price)
//                   .statement(OrderStateMsg.입금대기중)
//                   .member(Member.toResponseEntity(memberService.getMemberBy(sUserId)))
//                   .build());
//
//		
//		
//		
//		String o_desc = orderItemList.get(0).getOptionSet().getProduct().getName() + "외" + (oi_tot_count - 1) + "개";
//		if (oi_tot_count == 1) {
//			o_desc = orderItemList.get(0).getOptionSet().getProduct().getName();
//		}
//		Orders orders = Orders.builder().statement(OrderStateMsg.입금대기중).orderItems(orderItemList).price(o_tot_price)
//				.description(o_desc).member(member).build();
//		Delivery delivery = Delivery.builder().name(ordersDto.getDelivaryName())
//				.phoneNumber(ordersDto.getDelivaryPhoneNumber()).address(ordersDto.getDelivaryAddress()).orders(orders)
//				.build();
//		Delivery saveDelivery = deliveryDao.insertDelivery(delivery);
//		orders.setDelivery(saveDelivery);
//		for (OrderItem orderItem : orderItemList) {
//			orderItem.setOrders(orders);
//			orderItemRepository.save(orderItem);
//		}
//		Orders saveOrder = orderDao.save(orders);

		return null;
	}

	/*
	 * 주문+주문아이템 목록(회원)
	 */

	@Transactional


	public List<Orders> memberOrderList(String userName)throws Exception {
		
		if(userName==null) {
			throw new Exception("일치하는 사용자가없습니다.");
		}
		
		return orderDao.findOrdersByMember_UserName(userName);
	}

	/*
	 * 주문상세보기(회원)
	 */
	public Orders memberOrderDetail(Long orderNo)throws Exception {
		return orderDao.findById(orderNo);
	}
	

	/*
	 * 1.정상주문
	 */
	@Override
	public Orders updateStatementByNormalOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByNormalOrder(orderNo);
		orderDao.save(updateOrder);
		return updateOrder;
	}
	/*
	 * 2.취소주문
	 */
	@Override
	public Orders updateStatementByCancleOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByCancleOrder(orderNo);
		orderDao.save(updateOrder);
		return updateOrder;
	}
	/*
	 * 3.환불주문
	 */
	@Override
	public Orders updateStatementByRefundOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByRefundOrder(orderNo);
		orderDao.save(updateOrder);
		return updateOrder;
	}
	/*
	 * 4.상태리셋
	 */
	@Override
	public Orders updateStatementByResetOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByResetOrder(orderNo);
		orderDao.save(updateOrder);
		return updateOrder;
	}
	
	
}	


	

