package com.example.lcd.gab.PayRoom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lcd.gab.R;

import java.util.Calendar;

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
    java.util.List<java.util.Map.Entry<String,Integer>> itemLists= new java.util.ArrayList<>();
    String itemname;
    int itemprice;
    int totalRoomPrice=0;
    String roomtotalprice;
    TextView date_textview;
    //java.util.Map.Entry<String,Integer> pair1=new java.util.AbstractMap.SimpleEntry<>("Not Unique key1",1);
    java.util.List<java.util.Map.Entry<String,Integer>> partyList = new java.util.ArrayList<>();
    int partyphonenum;

    //for Tag
    String ItemTag = "itemTag";

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


        //더치페이 item List 품목 추가 버튼 눌렀을시
        Button itemAddBT = (Button)findViewById(R.id.showItemListBT);
        itemAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog 띄우기


            }
        });

        //더치페이 item LIST에 데이터 입력시 자동으로 추가 생성
        EditText dutch_item_edit = (EditText) findViewById(R.id.roomitem_edit1_name);
        dutch_item_edit.setTag(ItemTag);
        dutch_item_edit.addTextChangedListener(new MyTextWatcherListener(thiscontext,PayRoomActivity));






        //더치페이 party 친구들 추가 버튼 눌렀을 시
        Button partyAddBT = (Button)findViewById(R.id.showFriendsList);
        Log.d(log,"this is partyADDBT"+partyAddBT);

        partyAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent invitePartyintent = new Intent(getApplicationContext(),inviteParty.class);
                startActivity(invitePartyintent);
            }
        });



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





            //Room making buttonclicked
        Button DroomButton = (Button)findViewById(R.id.DRoom_makingbutton);
        DroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(log, "onCreate payroommain() :");
                //getroomname
                EditText roomname_edit = (EditText)findViewById(R.id.roomname_edit);
                roomname = roomname_edit.getText().toString();
                Log.d(log, "onCreate payroommain()1.1");
                //get date
                EditText roomdate_edit = (EditText)findViewById(R.id.roomdate_edit);
                roomdate = roomdate_edit.getText().toString();
                Log.d(log, "onCreate payroommain()1.2");

                //get item1's name
                totalRoomPrice=0;
                itemLists.clear();

                EditText roomitem_editname_1 = (EditText)findViewById(R.id.roomitem_edit1_name);
                itemname = roomitem_editname_1.getText().toString();
                Log.d(log, "onCreate payroommain()1.3");
                //get item1's price and add into itemLists
                EditText roomitem_editprice_1 = (EditText)findViewById(R.id.roomitem_edit1_price);
                Log.d(log, "onCreate payroommain()1.4");
                itemprice = Integer.parseInt(roomitem_editprice_1.getText().toString());
                Log.d(log, "onCreate payroommain()1");
                java.util.Map.Entry<String,Integer> pair1=new java.util.AbstractMap.SimpleEntry<>(itemname,itemprice);
                Log.d(log, "onCreate payroommain()2");
                itemLists.add(pair1);
                Log.d(log, "onCreate payroommain()3");
                //get item2's name & add into itemLists
                EditText roomitem_edit_2 = (EditText)findViewById(R.id.roomitem_edit2_name);
                itemname = roomitem_edit_2.getText().toString();
                EditText roomitem_editprice_2 = (EditText)findViewById(R.id.roomitem_edit2_price);
                itemprice = Integer.parseInt(roomitem_editprice_2.getText().toString());
                pair1=new java.util.AbstractMap.SimpleEntry<>(itemname,itemprice);
                itemLists.add(pair1);

                //get total price
                for(int i =0 ; i< itemLists.size();i++){
                    String itemname = itemLists.get(i).getKey();
                    int itemprice = itemLists.get(i).getValue();

                    Log.d(log,"itemname :" +i+ ":"+ itemname);
                    Log.d(log,"itemprice :" +i+ ":"+ itemprice);
                    totalRoomPrice+=itemprice;

                }
                Log.d(log,"this is total price"+totalRoomPrice);

                partyList.clear();
                //get participants List; = partyList
                EditText party_phonenum = (EditText)findViewById(R.id.party_edit1);
                partyphonenum = Integer.parseInt(party_phonenum.getText().toString());
                String party_name = "sample party name";
                java.util.Map.Entry<String,Integer> partypairs = new java.util.AbstractMap.SimpleEntry<>(itemname,partyphonenum);
                partyList.add(partypairs);


                //check partyList
                for(int i =0 ; i< partyList.size();i++){
                    String partyID = partyList.get(i).getKey();
                    int partyPhonenum = partyList.get(i).getValue();

                    Log.d(log,"partyname :" +i+ ":"+ partyID);
                    Log.d(log,"partyphonenum :" +i+ ":"+ partyPhonenum);


                }
                Log.d(log, "this is total price" + totalRoomPrice);


            }
        });

        Log.d(log,"this is after clickEVENT");

        //get

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



}
