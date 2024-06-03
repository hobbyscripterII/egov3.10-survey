package egovframework.example.sample.service.model.survey;

import java.util.ArrayList;
import java.util.List;

public class SurveyResponseInsDto {
	private int iuser;
	private List<SurveyQuestionResponseDto> list = new ArrayList();
	
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }
	public List<SurveyQuestionResponseDto> getList() { return list; }
	public void setList(List<SurveyQuestionResponseDto> list) { this.list = list; }

	@Override public String toString() { return "SurveyResponseInsDto [iuser=" + iuser + ", list=" + list + "]"; }

	public static class SurveyQuestionResponseDto {
		private int iquestion;
		private int ioption;
		private int iresponseformat;
		private String responseContents;
		private String requiredFl;
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIoption() { return ioption; }
		public void setIoption(int ioption) { this.ioption = ioption; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public String getResponseContents() { return responseContents; }
		public void setResponseContents(String responseContents) { this.responseContents = responseContents; }
		public String getRequiredFl() { return requiredFl; }
		public void setRequiredFl(String requiredFl) { this.requiredFl = requiredFl; }
		
		@Override public String toString() { return "SurveyQuestionResponseDto [iquestion=" + iquestion + ", ioption=" + ioption + ", iresponseformat=" + iresponseformat + ", responseContents=" + responseContents + ", requiredFl=" + requiredFl + "]"; }
	}
}
