package com.moon.spring.notice.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.moon.spring.notice.domain.Notice;

public interface NoticeStore {

	/**
	 * 공지가항 등록 Store
	 * @param session
	 * @param notice
	 * @return int
	 */
	int insertNotice(SqlSession session, Notice notice);
	
	/**
	 * 공지사항 목록 조회 Store
	 * @param session
	 * @return
	 */
	List<Notice> selectNoticeList(SqlSession session);


}
