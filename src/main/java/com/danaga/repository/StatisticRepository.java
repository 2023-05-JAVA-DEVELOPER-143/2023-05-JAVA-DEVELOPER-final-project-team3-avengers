package com.danaga.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danaga.entity.Statistic;


public interface StatisticRepository extends JpaRepository<Statistic, Date>{
	// N일 총 판매수량
	@Query(value = "select nvl(count(create_date),0) from orders where to_char(create_date,'YYYYMMDD') = :findDate", nativeQuery = true)
	Long countTotSalesOn(@Param("findDate") String findDate);
	
	// N일 총 판매수익
	@Query(value = "select nvl(sum(price),0) from orders where to_char(create_date,'YYYYMMDD') = :findDate", nativeQuery = true)
	Long countTotRevenueOn(@Param("findDate") String findDate);
	
	// N일 가입한 신규회원 수
	@Query(value = "select nvl(count(id),0) from member where to_char(join_date,'YYYYMMDD') = :findDate", nativeQuery = true)
	Long countNewMembersOn(@Param("findDate") String findDate);
	
	// N일 작성된 게시글 수 완료
	

}
