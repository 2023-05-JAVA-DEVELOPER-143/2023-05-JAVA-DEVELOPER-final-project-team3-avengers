package com.danaga.service;

import java.util.List;

import com.danaga.dao.OrderDao;
import com.danaga.entity.Delivery;

import com.danaga.dto.*;

import com.danaga.entity.Member;
import com.danaga.entity.Orders;

public interface OrderService {
	/*
	 * 상품에서 직접주문
	 */
	OrdersDto memberProductOrderSave(String sUserId,OrdersProductDto ordersProductDto) throws Exception;

	/*
	 * cart에서 주문
	 */
	OrdersDto memberCartOrderSave(String sUserId,DeliveryDto deliveryDto) throws Exception;

	/*
	 * cart에서 선택주문
	 */
	OrdersDto memberCartSelectOrderSave(String sUserId,DeliveryDto deliveryDto,List<Long> cartItemNoArray)throws Exception;
	/*
	 * 주문+주문아이템 목록
	 */
	List<Orders> memberOrderList(String userName)throws Exception;

	/*
	 * 주문상세보기
	 */
	Orders memberOrderDetail(Long orderNo) throws Exception;

	// 주문상태업데이트(특정주문)
	// 1.정상주문
	Orders updateStatementByNormalOrder(Long orderNo);

	// 2.취소주문
	Orders updateStatementByCancleOrder(Long orderNo);

	// 3.환불주문
	Orders updateStatementByRefundOrder(Long orderNo);

	Orders updateStatementByResetOrder(Long orderNo);
}
