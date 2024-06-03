package egovframework.example.sample.service.model.board;

public class BoardReplyUpdDto {
	private int iboard;
	private int code;
	private String replyFl;
	
	public int getIboard() { return iboard; }
	public void setIboard(int iboard) { this.iboard = iboard; }
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code; }
	public String getReplyFl() { return replyFl; }
	public void setReplyFl(String replyFl) { this.replyFl = replyFl; }
	
	@Override public String toString() { return "BoardReplyUpdDto [iboard=" + iboard + ", replyFl=" + replyFl + "]"; }
}
