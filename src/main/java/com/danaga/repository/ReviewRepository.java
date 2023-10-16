package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danaga.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	/*
	 * // 특정 상품의 특정 고객에 작성한 리뷰 조회
	 * 
	 * @Query(value =
	 * "select * from review r1 left outer join member m1 on r1.memberId=m1.memberId left outer join product p1 on r1.productId=p1.productId "
	 * ,nativeQuery = true)
	 * List<Review>findByProductIdAndMemberId(@Param("memberId")String
	 * memberId,@Param("productId") Long productId);
	 */
	@Query(value = "select * from review r1 left outer join product p1 on r1.productId=p1.productId",nativeQuery = true)
	List<Review>findByProductId(@Param("productId")Long productId);
}
