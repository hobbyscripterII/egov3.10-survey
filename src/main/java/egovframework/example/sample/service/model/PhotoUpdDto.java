package egovframework.example.sample.service.model;

import java.util.Arrays;

public class PhotoUpdDto {
	private int iboard;
	private int iuser;
	private String name; // 게시글 등록 시 이름 출력을 위해 값 잠깐 담아놓는 용 / 실제 insert 시 사용되진 않음
	private String title;
	private String contents;
	private int thumbnail;
	private int[] deleteFileArr; // 사용자가 삭제 버튼 누른 이미지의 pk가 담긴 배열
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }
	public int getThumbnail() { return thumbnail; }
	public void setThumbnail(int thumbnail) { this.thumbnail = thumbnail; }
	public int[] getDeleteFileArr() { return deleteFileArr; }
	public void setDeleteFileArr(int[] deleteFileArr) { this.deleteFileArr = deleteFileArr; }
	
	@Override public String toString() { return "PhotoUpdDto [iboard=" + iboard + ", iuser=" + iuser + ", name=" + name + ", title=" + title + ", contents=" + contents + ", thumbnail=" + thumbnail + ", deleteFileArr=" + Arrays.toString(deleteFileArr) + "]"; }
}
