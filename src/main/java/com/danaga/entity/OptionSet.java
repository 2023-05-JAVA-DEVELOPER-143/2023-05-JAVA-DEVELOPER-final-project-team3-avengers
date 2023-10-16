package com.danaga.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OptionSet extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private Integer stock;
	@ColumnDefault(value = "0")
	private Integer viewCount;
	@ColumnDefault(value = "0")
	private Integer orderCount;
	
	@OneToMany(mappedBy = "optionset")
	@Builder.Default
	private List<Options> options = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "optionset")
	@Builder.Default
	private List<OrderItem> orderItems = new ArrayList<>();
	
	/*
	 * @OneToMany(mappedBy = "optionset")
	 * 
	 * @Builder.Default private List<Cart> carts = new ArrayList<>();
	 */
	
}
