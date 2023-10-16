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
