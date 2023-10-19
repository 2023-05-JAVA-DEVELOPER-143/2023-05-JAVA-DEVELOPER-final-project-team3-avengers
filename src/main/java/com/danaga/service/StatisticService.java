package com.danaga.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.danaga.entity.Statistic;

@Transactional
public interface StatisticService {
	//전체 통계 데이터 출력
	List<Statistic> Statistics(); 

		//N일자 통계 입력
	Statistic updateAt(String string);
		
		//전체 통계 입력
	List<Statistic> updateAll();
}
