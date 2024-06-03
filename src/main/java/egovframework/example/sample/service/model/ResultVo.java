package egovframework.example.sample.service.model;

public class ResultVo {
	private int result;
	private String msg;
	
	public ResultVo(int result, String msg) {
		this.result = result;
		this.msg = msg;
	}
	
	public int getResult() { return result; }
	public void setResult(int result) { this.result = result; }
	public String getMsg() { return msg; }
	public void setMsg(String msg) { this.msg = msg; }
	
	@Override public String toString() { return "ResultVo [msg=" + msg + ", result=" + result + "]"; }
}
