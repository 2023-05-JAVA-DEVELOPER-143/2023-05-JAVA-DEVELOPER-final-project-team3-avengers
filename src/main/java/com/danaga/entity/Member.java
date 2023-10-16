package com.danaga.entity;

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
	private String memberBirthday;
	private String memberPhoneNo;
}
