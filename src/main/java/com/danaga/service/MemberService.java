package com.danaga.service;

import java.util.List;

import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.MemberResponseDto;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;

import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {
	public List<MemberResponseDto> getMembers();
	public MemberResponseDto getMemberBy(String value) throws Exception;
	public MemberResponseDto joinMember(MemberResponseDto memberResponseDto) throws Exception;
	public MemberResponseDto joinGuest(MemberInsertGuestDto memberInsertGuestDto) throws Exception;
	public MemberResponseDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception;
	public void deleteMember(String value) throws Exception;
	public boolean isDuplicate(String value) throws Exception;
	public boolean login(String userName, String password) throws Exception;
	public void updateGrade(Member member, int gradePoint) throws Exception;
	Long findIdByUsername(String username) throws Exception;
}
