package com.AriesT.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.Encoder;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AriesT.Entity.RepoLanguageCount;
import com.AriesT.Entity.Repository;
import com.AriesT.dao.HighlyRatedRepositoriesDao;
import com.AriesT.dao.RepoLanguageCountDao;

@Service
public class JsonService{

	static final String[] languages = { "Java", "Python", "HTML", "JavaScript", "PHP", "Ruby", "CSS", "C%2B%2B", "C#",
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
	static final String token = "a9bdaa95ec7438c97cb9a11d982043a02a7633c6";

	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}
	
	@Autowired
	RepoLanguageCountDao repoLanguageCountDao;
	
	@Autowired
	HighlyRatedRepositoriesDao highlyRatedRepositoriesDao;

	// @Autowired//用xml代替
	// private Mapper mapper;

	public Map<String, Object> saveLanguageUseByYear() throws Exception {
		Map<String, Object> map = new HashMap<>();

		ArrayList<RepoLanguageCount> datas = new ArrayList<>();

		for (int i = 0; i < languages.length; i++) {
			for (int j = 0; j < years.length; j++) {
				String language = languages[i];
				String year = years[j];
				String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
						+ year + "-01-01.." + year + "-12-31&per_page=1";
				logger.info(request);

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						JSONObject JSONObject = analyjson(request);
						RepoLanguageCount data = null;
						synchronized (datas) {
							if (JSONObject != null) {
								data = new RepoLanguageCount(language, year, "",
										(Integer) JSONObject.get("total_count"));
								datas.add(data);
							} else {
								logger.warn("未获取到json");
							}
						}
					}
				});
				thread.run();
			}
		}
		
		repoLanguageCountDao.inserttodao(datas);
		
		map.put("num", datas.size());
		map.put("data", null);
		map.put("info", null);

		return map;
	}

	public Map<String, Object> saveLanguageUseByMonth() throws Exception {
		Map<String, Object> map = new HashMap<>();
		ArrayList<RepoLanguageCount> datas = new ArrayList<>();

		for (int k = 0; k < years.length; k++) {
			for (int i = 0; i < languages.length; i++) {
				for (int j = 0; j < daysofmonth.size(); j++) {
					String language = languages[i];
					String month = String.valueOf(j + 1);
					String day = daysofmonth.get(month);
					String year = years[k];
					String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
							+ year + "-" + month + "-01.." + year + "-" + month + "-" + day + "&per_page=1";

					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							JSONObject jsonObject = analyjson(request);
							RepoLanguageCount data = null;
							synchronized (datas) {
								if (jsonObject != null) {
									data = new RepoLanguageCount(language, year, month,
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
		}
		
		repoLanguageCountDao.inserttodao(datas);

		map.put("num", datas.size());
		map.put("data", null);
		map.put("info", null);

		return map;
	}

	public Map<String, Object> saveHighlyRatedRepositories() {
		Map<String, Object> map = new HashMap<>();
		ArrayList<Repository> datas = new ArrayList<>();
		String[] types = {"stars","forks"};

		for (int k = 0; k < types.length; k++) {
			for (int j = 1; j <= 5; j++) {
				String request = "https://api.github.com/search/repositories?q=" + types[k] + ":>1000&sort=" + types[k]
						+ "&per_page=100&page=" + j;
				JSONObject jsonObject = analyjson(request);
				JSONObject insideObject = null;

				if (jsonObject != null) {
					JSONArray array = jsonObject.getJSONArray("items");
					for (int i = 0; i < array.length(); i++) {// json数组
						insideObject = array.getJSONObject(i);

						String fullname = insideObject.getString("full_name");
						String language = insideObject.get("language") instanceof java.lang.String
								? insideObject.getString("language")
								: "";
						String owner = insideObject.getJSONObject("owner").getString("login");
						Integer star = insideObject.getInt("stargazers_count");
						Integer fork = insideObject.getInt("forks_count");

						String userJson = insideObject.getJSONObject("owner").getString("url");
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								JSONObject userObject = analyjson(userJson);
								if (userObject != null) { //也不清楚究竟得不到时返回什么就直接if下了
									String region = userObject.get("location") instanceof java.lang.String
											? userObject.getString("location")
											: "";
									Repository data = new Repository(fullname, owner, region, language, star, fork);
									synchronized (datas) {
										datas.add(data);
									}
								} else {
									Repository data = new Repository(fullname, owner, "", language, star, fork);
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
		}
		
		highlyRatedRepositoriesDao.inserttodao(datas);
		
		map.put("num", datas.size());
		map.put("data", null);
		map.put("info", null);

		return map;
	}
	
	JSONObject analyjson(final String address) {
		JSONObject json = null;
		int response = 0;
		try {
			URL url = new URL(address);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(5000);// 辣鸡github反应真慢
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.setInstanceFollowRedirects(false);

			String tokens = token + ":x-oauth-basic";
			Encoder encoder = Base64.getEncoder();
			String authString = "Basic " + encoder.encodeToString(tokens.getBytes());
			httpURLConnection.setRequestProperty("Authorization", authString);// 验证

			response = httpURLConnection.getResponseCode();
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
					stringBuffer.append(string + "\r\n");
				}
				reader.close();
				httpURLConnection.disconnect();

				json = new JSONObject(stringBuffer.toString());
			} else {
				
				json = null;
			}
		} catch (Exception e) {
			logger.error("json获取失败" + response);
		}
		return json;
	}


}
