package com.AriesT.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	@Scheduled(cron = "0 * * * * *")
	public ResultEntity<Map<String, Object>> saveAll() throws Exception {
		Map<String, Object> map = jsonservice.saveAll();
		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}
	
//	用上面那个一次搞定
//	@RequestMapping(value = "/saveLanguageUseByYear", method = RequestMethod.GET)
//	public ResultEntity<Map<String, Object>> saveLanguageUseByYear() throws Exception {
//		Map<String, Object> map = jsonservice.saveLanguageUseByYear();
//		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
//		return result;
//	}
//
//	@RequestMapping(value = "/saveLanguageUseByMonth", method = RequestMethod.GET)
//	public ResultEntity<Map<String, Object>> saveLanguageUseByMonth() throws Exception {
//		Map<String, Object> map = jsonservice.saveLanguageUseByMonth();
//		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
//		return result;
//	}
//
//	@RequestMapping(value = "/saveHighlyRatedRepositories", method = RequestMethod.GET)
//	public ResultEntity<Map<String, Object>> saveHighlyRatedRepositories() throws Exception {
//		Map<String, Object> map = jsonservice.saveHighlyRatedRepositories();
//		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
//		return result;
//	}

	@RequestMapping(value = "/getLanguageUseByYear", method = RequestMethod.GET)
	public ResultEntity<Map<String, Object>> getLanguageUseByYear() throws Exception {
		Map<String, Object> map = daoservice.getLanguageUseByYear();
		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}

	@RequestMapping(value = "/getLanguageUseByMonth", method = RequestMethod.GET)
	public ResultEntity<Map<String, Object>> getLanguageUseByMonth(String year) throws Exception {
		Map<String, Object> map = daoservice.getLanguageUseByMonth(year);
		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
		return result;
	}

	@RequestMapping(value = "/getHighlyRatedRepositories", method = RequestMethod.GET)
	public ResultEntity<Map<String, Object>> getHighlyRatedRepositories(String type, String lang) throws Exception {
		Map<String, Object> map = daoservice.getHighlyRatedRepositories(type, lang);
		ResultEntity<Map<String, Object>> result = ResultEntity.SetResultForController(map);
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

}
