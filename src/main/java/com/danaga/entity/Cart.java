package com.danaga.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.danaga.dto.CartCreateDto;
import com.danaga.dto.CartDto;
import com.danaga.dto.CartUpdateDto;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
	@ManyToOne
	@JoinColumn(name = "memberId")
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "optionSetId")
	private OptionSet optionSet;

	public static Cart toEntity(CartCreateDto dto) {
		return Cart.builder().qty(dto.getQty()).optionSet(dto.getOptionset()).build();
	}
	
	
	

}
