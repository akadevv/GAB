package com.example.lcd.gab.OwedList;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.PayRoom.DRoomPartyInfo;
import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.example.lcd.gab.R;
import com.example.lcd.gab.RoomList.RoomListMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCD on 2016-02-02.
 */
public class OwedListMain extends android.support.v4.app.Fragment{

    private LinearLayout recyclerLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<DRoomPartyInfo> roomPartyInfos;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        recyclerLayout = (LinearLayout) inflater.inflate(R.layout.owed_list_main, container, false);
        recyclerView = (RecyclerView) recyclerLayout.findViewById(R.id.owed_list_recycler_view);
        searchView = (SearchView) recyclerLayout.findViewById(R.id.owed_list_search_view);

        TextView totalText = (TextView) recyclerLayout.findViewById(R.id.owed_list_total_cost);
        TextView unFinishedText = (TextView) recyclerLayout.findViewById(R.id.owed_list_number_of_unfinished_people);
        roomPartyInfos = new ArrayList<>();

        int total = 0;
        int unFinishedCount = 0;
        List<DRoom_FullInfo> roomListDatas = RoomListMain.getRoomListDatas();

        for(int i = 0; i < roomListDatas.size(); i++){
            DRoom_FullInfo roomListData = roomListDatas.get(i);
            List<DRoomPartyInfo> partyInfos = roomListData.getDRoomPartyList();
            DRoomPartyInfo partyInfo = new DRoomPartyInfo();

            String roomMasterPhone = roomListData.getMasterPhoneNum();

            if(!MainActivity.getMasterInfo().getUserPhoneNum().equals(roomMasterPhone)){

                for(int j = 0; j < partyInfos.size(); j++){
                    if(!MainActivity.getMasterInfo().getUserPhoneNum().equals(partyInfos.get(j).getPartyPhonenum())){
                        partyInfo.setPartyPhonenum(partyInfos.get(i).getPartyPhonenum());
                        partyInfo.setRoomRcdNum(partyInfos.get(i).getRoomRcdNum());
                        partyInfo.setParty_name(partyInfos.get(i).getParty_name());
                        partyInfo.setPartyMoney(partyInfos.get(i).getPartyMoney());
                        partyInfo.setParty_finished(partyInfos.get(i).getParty_finished());

                        roomPartyInfos.add(partyInfo);

                        total = total + partyInfos.get(j).getPartyMoney();
                        unFinishedCount++;
                    }
                }
            }
        }

        totalText.addTextChangedListener(new MoneyUnitListener_Text(totalText));
        totalText.setText(Integer.toString(total));
        unFinishedText.setText(Integer.toString(unFinishedCount));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerLayout.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        OwedListAdapter owedListAdapter = new OwedListAdapter(roomListDatas, recyclerLayout.getContext());
        recyclerView.setAdapter(owedListAdapter);

        return recyclerLayout;
    }
}
