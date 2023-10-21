package com.danaga.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.dto.OptionCreateDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.repository.OptionNamesOnly;
import com.danaga.repository.OptionValuesOnly;
import com.danaga.repository.OptionsRepository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class OptionsDaoImpl implements OptionsDao{

	private final OptionsRepository optionsRepository;
//	@Override
//	public List<Options> findOptionsByOptionSet(OptionSet optionSet) {
//		List<Options> options = optionsRepository.findByOptionSet(optionSet);
//		return options;
//	}

	@Override
	public List<OptionNamesOnly> findOptionNamesByCategoryId(Long id) {
		return optionsRepository.findDistinctNameByOptionSet_Product_CategorySets_Category_Id(id);
	}

	@Override
	public List<OptionValuesOnly> findOptionValuesByCategoryId(Long id) {
		return optionsRepository.findDistinctValueByOptionSet_Product_CategorySets_Category_Id(id);
	}

	@Override
	public Options findById(Long id) {
		return optionsRepository.findById(id).get();
	}

	@Override
	public List<Options> findOptionsByOptionSetId(Long optionSetId) {
		return optionsRepository.findAllByOptionSetId(optionSetId);
	}
	
	@Override
	public Options save(OptionCreateDto dto) {
		return optionsRepository.save(dto.toEntity());
	}

	@Override
	public void deleteAllByOptionSetId(Long optionSetId) {
		optionsRepository.deleteAllByOptionSetId(optionSetId);
	}

	@Override
	public void deleteById(Long id) {
		optionsRepository.deleteById(id);
	}

}
