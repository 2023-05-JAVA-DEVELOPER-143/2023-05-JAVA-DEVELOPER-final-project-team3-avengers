package com.danaga.service;

import com.danaga.dto.*;
import com.danaga.entity.*;

public interface RefundService {
	
	//환불번호로 환불내역
	 RefundResponseDto getRefundById(Long id); // 메인페이지에서 환불목록창 따로 파서 나오게

	//환불 요청
	 RefundResponseDto saveRefund(RefundDto refundDto);// 환불페이지. 환불하시겠습니까? 네
	
}
