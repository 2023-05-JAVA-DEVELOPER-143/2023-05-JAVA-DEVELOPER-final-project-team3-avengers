package com.danaga.repository.product;

import java.util.Iterator;
import java.util.List;

import com.danaga.dto.product.QueryStringDataDto;
import com.danaga.entity.Options;

public class OptionSetSearchQuery {

	private String searchQuery;

	public void setOrderType(String orderType) {
		this.searchQuery=this.searchQuery.replace(":orderType", orderType);
	}

	public OptionSetSearchQuery() {
		this.searchQuery="SELECT os "
				+ "FROM OptionSet os "
				+ " join fetch os.product p"
				+ " WHERE os.stock >0 "
				+ " Order By :orderType ";
		setOrderType(OptionSetQueryData.BY_ORDER_COUNT);//default 설정
	}
	public OptionSetSearchQuery(QueryStringDataDto searchDto) {
		searchDto.getCategory().ifPresent((category)->{
			
		
		this.searchQuery="SELECT os "
				+ "FROM OptionSet os "
				+ " join fetch os.product p"
				+ OptionSetQueryData.JOIN_CATEGORY 
				+ " WHERE os.stock >0 ";
		});
		if(searchDto.getCategory().isEmpty()) {
			this.searchQuery="SELECT os "
					+ "FROM OptionSet os "
					+ " join fetch os.product p"
					+ " WHERE os.stock >0 ";
		}
		searchDto.getOptionset().ifPresent((optionset)->{
			for(int i=0; i<optionset.size();i++) {
				optionFilter(optionset.get(i).getName(), optionset.get(i).getValue());
			}
		});
		
		searchDto.getCategory().ifPresent((category)->{
			categoryFilter(category);
		});
		searchDto.getNameKeyword().ifPresent((name)->{
			nameKeyword(name);
		});
		searchDto.getBrand().ifPresent((brand)->{
			brandFilter(brand);
		});
		
		int minPrice = searchDto.getMinPrice();
		int maxPrice = searchDto.getMaxPrice();
		
		priceRange(minPrice, maxPrice);
		this.searchQuery+= " Order By :orderType ";
		searchDto.getOrderType().ifPresent((orderType)->{
			if(orderType.equals("판매순")) {
				setOrderType(OptionSetQueryData.BY_ORDER_COUNT);
			}else if(orderType.equals("조회순")) {
				setOrderType(OptionSetQueryData.BY_VIEW_COUNT);
			}else if(orderType.equals("최신순")) {
				setOrderType(OptionSetQueryData.BY_CREATE_TIME);
			}else if(orderType.equals("평점순")) {
				setOrderType(OptionSetQueryData.BY_RATING);
			}else if(orderType.equals("최저가순")) {
				setOrderType(OptionSetQueryData.BY_TOTAL_PRICE);
			}else {//default
				setOrderType(OptionSetQueryData.BY_ORDER_COUNT);
			}
		});
		if(searchDto.getOrderType().isEmpty()) {
			setOrderType(OptionSetQueryData.BY_ORDER_COUNT);
		}
	}
	public void categoryFilter(String category) {
		String category_filter = "AND cs.category.name = :categoryFilter ";
		category_filter = category_filter.replace(":categoryFilter", "'"+category+"'");
		this.searchQuery+=category_filter;
	}
	public void brandFilter(String brand) {
		String brand_filter = "AND os.product.brand like :brandFilter ";
		brand_filter = brand_filter.replace(":brandFilter", "'%"+brand+"%'");
		this.searchQuery+=brand_filter;
	}
	public void optionFilter(String optionName,String optionValue) {
		String option_filter = "AND EXISTS ( SELECT 1 FROM Options o WHERE o.optionSet = os AND o.name = :optionName AND o.value = optionValue )";
		option_filter = option_filter.replace(":optionName", "'"+optionName+"'");
		option_filter = option_filter.replace(":optionValue", "'"+optionValue+"'");
		this.searchQuery+=option_filter;
	}
	public void priceRange(int minPrice,int maxPrice) {
		String price_range = "AND os.totalPrice between :minPrice and :maxPrice ";
		price_range = price_range.replace(":minPrice", String.valueOf(minPrice));
		price_range = price_range.replace(":maxPrice", String.valueOf(maxPrice));
		this.searchQuery+=price_range;
	}
	public void nameKeyword(String nameKeyword) {
		String name_keyword = "AND os.product.name like :nameKeyword ";
		name_keyword = name_keyword.replace(":nameKeyword", "'"+nameKeyword+"'");
		this.searchQuery+=name_keyword;
	}
	public String build() {
		return this.searchQuery;
	}

}
