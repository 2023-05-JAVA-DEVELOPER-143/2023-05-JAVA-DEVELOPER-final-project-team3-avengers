package com.danaga.entity;

import jakarta.persistence.Column;
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
	@Column(length = 255, nullable = false)
	private String memberId;
	@Column(length = 255, nullable = false)
	private String memberPassword;
	@Column(length = 255, nullable = false)
	private String memberAddress;
	private String memberBirthday;
	@Column(length = 255, nullable = false)
	private String memberPhoneNo;
	private String memberJoinDate;
	private String memberRole;
	private String memberGrade;
	
	
}
