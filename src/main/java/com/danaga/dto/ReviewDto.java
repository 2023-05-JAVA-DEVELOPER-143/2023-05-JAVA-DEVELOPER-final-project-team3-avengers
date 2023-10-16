package com.danaga.dto;

import com.danaga.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewDto {

	private	Long reviewNo;
	
	private String title;

	private String content;

	private String img;

	private Long rating;
	
	private String memberId;
	
	private Long productNo;
	
	public static ReviewDto createReviewDto(Review review) {
		return new ReviewDto(review.getReviewNo(),review.getTitle(),review.getContent(),review.getImg(),review.getRating(),review.getMember().getMemberId(),review.getProduct().getProductNo());
		
	}
}
