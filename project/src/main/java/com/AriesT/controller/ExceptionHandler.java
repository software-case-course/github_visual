package com.AriesT.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AriesT.service.JsonService;

@ResponseBody
@ControllerAdvice
public class ExceptionHandler {

	static Logger logger;
	static {
		logger = Logger.getLogger(JsonService.class);
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public void printException(Exception e) {
		logger.error("Error: ", e);
	}
}
