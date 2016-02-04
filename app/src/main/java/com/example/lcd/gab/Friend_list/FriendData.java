package com.example.lcd.gab.Friend_list;

import java.io.Serializable;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendData implements Serializable {

    private int id;
    private String name;
    private String phoneNum;
    private int bookMark = 0;

    public FriendData(String name, String phoneNum){
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public FriendData(){
        name="";
        phoneNum="";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public void setBookMark(int bookMark){
        this.bookMark = bookMark;
    }

    public int getBookMark(){
        return bookMark;
    }
}
