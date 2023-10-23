package com.danaga.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.danaga.dao.*;
import com.danaga.dto.*;
import com.danaga.dto.RefundResponseDto.*;
import com.danaga.entity.*;
import com.danaga.repository.*;

@Service
public class RefundServiceImpl implements RefundService {
//	private final RefundDao refundDao;

	@Autowired
	private RefundDao refundDao;
	@Autowired
	private RefundRepository refundRepository;
	@Autowired
	private OrderRepository orderRepository;


//	// 환불요청 확인하기.메인페이지에서 환불목록창 따로 파서 나오게
//	public RefundResponseDto getRefundById(Long id) {
//		Refund getRefund = refundDao.selectRefund(id);
//		RefundResponseDto refundResponseDto = RefundResponseDto.toDto(getRefund);
//		return refundResponseDto;
//	}

	//환불요청
	@Override
	@Transactional
	public RefundResponseDto saveRefund(RefundDto refundDto, Long orderId) {
		Refund refund = Refund.toEntity(refundDto);
		Orders orders = orderRepository.findById(orderId).get();
		refund.setOrders(orders);
		RefundResponseDto refundResponseDto = RefundResponseDto.toDto(refund);
		return refundResponseDto;
	}
	
	//환불확인
	@Override
	@Transactional
	public RefundResponseDto findRefundByOrdersId(Long orderId) throws Exception{
		Refund findRefund = refundRepository.findRefundByOrdersId(orderId);
		
		if (findRefund.getOrders() != null) {
		    System.out.println("해당하는 주문내역은: " + findRefund.getOrders());
		} else {
			throw new Exception("해당하는 주문내역이 없습니다.");
		}
		
		RefundResponseDto refudnResponseDto = RefundResponseDto.toDto(findRefund);
		return refudnResponseDto;
		
	}
	
	
	
	
	
	
	

}
