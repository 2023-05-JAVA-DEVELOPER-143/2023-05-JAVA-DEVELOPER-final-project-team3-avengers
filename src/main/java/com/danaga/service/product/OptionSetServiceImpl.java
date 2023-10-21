package com.danaga.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.dto.product.uploadProductDto;
import com.danaga.entity.Category;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Product;
import com.danaga.repository.product.OptionSetQueryData;
import com.danaga.repository.product.OptionSetSearchQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionSetServiceImpl implements OptionSetService{
	
	private final OptionSetDao optionSetDao;
	private final OptionsDao optionDao;
	private final ProductDao productDao;
	private final CategoryDao categoryDao;
	
	//프로덕트 삭제해서 옵션셋도 같이 삭제되게
	@Override
	public ResponseDto<?> deleteProduct(Long productId, QueryStringDataDto dataDto) {
		productDao.deleteById(productId);
		List<OptionSet> optionSetList = optionSetDao.findByFilter(dataDto);
		return ResponseDto.<OptionSet>builder().data(optionSetList).build();
	}
	//옵션셋 삭제시 옵션들도 삭제
	@Override
	public ResponseDto<?> deleteOptionSet(Long optionSetId, QueryStringDataDto dataDto) {
		optionSetDao.deleteById(optionSetId);
		List<OptionSet> optionSetList = optionSetDao.findByFilter(dataDto);
		return ResponseDto.<OptionSet>builder().data(optionSetList).build();
	}
	//옵션 삭제하고 옵션이 붙어있던 오리진 옵션셋 반환 
	@Override
	public ResponseDto<?> deleteOption(Long optionId) {
		optionDao.deleteById(optionId);
		OptionSet originOptionSet = optionSetDao.findByOptionId(optionId);
		List<OptionSet> optionSet = new ArrayList<>();
		optionSet.add(originOptionSet);
		return ResponseDto.<OptionSet>builder().data(optionSet).build();
	}

	//오더하면 옵션셋 재고 -1, 환불하거나 취소하면 +1
	//+1, -1은 컨트롤러에서 get+1로 하고 여기서는 그냥 지정한 숫자로 변경 
	@Override
	public ResponseDto<?> updateStock(OptionSetUpdateDto dto) {
		OptionSet optionset = optionSetDao.updateStock(dto);
		List<OptionSet> data = new ArrayList<>();
		data.add(optionset);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	
	//주문했을때 주문수 업뎃, +,-는 컨트롤러에서 get+1 
	@Override
	public ResponseDto<?> updateOrderCount(Long optionSetId,Integer orderCount) {
		OptionSet optionset = optionSetDao.updateOrderCount(optionSetId, orderCount);
		List<OptionSet> data = new ArrayList<>();
		data.add(optionset);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}
	//클릭했을때 조회수 업뎃
	@Override
	public ResponseDto<?> updateViewCount(Long optionSetId) {
		OptionSet optionset = optionSetDao.updateViewCount(optionSetId);
		List<OptionSet> data = new ArrayList<>();
		data.add(optionset);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}

	//같은 카테고리 인기상품
	@Override
	public ResponseDto<?> displayHitProducts(Long optionSetId) {
		Category findCategory = categoryDao.findByOptionSetId(optionSetId);
		String orderType = OptionSetQueryData.BY_VIEW_COUNT;
		List<OptionSet> searchResult =optionSetDao.findByFilter(QueryStringDataDto.builder().orderType(Optional.of(orderType)).category(Optional.of(findCategory.getName())).build());
		return ResponseDto.<OptionSet>builder().data(searchResult).build();
	}
	//카테고리에 해당하는 리스트 전체 조회
	//조건에 해당하는 리스트 전체 조회
	@Override
	public ResponseDto<?> searchProducts(QueryStringDataDto dto) {
		List<OptionSet> data = optionSetDao.findByFilter(dto);
		return ResponseDto.<OptionSet>builder().data(data).build();
	}

	//장바구니에서 옵션 변경하려면
	//해당 프로덕트의 옵션셋들이 무엇이 있는지 알아야함
	//그리고 옵션 변경이 아니라 옵션셋 변경으로 
	//프로덕트 아이디로 옵션셋 찾기
	//품절옵션은 일단 표시하고 프론트에서 표시
	@Override
	public ResponseDto<?> showOtherOptionSets(Long optionSetId) {
		Product product = productDao.findByOptionSetId(optionSetId); 
		List<OptionSet> findOptionSets = optionSetDao.findAllByProductId(product.getId());
		for (OptionSet optionSet : findOptionSets) {
			if(optionSet.getStock()==1)findOptionSets.remove(optionSet);
		}
		return ResponseDto.<OptionSet>builder().data(findOptionSets).build();
	}
	
	
	/////////////////////////////////////////////////////////
	//옵션은 바꿀때 바꾼 결과 기존의 옵션셋과 겹치는지 확인
	//프로덕트도 기존제품과 겹치는지 확인 겹치면 옵션셋도 비교해서 옵션셋으로 추가할지 아니면 이미 있어서  그냥 추가를 안할지 
	@Override
	public ResponseDto<?> update(ProductSaveDto dto) {
		return ResponseDto.<Product>builder().data(null).build();
	}

	//토탈프라이스도 계산
	@Override
	public ResponseDto<?> update(OptionSaveDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	//프로덕트, 옵션, 옵션셋, 추가
	@Override
	public ResponseDto<?> uploadProduct(uploadProductDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	//최하위 카테고리 선택하고 나면 어떤 옵션 필터들 있는지 옵션 명과 옵션값 나열
			//일단 최하위 카테고리인지 확인하고 
	@Override
	public ResponseDto<?> showOptionNameValues(OptionNameValueDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	//프로덕트 별점 업뎃
	@Override
	public ResponseDto<?> updateRating(ProductSaveDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
