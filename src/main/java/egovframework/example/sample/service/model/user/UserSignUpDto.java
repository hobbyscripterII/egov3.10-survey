package egovframework.example.sample.service.model.user;

public class UserSignUpDto {
	private String id;
	private String pwd;
	private String pwdChk;
	private String name;
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getPwd() { return pwd; }
	public void setPwd(String pwd) { this.pwd = pwd; }
	public String getPwdChk() { return pwdChk; }
	public void setPwdChk(String pwdChk) { this.pwdChk = pwdChk; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@Override public String toString() { return "UserSignUpDto [id=" + id + ", pwd=" + pwd + ", pwdChk=" + pwdChk + ", name=" + name + "]"; }
}
