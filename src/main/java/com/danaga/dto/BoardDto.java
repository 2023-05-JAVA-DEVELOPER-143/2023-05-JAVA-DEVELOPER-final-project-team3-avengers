package com.danaga.dto;

import java.util.ArrayList;
import java.util.List;

import com.danaga.entity.Board;
import com.danaga.entity.BoardGroup;
import com.danaga.entity.LikeConfig;
import com.danaga.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDto {
	private Long id;
    private String title;
    private String content;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private Integer isLike;
    private Integer disLike;
    private Integer readCount;
    private Integer isAdmin;
    private Long memberId;
    private Long boardGroupId;
    @Builder.Default
    private List<LikeConfig> lConfigs = new ArrayList<>();
    
    public static Board toEntity(BoardDto dto,Member member, BoardGroup boardGroup) {
    	if(boardGroup.getId()==null) {
    		System.out.println("게시판을 선택해주세요");
    	}
    	if(member.getId()==null) {
    		System.out.println("사용자가 없습니다");
    	}
    	if(member.getRole()=="ADMIN") {
    		dto.setIsAdmin(1);
    	}
    	return Board.builder()
    			.id(dto.getId()).title(dto.getTitle())
    			.content(dto.getContent()).img1(dto.getImg1()).img2(dto.getImg2())
    			.img3(dto.getImg3()).img4(dto.getImg4()).img5(dto.getImg5())
    			.isLike(dto.getIsLike()).disLike(dto.getDisLike())
    			.readCount(dto.getReadCount()).member(member).boardGroup(boardGroup).lConfigs(dto.getLConfigs())
    			.build();
    }
    
}
