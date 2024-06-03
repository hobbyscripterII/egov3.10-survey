package egovframework.example.sample.service.model.admin;

public class AdminSurveyListGetVo {
	private int isurvey;
	private String surveyTitle;
	private String surveyUseFl;
	private String surveyResponseUseFl;
	private String name;
	
	public int getIsurvey() { return isurvey; }
	public void setIsurvey(int isurvey) { this.isurvey = isurvey; }
	public String getSurveyTitle() { return surveyTitle; }
	public void setSurveyTitle(String surveyTitle) { this.surveyTitle = surveyTitle; }
	public String getSurveyResponseUseFl() { return surveyResponseUseFl; }
	public void setSurveyResponseUseFl(String surveyResponseUseFl) { this.surveyResponseUseFl = surveyResponseUseFl; }
	public String getSurveyUseFl() { return surveyUseFl; }
	public void setSurveyUseFl(String surveyUseFl) { this.surveyUseFl = surveyUseFl; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@Override public String toString() { return "AdminSurveyListGetVo [isurvey=" + isurvey + ", surveyTitle=" + surveyTitle + ", surveyUseFl=" + surveyUseFl + ", surveyResponseUseFl=" + surveyResponseUseFl + ", name=" + name + "]"; }
}
