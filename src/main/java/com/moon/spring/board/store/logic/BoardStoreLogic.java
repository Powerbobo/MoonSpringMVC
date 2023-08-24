package com.moon.spring.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.moon.spring.board.domain.Board;
import com.moon.spring.board.domain.PageInfo;
import com.moon.spring.board.store.BoardStore;

@Repository
public class BoardStoreLogic implements BoardStore{

	// 게시글 등록
	@Override
	public int insertBoard(SqlSession sqlSession, Board board) {
		int result = sqlSession.insert("BoardMapper.insertBoard", board);
		return result;
	}

	// 전체 게시물 갯수
	@Override
	public int selectListCount(SqlSession sqlSession) {
		int result = sqlSession.selectOne("BoardMapper.selectListCount");
		return result;
	}

	// 게시글 전체 조회
	@Override
	public List<Board> selectBoardList(SqlSession sqlSession, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Board> bList = sqlSession.selectList("BoardMapper.selectBoardList", null, rowBounds);
		return bList;
	}

	// 번호로 게시글 조회
	@Override
	public Board selectBoardByNo(SqlSession sqlSession, Integer boardNo) {
		Board boardOne = sqlSession.selectOne("BoardMapper.selectBoardByNo", boardNo);
		return boardOne;
	}

}
