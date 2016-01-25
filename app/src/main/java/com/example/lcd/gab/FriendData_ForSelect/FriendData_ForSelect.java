package com.example.lcd.gab.FriendData_ForSelect;

import java.io.Serializable;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendData_ForSelect implements Serializable {

    private String name;
    private String phoneNum;
    private int Money;
    private int Party_finished; //정산 미완료 0, 정산완료 1
    private Boolean selected;

    public FriendData_ForSelect() {
        name = "";
        phoneNum = "";
        Money = 0;
        Party_finished = 0;
        selected= false;
    }

    public FriendData_ForSelect(String name, String phoneNum, int money, int party_finished, Boolean selected) {

        this.name = name;
        this.phoneNum = phoneNum;
        Money = money;
        Party_finished = party_finished;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public int getParty_finished() {
        return Party_finished;
    }

    public void setParty_finished(int party_finished) {
        Party_finished = party_finished;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
