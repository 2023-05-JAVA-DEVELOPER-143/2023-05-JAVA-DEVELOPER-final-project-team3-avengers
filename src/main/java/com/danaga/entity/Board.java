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

    public static Board createBoard(BoardDto dto,Member member, BoardGroup boardGroup,List<LikeConfig> configs) {
    	if(boardGroup.getId()==null) {
    		System.out.println("게시판을 선택해주세요");
    	}
    	if(member.getId()==null) {
    		System.out.println("사용자가 없습니다");
    	}
    	if(member.getRole().equals("ADMIN")) {
    		dto.setIsAdmin(2);
    	}
    	return Board.builder()
    			.id(dto.getId()).title(dto.getTitle())
    			.content(dto.getContent()).img1(dto.getImg1()).img2(dto.getImg2())
    			.img3(dto.getImg3()).img4(dto.getImg4()).img5(dto.getImg5())
    			.isLike(dto.getIsLike()).disLike(dto.getDisLike())
    			.readCount(dto.getReadCount()).member(member).boardGroup(boardGroup).lConfigs(configs)
    			.build();
    }
  
    public void patch(BoardDto dto) {
    	if(this.id!=dto.getId()) {
    		throw new IllegalArgumentException("수정 실패!~ 대상이 잘못됬습니다.");
    	}
    	if(this.getMember().getId()!=dto.getMemberId()) {
    		throw new IllegalArgumentException("수정 실패!~ 회원이 잘못됬습니다.");
    	}
    	if(this.getBoardGroup().getId()!=dto.getBoardGroupId()) {
    		throw new IllegalArgumentException("수정 실패!~ 게시판 선택이 잘못됬습니다.");
    	}
    	if(dto.getLConfigs()==null) {
    		System.out.println("상태값이 없어요!");
    	}
    	if(dto.getTitle()!=null) {
    		this.title=dto.getTitle();
    	}
    	if(dto.getContent()!=null) {
    		this.content=dto.getContent();
    	}
    	if(dto.getImg1()!=null) {
    		this.img1=dto.getImg1();
    	}
    	if(dto.getImg2()!=null) {
    		this.img2=dto.getImg2();
    	}
    	if(dto.getImg3()!=null) {
    		this.img3=dto.getImg3();
    	}
    	if(dto.getImg4()!=null) {
    		this.img4=dto.getImg4();
    	}
    	if(dto.getImg5()!=null) {
    		this.img5=dto.getImg5();
    	}
    }

    public void readCountUp(Board board) {
    	board.readCount++;
    }
}
