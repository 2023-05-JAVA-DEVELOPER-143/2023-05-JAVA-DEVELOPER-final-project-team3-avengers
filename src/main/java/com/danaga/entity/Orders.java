package com.danaga.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Orders {
	
/*	o_no (PK)
	o_date

	user_id(FK)
	o_desc
	o_price
	o_state         주문상태( 주문완료, 배송중, 배송완료, 환불대기중, 환불완료)
*/
	@Id
	@SequenceGenerator(name = "order_order_no_seq",sequenceName = "order_order_no_seq",initialValue = 1,allocationSize = 1)
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(length = 20)
	private Long oNo;
	@Column(length = 1000)
	private String oDesc;
	@Column(length = 100)
	private Integer oPrice;
	@Column(length = 100)
	private String oState;
	@CreationTimestamp
	private LocalDateTime createdate;
}
