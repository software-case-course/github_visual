package com.AriesT.service;

import java.util.HashMap;
import java.util.Map;

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
	
	public Map<String, Object> getjson(JSONObject jsonObject) {
		Map<String, Object> map = new HashMap<>();
		Integer count = (Integer) jsonObject.get("total_count");
		
		map.put("num", 1);
		map.put("data", count);
		map.put("info", null);
		
		return map;
	}
}
