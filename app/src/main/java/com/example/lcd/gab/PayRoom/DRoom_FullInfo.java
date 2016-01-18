package com.example.lcd.gab.PayRoom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-15.
 */
public class DRoom_FullInfo implements Serializable {
    String MasterID;
    String DRoomName;
    int DRoomDate;
    List<DRoomItemInfo> DRoomItemList = new ArrayList<DRoomItemInfo>();
    int totalPrice;
    List<DRoomPartyInfo> DRoomPartyList = new ArrayList<DRoomPartyInfo>();

    public DRoom_FullInfo(String masterID, String DRoomName, int DRoomDate, List<DRoomItemInfo> DRoomItemList, int totalPrice, List<DRoomPartyInfo> DRoomPartyList) {
        MasterID = masterID;
        this.DRoomName = DRoomName;
        this.DRoomDate = DRoomDate;
        this.DRoomItemList = DRoomItemList;
        this.totalPrice = totalPrice;
        this.DRoomPartyList = DRoomPartyList;
    }

    public String getMasterID() {
        return MasterID;
    }

    public void setMasterID(String masterID) {
        MasterID = masterID;
    }

    public String getDRoomName() {
        return DRoomName;
    }

    public void setDRoomName(String DRoomName) {
        this.DRoomName = DRoomName;
    }

    public int getDRoomDate() {
        return DRoomDate;
    }

    public void setDRoomDate(int DRoomDate) {
        this.DRoomDate = DRoomDate;
    }

    public List<DRoomItemInfo> getDRoomItemList() {
        return DRoomItemList;
    }

    public void setDRoomItemList(List<DRoomItemInfo> DRoomItemList) {
        this.DRoomItemList = DRoomItemList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<DRoomPartyInfo> getDRoomPartyList() {
        return DRoomPartyList;
    }

    public void setDRoomPartyList(List<DRoomPartyInfo> DRoomPartyList) {
        this.DRoomPartyList = DRoomPartyList;
    }
}
