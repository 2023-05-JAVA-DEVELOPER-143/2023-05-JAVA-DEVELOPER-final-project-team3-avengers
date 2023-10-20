package com.danaga.service;

import com.danaga.dao.OrderDao;
import com.danaga.dto.OrdersDto;
import com.danaga.entity.Orders;

public interface OrderService {
	
	public Orders memberProductOrderSave(OrdersDto ordersDto)throws Exception;
	
	
}
