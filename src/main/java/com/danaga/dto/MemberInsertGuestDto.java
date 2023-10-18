package com.danaga.dto;

import com.danaga.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberInsertGuestDto {
	private Long memberIdCode;
	private String memberId;
	private String memberName;
	private String memberPhoneNo;
	
	public static MemberInsertGuestDto toDto(Member entity) {
    	return MemberInsertGuestDto.builder()
    			.memberIdCode(entity.getMemberIdCode())
    			.memberId(entity.getMemberId())
    			.memberName(entity.getMemberName())
    			.memberPhoneNo(entity.getMemberPhoneNo())
    			.build();
    }
}

