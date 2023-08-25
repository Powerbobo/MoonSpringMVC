package com.moon.spring.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.moon.spring.board.domain.Board;
import com.moon.spring.board.domain.PageInfo;
import com.moon.spring.board.domain.Reply;
import com.moon.spring.board.service.BoardService;
import com.moon.spring.board.service.ReplyService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	@Autowired
	private ReplyService rService;
	
	/**
	 * 게시글 작성 페이지 이동
	 * @param mv
	 * @return mv
	 */
	@RequestMapping(value="/board/write.kh", method=RequestMethod.GET)
	public ModelAndView showWriteForm(ModelAndView mv) {
		// ModelAndView -> 페이지 이동 및 데이터 전송 가능한 클래스
		// 기능이 많기때문에 사용
		
		// 페이지 이동
		mv.setViewName("board/write");
		return mv;
	}

	/**
	 * 게시글 전체 조회
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value="/board/list.kh", method=RequestMethod.GET)
	public ModelAndView showBoardList(
			@RequestParam(value="page", required=false, defaultValue="1") Integer currentPage
			, ModelAndView mv) {
		try {
			// 총 게시물 갯수
			Integer totalCount = bService.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			// 게시글 목록
			List<Board> bList = bService.selectBoardList(pInfo);
			if(!bList.isEmpty()) {
				// 메소드 체이닝 기법
				// mv. 이 반복되기 때문에 붙여서 사용이 가능하다.
				mv.addObject("bList", bList).addObject("pInfo", pInfo).setViewName("/board/list");
			} else {
				mv.addObject("msg", "게시글 조회가 완료되지 않았습니다.");
				mv.addObject("error", "게시글 상세조회 실패");
				mv.addObject("url", "/board/list.kh");
				mv.setViewName("/common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "게시글 조회가 완료되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("/common/errorPage");
		}
		return mv;
	}

	/**
	 * 번호로 게시글 조회
	 * @param boardNo
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/board/detail.kh", method=RequestMethod.GET)
	public ModelAndView showDetailForm(
			@RequestParam("boardNo") Integer boardNo
			, ModelAndView mv) {
		try {
			Board boardOne = bService.selectBoardByNo(boardNo);
			if(boardOne != null) {
				List<Reply> replyList = rService.selectReplyList(boardNo);
				if(replyList.size() > 0) {
					mv.addObject("rList", replyList);
				}
				mv.addObject("board", boardOne);
				mv.addObject("board/detail");
			} else {
				mv.addObject("msg", "게시글 조회가 완료되지 않았습니다.");
				mv.addObject("error", "게시글 상세조회 실패");
				mv.addObject("url", "/board/list.kh");
				mv.setViewName("/common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("/common/errorPage");
		}
		return mv;
	}
	
	/**
	 * 게시글 등록
	 * @param mv
	 * @param board
	 * @param uploadFile
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="/board/write.kh", method=RequestMethod.POST)
	public ModelAndView boardRegister(
			ModelAndView mv
			, @ModelAttribute Board board
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request) {
		try {
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				// 파일정보(이름, 리네임, 경로, 크기) 및 파일저장
				// 이름, 리네임, 경로, 크기를 넘겨주는 메소드를 만들어서 재사용성을 높힘
				Map<String, Object> bMap = this.saveFile(request, uploadFile);
				board.setBoardFilename((String)bMap.get("fileName"));
				board.setBoardFileRename((String)bMap.get("fileRename"));
				board.setBoardFilepath((String)bMap.get("filePaht"));
				board.setBoardFileLength((long)bMap.get("fileLength"));
			}
			int result = bService.insertBoard(board);
			if(result > 0) {
				mv.setViewName("/board/write.kh");
			} else {
				mv.addObject("msg", "데이터 조회를 실패했습니다.");
				mv.addObject("error", "데이터 조회 실패!");
				mv.addObject("url", "/board/list.kh");
				mv.setViewName("common/errorPage");
			}
			mv.setViewName("redirect:/board/list.kh");
		} catch (Exception e) {
			// 기존 model.addAttribute 사용하던걸 mv.addObject 로 사용!
			// Ajax 에선 ModelAndView 사용이 불가능해서 2가지 방법 다 일고있어야 함.
			mv.addObject("msg", "데이터 조회를 실패했습니다..");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	/**
	 * 파일 이름/리네임/경로/크기 구하기
	 * @param request
	 * @param uploadFile
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> saveFile(HttpServletRequest request, MultipartFile uploadFile) throws Exception {
		Map<String, Object> fileMap = new HashMap<String, Object>();
		
		// resources 경로 구하기
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		// 파일 저장경로 구하기
		String savePath = root + "\\buploadFiles\\";
		
		// 파일 이름 구하기
		String fileName = uploadFile.getOriginalFilename();
		
		// 파일 확장자 구하기
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 시간으로 파일 리네임하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileRename = sdf.format(new Date(System.currentTimeMillis()))+"."+extension;
		
		// 파일 저장 전 폴더 만들기
		File saveFolder = new File(savePath);
		// 유효성 검사, 저장폴더가 없다면!
		// 보통 한번만 만들면 되어서 if를 사용할 필요가 없지만 사용자마다 폴더가 필요하다면 
		// if 문으로 유효성 검사를 해야한다.
		if(!saveFolder.exists()) {
			saveFolder.mkdir();
		}
		
		// 파일 저장
		File saveFile = new File(savePath+"\\"+fileRename);
		uploadFile.transferTo(saveFile);	// checked Exception -> throws Exception
		
		// 파일 크기
		long fileLength = uploadFile.getSize();
		
		// 파일 리턴
		fileMap.put("fileName", fileName);
		fileMap.put("fileRename", fileRename);
		fileMap.put("filePath", "../resources/buploadFiles/"+fileRename);
		fileMap.put("fileLength", fileLength);
		
		return fileMap;
	}
	
	private PageInfo getPageInfo(Integer currentPage, Integer totalCount) {
		int recordCountPerPage = 10;
		int naviCountPerPage = 10;
		// 범위(네비)의 총 갯수 구하기
		// Math.ceil 로 반올림 해서 +1 을 해 줄 필요가 없음.
		int naviTotalCount = (int)Math.ceil((double)totalCount/recordCountPerPage);;
		int startNavi = ((int)((double)currentPage/naviCountPerPage+0.9)-1)*naviCountPerPage+1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		
		PageInfo pInfo = new PageInfo(currentPage, totalCount, naviTotalCount, recordCountPerPage, naviCountPerPage, startNavi, endNavi);
		
		return pInfo;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
