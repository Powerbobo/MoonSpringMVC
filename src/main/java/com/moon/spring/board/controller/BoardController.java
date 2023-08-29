package com.moon.spring.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	 * 게시글 등록
	 * 
	 * @param mv
	 * @param board
	 * @param uploadFile
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/board/write.kh", method = RequestMethod.POST)
	public ModelAndView boardRegister(
			ModelAndView mv
			, @ModelAttribute Board board
			,@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile
			, HttpServletRequest request
			, HttpSession session) {
		try {
			// 작성자를 아이디로 넣기
			String boardWriter = (String)session.getAttribute("memberId");
			if(boardWriter != null && !boardWriter.equals("")) {
				board.setBoardWriter(boardWriter);
				if (uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
					// 파일정보(이름, 리네임, 경로, 크기) 및 파일저장
					// 이름, 리네임, 경로, 크기를 넘겨주는 메소드를 만들어서 재사용성을 높힘
					Map<String, Object> bMap = this.saveFile(request, uploadFile);
					board.setBoardFilename((String) bMap.get("fileName"));
					board.setBoardFileRename((String) bMap.get("fileRename"));
					board.setBoardFilepath((String) bMap.get("filePaht"));
					board.setBoardFileLength((long) bMap.get("fileLength"));
				}
			}
			int result = bService.insertBoard(board);
			if (result > 0) {
				mv.setViewName("/board/write.kh");
			} else {
				mv.addObject("msg", "게시글 등록을 실패했습니다.");
				mv.addObject("error", "게시글 등록 실패!");
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
	 * 게시글 수정하기
	 * 
	 * @param mv
	 * @param board
	 * @param uploadFile
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/board/modify.kh", method = RequestMethod.POST)
	public ModelAndView boardModify(ModelAndView mv, @ModelAttribute Board board,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile
			// 파일 경로 구하기 위해서 사용
			, HttpServletRequest request, HttpSession session) {
		try {
			String memberId = (String) session.getAttribute("memberId");
			String boardWriter = board.getBoardWriter();
			if (boardWriter != null && boardWriter.equals(memberId)) {
				// 수정 -> 대체하는 것, 대체하는 방법은 삭제 후 등록
				// 업로드파일 null 체크, 업로드파일 오리지널 네임 가져와서 와 같은지 비교
				if (uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
					String fileRename = board.getBoardFileRename();
					if (fileRename != null) {
						this.deleteFile(fileRename, request);
					}
					// 기존 파일 삭제했거나, 없을 경우
					Map<String, Object> bFileMap = this.saveFile(request, uploadFile);
					board.setBoardFilename((String) bFileMap.get("fileName"));
					board.setBoardFileRename((String) bFileMap.get("fileRename"));
					board.setBoardFilepath((String) bFileMap.get("filePath"));
					board.setBoardFileLength((long) bFileMap.get("fileLength"));
				}
				int result = bService.updateBoard(board);
				if (result > 0) {
					// 성공
					mv.setViewName("redirect:/board/detail.kh?boardNo=" + board.getBoardNo());
				} else {
					// 실패
					mv.addObject("msg", "게시글 수정에 실패하였습니다.");
					mv.addObject("error", "게시글 수정 실패");
					mv.addObject("url", "/board/detail.kh?boardNo=" + board.getBoardNo());
					mv.setViewName("/common/errorPage");
				}
			} else {
				mv.addObject("msg", "게시글 수정 권한이 없습니다.");
				mv.addObject("error", "게시글 수정 불가");
				mv.addObject("url", "/board/detail.kh?boardNo=" + board.getBoardNo());
				mv.setViewName("/common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의 바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/detail.kh?boardNo=" + board.getBoardNo());
			mv.setViewName("/common/errorPage");
		}
		return mv;
	}

	/**
	 * 게시글 삭제하기
	 * 
	 * @param mv
	 * @param boardNo
	 * @param boardWriter
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/board/delete.kh", method = RequestMethod.GET)
	public ModelAndView deleteBoard(ModelAndView mv
//			, @ModelAttribute("board") Board board
			, @RequestParam("boardNo") Integer boardNo, @RequestParam("boardWriter") String boardWriter,
			HttpSession session) {
		try {
			String memberId = (String) session.getAttribute("memberId");
			Board board = new Board();
			board.setBoardNo(boardNo);
			board.setBoardWriter(boardWriter);
			if (boardWriter != null && boardWriter.equals(memberId)) {
				int result = bService.deleteBoard(board);
				if (result > 0) {
					// 성공
					mv.setViewName("redirect:/board/list.kh");
				} else {
					// 실패
					mv.addObject("msg", "게시글 삭제가 완료되지 않았습니다.");
					mv.addObject("error", "게시글 삭제 실패");
					mv.addObject("url", "/board/list.kh");
					mv.setViewName("/common/errorPage");
				}
			} else {
				mv.addObject("msg", "본인이 작성한 게시글만 삭제할 수 있습니다.");
				mv.addObject("error", "게시글 삭제 불가");
				mv.addObject("url", "/board/list.kh");
				mv.setViewName("/common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의 바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("/common/errorPage");
		}
		return mv;
	}

	/**
	 * 게시글 작성 페이지 이동
	 * 
	 * @param mv
	 * @return mv
	 */
	@RequestMapping(value = "/board/write.kh", method = RequestMethod.GET)
	public ModelAndView showWriteForm(ModelAndView mv) {
		// ModelAndView -> 페이지 이동 및 데이터 전송 가능한 클래스
		// 기능이 많기때문에 사용

		// 페이지 이동
		mv.setViewName("board/write");
		return mv;
	}

	/**
	 * 수정하기 페이지 이동
	 * 
	 * @param mv
	 * @param boardNo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/board/modify.kh", method = RequestMethod.GET)
	public ModelAndView showModifyForm(ModelAndView mv, @RequestParam("boardNo") Integer boardNo) {
		try {
			Board board = bService.selectBoardByNo(boardNo);
			mv.addObject("board", board);
			mv.setViewName("/board/modify");
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의 바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("/common/errorPage");
		}
		return mv;
	}

	/**
	 * 게시글 전체 조회
	 * 
	 * @param mv
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/board/list.kh", method = RequestMethod.GET)
	public ModelAndView showBoardList(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer currentPage, ModelAndView mv) {
		try {
			// 총 게시물 갯수
			Integer totalCount = bService.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			// 게시글 목록
			List<Board> bList = bService.selectBoardList(pInfo);
			if (!bList.isEmpty()) {
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
			mv.addObject("msg", "관리자에게 문의 바랍니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url", "/board/list.kh");
			mv.setViewName("/common/errorPage");
		}
		return mv;
	}

	/**
	 * 번호로 게시글 조회
	 * 
	 * @param boardNo
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/board/detail.kh", method = RequestMethod.GET)
	public ModelAndView showDetailForm(@RequestParam("boardNo") Integer boardNo, ModelAndView mv) {
		try {
			Board boardOne = bService.selectBoardByNo(boardNo);
			if (boardOne != null) {
				List<Reply> replyList = rService.selectReplyList(boardNo);
				if (replyList.size() > 0) {
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
	 * 파일 이름/리네임/경로/크기 구하기
	 * 
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
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		// 시간으로 파일 리네임하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileRename = sdf.format(new Date(System.currentTimeMillis())) + "." + extension;

		// 파일 저장 전 폴더 만들기
		File saveFolder = new File(savePath);
		// 유효성 검사, 저장폴더가 없다면!
		// 보통 한번만 만들면 되어서 if를 사용할 필요가 없지만 사용자마다 폴더가 필요하다면
		// if 문으로 유효성 검사를 해야한다.
		if (!saveFolder.exists()) {
			saveFolder.mkdir();
		}

		// 파일 저장
		File saveFile = new File(savePath + "\\" + fileRename);
		uploadFile.transferTo(saveFile); // checked Exception -> throws Exception

		// 파일 크기
		long fileLength = uploadFile.getSize();

		// 파일 리턴
		fileMap.put("fileName", fileName);
		fileMap.put("fileRename", fileRename);
		fileMap.put("filePath", "../resources/buploadFiles/" + fileRename);
		fileMap.put("fileLength", fileLength);

		return fileMap;
	}

	/**
	 * 파일 삭제 메소드
	 * 
	 * @param fileRename
	 * @param request
	 */
	private void deleteFile(String fileRename, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String delPath = root + "\\buploadFiles\\" + fileRename;
		File delFile = new File(delPath);
		if (delFile.exists()) {
			delFile.delete();
		}
	}

	/**
	 * 페이징 정보
	 * 
	 * @param currentPage
	 * @param totalCount
	 * @return PageInfo
	 */
	private PageInfo getPageInfo(Integer currentPage, Integer totalCount) {
		int recordCountPerPage = 10;
		int naviCountPerPage = 10;
		// 범위(네비)의 총 갯수 구하기
		// Math.ceil 로 반올림 해서 +1 을 해 줄 필요가 없음.
		int naviTotalCount = (int) Math.ceil((double) totalCount / recordCountPerPage);
		;
		int startNavi = ((int) ((double) currentPage / naviCountPerPage + 0.9) - 1) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if (endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}

		PageInfo pInfo = new PageInfo(currentPage, totalCount, naviTotalCount, recordCountPerPage, naviCountPerPage,
				startNavi, endNavi);

		return pInfo;
	}

}
