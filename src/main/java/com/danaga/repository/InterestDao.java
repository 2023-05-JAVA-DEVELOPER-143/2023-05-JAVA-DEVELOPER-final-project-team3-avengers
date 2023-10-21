package com.danaga.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.dto.InterestDto;
import com.danaga.entity.Interest;

public interface InterestDao {
	
	Boolean isInterested(InterestDto dto);
	
	InterestDto save(InterestDto dto);
	
	void delete(InterestDto dto);
	
	void deleteAll(Long memberId);
}
