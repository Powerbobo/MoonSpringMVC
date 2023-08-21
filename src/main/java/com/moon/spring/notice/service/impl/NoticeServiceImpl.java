package com.moon.spring.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.spring.notice.domain.Notice;
import com.moon.spring.notice.domain.PageInfo;
import com.moon.spring.notice.service.NoticeService;
import com.moon.spring.notice.store.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private SqlSession session;
	
	@Autowired
	private NoticeStore nStore;

	// 공지사항 등록
	@Override
	public int insertNotice(Notice notice) {
		int result = nStore.insertNotice(session, notice);
		return result;
	}

	// 공지사항 리스트 조회
	@Override
	public List<Notice> selectNoticeList(PageInfo pInfo) {
		List<Notice> nList = nStore.selectNoticeList(session, pInfo);
		return nList;
	}

	// 공지사항 갯수 조회
	@Override
	public int getListCount() {
		int result = nStore.selectNoticeListCount(session);
		return result;
	}
	
	// 공지사항 전체 검색
	@Override
	public List<Notice> searchNoticeByAll(String searchKeyword) {
		List<Notice> searchList = nStore.searchNoticeByAll(session, searchKeyword);
		return searchList;
	}

	// 공지사항 작성자로 검색
	@Override
	public List<Notice> searchNoticeByWriter(String searchKeyword) {
		List<Notice> searchList = nStore.searchNoticeByWriter(session, searchKeyword);
		return searchList;
	}

	// 공지사항 제목으로 검색
	@Override
	public List<Notice> searchNoticeByTitle(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticeByTitle(session, searchKeyword);
		return searchList;
	}

	// 공지사항 내용으로 검색
	@Override
	public List<Notice> searchNoticeByContent(String searchKeyword) {
		List<Notice> searchList = nStore.searchNoticeByContent(session, searchKeyword);
		return searchList;
	}

	// 공지사항 조건에 따라 키워드로 검색
	@Override
	public List<Notice> searchNoticeByKeyword(PageInfo pInfo, Map<String, String> paramMap) {
		List<Notice> searchList = nStore.searchNoticeByKeyword(session, pInfo, paramMap);
		return searchList;
	}

	// 공지사항 검색 게시물 전체 갯수(검색 한 게시물 페이징처리)
	@Override
	public int getListCount(Map<String, String> paramMap) {
		int result = nStore.selectListCount(session, paramMap);
		return result;
	}

}
