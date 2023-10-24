package com.danaga.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

	private final OrderItemRepository orderItemRepository;

	private final MemberService memberService;
	private final CartRepository cartRepository;
	private final OptionSetDao optionSetDao;
	private final DeliveryDao deliveryDao;
	private final OrderDao orderDao;
	private final OrdersOptionSetDto ordersOptionSetDto;
	private final CartService cartService;
	

	private final OrderRepository orderRepository;

	private final MemberRepository memberRepository;

	/*
	 * 상품에서 직접주문
	 */
	@Transactional
	public OrdersResponseDto memberProductOrderSave(OrdersDto ordersDto,Long optionSetId, String oName, String oPhoneNumber,String sUserId) throws Exception {



		OptionSet optionSet = optionSetDao.findById(optionSetId);// 상품 찾고
		System.out.println("@@@@@@@@@@@@@@@optionSet = "+optionSet);
		OrderItem orderItem = OrderItem.builder()
										.qty(ordersOptionSetDto.getOrderItem_qty())
										.optionSet(optionSet)
										.build();//orderItem찾음
		System.out.println("@@@@@@@@@@@@@@@미완성 orderItem: "+orderItem);
		
		
		
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		
		
		orderItemList.add(orderItem);//상품에서 직접 주문하는거니까 orderItem하나만 들어간다
		System.out.println("@@@@@@@@@@@@@@@orderItemList = "+orderItemList);
		
		MemberResponseDto memberResponseDto = memberService.getMemberBy(ordersOptionSetDto.getUserName());
		System.out.println("@@@@@@@@@@@@@@@memberResponseDto = "+memberResponseDto);
		
		Member memberEntity = Member.toResponseEntity(memberResponseDto);
		System.out.println("@@@@@@@@@@@@@@@memberEntity= "+memberEntity);

		Delivery delivery = Delivery.builder()
				.name(ordersOptionSetDto.getDelivaryName())
				.phoneNumber(ordersOptionSetDto.getDelivaryPhoneNumber())
				.address(ordersOptionSetDto.getDelivaryAddress())
				.build();
		System.out.println("@@@@@@@@@@@@@@@delivery= "+delivery);
		
		
		Refund refund = new Refund();
		Orders orders = new Orders();
		orders.setRefund(refund);
		orders = Orders.builder()
							  .statement(OrderStateMsg.입금대기중)
							  .orderItems(orderItemList)
							  .price(orderItemList.get(0).getQty() * orderItemList.get(0).getOptionSet().getTotalPrice())
							  .description(orderItemList.get(0).getOptionSet().getProduct().getName())
							  .delivery(delivery)
//							  .refund(refund)
							  .member(memberEntity).build();
		System.out.println("@@@@@@@@@@@@@@@미완성 orders= "+orders);

		


		delivery.setOrders(orders);
		
		orders.setDelivery(delivery);

		System.out.println("@@@@@@@@@@@@@@@완성 orders= "+orders);
//		Delivery saveDelivery = deliveryDao.insertDelivery(delivery,orderId );
		orderItem.setOrders(orders);
		
		orderItemRepository.save(orderItem);
		System.out.println("@@@@@@@@@@@@@@@완성 orderItem: "+orderItem);

		Orders saveOrders = orderDao.save(orders,sUserId);
		
		OrdersResponseDto ordersResponseDto = OrdersResponseDto.OrdersResponseDto(saveOrders);
		System.out.println("@@@@@@@@@@@@@@@ordersResponseDto = "+ordersResponseDto);
		
		
		return ordersResponseDto;

	}

	/*
	 * cart에서 선택주문(회원)
	 */
//
//	@Transactional
//	public Orders memberCartSelectOrderSave(OrdersDto ordersDto, String[] cart_item_noStr_array) throws Exception {
//		
		   /*
		    * cart에서 주문(회원)
		    */
		   @Transactional
		   public Orders memberCartOrderSave(OrdersDto ordersDto, String sUserId,Delivery delivery) throws Exception {
		      
		      OrdersDto ordersInsertDto= OrdersDto.builder()
		             .description(ordersDto.getDescription())
		             .price(ordersDto.getPrice())
		             .stateMsg(OrderStateMsg.입금대기중)
		             .userName(ordersDto.getUserName())
		             .build();

		      Orders orders= orderDao.save(Orders.toResponseEntity(ordersInsertDto), sUserId);
		      
		      
		      List<Cart> cartList= cartService.findCartList(sUserId);
		      
		      int o_tot_price = 0;
		      int oi_tot_count = 0;
		      List<OrderItem> orderItemList = new ArrayList<>();
		      for (Cart cart : cartList) {
		         
		         o_tot_price+=(cart.getOptionSet().getTotalPrice())*(cart.getQty());
		         oi_tot_count+=cart.getQty();
		      
		         
		         OrderItem inputOI = OrderItem.builder()
		               .qty(cart.getQty())
		               .orders(orders)
		               .optionSet(cart.getOptionSet())
		               .build();
		         orderItemList.add(inputOI);
		         orderItemRepository.save(inputOI);
		      }
		      
		      orders = orderDao.save(Orders.builder()
		                            .id(orders.getId())
		                            .description(orderItemList.get(0).getOptionSet().getProduct().getName()+ "외" + (oi_tot_count - 1) + "개")
		                            .price(o_tot_price)
		                            .statement(OrderStateMsg.입금대기중)
		                            .member(Member.toResponseEntity(memberService.getMemberBy(sUserId)))
		                            .build(),sUserId);
		      
		      deliveryDao.insertDelivery(delivery,orders.getId());
		      
		      MemberResponseDto memberResponseDto =memberService.getMemberBy(sUserId);
		      
		      memberResponseDto.setGradePoint((int)((orders.getPrice())*0.1));
		      memberRepository.save(Member.builder()
		                           .id(memberResponseDto.getId())
		                           .userName(memberResponseDto.getUserName())
		                           .password(memberResponseDto.getPassword())
		                           .email(memberResponseDto.getEmail())
		                           .nickname(memberResponseDto.getNickname())
		                           .address(memberResponseDto.getAddress())
		                           .role(memberResponseDto.getRole())
		                           .build());
		      
		      return orders;
		   }

	/*
	 * cart에서 선택주문(회원)
	 */

	@Transactional
	public Orders memberCartSelectOrderSave(OrdersDto ordersDto, String[] cart_item_noStr_array) throws Exception {
//		Member member = memberService.getMemberBy(ordersDto.getUserName());
//		ArrayList<OrderItem> orderItemList = new ArrayList<>();
//		int o_tot_price = 0;
//		int oi_tot_count = 0;
//		for (int i = 0; i < cart_item_noStr_array.length; i++) {
//			Cart cart = cartRepository.findById(Long.parseLong(cart_item_noStr_array[i])).get();
//			OrderItem orderItem = OrderItem.builder().qty(cart.getQty()).optionSet(cart.getOptionSet()).build();
//			orderItemList.add(orderItem);
//			o_tot_price += orderItem.getQty() * orderItem.getOptionSet().getTotalPrice();
//			oi_tot_count += orderItem.getQty();
//		}
//		String o_desc = orderItemList.get(0).getOptionSet().getProduct().getName() + "외" + (oi_tot_count - 1) + "개";
//		if (oi_tot_count == 1) {
//			o_desc = orderItemList.get(0).getOptionSet().getProduct().getName();
//		}
//		Orders orders = Orders.builder().statement(OrderStateMsg.입금대기중).orderItems(orderItemList).price(o_tot_price)
//				.description(o_desc).member(member).build();
//		Delivery delivery = Delivery.builder().name(ordersDto.getUserName())
//				.phoneNumber(ordersDto.).address(ordersDto.getDelivaryAddress()).orders(orders)
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

//	public Orders memberCartSelectOrderSave(Orders orders, String[] cart_item_noStr_array) {
//
//
//		return null;
//		Member member = memberService.getMemberBy(ordersDto.getUserName());
//		ArrayList<OrderItem> orderItemList = new ArrayList<>();
//		int o_tot_price = 0;
//		int oi_tot_count = 0;
//		for (int i = 0; i < cart_item_noStr_array.length; i++) {
//			Cart cart = cartRepository.findById(Long.parseLong(cart_item_noStr_array[i])).get();
//			OrderItem orderItem = OrderItem.builder().qty(cart.getQty()).optionSet(cart.getOptionSet()).build();
//			orderItemList.add(orderItem);
//			o_tot_price += orderItem.getQty() * orderItem.getOptionSet().getTotalPrice();
//			oi_tot_count += orderItem.getQty();
//		}
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
//
//		return saveOrder;
//	}
//
//	}

	/*
	 * 주문+주문아이템 목록(회원)
	 */

	@Transactional


	public List<Orders> memberOrderList(String userName) {

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
	public Orders updateStatementByNormalOrder(Long orderNo,String sUserId) {
		Orders updateOrder= orderDao.updateStatementByNormalOrder(orderNo);
		orderDao.save(updateOrder,sUserId);
		return updateOrder;
	}
	/*
	 * 2.취소주문
	 */
	@Override
	public Orders updateStatementByCancleOrder(Long orderNo,String sUserId) {
		Orders updateOrder= orderDao.updateStatementByCancleOrder(orderNo);
		orderDao.save(updateOrder,sUserId);
		return updateOrder;
	}
	/*
	 * 3.환불주문
	 */
	@Override
	public Orders updateStatementByRefundOrder(Long orderNo,String sUserId) {
		Orders updateOrder= orderDao.updateStatementByRefundOrder(orderNo);
		orderDao.save(updateOrder,sUserId);
		return updateOrder;
	}
	/*
	 * 4.상태리셋
	 */
	@Override
	public Orders updateStatementByResetOrder(Long orderNo,String sUserId) {
		Orders updateOrder= orderDao.updateStatementByResetOrder(orderNo);
		orderDao.save(updateOrder,sUserId);
		return updateOrder;
	}
	
	
	
	
	
	
//	/*
//	 * 0: 초 (0초), 0: 분 (0분),0: 시간 (0시간),*: 일자 (모든 일자),*: 월 (모든 월),?: 요일 (매일)
//	 */
//	@Scheduled(cron = "0 20 11 * * ?")
//	public void deleteOldOrders() {
//		LocalDateTime threeHoursAgo = LocalDateTime.now().minusMinutes(3);
//		orderRepository.deleteByCreatedAtBefore(threeHoursAgo);
//	}
	
	
	
		/*
		 * 세션객체를 사용한 선택주문,상품에서 직접주문
		 */
//		public int create(Order order, String[] cart_item_noStr_array) throws Exception {
//			return null;
//		}

		/*
		 * 세션객체를 사용한 장바구니전체주문
		 */
//		public int create(Order order) throws Exception {
//			return null;
//		}
//	

	
}	


	

