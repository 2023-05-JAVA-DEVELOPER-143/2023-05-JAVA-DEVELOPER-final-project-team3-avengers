package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Event;


public interface EventRepository extends JpaRepository<Event, Long>{

}
