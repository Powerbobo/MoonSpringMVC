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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	// 마이페이지 수정하기
	@RequestMapping(value="/member/update.kh", method=RequestMethod.POST)
	public String modifyMember(
			@ModelAttribute Member member
			, Model model
			) {
		try {
			int result = service.modifyMember(member);
			if(result > 0) {	// 유효성 검사
				// 성공 시 메인페이지
				return "redirect:/index.jsp";
			} else {
				// 실패 시 에러페이지 거쳐서 마이페이지
				model.addAttribute("error", "마이페이지 이동에 실패했습니다..");
				model.addAttribute("msg", "데이터 조회 실패");
				model.addAttribute("url", "/member/myPage.kh?memberId="+member.getMemberId());
				return "member/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}

	// 회원 탈퇴하기(MEMBER_YN -> N 으로 정보 보존하는 형태로 UPDATE
	@RequestMapping(value="/member/delete.kh", method=RequestMethod.GET)
	public String outService(
			@RequestParam("memberId") String memberId
			, Model model) {
		try {
			int result = service.deleteMember(memberId);
			if(result > 0) {
				return "redirect:/member/logout.kh";
			} else {
				// 실패하면 에러페이지로 이동
				model.addAttribute("msg", "회원 탈퇴가 완료되지 않았습니다.");
				model.addAttribute("error", "회원탈퇴 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/index.jsp");
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
			// int result = service.checkMemberLogin(member);
			Member mOne = service.checkMemberLogin(member);
			if(mOne != null) {
				// 로그인 세션 저장
				// 성공하면 메인페이지로 이동
				// 결과값을 카운트로 있는지 여부만 확인하기 때문에 Name 못가져 옴
				// Name 을 가져오기 위해선 int result (카운트) 가 아닌 Member member 를 사용해야함.
				session.setAttribute("memberId", mOne.getMemberId());
				session.setAttribute("memberName", mOne.getMemberName());
				return "redirect:/index.jsp";
			} else {
				// 실패하면 에러페이지로 이동
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
	
	// 로그아웃
	// 로그아웃 다음에 쿼리문 실행하는게 없기때문에 데이터 보낼게 없어서 GET 방식 사용
	@RequestMapping(value="/member/logout.kh", method=RequestMethod.GET)
	public String memberLogout(HttpSession session, Model model) {
		if(session != null) {
			session.invalidate();
			return "redirect:/index.jsp";
		} else {
			model.addAttribute("error", "로그아웃을 완료하지 못하였습니다.");
			model.addAttribute("msg", "로그아웃 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	// 마이페이지 이동
	@RequestMapping(value="/member/myPage.kh", method=RequestMethod.POST)
	// url 과 쿼리스트링을 알면 다른 아이디의 마이페이지도 들어갈 수 있어서 post 방식으로 바꿈
	public String showMyPage(
			// 쿼리스트링 받기 위해서 RequestParam 써줌
			// @RequestParam("memberId") String memberId
			// 모델에 키와 키값으로 데이터를 넣어주면 jsp 에서 꺼내서 사용 가능
			
			// 마이페이지를 session에서 아이디 정보를 가져와 이동하기 위해 선언함
			 HttpSession session
			 
			, Model model) {
		// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?
		// Exception 발생 시 에러메세지를 출력하기 위해 try ~ catch 설정해줌
		try {
			
			// getAttribute 가 Object로 가져오기 때문에 String 으로 강제 형변환을 해준다.
			String memberId = (String)session.getAttribute("memberId");
			Member member = null;
			// session이 값이 없거나 null 일 경우
			if(memberId != "" && memberId!=null) { // 유효성 검사
				member = service.getMemberById(memberId);
			}
			if(member != null) {
				// 성공 -> 마이페이지 이동
				model.addAttribute("member", member);
				return "member/myPage";
			} else {
				// 실패 -> 에러페이지 이동
				model.addAttribute("error", "마이페이지 이동에 실패했습니다..");
				model.addAttribute("msg", "데이터 조회 실패");
				model.addAttribute("url", "/index.jsp");
				return "member/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	// 회원정보 수정페이지로 이동하기
	@RequestMapping(value="/member/update.kh", method=RequestMethod.GET)
	public String showModifyForm(
			String memberId
			, Model model) {
		Member member = service.getMemberById(memberId);
		model.addAttribute("member", member);
		return "member/modify";
	}
}
