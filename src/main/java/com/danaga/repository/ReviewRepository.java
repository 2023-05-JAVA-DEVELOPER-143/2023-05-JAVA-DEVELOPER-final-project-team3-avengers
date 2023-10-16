package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
