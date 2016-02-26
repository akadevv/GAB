package com.example.lcd.gab.MainPage;

import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.lcd.gab.Friend_list.FriendListMain;
import com.example.lcd.gab.OwedList.OwedListMain;
import com.example.lcd.gab.ReceivableList.ReceivableListMain;
import com.example.lcd.gab.RoomList.RoomListMain;

/**
 * Created by LCD on 2016-01-12.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int MAX_PAGE = 4;
    android.support.v4.app.Fragment cur_fragement = new android.support.v4.app.Fragment();

private String log = "jjunest";
    public PagerAdapter(android.support.v4.app.FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if(position<0 || MAX_PAGE<position)
            return null;

        switch (position){
            case 0:
                cur_fragement = new FriendListMain();
                break;
            case 1:
                Log.d(log, "RoomList 시작");
                cur_fragement = new RoomListMain();
                Log.d(log, "RoomList 끝");
                break;
            case 2:
                Log.d(log, "RoomReceivable 시작");
                cur_fragement= new ReceivableListMain();
                Log.d(log, "RoomReceivable 끝");
                break;
            case 3:
                cur_fragement = new OwedListMain();
                break;
        }

        return cur_fragement;
    }

    @Override
    public int getCount(){
        return MAX_PAGE;
    }

}

