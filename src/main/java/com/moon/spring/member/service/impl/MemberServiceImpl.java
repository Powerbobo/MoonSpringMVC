package com.moon.spring.member.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.spring.member.domain.Member;
import com.moon.spring.member.service.MemberService;
import com.moon.spring.member.store.MemberStore;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSession session;
	
	@Autowired
	private MemberStore mStore;
	
	// 회원가입 (데이터 추가)
	@Override
	public int insertMember(Member member) {
		int result = mStore.insertMember(session, member);
		return result;
	}
	
	// 로그인 (데이터 조회)
	@Override
	public Member checkMemberLogin(Member member) {
		Member mOne = mStore.checkMemberLogin(session, member);
		return mOne;
	}

	// 마이페이지 조회 (데이터 조회)
	@Override
	public Member getMemberById(String memberId) {
		Member member = mStore.getMemberById(session, memberId);
		return member;
	}
	
	// 마이페이지 수정
	@Override
	public int modifyMember(Member member) {
		int result = mStore.modifyMember(session, member);
		return result;
	}
	
	// 회원 탈퇴 (업데이트)
	@Override
	public int deleteMember(String memberId) {
		int result = mStore.deleteMember(session, memberId);
		return result;
	}
}
