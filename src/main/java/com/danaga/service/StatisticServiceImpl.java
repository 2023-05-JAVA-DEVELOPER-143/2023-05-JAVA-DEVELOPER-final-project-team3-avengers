package com.danaga.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.danaga.config.OrderStateMsg;
import com.danaga.entity.Orders;
import com.danaga.entity.Statistic;
import com.danaga.repository.OrderRepository;
import com.danaga.repository.StatisticRepository;

@Service
public class StatisticServiceImpl implements StatisticService {
	@Autowired
	private StatisticRepository statisticRepository;
	@Autowired
	private OrderRepository orderRepository;

	// 수동업데이트 및 반환
	@Override
	public Statistic updateAt(String findDate) {
		Long salesTotQty = statisticRepository.countTotSalesOn(findDate);
		Long salesRevenue = statisticRepository.countTotRevenueOn(findDate);
		Long newMember = statisticRepository.countNewMembersOn(findDate);
		Long newBoard = statisticRepository.countNewBoardsOn(findDate);
		Statistic updatedStatistic = Statistic.builder().id(findDate).dailySalesTotQty(salesTotQty)
				.dailySalesRevenue(salesRevenue).dailyNewMember(newMember).dailyBoardInquiry(newBoard).build();
		statisticRepository.save(updatedStatistic);
		return updatedStatistic;
	}

	// 최근 7일 통계 단순반환
	@Override
	public List<Statistic> latest7DaysStatistic() {
		List<Statistic> statisticList = statisticRepository.findTop7ByOrderByIdDesc();
		return statisticList;
	}

	// 이번 달 기록
	@Override
	public List<Statistic> thisMonthStatistic() {
		List<Statistic> statisticList = statisticRepository
				.findByIdStartsWith(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
		return statisticList;
	}

	// YYYYMM월 기록 단순반환
	@Override
	public List<Statistic> monthlyStatistic(String month) {
		List<Statistic> statisticList = statisticRepository.findByIdStartsWith(month);
		return statisticList;
	}

	// YYYY년 월별 기록 단순 반환
	@Override
	public List<Statistic> yearlyStatistic(String year) {
		List<Statistic> statisticList = statisticRepository.findByIdStartsWith("1M" + year);
		return statisticList;
	}

	/******************** Batch *****************/

	// 이번 달 업데이트
	@Override
	public void updateThisMonth() {
		int date = Integer.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd")));
		for (int i = date; i > 0; i--) {
			String updateDate = String
					.valueOf(Integer.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))) - i + 1);
			updateAt(updateDate);
		}
		return;
	}

	// YYYYMM M데이터 생성
	@Override
	public void createMoData(String month) {
		List<Statistic> statisticList = monthlyStatistic(month);
		Long salesTotQty = 0l;
		Long salesRevenue = 0l;
		Long newMember = 0l;
		Long newBoard = 0l;
		for (Statistic statistic : statisticList) {
			salesTotQty += statistic.getDailySalesTotQty();
			salesRevenue += statistic.getDailySalesRevenue();
			newMember += statistic.getDailyNewMember();
			newBoard += statistic.getDailyBoardInquiry();
		}
		Statistic stat = Statistic.builder().id("1M" + month).dailySalesTotQty(salesTotQty)
				.dailySalesRevenue(salesRevenue).dailyNewMember(newMember).dailyBoardInquiry(newBoard).build();
		statisticRepository.save(stat);
		return;
	}

	// 주문 상태 변경
	@Override
	public void updateOrderStatement(Long id, String stmt) {
		Orders order = orderRepository.findById(id).get();
		OrderStateMsg msg;
		for (OrderStateMsg value : OrderStateMsg.values()) {
			if (value.name().equalsIgnoreCase(stmt)) {
				msg = value;
				order.setStatement(msg);
			}
		}
		return;
	}

}
