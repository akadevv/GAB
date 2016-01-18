package com.example.lcd.gab.PayRoom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lcd.gab.ACCESS_TO_DB.Insert_DRoom_FullInfo_DB;
import com.example.lcd.gab.FriendData_ForSelect.FriendListMain_ForSelect;
import com.example.lcd.gab.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by LCD on 2016-01-11.
 */
public class PayRoomMain extends Activity {
    Context PayRoomContext;
    Activity PayRoomActivity;
    Dialog calendarDialog;
    private int mYear;
    private int mMonth;
    private int mDay;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_room);

        PayRoomContext=this;
        PayRoomActivity = (Activity)this;

        Context thiscontext = this;
        Log.d(log ,"getApplicationContext : "+PayRoomContext);
        Log.d(log,"this context: "+thiscontext);
        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        //더치페이 item List 품목 추가 버튼 눌렀을시 //미완성, DRAG & DROP 아이템 목록 창 만들기
        Button itemAddBT = (Button)findViewById(R.id.showItemListBT);
        itemAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog 띄우기
                Log.d(log, "this is itemList Button");


            }
        });

        //(미완성) 더치페이 친구 List 버튼 눌렀을 시에!
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


        //(미완성) 더치페이 item LIST에 데이터 입력시 자동으로 추가 생성
        EditText dutch_item_edit = (EditText) findViewById(R.id.roomitem_edit1_name);
        dutch_item_edit.setTag(ItemTag);
        dutch_item_edit.addTextChangedListener(new MyTextWatcherListener(thiscontext, PayRoomActivity));




        date_textview = (TextView)findViewById(R.id.roomdate_text);
        date_textview.setText(String.valueOf(mYear) + String.valueOf(mMonth) + String.valueOf(mDay));
        //date picker 추가
        Button datepick_button = (Button) findViewById(R.id.room_date_selector);
        datepick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(log,"this is datepickButton Clicked()");
                showDialog(DATE_DIALOG_ID);
            }
        });





            //Room making buttonclicked 방만들기 버튼 눌렀을 시에
        Button DroomButton = (Button)findViewById(R.id.DRoom_makingbutton);
        DroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DRoom_itemLists.clear();
                DRoom_partyLists.clear();
                Log.d(log, "onCreate payroommain() :");
                //getroomname
                EditText roomname_edit = (EditText)findViewById(R.id.roomname_edit);
                roomname = roomname_edit.getText().toString();
                Log.d(log, "onCreate payroommain()1.1");
                //get date
                EditText roomdate_edit = (EditText)findViewById(R.id.roomdate_edit);
                roomdate = roomdate_edit.getText().toString();
                roomdate_int = Integer.parseInt(roomdate);
                Log.d(log, "onCreate payroommain()1.2");
                //get item1's name
                totalRoomPrice=0;

                //첫 번쨰 아이템 넣기
                EditText roomitem_editname_1 = (EditText)findViewById(R.id.roomitem_edit1_name);
                itemname = roomitem_editname_1.getText().toString();
                Log.d(log, "onCreate payroommain()1.3");
                //get item1's price and add into itemLists
                EditText roomitem_editprice_1 = (EditText)findViewById(R.id.roomitem_edit1_price);
                Log.d(log, "onCreate payroommain()1.4");
                itemprice = Integer.parseInt(roomitem_editprice_1.getText().toString());
                Log.d(log, "onCreate payroommain()1");
                newDroomItem = new DRoomItemInfo(itemname, itemprice);
                DRoom_itemLists.add(newDroomItem);

                //두 번쨰 아이템 넣기
                Log.d(log, "onCreate payroommain()3");
                //get item2's name & add into itemLists
                EditText roomitem_edit_2 = (EditText)findViewById(R.id.roomitem_edit2_name);
                itemname = roomitem_edit_2.getText().toString();
                EditText roomitem_editprice_2 = (EditText)findViewById(R.id.roomitem_edit2_price);
                itemprice = Integer.parseInt(roomitem_editprice_2.getText().toString());
                newDroomItem = new DRoomItemInfo(itemname, itemprice);
                DRoom_itemLists.add(newDroomItem);

                //get total price 아이템 총합 구하기
                for(int i =0 ; i< DRoom_itemLists.size();i++){
                    int itemprice = DRoom_itemLists.get(i).DRoomitem_price;

                    Log.d(log,"itemname :" +i+ ":"+ itemname);
                    Log.d(log,"itemprice :" +i+ ":"+ itemprice);
                    totalRoomPrice+=itemprice;

                }
                Log.d(log, "this is total price" + totalRoomPrice);
                //partyList 데이터 꺼내오기 (파티원 전화번호, 파티원에 해당되는 금액)
                 party_phoneNum = (EditText)findViewById(R.id.party_edit1);
                party_phoneNum_String = party_phoneNum.getText().toString();
                 party_Money = (EditText)findViewById(R.id.party_edit1_mooney);
                 party_Money_int = Integer.parseInt(party_Money.getText().toString());
                newDroomPartyInfo = new DRoomPartyInfo(party_phoneNum_String,party_Money_int,0);
                DRoom_partyLists.add(newDroomPartyInfo);

                party_phoneNum = (EditText)findViewById(R.id.party_edit2);
                party_phoneNum_String = party_phoneNum.getText().toString();
                party_Money = (EditText)findViewById(R.id.party_edit2_mooney);
                party_Money_int = Integer.parseInt(party_Money.getText().toString());
                newDroomPartyInfo = new DRoomPartyInfo(party_phoneNum_String,party_Money_int,0);
                DRoom_partyLists.add(newDroomPartyInfo);
                masterID="jjunest";
                masterPhoneNum = "01072729771";
                //전송할 Droom_full_info 생성
                DRoom_fullinfo = new DRoom_FullInfo(masterID,masterPhoneNum,roomname,roomdate_int,DRoom_itemLists,totalRoomPrice,DRoom_partyLists);


                new InsertDRoomFullToDB().execute(DRoom_fullinfo);

            }
        });

        Log.d(log,"this is after clickEVENT");

        //get

    }

    //미완성 friend LIST 선택 화면에서 올시에 데이터 처리 과정
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FriendListREQUEST_CODE){
            if(resultCode == RESULT_OK){
                Log.d(log, " FriendsList RESULT OK");
                //미완성 인텐트 받아와서,  받은 데이터들을 List<DRoomPartyInfo> DRoom_partyLists = new ArrayList<DRoomPartyInfo>();에 넣어주기

            }
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

    //for date picker
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


    //for date picker
    private void updateDisplay()
    {
        date_textview .setText(new StringBuilder()
                .append(mMonth + 1).append("-")
                .append(mDay).append("-")
                .append(mYear).append(" "));
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

}
