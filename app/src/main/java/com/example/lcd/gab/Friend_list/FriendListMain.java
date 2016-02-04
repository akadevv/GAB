package com.example.lcd.gab.Friend_list;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lcd.gab.FriendListDBManager;
import com.example.lcd.gab.InitialSoundSearcher;
import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-01-12.
 */
public class FriendListMain extends android.support.v4.app.Fragment{

    private static FriendListDBManager db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final ArrayList<FriendData> friendDataList;
        final RecyclerView recyclerView;
        android.widget.SearchView searchView;
        final RelativeLayout recyclerLayout;

        recyclerLayout = (RelativeLayout) inflater.inflate(R.layout.friend_list_main, container, false);

        recyclerView = (RecyclerView)recyclerLayout.findViewById(R.id.friend_recycler_view);
        searchView = (android.widget.SearchView) recyclerLayout.findViewById(R.id.friend_search_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerLayout.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        friendDataList = getPhoneNumList(recyclerLayout);

        db = new FriendListDBManager(recyclerLayout.getContext());
        for(int i = 0; i < friendDataList.size(); i++){
            db.addFriendData(friendDataList.get(i));
        }

        FriendListAdapter friendListAdapter = new FriendListAdapter(friendDataList, recyclerLayout.getContext());
        recyclerView.setAdapter(friendListAdapter);


        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                query = query.toLowerCase();
                final ArrayList<FriendData> filteredList = new ArrayList<>();

                for (int i = 0; i < friendDataList.size(); i++) {
                    final String name = friendDataList.get(i).getName().toLowerCase();
                    final String phoneNum = friendDataList.get(i).getPhoneNum();
                    if (name.contains(query)) {
                        filteredList.add(friendDataList.get(i));
                    } else if (phoneNum.contains(query)) {
                        filteredList.add(friendDataList.get(i));
                    } else if (InitialSoundSearcher.patternMatching(name, query)) {
                        filteredList.add(friendDataList.get(i));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerLayout.getContext()));
                FriendListAdapter friendListAdapter = new FriendListAdapter(filteredList, recyclerLayout.getContext());
                recyclerView.setAdapter(friendListAdapter);
                friendListAdapter.notifyDataSetChanged();
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return recyclerLayout;
    }


    public ArrayList<FriendData> getPhoneNumList(RelativeLayout recyclerLayout) {
        ArrayList<FriendData> list = new ArrayList<>();
        FriendData friendData;

        Cursor cursor = null;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String phoneName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String[] ad = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        cursor = recyclerLayout.getContext().getContentResolver().query(uri, ad, null, null, phoneName);

        if(cursor.moveToFirst()) {
            while (cursor.moveToNext()) {

                if (cursor.getString(1) != null) {
                    if (list.size()==0) {
                        friendData = new FriendData();
                        friendData.setName(cursor.getString(0));
                        friendData.setPhoneNum(cursor.getString(1).replaceAll("-", ""));
                        list.add(friendData);
                    } else {
                        if (!list.get(list.size() - 1).getPhoneNum().equals(cursor.getString(1))) {
                            friendData = new FriendData();
                            friendData.setName(cursor.getString(0));
                            friendData.setPhoneNum(cursor.getString(1).replaceAll("-", ""));
                            list.add(friendData);
                        }
                    }
                }
            }
        }
        cursor.close();
        cursor = null;

        return list;
    }

    public static FriendListDBManager getFriendListDB(){
        return db;
    }
}
