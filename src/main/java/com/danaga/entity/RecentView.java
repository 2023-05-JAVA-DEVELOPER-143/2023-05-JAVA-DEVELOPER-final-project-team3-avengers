package com.danaga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "RECENT_VIEW_UQ", columnNames = {"member_id","product_id"})})
public class RecentView extends BaseEntity{//멤버아이디와 프로덕트아이디로 고유키 제약 
	//최근 조회한 상품 
	//30일간만 보관 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;//복합키 설정해서 없애도 될듯? 
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;//member FK
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;//product FK
}
