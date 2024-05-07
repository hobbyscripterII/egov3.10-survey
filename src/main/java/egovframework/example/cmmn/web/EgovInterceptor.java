package egovframework.example.cmmn.web;

import java.net.Inet4Address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import egovframework.example.cmmn.Const;
import egovframework.example.sample.service.BoardService;

public class EgovInterceptor implements HandlerInterceptor {
	@Autowired BoardService boardService;
	Logger log = LoggerFactory.getLogger(getClass());

	// preHandle - 컨트롤러 동작 이전 요청을 가로챔
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false); // 첫번째 인자 false를 넣으면 session이 없을 경우 새로 생성하지 않고 null로 반환함
		String url = request.getRequestURI();
		String ip = Inet4Address.getLocalHost().getHostAddress();
		request.setAttribute(Const.USER_IP, ip);
		int iboard = Integer.parseInt(request.getParameter("iboard"));
		int boardIuser = boardService.getBoardByIuser(iboard);
		Integer sessionIuser = (int) session.getAttribute(Const.USER_IUSER);

		log.info("REQUEST [{}][{}]", ip, url);

		// 저장된 session이 없거나 session에 회원 pk가 저장되지 않았다면 아래 로직을 실행함
		if (session == null || sessionIuser == null) {
			response.sendRedirect("/winitech/user/signin.do"); // sendRedirect - 로그인 페이지로 리다이렉트 시킴(forward랑 다르게 값을
			return false; // false를 반환할 경우 리다이렉트 후 종료됨 / 남은 인터셉터, 컨트롤러 실행 x
		} else if (boardIuser != sessionIuser) {
			response.sendRedirect("/winitech/exception/403.do"); // tiles 레이아웃 적용위해 컨트롤러로 따로 뺌
//			response.sendRedirect("/winitech/common/403.jsp"); // 'webapp/common' 경로에 jsp 바로 넣고 접근할 경우 tiles 적용 안됨
			return false; // 게시글을 작성한 회원이 아닌 경우
		}
		
		return true; // 반환 값이 true일 때만 다음 동작(컨트롤러 이동)을 실행함
	}

	// 컨트롤러 동작 이후 실행
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("POSTHANDLE [{}]", modelAndView);
	}

	// 화면 처리 이후 실행
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String url = request.getRequestURI();
		String ip = (String) request.getAttribute(Const.USER_IP);

		log.info("RESPONSE [{}][{}]", ip, url);

		if (ex != null) {
			log.error("AFTERCOMPLETION ERROR", ex);
		}
	}
}