package com.example.lcd.gab.PayRoom;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-01-15.
 */

//item for _ (party phonenum, partymoney , party_finished)

public class DRoomItemInfo implements Serializable{
    String DRoomitem_name;
    int DRoomitem_price;

    public DRoomItemInfo(){ }

    public DRoomItemInfo(String DRoomitem_name, int DRoomitem_price) {
        this.DRoomitem_name = DRoomitem_name;
        this.DRoomitem_price = DRoomitem_price;
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
