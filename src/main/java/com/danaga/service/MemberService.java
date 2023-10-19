package com.danaga.service;

import java.util.List;

import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;

public interface MemberService {
	public List<Member> members();
	public Member getMemberBy(String value) throws Exception;
	public Member createMember(Member member) throws Exception;
	public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception;
	public void deleteMember(String memberId) throws Exception;
	public boolean isDuplicate(String value) throws Exception;
}
