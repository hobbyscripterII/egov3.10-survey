package egovframework.example.cmmn.web;

import java.net.Inet4Address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import egovframework.example.cmmn.Const;

public class EgovInterceptor implements HandlerInterceptor {
	public static final String IP = "USER_IP";
	Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		String ip = Inet4Address.getLocalHost().getHostAddress();
		request.setAttribute(IP, ip);
		log.info("REQUEST [{}][{}]", ip, url);

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute(Const.USER_IUSER) == null) {
			log.info("미인증 사용자 요청 [{}][{}]", ip, url);
			response.sendRedirect("/winitech/user/signin.do");
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("POSTHANDLE [{}]", modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String url = request.getRequestURI();
		String ip = (String) request.getAttribute(IP);
		log.info("RESPONSE [{}][{}]", ip, url);

		if (ex != null) log.error("AFTERCOMPLETION ERROR", ex);
	}
}