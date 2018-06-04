package com.AriesT.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HighlyRatedRepositoriesDao {

	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}

	@Autowired
	BaseDao baseDao;

	public void inserttodao(ArrayList<com.AriesT.Entity.Repository> arrayList) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("basename", "Repositorys");
		map.put("key", new ArrayList<String>() {
			{
				add("repo_name");
			}
		});
		map.put("col", new ArrayList<String>() {
			{
				add("repo_name");
				add("owner");
				add("location");
				add("language");
				add("stars");
				add("forks");
			}
		});

		ArrayList<com.AriesT.Entity.Repository> addlist = new ArrayList<>();
		ArrayList<com.AriesT.Entity.Repository> updatelist = new ArrayList<>();

		com.AriesT.Entity.Repository repository = null;
		for (int i = 0; i < arrayList.size(); i++) {
			repository = arrayList.get(i);
			map.put("repo_name", repository.getRepo_name());
			map.put("owner", repository.getOwner());
			map.put("location", repository.getLocation());
			map.put("language", repository.getLanguage());
			map.put("stars", repository.getStars());
			map.put("forks", repository.getForks());

			if (baseDao.checkifexist(map)) {
				logger.info("有了");
				updatelist.add(repository);
			} else {
				logger.info("没有");
				addlist.add(repository);
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
