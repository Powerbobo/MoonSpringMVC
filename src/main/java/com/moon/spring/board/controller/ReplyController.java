package com.moon.spring.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.moon.spring.board.domain.Reply;
import com.moon.spring.board.service.ReplyService;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService rService;

	/**
	 * 게시글 댓글 등록 Controller
	 * @param mv
	 * @param reply
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value="/add.kh", method=RequestMethod.POST)
	public ModelAndView insertReply(
			ModelAndView mv
			, @ModelAttribute Reply reply
			, HttpSession session) {
		String url = "";
		try {
			String replyWriter = (String)session.getAttribute("memberId");
			reply.setReplyWriter(replyWriter);
			int result = rService.insertReply(reply);
			url = "/board/detail.kh?boardNo="+reply.getRefBoardNo();
			if(result > 0) {
				// 성공
				mv.setViewName("redirect:"+url);
			} else {
				// 실패
				mv.addObject("msg", "댓글 등록이 완료되지 않았습니다.");
				mv.addObject("error", "댓글 등록 실패");
				mv.addObject("url", url);
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", url);
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	/**
	 * 게시글 댓글 수정 Controller
	 * @param reply
	 * @param session
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value="/update.kh", method=RequestMethod.POST)
	public ModelAndView updateReply(
			@ModelAttribute Reply reply
			, HttpSession session
			, ModelAndView mv) {
		String url = "";
		// memberId 는 세션에 저장된 값 불러오기
		try {
			String replyWriter = (String)session.getAttribute("memberId");
			if(replyWriter != null &&!replyWriter.equals("")) {	// 작성자가 비어있지 않다면
				reply.setReplyWriter(replyWriter);
				// detail로 이동
				url = "/board/detail.kh?boardNo="+reply.getRefBoardNo();
				int result = rService.updateReply(reply);
				mv.setViewName("redirect:"+url);
			} else {	// 작성자가 비어있다면
				mv.addObject("msg", "로그인이 되지 않았습니다.");
				mv.addObject("error", "로그인 정보확인 실패");
				mv.addObject("url", "/index.jsp");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", url);
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	/**
	 * 게시글 댓글 삭제
	 * @param mv
	 * @param replyNo
	 * @param replyWriter
	 * @return ModelAndView
	 */
	@RequestMapping(value="/delete.kh", method=RequestMethod.GET)
	public ModelAndView deleteReply(ModelAndView mv
			, @ModelAttribute("reply") Reply reply
//			, @RequestParam("replyNo") Integer replyNo
//			, @RequestParam("replyWriter") String replyWriter
			// 본인이 작성한 댓글만 삭제하게끔 하기 위해서 추가
			, HttpSession session) {
		// Exception 에도 url 사용하기 위해서 적역변수 선언
		String url = "";
		try {
			// Session에서 아이디 가져오기
			String memberId = (String)session.getAttribute("memberId");
			String replyWriter = reply.getReplyWriter();
			url = "/board/detail.kh?boardNo="+reply.getRefBoardNo();
//			if(memberId.equals(replyWriter)) {
				// ModelAttrubte로 변경했기 때문에 수정
			if(replyWriter != null &&  reply.getReplyWriter().equals(memberId)) {
//				Reply reply = new Reply();
//				reply.setReplyNo(replyNo);
//				reply.setReplyWriter(replyWriter);
				int result = rService.deleteRely(reply);
				if(result > 0) {
					// 성공
					mv.setViewName("redirect:"+url);
				} else {
					// 실패
					mv.addObject("msg", "댓글 삭제가 완료되지 않았습니다.");
					mv.addObject("error", "댓글 삭제실패");
					mv.addObject("url", url);
					mv.setViewName("common/errorPage");
				}
			} else {
				mv.addObject("msg", "자신의 댓글만 삭제할 수 있습니다.");
				mv.addObject("error", "댓글 삭제 불가");
				mv.addObject("url", url);
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", url);
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	
	
	
	
	
}
