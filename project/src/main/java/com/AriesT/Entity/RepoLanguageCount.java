package com.AriesT.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class RepoLanguageCount {

	String language;
	Data data;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public void setData(String year, String month, Integer number) {
		this.data.year = year;
		this.data.month = month;
		this.data.number = number;
	}

	public String getYear() {
		return data.year;
	}

	public void setYear(String year) {
		this.data.setYear(year);
	}

	public String getMonth() {
		return data.month;
	}

	public void setMonth(String month) {
		this.data.setMonth(month);
	}

	public Integer getNumber() {
		return data.number;
	}

	public void setNumber(Integer number) {
		this.data.setNumber(number);
	}

	public RepoLanguageCount(String language, Data data) {
		super();
		this.language = language;
		this.data = data;
	}

	public RepoLanguageCount(String language, String year, String month, Integer number) {
		super();
		this.language = language;
		this.data = new Data(year, month, number);
	}

	@Override
	public String toString() {
		return "Data_Language_Use [language=" + language + ", data=" + data + "]";
	}

	public class Data {

		@Override
		public String toString() {
			return "Data [year=" + year + ", month=" + month + ", number=" + number + "]";
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

		String year;
		String month;
		Integer number;

		public Data(String year, String month, Integer number) {
			this.year = year;
			this.month = month;
			this.number = number;
		}
	}

}
