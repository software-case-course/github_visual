package com.AriesT.dao;

import java.util.HashMap;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao extends SqlSessionDaoSupport {
	
	static Logger logger;
	static {
		logger = Logger.getLogger("com.AriesT");
	}

	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	Boolean checkifexist(HashMap<String, Object> map) {
		if (this.getSqlSession().selectList("com.AriesT.mapping.mapping.checkifexist", map).size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	Integer inserttodao (HashMap<String, Object> map) {
		if ((Integer) map.get("addlistsize") == 0) {
			return 0;
		} else {
			return this.getSqlSession().insert("com.AriesT.mapping.mapping.inserttodao", map);
		}
	}

	Integer updatetodao(HashMap<String, Object> map) {
		if ((Integer) map.get("updatelistsize") == 0) {
			return 0;
		} else {
			return this.getSqlSession().update("com.AriesT.mapping.mapping.updatetodao", map);
		} 
	}
}
