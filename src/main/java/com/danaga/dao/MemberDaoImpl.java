package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Member;
import com.danaga.repository.MemberRepository;

@Repository
public class MemberDaoImpl implements MemberDao {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findMember(String value) throws Exception {

		if (value.contains("@")) {
			if (memberRepository.findByMemberId(value).isPresent()) {
				return memberRepository.findByMemberId(value).get();
			}
			throw new Exception("해당 이메일로 찾을 수 없습니다");
		} else if (value.contains("-")) {
			if (memberRepository.findByMemberId(value).isPresent()) {
				return memberRepository.findByMemberId(value).get();
			}
			throw new Exception("해당 번호로 찾을 수 없습니다");
		} else {
			if (memberRepository.findByMemberId(value).isPresent()) {
				return memberRepository.findByMemberId(value).get();
			}
			throw new Exception("해당 아이디로 찾을 수 없습니다");
		}
	}

	public Member insert(Member member) {
		return memberRepository.save(member);
	}

	public Member update(Member updateMember) throws Exception {
		Optional<Member> findOptionalMember = memberRepository.findByMemberId(updateMember.getUserName());
		Member updatedMember = null;
		if (findOptionalMember.isPresent()) {
			Member member = findOptionalMember.get();
			member.setPassword(updateMember.getPassword());
			member.setEmail(updateMember.getEmail());
			member.setName(updateMember.getName());
			member.setNickname(updateMember.getNickname());
			member.setAddress(updateMember.getAddress());
			member.setPhoneNo(updateMember.getPhoneNo());
			updatedMember = memberRepository.save(member);
		} else {
			throw new Exception("존재하지 않는 회원입니다");
		}
		return updatedMember;
	}

	public void delete(String memberId) throws Exception {
		if (memberRepository.findByMemberId(memberId).isPresent()) {
			memberRepository.delete(memberRepository.findByMemberId(memberId).get());
		} else {
			throw new Exception("존재하지 않는 회원입니다");
		}
	}

	public boolean existedMemberBy(String value) throws Exception {
		if (value.contains("@")) {
			if (memberRepository.findByMemberEmail(value).isPresent()) {
				return false;
			}
			return true;
		} else if (value.contains("-")) {
			if (memberRepository.findByMemberPhoneNo(value).isPresent()) {
				return false;
			}
			return true;
		} else {
			if (memberRepository.findByMemberId(value).isPresent()) {
				return false;
			}
			return true;
		}
	}
}
