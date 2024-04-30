package egovframework.example.sample.service;

public class BoardListGetVo {
	private int total;
	private int iboard;
	private String title;
	private String name;
	private int view;
	private String createdAt;
	private BoardSelVo reply;
	
	public int getTotal() { return total; }
	public void setTotal(int total) { this.total = total; }
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public int getView() { return view; }
	public void setView(int view) { this.view = view; }
	public String getCreatedAt() { return createdAt; }
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
	public BoardSelVo getReply() { return reply; }
	public void setReply(BoardSelVo reply) { this.reply = reply; }
	
	@Override public String toString() { return "BoardListGetVo [total=" + total + ", iboard=" + iboard + ", title=" + title + ", name=" + name + ", view=" + view + ", createdAt=" + createdAt + ", reply=" + reply + "]"; }
}
