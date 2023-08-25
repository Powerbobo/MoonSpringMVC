package com.moon.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.moon.spring.board.domain.Reply;

public interface ReplyStore {

	/**
	 * 게시글 댓글 등록 Store
	 * @param session
	 * @param reply
	 * @return int
	 */
	int insertReply(SqlSession session, Reply reply);

	/**
	 * 댓글 전체 조회 Store
	 * @param session
	 * @param reply
	 * @return
	 */
	List<Reply> selectReplyList(SqlSession session, Integer boardNo);

	/**
	 * 게시글 댓글 수정 Store
	 * @param session
	 * @param reply
	 * @return int
	 */
	int updateReply(SqlSession session, Reply reply);

}
