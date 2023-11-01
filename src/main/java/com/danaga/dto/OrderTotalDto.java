package com.danaga.dto;

import com.danaga.entity.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderTotalDto {
	private String ordererName;
	private String ordererPhoneNo;
    private String receiverName;
    private String receiverPhoneNo;
    private String receiverAddress;

    

}
