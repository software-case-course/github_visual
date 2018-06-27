package com.AriesT.Entity;

public class Location {

    long id;
    long pid;
    String path;
    long level;
    String name;
    String name_en;
    String name_pinyin;
    String code;

    public Location(long mid,long mpid,String mpath,long mlevel,String mname,String mname_en,String mname_pinyin,String mcode){
        id=mid;
        pid=mpid;
        path=mpath;
        level=mlevel;
        name=mname;
        name_en=mname_en;
        name_pinyin=mname_pinyin;
        code=mcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_pinyin() {
        return name_pinyin;
    }

    public void setName_pinyin(String name_pinyin) {
        this.name_pinyin = name_pinyin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
