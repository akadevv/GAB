package com.example.lcd.gab.TEST;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-01-17.
 */
public class ParcelData implements Parcelable {
    int partyPhoneNum;
    int partyMoney;
    int partyFinished;

    public ParcelData(Parcel source) {
        partyPhoneNum = source.readInt();
        partyMoney = source.readInt();
        partyFinished = source.readInt();

    }

    public ParcelData(int partyPhoneNum, int partyMoney, int partyFinished) {
        this.partyPhoneNum = partyPhoneNum;
        this.partyMoney = partyMoney;
        this.partyFinished = partyFinished;
    }


    public int getPartyPhoneNum() {
        return partyPhoneNum;
    }

    public void setPartyPhoneNum(int partyPhoneNum) {
        this.partyPhoneNum = partyPhoneNum;
    }

    public int getPartyMoney() {
        return partyMoney;
    }

    public void setPartyMoney(int partyMoney) {
        this.partyMoney = partyMoney;
    }

    public int getPartyFinished() {
        return partyFinished;
    }

    public void setPartyFinished(int partyFinished) {
        this.partyFinished = partyFinished;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(partyPhoneNum);
        dest.writeInt(partyMoney);
        dest.writeInt(partyFinished);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ParcelData> CREATOR = new Parcelable.Creator<ParcelData>() {

        @Override
        public ParcelData createFromParcel(Parcel source) {
            return new ParcelData(source);
        }

        @Override
        public ParcelData[] newArray(int size) {
            return new ParcelData[0];
        }
    };

}
