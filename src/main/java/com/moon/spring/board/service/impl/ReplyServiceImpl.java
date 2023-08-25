package com.moon.spring.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.spring.board.domain.Board;
import com.moon.spring.board.domain.Reply;
import com.moon.spring.board.service.ReplyService;
import com.moon.spring.board.store.ReplyStore;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private SqlSession session;
	
	@Autowired
	private ReplyStore rStore;

	// 게시글 댓글 등록
	@Override
	public int insertReply(Reply reply) {
		int result = rStore.insertReply(session, reply);
		return result;
	}

	// 댓글 전체 조회
	@Override
	public List<Reply> selectReplyList(Integer boardNo) {
		List<Reply> bList = rStore.selectReplyList(session, boardNo);
		return bList;
	}

	// 게시글 댓글 수정
	@Override
	public int updateReply(Reply reply) {
		int result = rStore.updateReply(session, reply);
		return result;
	}
	
}
