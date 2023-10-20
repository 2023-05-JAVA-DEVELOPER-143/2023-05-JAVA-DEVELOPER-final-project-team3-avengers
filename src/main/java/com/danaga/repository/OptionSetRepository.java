package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.OptionSet;

public interface OptionSetRepository extends JpaRepository<OptionSet, Long>{
	List<OptionSet> findByInterests_MemberId(Long id);
	
	
}
