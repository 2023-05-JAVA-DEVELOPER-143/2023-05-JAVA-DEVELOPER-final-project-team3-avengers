package com.danaga.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.danaga.entity.Statistic;
import com.danaga.repository.StatisticRepository;

@Service
public class StatisticServiceImpl implements StatisticService{
	@Autowired
	private StatisticRepository statisticRepository;
	
	@Override
	public List<Statistic> Statistics() {
		List<Statistic> statisticList = statisticRepository.findAll();
		return statisticList;
	}
	//수동업데이트
	@Override
	public Statistic updateAt(String findDate) {
		Long salesTotQty = statisticRepository.countTotSalesOn(findDate);
		Long salesRevenue = statisticRepository.countTotRevenueOn(findDate);
		Long newMember = statisticRepository.countNewMembersOn(findDate);
		Long newBoard = statisticRepository.countNewBoardsOn(findDate);
		Statistic updatedStatistic = Statistic.builder().id(findDate).dailySalesTotQty(salesTotQty)
																	.dailySalesRevenue(salesRevenue)
																	.dailyNewMember(newMember)
																	.dailyBoardInquiry(newBoard).build();
		statisticRepository.save(updatedStatistic);
		return updatedStatistic;
	}
	
	//오래걸림 주의, 매 00시 실행
	@Override
	public List<Statistic> updateAll() {
		List<Statistic> statisticList = statisticRepository.findAll();
		for (Statistic statistic : statisticList) {
			String findDate = statistic.getId();
			updateAt(findDate);
		}
		statisticList = statisticRepository.findAll();
		return statisticList;
	}
	
	//최근 7일 통계
	@Override
	public List<Statistic> latest7DaysStatistic() {
		List<Statistic> statisticList = statisticRepository.findTop7ByOrderByIdDesc();
		return statisticList;
	}
	
	//YYYYMM월 기록
	@Override
	public List<Statistic> monthlyStatistic(String month) {
		List<Statistic> statisticList = statisticRepository.findByIdStartsWith(month);
		return statisticList;
	}
	
	//이번 달 기록
	@Override
	public List<Statistic> thisMonthStatistic() {
		List<Statistic> statisticList = statisticRepository.findByIdStartsWith(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
		return statisticList;
	}
	
	
}
