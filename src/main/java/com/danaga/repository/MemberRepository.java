package com.danaga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danaga.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findByMemberId(String MemberId);
	public Member findByMemberEmail(String MemberEmail);
	public Member findByMemberPhoneNo(String MemberPhoneNo);
}
