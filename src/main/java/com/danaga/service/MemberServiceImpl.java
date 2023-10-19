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

	@Autowired
	private MemberRepository memberRepository;

	public List<Member> members() {
		return memberDao.findMembers();
	}

	public Member getMemberBy(String value) throws Exception {
		return memberDao.findMember(value);
	}

	public Member joinMember(Member member) throws Exception {
		// 1.아이디중복체크
		if (memberDao.existedMemberBy(member.getUserName())) {
			// 아이디중복
			//throw new Exception(member.getUserName() + " 는 이미 존재하는 아이디 입니다.");
		} else if (memberDao.existedMemberBy(member.getEmail())) {
			throw new Exception(member.getEmail() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberBy(member.getPhoneNo())) {
			throw new Exception(member.getPhoneNo() + " 는 이미 등록된 번호 입니다.");
		}
		// 아이디안중복
		// 2.회원가입
		return memberDao.insert(member);
	}

	public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception {
		if (memberDao.existedMemberBy(memberUpdateDto.getEmail())) {
			throw new Exception(memberUpdateDto.getEmail() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberBy(memberUpdateDto.getPhoneNo())) {
			throw new Exception(memberUpdateDto.getPhoneNo() + " 는 이미 등록된 번호 입니다.");
		}
		return MemberUpdateDto.toDto(memberDao.insert(Member.toUpdateEntity(memberUpdateDto)));
	}

	public void deleteMember(String userName) throws Exception {
		memberDao.delete(userName);
	}

	// 중복체크
	public boolean isDuplicate(String value) throws Exception {
		return memberDao.existedMemberBy(value);
	}

	public boolean login(String userName, String password) throws Exception {
		Optional<Member> findOptionalMember = memberRepository.findByUserName(userName);
		if (findOptionalMember.isEmpty()) {
			throw new Exception(userName + " 는 존재하지않는 아이디입니다.");
		} else if (findOptionalMember.isPresent()) {
			throw new Exception("패쓰워드가 일치하지않습니다.");
		}
		return true;
	}
}
