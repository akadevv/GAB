package com.example.lcd.gab.FastCalculator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.CommonListener.ImageViewTouchListener;
import com.example.lcd.gab.CommonListener.MoneyUnitListener_Edit;
import com.example.lcd.gab.CommonListener.MoneyUnitListener_Text;
import com.example.lcd.gab.MasterInfo.MasterInfo;
import com.example.lcd.gab.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-02-22.
 */
public class FastCalculatorMainPage extends Activity{
    private static MasterInfo masterInfo=MasterInfo.getMasterInfo();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fastcalculator_room_main);

        //폰트 설정
        TextView FastCalculator_textView1 = (TextView)findViewById(R.id.FastCalculator_textView1);
        FastCalculator_textView1.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        TextView FastCalculator_helpitemmessage =(TextView)findViewById(R.id.FastCalculator_helpitemmessage);
        FastCalculator_helpitemmessage.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Pen));

        Button FastCalculator_Kakao_sendButton = (Button)findViewById(R.id.FastCalculator_Kakao_sendButton);
        FastCalculator_Kakao_sendButton.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        //MasterContainer 의 정보를 넣는다 //로그인 시의 로그인 한 사람의 정보
       TextView FastCalculator_MasterNameText = (TextView) findViewById(R.id.FastCalculator_MasterNameTextView);
        FastCalculator_MasterNameText.setText(masterInfo.getMasterName()); //해당 부분은 본인의 이름 //로그인 시에 자동으로 설정
        EditText FastCalculator_MasterMoneyEdit = (EditText) findViewById(R.id.FastCalculator_MasterMoneyEditText);
        //MasterMoneyEdit에 TotalMoneyListener 추가
        FastCalculator_MasterMoneyEdit.addTextChangedListener(new PartyTotalPriceTextWatcher());
        FastCalculator_MasterMoneyEdit.addTextChangedListener(new MoneyUnitListener_Edit(FastCalculator_MasterMoneyEdit));

        Context FastCalculator_Context = this;
        Activity FastCalculator_Activity = (Activity)this;

        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        //방제목 자동으로 삽입
        EditText FastCalculator_roomname_edit = (EditText)findViewById(R.id.FastCalculator_roomname_edit);
        FastCalculator_roomname_edit.setText(mMonth + 1 + "월 " + mDay + "일 더치페이방");

        //pizza item image

        ImageView FastCalculator_pizza_img = (ImageView) findViewById(R.id.FastCalculator_pizza_img);
        FastCalculator_pizza_img.setTag("피자");
        FastCalculator_pizza_img.setOnTouchListener(new ImageViewTouchListener());
        FastCalculator_pizza_img.setOnClickListener(new ItemClickAddListener(FastCalculator_pizza_img));


        //itemPriceTotalTextView
        TextView FastCalculator_itemPriceTotal = (TextView) findViewById(R.id.FastCalculator_itemPriceTotal);
        FastCalculator_itemPriceTotal.addTextChangedListener(new MoneyUnitListener_Text(FastCalculator_itemPriceTotal));



        //for date picker 날짜 선택 방
        Button FastCalculator_room_date_selector = (Button) findViewById(R.id.FastCalculator_room_date_selector);
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

        FastCalculator_room_date_selector.setText(String.valueOf(mYear) + mMonth_String + mDay_String);
        //date picker Click 시에
        FastCalculator_room_date_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(log, "this is datepickButton Clicked()");
                showDialog(DATE_DIALOG_ID);
            }
        });


        //더치페이 방원 number 적을 시에 TextWatcher 설정
        EditText FastCalculator_totalPartyNum = (EditText)findViewById(R.id.FastCalculator_totalPartyNum);
        FastCalculator_totalPartyNum.addTextChangedListener(new FastCalculator_PartyTotalNum_Watcher());


        Button FastCalculator_DutchCalculateBT = (Button)findViewById(R.id.FastCalculator_DutchCalculateBT);
        //버튼 클릭시 나 + party 원들의 수만큼 나누어줌.
        FastCalculator_DutchCalculateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemContainer에 있는 모든 총합을 구한다.
                int totalItemPrice = calculateItemTotalPrice();
                LinearLayout FastCalculator_PartyContainer = (LinearLayout)findViewById(R.id.FastCalculator_PartyContainer);
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
                EditText FastCalculator_MasterMoneyEditText = (EditText)findViewById(R.id.FastCalculator_MasterMoneyEditText);
                FastCalculator_MasterMoneyEditText.setText(String.valueOf(DivideMoneyToMaster));
            }
        });

        //partyTotalTextView 3자리씩 끊어서 보여주기
        TextView FastCalculator_partyMoneyTotal = (TextView)findViewById(R.id.FastCalculator_partyMoneyTotal);
        FastCalculator_partyMoneyTotal.addTextChangedListener(new MoneyUnitListener_Text(FastCalculator_partyMoneyTotal));

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
            TextView FastCalculator_partyMoneyTotal = (TextView)findViewById(R.id.FastCalculator_partyMoneyTotal);
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

        EditText FastCalculator_MasterMoneyEditText = (EditText)findViewById(R.id.FastCalculator_MasterMoneyEditText);
        if (FastCalculator_MasterMoneyEditText.getText().toString().trim().length() == 0) {

        } else {
            totalItemPrice += Integer.parseInt(FastCalculator_MasterMoneyEditText.getText().toString().replaceAll(",", ""));
        }

        return totalItemPrice;
    }


    //아이템 클릭시에 추가하는 Listener
    private class ItemClickAddListener implements View.OnClickListener{
        ImageView clickedImageView;

        public ItemClickAddListener(ImageView clickedImageView) {
            this.clickedImageView = clickedImageView;
        }

        @Override
        public void onClick(View v) {

            Drawable clickedImageViewDrawable = clickedImageView.getDrawable();
            Log.d(log, "ImageView = " + clickedImageView);
            Log.d(log, "ImageViewDrawable = " + clickedImageViewDrawable);


            // drop 시에 inflater를 이용해서 itemrow를 넣는다.
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.itemrow, null);
            ImageView itemRowImage = (ImageView) addView.findViewById(R.id.ItemImageView);

            itemRowImage.setBackground(clickedImageViewDrawable);  //setBackground 작동함 (API버전 문제임)
            TextView textOut = (TextView) addView.findViewById(R.id.itemNameText);
            EditText itemprice = (EditText) addView.findViewById(R.id.itemPriceEdit);
            itemprice.addTextChangedListener(new MoneyUnitListener_Edit(itemprice)); //3자리씩 끊어서 보여주는 listener
            itemprice.addTextChangedListener(new itemTotalPriceTextWatcher());       //totalPrice를 변경해주는 listener
            textOut.setText(clickedImageView.getTag().toString());
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
            LinearLayout FastCalculator_dutchPayItemContainer = (LinearLayout)findViewById(R.id.FastCalculator_dutchPayItemContainer);
            FastCalculator_dutchPayItemContainer.addView(addView);


        }
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
            TextView FastCalculator_itemPriceTotal = (TextView)findViewById(R.id.FastCalculator_itemPriceTotal);
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
                        .append(mYear).append("")
                        .append("0")
                        .append(mMonth + 1).append("")
                        .append("0")
                        .append(mDay));
            } else {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("")
                        .append("0")
                        .append(mMonth + 1).append("")
                        .append(mDay));
            }

        } else {
            if (mDay < 9) {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("")
                        .append(mMonth + 1).append("")
                        .append("0")
                        .append(mDay));
            } else {
                FastCalculator_room_date_selector.setText(new StringBuilder()
                        .append(mYear).append("")
                        .append(mMonth + 1).append("")
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

            Log.d(log,"s :"+s);

            if(s.toString().equals("")){
                partyTotalNum = 0;
            }else{
                //partyTotalNum 이 숫자이라면
                partyTotalNum = Integer.valueOf(s.toString());
            }

            for (int i = 0; i < partyTotalNum-1; i++) {
                final String FriendName = "참가자"+(i+1);
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addPartyView = layoutInflater.inflate(R.layout.fastcalculator_partyrow, null);
                TextView partyName = (TextView) addPartyView.findViewById(R.id.FastCalculator_partyNameTextView);
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
                        TextView FastCalculator_partyMoneyTotal = (TextView)findViewById(R.id.FastCalculator_partyMoneyTotal);
                        FastCalculator_partyMoneyTotal.setText(String.valueOf(totalPartyMoney));
                    }
                });

                FastCalculator_PartyContainer.addView(addPartyView);

            }


            //itemContainer에 있는 모든 총합을 구한다.
            int totalItemPrice = calculateItemTotalPrice();
            FastCalculator_PartyContainer = (LinearLayout)findViewById(R.id.FastCalculator_PartyContainer);
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
            EditText FastCalculator_MasterMoneyEditText = (EditText)findViewById(R.id.FastCalculator_MasterMoneyEditText);
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

}
