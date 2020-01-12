package com.example.applove.Model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private Date birthday;
    private String linkAvata;
    private String linkFace;
    private String sex;
    private int Point;
    private String liked;

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLinkAvata() {
        return linkAvata;
    }

    public void setLinkAvata(String linkAvata) {
        this.linkAvata = linkAvata;
    }

    public String getLinkFace() {
        return linkFace;
    }

    public void setLinkFace(String linkFace) {
        this.linkFace = linkFace;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
