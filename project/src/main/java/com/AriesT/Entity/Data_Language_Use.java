package com.AriesT.Entity;

import java.util.Date;

public class Data_Language_Use {

	Date date;
	String language;
	Integer number;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Data_Language_Use(Date date, String language, Integer number) {
		super();
		this.date = date;
		this.language = language;
		this.number = number;
	}

}
