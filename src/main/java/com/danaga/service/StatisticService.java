package com.danaga.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.danaga.dto.AdminOrderDataDto;
import com.danaga.dto.AdminProductInsertDto;
import com.danaga.entity.Statistic;

@Transactional
public interface StatisticService {
	//오늘날짜 단순 반환
	Statistic todayStatistic();
	
	//최근 7일치 기록 단순반환
	List<Statistic> latest7DaysStatistic();
	
	//이번달 기록 단순반환
	List<Statistic> thisMonthStatistic();
	
	//YYYYMM월 기록 단순반환
	List<Statistic> monthlyStatistic(String month);

	//YYYY년 기록 단순반환
	List<Statistic> yearlyStatistic(String year);
	
	//이번달 배송율 반환
	AdminOrderDataDto deliveryRate();
	
	/******************** Batch *****************/
	//N일자 통계 입력 및 반환
	Statistic updateAt(String string);
	
	//이번 달 업데이트
	void updateLatest7Days();
	
	//저번 달 업데이트
	void updateLastMonth();
	
	//월별 업데이트
	void createMoData(String month);
	
	/****************** Others ***************/
	//주문 상태 변경
	void updateOrderStatement(Long id, String stmt);
	
	/****************** InsertProduct ***************/
	//신규 제품 추가
	void createProduct(AdminProductInsertDto dto);
	


	
}
