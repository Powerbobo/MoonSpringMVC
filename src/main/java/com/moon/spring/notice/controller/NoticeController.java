package com.moon.spring.notice.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.moon.spring.notice.domain.PageInfo;
import com.moon.spring.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	// 의존성 주입을 받아서 객체처럼 사용!
	// 인터페이스와 연결해서 결합도 낮춤
	@Autowired	
	private NoticeService service;

	// 공지사항 작성
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.POST)
	public String insertNotice(
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			, Model model) {
		try {
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				
				Map<String, Object> nMap = this.saveFile(uploadFile, request);
				String fileName = (String)nMap.get("fileName");
				String fileRename = (String)nMap.get("fileRename");
				String savePath = (String)nMap.get("filePath");
				long fileLength = (long)nMap.get("fileLength");
				
				// DB에 저장하기 위해 notice에 데이터를 Set 하는 부분
				notice.setNoticeFilename(fileName);
				notice.setNoticeFileRename(fileRename);
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

	// 공지사항 수정하기
	@RequestMapping(value="/notice/modify.kh", method=RequestMethod.POST)
	public String updateNotice(
			// @ModelAttribute 는 name의 값이 Notice의 필드명과 동일해야 사용할 수 있음
			@ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			, Model model) {
		try {
			//uploadFile 유효성(null인지 아닌지) 검사하고 나서 동작
			if(uploadFile != null && !uploadFile.isEmpty()) {
				// 수정
				// 1. 대체, 2. 삭제 후 등록
				// 삭제 후 등록 -> 기존 업로드 된 파일 존재 여부 체크 후
				// 랜덤한 파일명으로 리네임해서 업로드하기 때문에 getNoticeFileRename 사용
				String fileName = notice.getNoticeFileRename();
				if(fileName != null) {
					// 있으면 기존 파일 삭제
					// 다른 메소드에서 쓰일 수 있기 때문에 메소드로 만들어서 사용
					this.deleteFile(request, fileName);
				}
				// 없으면 새로 업로드 하려는 파일 저장
				Map<String, Object> infoMap = this.saveFile(uploadFile, request);	// 기존 파일 불러오기
				String noticeFilename = (String)infoMap.get("fileName"); // Object 타입이기때문에 형 변환
				long noticeFilelength = (long)infoMap.get("fileLength");
				notice.setNoticeFilename(noticeFilename);
				// 변수선언방식도 있지만, 이렇게 한줄로 쓸수도 있음
				notice.setNoticeFileRename((String)infoMap.get("fileRename"));
				notice.setNoticeFilepath((String)infoMap.get("filePath"));
				notice.setNoticeFilelength(noticeFilelength);
			}
			int result = service.updateNotice(notice);
			if(result > 0) {
				// 성공
				return "redirect:/notice/detail.kh?noticeNo="+notice.getNoticeNo();
			} else {
				// 실패하면 에러 페이지
				model.addAttribute("msg", "공지사항 수정이 완료되지 않았습니다.");
				model.addAttribute("error", "공지사항 수정 실패");
				model.addAttribute("url", "/notice/detail.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/list.kh");
			return "common/errorPage";
		}
	}

	// 공지사항 페이지로 이동
	@RequestMapping(value="/notice/insert.kh", method=RequestMethod.GET)
	public String showInsertForm() {
		return "notice/insert";
	}
	
	// 공지사항 리스트로 이동
	// 정보를 가져와서 공지사항을 보여줘야하기 때문에 Model이 필요함
	@RequestMapping(value="/notice/list.kh", method=RequestMethod.GET)
	public String showNoticeList(
			// 페이징처리 위해서 선언
			// required=false -> 필수가 아니라고 선언
			// 삼항연산자로 기본값 1로 설정하는 대신, defaultValue 으로 설정할 수 있음!
			// 페이지값을 쓰지 않았다면, 디폴트값을 1로 설정함.
			@RequestParam(value="page", required=false, defaultValue = "1") Integer currentPage
			, Model model) {
		try {
			// 삼항연산자를 사용해
			// int currentPage = page !=null ? page : 1;
			
			// totalCount는 쿼리문 동작해서 값을 가져와야 하기때문에 비지니스 로직으로 만들어야 함.
			int totalCount = service.getListCount();
			
			// 공지사항 리스트 정보를 pInfo 변수에 넣어주기
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			List<Notice> nList = service.selectNoticeList(pInfo);
			// List 데이터의 유효성 체크 방법 2가지
			// 1. isEmpty()
			// 2. size()
			if(nList.size() > 0) {
				model.addAttribute("pInfo", pInfo);	// model을 이용해 jsp에서 pInfo 사용
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

	// 공지사항 번호로 조회
	@RequestMapping(value="/notice/detail.kh", method=RequestMethod.GET)
	public String showDetailNotice(
			// "noticeNo" -> 쿼리스트링의 키값을 넣어줘야 함
			// Null 체크하기 위해서 Integer 사용했고, Null 체크하지 않으면 int 사용해도 괜찮다.
			@RequestParam("noticeNo") Integer noticeNo
			, Model model) {	
		Notice noticeOne = service.selectNoticeByNo(noticeNo);
		// "notice" 은 키값으로 내가 정할 수 있다.
		// noticeOne 으로 키값을 정했다면 detail.jsp 제목의 value 값을 notice. 이 아닌
		// noticeOne 으로 사용하면 된다.
		model.addAttribute("notice", noticeOne);
		return "notice/detail";
	}

	// 수정페이지로 이동
	@RequestMapping(value="/notice/modify.kh", method=RequestMethod.GET)
	public String showModifyForm(
			@RequestParam("noticeNo") Integer noticeNo
			, Model model) {
		Notice noticeOne = service.selectNoticeByNo(noticeNo);
		model.addAttribute("notice", noticeOne);
		return "notice/modify";
	}
	
	// 페이지 네비게이션
	public PageInfo getPageInfo(int currentPage, int totalCount) {
		PageInfo pi = null;
		int recordCountPerPage = 10;	// 한 페이지당 보여질 게시물의 개수
		int naviCountPerPage = 10;		// 한 페이지 범위에 보여질 페이지의 개수
		int naviTotalCount;				// 범위의 총 개수
		int startNavi;					// 각 페이지 범위 시작 번호
		int endNavi;					// 각 페이지 범위 끝 번호
		
		// 페이지가 10 단위로 딱 떨어지는게 아니기 때문에, 누락되는 페이지 없이
		// 전체 페이지를 보여주기 위해서 무조건 올림 하기 위해서 0.9를 더해줌.
		// + 0.9 로 자동 형변환, (int)로 강제 형번환!
		naviTotalCount = (int)(totalCount/recordCountPerPage + 0.9);
		
		// + 0.9 하지 않고, Math 메소드로 올림하는 방법
		// Math.ceil((double)totalCount/recordCountPerPage)
		
		// startNavi 값 구하는 방법 (시작되는 페이지, recordCountPerPage가 넘어가면 startNavi 값도 변함.)
		// CurrentPage값이 1~5 일때, startNavi가 1로 고정되도록 구해주는 식
		startNavi = (((int)((double)currentPage/naviCountPerPage+0.9))-1) * naviCountPerPage + 1;
		
		// 한 페이지의 끝 값
		endNavi = startNavi + naviCountPerPage - 1;
		
		// endNavi는 startNavi에 무조건 naviCountPerPage값을 더하므로 실제 최대값보다 커질 수 있음
		// 총 페이지 12일때 endNavi가 15가 될 수 있음. 그것을 방지하기 위해서 아래 식 작성
		if(endNavi > naviCountPerPage) {
			endNavi = naviCountPerPage;
		}
		
		// 아래 모든 값을 return 으로 반환해야하는데, 메소드로 아래 모든 값 반환이 불가능해서
		// 아래 변수를 전부 선언한 Class(domain)을 선언
		// return recordCountPerPage, naviCountPerPage, naviTotalCount, startNavi, endNavi;
		
		// PageInfo Class 를 만들어서 모든 변수를 담고, 해당 클래스를 이용해 객체를 만들어서 return
		pi = new PageInfo(currentPage, recordCountPerPage, naviCountPerPage, startNavi, endNavi, totalCount, naviTotalCount);
		return pi;
	}
	
	// 네비게이션 검색하기
	@RequestMapping(value="/notice/search.kh", method=RequestMethod.GET)
	public String searchNoticeList(
			@RequestParam("searchCondition") String searchCondition
			, @RequestParam("searchKeyword") String searchKeyword
			, @RequestParam(value="page", required=false, defaultValue="1") Integer currentPage
			, Model model) {
		
		// switch case문에서 사용하고, 밖에서도 사용해야하기 때문에 전역변수 선언
		List<Notice> searchList = new ArrayList<Notice>();
		
		// 총 4개의 쿼리문이 필요함.
		//SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT = ? OR NOTICE_CONTENT = ? OR WHERE NOTICE_WRITER = ?
		//SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT = ?
		//SELECT * FROM NOTICE_TBL WHERE NOTICE_CONTENT = ?
		//SELECT * FROM NOTICE_TBL WHERE NOTICE_WRITER = ?
		
		// parameter(값을 넘겨주는 자리)의 자리는 1개밖에 없기때문에 2개를 넘길 수 없음
		// 2개의 값을 하나의 변수로 다루는 방법
		// 1. VO 클래스 만드는 방법 - 이미 해봤음!
		// 2. HashMap 사용 방법 - 이 방법 사용
		Map<String, String> paramMap = new HashMap<String, String>();
		// put() 메소드를 사용해서 key-value 설정하는데,
		// key값(파란색)이 mapper.xml에서 사용됨!!
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchKeyword", searchKeyword);
		
		// 페이징 처리
		int totalCount = service.getListCount(paramMap);
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		
		
		// 원래는 get메소드로 paramMap에 각 밸류값을 넣어줘야하는데,
		// 이 부분은 Mybatis에서 동작해줘서 생략해도 괜찮음!!
		//paramMap.get("searchCondition");
		//paramMap.get("searchKeyword");
		searchList = service.searchNoticeByKeyword(pInfo, paramMap);
		
//		switch(searchCondition) {
//			case "all" :
//				//SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT = ? OR NOTICE_CONTENT = ? OR WHERE NOTICE_WRITER = ?
//				searchList = service.searchNoticeByAll(searchKeyword);
//				break;
//			case "writer" : 
//				//SELECT * FROM NOTICE_TBL WHERE NOTICE_WRITER = ?
//				searchList = service.searchNoticeByWriter(searchKeyword);
//				break;
//			case "title" : 
//				//SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT = ?
//				// 제목으로 검색 시 결과값이 1개가 아닐 수 있기때문에 List로 반환
//				searchList = service.searchNoticeByTitle(searchKeyword);
//				break;
//			case "content" : 
//				//SELECT * FROM NOTICE_TBL WHERE NOTICE_CONTENT = ?
//				searchList = service.searchNoticeByContent(searchKeyword);
//				break;
//		}
		if(!searchList.isEmpty()) {
			// 조건으로 검색 후 2페이지 이후에도 해당 조건에 맞게 조회하기 위해서
			// searchCondition, searchKeyword를 model.addAttribut 해 jsp 에서 사용!
			model.addAttribute("searchCondition", searchCondition);
			model.addAttribute("searchKeyword", searchKeyword);
			model.addAttribute("pInfo", pInfo);
			model.addAttribute("sList", searchList);
			return "notice/search";
		} else {
			model.addAttribute("msg", "데이터 조회가 완료되지 않았습니다.");
			model.addAttribute("error", "공지사항 제목으로 조회 실패");
			model.addAttribute("url", "/list.jsp");
			return "common/errorPage";
		}
	}

	// 파일이름, 경로, 크기를 넘겨주는 메소드
	public Map<String, Object> saveFile(MultipartFile uploadFile, HttpServletRequest request) throws Exception {
		// 1. 넘겨야 하는 값이 여러개일 때 사용하는 방법
		// 2. Hashmap을 사용하는 방법
		// 데이터 타입이 여러개이기 때문에 Object 사용함
		Map<String, Object> infoMap = new HashMap<String, Object>();
		
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
		// FILERENAME 추가
		// 임의로 이름을 계속 부여하기 위해서 Random 메소드로 이름을 생성함
		// 앞에 n 으로 시작하게끔 한다던지, 일정한 규칙을 새워 리네임을 할 수 있음
		
		// Random 으로 파일 리네임 이름 정하기
//		Random rand = new Random();
//		String strResult = "N";
//		for(int i = 0; i < 7; i++) {
//			int result = rand.nextInt(20)+1;
//			strResult += result + "";
//		}
		
		// Date 를 사용해 파일명에 시간을 붙여서 리네임하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // 나중에 SS랑 비교
		String strResult = sdf.format(new Date(System.currentTimeMillis()));
		
		// 파일 이름에서 확장자명 추출하기
		// 확장자명 만 자르기 위해서 +1 을 해줌
		String ext = fileName.substring(fileName.lastIndexOf(".")+1); // .을 포함하지 않고 자름 +1
		String fileRename = "N"+strResult+"."+ext;
		
		String savePath = saveFolder + "\\" + fileRename;
		File file = new File(savePath);
		// ***************************** 파일 저장 *****************************
		// Unhandled exception type IOException -> 입출력 예외처리를 해줘야 하는데,
		// insert 때 try-catch를 해주기 때문에 throws Exception 처리를 함.
		uploadFile.transferTo(file);
		
		// ============================== 파일 크기 ==============================
		long fileLength = uploadFile.getSize();
		
		// 파일이름, 경로, 크기를 넘겨주기 위해 Map에 필요한 정보를 저장한 후 return함
		// 왜 return하는가? DB에 저장하기 위해서 필요한 정보이기 때문에!
		infoMap.put("fileName", fileName);
		infoMap.put("fileRename", fileRename);
		infoMap.put("filePath", savePath);
		infoMap.put("fileLength", fileLength);
		return infoMap;
	}

	// 파일 삭제 메소드
	private void deleteFile(HttpServletRequest request, String fileName) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String delFilepath = root + "\\nuploadFiles\\" + fileName;// 삭제할 파일 경로
		File file = new File(delFilepath);
		if(file.exists()) { // 파일이 존재한다면
			file.delete();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
