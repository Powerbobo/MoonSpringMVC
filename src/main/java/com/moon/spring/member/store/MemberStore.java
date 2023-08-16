package com.moon.spring.member.store;

import org.apache.ibatis.session.SqlSession;

import com.moon.spring.member.domain.Member;

public interface MemberStore {
	/**
	 * 회원가입 Store
	 * @param session
	 * @param member
	 * @return
	 */
	int insertMember(SqlSession session, Member member);

	/**
	 * 로그인 Stroe
	 * @param session
	 * @param member
	 * @return
	 */
	Member checkMemberLogin(SqlSession session, Member member);
	

}
