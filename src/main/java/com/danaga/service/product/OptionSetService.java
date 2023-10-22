package com.danaga.service.product;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.OptionNameValueDto;
import com.danaga.dto.product.OptionSaveDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.ProductSaveDto;
import com.danaga.dto.product.ProductUpdateDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.UploadProductDto;

public interface OptionSetService {

		ResponseDto<?> deleteProduct(Long productId, QueryStringDataDto dataDto);
		ResponseDto<?> deleteOptionSet(Long optionSetId, QueryStringDataDto dataDto);
		ResponseDto<?> deleteOption(Long optionId);
 		
		ResponseDto<?> updateStock(OptionSetUpdateDto dto);
		ResponseDto<?> updateOrderCount(Long optionSetId, Integer orderCount);
		ResponseDto<?> updateViewCount(Long optionSetId);
		ResponseDto<?> update(ProductUpdateDto dto);
		ResponseDto<?> updateRating(ProductSaveDto dto);
		ResponseDto<?> update(OptionSaveDto dto);

		ResponseDto<?> uploadProduct(UploadProductDto dto);
		
		ResponseDto<?> findById(Long optionSetId);
		ResponseDto<?> showOptionNameValues(Long categoryId);
		
		ResponseDto<?> displayHitProducts(Long optionSetId);
		
		ResponseDto<?> searchProducts(QueryStringDataDto dto);
		
		ResponseDto<?> showOtherOptionSets(Long optionSetId);
		
}
