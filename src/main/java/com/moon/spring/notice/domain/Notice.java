package com.moon.spring.notice.domain;

import java.sql.Timestamp;

public class Notice {
	
	// 필드
	private int noticeNo;
	private String noticeSubject;
	private String noticeContent;
	private String noticeWriter;
	private Timestamp nCreateDate;
	private Timestamp nUpdateDate;
	private String noticeFilename;
	private String noticeFileRename;
	private String noticeFilepath;
	private long noticeFilelength;
	
	// 기본 생성자
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 매개변수 생성자
	public Notice(int noticeNo, String noticeSubject, String noticeContent, String noticeWriter, Timestamp nCreateDate,
			Timestamp nUpdateDate, String noticeFilename, String noticeFileRename, String noticeFilepath,
			long noticeFilelength) {
		super();
		this.noticeNo = noticeNo;
		this.noticeSubject = noticeSubject;
		this.noticeContent = noticeContent;
		this.noticeWriter = noticeWriter;
		this.nCreateDate = nCreateDate;
		this.nUpdateDate = nUpdateDate;
		this.noticeFilename = noticeFilename;
		this.noticeFileRename = noticeFileRename;
		this.noticeFilepath = noticeFilepath;
		this.noticeFilelength = noticeFilelength;
	}


	// getter, setter 메소드
	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeSubject() {
		return noticeSubject;
	}

	public void setNoticeSubject(String noticeSubject) {
		this.noticeSubject = noticeSubject;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeWriter() {
		return noticeWriter;
	}

	public void setNoticeWriter(String noticeWriter) {
		this.noticeWriter = noticeWriter;
	}

	public Timestamp getnCreateDate() {
		return nCreateDate;
	}

	public void setnCreateDate(Timestamp nCreateDate) {
		this.nCreateDate = nCreateDate;
	}

	public Timestamp getnUpdateDate() {
		return nUpdateDate;
	}

	public void setnUpdateDate(Timestamp nUpdateDate) {
		this.nUpdateDate = nUpdateDate;
	}

	public String getNoticeFilename() {
		return noticeFilename;
	}

	public void setNoticeFilename(String noticeFilename) {
		this.noticeFilename = noticeFilename;
	}

	public String getNoticeFilepath() {
		return noticeFilepath;
	}

	public void setNoticeFilepath(String noticeFilepath) {
		this.noticeFilepath = noticeFilepath;
	}

	public long getNoticeFilelength() {
		return noticeFilelength;
	}

	public void setNoticeFilelength(long noticeFilelength) {
		this.noticeFilelength = noticeFilelength;
	}

	public String getNoticeFileRename() {
		return noticeFileRename;
	}

	public void setNoticeFileRename(String noticeFileRename) {
		this.noticeFileRename = noticeFileRename;
	}

	// toString() - 정보 확인용
	@Override
	public String toString() {
		return "공지사항 [번호=" + noticeNo + ", 제목=" + noticeSubject + ", 내용=" + noticeContent
				+ ", 작성자=" + noticeWriter + ", 작성일=" + nCreateDate + ", 수정일=" + nUpdateDate
				+ ", 파일이름=" + noticeFilename + ", 파일리네임=" + noticeFileRename 
				+ ", 파일경로=" + noticeFilepath + ", 파일길이=" + noticeFilelength + "]";
	}
	
}
