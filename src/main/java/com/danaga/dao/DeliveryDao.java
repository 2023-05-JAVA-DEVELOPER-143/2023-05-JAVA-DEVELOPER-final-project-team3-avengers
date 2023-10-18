package com.danaga.dao;

import com.danaga.entity.*;

public interface DeliveryDao {
	//배송신청
	Delivery insertDelivery(Delivery delivery);// 배송정보 입력후 배송하기 눌렀을 때
	
	//배송번호로 배송내역찾기
	Delivery selectDelivery(Long de_no);// 주문목록에서 현재 주문상태가 배송중, 배송완료일 때 배송번호나오게하기
	
	//update는 없음
	
	//배송취소   //배송취소는 Orders의 oState가 변경된다.
//	void deleteDelivery(Long de_no); // 주문목록의 현재 주문상태가 결제 완료인 상태에서 취소 가능하기 떄문에
}
