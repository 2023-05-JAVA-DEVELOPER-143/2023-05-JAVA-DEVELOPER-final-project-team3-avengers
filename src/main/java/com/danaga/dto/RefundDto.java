package com.danaga.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RefundDto {
	private Long re_no;
	private String re_desc;
	private Integer re_acno;
	
}
//주로 입력데이터를 담음  '환불하시겠습니까? 네'  누르면 refund객체 만들어진다. 