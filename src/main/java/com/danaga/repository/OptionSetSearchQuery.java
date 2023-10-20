package com.danaga.repository;

import java.util.Iterator;

import com.danaga.dto.QueryStringDataDto;

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
		this.searchQuery="SELECT os "
				+ "FROM OptionSet os "
				+ " join fetch os.product p"
				+ OptionSetQueryData.JOIN_CATEGORY 
				+ " WHERE os.stock >0 "
				+ " Order By :orderType ";
		if(searchDto.getOrderType().equals("판매순")) {
			setOrderType(OptionSetQueryData.BY_ORDER_COUNT);
		}else if(searchDto.getOrderType().equals("조회순")) {
			setOrderType(OptionSetQueryData.BY_VIEW_COUNT);
		}else if(searchDto.getOrderType().equals("최신순")) {
			setOrderType(OptionSetQueryData.BY_CREATE_TIME);
		}else if(searchDto.getOrderType().equals("평점순")) {
			setOrderType(OptionSetQueryData.BY_RATING);
		}else if(searchDto.getOrderType().equals("최저가순")) {
			setOrderType(OptionSetQueryData.BY_TOTAL_PRICE);
		}
		for(int i=0; i<searchDto.getOptionset().size();i++) {
			////////
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
