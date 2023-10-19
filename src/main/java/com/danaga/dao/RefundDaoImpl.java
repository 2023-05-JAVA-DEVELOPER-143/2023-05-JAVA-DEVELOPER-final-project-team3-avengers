package com.danaga.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.*;

import com.danaga.entity.*;
import com.danaga.repository.*;


public class RefundDaoImpl  implements RefundDao{

	@Autowired
	RefundRepository refundRepository;

	
	@Override
	public Refund selectRefund(Long id) {  // 메인페이지에서 환불목록창 따로 파서 나오게
		Refund selectedRefund = refundRepository.findById(id).get();
		return selectedRefund;
	}
	
	@Override
	public Refund insertRefund(Refund refund) { // 환불페이지. 환불하시겠습니까? 네
		Refund saveRefund = refundRepository.save(refund);
		return saveRefund;
	}
	
//	@Override
//	public void deleteRefund(Long id)  {
//		Refund DeleteRefund= refundRepository.findById(re_no).get();
//		refundRepository.delete(DeleteRefund);
//	}
}
