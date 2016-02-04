package com.example.lcd.gab.OwedList;

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
import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.PayRoom.DRoomPartyInfo;
import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.example.lcd.gab.PayRoom.PayRoomMainPage;
import com.example.lcd.gab.R;

import java.util.List;

/**
 * Created by LCD on 2016-02-02.
 */
public class OwedListAdapter extends RecyclerView.Adapter<OwedListAdapter.ListViewHolder> {

    private boolean room = false;
    private Context mContext;
    private List<DRoom_FullInfo> mRoomListDatas;

    public OwedListAdapter(List<DRoom_FullInfo> roomListDatas, Context context){
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
        String roomMasterPhone = roomListData.getMasterPhoneNum();

        if(!MainActivity.getMasterInfo().getUserPhoneNum().equals(roomMasterPhone)){

            for(int i = 0 ; i < partyInfos.size(); i++){
                if(!MainActivity.getMasterInfo().getUserPhoneNum().equals(partyInfos.get(i).getPartyPhonenum())) {
                    partyInfo.setRoomRcdNum(partyInfos.get(i).getRoomRcdNum());
                    partyInfo.setParty_name(partyInfos.get(i).getParty_name());
                    partyInfo.setPartyMoney(partyInfos.get(i).getPartyMoney());
                    partyInfo.setParty_finished(partyInfos.get(i).getParty_finished());
                    room = true;
                }else if(partyInfo.getRoomRcdNum()==0)
                    room = false;
            }

            listViewHolder.vName.setText(partyInfo.getParty_name());
            listViewHolder.vCost.addTextChangedListener(new MoneyUnitListener_Text(listViewHolder.vCost));
            listViewHolder.vCost.setText(Integer.toString(partyInfo.getPartyMoney()));

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
        }else
            listViewHolder.vRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.owed_list_item,parent,false);
        return new ListViewHolder(viewItem);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vCost;
        protected RelativeLayout vRelativeLayout;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.owed_list_item_name);
            vCost = (TextView) v.findViewById(R.id.owed_list_item_cost);
            vRelativeLayout = (RelativeLayout) v.findViewById(R.id.owed_list_item_layout);
        }
    }
}
