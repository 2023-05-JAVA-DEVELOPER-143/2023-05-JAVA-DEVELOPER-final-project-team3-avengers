package com.danaga.entity;

import java.time.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
	@Id
	@SequenceGenerator(name = "DELIVERY_DELIVERY_NO_SEQ", sequenceName = "DELIVERY_DELIVERY_NO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_DELIVERY_NO_SEQ")
	@Column(length = 20)
	private Long deNo; //PK
	@Column(length = 10)
	private String deName; //수령인
	@Column(length = 20)
	private String dePhonenumber; //수령인전화번호
	@Column(length = 100)
	private String deAddress; //수령인 주소
	
	@OneToOne
	@JoinColumn(name = "oNo")
	private Orders orders; //주문번호
	 

}
