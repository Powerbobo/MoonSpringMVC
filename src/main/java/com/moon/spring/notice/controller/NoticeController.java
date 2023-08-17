package com.moon.spring.notice.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.moon.spring.notice.domain.Notice;
import com.moon.spring.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	// 의존성 주입을 받아서 객체처럼 사용!
	// 인터페이스와 연결해서 결합도 낮춤
	@Autowired	
	private NoticeService service;

	// 공지사항 페이지로 이동
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.GET)
	public String showInsertForm() {
		return "notice/insert";
	}
	
	// 공지사항 작성
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.POST)
	public String insertNotice(
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			, Model model) {
		try {
			if(!uploadFile.getOriginalFilename().equals("")) {
				// ============================== 파일 이름 ==============================
				String fileName = uploadFile.getOriginalFilename();
				// (내가 저장훈 후 그 경로를 DB에 저장하도록 준비하는 것)
				// getServletContext() -> Servlet에 관련된 객체들이 저장되어있는 메소드
				// "resources" -> resources의 경로
				String root = request.getSession().getServletContext().getRealPath("resources");
				// 폴더가 없을 경우 자동 생성(내가 업로드한 파일을 저장할 폴더)
				String saveFolder = root + "\\nuploadFiles";
				File folder = new File(saveFolder);
				if(!folder.exists()) {	// 해당 경로에 파일 있는지 여부 확인
					// 없을 경우 폴더 생성
					folder.mkdir();
				}
				// ============================== 파일 경로 ==============================
				String savePath = saveFolder + "\\" + fileName;
				File file = new File(savePath);
				// ***************************** 파일 저장 *****************************
				uploadFile.transferTo(file);
				
				// ============================== 파일 크기 ==============================
				long fileLength = uploadFile.getSize();
				
				// DB에 저장하기 위해 notice에 데이터를 Set 하는 부분
				notice.setNoticeFilename(fileName);
				notice.setNoticeFilepath(savePath);
				notice.setNoticeFilelength(fileLength);
				
			}
			int result = service.insertNotice(notice);
			if(result > 0) {
				// 성공 
				return "redirect:/notice/list.kh";
			} else {
				// 실패하면 에러 페이지
				model.addAttribute("msg", "공지사항 등록이 완료되지 않았습니다.");
				model.addAttribute("error", "공지사항 등록 실패");
				model.addAttribute("url", "/member/insert.kh");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/insert.kh");
			return "common/errorPage";
		}
	}
	
	// 공지사항 리스트로 이동
	// 정보를 가져와서 공지사항을 보여줘야하기 때문에 Model이 필요함
	@RequestMapping(value="/notice/list.kh", method=RequestMethod.GET)
	public String showNoticeList(Model model) {
		try {
			List<Notice> nList = service.selectNoticeList();
			// List 데이터의 유효성 체크 방법 2가지
			// 1. isEmpty()
			// 2. size()
			if(nList.size() > 0) {
				model.addAttribute("nList", nList);
				return "notice/list";
			} else {
				model.addAttribute("msg", "데이터 조회가 완료되지 않았습니다.");
				model.addAttribute("error", "공지사항 목록 조회 실패");
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
	

	
}
