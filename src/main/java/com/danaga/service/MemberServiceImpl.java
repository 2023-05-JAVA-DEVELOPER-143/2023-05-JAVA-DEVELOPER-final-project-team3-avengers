package com.danaga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.MemberDao;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;
import com.danaga.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;

	public List<Member> members() {
		return memberDao.findMembers();
	}

	public Member getMemberBy(String value) throws Exception {
		return memberDao.findMember(value);
	}

	
	public Member createMember(Member member) throws Exception {
		// 1.아이디중복체크
		if (memberDao.existedMemberBy(member.getMemberId())) {
			// 아이디중복
			throw new Exception(member.getMemberId() + " 는 이미 존재하는 아이디 입니다.");
		} else if (memberDao.existedMemberBy(member.getMemberEmail())) {
			throw new Exception(member.getMemberId() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberBy(member.getMemberPhoneNo())) {
			throw new Exception(member.getMemberId() + " 는 이미 등록된 번호 입니다.");
		}
		// 아이디안중복
		// 2.회원가입
		return memberDao.insert(member);
	}
	
	public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception {
		if (memberDao.existedMemberBy(memberUpdateDto.getMemberEmail())) {
			throw new Exception(memberUpdateDto.getMemberId() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberBy(memberUpdateDto.getMemberPhoneNo())) {
			throw new Exception(memberUpdateDto.getMemberId() + " 는 이미 등록된 번호 입니다.");
		}
		return MemberUpdateDto.toDto(memberDao.insert(Member.toUpdateEntity(memberUpdateDto)));
	}

	public void deleteMember(String memberId) throws Exception {
		memberDao.delete(memberId);
	}
	// 중복체크
	public boolean isDuplicate(String value) throws Exception {
		return memberDao.existedMemberBy(value);
	}
	
//	public boolean login(String memberId, String memberPassword) throws Exception {
//		Member findMember = memberRepository.findByMemberId(memberId);
//		Optional<Member> findOptionalMember = memberRepository.findById(findMember.getMemberIdCode());
//		if (findOptionalMember.isPresent()) {
//			
//		}
//	}
}
