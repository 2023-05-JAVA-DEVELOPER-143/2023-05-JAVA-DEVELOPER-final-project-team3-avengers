	package com.danaga.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.querydsl.core.annotations.Generated;

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
public class OptionSet extends BaseEntity {
	//옵션들을 가지고 프로덕트를 FK로 참조하는 옵션셋
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;//pk
	
	private Integer totalPrice;
	//옵션을 추가할 때 옵션들의 추가금+프로덕트 가격 총합을 계산하여 저장

	private Integer stock;//재고
	@ColumnDefault(value = "0")
	private Integer viewCount;//조회수
	@ColumnDefault(value = "0")
	private Integer orderCount;//주문수

	@OneToMany(mappedBy = "optionSet")
	@Builder.Default
	private List<Options> options = new ArrayList<>();//옵션들을 가진다.

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;//프로덕트FK

	@OneToMany(mappedBy = "optionSet")
	@Builder.Default
	private List<OrderItem> orderItems = new ArrayList<>();
	//오더아이템List 

	@OneToMany(mappedBy = "optionSet")
	@Builder.Default 
	private List<Cart> carts = new ArrayList<>();
	//카트아이템List
	
	@Builder.Default
	@OneToMany(mappedBy = "optionSet")
	private List<RecentView> recentViews = new ArrayList<>();
	

}
