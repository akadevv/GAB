package com.example.lcd.gab.RoomList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lcd.gab.InitialSoundSearcher;
import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.PayRoom.DRoomItemInfo;
import com.example.lcd.gab.PayRoom.DRoomPartyInfo;
import com.example.lcd.gab.PayRoom.DRoom_FullInfo;
import com.example.lcd.gab.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCD on 2016-01-18.
 */
public class RoomListMain extends Fragment{

    String log = "이창대";
    private String masterName = MainActivity.getMasterInfo().getMasterID(); // 마스터 핸드폰 번호 받기
    private RelativeLayout recyclerLayout; // 방 목록 만들 recyclerview
    private RecyclerView recyclerView;
    private static ArrayList<DRoom_FullInfo> roomListDatas = new ArrayList<>();
    private android.widget.SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerLayout = (RelativeLayout) inflater.inflate(R.layout.room_list_main, container, false);
        recyclerView = (RecyclerView) recyclerLayout.findViewById(R.id.room_list_recycler_view);
        searchView = (android.widget.SearchView) recyclerLayout.findViewById(R.id.room_list_search_view);

        roomListDatas.clear();
        SendPost sendPost = new SendPost();
        sendPost.execute();

        searchView.setOnQueryTextListener(listener);

        return recyclerLayout;
    }

    //방목록 검색 리스너
    android.widget.SearchView.OnQueryTextListener listener = new android.widget.SearchView.OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String query){
            query = query.toLowerCase();
            final ArrayList<DRoom_FullInfo> filteredList = new ArrayList<>();

            for(int i=0; i< roomListDatas.size(); i++){
                final DRoom_FullInfo tempRoomInfos = roomListDatas.get(i);

                final String name = tempRoomInfos.getDRoomName().toLowerCase();
                final String date = Integer.toString(tempRoomInfos.getDRoomDate());

                String [] phoneNum = new String[tempRoomInfos.getDRoomPartyList().size()];
                String [] memberName = new String[tempRoomInfos.getDRoomPartyList().size()];

                for(int j = 0; j < tempRoomInfos.getDRoomPartyList().size(); j++){
                    phoneNum[j] = tempRoomInfos.getDRoomPartyList().get(j).getPartyPhonenum().replaceAll("-", "");
                    memberName[j] = tempRoomInfos.getDRoomPartyList().get(j).getParty_name();
                }
                if(name.contains(query)){
                    filteredList.add(roomListDatas.get(i));
                }
                else if(date.contains(query)){
                    filteredList.add(roomListDatas.get(i));
                }
                else if(InitialSoundSearcher.patternMatching(name, query)){
                    filteredList.add(roomListDatas.get(i));
                }
                else{
                    for(int j = 0; j < tempRoomInfos.getDRoomPartyList().size(); j++){
                        if(phoneNum[j].contains(query))
                            filteredList.add(roomListDatas.get(i));
                        else if(memberName[j].contains(query))
                            filteredList.add(roomListDatas.get(i));
                        else if(InitialSoundSearcher.patternMatching(memberName[j], query))
                            filteredList.add(roomListDatas.get(i));
                    }
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerLayout.getContext()));
            RoomListAdapter roomListAdapter = new RoomListAdapter(filteredList, recyclerLayout.getContext());
            recyclerView.setAdapter(roomListAdapter);
            roomListAdapter.notifyDataSetChanged();
            return true;
        }
        public boolean onQueryTextSubmit(String query){
            return false;
        }
    };

    //파싱 AsyncTask
    private class SendPost extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            String content = getRoomInfoFromDB(masterName, "http://jjunest.cafe24.com/DB/getRoomInfo.php");
            //마스터 아이디 보내고 DB로 부터 정보 가져오는 함수
            ArrayList<DRoom_FullInfo> roomPartyInfos = new ArrayList<>();
            ArrayList<DRoom_FullInfo> roomInfos = new ArrayList<>();
            ArrayList<DRoom_FullInfo> roomItemInfos = new ArrayList<>();

            try {
                JSONObject root = new JSONObject(content);

                JSONArray resultsJa = root.getJSONArray("results");

                /*------------------------------------------------------------------------------------*/
//                dutch_partyinfo_cur json type parsing

                JSONObject partyInfoJo = new JSONObject(resultsJa.getString(0));
                JSONArray partyInfoJa = partyInfoJo.getJSONArray("party_info_cur");

                for (int i = 0; i < partyInfoJa.length(); i++) {
                    JSONArray ja = partyInfoJa.getJSONArray(i);
                    int totalCost=0;
                    ArrayList<DRoomPartyInfo> memberInfoList = new ArrayList<>();

                    for (int j = 0; j < ja.length(); j++) {

                        JSONObject jo = ja.getJSONObject(j);

                        DRoomPartyInfo memberInfo = new DRoomPartyInfo();
                        String rcdNum = jo.getString("room_rcdnum");
                        String memberName = jo.getString("member_name");
                        String memberPhoneNum = jo.getString("member_phonenum");
                        String costS = jo.getString("cost");
                        String finishS = jo.getString("finished or not");

                        int roomRcdNum = Integer.parseInt(rcdNum);
                        int cost = Integer.parseInt(costS);
                        int finish = Integer.parseInt(finishS);

                        totalCost += cost;

                        memberInfo.setRoomRcdNum(roomRcdNum);
                        memberInfo.setParty_name(memberName);
                        memberInfo.setPartyPhonenum(memberPhoneNum);
                        memberInfo.setPartyMoney(cost);
                        memberInfo.setParty_finished(finish);

                          memberInfoList.add(memberInfo);
                      }

                      if (!roomPartyInfos.isEmpty()) {
                        for (int k = 0; k < roomPartyInfos.size(); k++) {

                            if (roomPartyInfos.get(k).getDRoomRcdNum() == memberInfoList.get(0).getRoomRcdNum()) {
                                roomPartyInfos.get(k).setDRoomRcdNum(memberInfoList.get(0).getRoomRcdNum());
                                roomPartyInfos.get(k).setDRoomPartyList(memberInfoList);
                                break;
                            }
                        }
                        DRoom_FullInfo tempData = new DRoom_FullInfo(memberInfoList.get(0).getRoomRcdNum(), totalCost, memberInfoList);
                        roomPartyInfos.add(tempData);
                    } else {
                        DRoom_FullInfo tempData = new DRoom_FullInfo(memberInfoList.get(0).getRoomRcdNum(), totalCost, memberInfoList);
                        roomPartyInfos.add(tempData);
                    }
                }

                /*-----------------------------------------------------------------------------------------*/
                //dutch_roominfo_cur json type parsing

                JSONObject roomInfoJo = new JSONObject(resultsJa.getString(1));

                JSONArray roomInfoJa = roomInfoJo.getJSONArray("room_info_cur");

                for (int i = 0; i < roomInfoJa.length(); i++) {
                    JSONArray ja = roomInfoJa.getJSONArray(i);


                    for (int j = 0; j < ja.length(); j++) {
                        JSONObject jo = ja.getJSONObject(j);

                        String rcdNum = jo.getString("rcdno");
                        String roomName = jo.getString("room_name");
                        String makingDate = jo.getString("making_date");
                        String finishS = jo.getString("room_finished");
                        String masterName = jo.getString("master_name");
                        String masterPhoneNum = jo.getString("master_phone");

                        String tempDate = makingDate.substring(0, 4);
                        tempDate += makingDate.substring(5, 7);
                        tempDate += makingDate.substring(8, 10);

                        int roomRcdNum = Integer.parseInt(rcdNum);
                        int roomDate = Integer.parseInt(tempDate);
                        int roomFinish = Integer.parseInt(finishS);


                        if (!roomInfos.isEmpty()) {
                            for (int k = 0; k < roomInfos.size(); k++) {

                                if (roomInfos.get(k).getDRoomRcdNum() == roomRcdNum) {
                                    roomInfos.get(k).setDRoomRcdNum(roomRcdNum);
                                    roomInfos.get(k).setDRoomName(roomName);
                                    roomInfos.get(k).setDRoomDate(roomDate);
                                    roomInfos.get(k).setmasterName(masterName);
                                    roomInfos.get(k).setDRoomFinished(roomFinish);
                                    roomInfos.get(k).setMasterPhoneNum(masterPhoneNum);
                                    break;
                                }
                            }
                            roomInfos.add(new DRoom_FullInfo(roomRcdNum,roomDate,roomFinish,masterName, masterPhoneNum, roomName));
                        }else{
                            roomInfos.add(new DRoom_FullInfo(roomRcdNum,roomDate,roomFinish,masterName, masterPhoneNum, roomName));
                        }
                    }

                }

                /*----------------------------------------------------------------------------------------*/
                //dutch_iteminfo_cur json type parsing

                JSONObject itemInfoJo = new JSONObject(resultsJa.getString(2));

                JSONArray itemInfoJa = itemInfoJo.getJSONArray("item_info_cur");

                for (int i = 0; i < itemInfoJa.length(); i++) {
                    JSONArray ja = itemInfoJa.getJSONArray(i);
                    int totalPrice=0;
                    ArrayList<DRoomItemInfo> itemInfoList = new ArrayList<>();

                    for (int j = 0; j < ja.length(); j++) {
                        JSONObject jo = ja.getJSONObject(j);

                        DRoomItemInfo itemInfo = new DRoomItemInfo();

                        String rcdNum = jo.getString("room_rcdnum");
                        String item = jo.getString("item");
                        String priceS = jo.getString("price");
                        String numberS = jo.getString("the number of item");

                        int number = Integer.parseInt(numberS);
                        int roomRcdNum = Integer.parseInt(rcdNum);
                        int price = Integer.parseInt(priceS);
                        totalPrice += price;

                        itemInfo.setDRoomitem_number(number);
                        itemInfo.setDRoomitem_name(item);
                        itemInfo.setDRoomitem_price(price);
                        itemInfo.setDRoomitem_roomRcdNum(roomRcdNum);

                        itemInfoList.add(itemInfo);


                    }

                    if (!roomItemInfos.isEmpty()) {
                        for (int k = 0; k < roomItemInfos.size(); k++) {
                            if (roomItemInfos.get(k).getDRoomRcdNum() == itemInfoList.get(0).getDRoomitem_roomRcdNum()) {
                                roomItemInfos.get(k).setDRoomRcdNum(itemInfoList.get(0).getDRoomitem_roomRcdNum());
                                roomItemInfos.get(k).setDRoomItemList(itemInfoList);
                                break;
                            }
                        }
                        DRoom_FullInfo tempData = new DRoom_FullInfo(itemInfoList.get(0).getDRoomitem_roomRcdNum(),totalPrice, itemInfoList);
                        roomItemInfos.add(tempData);
                    }else{
                        DRoom_FullInfo tempData = new DRoom_FullInfo(itemInfoList.get(0).getDRoomitem_roomRcdNum(), totalPrice, itemInfoList);
                        roomItemInfos.add(tempData);
                    }
                }
            }catch(Exception e){

            }

            for(int i = 0; i<roomPartyInfos.size(); i++)
                for(int j = 0; j < roomInfos.size(); j++ )
                    for(int k = 0; k < roomItemInfos.size(); k++)
                        if (roomPartyInfos.get(i).getDRoomRcdNum() == roomInfos.get(j).getDRoomRcdNum() && roomPartyInfos.get(i).getDRoomRcdNum() == roomItemInfos.get(k).getDRoomRcdNum()) {
                            roomListDatas.add(new DRoom_FullInfo(roomPartyInfos.get(i).getDRoomRcdNum(), roomInfos.get(j).getmasterName(), roomInfos.get(j).getMasterPhoneNum(), roomInfos.get(j).getDRoomName(), roomInfos.get(j).getDRoomDate(), roomItemInfos.get(k).getDRoomItemList(), roomItemInfos.get(k).getTotalPrice(), roomPartyInfos.get(i).getDRoomPartyList()));
                            break;
                        }

            return null;
        }

        //리사이클러 뷰 붙이기
        @Override
        protected void onPostExecute(Void unused) {

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerLayout.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

            recyclerView.setLayoutManager(linearLayoutManager);
            RoomListAdapter roomListAdapter = new RoomListAdapter(roomListDatas, recyclerLayout.getContext());
            recyclerView.setAdapter(roomListAdapter);

            super.onPostExecute(null);
        }

        private String getRoomInfoFromDB(String masterId, String phpUrl) {
            StringBuilder sb = new StringBuilder();

            try {
                String data = URLEncoder.encode("masterId", "UTF-8") + "=" + URLEncoder.encode(masterId, "UTF-8");

                URL urlC = new URL(phpUrl);

                URLConnection conn = urlC.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line);

                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }

    public static ArrayList<DRoom_FullInfo> getRoomListDatas(){
        return roomListDatas;
    }

    public static ArrayList<DRoomPartyInfo> getOwedListDatas(){
        ArrayList<DRoomPartyInfo> roomPartyInfos = new ArrayList<>();

        for(int i = 0; i < roomListDatas.size(); i++){
            DRoom_FullInfo roomListData = roomListDatas.get(i);
            List<DRoomPartyInfo> partyInfos = roomListData.getDRoomPartyList();
            DRoomPartyInfo partyInfo = new DRoomPartyInfo();

            String roomMasterPhone = roomListData.getMasterPhoneNum();

            if(!MainActivity.getMasterInfo().getMasterPhoneNum().equals(roomMasterPhone)){

                for(int j = 0; j < partyInfos.size(); j++){
                    if(!MainActivity.getMasterInfo().getMasterPhoneNum().equals(partyInfos.get(j).getPartyPhonenum())){
                        partyInfo.setPartyPhonenum(partyInfos.get(i).getPartyPhonenum());
                        partyInfo.setRoomRcdNum(partyInfos.get(i).getRoomRcdNum());
                        partyInfo.setParty_name(partyInfos.get(i).getParty_name());
                        partyInfo.setPartyMoney(partyInfos.get(i).getPartyMoney());
                        partyInfo.setParty_finished(partyInfos.get(i).getParty_finished());

                        roomPartyInfos.add(partyInfo);
                    }
                }
            }
        }
        return roomPartyInfos;
    }

    public static ArrayList<DRoomPartyInfo> getReceivableListDatas(){
        ArrayList<DRoomPartyInfo> roomPartyInfos = new ArrayList<>();

        for(int i = 0; i < roomListDatas.size(); i++){
            DRoom_FullInfo roomListData = roomListDatas.get(i);
            List<DRoomPartyInfo> partyInfos = roomListData.getDRoomPartyList();
            DRoomPartyInfo partyInfo = new DRoomPartyInfo();

            String roomMasterPhone = roomListData.getMasterPhoneNum();

            if(MainActivity.getMasterInfo().getMasterPhoneNum().equals(roomMasterPhone)){
                for(int j = 0; j < partyInfos.size(); j++){
                    if(!MainActivity.getMasterInfo().getMasterPhoneNum().equals(partyInfos.get(j).getPartyPhonenum())){
                        partyInfo.setPartyPhonenum(partyInfos.get(i).getPartyPhonenum());
                        partyInfo.setRoomRcdNum(partyInfos.get(i).getRoomRcdNum());
                        partyInfo.setParty_name(partyInfos.get(i).getParty_name());
                        partyInfo.setPartyMoney(partyInfos.get(i).getPartyMoney());
                        partyInfo.setParty_finished(partyInfos.get(i).getParty_finished());

                        roomPartyInfos.add(partyInfo);
                    }
                }
            }
        }
        return roomPartyInfos;
    }
}
