package egovframework.example.sample.web;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import egovframework.example.cmmn.Const;
import egovframework.example.cmmn.FileUtils;
import egovframework.example.cmmn.Pagination;
import egovframework.example.cmmn.Utils;
import egovframework.example.sample.service.BoardInsValidator;
import egovframework.example.sample.service.BoardService;
import egovframework.example.sample.service.model.board.BoardFileInsDto;
import egovframework.example.sample.service.model.board.BoardFileSelVo;
import egovframework.example.sample.service.model.board.BoardInsDto;
import egovframework.example.sample.service.model.board.BoardListGetVo;
import egovframework.example.sample.service.model.board.BoardUpdDto;

@Controller
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	private final FileUtils fileUtils;
	Logger log = LoggerFactory.getLogger(getClass()); // 로그 객체

	/**
	 * @param boardService - 게시판 관련 비즈니스 로직 처리
	 * @param fileUtils    - 파일 업로드 및 다운로드 관련 로직 처리<br>
	 * @apiNote - 생성자가 1개만 있을 경우에는 의존성 주입 어노테이션(@Autowired, @Resource) 생략 가능
	 */
	public BoardController(BoardService boardService, FileUtils fileUtils) { this.boardService = boardService; this.fileUtils = fileUtils; }

	/**
	 * @param files - 게시글 등록 / 수정 시 클라이언트에서 넘겨주는 첨부파일 목록(배열)<br>
	 *              - 게시글 등록 / 수정 시 파일 업로드 해야 할 경우가 있기 때문에 따로 메소드를 분리시켜 놓음
	 */
	private List<BoardFileInsDto> fileUpload(MultipartFile[] files) throws Exception {
		List<BoardFileInsDto> fileDtoList = new ArrayList(); // 로컬에 저장된 파일 정보를 담기위한 list 생성
		// 반복문으로 로컬에 첨부파일 저장 후 반환된 dto(테이블에 저장할 파일 정보)를 list에 담음
		// 배열 []은 List<>와 달리 stream 사용 불가능
		for (MultipartFile file : files) {
			BoardFileInsDto dto = fileUtils.fileUpload(file); // 파일 업로드 후 해당 파일의 정보를 dto에 담아서 줌
			fileDtoList.add(dto); // 테이블에 저장하기 위해 list에 담음
		}
		return fileDtoList; // 테이블에 저장하기 위해 해당 List<dto>를 반환
	}

	/**
	 * @param ifile - 클라이언트 측에서 첨부파일 클릭 시 쿼리 스트링으로 해당 첨부파일의 pk를 받아 옴
	 */
	@GetMapping("/download.do")
	public void download(@RequestParam int ifile, HttpServletResponse response) throws Exception {
		try {
			BoardFileSelVo vo = boardService.selBoardFile(ifile); // 클라이언트가 클릭한 첨부파일의 pk를 갖고 해당 첨부파일의 정보를 가져옴
			String savedName = vo.getSavedName() + vo.getExt(); // 리소스를 찾기 위해 실제 서버에 저장된 'UUID + 확장명'을 가져옴
			String downloadName = vo.getOriginalName() + vo.getExt(); // 클라이언트 로컬에 저장할 때는 '원래 파일명 + 확장명'으로 다운로드 하기 위해 해당 이름을 가져옴
			String downloadPath = fileUtils.getDownloadPath(savedName); // 다운로드 하기 위해 로컬의 실제 경로를 가져옴(경로/UUID.확장명)
			Resource resource = new UrlResource(Paths.get(downloadPath).toUri()); // 서버에 저장된 다운로드 받을 리소스를 알려줌

			// 리소스 존재 시 해당 첨부파일을 다운로드 받음
			if(resource.exists()) {
				response.setContentType("application/octet-stream");
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode(downloadName, "UTF-8") + "\"");
				InputStream inputStream = resource.getInputStream();
				OutputStream outputStream = response.getOutputStream();
				IOUtils.copy(inputStream, outputStream);
				outputStream.flush();
				outputStream.close(); }
			else { throw new FileNotFoundException(); }}
		catch (Exception e) { throw new RuntimeException(); }
	}

	/**
	 * @param criteria - 페이지네이션 내에 페이징 관련 이너 클래스
	 */
	@GetMapping("/list.do")
	public String getBoardList(Pagination.Criteria criteria, Model model) throws Exception {
		Pagination pagination = new Pagination(criteria, boardService.getBoardListCnt(criteria));
		List<BoardListGetVo> vo = boardService.getBoardList(criteria);
		model.addAttribute(Const.MODEL_VO_KEY, vo); // view에 출력할 게시글 정보
		model.addAttribute("pagination", pagination); // 페이지네이션
		model.addAttribute("criteria", criteria);
		return "board/list";
	}

	/**
	 * @param iboard - 클라이언트 측에서 특정 게시글을 클릭했을 때 쿼리 스트링으로 넘어오는 게시글의 pk
	 */
	@GetMapping("/view.do")
	public String selBoard(@RequestParam int iboard, Model model) {
		boardService.updView(iboard); // 조회수 증가(수정 필요)
		model.addAttribute(Const.MODEL_VO_KEY, boardService.selBoard(iboard));
		return "board/read";
	}

	/**
	 * @param iboard - 클라이언트 측에서 게시글 '작성' 버튼을 클릭할 경우에는 iboard를 '0'을 줘서 '게시글' 등록으로 인식<br>
	 *               - '답변' 버튼을 클릭할 경우에는 쿼리 스트링으로 답변을 달 게시글의 pk를 받아오기 때문에 defaultValue가 '0'이 될 수 없음<br>
	 *               - 게시판 테이블의 답변글 식별코드를 게시글의 pk로 받아 저장 중
	 */
	@GetMapping("/write.do")
	public String insBoard(@RequestParam(required = false, defaultValue = "0") int iboard, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute(Const.USER_NAME);
		BoardInsDto dto = new BoardInsDto();
		dto.setName(name);
		model.addAttribute("dto", dto); // write.jsp를 게시글 작성 / 수정 시 재사용하기 위해 jsp 파일에 새로운 dto를 보냄
		model.addAttribute("iboard", iboard); // 쿼리 스트링으로 게시글의 pk를 보냄('0'이라면 게시글 / '0'이 아니라면 답변글로 인식)
		return "board/write";
	}

	/**
	 * @param dto           - ajax에서 formData로 보낸 dto 객체로 사용자가 작성한 게시글의 정보가 담겨있음
	 * @param files         - ajax에서 formData로 보낸 MultipartFile 배열로 사용자가 첨부한 첨부파일이 담겨져 있음<br>
	 *                      - 첨부파일을 등록하지 않았을 때도 게시글 등록 처리가 되어야 하기 때문에 required를 false로 명시
	 * @param bindingResult - 유효성 검증 실패 시 dto에 먼저 담기는 게 아닌 bindingResult에 담김
	 * @apiNote - 단일로 받을 때는 MultipartFile / 여러 개 받을 때는 MultipartFile[] 배열로 받음
	 */
	@PostMapping("/write.do")
	@ResponseBody
	public HashMap<String, Object> insBoard(@RequestPart(name = "dto") BoardInsDto dto, BindingResult bindingResult, @RequestPart(name = "files", required = false) MultipartFile[] files, HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultMap = new HashMap(); // ajax 반환용 map 생성
		
		// ===== 유효성 검증 로직 시작 =====
		new BoardInsValidator().validate(dto, bindingResult); // 인자 값으로 검증할 객체(dto), bindingResult 객체를 담음
		// 유효성 검증 시 에러가 발생하면 true를 반환
		// -> 에러가 있을 경우 유효성 검증 에러 코드와 에러 메세지가 담긴 map을 보냄
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrorList = bindingResult.getFieldErrors(); // 발생한 필드 에러를 list에 담음
			HashMap<String, Object> errorsMap = new HashMap<String, Object>(); // 에러 메세지를 담을 map 생성
			fieldErrorList.forEach(fieldError -> { errorsMap.put(fieldConverter(fieldError.getField()), fieldError.getDefaultMessage()); }); // 반복문 돌면서 에러가 발생한 필드명과 에러 메세지를 map에 담음
			// ajax 반환용 hashmap(반환 코드, 에러 메세지 객체)
			resultMap.put(Const.MSG_KEY, Const.VALIDATION_ERROR); // 반환 메세지(유효성 검증 실패 플래그)
			resultMap.put(Const.ERRORS_KEY, errorsMap); // 필드 유효성 검증 실패 메세지가 담긴 map 객체
			return resultMap;
		}
		// ===== 유효성 검증 로직 끝 =====

		// ===== 확장자 검증 로직 시작 =====
		if (Utils.isNotNull(files.length)) {
			// <서버에서 확장자 예외 처리>
//				List<String> extList = new ArrayList();
			// 첨부파일 확장자를 list에 담음
//				for(MultipartFile file : files) { extList.add(fileUtils.getExt(file)); }
//				for(int i = 0; i < Const.EXT_EXCEPTION_ARR.length; i++) { log.info("{} = {}", i, Const.EXT_EXCEPTION_ARR[i]);}
			// ===== 확장자 검증 로직 끝 =====
			dto.setFile(fileUpload(files));
		}
		
		HttpSession session = request.getSession();
		int iuser = (int) session.getAttribute(Const.USER_IUSER);
		dto.setIuser(iuser);
		// 게시글 등록 완료 시 반환 값으로 등록된 게시글의 pk를 반환
		int code = boardService.insBoard(dto); // 게시글 등록 완료 시에는 게시글 pk가 담겨져 있으며 실패 시에는 예외 발생 플래그 값이 담겨져 있음
		if (code > 0) {
			resultMap.put(Const.MSG_KEY, Const.SUCCESS);
			resultMap.put(Const.IBOARD_KEY, code); }
		else {resultMap.put(Const.MSG_KEY, code); }
		
		return resultMap;
	}

	@GetMapping("/update.do")
	public String updBoard(@RequestParam int iboard, Model model) {
		model.addAttribute("dto", boardService.selBoard(iboard));
		return "board/write";
	}

	@PostMapping("/update.do")
	@ResponseBody
	public int updBoard(@RequestPart(name = "dto") BoardUpdDto dto, @RequestPart(name = "files", required = false) MultipartFile[] files) throws Exception {
		try {
			if (Utils.isNotNull(files.length)) { dto.setFile(fileUpload(files)); }
			return Utils.isNotNull(boardService.updBoard(dto)) ? Const.SUCCESS : Const.FAIL; }
		catch (Exception e) { return Const.FAIL; }
	}

	@PostMapping("/delete.do")
	@ResponseBody
	public int delBoard(@RequestParam int iboard) {
		return boardService.delBoard(iboard);
	}
	
//	@PostMapping("/check.do")
//	@ResponseBody
//	public int chkPwd(@RequestBody BoardChkPwdDto dto) {
//		return boardService.chkPwd(dto);
//	}

	// 필드 변수명(name) -> 클라이언트 출력용 변수명(이름) 변환 메소드
	private String fieldConverter(String field) {
		switch (field) {
		case Const.FIELD_NAME: field = Const.FIELD_NAME_CONVERT; break;
		case Const.FIELD_PWD: field = Const.FIELD_PWD_CONVERT; break;
		case Const.FIELD_TITLE: field = Const.FIELD_TITLE_CONVERT; break;
		case Const.FIELD_CONTENTS: field = Const.FIELD_CONTENTS_CONVERT; break;
		}
		return field;
	}
}