package com.danaga.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.danaga.dto.*;
import com.danaga.entity.*;

@Service
public interface DeliveryService {

	Delivery saveDeliveryByOrdersId(Delivery delivery); 
	
	Delivery findDeliveryByOrdersId(Long id);
//	void deleteDelivery(Long de_no) throws Exception;
}
