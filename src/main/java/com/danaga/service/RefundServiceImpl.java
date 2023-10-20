package com.danaga.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.danaga.dao.*;
import com.danaga.dto.*;
import com.danaga.entity.*;
import com.danaga.repository.*;

@Service
public class RefundServiceImpl implements RefundService {
//	private final RefundDao refundDao;

	@Autowired
	private RefundDao refundDao;
	@Autowired
	private RefundRepository refundRepository;


//	// 환불요청 확인하기.메인페이지에서 환불목록창 따로 파서 나오게
//	public RefundResponseDto getRefundById(Long id) {
//		Refund getRefund = refundDao.selectRefund(id);
//		RefundResponseDto refundResponseDto = RefundResponseDto.toDto(getRefund);
//		return refundResponseDto;
//	}

	//환불요청
	@Override
	@Transactional
	public Refund saveRefund(Refund refund) {
		Refund createdRefund = refundDao.insertRefund(refund);

		return createdRefund;
	}
	
	//환불확인
	@Override
	@Transactional
	public Refund findRefundByOrdersId(Long id) {
		Refund findRefund = refundRepository.findRefundByOrdersId(id);
		if (findRefund.getOrders() != null) {
		    System.out.println("@@@@@@@@@@@@" + findRefund.getOrders());
		} else {
		    System.out.println("@@@@@@@@@@@@ Orders is null");
		}
		return findRefund;
		
	}
	
	
	
	
	
	
	

}
