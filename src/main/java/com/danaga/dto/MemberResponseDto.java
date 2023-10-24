package com.danaga.dto;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.danaga.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MemberResponseDto {
	private Long id;
	private String userName;
	private String password;
	private String email;
	private String name;
	private String nickname;
	private String address;
	private String phoneNo;
	private LocalDateTime joinDate;
	private Date birthday;
	private String role;
	private String grade;
	private Integer gradePoint;
	
	public static MemberResponseDto toDto(Member entity) {
    	return MemberResponseDto.builder()
    			.id(entity.getId())
    			.userName(entity.getUserName())
    			.password(entity.getPassword())
    			.email(entity.getEmail())
    			.name(entity.getName())
    			.nickname(entity.getNickname())
    			.address(entity.getAddress())
    			.phoneNo(entity.getPhoneNo())
    			.joinDate(entity.getJoinDate())
    			.birthday(entity.getBirthday())
    			.role(entity.getRole())
    			.grade(entity.getGrade())
    			.gradePoint(entity.getGradePoint())
    			.build();
    	
    }
}
