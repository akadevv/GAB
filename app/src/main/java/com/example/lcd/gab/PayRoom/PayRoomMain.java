package com.example.lcd.gab.PayRoom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.ACCESS_TO_DB.Insert_DRoom_FullInfo_DB;
import com.example.lcd.gab.FriendData_ForSelect.FriendListMain_ForSelect;
import com.example.lcd.gab.Friend_list.FriendData;
import com.example.lcd.gab.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by LCD on 2016-01-11.
 */
public class PayRoomMain extends Activity {
    String logclass= "| in PayRoomMain.java |";
    Context PayRoomContext;
    Activity PayRoomActivity;
    Dialog calendarDialog;
    private int mYear;
    private int mMonth;
    private String mMonth_String;
    private int mDay;
    private String mDay_String;
    private String TodayDate;
    static final int DATE_DIALOG_ID = 0;
    String log="jjunest";
    String roomname;
    String roomdate;
    int roomdate_int;
    //java.util.List<java.util.Map.Entry<String,Integer>> itemLists= new java.util.ArrayList<>(); //(item 명 + 가격)
    List<DRoomItemInfo> DRoom_itemLists = new ArrayList<DRoomItemInfo>();

    String itemname;
    int itemprice;
    int totalRoomPrice=0;
    String roomtotalprice;
    TextView date_textview;
    //java.util.Map.Entry<String,Integer> pair1=new java.util.AbstractMap.SimpleEntry<>("Not Unique key1",1);
    //java.util.List<java.util.Map.Entry<String,Integer>> partyList = new java.util.ArrayList<>(); //(party phonenum, partymoney , party_finished)
    List<DRoomPartyInfo> DRoom_partyLists = new ArrayList<DRoomPartyInfo>();
    DRoom_FullInfo DRoom_fullinfo;
    DRoomItemInfo newDroomItem;
    int partyphonenum;
    EditText party_phoneNum;
    String party_phoneNum_String;
    DRoomPartyInfo newDroomPartyInfo;
    EditText party_Money;
    int party_Money_int;
    //for DB
    String PHPlink= "http://jjunest.cafe24.com/DB/insert_into_DRoomInfo.php";
    //for Tag
    String ItemTag = "itemTag";
    String masterID;
    String masterPhoneNum;
    Insert_DRoom_FullInfo_DB InsertRoomInfo;
    //for friends list 부를때 코드
    public static final int FriendListREQUEST_CODE = 2001;
    List<FriendData> selectedFriendList = new ArrayList<FriendData>();
    EditText Itemin;
    LinearLayout itemContainer;
    String newitemName_String;
    String newitemPrice_String;
    int newitemPrice_Int;
    LinearLayout PartyListContainer;

    @Override //다시 불러졌을때, 모든 정보들 삭제
    protected void onResume() {
        Log.d(log, "this is onResume() called in PayRoomMain.java");
        super.onResume();
        selectedFriendList.clear();
        DRoom_partyLists.clear();
        roomname="";
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        if(mMonth<9){
            mMonth_String = "0"+String.valueOf(mMonth+1);
        }else{
            mMonth_String=String.valueOf(mMonth);
        }
        if(mDay<10){
            mDay_String = "0"+String.valueOf(mDay);
        }else{
            mDay_String=String.valueOf(mDay);
        }

        TodayDate = String.valueOf(mYear)+mMonth_String+mDay_String;
        roomdate_int=
        roomdate_int=0;
        DRoom_itemLists.clear();
        totalRoomPrice=0;
        DRoom_partyLists.clear();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_room);

        //기존에 선택되있던 친구 목록들 삭제
        selectedFriendList.clear();
        DRoom_partyLists.clear();
        PayRoomContext=this;
        PayRoomActivity = (Activity)this;

        Context thiscontext = this;
        Log.d(log ,"getApplicationContext : "+PayRoomContext);
        Log.d(log,"this context: "+thiscontext);
        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //(미완성) textView 에 클릭시, item row 하나씩 추가
        itemContainer = (LinearLayout)findViewById(R.id.dutchPayItemContainer);
        Itemin = (EditText) findViewById(R.id.newItemName);
        Button ItemAddBT = (Button)findViewById(R.id.newItemAddBT);
        ItemAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.itemrow, null);
                TextView textOut = (TextView)addView.findViewById(R.id.itemNameText);
                textOut.setText(Itemin.getText().toString());
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                //추가된 item layout 에 remove 버튼 눌럿을 시에, 해당 addView를 사라지게 만든다.
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                itemContainer.addView(addView);

            }
        });




        //(미완성) 더치페이 item List에 itemPrice 데이터 입력시, 자동으로 total price를 계산해주는 칸 추가 생성;




        // 더치페이 친구 List 버튼 눌렀을 시에! FriendListMain_ForSelect Activity 를 불러온다
        Button FriendListShowBT = (Button)findViewById(R.id.showFriendListBT);
        FriendListShowBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //친구 목록 보여주는 Recycler View 로 넘어가고 데이터 받아오기
                Log.d(log, "Friends List Button clicked");
                Intent friendList = new Intent(getApplicationContext(), FriendListMain_ForSelect.class);
                startActivityForResult(friendList, FriendListREQUEST_CODE);


            }
        });

        //for date picker 날짜 선택 방
        date_textview = (TextView)findViewById(R.id.roomdate_text);
        if(mMonth<9){
            mMonth_String = "0"+String.valueOf(mMonth+1);
        }else{
            mMonth_String=String.valueOf(mMonth);
        }
        if(mDay<10){
            mDay_String = "0"+String.valueOf(mDay);
        }else{
            mDay_String=String.valueOf(mDay);
        }

        date_textview.setText(String.valueOf(mYear) + mMonth_String + mDay_String);
        //date picker 추가
        Button datepick_button = (Button) findViewById(R.id.room_date_selector);
        datepick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(log,"this is datepickButton Clicked()");
                showDialog(DATE_DIALOG_ID);
            }
        });





        //Room making buttonclicked 방만들기 버튼 눌렀을 시에,모든 정보 합산 후에 DB에 넣어준다.
        Button DroomButton = (Button)findViewById(R.id.DRoom_makingbutton);
        DroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DRoom_itemLists.clear();
                Log.d(log, "onCreate payroommain() :");
                //getroomname
                EditText roomname_edit = (EditText)findViewById(R.id.roomname_edit);
                roomname = roomname_edit.getText().toString();
                Log.d(log, "onCreate payroommain()1.1");
                //get date

                roomdate = date_textview.getText().toString();
                roomdate_int = Integer.parseInt(roomdate);
                Log.d(log, "onCreate payroommain()1.2");
                //get item1's name
                totalRoomPrice=0;

                //아이템 추가된 것 찾고, 아이템 이름 및 아이템 가격 넣기
                itemContainer = (LinearLayout)findViewById(R.id.dutchPayItemContainer);
                int itemCounter = itemContainer.getChildCount();
                Log.d(log,"this is itemCounter : " + itemCounter);
                for(int i =0; i<itemCounter; i++){

                    String getTag = (String)itemContainer.getChildAt(i).getTag();
                    if(getTag==null){Log.d(log,"getTag is null");}
                    else{
                        if(getTag.equals("newItemTAG")){
                           RelativeLayout newItemLayout = (RelativeLayout)itemContainer.getChildAt(i);
                           TextView itemNameView = (TextView) newItemLayout.getChildAt(0);
                            newitemName_String = itemNameView.getText().toString();
                            EditText itemPriceView = (EditText) newItemLayout.getChildAt(1);
                            newitemPrice_String = itemPriceView.getText().toString();

                            Log.d(log, "newitemName_String : "+newitemName_String);
                            Log.d(log, "newitemPrice_String : "+newitemPrice_String);
                            newitemPrice_Int = Integer.parseInt(newitemPrice_String);
                            //item 개수대로 itemList에 넣어주기
                            newDroomItem = new DRoomItemInfo(newitemName_String, newitemPrice_Int);
                            DRoom_itemLists.add(newDroomItem);
                        }

                    }

                }

                //get total price 아이템 총합 구하기 (ITEM의 TOTAL 총합 금액 구하기)// 해당 부분은 textview로 수정이 불가능하게, item 가격이 변동될 시에, total text view가 수정됨
                for(int i =0 ; i< DRoom_itemLists.size();i++){
                    int itemprice = DRoom_itemLists.get(i).DRoomitem_price;
                    totalRoomPrice+=itemprice;
                }
                Log.d(log, "this is total price" + totalRoomPrice);

                Log.d(log, "selectedFriendList : size() = " + selectedFriendList.size());
                for(int i=0; i<selectedFriendList.size();i++){
                            FriendData selectedFriend = (FriendData)selectedFriendList.get(i);
                            String friendName = selectedFriend.getName();
                            String friendPhone = selectedFriend.getPhoneNum();
                            Log.d(log, "friendName : " +friendName);
                            Log.d(log,"friendPhone : "+ friendPhone);
                }

                //최종 선택된 Party Name , 각자 Money 저장; SelectedPartyContainer 뒤져서, 모든 Party 정보, DRoom_PartyLists에 넣어서 만들어준다.
                final_DRoom_PartyLists();




                //임의로 정한 masterID & Phonenum
                masterID="jjunest";
                masterPhoneNum = "01072729771";

                //전송할 Droom_full_info 생성
                DRoom_fullinfo = new DRoom_FullInfo(masterID,masterPhoneNum,roomname,roomdate_int,DRoom_itemLists,totalRoomPrice,DRoom_partyLists);



                //DRoom_fullinfo 데이터 확인
                Log.d(log,"MasterID : "+masterID);
                Log.d(log,"masterPhoneNum : "+masterPhoneNum);
                Log.d(log,"roomname : "+roomname);
                Log.d(log,"roomdate_int : "+roomdate_int);
                Log.d(log,"DRoom_itemLists size : "+DRoom_itemLists.size());
                for(int i=0; i<DRoom_itemLists.size();i++){
                    String DroomITEMname = DRoom_itemLists.get(i).getDRoomitem_name();
                    Log.d(log,"DRoomItemName" + DroomITEMname);
                    int DroomITEMprice = DRoom_itemLists.get(i).getDRoomitem_price();
                    Log.d(log,"DRoomItemName" + DroomITEMprice);
                }
                Log.d(log,"totalRoomPrice : "+totalRoomPrice);
                Log.d(log,"DRoom_partyLists size : "+DRoom_partyLists.size());
                for(int i=0; i<DRoom_partyLists.size();i++){
                    String PartyName = DRoom_partyLists.get(i).getParty_name();
                    Log.d(log,"PartyName" + PartyName);
                    String PartyPhone = DRoom_partyLists.get(i).getPartyPhonenum();
                    Log.d(log,"PartyPhone" + PartyPhone);
                    String PartyPhoneNum = DRoom_partyLists.get(i).getPartyPhonenum();
                    Log.d(log,"PartyPhoneNum" + PartyPhoneNum);
                }

                //잠시 전송중단 (임시 주석처리) //
//                new InsertDRoomFullToDB().execute(DRoom_fullinfo);
                Log.d(log,"방만들기 버튼 클릭 완료 후 ");

            }
        });

        Log.d(log,"this is after clickEVENT");

        //get

    }

    //미완성 friend LIST 선택 화면에서 올시에 데이터 처리 과정
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                //FriendList_ForSelector에서 친구들을 모두 선택하고 돌아올 시에,
        if(requestCode == FriendListREQUEST_CODE){

            if(resultCode == RESULT_OK){

                selectedFriendList.clear();
                Log.d(log, " When Friend Selection is DONE ! On ActivityResult() called " +logclass);
                //미완성 인텐트 받아와서,  받은 데이터들을 List<DRoomPartyInfo> DRoom_partyLists = new ArrayList<DRoomPartyInfo>();에 넣어주기

                //FrinedList이니 Bundle로 받아온다.
                Bundle bundle = data.getExtras();
                if(bundle!=null){
                    Log.d(log, "bundle exist: "+bundle);
                }
                //선택된 친구 List를 selectedFriendList으로 저장
                selectedFriendList = (List<FriendData>)bundle.getSerializable("SelectedList");
                for(int i = 0 ; i<selectedFriendList.size();i++){
                    Log.d(log,"selectedFriendlist's name : "+ selectedFriendList.get(i).getName()+logclass);
                    Log.d(log,"selectedFriendlist's phoenum : "+ selectedFriendList.get(i).getPhoneNum()+logclass);
                }
            }
            //selectedFriendList 에 모든 선택된 친구들 정보 받아 오면, 새로 생성, 이때는 오직 partymoney없이 선택
            updatePartyContainer_withOnlyNameAndPhone();
        }
    }

    //for date picker
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    //(미완성) selectedFriendList를 기준으로 새롭게 PartyContainer에 알맞은 수 만큼 넣어준다.
    private void updatePartyContainer_withOnlyNameAndPhone(){
        for(int i=0; i<selectedFriendList.size(); i++) {
            final String FriendName = selectedFriendList.get(i).getName();
            final String FriendPhoneNum = selectedFriendList.get(i).getPhoneNum();
            Log.d(log,"Friend Selected Name"+FriendName +logclass);
            Log.d(log,"Friend Selected Phonenum"+FriendPhoneNum+logclass);
            PartyListContainer = (LinearLayout) findViewById(R.id.SelectedPartyContainer);
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addPartyView = layoutInflater.inflate(R.layout.partyrow, null);
            TextView partyName = (TextView)addPartyView.findViewById(R.id.partyNameTextView);
            partyName.setText(FriendName);
            TextView partyPhone = (TextView)addPartyView.findViewById(R.id.partyPhoneNumTextView);
            partyPhone.setText(FriendPhoneNum);

            Button removePartyBT = (Button)addPartyView.findViewById(R.id.partyRemoved);
            removePartyBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PartyListContainer.removeView(addPartyView);
                }
            });
            PartyListContainer.addView(addPartyView);

        }

    }



    //for date picker DIALOG 가 DATEPIICKER 일시에 불러오기
    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch(id)
        {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }


    //for date picker , 날짜 변경시 UPDATE 한다.
    private void updateDisplay()
    {
        if(mMonth<9){
            if(mDay<9){
                    date_textview .setText(new StringBuilder()
                    .append(mYear).append("")
                    .append("0")
                    .append(mMonth + 1).append("")
                            .append("0")
                            .append(mDay));
            }else{
                date_textview .setText(new StringBuilder()
                        .append(mYear).append("")
                        .append("0")
                        .append(mMonth + 1).append("")
                        .append(mDay));
            }

        }else{
            if(mDay<9) {
                date_textview.setText(new StringBuilder()
                        .append(mYear).append("")
                        .append(mMonth + 1).append("")
                        .append("0")
                        .append(mDay));
            }else{
                date_textview.setText(new StringBuilder()
                        .append(mYear).append("")
                        .append(mMonth + 1).append("")
                        .append(mDay));

            }
        }

    }

        //for insertDBFull DATA using AsyncTask
    private class InsertDRoomFullToDB extends AsyncTask<DRoom_FullInfo, String, String>{
        @Override
        protected String doInBackground(DRoom_FullInfo... params) {
        try {

            InsertRoomInfo = new Insert_DRoom_FullInfo_DB();
            DRoom_FullInfo newDRoom = (DRoom_FullInfo)params[0];
            Log.d(log,"InsertDRoomFullToDB() DRoomname ="+newDRoom.getDRoomName());
            InsertRoomInfo.insertIntoDB_DroomFullInfo(newDRoom, PHPlink);

        }catch(Exception e){
            e.printStackTrace();
        }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    //최종 선택된 Party Name , 각자 Money 저장; SelectedPartyContainer 뒤져서, 모든 Party 정보, DRoom_PartyLists에 넣어서 만들어준다.
    public void final_DRoom_PartyLists(){
        //모든 D_RoomPartyList 를 클리어 시키고 시작한다.
        DRoom_partyLists.clear();

        PartyListContainer = (LinearLayout) findViewById(R.id.SelectedPartyContainer);
        int partycounter = PartyListContainer.getChildCount();
        for(int i =0; i<partycounter; i++){
            RelativeLayout partyRelativeLayout = (RelativeLayout)PartyListContainer.getChildAt(i);
            TextView PartyTextView = (TextView)partyRelativeLayout.findViewById(R.id.partyNameTextView);
            String partyNameString = PartyTextView.getText().toString();
            TextView PartyPhoneView = (TextView)partyRelativeLayout.findViewById(R.id.partyPhoneNumTextView);
            String partyPhoneString = PartyPhoneView.getText().toString();
            EditText PartyMoneyView = (EditText)partyRelativeLayout.findViewById(R.id.partyMoneyEditView);
            String partyMoneyString = PartyMoneyView.getText().toString();
            int partyMoneyInt = Integer.parseInt(partyMoneyString);
            newDroomPartyInfo = new DRoomPartyInfo(partyNameString,partyPhoneString,partyMoneyInt,0);
            DRoom_partyLists.add(newDroomPartyInfo);

        }

    }

}
