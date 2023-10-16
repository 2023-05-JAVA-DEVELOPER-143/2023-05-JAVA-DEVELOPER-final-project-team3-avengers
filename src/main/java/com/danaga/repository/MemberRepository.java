package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
