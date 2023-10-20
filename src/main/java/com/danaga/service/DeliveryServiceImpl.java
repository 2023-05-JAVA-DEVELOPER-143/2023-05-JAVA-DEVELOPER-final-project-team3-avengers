package com.danaga.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.danaga.dao.*;
import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.*;

@Service
public class DeliveryServiceImpl implements DeliveryService{

	@Autowired
	private DeliveryDao deliveryDao;
	
	@Autowired 
	private DeliveryRepository deliveryRepository;
	
	
	
	//배송번호로 배송내역찾기
//	public DeliveryResponseDto  getDeliveryById(Long id) {
//		Delivery getDelivery = deliveryDao.selectDelivery(id);
//		DeliveryResponseDto deliveryResponseDto = DeliveryResponseDto.toDto(getDelivery);
//		return deliveryResponseDto;
//	}
	
	
	//배송신청
	@Override
	@Transactional
	public Delivery saveDeliveryByOrdersId(Delivery delivery) {
		Delivery createdDelivery = deliveryDao.insertDelivery(delivery);
		return createdDelivery;
	}
	
	//배송확인
	@Override
	@Transactional
	public Delivery findDeliveryByOrdersId(Long id) {
		Delivery findDelivery = deliveryRepository.findDeliveryByOrdersId(id);
		if (findDelivery.getOrders() != null) {
		    System.out.println("@@@@@@@@@@@@" + findDelivery.getOrders());
		} else {
		    System.out.println("@@@@@@@@@@@@ Orders is null");
		}
		return findDelivery;
	}
	
}
