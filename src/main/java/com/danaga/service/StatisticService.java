package com.danaga.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.danaga.entity.Statistic;

@Transactional
public interface StatisticService {
	//N일자 통계 입력 및 반환
	Statistic updateAt(String string);
	
	//최근 7일치 기록 단순반환
	List<Statistic> latest7DaysStatistic();
	
	//이번달 기록 단순반환
	List<Statistic> thisMonthStatistic();
	
	//YYYYMM월 기록 단순반환
	List<Statistic> monthlyStatistic(String month);

	//YYYY년 기록 단순반환
	List<Statistic> yearlyStatistic(String year);
	
	/******************** Batch *****************/
	
	//이번 달 업데이트
	void updateThisMonth();
	
	//월별 업데이트
	void createMoData(String month);
	


	
}
