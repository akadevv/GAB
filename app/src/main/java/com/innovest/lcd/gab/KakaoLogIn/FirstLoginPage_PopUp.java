package com.innovest.lcd.gab.KakaoLogIn;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.innovest.lcd.gab.ACCESS_TO_DB.Insert_UserInfo_DB;
import com.innovest.lcd.gab.MasterInfo.MasterInfo;
import com.innovest.lcd.gab.R;

/**
 * Created by Administrator on 2016-02-26.
 */
public class FirstLoginPage_PopUp extends Dialog {
    private MasterInfo masterinfo = MasterInfo.getMasterInfo();
    private Insert_UserInfo_DB insertUserInfo;
    private String GAB_Nanum_Bold = "NanumGothicBold.ttf";
    private String GAB_Nanum_Pen = "NanumPen.ttf";
    private String GAB_Nanum_ExtraBold = "NanumGothicExtraBold.ttf";
    String username,userphone,userbankname,userbanknum;
    private Context thisContext;
    private String KakaoId;
    FirstLoginPopupDialogListener popupListener;

    public FirstLoginPopupDialogListener getPopupListener() {
        return popupListener;
    }

    public void setPopupListener(FirstLoginPopupDialogListener popupListener) {
        this.popupListener = popupListener;
    }

    public FirstLoginPage_PopUp(Context context,String username,String userPhone,String userBankName,String userBanknum,String KakaoId) {
        super(context);
        thisContext = context;
        this.username = username;
        this.userphone = userPhone;
        this.userbankname=userBankName;
        this.userbanknum=userBanknum;
        this.KakaoId = KakaoId;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.first_login_popup);



        // 폰트 설정
        TextView title_firstloginPopup = (TextView)findViewById(R.id.title_firstloginPopup);
        title_firstloginPopup.setTypeface(Typeface.createFromAsset(thisContext.getAssets(), GAB_Nanum_ExtraBold));

        TextView textview1_firstloginPopup = (TextView) findViewById(R.id.textview1_firstloginPopup);
        textview1_firstloginPopup.setTypeface(Typeface.createFromAsset(thisContext.getAssets(), GAB_Nanum_Bold));

        TextView textview2_firstloginPopup = (TextView) findViewById(R.id.textview2_firstloginPopup);
        textview2_firstloginPopup.setTypeface(Typeface.createFromAsset(thisContext.getAssets(), GAB_Nanum_Bold));



        TextView userName = (TextView)findViewById(R.id.userName);
        userName.setText(username);
        //밑줄 설정
        userName.setPaintFlags(userName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView userPhoneNum = (TextView)findViewById(R.id.userPhoneNum);
        userPhoneNum.setText(userphone);
        userPhoneNum.setPaintFlags(userPhoneNum.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView userBankName = (TextView)findViewById(R.id.userBankName);
        userBankName.setText(userbankname);
        userBankName.setPaintFlags(userBankName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView userBankNum = (TextView)findViewById(R.id.userBankNum);
        userBankNum.setText(userbanknum);
        userBankNum.setPaintFlags(userBankNum.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Button yesButton_popup = (Button)findViewById(R.id.yesButton_popup);
        yesButton_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupListener.userSelectedAValue("success");
                dismiss();

            }
        });


        Button noButton_popup = (Button)findViewById(R.id.noButton_popup);
        noButton_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupListener.userSelectedAValue("cancel");
             dismiss();

            }
        });



    }


}
