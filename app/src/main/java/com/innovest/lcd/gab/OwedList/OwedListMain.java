package com.innovest.lcd.gab.OwedList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.innovest.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.innovest.lcd.gab.InitialSoundSearcher;
import com.innovest.lcd.gab.PayRoom.DRoomPartyInfo;
import com.innovest.lcd.gab.PayRoom.DRoom_FullInfo;
import com.innovest.lcd.gab.R;
import com.innovest.lcd.gab.RoomList.RoomListMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCD on 2016-02-02.
 */
public class OwedListMain extends android.support.v4.app.Fragment{

    private LinearLayout recyclerLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<DRoomPartyInfo> roomPartyInfos = RoomListMain.getOwedListDatas();
    private List<DRoom_FullInfo> roomListDatas = RoomListMain.getRoomListDatas();
    private OwedListAdapter owedListAdapter;
    private Context mycontext;
    private String log = "jjunest";

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mycontext = inflater.getContext();

        mycontext = inflater.getContext();
        recyclerLayout = (LinearLayout) inflater.inflate(R.layout.owed_list_main, container, false);
        recyclerView = (RecyclerView) recyclerLayout.findViewById(R.id.owed_list_recycler_view);
        searchView = (SearchView) recyclerLayout.findViewById(R.id.owed_list_search_view);

        TextView totalText = (TextView) recyclerLayout.findViewById(R.id.owed_list_total_cost);
        TextView unFinishedText = (TextView) recyclerLayout.findViewById(R.id.owed_list_number_of_unfinished_people);

        int total = 0;
        int unFinishedCount = 0;

        for(int i = 0 ; i < roomPartyInfos.size(); i++){
            total = total + roomPartyInfos.get(i).getPartyMoney();
            unFinishedCount++;
        }

        totalText.addTextChangedListener(new MoneyUnitListener_Text(totalText));
        totalText.setText(Integer.toString(total));
        unFinishedText.setText(Integer.toString(unFinishedCount));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerLayout.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        owedListAdapter = new OwedListAdapter(roomPartyInfos, roomListDatas, recyclerLayout.getContext());
        recyclerView.setAdapter(owedListAdapter);

        searchView.setOnQueryTextListener(listener);
        return recyclerLayout;
    }

    android.widget.SearchView.OnQueryTextListener listener = new android.widget.SearchView.OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String query){
            query = query.toLowerCase();
            final ArrayList<DRoomPartyInfo> filteredList = new ArrayList<>();

            for(int i=0; i < roomPartyInfos.size(); i++){
                final String name = roomPartyInfos.get(i).getParty_name().toLowerCase();
                final String phoneNum = roomPartyInfos.get(i).getPartyPhonenum().replaceAll("-","");

                if(name.contains(query)){
                    filteredList.add(roomPartyInfos.get(i));
                }
                else if(phoneNum.contains(query)){
                    filteredList.add(roomPartyInfos.get(i));
                }
                else if(InitialSoundSearcher.patternMatching(name, query)){
                    filteredList.add(roomPartyInfos.get(i));
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerLayout.getContext()));
            owedListAdapter = new OwedListAdapter(filteredList, roomListDatas, recyclerLayout.getContext());
            recyclerView.setAdapter(owedListAdapter);
            owedListAdapter.notifyDataSetChanged();
            return true;
        }
        public boolean onQueryTextSubmit(String query){
            return false;
        }
    };
}
