package com.danaga.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Builder.Default
	private String status="F";
	private String name;
	private String type;
	private LocalDateTime startDay;
	private LocalDateTime endDay;
	private String range;
	private String target;
	private Double rate;
	private Integer discountPrice;
	@Builder.Default
	@OneToMany(mappedBy = "event")
	List<Product> products = new ArrayList<>();
}
