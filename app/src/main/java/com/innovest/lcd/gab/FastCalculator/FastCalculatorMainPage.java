package com.innovest.lcd.gab.FastCalculator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovest.lcd.gab.CommonListener.ImageViewTouchListener;
import com.innovest.lcd.gab.CommonListener.MoneyUnitListener_Edit;
import com.innovest.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.innovest.lcd.gab.MasterInfo.MasterInfo;
import com.innovest.lcd.gab.R;
import com.innovest.lcd.gab.SettingOption.SettingOptionMain;
import com.kakao.kakaolink.AppActionBuilder;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-02-22.
 */
public class FastCalculatorMainPage extends Activity {
    private static MasterInfo masterInfo = MasterInfo.getMasterInfo();
    private String GAB_Nanum_Bold = "NanumGothicBold.ttf";
    private String GAB_Nanum_Pen = "NanumPen.ttf";
    private String GAB_Nanum_ExtraBold = "NanumGothicExtraBold.ttf";
    private String log = "jjunest";
    private int mYear;
    private int mMonth;
    private int mDay;
    String mMonth_String;
    String mDay_String;
    static final int DATE_DIALOG_ID = 0;
    Context FastCalculator_Context;
    Activity FastCalculator_Activity;
    private boolean itemoptionHidden = true;
    Context thisContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fastcalculator_room_main);

        //폰트 설정
        TextView FastCalculator_textView1 = (TextView) findViewById(R.id.FastCalculator_textView1);
        FastCalculator_textView1.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        TextView FastCalculator_helpitemmessage = (TextView) findViewById(R.id.FastCalculator_helpitemmessage);
        FastCalculator_helpitemmessage.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Pen));

        Button FastCalculator_Kakao_sendButton = (Button) findViewById(R.id.FastCalculator_Kakao_sendButton);
        FastCalculator_Kakao_sendButton.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        Button FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
        FastCalculator_room_date_selector.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));

        Button FastCalculator_DutchCalculateBT = (Button) findViewById(R.id.FastCalculator_DutchCalculateBT);
        FastCalculator_DutchCalculateBT.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));

        //밑줄 설정
        TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
        FastCalculator_itemPriceTotal.setPaintFlags(FastCalculator_itemPriceTotal.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
        FastCalculator_partyMoneyTotal.setPaintFlags(FastCalculator_partyMoneyTotal.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //MasterContainer 의 정보를 넣는다 //로그인 시의 로그인 한 사람의 정보
        TextView FastCalculator_MasterNameText = (TextView) findViewById(R.id.FastCalculator_MasterNameTextView);
        FastCalculator_MasterNameText.setText(masterInfo.getMasterName()); //해당 부분은 본인의 이름 //로그인 시에 자동으로 설정
        EditText FastCalculator_MasterMoneyEdit = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
        //MasterMoneyEdit에 TotalMoneyListener 추가
        FastCalculator_MasterMoneyEdit.addTextChangedListener(new PartyTotalPriceTextWatcher());
        FastCalculator_MasterMoneyEdit.addTextChangedListener(new MoneyUnitListener_Edit(FastCalculator_MasterMoneyEdit));

        FastCalculator_Context = this;
        FastCalculator_Activity = (Activity) this;

        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //setting button 설정 -> setting 버튼 클릭 시, 설정 화면으로 이동
        ImageView settingimg = (ImageView)findViewById(R.id.settingimg);
        settingimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent settingIntent = new Intent(thisContext,SettingOptionMain.class);
                startActivity(settingIntent);

            }
        });


        //방제목 자동으로 삽입
        EditText FastCalculator_roomname_edit = (EditText) findViewById(R.id.FastCalculator_roomname_edit);
        FastCalculator_roomname_edit.setText(mMonth + 1 + "월 " + mDay + "일 모임");


        //Option item ImageView 에 설정

        setOptionItemTagAndListener();


        //itemPriceTotalTextView
        FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
        FastCalculator_itemPriceTotal.addTextChangedListener(new MoneyUnitListener_Text(FastCalculator_itemPriceTotal));


        //for date picker 날짜 선택 방
        FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
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

        FastCalculator_room_date_selector.setText(String.valueOf(mYear) + "-" + mMonth_String + "-" + mDay_String);
        //date picker Click 시에
        FastCalculator_room_date_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(log, "this is datepickButton Clicked()");
                showDialog(DATE_DIALOG_ID);
            }
        });

        //item Option 에서 더 추가시

        ImageButton itemOptionMoreBT = (ImageButton) findViewById(R.id.itemOptionMoreBT);
        itemOptionMoreBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemoptionHidden) {
                    LinearLayout itemOptionLayout_hidden1 = (LinearLayout) findViewById(R.id.itemOptionLayout_hidden1);
                    itemOptionLayout_hidden1.setVisibility(View.VISIBLE);
                    LinearLayout itemOptionLayout_hidden2 = (LinearLayout) findViewById(R.id.itemOptionLayout_hidden2);
                    itemOptionLayout_hidden2.setVisibility(View.VISIBLE);
                    LinearLayout itemOptionLayout_hidden3 = (LinearLayout) findViewById(R.id.itemOptionLayout_hidden3);
                    itemOptionLayout_hidden3.setVisibility(View.VISIBLE);
                    ImageButton itemOptionMoreBT = (ImageButton) findViewById(R.id.itemOptionMoreBT);
                    itemOptionMoreBT.setBackgroundResource(R.drawable.minus_button);
                    itemoptionHidden = false;

                } else {
                    LinearLayout itemOptionLayout_hidden1 = (LinearLayout) findViewById(R.id.itemOptionLayout_hidden1);
                    itemOptionLayout_hidden1.setVisibility(View.GONE);
                    LinearLayout itemOptionLayout_hidden2 = (LinearLayout) findViewById(R.id.itemOptionLayout_hidden2);
                    itemOptionLayout_hidden2.setVisibility(View.GONE);
                    LinearLayout itemOptionLayout_hidden3 = (LinearLayout) findViewById(R.id.itemOptionLayout_hidden3);
                    itemOptionLayout_hidden3.setVisibility(View.GONE);
                    ImageButton itemOptionMoreBT = (ImageButton) findViewById(R.id.itemOptionMoreBT);
                    itemOptionMoreBT.setBackgroundResource(R.drawable.plus_button);
                    itemoptionHidden = true;

                }

            }
        });


        //더치페이 방원 number 적을 시에 TextWatcher 설정
        EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
        FastCalculator_totalPartyNum.addTextChangedListener(new FastCalculator_PartyTotalNum_Watcher());


        FastCalculator_DutchCalculateBT = (Button) findViewById(R.id.FastCalculator_DutchCalculateBT);

        //1/n 버튼 클릭시에  나 + party 원들의 수만큼 나누어줌.
        FastCalculator_DutchCalculateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int partyTotalNum = 0;

                EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
                String s = FastCalculator_totalPartyNum.getText().toString();
                //일단 모든 partyContainer안에 잇는 것 삭제
                LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
                clearAllChildView(FastCalculator_PartyContainer);

                Log.d(log, "s :" + s);

                if (s.toString().equals("")) {
                    partyTotalNum = 0;
                } else {
                    //partyTotalNum 이 숫자이라면
                    partyTotalNum = Integer.valueOf(s.toString());
                }

                for (int i = 0; i < partyTotalNum - 1; i++) {
                    final String FriendName = "참가자" + (i + 2);
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addPartyView = layoutInflater.inflate(R.layout.fastcalculator_partyrow, null);
                    TextView partyName = (TextView) addPartyView.findViewById(R.id.FastCalculator_partyNameTextView);
                    partyName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final TextView textOut = (TextView) v;
                            // get prompts.xml view
                            LayoutInflater layoutInflater = LayoutInflater.from(FastCalculatorMainPage.this);
                            View promptView = layoutInflater.inflate(R.layout.itemname_change_popup, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FastCalculatorMainPage.this);
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
                    partyName.setText(FriendName);

                    EditText partyMoney = (EditText) addPartyView.findViewById(R.id.FastCalculator_partyMoneyEditView);
                    partyMoney.addTextChangedListener(new PartyTotalPriceTextWatcher());
                    partyMoney.addTextChangedListener(new MoneyUnitListener_Edit(partyMoney));

                    Button removePartyBT = (Button) addPartyView.findViewById(R.id.FastCalculator_partyRemoved);
                    removePartyBT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
                            FastCalculator_PartyContainer.removeView(addPartyView);
                            int totalPartyMoney = 0;
                            totalPartyMoney = calculatePartyTotalMoney();
                            Log.d(log, "totalPartyMoney :" + totalPartyMoney);
                            TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
                            FastCalculator_partyMoneyTotal.setText(String.valueOf(totalPartyMoney));

                            //총인원 수 변동해주기
                            EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
                            if (FastCalculator_totalPartyNum.getText().toString().equals("")) {

                            } else {
                                int originaltotalNum = Integer.valueOf(FastCalculator_totalPartyNum.getText().toString());
                                int newtotalNum = originaltotalNum - 1;
                                FastCalculator_totalPartyNum.setText(String.valueOf(newtotalNum));

                            }


                        }
                    });

                    FastCalculator_PartyContainer.addView(addPartyView);

                }


                //itemContainer에 있는 모든 총합을 구한다.
                int totalItemPrice = calculateItemTotalPrice();
                FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
                int partyCounter = 1 + FastCalculator_PartyContainer.getChildCount(); //1은 master //나머지는 party원

                int DivideMoneyToParty = ((totalItemPrice / partyCounter) + 9) / 10 * 10;
                int DivideMoneyToMaster = totalItemPrice - (DivideMoneyToParty * (partyCounter - 1));
                //party원들에게 분배될 금액 표시
                Log.d(log, "partyCounter is :" + partyCounter);
                if (partyCounter > 1) {
                    for (int i = 0; i < FastCalculator_PartyContainer.getChildCount(); i++) {
                        LinearLayout partyLayout = (LinearLayout) FastCalculator_PartyContainer.getChildAt(i);
                        EditText partyMoney = (EditText) partyLayout.findViewById(R.id.FastCalculator_partyMoneyEditView);
                        partyMoney.setText(String.valueOf(DivideMoneyToParty));

                    }
                }
                //방장에게 분배될 금액 표시
                EditText FastCalculator_MasterMoneyEditText = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
                FastCalculator_MasterMoneyEditText.setText(String.valueOf(DivideMoneyToMaster));


            }
        });

        //partyTotalTextView 3자리씩 끊어서 보여주기
        FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
        FastCalculator_partyMoneyTotal.addTextChangedListener(new MoneyUnitListener_Text(FastCalculator_partyMoneyTotal));

        //카카오 버튼 누를 시에 계싼된 정보 메세지로 보내주기

        FastCalculator_Kakao_sendButton = (Button) findViewById(R.id.FastCalculator_Kakao_sendButton);
        FastCalculator_Kakao_sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //팝업창을 띄어서 마지막 정보 확인 후 메세지를 보내게 한다.


                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(FastCalculatorMainPage.this);
                View promptView = layoutInflater.inflate(R.layout.fastcalculator_lastconfirm, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FastCalculatorMainPage.this);
                alertDialogBuilder.setView(promptView);

                //popup창에 정보 입력
                EditText FastCalculator_roomname_edit = (EditText) findViewById(R.id.FastCalculator_roomname_edit);
                TextView lastconfirmName = (TextView) promptView.findViewById(R.id.lastconfirmName);
                lastconfirmName.setText(FastCalculator_roomname_edit.getText().toString());

                Button FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
                TextView lastconfirmRoomdate = (TextView) promptView.findViewById(R.id.lastconfirmRoomdate);
                lastconfirmRoomdate.setText(FastCalculator_room_date_selector.getText().toString());

                //lastconfirm popup창에 있는 item layout container 를 불러들인다. 그 후에 다 숫자에 맞춰서 넣는다.
                LinearLayout item_confirmContainer = (LinearLayout) promptView.findViewById(R.id.item_confirmContainer);

                LinearLayout FastCalculator_dutchPayItemContainer = (LinearLayout) findViewById(R.id.FastCalculator_dutchPayItemContainer);
                int itemCounter = FastCalculator_dutchPayItemContainer.getChildCount();
                for (int i = 0; i < itemCounter; i++) {
                    LinearLayout newItemLayout = (LinearLayout) FastCalculator_dutchPayItemContainer.getChildAt(i);
                    if (newItemLayout.getTag() == null) {
                        Log.d(log, "getTag is null");
                    } else {
                        if (newItemLayout.getTag().equals("newItemTAG")) {
                            //inflator 를 통해서 lastconfirm 에 붙여준다.
                            LayoutInflater itemlayoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View confirm_itemrow = itemlayoutInflater.inflate(R.layout.confirm_itemrow, null);


                            TextView itemNameTextView = (TextView) newItemLayout.findViewById(R.id.itemNameText);
                            String newitemName_String = itemNameTextView.getText().toString();
                            EditText itemPriceView = (EditText) newItemLayout.findViewById(R.id.itemPriceEdit);
                            //price는 3자리마다 ,가 있으니 모든 콤마를 없애준후에 넣어준다.

                            String newitemPrice_String = itemPriceView.getText().toString();
                            NumberPicker itemNumberPicker = (NumberPicker) newItemLayout.findViewById(R.id.numberPicker);
                            int itemNumber_int = itemNumberPicker.getValue();
                            Log.d(log, "newitemName_String : " + newitemName_String);
                            Log.d(log, "newitemPrice_String : " + newitemPrice_String);
                            Log.d(log, "new itemNumber_int :" + itemNumber_int);

                            TextView itemrow_itemname = (TextView) confirm_itemrow.findViewById(R.id.itemrow_itemname);
                            itemrow_itemname.setText(newitemName_String);

                            TextView itemrow_itemprice = (TextView) confirm_itemrow.findViewById(R.id.itemrow_itemprice);
                            itemrow_itemprice.setText(newitemPrice_String);

                            TextView itemrow_itemnum = (TextView) confirm_itemrow.findViewById(R.id.itemrow_itemnum);
                            itemrow_itemnum.setText(String.valueOf(itemNumber_int));

                            item_confirmContainer.addView(confirm_itemrow);


                        }

                    }

                }


                //항목 총합 구하고 popup 창에 띄우기
                TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
                TextView lastconfirmItemTotalPrice = (TextView) promptView.findViewById(R.id.lastconfirmItemTotalPrice);
                lastconfirmItemTotalPrice.setText(FastCalculator_itemPriceTotal.getText().toString());


                //총인원 구하고 popup
                EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
                TextView lastconfirmTotalPartyNum = (TextView) promptView.findViewById(R.id.lastconfirmTotalPartyNum);
                lastconfirmTotalPartyNum.setText(FastCalculator_totalPartyNum.getText().toString());


                //Master정보부터 partyContainer 에 넣는다
                LinearLayout party_confirmContainer = (LinearLayout) promptView.findViewById(R.id.party_confirmContainer);
                TextView FastCalculator_MasterNameTextView = (TextView) findViewById(R.id.FastCalculator_MasterNameTextView);
                EditText FastCalculator_MasterMoneyEditText = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);


                LayoutInflater partylayoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View confirm_partyrow = partylayoutInflater.inflate(R.layout.confirm_partyrow, null);
                TextView FastCalculator_partyNameTextView = (TextView) confirm_partyrow.findViewById(R.id.confirm_partyNameView);
                FastCalculator_partyNameTextView.setText(FastCalculator_MasterNameTextView.getText().toString());

                TextView confirm_partyMoneyView = (TextView) confirm_partyrow.findViewById(R.id.confirm_partyMoneyView);
                confirm_partyMoneyView.setText(FastCalculator_MasterMoneyEditText.getText().toString());
                party_confirmContainer.addView(confirm_partyrow);

                //그 후에 partyinfo 를 넣어준다.

                LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
                int partycounter = FastCalculator_PartyContainer.getChildCount();
                for (int i = 0; i < partycounter; i++) {
                    LinearLayout partyRelativeLayout = (LinearLayout) FastCalculator_PartyContainer.getChildAt(i);
                    TextView PartyTextView = (TextView) partyRelativeLayout.findViewById(R.id.FastCalculator_partyNameTextView);
                    String partyNameString = PartyTextView.getText().toString();

                    EditText PartyMoneyView = (EditText) partyRelativeLayout.findViewById(R.id.FastCalculator_partyMoneyEditView);
                    String partyMoneyString = PartyMoneyView.getText().toString();
                    //숫자 입력칸 null 방지,
                    if (partyMoneyString.equals("")) {
                        partyMoneyString = "0";
                    }

                    //inflator 를 통해서 partyrow를  붙여준다.
                    partylayoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    confirm_partyrow = partylayoutInflater.inflate(R.layout.confirm_partyrow, null);

                    FastCalculator_partyNameTextView = (TextView) confirm_partyrow.findViewById(R.id.confirm_partyNameView);
                    FastCalculator_partyNameTextView.setText(partyNameString);

                    confirm_partyMoneyView = (TextView) confirm_partyrow.findViewById(R.id.confirm_partyMoneyView);
                    confirm_partyMoneyView.setText(partyMoneyString);


                    party_confirmContainer.addView(confirm_partyrow);


                }


                //분배총합 구하고 popup창에 띄우기

                TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
                TextView lastconfirmDivideTotalMoney = (TextView) promptView.findViewById(R.id.lastconfirmDivideTotalMoney);
                lastconfirmDivideTotalMoney.setText(FastCalculator_partyMoneyTotal.getText().toString());


                //입금은행 구하기
                EditText lastconfirmBankName = (EditText) promptView.findViewById(R.id.lastconfirmBankName);
                lastconfirmBankName.setText(masterInfo.getMasterBankName());

                EditText lastconfirmBankNum = (EditText) promptView.findViewById(R.id.lastconfirmBankNum);
                lastconfirmBankNum.setText(masterInfo.getMasterBankNum());

                //popup창에 폰트 설정하기
                TextView lastconfirm_textview = (TextView)promptView.findViewById(R.id.lastconfirm_textview);
                lastconfirm_textview.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));
                TextView lastconfirm_helpmessage = (TextView) promptView.findViewById(R.id.lastconfirm_helpmessage);
                lastconfirm_helpmessage.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Pen));

                final String lastBankName = lastconfirmBankName.getText().toString();
                final String lastBankNum = lastconfirmBankNum.getText().toString();
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("보내기", new DialogInterface.OnClickListener() {
                            //카카오톡으로 실제로 보낸다.
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(log, "보내기 버튼 클릭");
                                //카카오톡으로 보내는 코드
                                try {
                                    final KakaoLink kakaoLink = KakaoLink.getKakaoLink(FastCalculator_Context);
                                    final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

                                    String sendingText = "";
                                    StringBuilder sb = new StringBuilder("◐ GAB 더치페이 방◑\n");
                                    EditText FastCalculator_roomname_edit = (EditText) findViewById(R.id.FastCalculator_roomname_edit);
                                    sb.append("1. 방 이름 \n");
                                    sb.append(FastCalculator_roomname_edit.getText().toString() + "\n\n");

                                    Button FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
                                    sb.append("2. 방 날짜\n");
                                    sb.append(FastCalculator_room_date_selector.getText().toString().replaceAll("-", "").toString() + "\n\n");

                                    LinearLayout FastCalculator_dutchPayItemContainer = (LinearLayout) findViewById(R.id.FastCalculator_dutchPayItemContainer);
                                    sb.append("3. 더치페이 항목\n");
                                    int itemCounter = FastCalculator_dutchPayItemContainer.getChildCount();
                                    for (int i = 0; i < itemCounter; i++) {
                                        LinearLayout newItemLayout = (LinearLayout) FastCalculator_dutchPayItemContainer.getChildAt(i);
                                        if (newItemLayout.getTag() == null) {
                                            Log.d(log, "getTag is null");
                                        } else {
                                            if (newItemLayout.getTag().equals("newItemTAG")) {
                                                TextView itemNameTextView = (TextView) newItemLayout.findViewById(R.id.itemNameText);
                                                String newitemName_String = itemNameTextView.getText().toString();
                                                EditText itemPriceView = (EditText) newItemLayout.findViewById(R.id.itemPriceEdit);
                                                //price는 3자리마다 ,가 있으니 모든 콤마를 없애준후에 넣어준다.

                                                String newitemPrice_String = itemPriceView.getText().toString();
                                                NumberPicker itemNumberPicker = (NumberPicker) newItemLayout.findViewById(R.id.numberPicker);
                                                int itemNumber_int = itemNumberPicker.getValue();
                                                Log.d(log, "newitemName_String : " + newitemName_String);
                                                Log.d(log, "newitemPrice_String : " + newitemPrice_String);
                                                Log.d(log, "new itemNumber_int :" + itemNumber_int);
                                                sb.append(newitemName_String + " - ");
                                                sb.append(newitemPrice_String + "원 * " + itemNumber_int + "개\n");
                                            }

                                        }

                                    }

                                    sb.append("\n4. 더치페이 항목 총액\n");
                                    TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
                                    sb.append(FastCalculator_itemPriceTotal.getText().toString() + "원\n\n");

                                    sb.append("5. 더치페이 총 인원\n");
                                    EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
                                    sb.append(FastCalculator_totalPartyNum.getText().toString() + "명\n\n");


                                    sb.append("6. 상세 금액\n");
                                    //    Master 는 자동으로 무조건 추가 해준다
                                    TextView FastCalculator_MasterNameTextView = (TextView) findViewById(R.id.FastCalculator_MasterNameTextView);
                                    EditText FastCalculator_MasterMoneyEditText = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
                                    sb.append(FastCalculator_MasterNameTextView.getText().toString() + "-" + FastCalculator_MasterMoneyEditText.getText().toString() + "원\n");


                                    LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
                                    int partycounter = FastCalculator_PartyContainer.getChildCount();
                                    for (int i = 0; i < partycounter; i++) {
                                        LinearLayout partyRelativeLayout = (LinearLayout) FastCalculator_PartyContainer.getChildAt(i);
                                        TextView PartyTextView = (TextView) partyRelativeLayout.findViewById(R.id.FastCalculator_partyNameTextView);
                                        String partyNameString = PartyTextView.getText().toString();

                                        EditText PartyMoneyView = (EditText) partyRelativeLayout.findViewById(R.id.FastCalculator_partyMoneyEditView);
                                        String partyMoneyString = PartyMoneyView.getText().toString();
                                        //숫자 입력칸 null 방지,
                                        if (partyMoneyString.equals("")) {
                                            partyMoneyString = "0";
                                        }

                                        sb.append(partyNameString + "-" + partyMoneyString + "원\n");
                                    }


                                    TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
                                    sb.append("\n7. 참가원 총 금액\n");
                                    sb.append(FastCalculator_partyMoneyTotal.getText().toString() + "원\n\n");

                                    sb.append("8. 입금 은행\n");
                                    sb.append(lastBankName + "\n\n");

                                    sb.append("9. 입금 계좌\n");
                                    sb.append(lastBankNum + "\n\n");

                                    kakaoTalkLinkMessageBuilder.addText(sb.toString());
                                    kakaoTalkLinkMessageBuilder.addAppButton("앱으로 이동", new AppActionBuilder().setUrl("https://market.android.com/details?id=com.innovest.lcd.gab").build());
                                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, FastCalculator_Context);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Log.d(log, "취소 버튼 클릭");
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                alert.show();


//                try {
//                    final KakaoLink kakaoLink = KakaoLink.getKakaoLink(FastCalculator_Context);
//                    final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
//
//                    String sendingText = "";
//                    StringBuilder sb = new StringBuilder("◐ GAB 더치페이 방◑\n");
//                    EditText FastCalculator_roomname_edit = (EditText) findViewById(R.id.FastCalculator_roomname_edit);
//                    sb.append("1. 방 이름 \n");
//                    sb.append(FastCalculator_roomname_edit.getText().toString() + "\n\n");
//
//                    Button FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
//                    sb.append("2. 방 날짜\n");
//                    sb.append(FastCalculator_room_date_selector.getText().toString().replaceAll("-","").toString() + "\n\n");
//
//                    LinearLayout FastCalculator_dutchPayItemContainer = (LinearLayout) findViewById(R.id.FastCalculator_dutchPayItemContainer);
//                    sb.append("3. 더치페이 항목\n");
//                    int itemCounter = FastCalculator_dutchPayItemContainer.getChildCount();
//                    for (int i = 0; i < itemCounter; i++) {
//                        LinearLayout newItemLayout = (LinearLayout) FastCalculator_dutchPayItemContainer.getChildAt(i);
//                        if (newItemLayout.getTag() == null) {
//                            Log.d(log, "getTag is null");
//                        } else {
//                            if (newItemLayout.getTag().equals("newItemTAG")) {
//                                TextView itemNameTextView = (TextView) newItemLayout.findViewById(R.id.itemNameText);
//                                String newitemName_String = itemNameTextView.getText().toString();
//                                EditText itemPriceView = (EditText) newItemLayout.findViewById(R.id.itemPriceEdit);
//                                //price는 3자리마다 ,가 있으니 모든 콤마를 없애준후에 넣어준다.
//
//                                String newitemPrice_String = itemPriceView.getText().toString();
//                                NumberPicker itemNumberPicker = (NumberPicker) newItemLayout.findViewById(R.id.numberPicker);
//                                int itemNumber_int = itemNumberPicker.getValue();
//                                Log.d(log, "newitemName_String : " + newitemName_String);
//                                Log.d(log, "newitemPrice_String : " + newitemPrice_String);
//                                Log.d(log, "new itemNumber_int :" + itemNumber_int);
//                                sb.append(newitemName_String + " - ");
//                                sb.append(newitemPrice_String + "원 * " + itemNumber_int + "개\n");
//                            }
//
//                        }
//
//                    }
//
//                    sb.append("\n4. 더치페이 항목 총액\n");
//                    TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
//                    sb.append(FastCalculator_itemPriceTotal.getText().toString() + "원\n\n");
//
//                    sb.append("5. 더치페이 총 인원\n");
//                    EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
//                    sb.append(FastCalculator_totalPartyNum.getText().toString() + "명\n\n");
//
//
//                    sb.append("6. 상세 금액\n");
//                //    Master 는 자동으로 무조건 추가 해준다
//                    TextView FastCalculator_MasterNameTextView = (TextView) findViewById(R.id.FastCalculator_MasterNameTextView);
//                    EditText FastCalculator_MasterMoneyEditText = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
//                    sb.append(FastCalculator_MasterNameTextView.getText().toString() + "-" + FastCalculator_MasterMoneyEditText.getText().toString() + "원\n");
//
//
//                    LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
//                    int partycounter = FastCalculator_PartyContainer.getChildCount();
//                    for (int i = 0; i < partycounter; i++) {
//                        LinearLayout partyRelativeLayout = (LinearLayout) FastCalculator_PartyContainer.getChildAt(i);
//                        TextView PartyTextView = (TextView) partyRelativeLayout.findViewById(R.id.FastCalculator_partyNameTextView);
//                        String partyNameString = PartyTextView.getText().toString();
//
//                        EditText PartyMoneyView = (EditText) partyRelativeLayout.findViewById(R.id.FastCalculator_partyMoneyEditView);
//                        String partyMoneyString = PartyMoneyView.getText().toString();
//                        //숫자 입력칸 null 방지,
//                        if (partyMoneyString.equals("")) {
//                            partyMoneyString = "0";
//                        }
//
//                        sb.append(partyNameString + "-" + partyMoneyString + "원\n");
//                    }
//
//
//                    TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
//                    sb.append("\n7. 참가원 총 금액\n");
//                    sb.append(FastCalculator_partyMoneyTotal.getText().toString() + "원\n\n");
//
//                    sb.append("8. 입금 은행\n");
//                    sb.append(masterInfo.getMasterBankName() + "\n\n");
//
//                    sb.append("9. 입금 계좌\n");
//                    sb.append(masterInfo.getMasterBankNum() + "\n\n");
//
//                    kakaoTalkLinkMessageBuilder.addText(sb.toString());
//                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, FastCalculator_Context);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
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
            TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
            FastCalculator_partyMoneyTotal.setText(String.valueOf(totalPartyMoney));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public int calculatePartyTotalMoney() {
        int totalItemPrice = 0;
        LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
        int partyCounter = FastCalculator_PartyContainer.getChildCount();
        Log.d(log, "this is partyContainer : " + partyCounter);
        for (int i = 0; i < partyCounter; i++) {
            //각각의 itemcontainer
            LinearLayout newPartyLayout = (LinearLayout) FastCalculator_PartyContainer.getChildAt(i);
            Log.d(log, "newPartyLayout :" + newPartyLayout);
            if (newPartyLayout.getTag() == null) {
                Log.d(log, "getTag is null");
            } else {
                if (newPartyLayout.getTag().equals("FastCalculator_newPartyTAG")) {
                    EditText partyMoney = (EditText) newPartyLayout.findViewById(R.id.FastCalculator_partyMoneyEditView);
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

        EditText FastCalculator_MasterMoneyEditText = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
        if (FastCalculator_MasterMoneyEditText.getText().toString().trim().length() == 0) {

        } else {
            totalItemPrice += Integer.parseInt(FastCalculator_MasterMoneyEditText.getText().toString().replaceAll(",", ""));
        }

        return totalItemPrice;
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
                    final TextView textOut = (TextView) v;
                    // get prompts.xml view
                    LayoutInflater layoutInflater = LayoutInflater.from(FastCalculatorMainPage.this);
                    View promptView = layoutInflater.inflate(R.layout.itemname_change_popup, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FastCalculatorMainPage.this);
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
                    TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
                    FastCalculator_itemPriceTotal.setText(String.valueOf(totalItemPrice));

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
                    TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
                    FastCalculator_itemPriceTotal.setText(String.valueOf(totalItemPrice));


                }
            });
            LinearLayout FastCalculator_dutchPayItemContainer = (LinearLayout) findViewById(R.id.FastCalculator_dutchPayItemContainer);
            FastCalculator_dutchPayItemContainer.addView(addView);


        }
    }


    //item의 고유 이름에 따라서 item의 이름을 반환해주는 함수
    private String findImageNameByTag(int image_tag) {
        String imageName = "";
        switch (image_tag) {
            case R.drawable.firsttime:
                imageName = "1차";
                break;

            case R.drawable.secondtime:
                imageName = "2차";
                break;

            case R.drawable.thirdtime:
                imageName = "3차";
                break;

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
            TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
            FastCalculator_itemPriceTotal.setText(String.valueOf(totalItemPrice));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    //itemContainer안의 모든 totalPrice를 구한다.return total item price (int) //itemContainer price에 있는 모든 콤마를 제외하고 계산해준다.
    private int calculateItemTotalPrice() {
        int totalItemPrice = 0;
        LinearLayout FastCalculator_dutchPayItemContainer = (LinearLayout) findViewById(R.id.FastCalculator_dutchPayItemContainer);
        int itemCounter = FastCalculator_dutchPayItemContainer.getChildCount();
        Log.d(log, "this is itemCounter : " + itemCounter);
        for (int i = 0; i < itemCounter; i++) {
            //각각의 itemcontainer
            LinearLayout newItemLayout = (LinearLayout) FastCalculator_dutchPayItemContainer.getChildAt(i);
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


    //for date picker DIALOG 가 DATEPIICKER 일시에 불러오기
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
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

    //for date picker , 날짜 변경시 UPDATE 한다.
    private void updateDisplay() {
        Button FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
        if (mMonth < 9) {
            if (mDay < 9) {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append("0")
                        .append(mMonth + 1).append("-")
                        .append("0")
                        .append(mDay));
            } else {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append("0")
                        .append(mMonth + 1).append("-")
                        .append(mDay));
            }

        } else {
            if (mDay < 9) {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append("0")
                        .append(mDay));
            } else {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay));

            }
        }

    }

    private class FastCalculator_PartyTotalNum_Watcher implements TextWatcher {
        int partyTotalNum;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //일단 모든 partyContainer안에 잇는 것 삭제
            LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
            clearAllChildView(FastCalculator_PartyContainer);

            Log.d(log, "s :" + s);



            if (s.toString().equals("")) {
                partyTotalNum = 0;
            }

            else if(Integer.valueOf(s.toString())>100){
                EditText FastCalculator_totalPartyNum = (EditText)findViewById(R.id.FastCalculator_totalPartyNum);
                FastCalculator_totalPartyNum.setText("");
                partyTotalNum = 0;
            }


            else {
                //partyTotalNum 이 숫자이라면
                partyTotalNum = Integer.valueOf(s.toString());
            }

            for (int i = 0; i < partyTotalNum - 1; i++) {
                final String FriendName = "참가자" + (i + 2);
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addPartyView = layoutInflater.inflate(R.layout.fastcalculator_partyrow, null);
                TextView partyName = (TextView) addPartyView.findViewById(R.id.FastCalculator_partyNameTextView);
                partyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final TextView textOut = (TextView) v;
                        // get prompts.xml view
                        LayoutInflater layoutInflater = LayoutInflater.from(FastCalculatorMainPage.this);
                        View promptView = layoutInflater.inflate(R.layout.itemname_change_popup, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FastCalculatorMainPage.this);
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
                partyName.setText(FriendName);

                EditText partyMoney = (EditText) addPartyView.findViewById(R.id.FastCalculator_partyMoneyEditView);
                partyMoney.addTextChangedListener(new PartyTotalPriceTextWatcher());
                partyMoney.addTextChangedListener(new MoneyUnitListener_Edit(partyMoney));

                Button removePartyBT = (Button) addPartyView.findViewById(R.id.FastCalculator_partyRemoved);
                removePartyBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
                        FastCalculator_PartyContainer.removeView(addPartyView);
                        int totalPartyMoney = 0;
                        totalPartyMoney = calculatePartyTotalMoney();
                        Log.d(log, "totalPartyMoney :" + totalPartyMoney);
                        TextView FastCalculator_partyMoneyTotal = (TextView) findViewById(R.id.FastCalculator_partyMoneyTotal);
                        FastCalculator_partyMoneyTotal.setText(String.valueOf(totalPartyMoney));

                        //총인원 수 변동해주기
                        EditText FastCalculator_totalPartyNum = (EditText) findViewById(R.id.FastCalculator_totalPartyNum);
                        if (FastCalculator_totalPartyNum.getText().toString().equals("")) {

                        } else {
                            int originaltotalNum = Integer.valueOf(FastCalculator_totalPartyNum.getText().toString());
                            int newtotalNum = originaltotalNum - 1;
                            FastCalculator_totalPartyNum.setText(String.valueOf(newtotalNum));

                        }

                        //party total 금액 변동해주기
                        int totalItemPrice = 0;
                        totalItemPrice = calculateItemTotalPrice();
                        Log.d(log, "totalPrice :" + totalItemPrice);
                        TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
                        FastCalculator_itemPriceTotal.setText(String.valueOf(totalItemPrice));


                    }
                });

                FastCalculator_PartyContainer.addView(addPartyView);

            }


            //itemContainer에 있는 모든 총합을 구한다.
            int totalItemPrice = calculateItemTotalPrice();
            FastCalculator_PartyContainer = (LinearLayout) findViewById(R.id.FastCalculator_PartyContainer);
            int partyCounter = 1 + FastCalculator_PartyContainer.getChildCount(); //1은 master //나머지는 party원

            int DivideMoneyToParty = ((totalItemPrice / partyCounter) + 9) / 10 * 10;
            int DivideMoneyToMaster = totalItemPrice - (DivideMoneyToParty * (partyCounter - 1));
            //party원들에게 분배될 금액 표시
            Log.d(log, "partyCounter is :" + partyCounter);
            if (partyCounter > 1) {
                for (int i = 0; i < FastCalculator_PartyContainer.getChildCount(); i++) {
                    LinearLayout partyLayout = (LinearLayout) FastCalculator_PartyContainer.getChildAt(i);
                    EditText partyMoney = (EditText) partyLayout.findViewById(R.id.FastCalculator_partyMoneyEditView);
                    partyMoney.setText(String.valueOf(DivideMoneyToParty));

                }
            }
            //방장에게 분배될 금액 표시
            EditText FastCalculator_MasterMoneyEditText = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
            FastCalculator_MasterMoneyEditText.setText(String.valueOf(DivideMoneyToMaster));


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
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

    private void setOptionItemTagAndListener() {

        ImageView firsttime = (ImageView) findViewById(R.id.firsttime);
        firsttime.setTag(R.drawable.firsttime);
        firsttime.setOnTouchListener(new ImageViewTouchListener());
        firsttime.setOnClickListener(new ItemClickAddListenerSRC(firsttime));

        ImageView secondtime = (ImageView) findViewById(R.id.secondtime);
        secondtime.setTag(R.drawable.secondtime);
        secondtime.setOnTouchListener(new ImageViewTouchListener());
        secondtime.setOnClickListener(new ItemClickAddListenerSRC(secondtime));


        ImageView thirdtime = (ImageView) findViewById(R.id.thirdtime);
        thirdtime.setTag(R.drawable.thirdtime);
        thirdtime.setOnTouchListener(new ImageViewTouchListener());
        thirdtime.setOnClickListener(new ItemClickAddListenerSRC(thirdtime));

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
}
