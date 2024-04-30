package egovframework.example.sample.service;

public class BoardChkPwdDto {
	private int iboard;
	private String pwd;
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }
	
	@Override public String toString() { return "BoardChkPwdDto [iboard=" + iboard + ", pwd=" + pwd + "]"; }
}
