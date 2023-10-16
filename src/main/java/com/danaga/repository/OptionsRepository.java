package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

public interface OptionsRepository extends JpaRepository<Options, Long> {
	List<Options> findByName(String name);
	List<Options> findByValue(String value);
	List<Options> findByOptionset(OptionSet optionset);
}
