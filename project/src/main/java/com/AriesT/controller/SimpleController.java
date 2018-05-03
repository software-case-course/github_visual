package com.AriesT.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/CheckLanguageUse", method = RequestMethod.GET)
	public void checkjava(String language) {
		String request = "https://api.github.com/search/repositories?q=language:" + language + "&sort=updated";
		JSONObject jsonObject;
		if ((jsonObject = getjson(request)) != null) {
			service.getjson(jsonObject);
		} else {
			logger.warn("未获取到json");
		}
	}
	
//	https://api.github.com/search/repositories?q=language:java&sort=updated&q=created:%3E=2017-01-01&q=created:%3C2018-01-01&per_page=1
//	https://api.github.com/search/repositories?q=language:java&sort=updated&q=created:2017-01-01..2018-01-01&per_page=1
	
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
