package com.innovest.lcd.gab.RoomList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovest.lcd.gab.PayRoom.DRoom_FullInfo;
import com.innovest.lcd.gab.PayRoom.PayRoomMainPage;
import com.innovest.lcd.gab.R;

import java.util.List;

/**
 * Created by LCD on 2016-01-18.
 */
public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ListViewHolder>{

    private Context mContext;
    private List<DRoom_FullInfo> mRoomListDatas;

    public RoomListAdapter(List<DRoom_FullInfo> roomListDatas, Context context){
        mContext=context;
        mRoomListDatas=roomListDatas;
    }

    @Override
    public int getItemCount(){return mRoomListDatas.size();}

    @Override
    public void onBindViewHolder(final ListViewHolder listViewHolder, final int position){
        final DRoom_FullInfo roomListData = mRoomListDatas.get(position);

        String memberName = "";

        listViewHolder.vRoomName.setText(roomListData.getDRoomName());
        listViewHolder.vRoomDate.setText(Integer.toString(roomListData.getDRoomDate()));

        for(int i=0;i<roomListData.getDRoomPartyList().size();i++)
            if(i != roomListData.getDRoomPartyList().size()-1)
                memberName = memberName + roomListData.getDRoomPartyList().get(i).getParty_name() +",";
            else
                memberName = memberName + roomListData.getDRoomPartyList().get(i).getParty_name();
        listViewHolder.vRoomMember.setText(memberName);

        listViewHolder.vRoomListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayRoomMainPage.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedDRoomInfo",roomListData);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_item,parent,false);
        return new ListViewHolder(viewItem);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vRoomName;
        protected TextView vRoomDate;
        protected TextView vRoomMember;
        protected RelativeLayout vRoomListItem;

        public ListViewHolder(View v){
            super(v);
            vRoomName = (TextView)v.findViewById(R.id.room_name);
            vRoomDate = (TextView)v.findViewById(R.id.room_date);
            vRoomMember = (TextView)v.findViewById(R.id.room_member);
            vRoomListItem = (RelativeLayout) v.findViewById(R.id.room_recyclerview_item);
        }

    }
}
