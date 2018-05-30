package com.AriesT.controller;

import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.AriesT.Entity.ResultEntity;
import com.AriesT.service.JsonService;

@RestController
public class SimpleController {

	@Autowired
	JsonService service;
	
	static Logger logger;
	static {
		logger = Logger.getLogger(SimpleController.class);
		logger.setLevel(Level.INFO);
		BasicConfigurator.configure();
	}

	@RequestMapping(value = "/CheckLanguageUseByYear", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> CheckLanguageUseByYear() throws Exception {
		Map<String, Object> map = service.CheckLanguageUseByYear();
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
	
	@RequestMapping(value = "/CheckLanguageUseByMonth", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> CheckLanguageUseByMonth(String year) throws Exception {
		Map<String, Object> map = service.CheckLanguageUseByMonth(year);
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
	
	@RequestMapping(value = "/getHighlyRatedRepositories", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> getHighlyRatedRepositories(String type) throws Exception {
		Map<String, Object> map = service.getHighlyRatedRepositories(type);
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> test() throws Exception {
		Map<String, Object> map = service.test();
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}

	@RequestMapping(value = "/testgetlocation",method = RequestMethod.GET)
	public String getlocation()throws Exception{
		String location=service.getlocation("aaa");
		return location;
	}

	@RequestMapping(value = "/gettoprepo",method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> gettoprepo(String type,String language)throws Exception{
		Map<String, Object> map = service.getrepo(type,language);
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
}
