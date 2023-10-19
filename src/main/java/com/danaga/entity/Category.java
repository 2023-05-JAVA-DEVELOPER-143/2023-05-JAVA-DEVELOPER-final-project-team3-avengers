package com.danaga.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {//셀프 참조하는 오너테이블, 카테고리셋과는 종속테이블
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) 
	private Long id; //pk
	private String name; //카테고리 이름
	
	@JoinColumn(name="parent", nullable = true)
	@ManyToOne
	private Category parent; //부모 카테고리
	
	@OneToMany(mappedBy = "parent")
	@Builder.Default
	private List<Category> childTypes= new ArrayList(); //자식 카테고리들
	
	@OneToMany(mappedBy = "category")
	@Builder.Default
	private List<CategorySet> categorySets = new ArrayList<>();
	//다대다 맵핑을 위한 categorySet과 관계 설정
}
