package com.danaga.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.criteria.Order;
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
	@Column(length = 1000)
	private Long oNo;
	@Column(length = 1000)
	private String oDesc;
	@Column(length = 100)
	private Integer oPrice;
	@Column(length = 100)
	private String oState;
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdate;

	@OneToOne
	@JoinColumn(name = "de_no")
	private Delivery delivery;
	
	@OneToOne
	@JoinColumn(name = "re_no")
	private Refund refund;
	
	@OneToMany(mappedBy = "orders")
	@Builder.Default
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "memeber_id_code")
	private Member member;
}
