package egovframework.example.sample.service.model.user;

public class UserDetailVo {
	private int iuser;
	private String pwd;
	private String name;
	private String role;
	
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }
	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	
	@Override public String toString() { return "UserDetailVo [iuser=" + iuser + ", pwd=" + pwd + ", name=" + name + ", role=" + role + "]"; }
}
