package com.danaga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Cart extends BaseEntity{
	
	@Id
	@SequenceGenerator(name = "cart_cart_no_seq" , sequenceName = "cart_cart_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private Integer cartQty;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	@ManyToOne
	@JoinColumn(name = "optionset_id")
	private OptionSet optionSet;
	
	
	
		
}
