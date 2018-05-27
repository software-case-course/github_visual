package com.AriesT.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64.Encoder;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AriesT.Entity.Data_Language_Use;
import com.AriesT.Entity.RepositoryInfo;
import com.AriesT.dao.Mapper;

@Service
public class JsonService extends SqlSessionDaoSupport{

	static final String[] languages = { "Java", "Python", "HTML", "JavaScript", "PHP", "Ruby", "CSS", "C++", "C#",
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
	static final String token = "99e129ff4fb85eda845a9fdacd30d7d1703f066a";

	static Logger logger;
	static {
		logger = Logger.getLogger(JsonService.class);
		BasicConfigurator.configure();
		logger.setLevel(Level.INFO);
	}
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Autowired
	private Mapper mapper;
	
	public Map<String, Object> CheckLanguageUseByYear() throws Exception {
		Map<String, Object> map = new HashMap<>();

		ArrayList<Data_Language_Use> datas = new ArrayList<>();

		for (int i = 0; i < languages.length; i++) {
			for (int j = 0; j < years.length; j++) {
				String language = languages[i];
				String year = years[j];
				// logger.info(language+" "+year);
				String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
						+ year + "-01-01.." + year + "-12-31&per_page=1";

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						JSONObject JSONObject = analyjson(request);
						Data_Language_Use data = null;
						synchronized (datas) {
							if (JSONObject != null) {
								data = new Data_Language_Use(language, year, "",
										(Integer) JSONObject.get("total_count"));
								datas.add(data);
							} else {
								logger.warn("未获取到json");
							}
						}
					}
				});
				Thread.sleep(300);
				thread.run();
			}
		}
		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}

	public Map<String, Object> CheckLanguageUseByMonth(String year) throws Exception {
		Map<String, Object> map = new HashMap<>();
		ArrayList<Data_Language_Use> datas = new ArrayList<>();

		for (int i = 0; i < languages.length; i++) {
			for (int j = 0; j < daysofmonth.size(); j++) {
				String language = languages[i];
				String month = String.valueOf(j + 1);
				String day = daysofmonth.get(month);
				String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
						+ year + "-" + month + "-01.." + year + "-" + month + "-" + day + "&per_page=1";

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						JSONObject jsonObject = analyjson(request);
						Data_Language_Use data = null;
						synchronized (datas) {
							if (jsonObject != null) {
								data = new Data_Language_Use(language, year, month,
										(Integer) jsonObject.get("total_count"));
								datas.add(data);
							} else {
								logger.warn("未获取到json");
							}
						}
					}
				});
				Thread.sleep(300);
				thread.run();
			}
		}
		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}

	public Map<String, Object> getHighlyRatedRepositories(String type) throws Exception {
		Map<String, Object> map = new HashMap<>();
		ArrayList<RepositoryInfo> datas = new ArrayList<>();

		for (int j = 1; j <= 5; j++) {
			String request = "https://api.github.com/search/repositories?q=" + type + ":>1000&sort=" + type
					+ "&per_page=100&page="+ j;
			JSONObject jsonObject = analyjson(request);
			JSONObject insideObject = null;
			
			if (jsonObject != null) {
				JSONArray array = jsonObject.getJSONArray("items");
				for (int i = 0; i < array.length(); i++) {//json数组
					insideObject = array.getJSONObject(i);

					String fullname = insideObject.getString("full_name");
					String language = insideObject.get("language") instanceof java.lang.String
							? insideObject.getString("language")
							: "";
					Integer num = insideObject.getInt(type.equals("stars") ? "stargazers_count" : "forks_count");

					String userJson = insideObject.getJSONObject("owner").getString("url");
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							JSONObject userObject = analyjson(userJson);
							if (userObject != null) {
								String region = userObject.get("location") instanceof java.lang.String
										? userObject.getString("location")
										: "";
								RepositoryInfo data = new RepositoryInfo(fullname, language, num, region);
								synchronized (datas) {
									datas.add(data);
								}
							} else {
								RepositoryInfo data = new RepositoryInfo(fullname, language, num, "");
								synchronized (datas) {
									datas.add(data);
								}
							}
						}
					});
					thread.run();
				}
			} else {
				logger.warn("未获取到json");
			}
		}

		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}
	
	public Map<String, Object> test() throws Exception {
		List<Data_Language_Use> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(new Data_Language_Use(String.valueOf(i), "asd", "sad", i));
		}
		SqlSession sqlSession = this.getSqlSession();
		sqlSession.insert("com.AriesT.mapping.mapping.insertyear", list);
		return new HashMap<>();
	}

	JSONObject analyjson(final String address) {
		JSONObject json = null;
		try {
			URL url = new URL(address);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(5000);// 辣鸡github反应真慢
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.setInstanceFollowRedirects(false);

			String tokens = token + ":x-oauth-basic";
			Encoder encoder = Base64.getEncoder();
			String authString = "Basic " + encoder.encodeToString(tokens.getBytes());
			httpURLConnection.setRequestProperty("Authorization", authString);//验证

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
					logger.info("json:  " + string);
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
