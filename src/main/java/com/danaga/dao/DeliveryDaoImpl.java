package com.danaga.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.danaga.entity.*;
import com.danaga.repository.*;

@Repository
public class DeliveryDaoImpl implements DeliveryDao {

	@Autowired
	DeliveryRepository deliveryRepository;
	
	@Override
	public  Delivery selectDelivery(Long de_no) {
		Delivery selectDelivery= deliveryRepository.findById(de_no).get();
		return selectDelivery;
	}
	
	@Override
	public Delivery insertDelivery(Delivery delivery) {
		Delivery insertDelivery = deliveryRepository.save(delivery);
		return insertDelivery;
	}
	
	@Override
	public void deleteDelivery(Long de_no)  {
		Delivery deleteDelivery = deliveryRepository.findById(de_no).get();
		deliveryRepository.delete(deleteDelivery);
	}
	
}
