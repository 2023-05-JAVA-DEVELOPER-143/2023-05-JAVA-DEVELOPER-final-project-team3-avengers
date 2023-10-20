package com.danaga.dto;

import java.util.ArrayList;
import java.util.List;

import com.danaga.entity.BoardGroup;
import com.danaga.entity.LikeConfig;
import com.danaga.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
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
    
}
