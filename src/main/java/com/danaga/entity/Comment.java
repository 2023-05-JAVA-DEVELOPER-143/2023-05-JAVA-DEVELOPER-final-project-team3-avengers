package com.danaga.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String writer;
	private String content;
	private Integer secret;
	private String pw;
	private Integer depth;
	private Integer isLike;
	private Integer disLike;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "board_id")
	private Board board;
	
	@OneToMany(mappedBy = "comment")
	private List<CommentLikeConfig> configs;
}
