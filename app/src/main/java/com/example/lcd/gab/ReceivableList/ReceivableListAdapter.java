package com.example.lcd.gab.ReceivableList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.MainActivity;
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

    public ReceivableListAdapter(List<DRoom_FullInfo> roomListDatas, Context context){
        mContext = context;
        mRoomListDatas = roomListDatas;
    }

    @Override
    public int getItemCount(){return mRoomListDatas.size();}

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position){
        final DRoom_FullInfo roomListData = mRoomListDatas.get(position);
        List<DRoomPartyInfo> partyInfos = roomListData.getDRoomPartyList();
        DRoomPartyInfo partyInfo = new DRoomPartyInfo();

        for(int i = 0 ; i < partyInfos.size(); i++){
            if(!MainActivity.getMasterInfo().getMasterPhoneNum().equals(partyInfos.get(i).getPartyPhonenum())) {
                partyInfo.setRoomRcdNum(partyInfos.get(i).getRoomRcdNum());
                partyInfo.setParty_name(partyInfos.get(i).getParty_name());
                partyInfo.setPartyMoney(partyInfos.get(i).getPartyMoney());
                partyInfo.setParty_finished(partyInfos.get(i).getParty_finished());
                room = true;
            }else if(partyInfo.getRoomRcdNum()==0)
                room = false;
        }

        listViewHolder.vName.setText(partyInfo.getParty_name());
        listViewHolder.vCost.setText(Integer.toString(partyInfo.getPartyMoney()));

        if(partyInfo.getParty_finished()==0){
            listViewHolder.vFinished.setVisibility(View.GONE);
            listViewHolder.vNotFinished.setVisibility(View.VISIBLE);
        }else{
            listViewHolder.vFinished.setVisibility(View.VISIBLE);
            listViewHolder.vNotFinished.setVisibility(View.GONE);
        }

        listViewHolder.vRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room){
                    Intent intent = new Intent(mContext, PayRoomMainPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedDRoomInfo", roomListData);
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
        protected ImageView vFinished;
        protected ImageView vNotFinished;
        protected RelativeLayout vRelativeLayout;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.receivable_list_item_name);
            vCost = (TextView) v.findViewById(R.id.receivable_list_item_cost);
            vFinished = (ImageView) v.findViewById(R.id.receivable_list_finished);
            vNotFinished = (ImageView) v.findViewById(R.id.receivable_list_not_finished);
            vRelativeLayout = (RelativeLayout) v.findViewById(R.id.receivable_list_item_layout);
        }
    }
}
