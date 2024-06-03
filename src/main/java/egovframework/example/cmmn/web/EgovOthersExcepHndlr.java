package egovframework.example.cmmn.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class EgovOthersExcepHndlr implements ExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovOthersExcepHndlr.class);

	@Override
	public void occur(Exception ex, String packageName) {
		LOGGER.debug("[알림] 예외 발생 ex = {} packageName = {}", ex, packageName);
	}
}