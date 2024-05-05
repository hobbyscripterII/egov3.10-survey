package egovframework.example.sample.service.model;

public class PhotoInsNullDto {
	private int iboard;
	private int category;
	private int iuser;
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getCategory() { return category; }
	public void setCategory(int category) { this.category = category; }
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }

	@Override public String toString() { return "PhotoInsNullDto [iboard=" + iboard + ", category=" + category + ", iuser=" + iuser + "]"; }
}