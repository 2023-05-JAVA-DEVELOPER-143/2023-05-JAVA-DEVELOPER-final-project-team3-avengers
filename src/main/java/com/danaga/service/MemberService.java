package com.danaga.service;

import java.util.List;

import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;

public interface MemberService {
	public List<Member> getMembers();
	public Member getMemberBy(String value) throws Exception;
	public Member joinMember(Member member) throws Exception;
	public MemberInsertGuestDto joinGuest(MemberInsertGuestDto memberInsertGuestDto) throws Exception;
	public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception;
	public void deleteMember(String value) throws Exception;
	public boolean isDuplicate(String value) throws Exception;
	public boolean login(String userName, String password) throws Exception;
	public void updateGrade(Member member, int gradePoint);
}
