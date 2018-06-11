package com.AriesT.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AriesT.Entity.RepoLanguageCount;
import com.AriesT.Entity.Repository;
import com.AriesT.Entity.User;
import com.AriesT.dao.RepoLanguageCountDao;

@Service
public class DaoService extends SqlSessionDaoSupport {
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Autowired
	RepoLanguageCountDao repoLanguageCountDao;
	
	public Map<String, Object> getHighlyRatedRepositories(String type, String lang) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Repository> list = new ArrayList<>();
		if (lang == null) {
			if (type.equals("stars"))
				list = this.getSqlSession().selectList("com.AriesT.mapping.mapping.getallrepobystars");
			else
				list = this.getSqlSession().selectList("com.AriesT.mapping.mapping.getallrepobyforks");
		} else {
			if (type.equals("stars"))
				list = this.getSqlSession().selectList("com.AriesT.mapping.mapping.getlangrepobystars", lang);
			else
				list = this.getSqlSession().selectList("com.AriesT.mapping.mapping.getlangrepobyforks", lang);
		}
		map.put("num", list.size());
		map.put("datas", list);
		map.put("info", null);
		return map;
	}
	
	public Map<String, Object> getLanguageUseByYear() throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<RepoLanguageCount> list = new ArrayList<>();
		
		list = this.getSqlSession().selectList("com.AriesT.mapping.mapping.getRepoLanguageCountByYear");

		map.put("num", list.size());
		map.put("datas", list);
		map.put("info", null);
		return map;
	}
	
	public Map<String, Object> getLanguageUseByMonth(String year) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<RepoLanguageCount> list = new ArrayList<>();
		
		map.put("year", year);
		list = this.getSqlSession().selectList("com.AriesT.mapping.mapping.getRepoLanguageCountByMonth", map);
		
		map.remove("year");
		map.put("num", list.size());
		map.put("datas", list);
		map.put("info", null);
		return map;
	}

	public String getlocation(String username) throws Exception {
		User user = this.getSqlSession().selectOne("com.AriesT.mapping.mapping.getuserlocation", username);
		return user.getUser_location();
	}
	

	public void test() throws Exception {
		ArrayList<RepoLanguageCount> arrayList = new ArrayList<RepoLanguageCount>();
		arrayList.add(new RepoLanguageCount("10", "asd", "sad ", 999));
		arrayList.add(new RepoLanguageCount("wd", "asd", "sad ", 999));
		arrayList.add(new RepoLanguageCount("ad", "asd", "sad ", 5));
		arrayList.add(new RepoLanguageCount("new", "aasd", "sad ", 5));
		repoLanguageCountDao.inserttodao(arrayList);
	}
}
