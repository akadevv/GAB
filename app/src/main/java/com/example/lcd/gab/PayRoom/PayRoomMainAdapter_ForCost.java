package com.example.lcd.gab.PayRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lcd.gab.R;

import java.util.List;

/**
 * Created by LCD on 2016-01-27.
 */
public class PayRoomMainAdapter_ForCost extends RecyclerView.Adapter<PayRoomMainAdapter_ForCost.ListViewHolder>{

    int totalCost = 0;
    private Context mContext;
    private List<DRoomPartyInfo> mRoomPartyInfos;

    public PayRoomMainAdapter_ForCost(List<DRoomPartyInfo> roomPartyInfos, Context context){
        mContext = context;
        mRoomPartyInfos = roomPartyInfos;
    }

    @Override
    public int getItemCount(){return mRoomPartyInfos.size();}

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position){
        DRoomPartyInfo roomPartyInfo = mRoomPartyInfos.get(position);

        totalCost += roomPartyInfo.getPartyMoney();

        listViewHolder.vMember.setText(roomPartyInfo.getParty_name());
        listViewHolder.vCost.setText(Integer.toString(roomPartyInfo.getPartyMoney()));
        if(roomPartyInfo.getParty_finished()==0) {
            listViewHolder.vNotFinished.setVisibility(View.VISIBLE);
            listViewHolder.vFinished.setVisibility(View.GONE);
        }else{
            listViewHolder.vNotFinished.setVisibility(View.GONE);
            listViewHolder.vFinished.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_room_main_item_for_cost,parent,false);
        return new ListViewHolder(viewItem);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vMember;
        protected TextView vCost;
        protected ImageView vNotFinished;
        protected ImageView vFinished;

        public ListViewHolder(View v){
            super(v);
            vMember = (TextView) v.findViewById(R.id.member_name);
            vCost = (TextView) v.findViewById(R.id.member_cost);
            vNotFinished = (ImageView) v.findViewById(R.id.not_finished);
            vFinished = (ImageView) v.findViewById(R.id.finished);
        }
    }

    public int getTotalCost(){
        return totalCost;
    }
}
