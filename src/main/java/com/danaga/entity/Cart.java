package com.danaga.entity;


import com.danaga.dto.CartCreateDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Builder
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity {
	@Id
	@SequenceGenerator(name = "cart_cart_no_seq", sequenceName = "cart_cart_no_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private int qty;
	@ManyToOne()
	@JoinColumn(name = "memberId")
	@ToString.Exclude
	private Member member;

	@ManyToOne
	@JoinColumn(name = "optionSetId")
	private OptionSet optionSet;

	public static Cart toEntity(CartCreateDto dto) {
		return Cart.builder().qty(dto.getQty()).optionSet(dto.getOptionset()).build();
	}

}
