package com.example.lcd.gab.PayRoom;

import java.util.List;

/**
 * Created by Administrator on 2016-01-15.
 */
public class DRoomPartyInfo {
    int partyPhonenum; //파티원 폰번호
    int partyMoney; //갚아야할 금액
    int party_finished;//0이면 미정산, 1이면 정산완료

    public DRoomPartyInfo(int partyPhonenum, int partyMoney, int party_finished) {
        this.partyPhonenum = partyPhonenum;
        this.partyMoney = partyMoney;
        this.party_finished = party_finished;
    }

    public int getPartyPhonenum() {
        return partyPhonenum;
    }

    public void setPartyPhonenum(int partyPhonenum) {
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
