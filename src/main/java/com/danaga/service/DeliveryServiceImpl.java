package com.danaga.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.danaga.dao.*;
import com.danaga.dto.*;
import com.danaga.entity.*;

@Service
public class DeliveryServiceImpl {

	@Autowired
	private DeliveryDao deliveryDao;
	
	
	//배송번호로 배송내역찾기
//	public DeliveryResponseDto  getDeliveryById(Long id) {
//		Delivery getDelivery = deliveryDao.selectDelivery(id);
//		DeliveryResponseDto deliveryResponseDto = DeliveryResponseDto.toDto(getDelivery);
//		return deliveryResponseDto;
//	}
	//배송신청
	Delivery saveDelivery(DeliveryDto deliveryDto) {
		Delivery createdDelivery = deliveryDao.insertDelivery(Delivery.toEntity(deliveryDto));
		return createdDelivery;
	}
	
}
