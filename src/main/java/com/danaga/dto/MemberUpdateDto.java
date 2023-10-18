package com.danaga.dto;

import java.sql.Date;
import java.time.LocalDateTime;

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
	private Long memberIdCode;
	private String memberId;
	private String memberPassword;
	private String memberEmail;
	private String memberNickname;
	private String memberAddress;
	private String memberPhoneNo;
	
	public static MemberUpdateDto toDto(Member entity) {
    	return MemberUpdateDto.builder()
    			.memberIdCode(entity.getMemberIdCode())
    			.memberId(entity.getMemberId())
    			.memberPassword(entity.getMemberPassword())
    			.memberEmail(entity.getMemberEmail())
    			.memberNickname(entity.getMemberNickname())
    			.memberAddress(entity.getMemberAddress())
    			.memberPhoneNo(entity.getMemberPhoneNo())
    			.build();
    }
}
