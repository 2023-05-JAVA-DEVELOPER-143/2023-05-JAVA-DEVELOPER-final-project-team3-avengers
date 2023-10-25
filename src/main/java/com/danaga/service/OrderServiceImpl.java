package com.danaga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.danaga.config.OrderStateMsg;
import com.danaga.dao.MemberDao;
import com.danaga.dao.OrderDao;

import com.danaga.dto.MemberResponseDto;

import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.CartRepository;
import com.danaga.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

	private final CartRepository cartRepository;
	private final OrderItemRepository orderItemRepository;
	private final MemberService memberService;
	private final OptionSetDao optionSetDao;
	private final OrderDao orderDao;
	private final CartService cartService;
	private final MemberDao memberDao;


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

	    MemberResponseDto memberResponseDto =memberService.getMemberBy(sUserId);
	      
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
//	                           .gradePoint(memberResponseDto.getGradePoint())
//	                           .build(),memberResponseDto.getGradePoint());
//	     
//	     orders.setMember(Member.toResponseEntity(memberResponseDto));
	      
	    return OrdersDto.orderDto(orders);

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
	         
	         orderItemList.add(inputOIEntity);
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

	public OrdersDto memberCartSelectOrderSave(String sUserId,DeliveryDto deliveryDto,List<Long> optionSetIdArray)throws Exception {

		ArrayList<OrderItem> orderItemList = new ArrayList<>();
		
		int o_tot_price = 0;
		int oi_tot_count = 0;
		
		MemberResponseDto memberResponseDto = memberService.getMemberBy(sUserId);
		
		for (int i = 0; i < optionSetIdArray.size(); i++) {
			Cart cart = cartRepository.findByOptionSetIdAndMemberId(optionSetIdArray.get(i),memberResponseDto.getId());
			
		    o_tot_price+=(cart.getOptionSet().getTotalPrice())*(cart.getQty());
		    oi_tot_count+=cart.getQty();
		
            OrderItem inputOIEntity = OrderItem.builder()
							                   .qty(cart.getQty())
							                   .optionSet(cart.getOptionSet())
							                   .build();

            orderItemList.add(inputOIEntity);
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
		

		return OrdersDto.orderDto(orders);
	}

	/*
	 * 주문+주문아이템 목록(회원)
	 */

	@Transactional
	public List<OrdersDto> memberOrderList(String userName)throws Exception {
		
		if(userName==null) {
			throw new Exception("일치하는 사용자가없습니다.");
		}
		 List<Orders> orderList= orderDao.findOrdersByMember_UserName(userName);
		 List<OrdersDto> ordersDtoList = new ArrayList<>();
		 for (Orders orders : orderList) {
			OrdersDto ordersDto= OrdersDto.orderDto(orders);
			ordersDtoList.add(ordersDto);
		}
		 
		return ordersDtoList;
	}

	/*
	 * 주문상세보기(회원)
	 */
	@Transactional
	public OrdersDto memberOrderDetail(Long orderNo)throws Exception {
		
		Orders orders=  orderDao.findById(orderNo);
		
		return OrdersDto.orderDto(orders);
	}
	

	/*
	 * 1.정상주문
	 */
	@Transactional
	@Override
	public OrdersDto updateStatementByNormalOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByNormalOrder(orderNo);
		orderDao.save(updateOrder);
		return OrdersDto.orderDto(updateOrder);
	}
	/*
	 * 2.취소주문
	 */
	@Transactional
	@Override
	public OrdersDto updateStatementByCancleOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByCancleOrder(orderNo);
		orderDao.save(updateOrder);
		return OrdersDto.orderDto(updateOrder);
	}
	/*
	 * 3.환불주문
	 */
	@Transactional
	@Override
	public OrdersDto updateStatementByRefundOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByRefundOrder(orderNo);
		orderDao.save(updateOrder);
		return OrdersDto.orderDto(updateOrder);
	}
	/*
	 * 4.상태리셋
	 */
	@Transactional
	@Override
	public OrdersDto updateStatementByResetOrder(Long orderNo) {
		Orders updateOrder= orderDao.updateStatementByResetOrder(orderNo);
		orderDao.save(updateOrder);
		return OrdersDto.orderDto(updateOrder);
	}
	
	
}	


	

