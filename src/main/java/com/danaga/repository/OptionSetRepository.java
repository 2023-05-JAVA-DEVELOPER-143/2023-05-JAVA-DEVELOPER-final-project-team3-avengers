package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.OptionSet;
import java.util.List;
import com.danaga.entity.Options;
import java.time.LocalDateTime;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Product;




public interface OptionSetRepository extends JpaRepository<OptionSet, Long> {
	List<OptionSet> findByOptions(List<Options> options);
	List<OptionSet> findByCreateTime(LocalDateTime createTime);
	List<OptionSet> findByOrderItems(List<OrderItem> orderItems);
	List<OptionSet> findByProduct(Product product);
	List<OptionSet> findByUpdateTime(LocalDateTime updateTime);
	OptionSet findByOrderItem(OrderItem orderItem);
	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLike(String name, String model);
	List<OptionSet> findByAllId(Long id);
	
}
