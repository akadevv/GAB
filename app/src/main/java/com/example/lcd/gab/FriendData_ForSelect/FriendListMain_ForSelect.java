package com.example.lcd.gab.FriendData_ForSelect;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.lcd.gab.FriendData_ForSelect.FriendListAdapter_ForSelect;
import com.example.lcd.gab.Friend_list.FriendData;
import com.example.lcd.gab.Friend_list.InitialSoundSearcher;
import com.example.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-01-17.
 */
public class FriendListMain_ForSelect extends Activity{
    public static String log = "jjunest";
    private ArrayList<FriendData> friendDataList;
    private ArrayList<FriendData> list = new ArrayList<>();
    private FriendData friendData;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private FriendListAdapter_ForSelect friendListAdapterForSelect;
    private android.widget.SearchView searchView;
    private RelativeLayout recyclerLayout;
    Context ListContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(log, "Friendist For Select Oncreate()1");
        setContentView(R.layout.friend_list_main_for_select);
        recyclerLayout = (RelativeLayout)findViewById(R.id.FriendSearchLayout);
        recyclerView = (RecyclerView)findViewById(R.id.friend_recycler_view);
        searchView = (android.widget.SearchView) findViewById(R.id.friend_search_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        Log.d(log, "Friendist For Select Oncreate()2");
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        ListContext = this;
        Log.d(log, "Friendist For Select Oncreate()2.2");
        recyclerView.setLayoutManager(mLayoutManager);
        Log.d(log, "Friendist For Select Oncreate()2.3");
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        Log.d(log, "Friendist For Select Oncreate()2.4");
        getPhoneNumList();
        Log.d(log,"friendDataList size :" +friendDataList.size());
        for(int i =0; i<friendDataList.size();i++){
            Log.d(log,"this is friendname :"+ friendDataList.get(i).getName());
            Log.d(log,"this is phonenm :"+ friendDataList.get(i).getPhoneNum());
        }
        Log.d(log, "Friendist For Select Oncreate()3");
        friendListAdapterForSelect = new FriendListAdapter_ForSelect(getApplicationContext(),friendDataList);
        recyclerView.setAdapter(friendListAdapterForSelect);
        searchView.setOnQueryTextListener(listener);
        Log.d(log, "Friendist For Select Oncreate()4");


        //after friend selector BUTTON clicked 친구 선택선택확인 버튼 클릭 후, 선택된 친구리스트 저장 후, 인텐트 다시 보내줌
        Button FriendListSelectButton = (Button)findViewById(R.id.FriendListSelect_BT);
        FriendListSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    android.widget.SearchView.OnQueryTextListener listener = new android.widget.SearchView.OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String query){
            Log.d(log, "Search Text Changed 1");
            query = query.toLowerCase();
            final ArrayList<FriendData> filteredList = new ArrayList<>();
            Log.d(log, "Search Text Changed 2");
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
            Log.d(log, "Search Text Changed 3");
            mLayoutManager = new LinearLayoutManager(ListContext);
            Log.d(log, "Friendist For Select Oncreate()2.2");
            recyclerView.setLayoutManager(mLayoutManager);
            Log.d(log, "Friendist For Select Oncreate()2.3");
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
            Log.d(log, "Friendist For Select Oncreate()2.4");
            friendListAdapterForSelect = new FriendListAdapter_ForSelect(ListContext,filteredList);
            recyclerView.setAdapter(friendListAdapterForSelect);
            friendListAdapterForSelect.notifyDataSetChanged();




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
        cursor = getApplicationContext().getContentResolver().query(uri, ad, null, null, phoneName);

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

    public ArrayList<FriendData> getFriendDataList(){
        return friendDataList;
    }

}
