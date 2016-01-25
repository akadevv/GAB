package com.example.lcd.gab.PayRoom;

/**
 * Created by Administrator on 2016-01-15.
 */
public class DRoomPartyInfo {
    int roomRcdNum;
    String party_name;
    String partyPhonenum; //파티원 폰번호
    int partyMoney; //갚아야할 금액
    int party_finished;//0이면 미정산, 1이면 정산완료

    public DRoomPartyInfo(){ }

    public DRoomPartyInfo(String party_name, String partyPhonenum, int partyMoney, int party_finished) {
        this.party_name = party_name;
        this.partyPhonenum = partyPhonenum;
        this.partyMoney = partyMoney;
        this.party_finished = party_finished;
    }

    public int getRoomRcdNum() {
        return roomRcdNum;
    }

    public void setRoomRcdNum(int roomRcdNum) {
        this.roomRcdNum = roomRcdNum;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getPartyPhonenum() {
        return partyPhonenum;
    }

    public void setPartyPhonenum(String partyPhonenum) {
        this.partyPhonenum = partyPhonenum;
    }

    public int getPartyMoney() {
        return partyMoney;
    }

    public void setPartyMoney(int partyMoney) {
        this.partyMoney = partyMoney;
    }

    public int getParty_finished() {
        return party_finished;
    }

    public void setParty_finished(int party_finished) {
        this.party_finished = party_finished;
    }
}
