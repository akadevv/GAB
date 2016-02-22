package com.example.lcd.gab.ReceivableList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.example.lcd.gab.PayRoom.DRoomPartyInfo;
import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.example.lcd.gab.PayRoom.PayRoomMainPage;
import com.example.lcd.gab.R;

import java.util.List;

/**
 * Created by LCD on 2016-01-28.
 */
public class ReceivableListAdapter extends RecyclerView.Adapter<ReceivableListAdapter.ListViewHolder>{

    private boolean room = false;
    private Context mContext;
    private List<DRoom_FullInfo> mRoomListDatas;
    private List<DRoomPartyInfo> mRoomPartyInfos;

    public ReceivableListAdapter(List<DRoomPartyInfo> roomPartyInfos, List<DRoom_FullInfo> roomListDatas, Context context){
        mContext = context;
        mRoomListDatas = roomListDatas;
        mRoomPartyInfos = roomPartyInfos;
    }

    @Override
    public int getItemCount(){return mRoomPartyInfos.size();}

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position){
        DRoomPartyInfo roomPartyInfo = mRoomPartyInfos.get(position);
        DRoom_FullInfo roomListData = new DRoom_FullInfo();

        listViewHolder.vName.setText(roomPartyInfo.getParty_name());
        listViewHolder.vCost.addTextChangedListener(new MoneyUnitListener_Text(listViewHolder.vCost));
        listViewHolder.vCost.setText(Integer.toString(roomPartyInfo.getPartyMoney()));

        for(int i = 0; i < mRoomListDatas.size(); i++){
            if(roomPartyInfo.getRoomRcdNum() == mRoomListDatas.get(i).getDRoomRcdNum()){
                roomListData = mRoomListDatas.get(i);
                room = true;
            }
            else
                room = false;
        }

        final DRoom_FullInfo temp = roomListData;

        listViewHolder.vRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room){
                    Intent intent = new Intent(mContext, PayRoomMainPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedDRoomInfo", temp);
                    intent.putExtras(bundle);

                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivable_list_item,parent,false);
        return new ListViewHolder(viewItem);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vCost;
        protected RelativeLayout vRelativeLayout;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.receivable_list_item_name);
            vCost = (TextView) v.findViewById(R.id.receivable_list_item_cost);
            vRelativeLayout = (RelativeLayout) v.findViewById(R.id.receivable_list_item_layout);
        }
    }
}
