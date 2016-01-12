package com.example.lcd.gab.MainPage;

import android.support.v4.app.FragmentPagerAdapter;

import com.example.lcd.gab.Friend_list.FriendListMain;

/**
 * Created by LCD on 2016-01-12.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int MAX_PAGE = 4;
    android.support.v4.app.Fragment cur_fragement = new android.support.v4.app.Fragment();


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
                cur_fragement = new page_02();
                break;
            case 2:
                cur_fragement= new page_03();
                break;
            case 3:
                cur_fragement = new page_04();
                break;
        }


        return cur_fragement;
    }

    @Override
    public int getCount(){
        return MAX_PAGE;
    }


}

