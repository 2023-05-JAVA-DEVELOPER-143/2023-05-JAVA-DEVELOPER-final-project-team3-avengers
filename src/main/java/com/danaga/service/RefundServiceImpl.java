package com.danaga.service;

import org.springframework.beans.factory.annotation.*;

import com.danaga.dao.*;
import com.danaga.dto.*;
import com.danaga.entity.*;
;

public class RefundServiceImpl implements RefundService {
//	private final RefundDao refundDao;

	@Autowired
	private RefundDao refundDao;


//	// 환불요청 확인하기.메인페이지에서 환불목록창 따로 파서 나오게
//	public RefundResponseDto getRefundById(Long id) {
//		Refund getRefund = refundDao.selectRefund(id);
//		RefundResponseDto refundResponseDto = RefundResponseDto.toDto(getRefund);
//		return refundResponseDto;
//	}
	
	
	
	
	//환불요청
	public RefundResponseDto saveRefund(RefundDto refundDto) {
		Refund createdRefund = refundDao.insertRefund(Refund.toEntity(refundDto));
		RefundResponseDto refundResponseDto = RefundResponseDto.toDto(createdRefund);
		return refundResponseDto;
	}

}
