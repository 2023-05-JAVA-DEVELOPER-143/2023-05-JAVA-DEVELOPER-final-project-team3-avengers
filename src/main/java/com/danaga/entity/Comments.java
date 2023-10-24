package com.danaga.entity;

import java.util.ArrayList;
import java.util.List;

import com.danaga.dto.CommentDto;

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
@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "comments")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false, length = 1000)
    private String content;
    private String pw;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comments parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Builder.Default
    private List<Comments> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void updateParent(Comments comment) {
        this.parent = comment;
    }
    public void patch(CommentDto dto) {
    	//예외처리
    	if(this.id!=dto.getId()) {
    		throw new IllegalArgumentException("댓글 수정 실패!! 잘못된 id가 입력되었습니다.");
    	}
    	//객체 갱신
    	if(dto.getContent()!=null) {
    		this.content=dto.getContent();
    	}
    }
}
