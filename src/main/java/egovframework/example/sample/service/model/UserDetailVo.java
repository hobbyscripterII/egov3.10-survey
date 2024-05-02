package egovframework.example.sample.service.model;

public class UserDetailVo {
	private int iuser;
	private String role;
	
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	
	@Override public String toString() { return "UserDetailVo [iuser=" + iuser + ", role=" + role + "]"; }
}
