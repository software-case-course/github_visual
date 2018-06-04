package com.AriesT.Entity;

public class Repository {

	String repo_name;
	String owner;
	String location;
	String language;
	int stars;
	int forks;

	public Repository(String repo_name, String owner, String location, String language, int stars, int forks) {
		super();
		this.repo_name = repo_name;
		this.owner = owner;
		this.location = location;
		this.language = language;
		this.stars = stars;
		this.forks = forks;
	}

	public String getRepo_name() {
		return repo_name;
	}

	public void setRepo_name(String repo_name) {
		this.repo_name = repo_name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public int getForks() {
		return forks;
	}

	public void setForks(int forks) {
		this.forks = forks;
	}
}
