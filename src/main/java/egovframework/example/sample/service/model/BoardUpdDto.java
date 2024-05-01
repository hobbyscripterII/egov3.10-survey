package egovframework.example.sample.service.model;

import java.util.ArrayList;
import java.util.List;

public class BoardUpdDto {
	private int iboard;
	private String title;
	private String contents;
	private List<Integer> deleteIfileList = new ArrayList();
	private List<BoardFileInsDto> file;
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }
	public List<Integer> getDeleteIfileList() { return deleteIfileList; }
	public void setDeleteIfileList(List<Integer> deleteIfileList) { this.deleteIfileList = deleteIfileList; }
	public List<BoardFileInsDto> getFile() { return file; }
	public void setFile(List<BoardFileInsDto> file) { this.file = file; }

	@Override public String toString() { return "BoardUpdDto [iboard=" + iboard + ", title=" + title + ", contents=" + contents + ", deleteIfileList=" + deleteIfileList + ", file=" + file + "]"; }
}
