package com.danaga.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danaga.entity.Statistic;


public interface StatisticRepository extends JpaRepository<Statistic, Date>{
	// 최초생성
//	@Query("SELECT count(o.order_no) FROM Orders o WHERE TO_CHAR(o.order_date, 'YYYYMMDD') = '20230725';")
//	Long countByYesterdaySalesTotQty(@Param("orderDate") Date orderDate);
	
	
	// 업데이트 (수동 + 자동 겸용)
	

}
