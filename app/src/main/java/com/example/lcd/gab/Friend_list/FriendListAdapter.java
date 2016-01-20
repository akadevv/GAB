package com.example.lcd.gab.Friend_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.PayRoom.PayRoomMain;
import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ListViewHolder>{

    private Context mContext;
    private Dialog popup;
    private ArrayList<FriendData> mFriendDataList;

    public FriendListAdapter(ArrayList<FriendData> friendDataList, Context context){
        this.mFriendDataList = friendDataList;
        this.mContext = context;
    }

    @Override
    public int getItemCount(){return mFriendDataList.size();}

    @Override
    public void onBindViewHolder(final ListViewHolder listViewHolder, final int position){
        final FriendData friendData = mFriendDataList.get(position);



        listViewHolder.vName.setText(friendData.getName());
        listViewHolder.vPhone.setText(friendData.getPhoneNum());
        listViewHolder.vRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = createDialog(friendData.getName(), friendData.getPhoneNum());
                popup.show();

            }
        });
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vPhone;
        protected RelativeLayout vRelativeLayout;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vPhone = (TextView) v.findViewById(R.id.phone_number);
            vRelativeLayout = (RelativeLayout) v.findViewById(R.id.friend_recyclerview_item);
        }
    }

    private AlertDialog createDialog(String name, final String phoneNum){
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.friend_list_popup_item, null);

        AlertDialog.Builder popup = new AlertDialog.Builder(mContext);
        popup.setView(view);

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView payRoom = (TextView) view.findViewById(R.id.pay_room);
        TextView transferRoom = (TextView) view.findViewById(R.id.transfer_room);
        TextView call = (TextView) view.findViewById(R.id.calling);

        userName.setText(name);

        payRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayRoomMain.class);
                mContext.startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                    mContext.startActivity(intent);
                }catch (SecurityException e){

                }

            }
        });

        return  popup.create();
    }
}
