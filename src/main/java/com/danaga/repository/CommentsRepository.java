package com.danaga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long>{

	List<Comments> findByBoard_Id(Long boardId);
}
