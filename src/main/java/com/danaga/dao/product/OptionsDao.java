package com.danaga.dao.product;

import java.util.List;

import com.danaga.dto.product.OptionSaveDto;
import com.danaga.dto.product.OptionUpdateDto;
import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.exception.product.FoundNoObjectException.FoundNoOptionsException;
import com.danaga.repository.product.OptionNamesValues;

public interface OptionsDao {
	
	Options findById(Long id) throws FoundNoOptionsException;
	
	List<Options> findOptionsByOptionSetId(Long optionSetId);
	
	void deleteAllByOptionSetId(Long optionSetId) throws FoundNoOptionsException;
	
	void deleteById(Long id) throws FoundNoOptionsException;
	Options update(OptionUpdateDto dto) throws FoundNoOptionsException;
	List<OptionNamesValues> findOptionNameValueMapByCategoryId(Long id);

	Options save(OptionSaveDto dto);
	
	
}
