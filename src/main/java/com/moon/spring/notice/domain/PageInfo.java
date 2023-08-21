package com.moon.spring.notice.domain;

// 페이징 처리 관련 VO
public class PageInfo {
	
	// 필드
	private int currentPage;
	private int recordCoutnPerPage;
	private int naviCountPerPage;
	private int startNavi;
	private int endNavi;
	private int totalCount;
	private int naviTotalCount;
	
	// 기본 생성자
	public PageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// 매개변수 생성자
	public PageInfo(int currentPage, int recordCoutnPerPage, int naviCountPerPage, int startNavi, int endNavi,
			int totalCount, int naviTotalCount) {
		super();
		this.currentPage = currentPage;
		this.recordCoutnPerPage = recordCoutnPerPage;
		this.naviCountPerPage = naviCountPerPage;
		this.startNavi = startNavi;
		this.endNavi = endNavi;
		this.totalCount = totalCount;
		this.naviTotalCount = naviTotalCount;
	}
	

	// getter, setter 메소드
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRecordCoutnPerPage() {
		return recordCoutnPerPage;
	}

	public void setRecordCoutnPerPage(int recordCoutnPerPage) {
		this.recordCoutnPerPage = recordCoutnPerPage;
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
	
	// toString() - 데이터 확인용
	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", naviCountPerPage=" + naviCountPerPage + ", startNavi="
				+ startNavi + ", endNavi=" + endNavi + ", totalCount=" + totalCount + ", naviTotalCount="
				+ naviTotalCount + "]";
	}
}
