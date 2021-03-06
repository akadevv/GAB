package com.innovest.lcd.gab.Friend_list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovest.lcd.gab.PayRoom.PayRoomMakingPage;
import com.innovest.lcd.gab.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ListViewHolder>{

    private Context mContext;
    private AlertDialog popup;
    private ArrayList<FriendData> mFriendDataList;
    private RelativeLayout mRecyclerLayout;
    private boolean mForSearchView;

    public FriendListAdapter(ArrayList<FriendData> friendDataList, Context context, RelativeLayout recyclerLayout, boolean forSearchView){
        this.mFriendDataList = friendDataList;
        this.mContext = context;
        this.mRecyclerLayout = recyclerLayout;
        this.mForSearchView = forSearchView;
    }

    public FriendListAdapter(ArrayList<FriendData> friendDataList, Context context, boolean forSearchView){
        this.mFriendDataList = friendDataList;
        this.mContext = context;
        this.mForSearchView = forSearchView;
    }

    @Override
    public int getItemCount(){return mFriendDataList.size();}

    @Override
    public void onBindViewHolder(final ListViewHolder listViewHolder, final int position){
        final FriendData friendData = mFriendDataList.get(position);

        listViewHolder.vName.setText(friendData.getName());
        //listViewHolder.vPhone.setText(friendData.getPhoneNum());
        listViewHolder.vRecyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = createDialog(friendData, mFriendDataList, mRecyclerLayout, mForSearchView);
                popup.show();
                popup.setCanceledOnTouchOutside(true);

            }
        });
        if(mForSearchView) {
            if (position == 0) {
                listViewHolder.vFriendTap.setVisibility(View.VISIBLE);
                listViewHolder.vSearchResultTapText.setVisibility(View.VISIBLE);
                listViewHolder.vBookmarkTapText.setVisibility(View.GONE);
                listViewHolder.vFriendTapText.setVisibility(View.GONE);
            } else
                listViewHolder.vFriendTap.setVisibility(View.GONE);
        } else {
            listViewHolder.vSearchResultTapText.setVisibility(View.GONE);

            if (friendData.getBookMark() == 1 && position == 0) {
                listViewHolder.vFriendTap.setVisibility(View.VISIBLE);
                listViewHolder.vBookmarkTapText.setVisibility(View.VISIBLE);
                listViewHolder.vFriendTapText.setVisibility(View.GONE);
            } else if (friendData.getBookMark() == 0 && position == 0) {
                listViewHolder.vFriendTap.setVisibility(View.VISIBLE);
                listViewHolder.vBookmarkTapText.setVisibility(View.GONE);
                listViewHolder.vFriendTapText.setVisibility(View.VISIBLE);
            } else if (friendData.getBookMark() == 0 && mFriendDataList.get(position - 1).getBookMark() == 1) {
                listViewHolder.vFriendTap.setVisibility(View.VISIBLE);
                listViewHolder.vBookmarkTapText.setVisibility(View.GONE);
                listViewHolder.vFriendTapText.setVisibility(View.VISIBLE);
            } else
                listViewHolder.vFriendTap.setVisibility(View.GONE);
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        //protected TextView vPhone;
        protected TextView vFriendTapText;
        protected TextView vBookmarkTapText;
        protected TextView vSearchResultTapText;
        protected RelativeLayout vRecyclerItem;
        protected RelativeLayout vFriendTap;

        public ListViewHolder(View v){
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            //vPhone = (TextView) v.findViewById(R.id.phone_number);
            vFriendTapText = (TextView) v.findViewById(R.id.friend_recycler_view_friend_tap_text);
            vBookmarkTapText = (TextView) v.findViewById(R.id.friend_recycler_view_bookmark_tap_text);
            vSearchResultTapText = (TextView) v.findViewById(R.id.friend_recycler_view_search_result_tap_text);
            vRecyclerItem = (RelativeLayout) v.findViewById(R.id.friend_recycler_view_item);
            vFriendTap = (RelativeLayout) v.findViewById(R.id.friend_recycler_view_tap);
        }
    }

    private AlertDialog createDialog(final FriendData friendData, final ArrayList<FriendData> friendDataList, final RelativeLayout recyclerLayout, final boolean forSearchView){
        final LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.friend_list_popup_item, null);

        final AlertDialog.Builder pop = new AlertDialog.Builder(view.getContext());
        pop.setView(view);

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        TextView payRoom = (TextView) view.findViewById(R.id.pay_room);
        TextView transferRoom = (TextView) view.findViewById(R.id.transfer_room);
        TextView call = (TextView) view.findViewById(R.id.calling);
        TextView setBookMark = (TextView) view.findViewById(R.id.friend_list_popup_set_bookmark);
        TextView resetBookMark = (TextView) view.findViewById(R.id.friend_list_popup_reset_bookmark);

        userName.setText(friendData.getName());

        payRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PayRoomMakingPage.class);
                view.getContext().startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + friendData.getPhoneNum()));
                    view.getContext().startActivity(intent);
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

                    if(forSearchView)
                        bookmarkFunc(friendData);
                    else
                        bookmarkAdapter(friendData, friendDataList, recyclerLayout);

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

                    if(forSearchView)
                        bookmarkFunc(friendData);
                    else
                        bookmarkAdapter(friendData, friendDataList, recyclerLayout);

                    popup.dismiss();
                    Toast toast = Toast.makeText(mContext,"즐겨찾기에 등록되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        return  pop.create();
    }

    private void bookmarkAdapter(FriendData friendData, ArrayList<FriendData> friendDataList, RelativeLayout recyclerLayout){

        RecyclerView bookmarkRecyclerView = (RecyclerView) recyclerLayout.findViewById(R.id.friend_recycler_view);

        LinearLayoutManager bookmarkLayoutManager = new LinearLayoutManager(recyclerLayout.getContext());
        bookmarkLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bookmarkRecyclerView.setLayoutManager(bookmarkLayoutManager);

        FriendListMain.getFriendListDB().updateFriendData(friendData);

        ArrayList<FriendData> tempList = friendDataList;
        ArrayList<FriendData> bookmarkedList = new ArrayList<>();

        Collections.sort(tempList, new Comparator<FriendData>() {
            @Override
            public int compare(FriendData lhs, FriendData rhs) {
                return (lhs.getName().compareToIgnoreCase(rhs.getName()));
            }
        });

        for(int i = 0; i < tempList.size(); i++)
            if(tempList.get(i).getBookMark() == 1)
                bookmarkedList.add(tempList.get(i));
            else if(i == tempList.size() - 1)
                for(int j = 0; j < tempList.size(); j++)
                    if(tempList.get(j).getBookMark() == 0)
                        bookmarkedList.add(tempList.get(j));

        FriendListAdapter friendListAdapter = new FriendListAdapter(bookmarkedList, recyclerLayout.getContext(), recyclerLayout, false);
        bookmarkRecyclerView.setAdapter(friendListAdapter);
        friendListAdapter.notifyDataSetChanged();
        FriendListMain.getFriendListDB().close();
    }

    private void bookmarkFunc(FriendData friendData){

        FriendListMain.getFriendListDB().updateFriendData(friendData);
        FriendListMain.getFriendListDB().close();
    }
}
