package egovframework.example.sample.service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BoardInsDto {
	private int iboard; // insert 후 pk를 받아오기 위함
	private int code; // 답변글 식별코드(게시글 pk)
	private String name; // Validation 적용
	private String pwd; // Validation 적용
	private String title; // Validation 적용
	private String contents; // Validation 적용
	private String replyFl; // 답변글 여부
	private List<BoardFileInsDto> file; // 첨부파일 목록
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }
	public String getReplyFl() { return replyFl; }
	public void setReplyFl(String replyFl) { this.replyFl = replyFl; }
	public List<BoardFileInsDto> getFile() { return file; }
	public void setFile(List<BoardFileInsDto> file) { this.file = file; }
	
	@Override public String toString() { return "BoardInsDto [iboard=" + iboard + ", code=" + code + ", name=" + name + ", pwd=" + pwd + ", title=" + title + ", contents=" + contents + ", replyFl=" + replyFl + ", file=" + file + "]"; }
}