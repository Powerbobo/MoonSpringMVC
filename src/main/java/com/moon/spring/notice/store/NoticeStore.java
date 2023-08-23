package com.moon.spring.notice.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.moon.spring.notice.domain.Notice;
import com.moon.spring.notice.domain.PageInfo;

public interface NoticeStore {

	/**
	 * 공지가항 등록 Store
	 * @param session
	 * @param notice
	 * @return int
	 */
	int insertNotice(SqlSession session, Notice notice);
	
	/**
	 * 공지사항 수정 Stroe
	 * @param session
	 * @param notice
	 * @return int
	 */
	int updateNotice(SqlSession session, Notice notice);

	/**
	 * 공지사항 목록 조회 Store
	 * @param session
	 * @return
	 */
	List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo);

	/**
	 * 공지사항 갯수 조회 Store
	 * @return int
	 */
	int selectNoticeListCount(SqlSession session);

	/**
	 * 공지사항 전체 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByAll(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 작성자로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByWriter(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 제목으로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> selectNoticeByTitle(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 내용으로 검색 Store
	 * @param session
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByContent(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 조건에 따라 키워드로 검색 Store
	 * @param session
	 * @param searchCondition all, writer,title, content
	 * @param searchKeyword
	 * @return List
	 */
	List<Notice> searchNoticeByKeyword(SqlSession session, PageInfo pInfo, Map<String, String> paramMap);

	/**
	 * 공지사항 검색 게시물 전체 갯수(검색 한 게시물 페이징처리) Store
	 * @param session
	 * @param paramMap
	 * @return int
	 */
	int selectListCount(SqlSession session, Map<String, String> paramMap);

	/**
	 * 공지사항 번호로 조회 Store
	 * @param session
	 * @param noticeNo
	 * @return Notice
	 */
	Notice selectNoticeByNo(SqlSession session, Integer noticeNo);


}
