package com.danaga.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.*;

import com.danaga.entity.*;
import com.danaga.repository.*;


public class RefundDaoImpl  implements RefundDao{

	@Autowired
	RefundRepository refundRepository;

	
	@Override
	public Refund selectRefund(Long re_no) {
		Refund selectedRefund = refundRepository.findById(re_no).get();
		return selectedRefund;
	}
	
	@Override
	public Refund insertRefund(Refund refund) {
		Refund saveRefund = refundRepository.save(refund);
		return saveRefund;
	}
	
//	@Override
//	public void deleteRefund(Long re_no)  {
//		Refund DeleteRefund= refundRepository.findById(re_no).get();
//		refundRepository.delete(DeleteRefund);
//	}
}
