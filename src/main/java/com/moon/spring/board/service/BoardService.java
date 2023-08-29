package com.moon.spring.board.service;

import java.util.List;

import com.moon.spring.board.domain.Board;
import com.moon.spring.board.domain.PageInfo;

public interface BoardService {

	/**
	 * 게시글 등록 Service
	 * @param board
	 * @return int
	 */
	int insertBoard(Board board);

	/**
	 * 게시글 수정 Service
	 * @param board
	 * @return int
	 */
	int updateBoard(Board board);

	/**
	 * 게시글 삭제 Service
	 * @param board
	 * @return int
	 */
	int deleteBoard(Board board);

	/**
	 * 게시글 전체 조회 Service
	 * @param pInfo
	 * @return List<Board>
	 */
	List<Board> selectBoardList(PageInfo pInfo);

	/**
	 * 번호로 게시글 조회 Service
	 * @param boardNo
	 * @return Board
	 */
	Board selectBoardByNo(Integer boardNo);

	/**
	 * 전체 게시물 갯수 Service
	 * @return int
	 */
	int getListCount();
}