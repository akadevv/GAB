package com.example.lcd.gab.PayRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lcd.gab.R;
import com.example.lcd.gab.RoomList.RoomListMain;

import java.util.List;

/**
 * Created by LCD on 2016-01-26.
 */
public class PayRoomMainAdapter_ForItem extends RecyclerView.Adapter<PayRoomMainAdapter_ForItem.ListViewHolder>{

    private Context mContext;
    private List<DRoom_FullInfo> mRoomListDatas = RoomListMain.getRoomListDatas();

    public PayRoomMainAdapter_ForItem(List<DRoom_FullInfo> roomListDatas, Context context){
        mContext = context;
        mRoomListDatas = roomListDatas;
    }

    @Override
    public int getItemCount(){return mRoomListDatas.size();}

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position){
        DRoom_FullInfo roomListData = mRoomListDatas.get(position);
        

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_room_main_item_for_item,parent,false);
        return new ListViewHolder(viewItem);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vItem;
        protected TextView vPrice;

     public ListViewHolder(View v){
         super(v);
         vItem = (TextView) v.findViewById(R.id.item);
         vPrice = (TextView) v.findViewById(R.id.price);
     }
    }
}
