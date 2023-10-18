package com.danaga.dao;

import java.util.List;

import com.danaga.entity.OptionSet;

public interface OptionSetDao {
	List<OptionSet> findByAllId(Long id);

	OptionSet findById(Long id);
}
