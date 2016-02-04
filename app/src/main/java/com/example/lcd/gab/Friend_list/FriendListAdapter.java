package com.example.lcd.gab.Friend_list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcd.gab.FriendListDBManager;
import com.example.lcd.gab.PayRoom.PayRoomMakingPage;
import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ListViewHolder>{

    private Context mContext;
    private AlertDialog popup;
    private ArrayList<FriendData> mFriendDataList;
    private FriendListDBManager mDB = FriendListMain.getFriendListDB();

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
        listViewHolder.vRecyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = createDialog(friendData.getName(), friendData.getPhoneNum(), friendData);
                popup.show();
                popup.setCanceledOnTouchOutside(true);

            }
        });

        if(friendData.getBookMark()==0)
            listViewHolder.vFriendTap.setVisibility(View.GONE);
        else
            listViewHolder.vFriendTap.setVisibility(View.VISIBLE);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vPhone;
        protected RelativeLayout vRecyclerItem;
        protected RelativeLayout vFriendTap;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vPhone = (TextView) v.findViewById(R.id.phone_number);
            vRecyclerItem = (RelativeLayout) v.findViewById(R.id.friend_recycler_view_item);
            vFriendTap = (RelativeLayout) v.findViewById(R.id.friend_recycler_view_friend_tap);
        }
    }

    private AlertDialog createDialog(String name, final String phoneNum, final FriendData friendData){
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.friend_list_popup_item, null);

        final AlertDialog.Builder pop = new AlertDialog.Builder(mContext);
        pop.setView(view);

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView payRoom = (TextView) view.findViewById(R.id.pay_room);
        TextView transferRoom = (TextView) view.findViewById(R.id.transfer_room);
        TextView call = (TextView) view.findViewById(R.id.calling);
        TextView setBookMark = (TextView) view.findViewById(R.id.friend_list_popup_set_bookmark);
        TextView resetBookMark = (TextView) view.findViewById(R.id.friend_list_popup_reset_bookmark);

        userName.setText(name);

        payRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayRoomMakingPage.class);
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

        if(friendData.getBookMark()==1){
            setBookMark.setVisibility(View.GONE);
            resetBookMark.setVisibility(View.VISIBLE);
            resetBookMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendData.setBookMark(0);
                    popup.dismiss();
                    Toast toast = Toast.makeText(mContext,"즐겨찾기에서 제거되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        else{
            setBookMark.setVisibility(View.VISIBLE);
            resetBookMark.setVisibility(View.GONE);
            setBookMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendData.setBookMark(1);
                    popup.dismiss();
                    Toast toast = Toast.makeText(mContext,"즐겨찾기에 등록되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();

                }
            });
        }

        return  pop.create();
    }

    private ArrayList<FriendData> bookMark(ArrayList<FriendData> friendDataList){
        ArrayList<FriendData> bookMarkList = new ArrayList<>();
        ArrayList<FriendData> normalList = new ArrayList<>();
        ArrayList<FriendData> friendList = new ArrayList<>();

        for(int i = 0; i < friendDataList.size(); i++){
                if(friendDataList.get(i).getBookMark()==1){
                    bookMarkList.add(friendDataList.get(i));
                }
                else{
                    normalList.add(friendDataList.get(i));
                }
        }

        for(int i = 0; i < bookMarkList.size(); i++)
            friendList.add(bookMarkList.get(i));
        for(int i = 0; i < normalList.size(); i++)
            friendList.add(normalList.get(i));

        return friendList;
    }

}
