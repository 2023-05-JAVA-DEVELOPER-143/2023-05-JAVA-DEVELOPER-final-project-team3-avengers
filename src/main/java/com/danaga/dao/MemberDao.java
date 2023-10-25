package com.danaga.dao;

import java.util.List;

import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.MemberResponseDto;
import com.danaga.entity.Member;

public interface MemberDao {
	public List<Member> findMembers();
	public Member findMember(String value) throws Exception;
	public Member insert(Member member) throws Exception;
	public Member update(Member member) throws Exception;
	public void delete(String memberId) throws Exception;
	public boolean existedMemberBy(String memberId) throws Exception;
}
