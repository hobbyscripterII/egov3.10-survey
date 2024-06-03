package egovframework.example.sample.service.model.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AdminSurveyInsDto {
	private int isurvey;
	private int iuser;
	private String title;
	private String descript;
	private String startedAt;
	private String finishedAt;
	private List<AdminSurveyQuestionInsDto> list = new ArrayList();
	
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
	public List<AdminSurveyQuestionInsDto> getList() { return list; }
	public void setList(List<AdminSurveyQuestionInsDto> list) { this.list = list; }
	
	@Override public String toString() { return "AdminSurveyInsDto [isurvey=" + isurvey + ", iuser=" + iuser + ", title=" + title + ", descript=" + descript + ", startedAt=" + startedAt + ", finishedAt=" + finishedAt + ", list=" + list + "]"; }

	public static class AdminSurveyQuestionInsDto {
		private int iquestion;
		private int isurvey;
		private int ioption;
		private int iresponseformat;
		private String name; // 질문 제목
//		private String descript; // 사용 x
		private String imgName;
		private String imgPath;
		private MultipartFile file; // 질문 사진
		private String requiredFl; // 필수 문항
		private List<AdminSurveyResponseInsDto> list = new ArrayList();
		
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIsurvey() { return isurvey; }
		public void setIsurvey(int isurvey) { this.isurvey = isurvey; }
		public int getIoption() { return ioption; }
		public void setIoption(int ioption) { this.ioption = ioption; }
		public int getIresponseformat() { return iresponseformat; 	}
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getImgName() { return imgName; }
		public void setImgName(String imgName) { this.imgName = imgName; }
		public String getImgPath() { return imgPath; }
		public void setImgPath(String imgPath) { this.imgPath = imgPath; }
		public MultipartFile getFile() { return file; }
		public void setFile(MultipartFile file) { this.file = file; }
		public String getRequiredFl() { return requiredFl; }
		public void setRequiredFl(String requiredFl) { this.requiredFl = requiredFl; }
		public List<AdminSurveyResponseInsDto> getList() { return list; }
		public void setList(List<AdminSurveyResponseInsDto> list) { this.list = list; }
		
		@Override public String toString() { return "AdminSurveyQuestionInsDto [iquestion=" + iquestion + ", isurvey=" + isurvey + ", ioption=" + ioption + ", iresponseformat=" + iresponseformat + ", name=" + name + ", imgName=" + imgName + ", imgPath=" + imgPath + ", file=" + file + ", requiredFl=" + requiredFl + ", list=" + list + "]"; }
	}
	
	public static class AdminSurveyResponseInsDto {
		private int iuser;
		private int iquestion;
		private int iresponseformat;
		private String optionName;
		
		public int getIuser() { return iuser; }
		public void setIuser(int iuser) { this.iuser = iuser; }
		public int getIquestion() { return iquestion; }
		public void setIquestion(int iquestion) { this.iquestion = iquestion; }
		public int getIresponseformat() { return iresponseformat; }
		public void setIresponseformat(int iresponseformat) { this.iresponseformat = iresponseformat; }
		public String getOptionName() { return optionName; }
		public void setOptionName(String optionName) { this.optionName = optionName; }
		
		@Override public String toString() { return "AdminSurveyResponseInsDto [iuser=" + iuser + ", iquestion=" + iquestion + ", iresponseformat=" + iresponseformat + ", optionName=" + optionName + "]"; }
	}
}
