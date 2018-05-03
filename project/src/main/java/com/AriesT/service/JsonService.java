package com.AriesT.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.AriesT.Entity.Data_Language_Use;
import com.AriesT.Entity.ResultEntity;


@Service
public class JsonService {
	
	static Logger logger;
	static {
		logger = Logger.getLogger(JsonService.class);
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
	}
	
	public ResultEntity<Data_Language_Use> getjson(JSONObject jsonObject) {
		
		
	}
}
