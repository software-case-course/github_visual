package com.AriesT.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.AriesT.Entity.Data_Language_Use;

@Service
public class JsonService {

	static final String[] languages = { "JavaScript", "Java", "Python", "HTML", "PHP", "Ruby", "CSS", "C++", "C#",
			"TypeScript" };
	static final String[] years = { "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
			"2018" };
	static final HashMap<String, String> daysofmonth = new HashMap<String, String>() {
		{
			put("1", "31");
			put("2", "28");
			put("3", "31");
			put("4", "30");
			put("5", "31");
			put("6", "30");
			put("7", "31");
			put("8", "31");
			put("9", "30");
			put("10", "31");
			put("11", "30");
			put("12", "31");
		}
	};

	static Logger logger;
	static {
		logger = Logger.getLogger(JsonService.class);
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
	}

	public Map<String, Object> CheckLanguageUseByYear() {
		Map<String, Object> map = new HashMap<>();

		ArrayList<Data_Language_Use> datas = null;
		Data_Language_Use data = null;
		String language;
		String year;

		String request;
		JSONObject jsonObject;
		for (int i = 0; i < languages.length; i++) {
			for (int j = 0; j < years.length; j++) {
				language = languages[i];
				year = years[j];
				logger.info(language+"   "+year);
				request = "https://api.github.com/search/repositories?q=language:" + language + "+created:" + year
						+ "-01-01.." + year + "-12-31&per_page=1";
				if ((jsonObject = analyjson(request)) != null) {
					if (datas == null) {
						datas = new ArrayList<>();
					}
					data = new Data_Language_Use(language, year, "", (Integer) jsonObject.get("total_count"));
					datas.add(data);
				} else {
					logger.warn("未获取到json");
				}
			}
		}
		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}
	
	public Map<String, Object> CheckLanguageUseByMonth(String year) {
		Map<String, Object> map = new HashMap<>();

		ArrayList<Data_Language_Use> datas = null;
		Data_Language_Use data = null;
		String language;
		String month;
		String day;

		String request;
		JSONObject jsonObject;
		for (int i = 0; i < languages.length; i++) {
			for (int j = 0; j < daysofmonth.size(); j++) {
				language = languages[i];
				month = String.valueOf(j+1);
				day = daysofmonth.get(month);
				request = "https://api.github.com/search/repositories?q=language:" + language + "+created:" + year + "-"
						+ month + "-01.." + year + "-" + month + "-" + day + "&per_page=1";
				if ((jsonObject = analyjson(request)) != null) {
					if (datas == null) {
						datas = new ArrayList<>();
					}
					data = new Data_Language_Use(language, year, month, (Integer) jsonObject.get("total_count"));
					datas.add(data);
				} else {
					logger.warn("未获取到json");
				}
			}
		}
		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}

	JSONObject analyjson(String address) {
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
				json = null;
			}
		} catch (Exception e) {
			logger.error("json获取失败");
		}
		return json;
	}
}
