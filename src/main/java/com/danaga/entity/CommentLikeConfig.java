package com.danaga.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "CommentLikeConfig") 
@Table(name = "comment_like_config", uniqueConstraints = {
    @UniqueConstraint(name = "comment_like_config_uq", columnNames = {"board_id", "writer"})
})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentLikeConfig {
	
	@Id
	@SequenceGenerator(name = "comment_like_config_id_seq", sequenceName = "comment_like_config_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_config_id_seq")
	private Long id;
	
	@ColumnDefault(value = "0")
    private Integer isLike; //1.좋아요누른 상태 0.좋아요 없는상태
    @ColumnDefault(value = "0")
    private Integer disLike; //0.싫어요 없는상태 1.좋아요 누른 상태
    
    private String writer;
	@ManyToOne
    @JoinColumn(name = "co_id")
    private Comment comment;
}
