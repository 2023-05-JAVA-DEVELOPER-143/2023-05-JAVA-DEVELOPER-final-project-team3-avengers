package com.danaga.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Statistic;


public interface StatisticRepository extends JpaRepository<Statistic, Date>{
	

}
