package com.AriesT.Entity;

public class Repository {

    String repo_name;
    String full_name;
    String owner;
    String location;
    String language;
    int stars;
    int forks;

    public Repository(String repo,String own,String lo,String lang,int s,int f){
        repo_name=repo;
        //full_name=full;
        owner=own;
        location=lo;
        language=lang;
        stars=s;
        forks=f;
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
