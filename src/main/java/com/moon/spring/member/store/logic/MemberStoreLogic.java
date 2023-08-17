package com.moon.spring.member.store.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.moon.spring.member.domain.Member;
import com.moon.spring.member.store.MemberStore;

@Repository
public class MemberStoreLogic implements MemberStore{

	// 회원가입 (데이터 추가)
	@Override
	public int insertMember(SqlSession session, Member member) {
		int result = session.insert("MemberMapper.insertMember", member);
		return result;
	}
	
	// 로그인 (데이터 조회)
	@Override
	public Member checkMemberLogin(SqlSession session, Member member) {
		Member mOne = session.selectOne("MemberMapper.checkMemberLogin", member);
		return mOne;
	}

	// 마이페이지 (데이터 조회)
	@Override
	public Member getMemberById(SqlSession session, String memberId) {
		Member member = session.selectOne("MemberMapper.getMemberById", memberId);
		return member;
	}
	
	// 마이페이지 수정
	@Override
	public int modifyMember(SqlSession session, Member member) {
		int result = session.update("MemberMapper.modifyMember", member);
		return result;
	}

	// 회원 탈퇴 (update)
	@Override
	public int deleteMember(SqlSession session, String memberId) {
		int result = session.update("MemberMapper.deleteMember", memberId);
		return result;
	}


}
