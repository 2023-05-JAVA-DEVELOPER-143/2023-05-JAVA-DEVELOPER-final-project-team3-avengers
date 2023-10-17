package com.danaga.dao;

import jakarta.persistence.criteria.Order;

public interface OrderDao {
	
	public int insert(Order order);
	
	public int deleteByUserId(String userId);
	
}
