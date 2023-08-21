package com.moon.spring.notice.service;

import java.util.List;
import java.util.Map;

import com.moon.spring.notice.domain.Notice;
import com.moon.spring.notice.domain.PageInfo;

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
	List<Notice> selectNoticeList(PageInfo pInfo);

	/**
	 * 공지사항 전체 갯수 조회 Service
	 * @return
	 */
	int getListCount();

	/**
	 * 공지사항 검색 Service
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticeByAll(String searchKeyword);

	/**
	 * 공지사항 작성자로 검색 Service
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByWriter(String searchKeyword);

	/**
	 * 공지사항 제목으로 검색 Service
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByTitle(String searchKeyword);

	/**
	 * 공지사항 내용으로 검색
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByContent(String searchKeyword);

	/**
	 * 공지사항 조건에 따라 키워드로 검색 Service
	 * @param searchCondition all, writer,title, content
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByKeyword(PageInfo pInfo, Map<String, String> paramMap);

	/**
	 * 공지사항 검색 게시물 전체 갯수(검색 한 게시물 페이징처리) Service
	 * @param paramMap
	 * @return int
	 */
	int getListCount(Map<String, String> paramMap);


}
