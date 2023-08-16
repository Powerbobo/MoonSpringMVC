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
}
