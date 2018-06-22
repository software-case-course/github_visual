package com.AriesT.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
public class JsonService {

	static final String[] languages = { "Java", "Python", "HTML", "JavaScript", "PHP", "Ruby", "C", "CSS", "C%2B%2B", "C%23",
			"TypeScript" };
	static final String[] years = { "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
			"2018" };
	static final HashMap<String, String> daysofmonth = new HashMap<String, String>() {
		{
			put("01", "31");
			put("02", "28");
			put("03", "31");
			put("04", "30");
			put("05", "31");
			put("06", "30");
			put("07", "31");
			put("08", "31");
			put("09", "30");
			put("10", "31");
			put("11", "30");
			put("12", "31");
		}
	};
	// static final String token = "a9bdaa95ec7438c97cb9a11d982043a02a7633c6";

	static final String clientId = "4f16520c1d7b59b65c8a";
	static final String clientSecret = "d3b7c7c6736545ae66d3548b23a29d34075260de";// 个人的，用着先

	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}

	static int threadcount = 0;

	// 记录请求位置
	static int functioncount = 0;

	static int language_year = 0;
	static int year_year = 0;

	static int language_month = 0;
	static int year_month = 0;

	static int type_highre = 0;
	static int page_highre = 1;
	static int user_highre = 0;

	// 线程池
	ThreadPoolExecutor tp;

	@Autowired
	RepoLanguageCountDao repoLanguageCountDao;

	@Autowired
	HighlyRatedRepositoriesDao highlyRatedRepositoriesDao;

	// @Autowired//用xml代替
	// private Mapper mapper;

	public Map<String, Object> saveAll() throws Exception { // 感觉特别傻，有好点的办法么
		tp = new ThreadPoolExecutor(10, 20, 10000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		Map<String, Object> map = null;
		switch (functioncount) {
		case 0:
			map = saveLanguageUseByYear();
			break;
		case 1:
			map = saveLanguageUseByMonth();
			break;
		case 2:
			map = saveHighlyRatedRepositories();
			break;
		default:
			break;
		}
		tp.shutdown();
		return map;
	}

	public Map<String, Object> saveLanguageUseByYear() throws Exception {
		Map<String, Object> map = new HashMap<>();
		ArrayList<RepoLanguageCount> datas = new ArrayList<>();

		over: for (; language_year < languages.length; language_year++) {
			for (; year_year < years.length; year_year++) {
				String language = languages[language_year];
				String year = years[year_year];
				String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
						+ year + "-01-01.." + year + "-12-31&per_page=1";

				if (tp.isShutdown()) {// 直接返回
					if (year_year != 0) {
						year_year--;
					} else if (language_year != 0) {
						year_year = years.length - 1;
						language_year--;
					} else {
						//应该不会发生
					}
					break over;
				}
				Runnable runnable = new Runnable() {
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
				};
				tp.execute(runnable);
				Thread.sleep(500);
			}
			year_year = 0;
		}
		if (!tp.isShutdown()) {// 还有空余的情况下才重置
			language_year = 0;
			year_year = 0;
			functioncount++;
		}

		repoLanguageCountDao.inserttodao(datas);

		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}

	public Map<String, Object> saveLanguageUseByMonth() throws Exception {
		Map<String, Object> map = new HashMap<>();
		ArrayList<RepoLanguageCount> datas = new ArrayList<>();

		over: 
		for (; language_month < languages.length; language_month++) {
			for (; year_month < years.length; year_month++) {
				for (int i = 0; i < daysofmonth.size(); i++) {
					String language = languages[language_month];
					String month = String.valueOf(i + 1).length() == 1 ? "0" + String.valueOf(i + 1)
							: String.valueOf(i + 1);//补个0
					String day = daysofmonth.get(month);
					String year = years[year_month];
					String request = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
							+ year + "-" + month + "-01.." + year + "-" + month + "-" + day + "&per_page=1";

					if (tp.isShutdown()) {// 直接返回
						if (i != 0) {
							i--;
						} else if (year_month != 0) {
							i = daysofmonth.size() - 1;
							year_month--;
						} else if (language_month != 0) {
							i = daysofmonth.size() - 1;
							year_month = years.length - 1;
							language_month--;
						} else {
							//应该不会发生
						}
						break over;
					}
					Runnable runnable = new Runnable() {
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
					};
					tp.execute(runnable);
					Thread.sleep(500);
				}
			}
			year_month = 0;
		}
		if (!tp.isShutdown()) {// 还有空余的情况下才重置
			year_month = 0;
			language_month = 0;
			functioncount++;
		}

		repoLanguageCountDao.inserttodao(datas);

		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}

	public Map<String, Object> saveHighlyRatedRepositories() throws Exception {
		Map<String, Object> map = new HashMap<>();
		ArrayList<Repository> datas = new ArrayList<>();
		ArrayList<Repository> typedata = new ArrayList<>();//存放一个type的数据
		String[] types = { "stars", "forks" };

		over:
		for (; type_highre < types.length; type_highre++) {
			for (; page_highre <= 5; page_highre++) {
				String request = "https://api.github.com/search/repositories?q=" + types[type_highre] + ":>1000&sort="
						+ types[type_highre] + "&per_page=100&page=" + page_highre;
				JSONObject jsonObject = analyjson(request);
				JSONObject insideObject = null;

				if (jsonObject != null) {
					JSONArray array = jsonObject.getJSONArray("items");
					for (; user_highre < array.length(); user_highre++) {// json数组
						insideObject = array.getJSONObject(user_highre);

						String fullname = insideObject.getString("full_name");
						String language = insideObject.get("language") instanceof java.lang.String
								? insideObject.getString("language")
								: "";
						String owner = insideObject.getJSONObject("owner").getString("login");
						Integer star = insideObject.getInt("stargazers_count");
						Integer fork = insideObject.getInt("forks_count");

						String userJson = insideObject.getJSONObject("owner").getString("url"); // 又一个请求

						if (tp.isShutdown()) {// 直接返回
							if (user_highre != 0) {
								user_highre --;
							} else if (page_highre != 1) {
								user_highre = array.length() - 1;
								page_highre--;
							} else if (type_highre != 0) {
								user_highre = array.length() - 1;
								page_highre = 5;
								type_highre--;
							} else {
								//应该不会发生
							}
							break over;
						}
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								JSONObject userObject = analyjson(userJson);
								if (userObject != null) { // 也不清楚究竟得不到时返回什么就直接if下了
									String region = userObject.get("location") instanceof java.lang.String
											? userObject.getString("location")
											: "";
									Repository data = new Repository(fullname, owner, region, language, star, fork);
									synchronized (typedata) {
										typedata.add(data);
									}
								} else {
									Repository data = new Repository(fullname, owner, "", language, star, fork);
									synchronized (typedata) {
										typedata.add(data);
									}
								}
								threadcount--;
							}
						};
						tp.execute(runnable);
						Thread.sleep(500);
					}
					user_highre = 0;
				} else {
					logger.warn("未获取到json");
				}
			}
			page_highre = 1;
			
			datas.addAll(typedata);
			highlyRatedRepositoriesDao.inserttodao(typedata);	//在这里插入避免array里有重复数据
			typedata.clear();
		}
		if (!tp.isShutdown()) {// 还有空余的情况下才重置
			type_highre = 0;
			page_highre = 1;
			user_highre = 0;
			functioncount = 0;
		}

		map.put("num", datas.size());
		map.put("data", datas);
		map.put("info", null);

		return map;
	}

	JSONObject analyjson(String address) {
		if (address.indexOf("&") == -1) { //没有&
			address += "?client_id=" + clientId + "&client_secret=" + clientSecret;// 加个认证
		} else {
			address += "&client_id=" + clientId + "&client_secret=" + clientSecret;// 加个认证
		}
		
		logger.info(address);
		JSONObject json = new JSONObject();
		json.put("message", "");

		int response = 0;
		try {
			URL url = new URL(address);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(5000);// 辣鸡github反应真慢
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.setInstanceFollowRedirects(false);

			// String tokens = token + ":x-oauth-basic";
			// Encoder encoder = Base64.getEncoder();
			// String authString = "Basic " + encoder.encodeToString(tokens.getBytes());
			// httpURLConnection.setRequestProperty("Authorization", authString);// 验证

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
				logger.info("remain" + httpURLConnection.getHeaderField("X-RateLimit-Remaining"));

				StringBuffer stringBuffer = new StringBuffer();
				String string = null;
				while ((string = reader.readLine()) != null) {
					stringBuffer.append(string + "\r\n");
				}
				reader.close();
				httpURLConnection.disconnect();

				json = new JSONObject(stringBuffer.toString());
			} else {
				logger.warn("未获得json" + response + "    " + json.getString("message"));
				if (response == HttpURLConnection.HTTP_FORBIDDEN // 检测是否达到请求上限
						&& httpURLConnection.getHeaderField("X-RateLimit-Remaining").equals("0")) {
					if (tp != null && !tp.isShutdown()) {
						tp.shutdownNow();
						logger.info("关闭线程池");
					}
				}
				json = null;
			}
		} catch (Exception e) {
			logger.warn("错误" + response + "    " + json.getString("message"));
			json = null;
		}
		return json;
	}

}
