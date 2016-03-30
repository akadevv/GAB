package com.innovest.lcd.gab.FriendData_ForSelect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.innovest.lcd.gab.InitialSoundSearcher;
import com.innovest.lcd.gab.PayRoom.DRoomPartyInfo;
import com.innovest.lcd.gab.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-01-17.
 */
public class FriendListMain_ForSelect extends Activity{
    private String logclass = " |in FriendListMain_ForSelect.java| ";
    public String log = "jjunest";
    private ArrayList<FriendData_ForSelect> friendDataList;
    private ArrayList<FriendData_ForSelect> list = new ArrayList<FriendData_ForSelect>();
    private FriendData_ForSelect friendData;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private FriendListAdapter_ForSelect friendListAdapterForSelect;
    private android.widget.SearchView searchView;
    private RelativeLayout recyclerLayout;
    private ArrayList<FriendData_ForSelect> filteredDataList = new ArrayList<FriendData_ForSelect>();
    private ArrayList<FriendData_ForSelect> selectedDataAllList = new ArrayList<FriendData_ForSelect>();
    private ArrayList<DRoomPartyInfo> fromPayRoomPartyList = new ArrayList<>();
    Context ListContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(log, "Friendist For Select Oncreate()1 in FriendListMain_ForSelect.java");
        //시작할때 filteredDataList 클리어해준다.
        Log.d(log, "filteredDataList size = " + filteredDataList.size() + logclass);
        Log.d(log, "filteredDataList size after = " + filteredDataList.size()+logclass);

        setContentView(R.layout.friend_list_main_for_select);
        recyclerLayout = (RelativeLayout)findViewById(R.id.FriendSearchLayout_ForSelect);
        recyclerView = (RecyclerView)findViewById(R.id.friend_recycler_view_ForSelect);
        searchView = (android.widget.SearchView) findViewById(R.id.friend_search_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        ListContext = this;

        recyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager.setOrientation(RecyclerView.VERTICAL);

        //처음 만들 떄는 모든 PhonenumList를 friendDataList에 집어 넣는다.
        getPhoneNumList();


        //(미완성) Intent 로 온 미리 선택된 party List를 검색해서, 내가 가지고 있는 friendDataList의 phoneNum Num과 같으면,
        // 그 부분을 selected로 바꾸어준다.
        Intent intent = getIntent();
        if(intent!=null) {
            Bundle bundle = intent.getExtras();
            fromPayRoomPartyList = (ArrayList<DRoomPartyInfo>) bundle.getSerializable("selectedParty");
            for (int i = 0; i < fromPayRoomPartyList.size(); i++) {
                for (int j = 0; j < friendDataList.size(); j++) {
                    //만약 fromPayRoomList에서 온 phoneNum 과 friendData 에 있는 phoneNu을 검색해서, 매칭하는 것은 selected를 true로 바꾼다.
                    if (fromPayRoomPartyList.get(i).getPartyPhonenum().equals(friendDataList.get(j).getPhoneNum().replaceAll("-",""))) {
                        friendDataList.get(j).setMoney(fromPayRoomPartyList.get(i).getPartyMoney());
                        friendDataList.get(j).setSelected(true);
                    }

                }

            }
        }

        friendListAdapterForSelect = new FriendListAdapter_ForSelect(getApplicationContext(),friendDataList);
        recyclerView.setAdapter(friendListAdapterForSelect);
        searchView.setOnQueryTextListener(listener);
        Log.d(log, "Friendist For Select Oncreate()4 in FriendListMain_ForSelect.java");


        //after friend selector BUTTON clicked 친구 선택선택확인 버튼 클릭 후, 선택된 친구리스트 저장 후, 인텐트 다시 보내줌
        Button FriendListSelectButton = (Button)findViewById(R.id.FriendListSelect_BT);
        FriendListSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모든 친구목록을 recycler view에 넣어준 후, recyclerView 에서 선택된 친구들을 검색한다.
                friendListAdapterForSelect = new FriendListAdapter_ForSelect(getApplicationContext(),friendDataList);
                recyclerView.setAdapter(friendListAdapterForSelect);


                    //인텐트에 넣어서 PayRoomMain에 보내주기
                Intent PartySelectResultIntent = new Intent();
                Bundle bundle = new Bundle();
                selectedDataAllList = ((FriendListAdapter_ForSelect)friendListAdapterForSelect).getFilteredDataList();
                    //selected 된 friendData 만을 검색해서 다른 activity에 보내준다.
                int filterCounter = selectedDataAllList.size();
                for (int i=0; i<filterCounter;i++){
                    if(selectedDataAllList.get(i).getSelected()){
                        Log.d(log,"this is selectedDataAllList : name in FriendListSelectionButtonClicked : "+ selectedDataAllList.get(i).getName());
                        filteredDataList.add(selectedDataAllList.get(i));
                    };

                }

                bundle.putSerializable("SelectedList", filteredDataList);
                PartySelectResultIntent.putExtras(bundle);
                setResult(RESULT_OK,PartySelectResultIntent); //응답 보내주기 (to PayRoomMain Activity)
                finish();
//                for(int i =0;i<filteredDataList.size();i++){
//                    Log.d(log,"this is selected :" +filteredDataList.get(i).getName());
//                }
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(log,"filteredDataList size = "+filteredDataList.size()+logclass);

    }

    android.widget.SearchView.OnQueryTextListener listener = new android.widget.SearchView.OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String query){
            selectedDataAllList = ((FriendListAdapter_ForSelect)friendListAdapterForSelect).getFilteredDataList();
            Log.d(log,"This is onQueryText changed");
            int filterCounter = selectedDataAllList.size();
            for (int i=0; i<filterCounter;i++){
                if(selectedDataAllList.get(i).getSelected()){
                    Log.d(log,"this is selectedDataAllList : name : "+ selectedDataAllList.get(i).getName());
                };

            }

            Log.d(log, "Search Text Changed 1in FriendListMain_ForSelect.java");
            query = query.toLowerCase();
            final ArrayList<FriendData_ForSelect> filteredList = new ArrayList<>();
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
            Log.d(log, "Search Text Changed 3 in FriendListMain_ForSelect.java");
            mLayoutManager = new LinearLayoutManager(ListContext);
            recyclerView.setLayoutManager(mLayoutManager);
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
            //여기서 Adapter Select의 getFilteredDaaList가 달라진다.//선택완룔ㄹ 할 시에, 모든
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
                        friendData = new FriendData_ForSelect();
                        friendData.setName(cursor.getString(0));
                        friendData.setPhoneNum(cursor.getString(1));
                        list.add(friendData);
                    } else {
                        if (!list.get(list.size() - 1).getPhoneNum().equals(cursor.getString(1))) {
                            friendData = new FriendData_ForSelect();
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

    public ArrayList<FriendData_ForSelect> getFriendDataList(){
        return friendDataList;
    }

}
