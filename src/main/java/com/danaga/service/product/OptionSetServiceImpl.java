package com.danaga.service.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.danaga.dao.product.CategoryDao;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dao.product.OptionsDao;
import com.danaga.dao.product.ProductDao;
import com.danaga.dto.ResponseDto;
import com.danaga.dto.product.CategoryDto;
import com.danaga.dto.product.OptionNameValueDto;
import com.danaga.dto.product.OptionSaveDto;
import com.danaga.dto.product.OptionSetCreateDto;
import com.danaga.dto.product.OptionSetUpdateDto;
import com.danaga.dto.product.ProductDto;
import com.danaga.dto.product.ProductSaveDto;
import com.danaga.dto.product.ProductUpdateDto;
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.UploadProductDto;
import com.danaga.entity.Category;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Product;
import com.danaga.repository.product.OptionNamesValues;
import com.danaga.repository.product.OptionSetQueryData;
import com.danaga.repository.product.OptionSetSearchQuery;

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
	@Transactional
	public ResponseDto<?> deleteProduct(Long productId) {
		productDao.deleteById(productId);
		// 기존 전체 프로덕트 리스트 화면에서 삭제 눌렀을때
		// 삭제 누른당시 리스트의 검색 조건을 그대로 가져와서
		// 삭제 후에도 같은 검색 조건으로 리스트 다시 갱신하게
		return null;
	}

	// 옵션셋 삭제시 옵션들도 삭제
	@Override
	@Transactional
	public ResponseDto<?> deleteOptionSet(Long optionSetId) {
		optionSetDao.deleteById(optionSetId);
		return null;
	}

	// 옵션 삭제하고 옵션이 붙어있던 오리진 옵션셋 반환
	@Override
	@Transactional
	public ResponseDto<?> deleteOption(Long optionId) {
		optionDao.deleteById(optionId);
		List<String> msg = new ArrayList<>();
		msg.add("삭제되었습니다.");
		return ResponseDto.<String>builder().data(msg).build();// 그냥 "msg"를 반환하는게
	}

	// 오더하면 옵션셋 재고 -1, 환불하거나 취소하면 +1
	// +1, -1은 컨트롤러에서 get+1로 하고 여기서는 그냥 지정한 숫자로 변경
	@Override
	public ResponseDto<?> updateStock(OptionSetUpdateDto dto) {
		OptionSet optionset = optionSetDao.updateStock(dto);
		List<ProductDto> data = new ArrayList<>();
		data.add(new ProductDto(optionset));
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	// 주문했을때 주문수 업뎃, +,-는 컨트롤러에서 get+1
	@Override
	public ResponseDto<?> updateOrderCount(Long optionSetId, Integer orderCount) {
		OptionSet optionset = optionSetDao.updateOrderCount(optionSetId, orderCount);
		List<ProductDto> data = new ArrayList<>();
		data.add(new ProductDto(optionset));
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	// 클릭했을때 조회수 업뎃
	@Override
	public ResponseDto<?> updateViewCount(Long optionSetId) {
		OptionSet optionset = optionSetDao.updateViewCount(optionSetId);
		List<ProductDto> data = new ArrayList<>();
		data.add(new ProductDto(optionset));
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	// 같은 카테고리 인기상품
	@Override
	@Transactional
	public ResponseDto<ProductDto> displayHitProducts(Long optionSetId,Integer firstResult) {
		System.out.println("####### oss" + optionSetId);
		
		List<Category> findCategory = categoryDao.findByOptionSetId(optionSetId);
		String orderType = OptionSetQueryData.BY_VIEW_COUNT;
		
		List<ProductDto> searchResult = optionSetDao
				.findByFilter(QueryStringDataDto.builder().orderType(orderType)
						.category(CategoryDto.builder().name(findCategory.get(findCategory.size() - 1).getName())
								.id(findCategory.get(findCategory.size() - 1).getId()).build())
						.build(),firstResult)
				.stream().map(t -> new ProductDto(t)).collect(Collectors.toList());
		;
		return ResponseDto.<ProductDto>builder().data(searchResult).build();
	}

	@Override
	@Transactional
	public ResponseDto<ProductDto> displayHitProductsForMember(Long optionSetId, String username,Integer firstResult) {
		List<Category> findCategory = categoryDao.findByOptionSetId(optionSetId);
		String orderType = OptionSetQueryData.BY_VIEW_COUNT;
		List<ProductDto> searchResult = optionSetDao
				.findForMemberByFilter(QueryStringDataDto.builder().orderType(orderType)
						.category(CategoryDto.builder().name(findCategory.get(findCategory.size() - 1).getName())
								.id(findCategory.get(findCategory.size() - 1).getId()).build())
						.build(), username,firstResult)
				.stream().filter(t -> t.getOsId()!=optionSetId).collect(Collectors.toList());
		;
		return ResponseDto.<ProductDto>builder().data(searchResult).build();
	}

	// 카테고리에 해당하는 리스트 전체 조회
	// 조건에 해당하는 리스트 전체 조회
	@Override
	public ResponseDto<ProductDto> searchProducts(QueryStringDataDto dto,Integer firstResult) {
		System.out.println("검색조건>>>>>>>>>>>>>>>"+dto);
		List<ProductDto> data = optionSetDao.findByFilter(dto,firstResult).stream().map(t -> new ProductDto(t))
				.collect(Collectors.toList());
		;
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	@Override
	public ResponseDto<ProductDto> searchProductsForMember(QueryStringDataDto dto, String username,Integer firstResult) {
		List<ProductDto> data = optionSetDao.findForMemberByFilter(dto, username,firstResult);
		;
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	// 장바구니에서 옵션 변경하려면
	// 해당 프로덕트의 옵션셋들이 무엇이 있는지 알아야함
	// 그리고 옵션 변경이 아니라 옵션셋 변경으로
	// 프로덕트 아이디로 옵션셋 찾기
	// 품절옵션은 일단 표시하고 프론트에서 표시?
	@Override
	@Transactional
	public ResponseDto<ProductDto> showOtherOptionSets(Long optionSetId) {
		Product product = productDao.findByOptionSetId(optionSetId);
		List<ProductDto> findOptionSets = optionSetDao.findAllByProductId(product.getId()).stream()
				.map(t -> new ProductDto(t)).collect(Collectors.toList());
		;
		for (ProductDto optionSet : findOptionSets) {
			if (optionSet.getStock() == 0)
				findOptionSets.remove(optionSet);
		}
		return ResponseDto.<ProductDto>builder().data(findOptionSets).build();
	}

	// 최하위 카테고리 선택하고 나면 어떤 옵션 필터들 있는지 옵션 명과 옵션값 나열
	@Override
	@Transactional
	public ResponseDto<?> showOptionNameValues(Long categoryId) {
		List<OptionNamesValues> optionNameValue = optionDao.findOptionNameValueMapByCategoryId(categoryId);
		Map<String, Set<String>> dto = optionNameValue.stream().collect(Collectors.groupingBy(
				OptionNamesValues::getName, Collectors.mapping(OptionNamesValues::getValue, Collectors.toSet())));
		List<Map<String, Set<String>>> data = new ArrayList<>();
		data.add(dto);
		return ResponseDto.<Map<String, Set<String>>>builder().data(data).build();
	}

	// 노트북 카테고리의 전체 옵션들 찾기
	// 부모 아이디를 받아서 자식들 찾아서 자식들의 카테고리 옵션들 구해서 그걸 하나의 map으로 합치기
	@Override
	@Transactional
	public ResponseDto<?> showAllOptionNameValues(Long categoryId) {
		List<Long> childrenIds = categoryDao.findChildTypesByParentId(categoryId).stream().map(t -> t.getId())
				.collect(Collectors.toList());
		Map<String, Set<String>> dto = new HashMap<String, Set<String>>();
		for (int i = 0; i < childrenIds.size(); i++) {
			List<OptionNamesValues> optionNameValue = optionDao.findOptionNameValueMapByCategoryId(childrenIds.get(i));
			Map<String, Set<String>> result = optionNameValue.stream().collect(Collectors.groupingBy(
					OptionNamesValues::getName, Collectors.mapping(OptionNamesValues::getValue, Collectors.toSet())));
			result.forEach((key, value) -> dto.merge(key, value, (v1, v2) -> {
				v1.addAll(v2);
				return v1;
			}));
		}
		List<Map<String, Set<String>>> data = new ArrayList<>();
		data.add(dto);
		System.out.println(dto);
		return ResponseDto.<Map<String, Set<String>>>builder().data(data).build();
	}

	// option update
	@Override
	@Transactional
	public ResponseDto<?> update(OptionSaveDto dto) {
		Options created = optionDao.update(dto);
		List<Options> data = new ArrayList<>();
		data.add(created);
		return ResponseDto.<Options>builder().data(data).build();
	}

	// 토탈프라이스도 계산
	// 프로덕트, 옵션, 옵션셋, 추가
	@Override
	@Transactional
	public ResponseDto<?> uploadProduct(UploadProductDto dto) {
		Product createdProduct = productDao.create(dto.getProduct());
		OptionSet createdOptionSet = optionSetDao.create(dto.getOptionSet());
		createdOptionSet.setProduct(createdProduct);
		int productPrice = createdProduct.getPrice();
		for (OptionSaveDto option : dto.getOptions()) {
			Options createdOption = optionDao.save(option);
			productPrice += option.getExtraPrice();
			createdOption.setOptionSet(createdOptionSet);
		}
		createdOptionSet.setTotalPrice(productPrice);
		List<ProductDto> data = new ArrayList<>();
		data.add(new ProductDto(createdOptionSet));
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	// 옵션셋 아이디로 옵션셋 찾기 디테일 들어갈때사용
	@Override
	public ResponseDto<ProductDto> findById(Long optionSetId) {
		OptionSet optionSet = optionSetDao.findById(optionSetId);
		List<ProductDto> data = new ArrayList<>();
		data.add(new ProductDto(optionSet));
		return ResponseDto.<ProductDto>builder().data(data).build();
	}

	/////////////////////////////////////////////////////////
	// 카테고리도 바꿀수 있게
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
		// 기존 프로덕트의 리뷰들을
		return null;
	}

}
