package com.example.lcd.gab.Friend_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ListViewHolder>{


    private ArrayList<FriendData> mFriendDataList;

    public FriendListAdapter(ArrayList<FriendData> friendDataList){
        this.mFriendDataList = friendDataList;
    }

    @Override
    public int getItemCount(){return mFriendDataList.size();}

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position){
        FriendData friendData = mFriendDataList.get(position);
        listViewHolder.vName.setText(friendData.getName());
        listViewHolder.vPhone.setText(friendData.getPhoneNum());
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vPhone;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vPhone = (TextView) v.findViewById(R.id.phone_number);
        }
    }
}
