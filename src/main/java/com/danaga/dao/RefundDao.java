package com.danaga.dao;

import com.danaga.entity.*;

import jakarta.persistence.*;

public interface RefundDao {
	
	//환불 요청
	public Refund insertRefund(Refund refund);// 환불페이지. 환불하시겠습니까? 네
	

	//환불번호로 환불내역
	public Refund selectRefund(Long id);// 메인페이지에서 환불목록창 따로 파서 나오게
	
	//update는 없음
	

	
	
}
