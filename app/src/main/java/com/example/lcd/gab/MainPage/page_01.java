package com.example.lcd.gab.MainPage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.lcd.gab.Friend_list.FriendData;
import com.example.lcd.gab.Friend_list.FriendListAdapter;
import com.example.lcd.gab.Friend_list.InitialSoundSearcher;
import com.example.lcd.gab.Pay_room.RoomMain;
import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-01-12.
 */
public class page_01 extends android.support.v4.app.Fragment{
    private ArrayList<FriendData> friendDataList;
    private ArrayList<FriendData> list = new ArrayList<>();
    private FriendData friendData;
    private RecyclerView recyclerView;
    private FriendListAdapter friendListAdapter;
    private android.widget.SearchView searchView;
    private Button button;
    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.friend_list_main, container, false);

        recyclerView = (RecyclerView)relativeLayout.findViewById(R.id.friend_recycler_view);
        searchView = (android.widget.SearchView) relativeLayout.findViewById(R.id.friend_search_view);
        button = (Button) relativeLayout.findViewById(R.id.roomButton);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(relativeLayout.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        getPhoneNumList();

        friendListAdapter = new FriendListAdapter((friendDataList));
        recyclerView.setAdapter(friendListAdapter);

        searchView.setOnQueryTextListener(listener);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(relativeLayout.getContext(), RoomMain.class);
                startActivity(intent);
            }
        });

        return relativeLayout;
    }

    android.widget.SearchView.OnQueryTextListener listener = new android.widget.SearchView.OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String query){
            query = query.toLowerCase();
            final ArrayList<FriendData> filteredList = new ArrayList<>();

            for(int i=0; i< friendDataList.size(); i++){
                final String name = friendDataList.get(i).getName().toLowerCase();
                final String phoneNum = friendDataList.get(i).getPhoneNum();
                if(name.contains(query)){
                    filteredList.add(friendDataList.get(i));
                }
                else if(phoneNum.contains(query)){
                    filteredList.add(friendDataList.get(i));
                }
                else if(InitialSoundSearcher.patternMatching(name, query)){
                    filteredList.add(friendDataList.get(i));
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(relativeLayout.getContext()));
            friendListAdapter = new FriendListAdapter(filteredList);
            recyclerView.setAdapter(friendListAdapter);
            friendListAdapter.notifyDataSetChanged();
            return true;
        }
        public boolean onQueryTextSubmit(String query){
            return false;
        }
    };

    public void getPhoneNumList() {
        Cursor cursor = null;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String phoneName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String[] ad = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        cursor = relativeLayout.getContext().getContentResolver().query(uri, ad, null, null, phoneName);

        if(cursor.moveToFirst()) {
            while (cursor.moveToNext()) {

                if (cursor.getString(1) != null) {
                    if (list.size()==0) {
                        friendData = new FriendData();
                        friendData.setName(cursor.getString(0));
                        friendData.setPhoneNum(cursor.getString(1));
                        list.add(friendData);
                    } else {
                        if (!list.get(list.size() - 1).getPhoneNum().equals(cursor.getString(1))) {
                            friendData = new FriendData();
                            friendData.setName(cursor.getString(0));
                            friendData.setPhoneNum(cursor.getString(1));
                            list.add(friendData);
                        }
                    }
                }
            }friendDataList = list;
        }
        cursor.close();
        cursor = null;
    }
}
