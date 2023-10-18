package com.danaga.dao;

import com.danaga.entity.*;

public interface RefundDao {
	
	//환불 요청
	Refund insertRefund(Refund refund);// 환불페이지. 환불하시겠습니까? 네
	

	//환불번호로 환불내역
	Refund selectRefund(Long re_no);// 메인페이지에서 환불목록창 따로 파서 나오게
	
	//update는 없음
	
	//환불 요청취소
	void deleteRefund(Long de_no); //주문목록에서 주문상태가 환불요청 취소일 때 가능하다. 
	
	
}
