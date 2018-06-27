package com.AriesT.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.AriesT.Entity.RepoLanguageCount;
import com.AriesT.dao.BaseDao;

@Repository
public class RepoLanguageCountDao {
	
	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}
	
	@Autowired
	BaseDao baseDao;
	
	public void inserttodao(ArrayList<RepoLanguageCount> arrayList) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("basename", "RepoLanguageCount");
		map.put("key",new ArrayList<String>(){{
			add("language");
			add("year");
			add("month");
		}});
		map.put("col",new ArrayList<String>(){{
			add("language");
			add("year");
			add("month");
			add("number");
		}});
		
		ArrayList<RepoLanguageCount> addlist = new ArrayList<>();
		ArrayList<RepoLanguageCount> updatelist = new ArrayList<>();
		
		RepoLanguageCount rCount = null;
		for (int i = 0; i < arrayList.size(); i++) {
			rCount = arrayList.get(i);
			map.put("language", rCount.getLanguage());
			map.put("year", rCount.getData().getYear());
			map.put("month", rCount.getData().getMonth());
			map.put("number", rCount.getData().getNumber());

			if (baseDao.checkifexist(map)) {
				logger.info("有了");
				updatelist.add(rCount);
			} else {
				logger.info("没有");
				addlist.add(rCount);
			}
		}
		
		map.put("addlistsize", addlist.size());
		map.put("updatelistsize", updatelist.size());
		map.put("addlist", addlist);
		map.put("updatelist", updatelist);
		
		baseDao.inserttodao(map);
		baseDao.updatetodao(map);
	}
}
