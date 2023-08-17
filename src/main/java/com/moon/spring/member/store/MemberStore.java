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
	 * 로그인 Store
	 * @param session
	 * @param member
	 * @return
	 */
	Member checkMemberLogin(SqlSession session, Member member);
	
	/**
	 * 마이페이지 조회 Store
	 * @param session
	 * @param memberId
	 * @return
	 */
	Member getMemberById(SqlSession session, String memberId);

	/**
	 * 마이페이지 수정 Store
	 * @param session
	 * @param member
	 * @return
	 */
	int modifyMember(SqlSession session, Member member);

	/**
	 * 회원 탈퇴 Store, update로 할 예정
	 * @param session
	 * @param memberId
	 * @return
	 */
	int deleteMember(SqlSession session, String memberId);

}
