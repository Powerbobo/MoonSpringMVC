package com.moon.spring.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.moon.spring.board.domain.Reply;
import com.moon.spring.board.store.ReplyStore;

@Repository
public class ReplyStoreLogic implements ReplyStore{

	// 게시글 댓글 등록
	@Override
	public int insertReply(SqlSession session, Reply reply) {
		int result = session.insert("ReplyMapper.insertReply", reply);
		return result;
	}

	// 게시글 댓글 수정
	@Override
	public int updateReply(SqlSession session, Reply reply) {
		int result = session.update("ReplyMapper.updateReply", reply);
		return result;
	}

	// 게시글 댓글 삭제
	@Override
	public int deleteRely(SqlSession session, Reply reply) {
		int result = session.update("ReplyMapper.deleteRely", reply);
		return result;
	}

	// 댓글 전체 조회
	@Override
	public List<Reply> selectReplyList(SqlSession session, Integer boardNo) {
		List<Reply> bLsit = session.selectList("ReplyMapper.selectReplyList", boardNo);
		return bLsit;
	}


}
