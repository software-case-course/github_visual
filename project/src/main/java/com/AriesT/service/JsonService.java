package com.AriesT.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class JsonService {
	
	static Logger logger;
	static {
		logger = Logger.getLogger(JsonService.class);
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
	}
	
	public void getjson(JSONObject jsonObject) {
		logger.info(jsonObject.toString());
	}
}
