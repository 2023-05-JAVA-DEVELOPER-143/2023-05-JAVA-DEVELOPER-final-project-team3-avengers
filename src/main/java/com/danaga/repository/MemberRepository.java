package com.danaga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Optional<Member> findByUserName(String MemberId);
	public Optional<Member> findByEmail(String MemberEmail);
	public Optional<Member> findByPhoneNo(String MemberPhoneNo);
}
