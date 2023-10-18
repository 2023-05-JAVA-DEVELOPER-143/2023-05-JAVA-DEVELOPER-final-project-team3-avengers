package com.danaga.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "product")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String brand;
	private Integer price;
	private String descImage;
	private String prevDesc;
	private String pImage;
	
	@ColumnDefault(value = "0.0")
	private Double rating;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	@Builder.Default
	private Event event = new Event();
	
	@ManyToMany
	@JoinTable(name="category_set",
		joinColumns = @JoinColumn(name="product_id"),
		inverseJoinColumns = @JoinColumn(name="category_id")
			)
	@Builder.Default
	private List<Category> categoryset = new ArrayList<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "product")
	private List<Review> reviews = new ArrayList<>();
	@Builder.Default
	@OneToMany(mappedBy = "product")
	private List<RecentView> recentViews = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	@Builder.Default
	private List<OptionSet> optionsets = new ArrayList<>();
	
}
