package com.moon.spring.notice.store.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.moon.spring.notice.domain.Notice;
import com.moon.spring.notice.domain.PageInfo;
import com.moon.spring.notice.store.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore{

	// 공지사항 등록
	@Override
	public int insertNotice(SqlSession session, Notice notice) {
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	// 공지사항 수정
	@Override
	public int updateNotice(SqlSession session, Notice notice) {
		int result = session.update("NoticeMapper.updateNotice", notice);
		return result;
	}

	// 게시판 (페이징 처리)
	@Override
	public List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo) {
		int limit = pInfo.getRecordCoutnPerPage();	// 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = session.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}

	// 네비게이션 갯수
	@Override
	public int selectNoticeListCount(SqlSession session) {
		int result = session.selectOne("NoticeMapper.selectNoticeListCount");
		return result;
	}

	// 공지사항 전체 검색
	@Override
	public List<Notice> searchNoticeByAll(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticeByAll", searchKeyword);
		return searchList;
	}

	// 공지사항 작성자로 검색
	@Override
	public List<Notice> searchNoticeByWriter(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticeByWriter", searchKeyword);
		return searchList;
	}

	// 공지사항 제목으로 검색
	@Override
	public List<Notice> selectNoticeByTitle(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.selectNoticeByTitle", searchKeyword);
		return searchList;
	}

	// 공지사항 내용으로 검색
	@Override
	public List<Notice> searchNoticeByContent(SqlSession session, String searchKeyword) {
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticeByContent", searchKeyword);
		return searchList;
	}

	// 공지사항 조건에 따라 키워드로 검색
	@Override
	public List<Notice> searchNoticeByKeyword(SqlSession session, PageInfo pInfo, Map<String, String> paramMap) {
		int limit = pInfo.getRecordCoutnPerPage();
		int offset = (pInfo.getCurrentPage()-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticeByKeyword", paramMap, rowBounds);
		return searchList;
	}

	// 공지사항 검색 게시물 전체 갯수(검색 한 게시물 페이징처리)
	@Override
	public int selectListCount(SqlSession session, Map<String, String> paramMap) {
		int result = session.selectOne("NoticeMapper.selectListByKeywordCount", paramMap);
		return result;
	}

	// 공지사항 번호로 조회
	@Override
	public Notice selectNoticeByNo(SqlSession session, Integer noticeNo) {
		Notice NoticeOne = session.selectOne("NoticeMapper.selectNoticeByNo", noticeNo);
		return NoticeOne;
	}

}
