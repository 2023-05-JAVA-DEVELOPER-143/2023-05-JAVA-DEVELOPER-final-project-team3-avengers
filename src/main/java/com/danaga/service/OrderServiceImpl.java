package com.danaga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.config.OrderStateMsg;
import com.danaga.dao.CartDao;
import com.danaga.dao.DeliveryDao;
import com.danaga.dao.MemberDao;
import com.danaga.dao.OptionSetDao;
import com.danaga.dao.OrderDao;
import com.danaga.entity.Cart;
import com.danaga.entity.Delivery;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Orders;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.OrderRepository;

public class OrderServiceImpl {
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	MemberService memberService;
	@Autowired
	OptionSetDao optionSetDao;
	@Autowired
	CartDao cartDao;
	@Autowired
	DeliveryDao deliveryDao;
	@Autowired
	OrderItem orderItem;
	/*
	 * 상품에서 직접주문
	 */
	public Orders memberProductOrderSave(Long memberId, Long optionSetId, Integer orderItem_qty, String name, String phoneNumber, String address) {
		
		OptionSet optionSet= optionSetDao.findById(optionSetId);
		OrderItem orderItem = OrderItem.builder()
									   .qty(orderItem_qty)
									   .optionSet(optionSet)
									   .build();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem);
		Orders orders = Orders.builder()
						.statement(OrderStateMsg.입금대기중)
						.orderItems(orderItemList)
						.price(orderItemList.get(0).getQty()*orderItemList.get(0).getOptionSet().getTotalPrice())
						.description(orderItemList.get(0).getOptionSet().getProduct().getName())
						.build();		
		
		Orders saveOrders = orderRepository.save(orders);
		
		Delivery delivery = Delivery.builder()
									.name(name)
									.phoneNumber(phoneNumber)
									.address(address)
									.build();
		Delivery saveDelivery= deliveryDao.insertDelivery(delivery);
		saveDelivery.setOrders(saveOrders);
		
		orders.setDelivery(saveDelivery);
		
		
		
		return saveOrders;
	}
	/*
	 * cart에서 주문
	 */
//	public Orders memberSave(Member member) {
//		List<Cart> carList = cartDao.findCartListByMember_memberId(member.getId());
//		ArrayList<OrderItem> orderItemList = new ArrayList<OrderItem>();
//		int o_tot_price =0;
//		int oi_tot_count = 0;
//		for (Cart cart : carList) {
//			OrderItem orderItem = new OrderItem(0, cart.getQty(), orderDao.findOrdersById(member.getId()), cart.getOptionSet());
//			orderItemList.add(orderItem);
//			o_tot_price += orderItem.getQty() * orderItem.getOptionSet().getTotalPrice();
//			oi_tot_count += orderItem.getQty();
//		}
//		String o_desc = orderItemList.get(0).getOptionSet().getProduct().getName()+"외" +(oi_tot_count-1) + "개";
//		if(oi_tot_count==1) {
//			o_desc = orderItemList.get(0).getOptionSet().getProduct().getName();
//		}
//		Orders newOrder = new Orders(0, o_desc, null, null, null, null, null, orderItemList, member)
//		orderRepository.save(newOrder);
//		return null;
//	}
	/*
	 * cart에서 선택주문
	 */
	public Orders memberSave(Orders orders, String[] cart_item_noStr_array) {
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