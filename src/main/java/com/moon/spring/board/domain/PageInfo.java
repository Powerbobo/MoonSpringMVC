package com.moon.spring.board.domain;

public class PageInfo {

	// 필드
	private int currentPage;		// 현재 목록의 페이지 번호
	private int totalCount;			// 게시물의 총 갯수 *
	private int naviTotalCount;		// 네비 총 갯수(올림 처리)
	private int recordCountPerPage;	// 전체 게시물의 갯수
	private int naviCountPerPage;
	private int startNavi;
	private int endNavi;
	
	// 기본 생성자
	public PageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 매개변수 생성자
	public PageInfo(int currentPage, int totalCount, int naviTotalCount, int recordCountPerPage, int naviCountPerPage,
			int startNavi, int endNavi) {
		super();
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.naviTotalCount = naviTotalCount;
		this.recordCountPerPage = recordCountPerPage;
		this.naviCountPerPage = naviCountPerPage;
		this.startNavi = startNavi;
		this.endNavi = endNavi;
	}

	// getter, setter 메소드
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNaviTotalCount() {
		return naviTotalCount;
	}

	public void setNaviTotalCount(int naviTotalCount) {
		this.naviTotalCount = naviTotalCount;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getNaviCountPerPage() {
		return naviCountPerPage;
	}

	public void setNaviCountPerPage(int naviCountPerPage) {
		this.naviCountPerPage = naviCountPerPage;
	}

	public int getStartNavi() {
		return startNavi;
	}

	public void setStartNavi(int startNavi) {
		this.startNavi = startNavi;
	}

	public int getEndNavi() {
		return endNavi;
	}

	public void setEndNavi(int endNavi) {
		this.endNavi = endNavi;
	}

	// toString() 오버라이딩 - 데이터 확인용
	@Override
	public String toString() {
		return "PageInfo [현재 페이지=" + currentPage + ", 총 게시물 갯수=" + totalCount + ", 네비 총 갯수="
				+ naviTotalCount + ", 한 페이지당 게시물갯수=" + recordCountPerPage + ", 한 페이지당 보여질 페이지의 갯수="
				+ naviCountPerPage + ", 각 페이지 시작 번호=" + startNavi + ", 각 페이지 마지막 번호=" + endNavi + "]";
	}
}
