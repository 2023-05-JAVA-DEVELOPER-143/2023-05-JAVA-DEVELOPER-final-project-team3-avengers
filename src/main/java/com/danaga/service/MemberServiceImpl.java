package com.danaga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danaga.dao.MemberDao;
import com.danaga.dto.MemberInsertGuestDto;
import com.danaga.dto.MemberResponseDto;
import com.danaga.dto.MemberUpdateDto;
import com.danaga.entity.Member;
import com.danaga.exception.PasswordMismatchException;
import com.danaga.exception.ExistedMemberException;
import com.danaga.exception.MemberNotFoundException;
import com.danaga.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberRepository memberRepository;

	public List<MemberResponseDto> getMembers() {
		List<Member> members = memberDao.findMembers();
		List<MemberResponseDto> responseMembers = new ArrayList<>();
		for (Member member : members) {
			responseMembers.add(MemberResponseDto.toDto(member));
		}
		return responseMembers;
	}

	public MemberResponseDto getMemberBy(String value) throws Exception {
		return MemberResponseDto.toDto(memberDao.findMember(value));
	}
	@Transactional
	public MemberResponseDto joinMember(MemberResponseDto memberResponseDto) throws Exception, ExistedMemberException {
		// 1.아이디중복체크
		if (memberDao.existedMemberBy(memberResponseDto.getUserName())) {
			// 아이디중복
			throw new ExistedMemberException(memberResponseDto.getUserName() + " 는 이미 존재하는 아이디 입니다.");
		} else if (memberDao.existedMemberBy(memberResponseDto.getEmail())) {
			throw new ExistedMemberException(memberResponseDto.getEmail() + " 는 이미 등록된 이메일 입니다.");
		} else if (memberDao.existedMemberBy(memberResponseDto.getPhoneNo())
				&& (memberDao.findMember(memberResponseDto.getPhoneNo()).getRole().equals("Guest"))) {
			memberResponseDto.setRole("Member");
			return MemberResponseDto.toDto((memberDao.update(Member.toResponseEntity(memberResponseDto))));
		} else if (memberDao.existedMemberBy(memberResponseDto.getPhoneNo())) {
			throw new ExistedMemberException(memberResponseDto.getPhoneNo() + " 는 이미 등록된 번호 입니다.");
		}
		// 아이디안중복
		// 2.회원가입
		return MemberResponseDto.toDto(memberDao.insert(Member.toResponseEntity(memberResponseDto)));
	}

	@Transactional
	public MemberInsertGuestDto joinGuest(MemberInsertGuestDto memberInsertGuestDto) throws Exception {
		if (!memberDao.existedMemberBy(memberInsertGuestDto.getPhoneNo())) {
			return MemberInsertGuestDto.toDto(memberDao.insert(Member.toGuestEntity(memberInsertGuestDto)));
		} else {
			return MemberInsertGuestDto.toDto(memberDao.findMember(Member.toGuestEntity(memberInsertGuestDto).getPhoneNo()));
		}
	}
	@Transactional
	public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) throws Exception, ExistedMemberException {
		Member originalMember = memberDao.findMember(memberUpdateDto.getUserName());
		if (originalMember.getPhoneNo().equals(memberUpdateDto.getPhoneNo())
				&& originalMember.getEmail().equals(memberUpdateDto.getEmail())) {
			// 이메일 x 번호 x

		} else if (originalMember.getEmail().equals(memberUpdateDto.getEmail())) {
			// 이메일 x 번호 o
			if (memberDao.existedMemberBy(memberUpdateDto.getPhoneNo())) {
				throw new ExistedMemberException(memberUpdateDto.getPhoneNo() + " 는 이미 등록된 번호 입니다.");
			}
		} else if (originalMember.getPhoneNo().equals(memberUpdateDto.getPhoneNo())) {
			// 이메일 o 번호 x
			if (memberDao.existedMemberBy(memberUpdateDto.getEmail())) {
				throw new ExistedMemberException(memberUpdateDto.getEmail() + " 는 이미 등록된 이메일 입니다.");
			}
		} else {
			// 이메일 o 번호 o
			if (memberDao.existedMemberBy(memberUpdateDto.getEmail())) {
				throw new ExistedMemberException(memberUpdateDto.getEmail() + " 는 이미 등록된 이메일 입니다.");
			} else if (memberDao.existedMemberBy(memberUpdateDto.getPhoneNo())) {
				throw new ExistedMemberException(memberUpdateDto.getPhoneNo() + " 는 이미 등록된 번호 입니다.");
			}
		}
		return MemberUpdateDto.toDto(memberDao.insert(Member.toUpdateEntity(memberUpdateDto)));
	}
	@Transactional
	public void deleteMember(String value) throws Exception {
		memberDao.delete(value);
	}

	// 중복체크
	public boolean isDuplicate(String value) throws Exception {
		return memberDao.existedMemberBy(value);
	}

	public boolean login(String userName, String password) throws Exception {
		// 비회원 세션 카트에서 로그인하면 세션 카트를 로그인한 멤버의 카트DB에 담게 구현?
		Optional<Member> findOptionalMember = memberRepository.findByUserName(userName);
		if (findOptionalMember.isEmpty()) {
			throw new MemberNotFoundException(userName + " 는 존재하지않는 아이디입니다.");
		} else if (findOptionalMember.isPresent()) {
			if (password.equals(findOptionalMember.get().getPassword())) {
			} else {
				throw new PasswordMismatchException("패쓰워드가 일치하지않습니다.");
			}
		}
		return true;
	}
	@Transactional
	@Override
	public void updateGrade(Member member, int gradePoint) {
		member.setGradePoint(member.getGradePoint() + gradePoint);
		if (member.getGradePoint() <= 1000) {
			/* Rookie Bronze, Silver, Gold, Platinum, Diamond 결제 가격의 1%가 등급 포인트로 쌓임
			등급 점수   Rookie : 0 ~ 1000
			Bronze : 1001 ~ 5000
			Silver : 5001 ~ 10000
			Gold : 10001 ~ 20000
			Platinum : 20001 ~ 35000
			Diamond : 35001 ~  */
			member.setGrade("Rookie");
		} else if (member.getGradePoint() > 1000 && member.getGradePoint() <= 5000) {
			member.setGrade("Silver");
		} else if (member.getGradePoint() > 5000 && member.getGradePoint() <= 10000) {
			member.setGrade("Gold");
		} else if (member.getGradePoint() > 10000 && member.getGradePoint() <= 20000) {
			member.setGrade("Platinum");
		} else if (member.getGradePoint() > 20000 && member.getGradePoint() <= 35000) {
			member.setGrade("Gold");
		} else if (member.getGradePoint() > 35000) {
			member.setGrade("Diamond");
		}
		memberRepository.save(member);

	}
}
