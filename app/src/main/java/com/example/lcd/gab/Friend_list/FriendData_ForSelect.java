package com.example.lcd.gab.Friend_list;

import java.io.Serializable;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendData_ForSelect implements Serializable {

    private String name;
    private String phoneNum;

    public FriendData_ForSelect(String name, String phoneNum){
        this.name = name;
        this.phoneNum = phoneNum;
    }
    public FriendData_ForSelect(){
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
