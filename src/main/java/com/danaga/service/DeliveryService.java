package com.danaga.service;

import org.springframework.transaction.annotation.*;

import com.danaga.dto.*;


public interface DeliveryService {
	
	DeliveryResponseDto  getDelivery(Long de_no);
	
	DeliveryResponseDto saveDelivery(DeliveryDto deliveryDto); 
	
//	void deleteDelivery(Long de_no) throws Exception;
}
