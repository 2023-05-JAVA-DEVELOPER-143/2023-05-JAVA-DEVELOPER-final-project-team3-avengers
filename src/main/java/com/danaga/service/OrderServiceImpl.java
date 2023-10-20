package com.danaga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.dao.MemberDao;
import com.danaga.dao.OptionSetDao;
import com.danaga.dao.OrderDao;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Orders;

public class OrderServiceImpl {
	@Autowired
	OrderDao orderDao;
	@Autowired
	MemberDao memberDao;
	@Autowired
	OptionSetDao optionSetDao;
	/*
	 * 상품에서 직접주문
	 */
//	public Orders memberProductOrderSave(String memberId, Long optionSetId, Integer qty, String name, String phoneNumber, String address) {
//		
//		OptionSet optionSet = optionSetDao.findById(optionSetId);
//		OrderItem orderItem = new OrderItem(0, qty,null , optionSet);
//		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
//		orderItemList.add(orderItem);
//		
//		Orders newOrder = new Orders(0, orderItemList.get(0), qty, null, null, null, null, orderItemList, null)
//		
//		return null;
//	}
	/*
	 * cart에서 주문
	 */
	public Orders memberSave(Orders orders) {

		return null;
	}
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