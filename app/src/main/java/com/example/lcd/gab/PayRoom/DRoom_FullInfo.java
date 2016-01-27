package com.example.lcd.gab.PayRoom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-15.
 */
public class DRoom_FullInfo implements Serializable {
    int DRoomRcdNum;
    String MasterID;
    String MasterPhoneNum;
    String DRoomName;
    int DRoomFinished=0;
    int DRoomDate;
    List<DRoomItemInfo> DRoomItemList = new ArrayList<DRoomItemInfo>();
    int totalPrice;
    List<DRoomPartyInfo> DRoomPartyList = new ArrayList<DRoomPartyInfo>();

    public DRoom_FullInfo(){ }

    public DRoom_FullInfo(int rcdNum, int roomDate, String roomName){
        DRoomRcdNum = rcdNum;
        DRoomDate = roomDate;
        DRoomName = roomName;
    }

    public DRoom_FullInfo(int rcdNum, List<DRoomPartyInfo> roomMemberDatas){
        DRoomRcdNum = rcdNum;
        DRoomPartyList = roomMemberDatas;
    }

    public DRoom_FullInfo(int rcdNum, int roomDate, String roomName, List<DRoomPartyInfo> roomMemberDatas){
        DRoomRcdNum = rcdNum;
        DRoomDate = roomDate;
        DRoomName = roomName;
        DRoomPartyList = roomMemberDatas;

    }

    public DRoom_FullInfo(String masterID, String masterPhoneNum, String DRoomName, int DRoomDate, List<DRoomItemInfo> DRoomItemList, int totalPrice, List<DRoomPartyInfo> DRoomPartyList) {
        MasterID = masterID;
        MasterPhoneNum = masterPhoneNum;
        this.DRoomName = DRoomName;
        this.DRoomDate = DRoomDate;
        this.DRoomItemList = DRoomItemList;
        this.totalPrice = totalPrice;
        this.DRoomPartyList = DRoomPartyList;
    }


    public DRoom_FullInfo(int DRoomRcdNum, String masterID, String masterPhoneNum, String DRoomName, int DRoomFinished, int DRoomDate, List<DRoomItemInfo> DRoomItemList, int totalPrice, List<DRoomPartyInfo> DRoomPartyList) {
        this.DRoomRcdNum = DRoomRcdNum;
        MasterID = masterID;
        MasterPhoneNum = masterPhoneNum;
        this.DRoomName = DRoomName;
        this.DRoomFinished = DRoomFinished;
        this.DRoomDate = DRoomDate;
        this.DRoomItemList = DRoomItemList;
        this.totalPrice = totalPrice;
        this.DRoomPartyList = DRoomPartyList;
    }


    public int getDRoomFinished() {
        return DRoomFinished;
    }

    public void setDRoomFinished(int DRoomFinished) {
        this.DRoomFinished = DRoomFinished;
    }

    public String getMasterID() {
        return MasterID;
    }

    public void setMasterID(String masterID) {
        MasterID = masterID;
    }

    public String getMasterPhoneNum() {
        return MasterPhoneNum;
    }

    public void setMasterPhoneNum(String masterPhoneNum) {
        MasterPhoneNum = masterPhoneNum;
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

    public int getDRoomRcdNum() {
        return DRoomRcdNum;
    }

    public void setDRoomRcdNum(int DRoomRcdNum) {
        this.DRoomRcdNum = DRoomRcdNum;
    }
}
