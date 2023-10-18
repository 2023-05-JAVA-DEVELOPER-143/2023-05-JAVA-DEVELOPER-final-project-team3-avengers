package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.danaga.entity.Member;
import com.danaga.repository.MemberRepository;

public class MemberDaoImpl implements MemberDao{
	@Autowired
	private MemberRepository memberRepository;
	
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	public Member findMemberById(String memberId) {
		return memberRepository.findByMemberId(memberId);
	}
	public Member findByMemberEmail(String MemberEmail) {
		return memberRepository.findByMemberEmail(MemberEmail);
	}
	public Member findByMemberPhoneNo(String MemberPhoneNo) {
		return memberRepository.findByMemberPhoneNo(MemberPhoneNo);
	}
	
	public Member insert(Member member) {
		return memberRepository.save(member);
	}
	
	public Member update(Member updateMember) throws Exception {
		Member findMember = memberRepository.findByMemberId(updateMember.getMemberId());
		Optional<Member> findOptionalMember = memberRepository.findById(findMember.getMemberIdCode());
		Member updatedMember = null;
		if (findOptionalMember.isPresent()) {
			Member member = findOptionalMember.get();
			member.setMemberPassword(updateMember.getMemberPassword());
			member.setMemberEmail(updateMember.getMemberEmail());
			member.setMemberName(updateMember.getMemberName());
			member.setMemberNickname(updateMember.getMemberNickname());
			member.setMemberAddress(updateMember.getMemberAddress());
			member.setMemberPhoneNo(updateMember.getMemberPhoneNo());
			updatedMember = memberRepository.save(member);
		} else {
			throw new Exception("존재하지 않는 회원입니다");
		}
		return updatedMember;
	}
}
