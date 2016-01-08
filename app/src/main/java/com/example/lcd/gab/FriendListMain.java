package com.example.lcd.gab;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-01-07.
 */
public class FriendListMain extends Activity{

    ArrayList<FriendData> friendDataList;
    ArrayList<FriendData> list = new ArrayList<>();
    FriendData friendData;
    RecyclerView recyclerView;
    FriendListAdapter friendListAdapter;
    android.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_main);

        recyclerView = (RecyclerView)findViewById(R.id.friend_recycler_view);
        searchView = (android.widget.SearchView) findViewById(R.id.friend_search_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        getPhoneNumList();

        recyclerView.setAdapter(new FriendListAdapter(friendDataList));

        searchView.setOnQueryTextListener(listener);
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
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(FriendListMain.this));
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
        cursor = getContentResolver().query(uri, ad, null, null, phoneName);

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
