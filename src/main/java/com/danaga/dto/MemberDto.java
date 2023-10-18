package com.danaga.dto;

import java.sql.Date;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
	private String memberId;
	private String memberEmail;
	private String memberPassword;
	private String memberAddress;
	private Date memberBirthday;
	private String memberPhoneNo;
	private LocalDateTime memberJoinDate;
	private String memberRole; // Member, Guest, Admin
	private String memberGrade; /* Rookie Bronze, Silver, Gold, Platinum, Diamond 결제 가격의 1%가 등급 포인트로 쌓임
	 							등급 점수   Rookie : 0 ~ 1000
	 										Bronze : 1001 ~ 5000
	 										Silver : 5001 ~ 10000
	 										Gold : 10001 ~ 20000
	 										Platinum : 20001 ~ 35000
	 										Diamond : 35001 ~  */
	private Integer memberGradePoint;
}
