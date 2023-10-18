package com.danaga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.MemberDao;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberDao;

	public List<Member> members() {
		return memberDao.findMembers();
	}

	public Member getByMemberId(String memberId) {
		return memberDao.findByMemberId(memberId);
	}

	public Member getByMemberEmail(String memberEmail) {
		return memberDao.findByMemberEmail(memberEmail);
	}

	public Member getByMemberPhoneNo(String memberPhoneNo) {
		return memberDao.findByMemberPhoneNo(memberPhoneNo);
	}

	public Member createMember(Member member) throws Exception {
		// 1.아이디중복체크
		if (memberDao.existedMemberById(member.getMemberId())) {
			// 아이디중복
			throw new Exception(member.getMemberId() + " 는 이미 존재하는 아이디 입니다.");
		} else if (memberDao.existedMemberByEmail(member.getMemberEmail())) {
			throw new Exception(member.getMemberId() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberByPhoneNo(member.getMemberPhoneNo())) {
			throw new Exception(member.getMemberId() + " 는 이미 등록된 번호 입니다.");
		}
		// 아이디안중복
		// 2.회원가입
		return memberDao.insert(member);
	}

	public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception {
		if (memberDao.existedMemberByEmail(memberUpdateDto.getMemberEmail())) {
			throw new Exception(memberUpdateDto.getMemberId() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberByPhoneNo(memberUpdateDto.getMemberPhoneNo())) {
			throw new Exception(memberUpdateDto.getMemberId() + " 는 이미 등록된 번호 입니다.");
		}
		return MemberUpdateDto.toDto(memberDao.insert(Member.toEntity(memberUpdateDto)));
	}
	public void deleteMember(Member deleteMember) {
		
	}
}
