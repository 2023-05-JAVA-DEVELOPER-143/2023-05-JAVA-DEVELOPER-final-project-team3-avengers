package com.danaga.service;

import java.util.List;

import com.danaga.dao.OrderDao;
import com.danaga.dto.OrdersDto;
import com.danaga.entity.Member;
import com.danaga.entity.Orders;

public interface OrderService {
	
	public Orders memberProductOrderSave(OrdersDto ordersDto,String oName,String oPhoneNumber)throws Exception;
	
	public Orders memberCartOrderSave(OrdersDto ordersDto)throws Exception;
	
	public List<Orders> memberOrderList(String userName);
	
	public List<Orders> memberOrderListWithOrderItem(String userName);
}
