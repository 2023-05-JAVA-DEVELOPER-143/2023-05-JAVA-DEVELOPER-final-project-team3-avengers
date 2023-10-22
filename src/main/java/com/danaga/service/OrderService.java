package com.danaga.service;

import java.util.List;

import com.danaga.dao.OrderDao;
import com.danaga.dto.OrdersDto;
import com.danaga.entity.Member;
import com.danaga.entity.Orders;

public interface OrderService {
	/*
	 * 상품에서 직접주문
	 */
	public Orders memberProductOrderSave(OrdersDto ordersDto,String oName,String oPhoneNumber)throws Exception;
	/*
	 * cart에서 주문
	 */
	public Orders memberCartOrderSave(OrdersDto ordersDto)throws Exception;
	/*
	 * cart에서 선택주문
	 */
	public Orders memberCartSelectOrderSave(OrdersDto ordersDto, String[] cart_item_noStr_array)throws Exception;
	/*
	 * 주문+주문아이템 목록
	 */
	public List<Orders> memberOrderList(String userName);
}
