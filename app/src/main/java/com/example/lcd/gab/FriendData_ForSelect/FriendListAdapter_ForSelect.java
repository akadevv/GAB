package com.example.lcd.gab.FriendData_ForSelect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.Friend_list.FriendData;
import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-01-17.
 */
public class FriendListAdapter_ForSelect extends RecyclerView.Adapter<FriendListAdapter_ForSelect.ListViewHolder> {
    public static String log = "jjunest";
    private Context mContext;
    private ArrayList<FriendData> mFriendDataList;
    private static ArrayList<FriendData> filteredDataList = new ArrayList<>();

    public FriendListAdapter_ForSelect(Context mContext, ArrayList<FriendData> mFriendDataList) {
        this.mContext = mContext;
        this.mFriendDataList = mFriendDataList;
    }

    @Override
    public int getItemCount() {
        return mFriendDataList.size();
    }

    @Override
    public FriendListAdapter_ForSelect.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item_for_select, parent, false);
        return new FriendListAdapter_ForSelect.ListViewHolder(itemView);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vPhone;
        protected RelativeLayout vRelativeLayout;
        protected CheckBox friendSelected;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vPhone = (TextView) v.findViewById(R.id.phone_number);
            vRelativeLayout = (RelativeLayout) v.findViewById(R.id.FriendSearchLayout);
            friendSelected = (CheckBox)v.findViewById(R.id.FriendCheckBOX);
        }
    }

    @Override
    public void onBindViewHolder(FriendListAdapter_ForSelect.ListViewHolder listViewHolder, int position) {
        final FriendData friendData = mFriendDataList.get(position);
        listViewHolder.vName.setText(friendData.getName());
        listViewHolder.vPhone.setText(friendData.getPhoneNum());
        listViewHolder.friendSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if(cb.isChecked()) {
                    Log.d(log, "this is checkbox clicked");
                    filteredDataList.add(friendData);
                }else{
                    Log.d(log, "this is not checkbox clicked");
                    filteredDataList.remove(friendData);
                }

            }
        });

    }

    public static ArrayList<FriendData> getFilteredDataList(){
        return filteredDataList;
    }
}
