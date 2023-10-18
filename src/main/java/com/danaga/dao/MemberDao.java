package com.danaga.dao;

import java.util.List;

import com.danaga.entity.Member;

public interface MemberDao {
	public List<Member> findMembers();
	public Member findByMemberId(String memberId);
	public Member findByMemberEmail(String MemberEmail);
	public Member findByMemberPhoneNo(String MemberPhoneNo);
	public Member insert(Member member);
	public Member update(Member updateMember) throws Exception;
	public void delete(Member deleteMember) throws Exception;
	public boolean existedMemberById(String memberId) throws Exception;
	public boolean existedMemberByEmail(String memberEmail) throws Exception;
	public boolean existedMemberByPhoneNo(String memberPhoneNo) throws Exception;
}
