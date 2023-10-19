package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.OptionSet;
import com.danaga.repository.OptionSetRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OptionSetDaoImpl implements OptionSetDao{

	private final OptionSetRepository optionSetRepository;
//	@Override
//	public List<OptionSet> findByAllId(Long id) {
//		final List<OptionSet> optionSets = optionSetRepository.findByAllId(id);
//		return optionSets;
//	}
	@Override
	public OptionSet findById(Long id) {
		return optionSetRepository.findById(id).get();
	}

	
}

//		final Optional<OptionSet> optionalOptionSets =optionSetRepository.findById(id);
//		optionalOptionSets.ifPresent(optionset ->{
//		});