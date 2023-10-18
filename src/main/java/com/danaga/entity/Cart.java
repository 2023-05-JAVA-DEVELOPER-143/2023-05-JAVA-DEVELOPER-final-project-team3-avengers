package com.danaga.entity;

import org.hibernate.annotations.ColumnDefault;

import com.danaga.dto.CartDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@ToString(callSuper = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity{
	@Id
	@SequenceGenerator(name = "cart_cart_no_seq",sequenceName = "cart_cart_no_seq",initialValue = 1,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="cart_no")
	private Long id;

	private int cartQty;
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="optionSet_id")
	private OptionSet optionSet;
}