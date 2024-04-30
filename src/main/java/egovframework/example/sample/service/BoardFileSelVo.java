package egovframework.example.sample.service;

public class BoardFileSelVo {
	private String originalName;
	private String savedName;
	private String ext;
	
	public String getOriginalName() { return originalName; }
	public void setOriginalName(String originalName) { this.originalName = originalName; }
	public String getSavedName() { return savedName; }
	public void setSavedName(String savedName) { this.savedName = savedName; }
	public String getExt() { return ext; }
	public void setExt(String ext) { this.ext = ext; }
	
	@Override public String toString() { return "BoardFileSelVo [originalName=" + originalName + ", savedName=" + savedName + ", ext=" + ext + "]"; }
}
