package egovframework.example.sample.service.model.survey;

import java.util.ArrayList;
import java.util.List;

public class SurveySelVo {
	private int isurvey;
	private String surveyTitle;
	private String surveyDescript;
	private String name; // 회원 이름
	private String startedAt;
	private String finishedAt;
	List<SurveyQuestionGetVo> list = new ArrayList();
	
	public int getIsurvey() { return isurvey; }
	public void setIsurvey(int isurvey) { this.isurvey = isurvey; }
	public String getSurveyTitle() { return surveyTitle; }
	public void setSurveyTitle(String surveyTitle) { this.surveyTitle = surveyTitle; }
	public String getSurveyDescript() { return surveyDescript; }
	public void setSurveyDescript(String surveyDescript) { this.surveyDescript = surveyDescript; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getStartedAt() { return startedAt; }
	public void setStartedAt(String startedAt) { this.startedAt = startedAt; }
	public String getFinishedAt() { return finishedAt; }
	public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }
	public List<SurveyQuestionGetVo> getList() { return list; }
	public void setList(List<SurveyQuestionGetVo> list) { this.list = list; }
	
	@Override public String toString() { return "SurveySelVo [isurvey=" + isurvey + ", surveyTitle=" + surveyTitle + ", surveyDescript=" + surveyDescript + ", name=" + name + ", startedAt=" + startedAt + ", finishedAt=" + finishedAt + ", list=" + list + "]"; }

	public static class SurveyQuestionGetVo {
		private int iquestion;
		private String questionName;
		private String questionDescript;
		private String questionImg;
		private String requiredFl;
		private List<SurveyQuestionResponseOptionGetVo> list = new ArrayList<SurveyQuestionResponseOptionGetVo>();
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public String getQuestionName() { return questionName; }
		public void setQuestionName(String questionName) { this.questionName = questionName; }
		public String getQuestionDescript() { return questionDescript; }
		public void setQuestionDescript(String questionDescript) { this.questionDescript = questionDescript; }
		public String getQuestionImg() { return questionImg; }
		public void setQuestionImg(String questionImg) { this.questionImg = questionImg; }
		public String getRequiredFl() { return requiredFl; }
		public void setRequiredFl(String requiredFl) { this.requiredFl = requiredFl; }
		public List<SurveyQuestionResponseOptionGetVo> getList() { return list; }
		public void setList(List<SurveyQuestionResponseOptionGetVo> list) { this.list = list; }
		
		@Override public String toString() { return "SurveyQuestionGetVo [iquestion=" + iquestion + ", questionName=" + questionName + ", questionDescript=" + questionDescript + ", questionImg=" + questionImg + ", requiredFl=" + requiredFl + ", list=" + list + "]"; }
	}
	
	public static class SurveyQuestionResponseOptionGetVo {
		private int ioption;
		private int iquestion;
		private int iresponseformat;
		private String responseOptionName;
		
		public int getIoption() { return ioption; }
		public void setIoption(int ioption) { this.ioption = ioption; }
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public String getResponseOptionName() { return responseOptionName; }
		public void setResponseOptionName(String responseOptionName) { this.responseOptionName = responseOptionName; }
		
		@Override public String toString() { return "SurveyQuestionResponseOptionGetVo [ioption=" + ioption + ", iquestion=" + iquestion + ", iresponseformat=" + iresponseformat + ", responseOptionName=" + responseOptionName + "]"; }
	}
}
