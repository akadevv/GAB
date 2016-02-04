package com.example.lcd.gab.ReceivableList;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.PayRoom.DRoomPartyInfo;
import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.example.lcd.gab.R;
import com.example.lcd.gab.RoomList.RoomListMain;

import java.util.List;

/**
 * Created by LCD on 2016-01-28.
 */
public class ReceivableListMain extends android.support.v4.app.Fragment{

    private LinearLayout recyclerLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        recyclerLayout = (LinearLayout) inflater.inflate(R.layout.receivable_list_main, container, false);
        recyclerView = (RecyclerView) recyclerLayout.findViewById(R.id.receivable_list_recycler_view);
        searchView = (SearchView) recyclerLayout.findViewById(R.id.receivable_list_search_view);

        TextView totalText = (TextView) recyclerLayout.findViewById(R.id.receivable_list_total_cost);
        TextView unFinishedText = (TextView) recyclerLayout.findViewById(R.id.receivable_list_number_of_unfinished_people);

        int total = 0;
        int unFinishedCount = 0;
        List<DRoom_FullInfo> roomListDatas = RoomListMain.getRoomListDatas();

        for(int i = 0; i < roomListDatas.size(); i++){
            DRoom_FullInfo roomListData = roomListDatas.get(i);
            List<DRoomPartyInfo> partyInfos = roomListData.getDRoomPartyList();

            for(int j = 0; j < partyInfos.size(); j++){
                if(!MainActivity.getMasterInfo().getMasterPhoneNum().equals(partyInfos.get(j).getPartyPhonenum())){
                    Log.d("이창대", "zzzzzzzzz" + partyInfos.get(j).getParty_name());
                    Log.d("이창대", "zzzzzzzzz" + Integer.toString(partyInfos.get(j).getPartyMoney()));
                    total = total + partyInfos.get(j).getPartyMoney();
                    unFinishedCount++;
                }
            }
        }

        Log.d("이창대", "zzzzzzzzz" + Integer.toString(total));
        totalText.setText(Integer.toString(total));
        unFinishedText.setText(Integer.toString(unFinishedCount));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerLayout.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        ReceivableListAdapter receivableListAdapter = new ReceivableListAdapter(roomListDatas, recyclerLayout.getContext());
        recyclerView.setAdapter(receivableListAdapter);

        return recyclerLayout;
    }
}
