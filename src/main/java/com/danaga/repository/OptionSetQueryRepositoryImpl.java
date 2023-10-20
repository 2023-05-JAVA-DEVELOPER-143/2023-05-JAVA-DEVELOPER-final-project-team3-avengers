package com.danaga.repository;

import java.util.List;
import com.danaga.entity.OptionSet;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OptionSetQueryRepositoryImpl implements OptionSetQueryRepository{
	
	private final JPAQueryFactory queryFactory;
	
	public List<OptionSet> findByFilter(){
		return null;
		
	}
}
