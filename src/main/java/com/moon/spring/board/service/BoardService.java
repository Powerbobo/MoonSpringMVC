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
	 * 전체 게시물 갯수 Service
	 * @return int
	 */
	int getListCount();

	/**
	 * 게시글 전체 조회 Service
	 * @param pInfo
	 * @return List<Board>
	 */
	List<Board> selectBoardList(PageInfo pInfo);

	/**
	 * 번호로 게시글 조회 Service
	 * @param boardNo
	 * @return
	 */
	Board selectBoardByNo(Integer boardNo);
}