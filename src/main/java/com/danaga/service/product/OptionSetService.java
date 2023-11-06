package com.danaga.service.product;

import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.OptionUpdateDto;
import com.danaga.dto.product.OtherOptionSetDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.ProductListOutputDto;
import com.danaga.dto.product.ProductSaveDto;
import com.danaga.dto.product.ProductUpdateDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.UploadProductDto;

public interface OptionSetService {

		ResponseDto<?> deleteProduct(Long productId);
		ResponseDto<?> deleteOptionSet(Long optionSetId);
		ResponseDto<?> deleteOption(Long optionId);
 		
		ResponseDto<?> updateStock(OptionSetUpdateDto dto);
		ResponseDto<?> updateOrderCount(Long optionSetId, Integer orderCount);
		ResponseDto<?> updateViewCount(Long optionSetId);
		ResponseDto<?> update(ProductUpdateDto dto);
		ResponseDto<?> updateRating(ProductSaveDto dto);
		ResponseDto<?> update(OptionUpdateDto dto);

		ResponseDto<?> uploadProduct(UploadProductDto dto);
		
		ResponseDto<ProductDto> findById(Long optionSetId);
		ResponseDto<?> showOptionNameValues(Long categoryId);
		
		ResponseDto<ProductListOutputDto> displayHitProducts(Long optionSetId,Integer firstResult);
		
		ResponseDto<ProductListOutputDto> searchProducts(QueryStringDataDto dto,Integer firstResult);
		
		ResponseDto<OtherOptionSetDto> showOtherOptionSets(Long optionSetId);
		ResponseDto<?> showAllOptionNameValues(Long categoryId);
		ResponseDto<ProductListOutputDto> searchProductsForMember(QueryStringDataDto dto, String username,Integer firstResult);
		ResponseDto<ProductListOutputDto> displayHitProductsForMember(Long optionSetId, String username,Integer firstResult);
		
}
