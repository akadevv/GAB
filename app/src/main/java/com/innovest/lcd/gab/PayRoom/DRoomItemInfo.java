package com.innovest.lcd.gab.PayRoom;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-01-15.
 */

//item for _ (party phonenum, partymoney , party_finished)

public class DRoomItemInfo implements Serializable {
    int DRoomitem_roomRcdNum;
    String DRoomitem_name;
    int DRoomitem_price;
    int DRoomitem_number;

    public DRoomItemInfo() {
    }

    public DRoomItemInfo(String DRoomitem_name, int DRoomitem_price) {
        this.DRoomitem_name = DRoomitem_name;
        this.DRoomitem_price = DRoomitem_price;
    }

    public DRoomItemInfo(String DRoomitem_name, int DRoomitem_price, int DRoomitem_number) {
        this.DRoomitem_name = DRoomitem_name;
        this.DRoomitem_price = DRoomitem_price;
        this.DRoomitem_number = DRoomitem_number;
    }

    public void setDRoomitem_roomRcdNum(int DRoomitem_roomRcdNum) {
        this.DRoomitem_roomRcdNum = DRoomitem_roomRcdNum;
    }

    public int getDRoomitem_number() {
        return DRoomitem_number;
    }

    public void setDRoomitem_number(int DRoomitem_number) {
        this.DRoomitem_number = DRoomitem_number;
    }

    public int getDRoomitem_roomRcdNum() {
        return DRoomitem_roomRcdNum;
    }

    public String getDRoomitem_name() {
        return DRoomitem_name;
    }

    public void setDRoomitem_name(String DRoomitem_name) {
        this.DRoomitem_name = DRoomitem_name;
    }

    public int getDRoomitem_price() {
        return DRoomitem_price;
    }

    public void setDRoomitem_price(int DRoomitem_price) {
        this.DRoomitem_price = DRoomitem_price;
    }


}
