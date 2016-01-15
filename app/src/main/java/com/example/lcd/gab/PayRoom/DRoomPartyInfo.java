package com.example.lcd.gab.PayRoom;

import java.util.List;

/**
 * Created by Administrator on 2016-01-15.
 */
public class DRoomPartyInfo {
    String partyPhonenum; //파티원 폰번호
    String partyMoney; //갚아야할 금액
    int party_finished;//0이면 미정산, 1이면 정산완료

    public DRoomPartyInfo(String partyPhonenum, String partyMoney, int party_finished) {
        this.partyPhonenum = partyPhonenum;
        this.partyMoney = partyMoney;
        this.party_finished = party_finished;
    }

    public String getPartyPhonenum() {
        return partyPhonenum;
    }

    public void setPartyPhonenum(String partyPhonenum) {
        this.partyPhonenum = partyPhonenum;
    }

    public String getPartyMoney() {
        return partyMoney;
    }

    public void setPartyMoney(String partyMoney) {
        this.partyMoney = partyMoney;
    }

    public int getParty_finished() {
        return party_finished;
    }

    public void setParty_finished(int party_finished) {
        this.party_finished = party_finished;
    }
}
