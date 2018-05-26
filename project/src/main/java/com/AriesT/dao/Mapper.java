package com.AriesT.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.AriesT.Entity.RepositoryInfo;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {
	
	@Select("select repo_name,language,stars,location from repository")
	List<RepositoryInfo> getAll();

}
