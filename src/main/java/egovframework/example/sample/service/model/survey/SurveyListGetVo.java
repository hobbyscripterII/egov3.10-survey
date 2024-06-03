package egovframework.example.sample.service.model.survey;

public class SurveyListGetVo {
	private int isurvey;
	private String surveyTitle;
	private String surveyDescript;
	private String startedAt;
	private String finishedAt;
	
	public int getIsurvey() { return isurvey; }
	public void setIsurvey(int isurvey) { this.isurvey = isurvey; }
	public String getSurveyTitle() { return surveyTitle; }
	public void setSurveyTitle(String surveyTitle) { this.surveyTitle = surveyTitle; }
	public String getSurveyDescript() { return surveyDescript; }
	public void setSurveyDescript(String surveyDescript) { this.surveyDescript = surveyDescript; }
	public String getStartedAt() { return startedAt; }
	public void setStartedAt(String startedAt) { this.startedAt = startedAt; }
	public String getFinishedAt() { return finishedAt; }
	public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }
	
	@Override public String toString() { return "SurveyListGetVo [isurvey=" + isurvey + ", surveyTitle=" + surveyTitle + ", surveyDescript=" + surveyDescript + ", startedAt=" + startedAt + ", finishedAt=" + finishedAt + "]"; }
}
