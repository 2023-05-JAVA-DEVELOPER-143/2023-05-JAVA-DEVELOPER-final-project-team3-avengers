package com.danaga.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;
import com.danaga.repository.OptionsRepository;
@Repository
public class OptionsDaoImpl implements OptionsDao{

	@Autowired
	private OptionsRepository optionsRepository;
	@Override
	public List<Options> findOptionsByOptionSet(OptionSet optionSet) {
		List<Options> options = optionsRepository.findByOptionSet(optionSet);
		return options;
	}

}
