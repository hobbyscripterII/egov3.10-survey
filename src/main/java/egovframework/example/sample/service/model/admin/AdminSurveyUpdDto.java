package egovframework.example.sample.service.model.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AdminSurveyUpdDto {
	private int isurvey;
	private int iuser;
	private String title;
	private String descript;
	private String startedAt;
	private String finishedAt;
	private List<AdminSurveyQuestionUpdDto> list = new ArrayList();
	
	public int getIsurvey() { return isurvey; }
	public void setIsurvey(int isurvey) { this.isurvey = isurvey; }
	public int getIuser() { return iuser; }
	public void setIuser(int iuser) { this.iuser = iuser; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescript() { return descript; }
	public void setDescript(String descript) { this.descript = descript; }
	public String getStartedAt() { return startedAt; }
	public void setStartedAt(String startedAt) { this.startedAt = startedAt; }
	public String getFinishedAt() { return finishedAt; }
	public void setFinishedAt(String finishedAt) { this.finishedAt = finishedAt; }
	public List<AdminSurveyQuestionUpdDto> getList() { return list; }
	public void setList(List<AdminSurveyQuestionUpdDto> list) { this.list = list; }
	
	@Override public String toString() { return "AdminSurveyUpdDto [isurvey=" + isurvey + ", iuser=" + iuser + ", title=" + title + ", descript=" + descript + ", startedAt=" + startedAt + ", finishedAt=" + finishedAt + ", list=" + list + "]"; }

	public static class AdminSurveyQuestionUpdDto {
		private int iquestion;
		private int iresponseformat;
		private String name; // 질문 제목
		private String imgPath;
		private MultipartFile file; // 질문 사진
		private String requiredFl; // 필수 문항 여부
		private List<AdminSurveyResponseUpdDto> list = new ArrayList();
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getImgPath() { return imgPath; }
		public void setImgPath(String imgPath) { this.imgPath = imgPath; }
		public MultipartFile getFile() { return file; }
		public void setFile(MultipartFile file) { this.file = file; }
		public String getRequiredFl() { return requiredFl; }
		public void setRequiredFl(String requiredFl) { this.requiredFl = requiredFl; }
		public List<AdminSurveyResponseUpdDto> getList() { return list; }
		public void setList(List<AdminSurveyResponseUpdDto> list) { this.list = list; }
		
		@Override public String toString() { return "AdminSurveyQuestionUpdDto [iquestion=" + iquestion + ", name=" + name + ", imgPath=" + imgPath + ", file=" + file + ", requiredFl=" + requiredFl + ", list=" + list + "]"; }
	}
	
	public static class AdminSurveyResponseUpdDto {
		private int ioption;
		private int iresponseformat;
		private String optionName;
		
		public int getIoption() { return ioption; }
		public void setIoption(int ioption) { this.ioption = ioption; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public String getOptionName() { return optionName; }
		public void setOptionName(String optionName) { this.optionName = optionName; }
		
		@Override public String toString() { return "AdminSurveyResponseUpdDto [ioption=" + ioption + ", iresponseformat=" + iresponseformat + ", optionName=" + optionName + "]"; }
	}
	
	public static class AdminSurveyQuestionImgUpdDto {
		private int iquestion;
		private String imgPath;
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public String getImgPath() { return imgPath; }
		public void setImgPath(String imgPath) { this.imgPath = imgPath; }
		
		@Override public String toString() { return "AdminSurveyQuestionImgUpdDto [iquestion=" + iquestion + ", imgPath=" + imgPath + "]"; }
	}
	
	public static class AdminSurveyResponseFormatUpdDto {
		private int iquestion;
		private int iresponseformat;
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		
		@Override public String toString() { return "AdminSurveyResponseFormatUpdDto [iquestion=" + iquestion + ", iresponseformat=" + iresponseformat + "]"; }
	}
}
