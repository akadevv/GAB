package com.example.lcd.gab.PayRoom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-15.
 */
public class DRoom_FullInfo implements Serializable {
    int DRoomRcdNum; //current TABLE의 RCDNO;
    String masterName;
    String MasterPhoneNum;
    String DRoomName;
    int DRoomFinished=0;
    int DRoomDate;
    List<DRoomItemInfo> DRoomItemList = new ArrayList<DRoomItemInfo>();
    int totalPrice;
    List<DRoomPartyInfo> DRoomPartyList = new ArrayList<DRoomPartyInfo>();

    public DRoom_FullInfo(){ }

    public DRoom_FullInfo(int rcdNum, int totalPrice, List<DRoomItemInfo> roomItemDatas){
        this.DRoomRcdNum = rcdNum;
        this.DRoomItemList = roomItemDatas;
        this.totalPrice = totalPrice;
    }

    public DRoom_FullInfo(int rcdNum,int totalPrice, ArrayList<DRoomPartyInfo> roomMemberDatas){
        this.DRoomRcdNum = rcdNum;
        this.DRoomPartyList = roomMemberDatas;
    }

    public DRoom_FullInfo(int rcdNum, int roomDate, String roomName){
        this.DRoomRcdNum = rcdNum;
        this.DRoomDate = roomDate;
        this.DRoomName = roomName;
    }

    public DRoom_FullInfo(int rcdNum, int roomDate, String roomName, List<DRoomPartyInfo> roomMemberDatas){
        DRoomRcdNum = rcdNum;
        DRoomDate = roomDate;
        DRoomName = roomName;
        DRoomPartyList = roomMemberDatas;

    }
    public DRoom_FullInfo(int rcdNum,int DRoomDate,int finish, String masterName, String masterPhoneNum, String DRoomName) {
        this.DRoomRcdNum = rcdNum;
        this.masterName = masterName;
        this.DRoomFinished = finish;
        this.MasterPhoneNum = masterPhoneNum;
        this.DRoomName = DRoomName;
        this.DRoomDate = DRoomDate;
    }

    public DRoom_FullInfo(String masterName, String masterPhoneNum, String DRoomName, int DRoomDate, List<DRoomItemInfo> DRoomItemList, int totalPrice, List<DRoomPartyInfo> DRoomPartyList) {
        this.masterName = masterName;
        this.MasterPhoneNum = masterPhoneNum;
        this.DRoomName = DRoomName;
        this.DRoomDate = DRoomDate;
        this.DRoomItemList = DRoomItemList;
        this.totalPrice = totalPrice;
        this.DRoomPartyList = DRoomPartyList;
    }

    public DRoom_FullInfo(int rcdNum, String masterName, String masterPhoneNum, String DRoomName, int DRoomDate, List<DRoomItemInfo> DRoomItemList, int totalPrice, List<DRoomPartyInfo> DRoomPartyList) {
        this.DRoomRcdNum = rcdNum;
        this.masterName = masterName;
        MasterPhoneNum = masterPhoneNum;
        this.DRoomName = DRoomName;
        this.DRoomDate = DRoomDate;
        this.DRoomItemList = DRoomItemList;
        this.totalPrice = totalPrice;
        this.DRoomPartyList = DRoomPartyList;
    }

    public String getmasterName() {
        return masterName;
    }

    public void setmasterName(String masterName) {
        masterName = masterName;
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

    public int getDRoomFinished() {
        return DRoomFinished;
    }

    public void setDRoomFinished(int DRoomFinished) {
        this.DRoomFinished = DRoomFinished;
    }
}
