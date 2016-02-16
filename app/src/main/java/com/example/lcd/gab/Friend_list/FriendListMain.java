package com.example.lcd.gab.Friend_list;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.lcd.gab.FriendListDBManager;
import com.example.lcd.gab.InitialSoundSearcher;
import com.example.lcd.gab.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        LinearLayout itemLayout;
        RelativeLayout friendTap;

        recyclerLayout = (RelativeLayout) inflater.inflate(R.layout.friend_list_main, container, false);
        itemLayout = (LinearLayout) inflater.inflate(R.layout.friend_list_item, container, false);

        friendTap = (RelativeLayout) itemLayout.findViewById(R.id.friend_recycler_view_friend_tap);

        recyclerView = (RecyclerView)recyclerLayout.findViewById(R.id.friend_recycler_view);
        searchView = (android.widget.SearchView) recyclerLayout.findViewById(R.id.friend_search_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerLayout.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        friendDataList = getPhoneNumList(recyclerLayout);

        //recyclerLayout.getContext().deleteDatabase("GAB");

        db = new FriendListDBManager(recyclerLayout.getContext());

        ArrayList<FriendData> DBDataList = db.getAllFriendData();
        ArrayList<FriendData> tempList = new ArrayList<>();
        ArrayList<FriendData> bookmarkedList = new ArrayList<>();

        if (db.getAllFriendData().size() == 0)
            for (int i = 0; i < friendDataList.size(); i++) {
                db.addFriendData(friendDataList.get(i));
            }
        else{
            for(int i = 0; i < friendDataList.size(); i++) {
                for (int j = 0; j < DBDataList.size(); j++) {
                    if (friendDataList.get(i).getPhoneNum().equals(DBDataList.get(j).getPhoneNum()) && friendDataList.get(i).getName().equals(DBDataList.get(j).getName())) {
                        tempList.add(DBDataList.get(j));
                        break;
                    } else if (friendDataList.get(i).getPhoneNum().equals(DBDataList.get(j).getPhoneNum()) && !friendDataList.get(i).getName().equals(DBDataList.get(j).getName())) {
                        DBDataList.get(j).setName(friendDataList.get(i).getName());
                        tempList.add(DBDataList.get(j));
                        break;
                    } else if (!friendDataList.get(i).getPhoneNum().equals(DBDataList.get(j).getPhoneNum()) && friendDataList.get(i).getName().equals(DBDataList.get(j).getName())) {
                        DBDataList.get(j).setPhoneNum(friendDataList.get(i).getPhoneNum());
                        tempList.add(DBDataList.get(j));
                        break;
                    } else if (j == DBDataList.size() - 1)
                        tempList.add(friendDataList.get(i));
                }
            }
        }

        Collections.sort(tempList, new Comparator<FriendData>() {
            @Override
            public int compare(FriendData lhs, FriendData rhs) {
                return (lhs.getName().compareToIgnoreCase(rhs.getName()));
            }
        });

        for(int i = 0; i < tempList.size(); i++){
            if(tempList.get(i).getBookMark() == 1)
                bookmarkedList.add(tempList.get(i));
        }

        for(int i = 0; i < tempList.size(); i++){
            if(tempList.get(i).getBookMark() == 0)
                bookmarkedList.add(tempList.get(i));
        }

        db.deleteAll();

        for(int i = 0; i < bookmarkedList.size(); i++){
            db.addFriendData(bookmarkedList.get(i));
        }




        FriendListAdapter friendListAdapter = new FriendListAdapter(db.getAllFriendData(), recyclerLayout.getContext());
        recyclerView.setAdapter(friendListAdapter);
        db.close();

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
        int id = 1;

        if(cursor.moveToFirst()) {

            do{
                if (cursor.getString(1) != null) {
                    if (list.size()==0) {
                        friendData = new FriendData();
                        friendData.setId(id++);
                        friendData.setName(cursor.getString(0));
                        friendData.setPhoneNum(cursor.getString(1).replaceAll("-", ""));
                        Log.d("cdd", friendData.getName());
                        list.add(friendData);
                        friendData = null;
                    } else {
                        if (!list.get(list.size() - 1).getPhoneNum().equals(cursor.getString(1))) {
                            friendData = new FriendData();
                            friendData.setId(id++);
                            friendData.setName(cursor.getString(0));
                            friendData.setPhoneNum(cursor.getString(1).replaceAll("-", ""));
                            list.add(friendData);
                            friendData = null;
                        }
                    }
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        cursor = null;

        return list;
    }

    public static FriendListDBManager getFriendListDB(){
        return db;
    }
}
