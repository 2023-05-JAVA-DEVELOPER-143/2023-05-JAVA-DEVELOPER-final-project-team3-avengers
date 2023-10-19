package com.danaga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Optional<Member> findByMemberId(String MemberId);
	public Optional<Member> findByMemberEmail(String MemberEmail);
	public Optional<Member> findByMemberPhoneNo(String MemberPhoneNo);
}
