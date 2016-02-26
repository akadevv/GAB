package com.example.lcd.gab.MainPage;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.lcd.gab.Friend_list.FriendListMain;
import com.example.lcd.gab.OwedList.OwedListMain;
import com.example.lcd.gab.R;
import com.example.lcd.gab.ReceivableList.ReceivableListMain;
import com.example.lcd.gab.RoomList.RoomListMain;

/**
 * Created by LCD on 2016-01-12.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int MAX_PAGE = 4;
    private Context mContext;

    private android.support.v4.app.Fragment cur_fragment;

    public PagerAdapter(android.support.v4.app.FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.main_page, null, false);

        ImageView friendListUnselected = (ImageView) view.findViewById(R.id.friend_list_icon_unselected);
        ImageView friendListSelected = (ImageView) view.findViewById(R.id.friend_list_icon_selected);
        ImageView roomListUnselected = (ImageView) view.findViewById(R.id.room_list_icon_unselected);
        ImageView roomListSelected = (ImageView) view.findViewById(R.id.room_list_icon_selected);
        ImageView receivableListUnselected = (ImageView) view.findViewById(R.id.receivable_list_icon_unselected);
        ImageView receivableListSelected = (ImageView) view.findViewById(R.id.receivable_list_icon_selected);
        ImageView owedListUnselected = (ImageView) view.findViewById(R.id.owed_list_icon_unselected);
        ImageView owedListSelected = (ImageView) view.findViewById(R.id.owed_list_icon_selected);

        if(position<0 || MAX_PAGE<position)
            return null;

        switch (position){
            case 0:{
                friendListUnselected.setVisibility(View.GONE);
                friendListSelected.setVisibility(View.VISIBLE);

                roomListUnselected.setVisibility(View.VISIBLE);
                roomListSelected.setVisibility(View.GONE);

                receivableListUnselected.setVisibility(View.VISIBLE);
                receivableListSelected.setVisibility(View.GONE);

                owedListUnselected.setVisibility(View.VISIBLE);
                owedListSelected.setVisibility(View.GONE);

                return cur_fragment = new FriendListMain();

            }case 1: {
                friendListUnselected.setVisibility(View.VISIBLE);
                friendListSelected.setVisibility(View.GONE);

                roomListUnselected.setVisibility(View.GONE);
                roomListSelected.setVisibility(View.VISIBLE);

                receivableListUnselected.setVisibility(View.VISIBLE);
                receivableListSelected.setVisibility(View.GONE);

                owedListUnselected.setVisibility(View.VISIBLE);
                owedListSelected.setVisibility(View.GONE);

                return cur_fragment = new RoomListMain();

            }case 2: {
                friendListUnselected.setVisibility(View.VISIBLE);
                friendListSelected.setVisibility(View.GONE);

                roomListUnselected.setVisibility(View.VISIBLE);
                roomListSelected.setVisibility(View.GONE);

                receivableListUnselected.setVisibility(View.GONE);
                receivableListSelected.setVisibility(View.VISIBLE);

                owedListUnselected.setVisibility(View.VISIBLE);
                owedListSelected.setVisibility(View.GONE);

                return cur_fragment = new ReceivableListMain();

            }case 3:
                friendListUnselected.setVisibility(View.VISIBLE);
                friendListSelected.setVisibility(View.GONE);

                roomListUnselected.setVisibility(View.VISIBLE);
                roomListSelected.setVisibility(View.GONE);

                receivableListUnselected.setVisibility(View.VISIBLE);
                receivableListSelected.setVisibility(View.GONE);

                owedListUnselected.setVisibility(View.GONE);
                owedListSelected.setVisibility(View.VISIBLE);

                return cur_fragment = new OwedListMain();
        }
        return cur_fragment;
    }

    @Override
    public int getCount(){
        return MAX_PAGE;
    }

}

