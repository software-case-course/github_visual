package com.AriesT.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class ExceptionHandler {

	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public void printException(Exception e) {
		logger.error("Error: ", e);
	}
}
