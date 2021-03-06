package com.innovest.lcd.gab.PayRoom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.innovest.lcd.gab.ACCESS_TO_DB.Insert_DRoom_FullInfo_DB;
import com.innovest.lcd.gab.ACCESS_TO_DB.Revise_DRoom_FullInfo_DB;
import com.innovest.lcd.gab.CommonListener.ImageViewTouchListener;
import com.innovest.lcd.gab.CommonListener.MoneyUnitListener_Edit;
import com.innovest.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.innovest.lcd.gab.FriendData_ForSelect.FriendData_ForSelect;
import com.innovest.lcd.gab.FriendData_ForSelect.FriendListMain_ForSelect;
import com.innovest.lcd.gab.MainPage.MainPage;
import com.innovest.lcd.gab.MasterInfo.MasterInfo;
import com.innovest.lcd.gab.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by LCD on 2016-01-11.
 */
public class PayRoomMakingPage extends Activity {
    private MasterInfo masterInfo = MasterInfo.getMasterInfo();
    String logclass = "| in PayRoomMain |";
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
    String log = "jjunest";
    String roomname;
    String roomdate;
    int roomdate_int;
    //java.util.List<java.util.Map.Entry<String,Integer>> itemLists= new java.util.ArrayList<>(); //(item 명 + 가격)
    List<DRoomItemInfo> DRoom_itemLists = new ArrayList<DRoomItemInfo>();

    String itemname;
    int itemprice;
    int totalRoomPrice = 0;
    String roomtotalprice;
    TextView date_textview;
    Button dateButton;
    //java.util.Map.Entry<String,Integer> pair1=new java.util.AbstractMap.SimpleEntry<>("Not Unique key1",1);
    //java.util.List<java.util.Map.Entry<String,Integer>> partyList = new java.util.ArrayList<>(); //(party phonenum, partymoney , party_finished)
    ArrayList<DRoomPartyInfo> DRoom_partyLists = new ArrayList<DRoomPartyInfo>();
    DRoom_FullInfo DRoom_fullinfo;
    DRoomItemInfo newDroomItem;
    int partyphonenum;
    EditText party_phoneNum;
    String party_phoneNum_String;
    DRoomPartyInfo newDroomPartyInfo;
    EditText party_Money;
    int party_Money_int;
    //for DB
    String PHPlink = "http://jjunest.cafe24.com/DB/insert_into_DRoomInfo.php";
    String revisedPHPlink = "http://jjunest.cafe24.com/DB/revise_into_DRoomInfo.php";
    //for Tag
    String ItemTag = "itemTag";
    String masterName;
    String masterPhoneNum;
    Insert_DRoom_FullInfo_DB InsertRoomInfo;
    //for friends list 부를때 코드
    public static final int FriendListREQUEST_CODE = 2001;
    List<FriendData_ForSelect> selectedFriendList = new ArrayList<FriendData_ForSelect>();
    EditText Itemin;
    LinearLayout itemContainer;
    String newitemName_String;
    String newitemPrice_String;
    int newitemPrice_Int;
    TextView MasterNameText;
    EditText MasterMoneyEdit;
    int MasterMoney;
    LinearLayout PartyListContainer;
    ImageView pizzaImg;
    ImageView hambugerImg;
    ImageView etcImg;
    ScrollView payRoomScrollView;
    ScrollView ItemContainerScrollView;
    TextView itemPriceTotalTextView;
    TextView partyTotalMoneyTextView;
    Button NDivideCalculatorBT;
    int itemNumber_int;
    private String GAB_Nanum_Bold = "NanumGothicBold.ttf";
    private String GAB_Nanum_Pen = "NanumPen.ttf";
    private String GAB_Nanum_ExtraBold = "NanumGothicExtraBold.ttf";
    private boolean itemoptionHidden = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_room_making_page);


        //폰트 설정
        TextView DRoomMaking_textView1 = (TextView)findViewById(R.id.DRoomMaking_textView1);
        DRoomMaking_textView1.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        TextView DRoomMaking_helpitemmessage =(TextView)findViewById(R.id.DRoomMaking_helpitemmessage);
        DRoomMaking_helpitemmessage.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Pen));

        Button DRoom_makingbutton = (Button)findViewById(R.id.DRoom_makingbutton);
        DRoom_makingbutton.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        Button room_date_selector = (Button)findViewById(R.id.room_date_selector);
        room_date_selector.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));

        Button DutchCalculateBT = (Button)findViewById(R.id.DutchCalculateBT);
        DutchCalculateBT.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));

        Button showFriendListBT = (Button)findViewById(R.id.showFriendListBT);
        showFriendListBT.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));

        TextView itemPriceTotal = (TextView)findViewById(R.id.itemPriceTotal);
        itemPriceTotal.setPaintFlags(itemPriceTotal.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView partyMoneyTotal = (TextView)findViewById(R.id.partyMoneyTotal);
        partyMoneyTotal.setPaintFlags(partyMoneyTotal.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //MasterContainer 의 정보를 넣는다 //로그인 시의 로그인 한 사람의 정보
        MasterNameText = (TextView) findViewById(R.id.MasterNameTextView);
        MasterNameText.setText(masterInfo.getMasterName()); //해당 부분은 본인의 이름 //로그인 시에 자동으로 설정
        MasterMoneyEdit = (EditText) findViewById(R.id.MasterMoneyEditText);
        //MasterMoneyEdit에 TotalMoneyListener 추가
        MasterMoneyEdit.addTextChangedListener(new PartyTotalPriceTextWatcher());
        MasterMoneyEdit.addTextChangedListener(new MoneyUnitListener_Edit(MasterMoneyEdit));

        //기존에 선택되있던 친구 목록들 삭제
        selectedFriendList.clear();
        DRoom_partyLists.clear();
        PayRoomContext = this;
        PayRoomActivity = (Activity) this;
        Context thiscontext = this;

        Log.d(log, "getApplicationContext : " + PayRoomContext);
        Log.d(log, "this context: " + thiscontext);
        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);



        //방제목 자동으로 삽입
        EditText DRoomMaking_roomname_Edit = (EditText)findViewById(R.id.roomname_edit);
        DRoomMaking_roomname_Edit.setText(mMonth + 1 + "월 " + mDay + "일 더치페이방");




        //Option item ImageView 에 설정
        setOptionItemTagAndListener();





        ScrollView DRoomScroll = (ScrollView) findViewById(R.id.DPayRoomMakingScrollView01);
        DRoomScroll.setOnDragListener(new DragListener());
        ItemContainerScrollView = (ScrollView) findViewById(R.id.DPayRoomMakingScrollView02);
        ItemContainerScrollView.setOnDragListener(new DragListener());

        //itemPriceTotalTextView
        itemPriceTotalTextView = (TextView) findViewById(R.id.itemPriceTotal);
        itemPriceTotalTextView.addTextChangedListener(new MoneyUnitListener_Text(itemPriceTotalTextView));
        itemContainer = (LinearLayout) findViewById(R.id.dutchPayItemContainer);

        //1/n으로 분배하기
        PartyListContainer = (LinearLayout) findViewById(R.id.SelectedPartyContainer);
        NDivideCalculatorBT = (Button) findViewById(R.id.DutchCalculateBT);
        //버튼 클릭시 나 + party 원들의 수만큼 나누어줌.
        NDivideCalculatorBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemContainer에 있는 모든 총합을 구한다.
                int totalItemPrice = calculateItemTotalPrice();
                int partyCounter = 1 + PartyListContainer.getChildCount(); //1은 master //나머지는 party원

                int DivideMoneyToParty = ((totalItemPrice / partyCounter) + 9) / 10 * 10;
                int DivideMoneyToMaster = totalItemPrice - (DivideMoneyToParty * (partyCounter - 1));
                //party원들에게 분배될 금액 표시
                Log.d(log, "partyCounter is :" + partyCounter);
                if (partyCounter > 1) {
                    for (int i = 0; i < PartyListContainer.getChildCount(); i++) {
                        LinearLayout partyLayout = (LinearLayout) PartyListContainer.getChildAt(i);
                        EditText partyMoney = (EditText) partyLayout.findViewById(R.id.partyMoneyEditView);
                        partyMoney.setText(String.valueOf(DivideMoneyToParty));

                    }
                }
                //방장에게 분배될 금액 표시
                MasterMoneyEdit.setText(String.valueOf(DivideMoneyToMaster));
            }
        });

        // 더치페이 친구 List 버튼 눌렀을 시에! FriendListMain_ForSelect Activity 를 불러온다
        // 이 순간, 현재까지 friend container 에 선택된 모든 친구 목록들을 검색해서 FriendListmain_ForSelect 에 넘겨준다
        // 그래야 선택했던 party 친구들을 선택할 수있게 된다.
        Button FriendListShowBT = (Button) findViewById(R.id.showFriendListBT);
        FriendListShowBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //친구 목록 보여주는 Recycler View 로 넘어가고 데이터 받아오기
                Log.d(log, "Friends List Button clicked");
                Intent friendList = new Intent(getApplicationContext(), FriendListMain_ForSelect.class);
                Bundle bundle = new Bundle();
                //지금까지 선택된 partyList들을 Droom_PartyLists에 넣어주고, 그것을 보낸다.
                final_DRoom_PartyLists();
                bundle.putSerializable("selectedParty", DRoom_partyLists);
                friendList.putExtras(bundle);
                startActivityForResult(friendList, FriendListREQUEST_CODE);


            }
        });

        //for date picker 날짜 선택 방
        dateButton = (Button) findViewById(R.id.room_date_selector);
        if (mMonth < 9) {
            mMonth_String = "0" + String.valueOf(mMonth + 1);
        } else {
            mMonth_String = String.valueOf(mMonth);
        }
        if (mDay < 10) {
            mDay_String = "0" + String.valueOf(mDay);
        } else {
            mDay_String = String.valueOf(mDay);
        }

        dateButton.setText(String.valueOf(mYear) +"-"+ mMonth_String + "-"+mDay_String);
        //date picker Click 시에
        Button datepick_button = (Button) findViewById(R.id.room_date_selector);
        datepick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(log, "this is datepickButton Clicked()");
                showDialog(DATE_DIALOG_ID);
            }
        });

        //item Option 에서 더 추가시

        ImageButton itemOptionMoreBT = (ImageButton)findViewById(R.id.itemOptionMoreBT);
        itemOptionMoreBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemoptionHidden)
                {
                    LinearLayout itemOptionLayout_hidden1 = (LinearLayout)findViewById(R.id.itemOptionLayout_hidden1);
                    itemOptionLayout_hidden1.setVisibility(View.VISIBLE);
                    LinearLayout itemOptionLayout_hidden2 = (LinearLayout)findViewById(R.id.itemOptionLayout_hidden2);
                    itemOptionLayout_hidden2.setVisibility(View.VISIBLE);
                    LinearLayout itemOptionLayout_hidden3 = (LinearLayout)findViewById(R.id.itemOptionLayout_hidden3);
                    itemOptionLayout_hidden3.setVisibility(View.VISIBLE);
                    ImageButton itemOptionMoreBT = (ImageButton)findViewById(R.id.itemOptionMoreBT);
                    itemOptionMoreBT.setBackgroundResource(R.drawable.minus_button);
                    itemoptionHidden = false;
                }

                else{
                    LinearLayout itemOptionLayout_hidden1 = (LinearLayout)findViewById(R.id.itemOptionLayout_hidden1);
                    itemOptionLayout_hidden1.setVisibility(View.GONE);
                    LinearLayout itemOptionLayout_hidden2 = (LinearLayout)findViewById(R.id.itemOptionLayout_hidden2);
                    itemOptionLayout_hidden2.setVisibility(View.GONE);
                    LinearLayout itemOptionLayout_hidden3 = (LinearLayout)findViewById(R.id.itemOptionLayout_hidden3);
                    itemOptionLayout_hidden3.setVisibility(View.GONE);
                    ImageButton itemOptionMoreBT = (ImageButton)findViewById(R.id.itemOptionMoreBT);
                    itemOptionMoreBT.setBackgroundResource(R.drawable.plus_button);
                    itemoptionHidden = true;


                }

            }
        });






        //partyMoney들의 총합을 보여준는 textView
        partyTotalMoneyTextView = (TextView) findViewById(R.id.partyMoneyTotal);
        partyTotalMoneyTextView.addTextChangedListener(new MoneyUnitListener_Text(partyTotalMoneyTextView));


        //Room making buttonclicked 방만들기 버튼 눌렀을 시에,모든 정보 합산 후에 DB에 넣어준다.
        Button DroomButton = (Button) findViewById(R.id.DRoom_makingbutton);
        DroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DRoom_itemLists.clear();
                //getroomname
                EditText roomname_edit = (EditText) findViewById(R.id.roomname_edit);
                roomname = roomname_edit.getText().toString();
                //get date

                roomdate = dateButton.getText().toString().replaceAll("-","").toString();
                roomdate_int = Integer.parseInt(roomdate);
                Log.d(log, "onCreate payroommain()1.2");
                //get item1's name


                //아이템 추가된 것 찾고, 아이템 이름 및 아이템 가격 넣기
                itemContainer = (LinearLayout) findViewById(R.id.dutchPayItemContainer);
                int itemCounter = itemContainer.getChildCount();
                Log.d(log, "this is itemCounter : " + itemCounter);
                for (int i = 0; i < itemCounter; i++) {


                    LinearLayout newItemLayout = (LinearLayout) itemContainer.getChildAt(i);
                    if (newItemLayout.getTag() == null) {
                        Log.d(log, "getTag is null");
                    } else {
                        if (newItemLayout.getTag().equals("newItemTAG")) {
                            TextView itemNameTextView = (TextView) newItemLayout.findViewById(R.id.itemNameText);
                            newitemName_String = itemNameTextView.getText().toString();
                            EditText itemPriceView = (EditText) newItemLayout.findViewById(R.id.itemPriceEdit);
                            //price는 3자리마다 ,가 있으니 모든 콤마를 없애준후에 넣어준다.
                            newitemPrice_String = itemPriceView.getText().toString().replaceAll(",", "");
                            NumberPicker itemNumberPicker = (NumberPicker) newItemLayout.findViewById(R.id.numberPicker);
                            itemNumber_int = itemNumberPicker.getValue();
                            Log.d(log, "newitemName_String : " + newitemName_String);
                            Log.d(log, "newitemPrice_String : " + newitemPrice_String);
                            Log.d(log, "new itemNumber_int :" + itemNumber_int);
                            newitemPrice_Int = Integer.parseInt(newitemPrice_String);
                            //item 개수대로 itemList에 넣어주기
                            newDroomItem = new DRoomItemInfo(newitemName_String, newitemPrice_Int, itemNumber_int);
                            DRoom_itemLists.add(newDroomItem);
                        }

                    }

                }

                //get total price 아이템 총합 구하기 (ITEM의 TOTAL 총합 금액 구하기)// 해당 부분은 textview로 수정이 불가능하게, item 가격이 변동될 시에, total text view가 수정됨
                totalRoomPrice = 0;
                int totalItemPrice = 0;
                totalItemPrice = calculateItemTotalPrice();
                totalRoomPrice = totalItemPrice;


                //최종 선택된 Party Name , 각자 Money 저장; SelectedPartyContainer 뒤져서, 모든 Party 정보, DRoom_PartyLists에 넣어서 만들어준다.
                DRoom_partyLists = final_DRoom_PartyLists();

                //마지막에 방 주인의 정보를 넣어준다. DRoom_partLists에도 추가해준다. .
                masterName = MasterNameText.getText().toString();
                masterPhoneNum = masterInfo.getMasterPhoneNum(); //이미 master폰번호
                MasterMoney = Integer.valueOf(MasterMoneyEdit.getText().toString().replaceAll(",", ""));
                newDroomPartyInfo = new DRoomPartyInfo(masterName, masterPhoneNum, MasterMoney, 1);
                DRoom_partyLists.add(newDroomPartyInfo);


                //전송할 Droom_full_info 생성
                DRoom_fullinfo = new DRoom_FullInfo(masterName, masterPhoneNum, roomname, roomdate_int, DRoom_itemLists, totalRoomPrice, DRoom_partyLists);
                Log.d(log, "=======================DATA TO DB ===========================");
                //DRoom_fullinfo 데이터 확인
                Log.d(log, "masterName : " + masterName);
                Log.d(log, "masterPhoneNum : " + masterPhoneNum);
                Log.d(log, "roomname : " + roomname);
                Log.d(log, "roomdate_int : " + roomdate_int);
                Log.d(log, "DRoom_itemLists size : " + DRoom_itemLists.size());
                for (int i = 0; i < DRoom_itemLists.size(); i++) {
                    String DroomITEMname = DRoom_itemLists.get(i).getDRoomitem_name();
                    Log.d(log, "DRoomItemName :" + DroomITEMname);
                    int DroomITEMprice = DRoom_itemLists.get(i).getDRoomitem_price();
                    Log.d(log, "DRoomItemPrice : " + DroomITEMprice);
                    int DRoomITEMNum = DRoom_itemLists.get(i).getDRoomitem_number();
                    Log.d(log, "DRoomItemNumber : " + DRoomITEMNum);
                }
                Log.d(log, "totalRoomPrice : " + totalRoomPrice);
                Log.d(log, "DRoom_partyLists size : " + DRoom_partyLists.size());
                for (int i = 0; i < DRoom_partyLists.size(); i++) {
                    String PartyName = DRoom_partyLists.get(i).getParty_name();
                    Log.d(log, "PartyName : " + PartyName);
                    String PartyPhone = DRoom_partyLists.get(i).getPartyPhonenum();
                    Log.d(log, "PartyPhone : " + PartyPhone);
                    int PartyPhoneNum = DRoom_partyLists.get(i).getPartyMoney();
                    Log.d(log, "PartyMoney : " + PartyPhoneNum);
                }

                //  DB에 전송 후 mainPage로 이동 & 끝내기
                new InsertDRoomFullToDB().execute(DRoom_fullinfo);
                Log.d(log, "방만들기 버튼 클릭 완료 후 ");
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.putExtra("currentPager", 1);
                startActivity(intent);
                finish();

            }
        });

        Log.d(log, "this is after clickEVENT");

        //get

    }

    //friend LIST 선택 화면에서 올시에 데이터 처리 과정
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //FriendList_ForSelector에서 친구들을 모두 선택하고 돌아올 시에,
        if (requestCode == FriendListREQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                selectedFriendList.clear();
                Log.d(log, " When Friend Selection is DONE ! On ActivityResult() called " + logclass);
                //FrinedList이니 Bundle로 받아온다.
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Log.d(log, "bundle exist: " + bundle);
                }
                //선택된 친구 List를 selectedFriendList으로 저장
                selectedFriendList = (List<FriendData_ForSelect>) bundle.getSerializable("SelectedList");
                for (int i = 0; i < selectedFriendList.size(); i++) {
                    Log.d(log, "selectedFriendlist's name : " + selectedFriendList.get(i).getName() + logclass);
                    Log.d(log, "selectedFriendlist's phoenum : " + selectedFriendList.get(i).getPhoneNum() + logclass);
                }
                //selectedFriendList 에 모든 선택된 친구들 정보 받아 오면, 새로 생성, 이때는 오직 partymoney없이 선택
                updatePartyContainer_withOnlyNameAndPhone();
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

    //selectedFriendList를 기준으로 새롭게 PartyContainer에 알맞은 수 만큼 넣어준다.
    private void updatePartyContainer_withOnlyNameAndPhone() {
        //기존에 있던 Party Container 에 있는 모든 child View 삭제
        PartyListContainer = (LinearLayout) findViewById(R.id.SelectedPartyContainer);
        clearAllChildView(PartyListContainer);

        for (int i = 0; i < selectedFriendList.size(); i++) {
            final String FriendName = selectedFriendList.get(i).getName();
            final String FriendPhoneNum = selectedFriendList.get(i).getPhoneNum();
            int FriendMoney = selectedFriendList.get(i).getMoney();
            String FriendMoney_String = String.valueOf(FriendMoney);
            Log.d(log, "Friend Selected Name" + FriendName + logclass);
            Log.d(log, "Friend Selected Phonenum" + FriendPhoneNum + logclass);
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addPartyView = layoutInflater.inflate(R.layout.partyrow, null);
            TextView partyName = (TextView) addPartyView.findViewById(R.id.partyNameTextView);
            partyName.setText(FriendName);
            TextView partyPhone = (TextView) addPartyView.findViewById(R.id.partyPhoneNumTextView);
            partyPhone.setText(FriendPhoneNum);
            EditText partyMoney = (EditText) addPartyView.findViewById(R.id.partyMoneyEditView);
            partyMoney.addTextChangedListener(new PartyTotalPriceTextWatcher());
            partyMoney.addTextChangedListener(new MoneyUnitListener_Edit(partyMoney));
            partyMoney.setText(FriendMoney_String);
            Button removePartyBT = (Button) addPartyView.findViewById(R.id.partyRemoved);
            removePartyBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PartyListContainer.removeView(addPartyView);
                    int totalPartyMoney = 0;
                    totalPartyMoney = calculatePartyTotalMoney();
                    Log.d(log, "totalPartyMoney :" + totalPartyMoney);
                    partyTotalMoneyTextView.setText(String.valueOf(totalPartyMoney));
                }
            });
            PartyListContainer.addView(addPartyView);

        }

    }


    //for date picker DIALOG 가 DATEPIICKER 일시에 불러오기
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }


    //for date picker , 날짜 변경시 UPDATE 한다.
    private void updateDisplay() {
        if (mMonth < 9) {
            if (mDay < 9) {
                dateButton.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append("0")
                        .append(mMonth + 1).append("-")
                        .append("0")
                        .append(mDay));
            } else {
                dateButton.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append("0")
                        .append(mMonth + 1).append("-")
                        .append(mDay));
            }

        } else {
            if (mDay < 9) {
                dateButton.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append("0")
                        .append(mDay));
            } else {
                dateButton.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay));

            }
        }

    }

    //for insertDBFull DATA using AsyncTask
    private class InsertDRoomFullToDB extends AsyncTask<DRoom_FullInfo, String, String> {
        ProgressDialog asyncDialog = new ProgressDialog(PayRoomMakingPage.this);

        @Override
        protected String doInBackground(DRoom_FullInfo... params) {
            try {

                InsertRoomInfo = new Insert_DRoom_FullInfo_DB();
                DRoom_FullInfo newDRoom = (DRoom_FullInfo) params[0];
                Log.d(log, "InsertDRoomFullToDB() DRoomname =" + newDRoom.getDRoomName());
                InsertRoomInfo.insertIntoDB_DroomFullInfo(newDRoom, PHPlink);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {

            asyncDialog.dismiss();

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();



            super.onPreExecute();

        }
    }


    //for revised UPDATE DATA TO DATABASE using AsyncTask
    private class reviseDRoomFullToDB extends AsyncTask<DRoom_FullInfo, String, String> {
        @Override
        protected String doInBackground(DRoom_FullInfo... params) {
            try {

                Revise_DRoom_FullInfo_DB reviseRoomInfo = new Revise_DRoom_FullInfo_DB();
                DRoom_FullInfo revised = (DRoom_FullInfo) params[0];
                Log.d(log, "InsertDRoomFullToDB() DRoomname =" + revised.getDRoomName());
                reviseRoomInfo.revise_DRoom_FullInfo_DB(revised, revisedPHPlink);

            } catch (Exception e) {
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
    public ArrayList<DRoomPartyInfo> final_DRoom_PartyLists() {
        //모든 D_RoomPartyList 를 클리어 시키고 시작한다.

        DRoom_partyLists.clear();

        PartyListContainer = (LinearLayout) findViewById(R.id.SelectedPartyContainer);
        int partycounter = PartyListContainer.getChildCount();
        for (int i = 0; i < partycounter; i++) {
            LinearLayout partyRelativeLayout = (LinearLayout) PartyListContainer.getChildAt(i);
            TextView PartyTextView = (TextView) partyRelativeLayout.findViewById(R.id.partyNameTextView);
            String partyNameString = PartyTextView.getText().toString();
            TextView PartyPhoneView = (TextView) partyRelativeLayout.findViewById(R.id.partyPhoneNumTextView);
            String partyPhoneString = PartyPhoneView.getText().toString().replaceAll("-","");
            EditText PartyMoneyView = (EditText) partyRelativeLayout.findViewById(R.id.partyMoneyEditView);
            //partyMoney 에 있는 모든 콤마를 없애준다.
            String partyMoneyString = PartyMoneyView.getText().toString().replaceAll(",", "");
            //숫자 입력칸 null 방지,
            if (partyMoneyString.equals("")) {
                partyMoneyString = "0";
            }
            int partyMoneyInt = Integer.parseInt(partyMoneyString);
            newDroomPartyInfo = new DRoomPartyInfo(partyNameString, partyPhoneString, partyMoneyInt, 0);
            DRoom_partyLists.add(newDroomPartyInfo);

        }
        return DRoom_partyLists;
    }

    //모든 child View 를 삭제하는 함수
    private void clearAllChildView(ViewGroup v) {
        boolean doBreak = false;
        while (!doBreak) {
            int childCount = v.getChildCount();
            int i;
            for (i = 0; i < childCount; i++) {
                View currentChild = v.getChildAt(i);
                // Change ImageView with your desired type view
                if (currentChild instanceof RelativeLayout) {
                    v.removeView(currentChild);
                    break;
                } else if (currentChild instanceof LinearLayout) {
                    v.removeView(currentChild);
                    break;
                }
            }

            if (i == childCount) {
                doBreak = true;
            }
        }
    }



    //아이템의 Drag & Drop On Drag Listener, Container에 설정을 한다. onDrag에서는 true로 설정해주어야 한다.
    class DragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            Log.d(log, "View v  :" + v);
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");
                    // 이미지가 들어왔다는 것을 알려주기 위해 배경이미지 변경
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DROP v: " + v);

                    if (v == findViewById(R.id.DPayRoomMakingScrollView02)) {

                        ImageView DraggedImageView = (ImageView) event.getLocalState();
                        Drawable DraggedImageDrawable = DraggedImageView.getDrawable();
                        Log.d(log, "ImageView = " + DraggedImageView);
                        Log.d(log, "ImageViewDrawable = " + DraggedImageDrawable);


                        // drop 시에 inflater를 이용해서 itemrow를 넣는다.
                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View addView = layoutInflater.inflate(R.layout.itemrow, null);
                        ImageView itemRowImage = (ImageView) addView.findViewById(R.id.ItemImageView);

                        itemRowImage.setBackground(DraggedImageDrawable);  //setBackground 작동함 (API버전 문제임)
                        TextView textOut = (TextView) addView.findViewById(R.id.itemNameText);
                        EditText itemprice = (EditText) addView.findViewById(R.id.itemPriceEdit);
                        itemprice.addTextChangedListener(new MoneyUnitListener_Edit(itemprice)); //3자리씩 끊어서 보여주는 listener
                        itemprice.addTextChangedListener(new itemTotalPriceTextWatcher());       //totalPrice를 변경해주는 listener
                        textOut.setText(DraggedImageView.getTag().toString());
                        NumberPicker numPicker = (NumberPicker) addView.findViewById(R.id.numberPicker);
                        numPicker.setMinValue(1);
                        numPicker.setMaxValue(100);
                        numPicker.setValue(1);
                        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                int totalItemPrice = 0;
                                totalItemPrice = calculateItemTotalPrice();
                                Log.d(log, "totalPrice :" + totalItemPrice);
                                itemPriceTotalTextView.setText(String.valueOf(totalItemPrice));

                            }
                        });
                        Button buttonRemove = (Button) addView.findViewById(R.id.remove);
                        //추가된 layout/itemrow.xml 에 remove 버튼 눌럿을 시에, 해당 addView를 사라지게 만든다.
                        buttonRemove.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ((LinearLayout) addView.getParent()).removeView(addView);
                                //버튼 누를시마다 itemPriceTotal 을 바꾸어서 계산해준다.
                                int totalItemPrice = 0;
                                totalItemPrice = calculateItemTotalPrice();
                                Log.d(log, "totalPrice :" + totalItemPrice);
                                itemPriceTotalTextView.setText(String.valueOf(totalItemPrice));


                            }
                        });

                        itemContainer.addView(addView);


                    } else {
                        Log.d(log, "you can''t drop in here");
                        break;
                    }
                    break;


                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");

                    break;
                default:
                    break;


            }
            return true;
        }
    }


    //itemContainer안의 모든 totalPrice를 구한다.return total item price (int) //itemContainer price에 있는 모든 콤마를 제외하고 계산해준다.
    public int calculateItemTotalPrice() {
        int totalItemPrice = 0;
        itemContainer = (LinearLayout) findViewById(R.id.dutchPayItemContainer);
        int itemCounter = itemContainer.getChildCount();
        Log.d(log, "this is itemCounter : " + itemCounter);
        for (int i = 0; i < itemCounter; i++) {
            //각각의 itemcontainer
            LinearLayout newItemLayout = (LinearLayout) itemContainer.getChildAt(i);
            Log.d(log, "newItemLayout :" + newItemLayout);
            if (newItemLayout.getTag() == null) {
                Log.d(log, "getTag is null");
            } else {
                if (newItemLayout.getTag().equals("newItemTAG")) {

                    EditText itemprice = (EditText) newItemLayout.findViewById(R.id.itemPriceEdit);
                    NumberPicker item_numPicker = (NumberPicker) newItemLayout.findViewById(R.id.numberPicker);
                    Log.d(log, "itemprice.getText() :" + itemprice.getText());
                    if (itemprice.getText().toString().trim().length() == 0) {
                        Log.d(log, "itemprice is null");

                    } else {
                        //itemprice.getText에서 3자리 단위를 위한 모든 콤마를 빼주어야 한다.


                        Log.d(log, "itemprice is not null");
                        totalItemPrice += Integer.parseInt(itemprice.getText().toString().replaceAll(",", "")) * item_numPicker.getValue();
                    }

                }

            }

        }


        return totalItemPrice;
    }

    public int calculatePartyTotalMoney() {
        int totalItemPrice = 0;
        PartyListContainer = (LinearLayout) findViewById(R.id.SelectedPartyContainer);
        int partyCounter = PartyListContainer.getChildCount();
        Log.d(log, "this is partyContainer : " + partyCounter);
        for (int i = 0; i < partyCounter; i++) {
            //각각의 itemcontainer
            LinearLayout newPartyLayout = (LinearLayout) PartyListContainer.getChildAt(i);
            Log.d(log, "newPartyLayout :" + newPartyLayout);
            if (newPartyLayout.getTag() == null) {
                Log.d(log, "getTag is null");
            } else {
                if (newPartyLayout.getTag().equals("newPartyTAG")) {

                    EditText partyMoney = (EditText) newPartyLayout.findViewById(R.id.partyMoneyEditView);

                    Log.d(log, "partymoney.getText() :" + partyMoney.getText());
                    if (partyMoney.getText().toString().trim().length() == 0) { //가격에 아무것도 없으면 넘어간다.
                        Log.d(log, "itemprice is null");
                    } else {//가격에 숫자가있으면 totalprice에 가격을 더해준다.
                        //itemprice.getText에서 3자리 단위를 위한 모든 콤마를 빼주어야 한다.
                        Log.d(log, "itemprice is not null");
                        totalItemPrice += Integer.parseInt(partyMoney.getText().toString().replaceAll(",", ""));
                    }

                }

            }
        }

        if (MasterMoneyEdit.getText().toString().trim().length() == 0) {

        } else {
            totalItemPrice += Integer.parseInt(MasterMoneyEdit.getText().toString().replaceAll(",", ""));
        }

        return totalItemPrice;
    }

    //ITEM 항목의 가격 변동시에 itemPriceTotalTextView 에 Total Price를 바꿔주는  TextWatcher
    private class itemTotalPriceTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int totalItemPrice = 0;
            totalItemPrice = calculateItemTotalPrice();
            Log.d(log, "totalPrice :" + totalItemPrice);
            itemPriceTotalTextView.setText(String.valueOf(totalItemPrice));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    //PARTY MONEY 분배 가격 변동시에 partyMoneyTotal Textview에 Total Price를 바꿔주는  TextWatcher
    private class PartyTotalPriceTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int totalPartyMoney = 0;
            totalPartyMoney = calculatePartyTotalMoney();
            Log.d(log, "totalPartyMoneyPrice :" + totalPartyMoney);
            partyTotalMoneyTextView.setText(String.valueOf(totalPartyMoney));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }



    private void setOptionItemTagAndListener(){

        //피자 item image
        ImageView item_pizza = (ImageView) findViewById(R.id.item_pizza);
        item_pizza.setTag(R.drawable.item_pizza);
        item_pizza.setOnTouchListener(new ImageViewTouchListener());
        item_pizza.setOnClickListener(new ItemClickAddListenerSRC(item_pizza));

        //치킨 item image
        ImageView item_chicken = (ImageView) findViewById(R.id.item_chicken);
        item_chicken.setTag(R.drawable.item_chicken);
        item_chicken.setOnTouchListener(new ImageViewTouchListener());
        item_chicken.setOnClickListener(new ItemClickAddListenerSRC(item_chicken));

        ImageView item_hambuger = (ImageView) findViewById(R.id.item_hambuger);
        item_hambuger.setTag(R.drawable.item_hambuger);
        item_hambuger.setOnTouchListener(new ImageViewTouchListener());
        item_hambuger.setOnClickListener(new ItemClickAddListenerSRC(item_hambuger));

        ImageView item_hotdog = (ImageView) findViewById(R.id.item_hotdog);
        item_hotdog.setTag(R.drawable.item_hotdog);
        item_hotdog.setOnTouchListener(new ImageViewTouchListener());
        item_hotdog.setOnClickListener(new ItemClickAddListenerSRC(item_hotdog));

        ImageView item_noodle = (ImageView) findViewById(R.id.item_noodle);
        item_noodle.setTag(R.drawable.item_noodle);
        item_noodle.setOnTouchListener(new ImageViewTouchListener());
        item_noodle.setOnClickListener(new ItemClickAddListenerSRC(item_noodle));

        ImageView item_bread = (ImageView) findViewById(R.id.item_bread);
        item_bread.setTag(R.drawable.item_bread);
        item_bread.setOnTouchListener(new ImageViewTouchListener());
        item_bread.setOnClickListener(new ItemClickAddListenerSRC(item_bread));

        ImageView item_steak = (ImageView) findViewById(R.id.item_steak);
        item_steak.setTag(R.drawable.item_steak);
        item_steak.setOnTouchListener(new ImageViewTouchListener());
        item_steak.setOnClickListener(new ItemClickAddListenerSRC(item_steak));

        ImageView item_pig = (ImageView) findViewById(R.id.item_pig);
        item_pig.setTag(R.drawable.item_pig);
        item_pig.setOnTouchListener(new ImageViewTouchListener());
        item_pig.setOnClickListener(new ItemClickAddListenerSRC(item_pig));

        ImageView item_dougnut = (ImageView) findViewById(R.id.item_dougnut);
        item_dougnut.setTag(R.drawable.item_dougnut);
        item_dougnut.setOnTouchListener(new ImageViewTouchListener());
        item_dougnut.setOnClickListener(new ItemClickAddListenerSRC(item_dougnut));

        ImageView item_dining = (ImageView) findViewById(R.id.item_dining);
        item_dining.setTag(R.drawable.item_dining);
        item_dining.setOnTouchListener(new ImageViewTouchListener());
        item_dining.setOnClickListener(new ItemClickAddListenerSRC(item_dining));

        ImageView item_beer = (ImageView) findViewById(R.id.item_beer);
        item_beer.setTag(R.drawable.item_beer);
        item_beer.setOnTouchListener(new ImageViewTouchListener());
        item_beer.setOnClickListener(new ItemClickAddListenerSRC(item_beer));

        ImageView item_soju = (ImageView) findViewById(R.id.item_soju);
        item_soju.setTag(R.drawable.item_soju);
        item_soju.setOnTouchListener(new ImageViewTouchListener());
        item_soju.setOnClickListener(new ItemClickAddListenerSRC(item_soju));

        ImageView item_coffee = (ImageView) findViewById(R.id.item_coffee);
        item_coffee.setTag(R.drawable.item_coffee);
        item_coffee.setOnTouchListener(new ImageViewTouchListener());
        item_coffee.setOnClickListener(new ItemClickAddListenerSRC(item_coffee));

        ImageView item_icecream = (ImageView) findViewById(R.id.item_icecream);
        item_icecream.setTag(R.drawable.item_icecream);
        item_icecream.setOnTouchListener(new ImageViewTouchListener());
        item_icecream.setOnClickListener(new ItemClickAddListenerSRC(item_icecream));

        ImageView item_cookies = (ImageView) findViewById(R.id.item_cookies);
        item_cookies.setTag(R.drawable.item_cookies);
        item_cookies.setOnTouchListener(new ImageViewTouchListener());
        item_cookies.setOnClickListener(new ItemClickAddListenerSRC(item_cookies));

        ImageView item_birthday = (ImageView) findViewById(R.id.item_birthday);
        item_birthday.setTag(R.drawable.item_birthday);
        item_birthday.setOnTouchListener(new ImageViewTouchListener());
        item_birthday.setOnClickListener(new ItemClickAddListenerSRC(item_birthday));

        ImageView item_bowling = (ImageView) findViewById(R.id.item_bowling);
        item_bowling.setTag(R.drawable.item_bowling);
        item_bowling.setOnTouchListener(new ImageViewTouchListener());
        item_bowling.setOnClickListener(new ItemClickAddListenerSRC(item_bowling));

        ImageView item_carrent = (ImageView) findViewById(R.id.item_carrent);
        item_carrent.setTag(R.drawable.item_carrent);
        item_carrent.setOnTouchListener(new ImageViewTouchListener());
        item_carrent.setOnClickListener(new ItemClickAddListenerSRC(item_carrent));

        ImageView item_gas = (ImageView) findViewById(R.id.item_gas);
        item_gas.setTag(R.drawable.item_gas);
        item_gas.setOnTouchListener(new ImageViewTouchListener());
        item_gas.setOnClickListener(new ItemClickAddListenerSRC(item_gas));

        ImageView item_movieticket = (ImageView) findViewById(R.id.item_movieticket);
        item_movieticket.setTag(R.drawable.item_movieticket);
        item_movieticket.setOnTouchListener(new ImageViewTouchListener());
        item_movieticket.setOnClickListener(new ItemClickAddListenerSRC(item_movieticket));

        ImageView item_pcroom = (ImageView) findViewById(R.id.item_pcroom);
        item_pcroom.setTag(R.drawable.item_pcroom);
        item_pcroom.setOnTouchListener(new ImageViewTouchListener());
        item_pcroom.setOnClickListener(new ItemClickAddListenerSRC(item_pcroom));

        ImageView item_taxi = (ImageView) findViewById(R.id.item_taxi);
        item_taxi.setTag(R.drawable.item_taxi);
        item_taxi.setOnTouchListener(new ImageViewTouchListener());
        item_taxi.setOnClickListener(new ItemClickAddListenerSRC(item_taxi));


        ImageView item_chicken_raw = (ImageView) findViewById(R.id.item_chicken_raw);
        item_chicken_raw.setTag(R.drawable.item_chicken_raw);
        item_chicken_raw.setOnTouchListener(new ImageViewTouchListener());
        item_chicken_raw.setOnClickListener(new ItemClickAddListenerSRC(item_chicken_raw));


        ImageView item_cow = (ImageView) findViewById(R.id.item_cow);
        item_cow.setTag(R.drawable.item_cow);
        item_cow.setOnTouchListener(new ImageViewTouchListener());
        item_cow.setOnClickListener(new ItemClickAddListenerSRC(item_cow));





    }


    //아이템 클릭시에 추가하는 Listener _ tag에 있는 id를 분석해서 추가해주는 listener
    private class ItemClickAddListenerSRC implements View.OnClickListener {
        ImageView clickedImageView;

        public ItemClickAddListenerSRC(ImageView clickedImageView) {
            this.clickedImageView = clickedImageView;
        }

        @Override
        public void onClick(View v) {

            int clickedImageID = Integer.valueOf(clickedImageView.getTag().toString());
            Log.d(log, "clickedImageID = " + clickedImageID);

            final String clickedImageName = findImageNameByTag(clickedImageID);

            // drop 시에 inflater를 이용해서 itemrow를 넣는다.
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.itemrow, null);
            ImageView itemRowImage = (ImageView) addView.findViewById(R.id.ItemImageView);
            itemRowImage.setBackgroundResource(clickedImageID);  //setBackground 작동함 (API버전 문제임)
            TextView textOut = (TextView) addView.findViewById(R.id.itemNameText);
            textOut.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));
            //item 이름을 눌렀을 시에, alertdialog 를 통해 item이름을 바꿀 수있는 dialog를 만들어 준다.
            textOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextView textOut = (TextView)v;
                    // get prompts.xml view
                    LayoutInflater layoutInflater = LayoutInflater.from(PayRoomMakingPage.this);
                    View promptView = layoutInflater.inflate(R.layout.itemname_change_popup, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PayRoomMakingPage.this);
                    alertDialogBuilder.setView(promptView);

                    final EditText edittext_itemnamechange = (EditText) promptView.findViewById(R.id.edittext_itemnamechange);
                    edittext_itemnamechange.setText(textOut.getText().toString());
                    edittext_itemnamechange.setSelection(edittext_itemnamechange.getText().length());
                    // setup a dialog window
                    alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("변경", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    textOut.setText(edittext_itemnamechange.getText().toString());
                                }
                            })
                            .setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create an alert dialog
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();

                }
            });

            EditText itemprice = (EditText) addView.findViewById(R.id.itemPriceEdit);
            itemprice.addTextChangedListener(new MoneyUnitListener_Edit(itemprice)); //3자리씩 끊어서 보여주는 listener
            itemprice.addTextChangedListener(new itemTotalPriceTextWatcher());       //totalPrice를 변경해주는 listener
            textOut.setText(clickedImageName); //이미지 기본 이름 설정하기
            NumberPicker numPicker = (NumberPicker) addView.findViewById(R.id.numberPicker);
            numPicker.setMinValue(1);
            numPicker.setMaxValue(100);
            numPicker.setValue(1);
            numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    int totalItemPrice = 0;
                    totalItemPrice = calculateItemTotalPrice();
                    Log.d(log, "totalPrice :" + totalItemPrice);
                    TextView itemPriceTotal = (TextView) findViewById(R.id.itemPriceTotal);
                    itemPriceTotal.setText(String.valueOf(totalItemPrice));

                }
            });
            Button buttonRemove = (Button) addView.findViewById(R.id.remove);
            buttonRemove.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));
            //추가된 layout/itemrow.xml 에 remove 버튼 눌럿을 시에, 해당 addView를 사라지게 만든다.
            buttonRemove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((LinearLayout) addView.getParent()).removeView(addView);
                    //버튼 누를시마다 itemPriceTotal 을 바꾸어서 계산해준다.
                    int totalItemPrice = 0;
                    totalItemPrice = calculateItemTotalPrice();
                    Log.d(log, "totalPrice :" + totalItemPrice);
                    TextView itemPriceTotal = (TextView) findViewById(R.id.itemPriceTotal);
                    itemPriceTotal.setText(String.valueOf(totalItemPrice));


                }
            });
            LinearLayout dutchPayItemContainer = (LinearLayout) findViewById(R.id.dutchPayItemContainer);
            dutchPayItemContainer.addView(addView);


        }
    }

    //item의 고유 이름에 따라서 item의 이름을 반환해주는 함수
    private String findImageNameByTag(int image_tag) {
        String imageName = "";
        switch (image_tag) {
            case R.drawable.item_pizza:
                imageName = "피자";
                break;

            case R.drawable.item_chicken:
                imageName = "치킨";
                break;

            case R.drawable.item_hambuger:
                imageName = "햄버거";
                break;

            case R.drawable.item_hotdog:
                imageName = "핫도그";
                break;

            case R.drawable.item_noodle:
                imageName = "면요리";
                break;

            case R.drawable.item_bread:
                imageName = "빵";
                break;

            case R.drawable.item_steak:
                imageName = "고기";
                break;

            case R.drawable.item_pig:
                imageName = "돼지";
                break;

            case R.drawable.item_dougnut:
                imageName = "도넛";
                break;
            case R.drawable.item_dining:
                imageName = "식사";
                break;

            case R.drawable.item_beer:
                imageName = "맥주";
                break;
            case R.drawable.item_soju:
                imageName = "소주";
                break;
            case R.drawable.item_coffee:
                imageName = "커피";
                break;
            case R.drawable.item_icecream:
                imageName = "아이스크림";
                break;
            case R.drawable.item_cookies:
                imageName = "쿠키&과자";
                break;
            case R.drawable.item_birthday:
                imageName = "생일파티";
                break;
            case R.drawable.item_bowling:
                imageName = "볼링";
                break;
            case R.drawable.item_carrent:
                imageName = "렌트카";
                break;
            case R.drawable.item_gas:
                imageName = "기름값";
                break;
            case R.drawable.item_movieticket:
                imageName = "영화티켓";
                break;
            case R.drawable.item_pcroom:
                imageName = "pc방";
                break;
            case R.drawable.item_taxi:
                imageName = "택시";
                break;
            case R.drawable.item_chicken_raw:
                imageName = "닭";
                break;

            case R.drawable.item_cow:
                imageName = "소";
                break;

        }


        return imageName;
    }

}
