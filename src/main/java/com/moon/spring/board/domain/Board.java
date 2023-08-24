package com.moon.spring.board.domain;

import java.util.Date;

public class Board {
	
	// 필드
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardFilename;
	private String boardFileRename;
	private String boardFilepath;
	private long boardFileLength;
	private int boardCount;
	private Date bCreateDate;
	private Date bUpdateDate;
	private char bStatus;
	
	// getter, setter 메소드
		public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public String getBoardFilename() {
		return boardFilename;
	}

	public void setBoardFilename(String boardFileName) {
		this.boardFilename = boardFileName;
	}

	public String getBoardFileRename() {
		return boardFileRename;
	}

	public void setBoardFileRename(String boardFileRename) {
		this.boardFileRename = boardFileRename;
	}

	public String getBoardFilepath() {
		return boardFilepath;
	}

	public void setBoardFilepath(String boardFilepath) {
		this.boardFilepath = boardFilepath;
	}

	public long getBoardFileLength() {
		return boardFileLength;
	}

	public void setBoardFileLength(long boardFileLength) {
		this.boardFileLength = boardFileLength;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public Date getbCreateDate() {
		return bCreateDate;
	}

	public void setbCreateDate(Date bCreateDate) {
		this.bCreateDate = bCreateDate;
	}

	public Date getbUpdateDate() {
		return bUpdateDate;
	}

	public void setbUpdateDate(Date bUpdateDate) {
		this.bUpdateDate = bUpdateDate;
	}

	public char getbStatus() {
		return bStatus;
	}

	public void setbStatus(char bStatus) {
		this.bStatus = bStatus;
	}
	
	// toString() - 데이터 확인용
		@Override
		public String toString() {
			return "게시글 [번호=" + boardNo + ", 제목=" + boardTitle + ", 내용=" + boardContent
					+ ", 작성자=" + boardWriter + ", 파일이름=" + boardFilename + ", 파일리네임=" + boardFileRename
					+ ", 파일경로=" + boardFilepath + ", 파일크기=" + boardFileLength + ", 파일크기=" + boardCount
					+ ", 작성일=" + bCreateDate + ", 수정일="
					+ bUpdateDate + ", 사용여부=" + bStatus + "]";
		}
}