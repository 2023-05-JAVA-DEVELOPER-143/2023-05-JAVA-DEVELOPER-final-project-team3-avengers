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
public class CategorySet {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) 
	private Long id;
	
	@JoinColumn(name="category_no")
	@ManyToOne
	private Category category;
	
	@JoinColumn(name = "product_no")
	@ManyToOne
	private Product product;
}
