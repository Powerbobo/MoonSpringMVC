package com.moon.spring.board.service;

import java.util.List;

import com.moon.spring.board.domain.Reply;

public interface ReplyService {

	/**
	 * 게시글 댓글 등록 Service
	 * @param reply
	 * @return int
	 */
	int insertReply(Reply reply);

	/**
	 * 댓글 전체 조회 Service
	 * @return
	 */
	List<Reply> selectReplyList(Integer boardNo);

	/**
	 * 게시글 댓글 수정 Service
	 * @param reply
	 * @return int
	 */
	int updateReply(Reply reply);

}
