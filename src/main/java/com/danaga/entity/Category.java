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
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) 
	private Long id;
	private String name;
	
	@JoinColumn(name="parent", nullable = true)
	@ManyToOne
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	@Builder.Default
	private List<Category> childTypes= new ArrayList();
	
	@ManyToMany(mappedBy = "categorySet")
	@Builder.Default
	private List<Product> products = new ArrayList<>();
}
