package com.danaga.dto;

import java.util.ArrayList;
import java.util.List;

public class CartResponseDto {
	private Integer qty; // 카트 수량
	private String productName; // 제품 이름
	private Integer totalPrice; // 
	private String pImage;
	private Long osId;
	private List<CartOptionDto> options=new ArrayList<>(); //name value
}
