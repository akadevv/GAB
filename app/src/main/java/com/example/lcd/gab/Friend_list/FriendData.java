package com.example.lcd.gab.Friend_list;

import java.io.Serializable;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendData implements Serializable {

    private String name;
    private String phoneNum;

    public FriendData(String name, String phoneNum){
        this.name = name;
        this.phoneNum = phoneNum;
    }
    public FriendData(){
        name="";
        phoneNum="";
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
}
