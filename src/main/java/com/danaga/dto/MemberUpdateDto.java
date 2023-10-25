package com.danaga.dto;


import com.danaga.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MemberUpdateDto {
	private Long id;
	private String password;
	private String nickname;
	private String address;
	
	public static MemberUpdateDto toDto(Member entity) {
    	return MemberUpdateDto.builder()
    			.id(entity.getId())
    			.password(entity.getPassword())
    			.nickname(entity.getNickname())
    			.address(entity.getAddress())
    			.build();
    }
}
