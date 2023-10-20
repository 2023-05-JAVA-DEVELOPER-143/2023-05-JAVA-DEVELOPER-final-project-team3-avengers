package com.danaga.repository;

public class OptionSetQueryData {
	public static final String BY_ORDER_COUNT = "os.orderCount desc";
	public static final String BY_VIEW_COUNT = "os.viewCount desc";
	public static final String BY_TOTAL_PRICE = "os.totalPrice asc";
	public static final String BY_CREATE_TIME = "os.createTime desc";
	public static final String BY_RATING = "p.rating desc";

	public static final String NONE = "";
	public static final String JOIN_CATEGORY = " join fetch categorySet cs ";
}