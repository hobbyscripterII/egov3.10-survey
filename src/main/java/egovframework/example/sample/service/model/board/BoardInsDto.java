package egovframework.example.sample.service.model.board;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BoardInsDto {
	private int iboard; // insert 후 pk를 받아오기 위함
	private int iuser; // 회원 pk (2차 과제 때 추가)
	private int category; // 게시판 카테고리 식별코드
	private int code; // 답변글 식별코드(게시글 pk)
	private String name; // Validation 적용 
//	private String pwd; // Validation 적용 / 회원 테이블 pk 참조 후 필요 x
	private String title; // Validation 적용
	private String contents; // Validation 적용
	private String replyFl; // 답변글 여부
	private List<BoardFileInsDto> file; // 첨부파일 목록
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }
	public int getCategory() { return category; }
	public void setCategory(int category) { this.category = category; }
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code; }
  	public String getName() { return name; }
  	public void setName(String name) { this.name = name; }
//	public String getPwd() { return pwd; }
//	public void setPwd(String pwd) { this.pwd = pwd; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }
	public String getReplyFl() { return replyFl; }
	public void setReplyFl(String replyFl) { this.replyFl = replyFl; }
	public List<BoardFileInsDto> getFile() { return file; }
	public void setFile(List<BoardFileInsDto> file) { this.file = file; }
	
	@Override public String toString() { return "BoardInsDto [iboard=" + iboard + ", iuser=" + iuser + ", category=" + category + ", code=" + code + ", name=" + name + ", title=" + title + ", contents=" + contents + ", replyFl=" + replyFl + ", file=" + file + "]"; }
}