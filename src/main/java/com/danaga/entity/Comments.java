package com.danaga.entity;

import java.util.ArrayList;
import java.util.List;

import com.danaga.dto.CommentDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comments extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String writer;

  private String pw;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  private Board board;

  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Comments parent;

  @Builder.Default
  @OneToMany(mappedBy = "parent", orphanRemoval = true,cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
  private List<Comments> children = new ArrayList<>();

  public void update(CommentDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  // 부모 댓글 수정
  public void updateParent(Comments parent){
    this.parent = parent;
  }


 
}
