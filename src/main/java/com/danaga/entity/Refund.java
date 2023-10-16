package com.danaga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Refund {

	/*
	 * re_no(Pk) 
	 * re_desc 환불 사유 
	 * re_acno 환불 계좌번호 
	 * o_no(Fk)
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(length = 1000)
	private Long re_no;
	@Column(length = 1000)
	private String re_desc;
	@Column(length = 100)
	private String re_acno;

	

}
