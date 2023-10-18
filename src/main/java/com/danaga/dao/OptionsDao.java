package com.danaga.dao;

import java.util.List;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

public interface OptionsDao {
	List<Options> findOptionsByOptionSet(OptionSet optionSet);
}
