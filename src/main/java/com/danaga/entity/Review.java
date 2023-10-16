package com.danaga.entity;

import com.danaga.dto.ReviewDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	Long reviewNo;
	
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private String img;
	@Column
	private Long rating;
	
	/*
	 * 관계설정
	 * 리뷰와 유저의 관계 = 하나의 유저가 여러개의 리뷰를 작성할수 있음으로, 다대일 
	 * 리뷰와 프로덕트의 관계 = 하나의 제품에 하나이상의 리뷰를 작성 할 수 있음으로 다대일
	 */
	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "pid")
	private Product product;
	
	public static Review createReview(ReviewDto dto,Member member,Product product) {
		//예외 처리
		if(dto.getReviewNo()!=null) {
			throw new IllegalArgumentException("리뷰 생성 실패 ! 리뷰의 번호가 없어야 해요!");
		}
		if(dto.getProductNo() !=product.getProductNo()) {
			throw new IllegalArgumentException("리뷰 생성 실패 ! 리뷰의 id가 잘못되었어요");
		}
		if(dto.getMemberId()!=member.getMemberId()) {
			throw new IllegalArgumentException("리뷰 생성 실패 ! 회원의 아이디가 다릅니다");
		}
		//Review 객체 생성 및 반환
		return new Review(dto.getProductNo(),dto.getTitle(),dto.getContent(),dto.getImg(),dto.getRating(),member,product);
	}
	//객체 수정 메소드
	public void patch(ReviewDto dto) {
		//예외 처리
		if(this.reviewNo!=dto.getReviewNo()) {
			throw new IllegalArgumentException("리뷰 수정 실패 ! 입력된 번호가 잘못되었어요!");
		}
		//객체 갱신
		if(dto.getTitle()!=null) {
			this.title=dto.getTitle();
		}
		if(dto.getContent()!=null) {
			this.content=dto.getContent();
		}
		if(dto.getImg()!=null) {
			this.img=dto.getImg();
		}
		if(dto.getRating()!=null) {
			this.rating=dto.getRating();
		}
	}
	
}
