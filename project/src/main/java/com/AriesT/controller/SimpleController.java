package com.AriesT.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.AriesT.Entity.ResultEntity;
import com.AriesT.service.DaoService;
import com.AriesT.service.JsonService;

@RestController
public class SimpleController {

	@Autowired
	JsonService jsonservice;
	
	@Autowired
	DaoService daoservice;
	
	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}

	@RequestMapping(value = "/CheckLanguageUseByYear", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> CheckLanguageUseByYear() throws Exception {
		Map<String, Object> map = jsonservice.CheckLanguageUseByYear();
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
	
	@RequestMapping(value = "/CheckLanguageUseByMonth", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> CheckLanguageUseByMonth(String year) throws Exception {
		Map<String, Object> map = jsonservice.CheckLanguageUseByMonth(year);
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
	
	@RequestMapping(value = "/getHighlyRatedRepositories", method = RequestMethod.GET)
	public ResultEntity<Map<String,Object>> getHighlyRatedRepositories(String type) throws Exception {
		Map<String, Object> map = jsonservice.getHighlyRatedRepositories(type);
		ResultEntity<Map<String,Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() throws Exception {
		daoservice.test();
	}

	@RequestMapping(value = "/testgetlocation", method = RequestMethod.GET)
	public String getlocation() throws Exception {
		String location = daoservice.getlocation("aaa");
		return location;
	}

	@RequestMapping(value = "/gettoprepo", method = RequestMethod.GET)
	public ResultEntity<Map<String, Object>> gettoprepo(String type, String language) throws Exception {
		Map<String, Object> map = daoservice.getrepo(type, language);
		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
}
