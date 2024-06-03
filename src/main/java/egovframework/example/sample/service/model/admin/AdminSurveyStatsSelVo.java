package egovframework.example.sample.service.model.admin;

import java.util.ArrayList;
import java.util.List;

public class AdminSurveyStatsSelVo {
	private int isurvey;
	private String surveyTitle;
	private String surveyDescript;
	private String surveyUseFl;
	private String startedAt;
	private String finishedAt;
	private String createdAt;
	private List<AdminSurveyQuestionStatsGetVo> list = new ArrayList();
	
	public int getIsurvey() { return isurvey; }
	public void setIsurvey(int isurvey) { this.isurvey = isurvey; }
	public String getSurveyTitle() { return surveyTitle; }
	public void setSurveyTitle(String surveyTitle) { this.surveyTitle = surveyTitle; }
	public String getSurveyDescript() { return surveyDescript; }
	public void setSurveyDescript(String surveyDescript) { this.surveyDescript = surveyDescript; }
	public String getSurveyUseFl() { return surveyUseFl; }
	public void setSurveyUseFl(String surveyUseFl) { this.surveyUseFl = surveyUseFl; }
	public String getStartedAt() { return startedAt; }
	public void setStartedAt(String startedAt) { this.startedAt = startedAt; }
	public String getFinishedAt() { return finishedAt; }
	public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }
	public String getCreatedAt() { return createdAt; }
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
	public List<AdminSurveyQuestionStatsGetVo> getList() { return list; }
	public void setList(List<AdminSurveyQuestionStatsGetVo> list) { this.list = list; }

	@Override public String toString() { return "AdminSurveyStatsSelVo [isurvey=" + isurvey + ", surveyTitle=" + surveyTitle + ", surveyDescript=" + surveyDescript + ", surveyUseFl=" + surveyUseFl + ", startedAt=" + startedAt + ", finishedAt=" + finishedAt + ", createdAt=" + createdAt + ", list=" + list + "]"; }

	public static class AdminSurveyQuestionStatsGetVo {
		private int iquestion;
		private String questionName;
		private List<AdminMultipleResponseStatsGetVo> multipleResponseList = new ArrayList();
		private List<AdminAnswereResponseStatsGetVo> answerResponseList = new ArrayList();
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public String getQuestionName() { return questionName; }
		public void setQuestionName(String questionName) { this.questionName = questionName; }
		public List<AdminMultipleResponseStatsGetVo> getMultipleResponseList() { return multipleResponseList; }
		public void setMultipleResponseList(List<AdminMultipleResponseStatsGetVo> multipleResponseList) { this.multipleResponseList = multipleResponseList; }
		public List<AdminAnswereResponseStatsGetVo> getAnswerResponseList() { return answerResponseList; }
		public void setAnswerResponseList(List<AdminAnswereResponseStatsGetVo> answerResponseList) { this.answerResponseList = answerResponseList; }
		
		@Override public String toString() { return "AdminSurveyQuestionStatsGetVo [iquestion=" + iquestion + ", questionName=" + questionName + ", multipleResponseList=" + multipleResponseList + ", answerResponseList=" + answerResponseList + "]"; }
	}
	
	public static class AdminAnswereResponseStatsGetVo {
		private String responseContents;

		public String getResponseContents() { return responseContents; }
		public void setResponseContents(String responseContents) { this.responseContents = responseContents; }

		@Override public String toString() { return "AdminAnswereResponseStatsGetVo [responseContents=" + responseContents + "]"; }
	}
	
	public static class AdminMultipleResponseStatsGetVo {
		private int iquestion;
		private int iresponseformat;
		private int ioption;
		private int responseSum;
		private double responsePercentage;
		private String responseOptionName;
		private String responseContents;
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public int getIoption() { return ioption; }
		public void setIoption(int ioption) { this.ioption = ioption; }
		public int getResponseSum() { return responseSum; }
		public void setResponseSum(int responseSum) { this.responseSum = responseSum; }
		public double getResponsePercentage() { return responsePercentage; }
		public void setResponsePercentage(double responsePercentage) { this.responsePercentage = responsePercentage; }
		public String getResponseOptionName() { return responseOptionName; }
		public void setResponseOptionName(String responseOptionName) { this.responseOptionName = responseOptionName; }
		public String getResponseContents() { return responseContents; }
		public void setResponseContents(String responseContents) { this.responseContents = responseContents; }
		
		@Override public String toString() { return "AdminSurveyResponseStatsGetVo [iquestion=" + iquestion + ", iresponseformat=" + iresponseformat + ", ioption=" + ioption + ", responseSum=" + responseSum + ", responsePercentage=" + responsePercentage + ", responseOptionName=" + responseOptionName + ", responseContents=" + responseContents + "]"; }
	}
}
