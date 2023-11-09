package com.danaga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CartCheckResponseDto {
	private Integer no;
	private String message = message(this.no);
	
	
	static String message(Integer no) {
		String message = "";
		switch (no) {
		case 1000: {
			message = " 장바구니로 이동하시겠습니까?";
			break;
		}
		case 2000: {
			message = " 이미 담겨있는 제품입니다. ";
			break;
		}
		case 2100: {
			message = "장바구니에는 5개";
			break;
		}
		case 2200: {
			message = "";
			break;
		}
		/*
		 * case "Platinum": { gradePoint = 13; break; } case "Diamond": { gradePoint =
		 * 16; break; } }
		 */
		}
		return message;
	}
	
}
