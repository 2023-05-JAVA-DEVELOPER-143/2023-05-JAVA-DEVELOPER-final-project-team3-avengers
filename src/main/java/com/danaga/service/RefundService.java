package com.danaga.service;

import org.springframework.stereotype.*;

import com.danaga.dto.*;
import com.danaga.entity.*;
@Service
public interface RefundService {
	
	//주문번호로 환불찾기
	Refund findRefundByOrdersId(Long id); // 메인페이지에서 환불목록창 따로 파서 나오게

	//환불 요청
	 Refund saveRefund(Refund refund);// 환불페이지. 환불하시겠습니까? 네
	
}
