package com.danaga.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@Id
	private String memberEmail;
	private String memberId;
	private String memberPassword;
	private String memberAddress;
	private Date memberBirthday;
	private String memberPhoneNo;
	@CreationTimestamp
	private LocalDateTime memberJoinDate;
	private String memberRole;
	private String memberGrade; /* Rookie Bronze, Silver, Gold, Platinum, Diamond 결제 가격의 1%가 등급 포인트로 쌓임
	 							등급 점수  Bronze : 1000 ~ 5000
	 										Silver : 5001 ~ 10000
	 										Gold : 10001 ~ 20000
	 										Platinum : 20001 ~ 35000
	 										Diamond : 35001 ~ MAX */
	private Long memberGradePoint;
	
	
}
