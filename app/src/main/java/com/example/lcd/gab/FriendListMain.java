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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.friend_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getPhoneNumList();
        recyclerView.setAdapter(new FriendListAdapter(friendDataList));
    }

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
