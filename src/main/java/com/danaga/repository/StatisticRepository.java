package com.danaga.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danaga.entity.Statistic;


public interface StatisticRepository extends JpaRepository<Statistic, Date>{
	// N일 총 판매수량
	@Query(value = "select count(createdate) from orders where to_char(createdate,'YYYYMMDD') = :findDate", nativeQuery = true)
	Long countTotSalesAt(@Param("findDate") String findDate);
	
	// N일 총 판매수익
	@Query(value = "select sum(o_price) from orders where to_char(createdate,'YYYYMMDD') = :findDate", nativeQuery = true)
	Long countTotRevenueAt(@Param("findDate") String findDate);
	
	// N일 가입한 신규회원 수
	@Query(value = "select count(member_id_code) from member where to_char(member_join_date,'YYYYMMDD') = :findDate", nativeQuery = true)
	Long countNewMembersAt(@Param("findDate") String findDate);
	
	// N일 작성된 게시글 수
	

}
