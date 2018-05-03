package com.AriesT.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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
	public ResultEntity<Map> CheckLanguageUseByYear(String language, String year) {
		String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:" + year
				+ "-01-01.." + year + "-12-31&per_page=1";
		JSONObject jsonObject;
		if ((jsonObject = getjson(request)) != null) {
			Map<String, Object> map = service.getjson(jsonObject);
			ResultEntity<Map> result = ResultEntity.SetResultForController(map);
			return result;
		} else {
			logger.warn("未获取到json");
		}
		return null;
	}
	
	@RequestMapping(value = "/CheckLanguageUseByMonth", method = RequestMethod.GET)
	public ResultEntity<Map> CheckLanguageUseByMonth(String language, String year, String month) {
		Integer days = 28;
		switch (month) {
		case "01":
			days = 31;
			break;
		case "02":
			days = 28;
			break;
		case "03":
			days = 31;
			break;
		case "04":
			days = 30;
			break;
		case "05":
			days = 31;
			break;
		case "06":
			days = 30;
			break;
		case "07":
			days = 31;
			break;
		case "08":
			days = 31;
			break;
		case "09":
			days = 30;
			break;
		case "10":
			days = 31;
			break;
		case "11":
			days = 30;
			break;
		case "12":
			days = 31;
			break;
		default:
			break;
		}
		String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:" + year + "-"
				+ month + "-01.." + year + "-" + month + "-" + days + "&per_page=1";
		JSONObject jsonObject;
		if ((jsonObject = getjson(request)) != null) {
			Map<String, Object> map = service.getjson(jsonObject);
			ResultEntity<Map> result = ResultEntity.SetResultForController(map);
			return result;
		} else {
			logger.warn("未获取到json");
		}
		return null;
	}
	
	
	JSONObject getjson(String address) {

		JSONObject json = null;

		try {
			URL url = new URL(address);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(30000);// 辣鸡github反应真慢
			httpURLConnection.setReadTimeout(30000);
			httpURLConnection.setInstanceFollowRedirects(false);

			int response = httpURLConnection.getResponseCode();
			boolean redircet = false;

			if (response == HttpURLConnection.HTTP_MOVED_PERM || response == HttpURLConnection.HTTP_SEE_OTHER
					|| response == HttpURLConnection.HTTP_MOVED_TEMP) {
				redircet = true;

				String newUrl = httpURLConnection.getHeaderField("Location");
				String cookies = httpURLConnection.getHeaderField("Set-Cookie");

				httpURLConnection = (HttpURLConnection) new URL(newUrl).openConnection();
				httpURLConnection.setRequestProperty("Cookie", cookies);
				logger.info("Redirect to URL : " + newUrl);
			}
			
			if (response == HttpURLConnection.HTTP_OK || redircet) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));

				StringBuffer stringBuffer = new StringBuffer();
				String string = null;
				while ((string = reader.readLine()) != null) {
					logger.info(string);
					stringBuffer.append(string + "\r\n");
				}
				reader.close();
				httpURLConnection.disconnect();

				json = new JSONObject(stringBuffer.toString());
			} else {
													
			}
		} catch (Exception e) {
			logger.error("json获取失败");
		}
		return json;
	}

}
