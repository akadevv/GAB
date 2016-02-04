package com.example.lcd.gab.MasterInfo;

/**
 * Created by LCD on 2016-01-19.
 */
public class MasterInfo {
    private static MasterInfo masterInfo = new MasterInfo();
    private String masterID;
    private String masterName;
    private String masterPhoneNum;
    private String masterBankName;
    private String masterBankNum;


    private MasterInfo(){    }

    public static MasterInfo getMasterInfo() {
        if(masterInfo==null){
            masterInfo = new MasterInfo();
        }
        return masterInfo;
    }

    public static void setMasterInfo(MasterInfo masterInfo) {
        if(masterInfo==null){
            masterInfo = new MasterInfo();
        }
        MasterInfo.masterInfo = masterInfo;
    }

    public String getMasterID() {
        return masterID;
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getMasterPhoneNum() {
        return masterPhoneNum;
    }

    public void setMasterPhoneNum(String masterPhoneNum) {
        this.masterPhoneNum = masterPhoneNum;
    }

    public String getMasterBankName() {
        return masterBankName;
    }

    public void setMasterBankName(String masterBankName) {
        this.masterBankName = masterBankName;
    }


    public String getMasterBankNum() {
        return masterBankNum;
    }

    public void setMasterBankNum(String masterBankNum) {
        this.masterBankNum = masterBankNum;
    }
}
