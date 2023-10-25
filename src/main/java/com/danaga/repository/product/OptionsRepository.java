package com.danaga.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

public interface OptionsRepository extends JpaRepository<Options, Long> {
//	List<Options> findByName(String name);
//	List<Options> findByValue(String value);
//	List<Options> findByOptionSet(OptionSet optionset);
	
//	List<OptionNamesOnly> findDistinctNameByOptionSet_Product_CategorySets_Category_Id(Long id);
//	List<OptionValuesOnly> findDistinctValueByOptionSet_Product_CategorySets_Category_Id(Long id);
	List<OptionNamesValues> findDistinctByOptionSet_Product_CategorySets_Category_Id(Long id);
	
	
	List<Options> findAllByOptionSetId(Long optionSetId);
	
	void deleteAllByOptionSetId(Long optionSetId);
}
