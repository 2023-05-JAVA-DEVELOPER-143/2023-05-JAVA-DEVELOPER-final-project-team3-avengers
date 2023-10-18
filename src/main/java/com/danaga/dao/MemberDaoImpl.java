package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Member;
import com.danaga.repository.MemberRepository;

@Repository
public class MemberDaoImpl implements MemberDao{
	@Autowired
	private MemberRepository memberRepository;
	
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	public Member findByMemberId(String memberId) {
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
	
	public void delete(Member deleteMember) throws Exception {
		Member findMember = memberRepository.findByMemberId(deleteMember.getMemberId());
		Optional<Member> findOptionalMember = memberRepository.findById(findMember.getMemberIdCode());
		if (findOptionalMember.isPresent()) {
			memberRepository.delete(findMember);
		} else {
			throw new Exception("존재하지 않는 회원입니다");
		}
	}
	
	public boolean existedMemberById(String memberId) throws Exception {
		Member findMember = memberRepository.findByMemberId(memberId);
		Optional<Member> findOptionalMember = memberRepository.findById(findMember.getMemberIdCode());
		if (findOptionalMember.isPresent()) {
			return false;
		}
		return true;
	}
	public boolean existedMemberByEmail(String memberEmail) throws Exception {
		Member findMember = memberRepository.findByMemberEmail(memberEmail);
		Optional<Member> findOptionalMember = memberRepository.findById(findMember.getMemberIdCode());
		if (findOptionalMember.isPresent()) {
			return false;
		}
		return true;
	}
	public boolean existedMemberByPhoneNo(String memberPhoneNo) throws Exception {
		Member findMember = memberRepository.findByMemberPhoneNo(memberPhoneNo);
		Optional<Member> findOptionalMember = memberRepository.findById(findMember.getMemberIdCode());
		if (findOptionalMember.isPresent()) {
			return false;
		}
		return true;
	}
	
}
