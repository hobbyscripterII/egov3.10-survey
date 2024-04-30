package egovframework.example.sample.service;

import java.util.ArrayList;
import java.util.List;

public class BoardSelVo {
	private int iboard;
	private int code;
	private String name;
	private String title;
	private String contents;
	private List<BoardFileGetVo> files = new ArrayList();
	private String replyFl;
	private int view;
	private String createdAt;
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }
	public List<BoardFileGetVo> getFiles() { return files; }
	public void setFiles(List<BoardFileGetVo> files) { this.files = files; }
	public String getReplyFl() { return replyFl; }
	public void setReplyFl(String replyFl) { this.replyFl = replyFl; }
	public int getView() { return view; }
	public void setView(int view) { this.view = view; }
	public String getCreatedAt() { return createdAt; }
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

	@Override public String toString() { return "BoardSelVo [iboard=" + iboard + ", code=" + code + ", name=" + name + ", title=" + title + ", contents=" + contents + ", files=" + files + ", replyFl=" + replyFl + ", view=" + view + ", createdAt=" + createdAt + "]"; }
}
