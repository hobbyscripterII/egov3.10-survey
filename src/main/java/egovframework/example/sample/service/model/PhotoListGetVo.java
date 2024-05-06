package egovframework.example.sample.service.model;

public class PhotoListGetVo {
	private int iboard;
	private String title;
	private String createdAt;
	private int view;
	private String thumbnail;
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getCreatedAt() { return createdAt; }
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
	public int getView() { return view; }
	public void setView(int view) { this.view = view; }
	public String getThumbnail() { return thumbnail; }
	public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

	@Override public String toString() { return "PhotoListGetVo [iboard=" + iboard + ", title=" + title + ", createdAt=" + createdAt + ", view=" + view + ", thumbnail=" + thumbnail + "]"; }
}