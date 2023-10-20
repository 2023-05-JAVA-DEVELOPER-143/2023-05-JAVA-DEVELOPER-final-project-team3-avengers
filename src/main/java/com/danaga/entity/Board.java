package com.danaga.entity;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.danaga.dto.BoardDto;

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
import lombok.ToString;



@Entity(name = "Board")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Data
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String content;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    @ColumnDefault(value = "0")
    private Integer isLike;
    @ColumnDefault(value = "0")
    private Integer disLike;
    @ColumnDefault(value = "0")
    private Integer readCount;
    private Integer isAdmin;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id") 
    @ToString.Exclude
    @Builder.Default
    private Member member = new Member();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_group_id") 
    @ToString.Exclude
    @Builder.Default
    private BoardGroup boardGroup = new BoardGroup();

    @OneToMany(mappedBy = "board")
    @Builder.Default
    @ToString.Exclude
    private List<LikeConfig> lConfigs = new ArrayList<>();
	
	/*
	 * 해당 entity는 BoardGroupConfig의 상태(pk)에따라 게시판의 성향이 달라진다. 
	 	like는 memberId를 체크하여 해당게시물에 like를 누른 회원은 두번이상 좋아요가 박히지 않으며 두번클릭시 취소가 된다.
	 	
	  		pk 분류 
	 			1. 자유게시판
	 			2. 1:1문의
	 			3. FAQ
	 			4. 공지
			
			like and dislike properties
				join table with thumbs
				thumbs_id (thumbs pk)
				member_id(member pk)(fk)
				product_id(product pk)(fk)
				thumbs_status
		
	 */

  
    public void patch(Board board) {
        if (!isValidPatch(board)) {
            throw new IllegalArgumentException("수정 실패! 대상이 잘못됐습니다.");
        }

        if (board.getTitle() != null) {
            this.title = board.getTitle();
        }
        if (board.getContent() != null) {
            this.content = board.getContent();
        }
        if (board.getImg1() != null) {
            this.img1 = board.getImg1();
        }
        if (board.getImg2() != null) {
            this.img2 = board.getImg2();
        }
        if (board.getImg3() != null) {
            this.img3 = board.getImg3();
        }
        if (board.getImg4() != null) {
            this.img4 = board.getImg4();
        }
        if (board.getImg5() != null) {
            this.img5 = board.getImg5();
        }
    }

    private boolean isValidPatch(Board board) {
        return this.id.equals(board.getId()) &&
               this.member.getId().equals(board.getMember().getId()) &&
               this.boardGroup.getId().equals(board.getBoardGroup().getId());
    }


}
