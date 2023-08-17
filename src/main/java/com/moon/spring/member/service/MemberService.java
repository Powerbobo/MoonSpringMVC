package com.moon.spring.member.service;

import com.moon.spring.member.domain.Member;

public interface MemberService {

	/**
	 * 회원가입 Service
	 * @param member
	 * @return
	 */
	int insertMember(Member member);
	
	/**
	 * 로그인 Service
	 * @param member
	 * @return
	 */
	Member checkMemberLogin(Member member);
	
	/**
	 * 마이페이지 조회 Service
	 * @param memberId
	 * @return
	 */
	Member getMemberById(String memberId);
	
	/**
	 * 마이페이지 수정 Service
	 * @param member
	 * @return
	 */
	int modifyMember(Member member);

	/**
	 * 회원탈퇴 Service, update로 할 예정
	 * @param memberId
	 * @return
	 */
	int deleteMember(String memberId);
}
