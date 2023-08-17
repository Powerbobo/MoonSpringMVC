package com.moon.spring.notice.service;

import java.util.List;

import com.moon.spring.notice.domain.Notice;

public interface NoticeService {

	/**
	 * 공지가항 등록 Service
	 * @param notice
	 * @return int
	 */
	int insertNotice(Notice notice);
	
	/**
	 * 공지사항 목록 조회 Service
	 * @return List
	 */
	List<Notice> selectNoticeList();

}
