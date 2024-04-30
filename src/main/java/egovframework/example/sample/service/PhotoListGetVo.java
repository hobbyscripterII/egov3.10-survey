package egovframework.example.sample.service;

public class PhotoListGetVo {
	private int ifile;
	private int iboard;
	private String originalName;
	private String savedName;
	private String ext;
	
	public int getIfile() { return ifile; }
	public void setIfile(int ifile) { this.ifile = ifile; }
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public String getOriginalName() { return originalName; }
	public void setOriginalName(String originalName) { this.originalName = originalName; }
	public String getSavedName() { return savedName; }
	public void setSavedName(String savedName) { this.savedName = savedName; }
	public String getExt() { return ext; }
	public void setExt(String ext) { this.ext = ext; }
	
	@Override public String toString() { return "PhotoListGetVo [ifile=" + ifile + ", iboard=" + iboard + ", originalName=" + originalName + ", savedName=" + savedName + ", ext=" + ext + "]"; }
}