package com.moon.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moon.spring.member.domain.Member;
import com.moon.spring.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	// get 방식으로 회원가입 페이지 단순 이동
	@RequestMapping(value="/member/register.kh", method=RequestMethod.GET)
	public String showRegisterForm() {
		return "member/register";
	}
	
	// post 방식으로 회원가입 처리
	@RequestMapping(value="/member/register.kh", method=RequestMethod.POST)
	public String registerMember(
			@RequestParam("memberId") String memberId
			// @ReuquestParam 방식이면 회원가입에서 작성하는 모든 name 값을 넣어줘야 하지만,
			// domain의 필드 변수명과 name 값이 동일하면 @ModelAttribute 1개로 대체할 수 있다.
			, @ModelAttribute Member member
			, Model model) {
		try {
			int result = service.insertMember(member);
			if(result > 0) {
				// 성공하면 로그인 페이지
				// home.jsp가 로그인할 수 있는 페이지가 되면 됨!
				return "redirect:/index.jsp";
			} else {
				// 실패하면 에러 페이지
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				model.addAttribute("error", "회원가입 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	// 로그인
	@RequestMapping(value="/member/login.kh", method=RequestMethod.POST)
	public String memberLoginCheck(
			@ModelAttribute Member member
			, HttpSession session
			, HttpServletRequest request
			, Model model) {
		try {
			// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
			// 카운트로 하는 방법!
//			int result = service.checkMemberLogin(member);
			Member mOne = service.checkMemberLogin(member);
			if(mOne != null) {
				// 로그인 세션 저장
				session = request.getSession();
				// 성공하면 메인페이지로 이동
				// 결과값을 카운트로 있는지 여부만 확인하기 때문에 Name 못가져 옴
				// Name 을 가져오기 위해선 int result (카운트) 가 아닌 Member member 를 사용해야함.
				session.setAttribute("memberId", member.getMemberId());
				session.setAttribute("memberPw", member.getMemberPw());
				model.addAttribute("memberName", member.getMemberName());
				return "redirect:/index.jsp";
			} else {
				// 실패하면 에로페이지로 이동
				model.addAttribute("msg", "로그인이 완료되지 않았습니다.");
				model.addAttribute("error", "로그인 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
		
	}
}
