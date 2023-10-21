package com.danaga.dao;

import java.util.List;

import com.danaga.dto.OptionCreateDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.repository.OptionNamesOnly;
import com.danaga.repository.OptionValuesOnly;

public interface OptionsDao {
	//List<Options> findOptionsByOptionSet(OptionSet optionSet);
	
	List<OptionNamesOnly> findOptionNamesByCategoryId(Long id); 
	List<OptionValuesOnly> findOptionValuesByCategoryId(Long id);
	
	Options findById(Long id);
	
	List<Options> findOptionsByOptionSetId(Long optionSetId);
	
	Options save(OptionCreateDto dto);
	
	void deleteAllByOptionSetId(Long optionSetId);
	
	void deleteById(Long id);
	
	
}
