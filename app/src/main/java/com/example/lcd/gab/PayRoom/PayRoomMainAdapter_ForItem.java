package com.example.lcd.gab.PayRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.example.lcd.gab.R;

import java.util.List;

/**
 * Created by LCD on 2016-01-26.
 */
public class PayRoomMainAdapter_ForItem extends RecyclerView.Adapter<PayRoomMainAdapter_ForItem.ListViewHolder>{

    private Context mContext;
    private List<DRoomItemInfo> mRoomItemInfos;

    public PayRoomMainAdapter_ForItem(List<DRoomItemInfo> roomItemInfos, Context context){
        mContext = context;
        mRoomItemInfos = roomItemInfos;
    }

    @Override
    public int getItemCount(){return mRoomItemInfos.size();}

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position){
        DRoomItemInfo roomItemInfo = mRoomItemInfos.get(position);
        int price = roomItemInfo.getDRoomitem_price();
        int number = roomItemInfo.getDRoomitem_number();

        listViewHolder.vPrice.addTextChangedListener(new MoneyUnitListener_Text(listViewHolder.vPrice));
        listViewHolder.vPrice.setText(Integer.toString(price));
        listViewHolder.vItem.setText(roomItemInfo.getDRoomitem_name());
        listViewHolder.vNumber.setText(Integer.toString(number));

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_room_main_item_for_item,parent,false);
        return new ListViewHolder(viewItem);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vItem;
        protected TextView vPrice;
        protected TextView vNumber;

     public ListViewHolder(View v){
         super(v);
         vItem = (TextView) v.findViewById(R.id.item);
         vPrice = (TextView) v.findViewById(R.id.price);
         vNumber = (TextView) v.findViewById(R.id.the_number);
     }
    }
}
