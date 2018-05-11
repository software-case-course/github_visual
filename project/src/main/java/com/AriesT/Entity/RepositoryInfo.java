package com.AriesT.Entity;

public class RepositoryInfo {

	String fullname;
	String language;
	Integer num;
	String region;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public RepositoryInfo(String fullname, String language, Integer num, String region) {
		super();
		this.fullname = fullname;
		this.language = language;
		this.num = num;
		this.region = region;
	}
}
