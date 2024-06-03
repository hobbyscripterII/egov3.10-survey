package egovframework.example.sample.service.model.photo;

public class UpdPhotoBoardFileThumbnailUnFlDto {
	private int iboard;
	private int ifile;
	
	public UpdPhotoBoardFileThumbnailUnFlDto(int iboard, int ifile) { this.iboard = iboard; this.ifile = ifile; }
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getIfile() { return ifile; }
	public void setIfile(int ifile) { this.ifile = ifile; }
	
	@Override public String toString() { return "UpdPhotoBoardFileThumbnailUnFlDto [iboard=" + iboard + ", ifile=" + ifile + "]"; }
}
