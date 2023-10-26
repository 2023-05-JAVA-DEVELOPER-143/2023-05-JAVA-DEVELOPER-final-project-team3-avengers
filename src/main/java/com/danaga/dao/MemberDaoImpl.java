package com.danaga.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danaga.entity.Member;
import com.danaga.exception.MemberNotFoundException;
import com.danaga.repository.MemberRepository;

@Repository
public class MemberDaoImpl implements MemberDao {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findMember(String value) throws Exception, MemberNotFoundException {
		if (value.contains("@")) {
			if (memberRepository.findByEmail(value).isPresent()) {
				return memberRepository.findByEmail(value).get();
			}
			throw new MemberNotFoundException("해당 이메일로 찾을 수 없습니다");
		} else if (value.contains("-")) {
			if (memberRepository.findByPhoneNo(value).isPresent()) {
				return memberRepository.findByPhoneNo(value).get();
			}
			throw new MemberNotFoundException("해당 번호로 찾을 수 없습니다");
		} else {
			if (memberRepository.findByUserName(value).isPresent()) {
				return memberRepository.findByUserName(value).get();
			}
			throw new MemberNotFoundException("해당 아이디로 찾을 수 없습니다");
		}
	}
	

	public Member insert(Member member) {
		return memberRepository.save(member);
	}

	public Member update(Member updateMember) throws Exception {
		Optional<Member> findOptionalMember = memberRepository.findById(updateMember.getId());
		Member updatedMember = null;
		if (findOptionalMember.isPresent()) {
			Member member = findOptionalMember.get();
			member.setPassword(updateMember.getPassword());
			member.setNickname(updateMember.getNickname());
			member.setAddress(updateMember.getAddress());
			member.setGradePoint(updateMember.getGradePoint());
			member.setGrade(updateMember.getGrade());
			updatedMember = memberRepository.save(member);
		} else {
			throw new MemberNotFoundException("존재하지 않는 회원입니다");
		}
		return updatedMember;
	}

	public void delete(String value) throws Exception, MemberNotFoundException {
		if (memberRepository.findByUserName(value).isPresent()) {
			memberRepository.delete(memberRepository.findByUserName(value).get());
		} else if (memberRepository.findByEmail(value).isPresent()) {
			memberRepository.delete(memberRepository.findByEmail(value).get());
		} else {
			throw new MemberNotFoundException("존재하지 않는 회원입니다");
		}
	}

	public boolean existedMemberBy(String value) throws Exception {
		if (value.contains("@")) {
			if (memberRepository.findByEmail(value).isPresent()) {
				return true;
			}
		} else if (value.contains("-")) {
			if (memberRepository.findByPhoneNo(value).isPresent()) {
				return true;
			}
		} else {
			if (memberRepository.findByUserName(value).isPresent()) {
				return true;
			}
		}
		return false;
	}

}
