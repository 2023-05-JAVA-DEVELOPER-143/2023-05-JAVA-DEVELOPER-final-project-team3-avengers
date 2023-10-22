package com.danaga.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.danaga.dao.product.CategoryDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dao.product.OptionsDao;
import com.danaga.dao.product.ProductDao;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.OptionNameValueDto;
import com.danaga.dto.product.OptionSaveDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.ProductSaveDto;
import com.danaga.dto.product.ProductUpdateDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.uploadProductDto;
import com.danaga.entity.Category;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;
import com.danaga.repository.product.OptionNamesOnly;
import com.danaga.repository.product.OptionSetQueryData;
import com.danaga.repository.product.OptionSetSearchQuery;
import com.danaga.repository.product.OptionValuesOnly;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionSetServiceImpl implements OptionSetService {

	private final OptionSetDao optionSetDao;
	private final OptionsDao optionDao;
	private final ProductDao productDao;
	private final CategoryDao categoryDao;
	private final CategoryService categoryService;

	// 프로덕트 삭제해서 옵션셋도 같이 삭제되게
	@Override
	public ResponseDto<?> deleteProduct(Long productId, QueryStringDataDto dataDto) {
		productDao.deleteById(productId);
		List<OptionSet> optionSetList = optionSetDao.findByFilter(dataDto);
		return ResponseDto.<OptionSet>builder().data(optionSetList).build();
	}

	// 옵션셋 삭제시 옵션들도 삭제
	@Override
	public ResponseDto<?> deleteOptionSet(Long optionSetId, QueryStringDataDto dataDto) {
		optionSetDao.deleteById(optionSetId);
		List<OptionSet> optionSetList = optionSetDao.findByFilter(dataDto);
		return ResponseDto.<OptionSet>builder().data(optionSetList).build();
	}

	// 옵션 삭제하고 옵션이 붙어있던 오리진 옵션셋 반환
	@Override
	public ResponseDto<?> deleteOption(Long optionId) {
		optionDao.deleteById(optionId);
		OptionSet originOptionSet = optionSetDao.findByOptionId(optionId);
		List<OptionSet> optionSet = new ArrayList<>();
		optionSet.add(originOptionSet);
		return ResponseDto.<OptionSet>builder().data(optionSet).build();
	}

	// 오더하면 옵션셋 재고 -1, 환불하거나 취소하면 +1
	// +1, -1은 컨트롤러에서 get+1로 하고 여기서는 그냥 지정한 숫자로 변경
	@Override
	public ResponseDto<?> updateStock(OptionSetUpdateDto dto) {
		OptionSet optionset = optionSetDao.updateStock(dto);
		List<OptionSet> data = new ArrayList<>();
		data.add(optionset);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}

	// 주문했을때 주문수 업뎃, +,-는 컨트롤러에서 get+1
	@Override
	public ResponseDto<?> updateOrderCount(Long optionSetId, Integer orderCount) {
		OptionSet optionset = optionSetDao.updateOrderCount(optionSetId, orderCount);
		List<OptionSet> data = new ArrayList<>();
		data.add(optionset);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}

	// 클릭했을때 조회수 업뎃
	@Override
	public ResponseDto<?> updateViewCount(Long optionSetId) {
		OptionSet optionset = optionSetDao.updateViewCount(optionSetId);
		List<OptionSet> data = new ArrayList<>();
		data.add(optionset);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}

	// 같은 카테고리 인기상품
	@Override
	public ResponseDto<?> displayHitProducts(Long optionSetId) {
		Category findCategory = categoryDao.findByOptionSetId(optionSetId);
		String orderType = OptionSetQueryData.BY_VIEW_COUNT;
		List<OptionSet> searchResult = optionSetDao.findByFilter(QueryStringDataDto.builder()
				.orderType(Optional.of(orderType)).category(Optional.of(findCategory.getName())).build());
		return ResponseDto.<OptionSet>builder().data(searchResult).build();
	}

	// 카테고리에 해당하는 리스트 전체 조회
	// 조건에 해당하는 리스트 전체 조회
	@Override
	public ResponseDto<?> searchProducts(QueryStringDataDto dto) {
		List<OptionSet> data = optionSetDao.findByFilter(dto);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}

	// 장바구니에서 옵션 변경하려면
	// 해당 프로덕트의 옵션셋들이 무엇이 있는지 알아야함
	// 그리고 옵션 변경이 아니라 옵션셋 변경으로
	// 프로덕트 아이디로 옵션셋 찾기
	// 품절옵션은 일단 표시하고 프론트에서 표시
	@Override
	public ResponseDto<?> showOtherOptionSets(Long optionSetId) {
		Product product = productDao.findByOptionSetId(optionSetId);
		List<OptionSet> findOptionSets = optionSetDao.findAllByProductId(product.getId());
		for (OptionSet optionSet : findOptionSets) {
			if (optionSet.getStock() == 1)
				findOptionSets.remove(optionSet);
		}
		return ResponseDto.<OptionSet>builder().data(findOptionSets).build();
	}

	// 최하위 카테고리 선택하고 나면 어떤 옵션 필터들 있는지 옵션 명과 옵션값 나열
		// 일단 최하위 카테고리인지 확인하고
		@Override
		public ResponseDto<?> showOptionNameValues(Long categoryId) {
			if (categoryService.isYoungest(categoryId)) {
				List<OptionNamesOnly> optionNames = optionDao.findOptionNamesByCategoryId(categoryId);
				List<OptionValuesOnly> optionValues = optionDao.findOptionValuesByCategoryId(categoryId);
				List<String> optionNameStrings = optionNames.stream().map(OptionNamesOnly::getName)
						.collect(Collectors.toList());
				List<String> optionValueStrings = optionValues.stream().map(OptionValuesOnly::getValue)
						.collect(Collectors.toList());
				OptionNameValueDto dto = OptionNameValueDto.builder().optionNames(optionNameStrings)
						.optionValues(optionValueStrings).build();
				List<OptionNameValueDto> data = new ArrayList<>();
				data.add(dto);
				return ResponseDto.<OptionNameValueDto>builder().data(data).build();
			} else {
				return ResponseDto.builder().error("하위 카테고리를 선택하세요").build();
			}
		}
		
		//option update
		@Override
		public ResponseDto<?> update(OptionSaveDto dto) {
			Options origin = optionDao.findById(dto.getId());
			Options created = optionDao.save(new OptionSaveDto(origin));
			List<Options> data = new ArrayList<>();
			data.add(created);
			return ResponseDto.<Options>builder().data(data).build();
		}
		

		// 토탈프라이스도 계산
		// 프로덕트, 옵션, 옵션셋, 추가
		@Override
		@Transactional
		public ResponseDto<?> uploadProduct(uploadProductDto dto) {
			Product createdProduct = productDao.create(dto.getProduct());
			OptionSet createdOptionSet = optionSetDao.create(dto.getOptionSet());
			createdOptionSet.setProduct(createdProduct);
			for(OptionSaveDto option : dto.getOptions()) {
				Options createdOption = optionDao.save(option);
				createdOption.setOptionSet(createdOptionSet);
			}
			List<OptionSet> data = new ArrayList<>();
			data.add(createdOptionSet);
			return ResponseDto.<OptionSet>builder().data(data).build();
		}
		
		//옵션셋 아이디로 옵션셋 찾기 디테일 들어갈때사용
		@Override
		public ResponseDto<?> findById(Long optionSetId) {
			OptionSet optionSet=optionSetDao.findById(optionSetId);
			List<OptionSet> data = new ArrayList<>();
			data.add(optionSet);
			return ResponseDto.<OptionSet>builder().data(data).build();
		}
	/////////////////////////////////////////////////////////
		//카테고리도 바꿀수 있게 
	@Override
	public ResponseDto<?> update(ProductUpdateDto dto) {
//		Product origin = productDao.findById(dto.getId());
//		dto.getName().ifPresent(t->origin.setName(t));
//		dto.getBrand().ifPresent(t->origin.setBrand(t));
//		dto.getPrice().ifPresent(t->origin.setPrice(t));
//		dto.getDescImage().ifPresent(t->origin.setDescImage(t));
//		dto.getPrevDesc().ifPresent(t->origin.setPrevDesc(t));
//		dto.getImg().ifPresent(t->origin.setImg(t));
//		
//		if() {//변경하려는 속성들이 기존 제품의 속성과 겹치지 않았을때
//			
//		}else {
//		return ResponseDto.<Product>builder().error("이미 등록되어 있는 제품입니다.").build();
//		}
		return null;
	}

	


	

	// 프로덕트 별점 업뎃
	// 리뷰가 등록될때마다 갱신
	@Override
	public ResponseDto<?> updateRating(ProductSaveDto dto) {
		//기존 프로덕트의 리뷰들을 
		return null;
	}

}
