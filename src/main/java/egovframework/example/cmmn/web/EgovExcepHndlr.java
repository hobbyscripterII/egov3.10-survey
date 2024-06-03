package egovframework.example.cmmn.web;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EgovExcepHndlr implements ExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovExcepHndlr.class);

	@Override
	public void occur(Exception ex, String packageName) {
		LOGGER.debug("[알림] 예외 발생 ex = {} packageName = {}", ex, packageName);
	}
}