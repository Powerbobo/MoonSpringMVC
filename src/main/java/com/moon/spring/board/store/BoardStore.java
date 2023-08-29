package com.moon.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.moon.spring.board.domain.Board;
import com.moon.spring.board.domain.PageInfo;

public interface BoardStore {


	/**
	 * 게시글 등록 Store
	 * @param session
	 * @param board
	 * @return int
	 */
	int insertBoard(SqlSession sqlSession, Board board);

	/**
	 * 게시글 수정 Store
	 * @param sqlSession
	 * @param board
	 * @return
	 */
	int updateBoard(SqlSession sqlSession, Board board);

	/**
	 * 게시글 삭제 Store
	 * @param sqlSession
	 * @param board
	 * @return int
	 */
	int deleteBoard(SqlSession sqlSession, Board board);

	/**
	 * 전체 게시물 갯수 Store
	 * @param sqlSession
	 * @return
	 */
	int selectListCount(SqlSession sqlSession);

	/**
	 * 게시글 전체 조회 Store
	 * @param sqlSession
	 * @param pInfo
	 * @return List<Board>
	 */
	List<Board> selectBoardList(SqlSession sqlSession, PageInfo pInfo);

	/**
	 * 번호로 게시글 조회 Store
	 * @param sqlSession
	 * @param boardNo
	 * @return Board
	 */
	Board selectBoardByNo(SqlSession sqlSession, Integer boardNo);

}
