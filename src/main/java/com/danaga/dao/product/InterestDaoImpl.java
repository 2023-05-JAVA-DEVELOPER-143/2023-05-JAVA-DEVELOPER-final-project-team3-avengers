package com.danaga.dao.product;

import org.springframework.stereotype.Repository;

import com.danaga.dto.product.InterestDto;
import com.danaga.entity.Interest;
import com.danaga.entity.Member;
import com.danaga.entity.OptionSet;
import com.danaga.repository.product.InterestRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InterestDaoImpl implements InterestDao{

	private final InterestRepository repository;
	
	@Override
	public Boolean isInterested(InterestDto dto) {
		return repository.existsByMemberIdAndOptionSetId(dto.getMemberId(), dto.getOptionSetId());
	}

	@Override
	public InterestDto save(InterestDto dto) {
		Interest savedEntity = repository.save(dto.toEntity(dto));
		return new InterestDto(savedEntity);
	}

	@Override
	public void delete(InterestDto dto) {
		repository.deleteByOptionSetIdAndMemberId(dto.getOptionSetId(),dto.getMemberId());
	}

	@Override
	public void deleteAll(Long memberId) {
		repository.deleteByMemberId(memberId);		
	}

}
