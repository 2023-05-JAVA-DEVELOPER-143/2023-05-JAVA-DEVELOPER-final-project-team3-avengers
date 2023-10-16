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
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) 
	private Long id;
	private String categoryName;
	
	@JoinColumn(name="superType", nullable = true)
	@ManyToOne
	private Category superType;
	
	@OneToMany(mappedBy = "superType")
	@Builder.Default
	private List<Category> childTypes= new ArrayList();
	
	@OneToMany(mappedBy = "category")
	@Builder.Default
	private List<Product> products = new ArrayList<>();
}