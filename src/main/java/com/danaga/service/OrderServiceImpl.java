package com.danaga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.config.OrderStateMsg;
import com.danaga.dao.CartDao;
import com.danaga.dao.DeliveryDao;
import com.danaga.dao.MemberDao;
import com.danaga.dao.OrderDao;
import com.danaga.dao.product.OptionSetDao;
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
	
	private final OrderItemRepository orderItemRepository;
	private final MemberService memberService;
	private final OptionSetDao optionSetDao;
	private final DeliveryDao deliveryDao;
	private final CartRepository cartRepository;
	private final OrderDao orderDao;
	
	
	/*
	 * 상품에서 직접주문
	 */
	@Transactional
	public Orders memberProductOrderSave(OrdersDto ordersDto)throws Exception {
		
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
									.name(ordersDto.getName())
									.phoneNumber(ordersDto.getPhoneNumber())
									.address(ordersDto.getAddress())
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
	 * cart에서 주문
	 */
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
				.name(ordersDto.getName())
				.phoneNumber(ordersDto.getPhoneNumber())
				.address(ordersDto.getAddress())
				.orders(orders)
				.build();
		
		Delivery saveDelivery= deliveryDao.insertDelivery(delivery);
		orders.setDelivery(saveDelivery);
		
		Orders saveOrder= orderDao.save(orders);
		
		cartRepository.deleteById(member.getId());
		
		return saveOrder;
	}
	/*
	 * cart에서 선택주문
	 */
	public Orders memberCartSelectOrderSave(Orders orders, String[] cart_item_noStr_array) {
		
		return null;
	}
	
	
	public Orders guestSave(Orders orders) {

		return null;
	}
	
	/*
	 * 주문목록
	 */
	public List<Orders> orderList(String userName){
		return null;
	}
	
	/*
	 * 주문+주문아이템 목록
	 */
	public List<Orders> orderListWithOrderItem(Long Id) {
		return null;
	}
}