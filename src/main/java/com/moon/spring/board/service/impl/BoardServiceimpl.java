package com.moon.spring.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.spring.board.domain.Board;
import com.moon.spring.board.domain.PageInfo;
import com.moon.spring.board.service.BoardService;
import com.moon.spring.board.store.BoardStore;

@Service
public class BoardServiceimpl implements BoardService{
	
	@Autowired
	private BoardStore bStore;
	
	@Autowired
	private SqlSession sqlSession;

	// 게시글 등록
	@Override
	public int insertBoard(Board board) {
		int result = bStore.insertBoard(sqlSession, board);
		return result;
	}

	// 게시글 수정
	@Override
	public int updateBoard(Board board) {
		int result = bStore.updateBoard(sqlSession, board);
		return result;
	}

	// 게시글 삭제
	@Override
	public int deleteBoard(Board board) {
		int result = bStore.deleteBoard(sqlSession, board);
		return result;
	}

	// 전체 게시물 갯수
	@Override
	public int getListCount() {
		int result = bStore.selectListCount(sqlSession);
		return result;
	}

	// 게시글 전체 조회
	@Override
	public List<Board> selectBoardList(PageInfo pInfo) {
		List<Board> bList = bStore.selectBoardList(sqlSession, pInfo);
		return bList;
	}

	// 번호로 게시글 조회
	@Override
	public Board selectBoardByNo(Integer boardNo) {
		Board boardOne = bStore.selectBoardByNo(sqlSession, boardNo);
		return boardOne;
	}

}
