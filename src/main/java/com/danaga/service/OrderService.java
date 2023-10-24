package com.danaga.service;

import java.util.List;

import com.danaga.dao.OrderDao;
import com.danaga.dto.OrdersDto;
import com.danaga.entity.Delivery;
import com.danaga.entity.Member;
import com.danaga.entity.Orders;

public interface OrderService {
	/*
	 * 상품에서 직접주문
	 */
	Orders memberProductOrderSave(OrdersDto ordersDto,String oName,String oPhoneNumber)throws Exception;
	/*
	 * cart에서 주문
	 */
	public Orders memberCartOrderSave(OrdersDto ordersDto, String sUserId,Delivery delivery) throws Exception;
	/*
	 * cart에서 선택주문
	 */
	Orders memberCartSelectOrderSave(OrdersDto ordersDto, String[] cart_item_noStr_array)throws Exception;
	/*
	 * 주문+주문아이템 목록
	 */
	List<Orders> memberOrderList(String userName);
	/*
	 * 주문상세보기
	 */
	Orders memberOrderDetail(Long orderNo)throws Exception;
	
	
   //  주문상태업데이트(특정주문)
	  // 1.정상주문
	  Orders updateStatementByNormalOrder(Long orderNo,String sUserId);
	  // 2.취소주문
	  Orders updateStatementByCancleOrder(Long orderNo,String sUserId);
	  // 3.환불주문
	  Orders updateStatementByRefundOrder(Long orderNo,String sUserId);
	  Orders updateStatementByResetOrder(Long orderNo,String sUserId);
}
