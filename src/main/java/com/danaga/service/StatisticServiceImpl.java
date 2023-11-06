package com.danaga.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.config.OrderStateMsg;
import com.danaga.dao.product.OptionSetDao;
import com.danaga.dto.AdminOptionDto;
import com.danaga.dto.AdminOrderDataDto;
import com.danaga.dto.AdminProductDto;
import com.danaga.dto.AdminProductInsertDto;
import com.danaga.dto.product.CategoryDto;
import com.danaga.dto.product.OptionSetCreateDto;
import com.danaga.entity.Category;
import com.danaga.entity.CategorySet;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.entity.Orders;
import com.danaga.entity.Product;
import com.danaga.entity.Statistic;
import com.danaga.repository.CategorySetRepository;
import com.danaga.repository.OrderRepository;
import com.danaga.repository.StatisticRepository;
import com.danaga.repository.product.CategoryRepository;
import com.danaga.repository.product.OptionSetRepository;
import com.danaga.repository.product.OptionsRepository;
import com.danaga.repository.product.ProductRepository;

@Service
public class StatisticServiceImpl implements StatisticService {
	@Autowired
	private StatisticRepository statisticRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OptionSetDao optionSetDao;
	@Autowired
	private CategorySetRepository categorySetRepository;
	@Autowired
	private OptionsRepository optionsRepository;

	// 오늘날짜 단순 반환
	@Override
	public Statistic todayStatistic() {
		return statisticRepository.findById(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))).get();
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
		Collections.reverse(statisticList);
		return statisticList;
	}

	// 이번달 배송율 반환
	@Override
	public AdminOrderDataDto deliveryRate() {
		Long totSales = statisticRepository
				.countTotSalesThisMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
		Long toSales = statisticRepository
				.countToSalesThisMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
		Long failSales = statisticRepository
				.countFailSalesThisMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
		AdminOrderDataDto dto = AdminOrderDataDto.builder().totSales(totSales).toSales(toSales).failSales(failSales)
				.build();
		return dto;
	}

	/******************** Batch *****************/

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

	// 이번 달 업데이트
	@Override
	public void updateLatest7Days() {
		for (int i = 6; i >= 0; i--) {
			updateAt(LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		return;
	}

	// 저번 달 업데이트
	@Override
	public void updateLastMonth() {
		int date = Integer.valueOf(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd")));
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

	// 신규 카테고리 추가
	@Override
	public void createProduct(AdminProductInsertDto dto) {
		List<CategoryDto> categoryDto = dto.getCategoryDto();
		AdminProductDto adminProductDto = dto.getAdminProductDto();
		List<AdminOptionDto> adminOptionDto = dto.getAdminOptionDto();
		//가격계산
		Integer totPrice = 0;
		for (AdminOptionDto option : adminOptionDto) {
			totPrice += option.getExtraPrice();
		}
		//Product 추가
		Product product = productRepository.save(adminProductDto.toEntity());
		//Category & CategorySet 추가
		for (CategoryDto category : categoryDto) {
			Category createdCategory = categoryRepository.save(category.toEntity());
			categorySetRepository.save(CategorySet.builder().category(createdCategory).product(product).build());
		}
		//OptionSet 추가
		OptionSet optionSet = optionSetDao.create(OptionSetCreateDto.builder()
				.stock(adminProductDto.getStock())
				.productId(product.getId())
				.productPrice(adminProductDto.getPrice()+totPrice)
				.build());
		//Option 추가
		for (AdminOptionDto option : adminOptionDto) {
			optionsRepository.save(Options.builder()
					.name(option.getName())
					.value(option.getValue())
					.extraPrice(option.getExtraPrice())
					.optionSet(optionSet)
					.build());
		}


	}
	
	
}
