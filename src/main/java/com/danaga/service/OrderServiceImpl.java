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
import com.danaga.dao.OptionSetDao;
import com.danaga.dao.OrderDao;
import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.OrdersDto;
import com.danaga.entity.Cart;
import com.danaga.entity.Delivery;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Orders;
import com.danaga.repository.CartRepository;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OrderItemRepository;
import com.danaga.repository.OrderRepository;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final MemberService memberService;
	private final CartRepository cartRepository;
	private final OptionSetDao optionSetDao;
	private final DeliveryDao deliveryDao;
	private final OrderDao orderDao;
	private final OrderItemRepository orderItemRepository;
//	private final OrderRepository orderRepository;
	
	
	/*
	 * 상품에서 직접주문
	 */
	@Transactional
	public Orders memberProductOrderSave(OrdersDto ordersDto,String oName,String oPhoneNumber)throws Exception {
		
//		if(memberRepository.findByPhoneNo(oPhoneNumber).isEmpty()) {
//			MemberInsertGuestDto member = MemberInsertGuestDto.builder()
//								.name(oName)
//								.phoneNo(oPhoneNumber)
//								.role("Guest")
//								.build();
//			System.out.println(member+"((((((((((((((((((((((((((((((((((((((((((((((");
//			MemberInsertGuestDto member2= memberService.joinGuest(member);
//			System.out.println(member2+")))))))))))))))))))))))))))))))))))))))))))))))))");
//			Member guestMember= memberService.getMemberBy(member2.getPhoneNo());
//			ordersDto.setUserName(guestMember.getUserName());
//			
//		
//		}
		
		OptionSet optionSet= optionSetDao.findById(ordersDto.getOptionSetId());
		OrderItem orderItem = OrderItem.builder()
									   .qty(ordersDto.getOrderItem_qty())
									   .optionSet(optionSet)
									   .build();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem);
		Member member = memberService.getMemberBy(ordersDto.getUserName());
		
		Orders orders = Orders.builder()
						.statement(OrderStateMsg.입금대기중)
						.orderItems(orderItemList)
						.price(orderItemList.get(0).getQty()*orderItemList.get(0).getOptionSet().getTotalPrice())
						.description(orderItemList.get(0).getOptionSet().getProduct().getName())
						.member(member)
						.build();		
		
		Delivery delivery = Delivery.builder()
									.name(ordersDto.getDelivaryName())
									.phoneNumber(ordersDto.getDelivaryPhoneNumber())
									.address(ordersDto.getDelivaryAddress())
									.orders(orders)
									.build();
		
		Delivery saveDelivery= deliveryDao.insertDelivery(delivery);
		
		orders.setDelivery(saveDelivery);
		orderItem.setOrders(orders);
		orderItemRepository.save(orderItem);
		Orders saveOrders = orderDao.save(orders);

		
		return saveOrders;
	}
	/*
	 * cart에서 주문(회원)
	 */
	@Transactional
	public Orders memberCartOrderSave(OrdersDto ordersDto)throws Exception {
		Member member= memberService.getMemberBy(ordersDto.getUserName());
		List<Cart> carList = cartRepository.findByMemberId(member.getId());
		ArrayList<OrderItem> orderItemList = new ArrayList<OrderItem>();
		int o_tot_price =0;
		int oi_tot_count = 0;
		for (Cart cart : carList) {
			OrderItem orderItem =  OrderItem.builder()
											.qty(cart.getQty())
											.optionSet(cart.getOptionSet())
											.build();
			orderItemList.add(orderItem);
			o_tot_price += orderItem.getQty() * orderItem.getOptionSet().getTotalPrice();
			oi_tot_count += orderItem.getQty();
			
		}
		String o_desc = orderItemList.get(0).getOptionSet().getProduct().getName()+"외" +(oi_tot_count-1) + "개";
		if(oi_tot_count==1) {
			o_desc = orderItemList.get(0).getOptionSet().getProduct().getName();
		}
		Orders orders = Orders.builder()
										.statement(OrderStateMsg.입금대기중)
										.orderItems(orderItemList)
										.price(o_tot_price)
										.description(o_desc)
										.member(member)
										.build();
		Delivery delivery = Delivery.builder()
				.name(ordersDto.getDelivaryName())
				.phoneNumber(ordersDto.getDelivaryPhoneNumber())
				.address(ordersDto.getDelivaryAddress())
				.orders(orders)
				.build();
		
		Delivery saveDelivery= deliveryDao.insertDelivery(delivery);
		orders.setDelivery(saveDelivery);
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrders(orders);
			orderItemRepository.save(orderItem);
		}
		Orders saveOrder= orderDao.save(orders);

		return saveOrder;
	}
	/*
	 * cart에서 선택주문(회원)
	 */
	@Transactional
	public Orders memberCartSelectOrderSave(OrdersDto ordersDto, String[] cart_item_noStr_array)throws Exception {
		Member member= memberService.getMemberBy(ordersDto.getUserName());
		ArrayList<OrderItem> orderItemList = new ArrayList<>();
		int o_tot_price =0;
		int oi_tot_count = 0;
		for (int i = 0; i < cart_item_noStr_array.length; i++) {
			Cart cart = cartRepository.findById(Long.parseLong(cart_item_noStr_array[i])).get();
			OrderItem orderItem = OrderItem.builder().qty(cart.getQty()).optionSet(cart.getOptionSet()).build();
			orderItemList.add(orderItem);
			o_tot_price += orderItem.getQty() * orderItem.getOptionSet().getTotalPrice();
			oi_tot_count += orderItem.getQty();
		}
		String o_desc = orderItemList.get(0).getOptionSet().getProduct().getName()+ "외" +(oi_tot_count-1) +"개";
		if(oi_tot_count==1) {
			o_desc = orderItemList.get(0).getOptionSet().getProduct().getName();
		}
		Orders orders = Orders.builder()
				.statement(OrderStateMsg.입금대기중)
				.orderItems(orderItemList)
				.price(o_tot_price)
				.description(o_desc)
				.member(member)
				.build();
		Delivery delivery = Delivery.builder()
		.name(ordersDto.getDelivaryName())
		.phoneNumber(ordersDto.getDelivaryPhoneNumber())
		.address(ordersDto.getDelivaryAddress())
		.orders(orders)
		.build();
		Delivery saveDelivery= deliveryDao.insertDelivery(delivery);
		orders.setDelivery(saveDelivery);
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrders(orders);
			orderItemRepository.save(orderItem);
		}
		Orders saveOrder= orderDao.save(orders);

		return saveOrder;
	}
	/*
	 * 주문+주문아이템 목록(회원)
	 */
	@Transactional
	public List<Orders> memberOrderList(String userName){
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