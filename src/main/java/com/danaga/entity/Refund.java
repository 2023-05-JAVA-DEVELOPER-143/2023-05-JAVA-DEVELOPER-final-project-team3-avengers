package com.danaga.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
	@SequenceGenerator(name = "refund_refund_no_seq",sequenceName = "refund_refund_no_seq",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refund_refund_no_seq")
	@Column(length = 20)
	private Long id;
	@Column(length = 100)
	private String description;
	@Column(length = 100)
	private String acNo;

	@OneToOne
	@JoinColumn(name = "orderId")
	private Orders orders;

}