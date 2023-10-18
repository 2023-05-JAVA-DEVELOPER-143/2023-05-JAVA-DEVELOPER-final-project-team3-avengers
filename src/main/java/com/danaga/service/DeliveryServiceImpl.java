package com.danaga.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.danaga.dao.*;

@Service
public class DeliveryServiceImpl {

	@Autowired
	private DeliveryDao deliveryDao;
	
	
}
