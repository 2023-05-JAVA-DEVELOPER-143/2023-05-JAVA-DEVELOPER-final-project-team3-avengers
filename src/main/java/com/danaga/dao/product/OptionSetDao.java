package com.danaga.dao.product;

import java.util.List;
import java.util.Optional;

import com.danaga.dto.product.OptionSetCreateDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.entity.OptionSet;

public interface OptionSetDao {
//	List<OptionSet> findByAllId(Long id);

	OptionSet findById(Long id);
	List<OptionSet> findAllByProductId(Long productId);
	OptionSet create(OptionSetCreateDto dto);
	void deleteById(Long id);
	void deleteAllByProductId(Long productId);
	OptionSet findByOptionId(Long optionId);
	List<OptionSet> findAllByRecentView_MemberId(Long memberId);
	List<OptionSet> findAllByInterest_MemberId(Long memberId);
	List<OptionSet> findByFilter(QueryStringDataDto dataDto);
	OptionSet updateStock(OptionSetUpdateDto dto);
	OptionSet updateOrderCount(Long id,Integer orderCount);
	OptionSet updateViewCount(Long id);
	OptionSet calculateTotalPrice(OptionSetUpdateDto dto);
	
	
}
