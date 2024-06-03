package egovframework.example.cmmn;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import egovframework.example.sample.service.model.board.BoardFileInsDto;

@Component
public class FileUtils {
	private final MessageSource messageSource;
	private final String prefixPath;
	Logger log = LoggerFactory.getLogger(getClass());
	
	// context-common.xml에 등록된 messageSource 빈을 사용
	public FileUtils(MessageSource messageSource) {
		this.messageSource = messageSource;
		this.prefixPath = messageSource.getMessage("upload.prefix.path", null, Locale.getDefault());
	}

	// 로컬에 저장될 UUID 추출
	private String getFileName() {
		return UUID.randomUUID().toString(); // 고유성이 보장되는 id(UUID) 생성 + string 형변환
	}

	public String getExt(MultipartFile file) {
		String originalFileName = file.getOriginalFilename(); // 첨부파일의 실제 파일명(확장자가 포함되어 있음)
		return originalFileName.substring(originalFileName.lastIndexOf(".")); // substring을 통해 제일 마지막에 있는 . 앞의 문자열을 자름 - 확장자 추출
	}

	private String getPath(String path) {
		Path getPath = Paths.get(prefixPath); // 로컬에 저장할 실제 경로를 가져옴
		String savedPath = getPath.toString(); // 반환하기 위해 string으로 형변환

		if (!Files.exists(getPath)) {
			// 해당 경로가 존재하지 않을 경우 해당 디렉토리를 생성
			try {
				Files.createDirectories(getPath);
			}
			// 생성에 실패할 경우 예외 발생(수정 필요)
			catch (Exception e) {
				throw new RuntimeException();
			}
		}
		return savedPath;
	}

	public String getDownloadPath(String savedName) {
		return Paths.get(prefixPath, savedName).toString(); // 실제 로컬에 저장될 '경로/UUID.확장명'
	}

	public BoardFileInsDto fileUpload(MultipartFile multipartFile) throws Exception {
		// 테이블에 저장할 데이터 추출
		String originalName = FilenameUtils.removeExtension(multipartFile.getOriginalFilename()); // 확장자를 제외한 첨부파일명
		String savedName = getFileName(); // 추출된 UUID
		String ext = getExt(multipartFile); // 첨부파일의 확장자
		String savedPath = getPath(prefixPath); // 로컬에 저장할 경로
		String uploadPath = savedPath + "/" + savedName + ext; // 로컬에 저장할 풀 경로(경로/UUID.확장명)
		int fileSize = (int) multipartFile.getSize(); // 해당 파일의 크기
		BoardFileInsDto dto = new BoardFileInsDto(); // 파일 테이블에 저장 시 사용할 dto 객체 생성
		dto.setOriginalName(originalName);
		dto.setSavedName(savedName);
		dto.setExt(ext);
		dto.setFileSize(fileSize);

		try {
			File file = new File(uploadPath); // 파일을 저장하기 위해 경로를 추상화시킨 File 객체 생성
			multipartFile.transferTo(file); // 해당 경로에 파일 저장
		} catch (Exception e) {
			throw new Exception(); // 서비스에서 처리함
		}
		return dto; // 저장 후 dto 반환
	}

	public void deleteFile(String path) {
		try {
			String fullPath = getDownloadPath(path); // 로컬에 저장된 풀 경로를 가져옴(경로/UUID.확장명)
			File file = new File(fullPath); // 파일을 삭제하기 위해 경로를 추상화시킨 File 객체 생성
			// 파일 경로가 존재하지 않을 경우 예외 처리 필요
			// ...
			file.delete(); // 파일 삭제
			log.info("파일 삭제를 완료했습니다.");
		}
		// 삭제에 실패할 경우 예외 발생(수정 필요)
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
