package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.OptionSet;
import java.util.List;
import com.danaga.entity.Options;
import java.time.LocalDateTime;
import com.danaga.entity.OrderItem;
import com.danaga.entity.Product;




public interface OptionSetRepository extends JpaRepository<OptionSet, Long> {
//	List<OptionSet> findByOptions(List<Options> options);
//	List<OptionSet> findByCreateTime(LocalDateTime createTime);
//	List<OptionSet> findByOrderItems(List<OrderItem> orderItems);
//	List<OptionSet> findByProduct(Product product);
//	List<OptionSet> findByUpdateTime(LocalDateTime updateTime);
//	OptionSet findByOrderItem(OrderItem orderItem);
//	List<OptionSet> findByStockIsNotEmptyAndCategory_CategoryIdAndProduct_PriceBetweenAndOptions_NameLikeOrOptions_ValueLikeOrProduct_NameLikeOrProduct_ModelLikeOrderByOrderCountDesc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByOrderCountAsc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByViewCountDesc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByViewCountAsc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByProduct_PriceDesc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByProduct_PriceAsc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByRatingDesc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByRatingAsc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByUpdateTimeDesc(String name, String model);
//	List<OptionSet> findByStockIsNotEmptyAndProduct_NameLikeOrProduct_ModelLikeOrderByUpdateTimeAsc(String name, String model);
//	List<OptionSet> findByAllId(Long id);
	
}
