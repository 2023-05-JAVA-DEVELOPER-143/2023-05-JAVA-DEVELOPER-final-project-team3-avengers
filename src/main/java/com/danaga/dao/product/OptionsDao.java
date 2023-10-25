package com.danaga.dao.product;

import java.util.List;

import com.danaga.dto.product.OptionSaveDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.repository.product.OptionNamesValues;

public interface OptionsDao {
	//List<Options> findOptionsByOptionSet(OptionSet optionSet);
	
//	List<OptionNamesOnly> findOptionNamesByCategoryId(Long id); 
//	List<OptionValuesOnly> findOptionValuesByCategoryId(Long id);
	
	Options findById(Long id);
	
	List<Options> findOptionsByOptionSetId(Long optionSetId);
	
	Options save(OptionSaveDto dto);
	
	void deleteAllByOptionSetId(Long optionSetId);
	
	void deleteById(Long id);
	Options update(OptionSaveDto dto);
	List<OptionNamesValues> findOptionNameValueMapByCategoryId(Long id);
	
	
}
