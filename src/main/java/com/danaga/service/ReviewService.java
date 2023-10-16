package com.danaga.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danaga.dto.ReviewDto;
import com.danaga.entity.Member;
import com.danaga.entity.Product;
import com.danaga.entity.Review;
import com.danaga.repository.MemberRepository;
import com.danaga.repository.ProductRepository;
import com.danaga.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

	@Autowired
	private ReviewRepository rRepository;
	@Autowired
	private ProductRepository pRepository;
	@Autowired
	private MemberRepository mRepository;

	/*
	 * //Read 특정 회원이 작성한 리뷰 목록들 public List<ReviewDto> reviewsSomeOne(String
	 * memberId, Long productNo){
	 * 
	 * 댓글 목록 조회
	 * 
	 * return
	 * rRepository.findByProductIdAndMemberId(memberId,productNo).stream().map(
	 * review -> ReviewDto.createReviewDto(review)).collect(Collectors.toList()); }
	 */
	//Read 특정 상품에 등록된 리뷰들
	public List<ReviewDto> reviews(Long productNo){
		
		return rRepository.findByProductId(productNo).stream().map(review -> ReviewDto.createReviewDto(review)).collect(Collectors.toList());
	}

	// Create
	public ReviewDto create(Long productId, String memberId, ReviewDto dto) {
		// 상품 조회 및 예외 발생
		Member member = mRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("리뷰 생성 실패 ! 대상 회원이 없어요!"));

		Product product = pRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("리뷰 생성 실패! 대상 상품이 없어요!"));
		// 리뷰 엔티티 생성
		Review review = Review.createReview(dto, member, product);
		// 리뷰 엔티티를 DB에 저장
		Review created = rRepository.save(review);
		// DTO객체로 변환후 리턴
		return ReviewDto.createReviewDto(review);
	}
	// Update

	// Delete

}
