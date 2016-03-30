package com.innovest.lcd.gab.KakaoLogIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.innovest.lcd.gab.ACCESS_TO_DB.Insert_UserInfo_DB;
import com.innovest.lcd.gab.MasterInfo.MasterInfo;
import com.innovest.lcd.gab.R;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-02-15.
 */
public class FirstLoginPage extends Activity {
    private MasterInfo masterinfo = MasterInfo.getMasterInfo();
    private String log = "jjunest";
    private Insert_UserInfo_DB insertUserInfo;
    String KakaoId;
    String username;
    String userPhone;
    String userBankName;
    String userBanknum;
    private Context thisContext;
    private String GAB_Nanum_Bold = "NanumGothicBold.ttf";
    private String GAB_Nanum_Pen = "NanumPen.ttf";
    private String GAB_Nanum_ExtraBold = "NanumGothicExtraBold.ttf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_getinfomation);
        thisContext = this;
        Intent receivedIntent = getIntent();
        KakaoId = receivedIntent.getStringExtra("kakao_Userid");


        //폰트 설정
        TextView title_firstloginPage = (TextView) findViewById(R.id.title_firstloginPage);
        title_firstloginPage.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        TextView message1_firstloginPage = (TextView) findViewById(R.id.message1_firstloginPage);
        message1_firstloginPage.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_Bold));

        Button firstLoginBT = (Button) findViewById(R.id.firstLoginBT);
        firstLoginBT.setTypeface(Typeface.createFromAsset(getAssets(), GAB_Nanum_ExtraBold));

        //내 전화번호 받아오기
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telManager.getLine1Number();
        System.out.println("this is phonenumber :" + phoneNumber);
        EditText userPhone_EditText = (EditText) findViewById(R.id.userPhoneNum);

        //만약 내전화번호가 있으면 미리 설정해준다.
        if (phoneNumber != null) {
            System.out.println("phonenumber exist");
            userPhone_EditText.setText(phoneNumber.replaceAll("-", ""));
        }

        Button newRegisterBT = (Button) findViewById(R.id.firstLoginBT);
        newRegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username_EditText = (EditText) findViewById(R.id.userName);
                EditText userPhone_EditText = (EditText) findViewById(R.id.userPhoneNum);
                EditText userBankName_EditText = (EditText) findViewById(R.id.userBankName);
                EditText userBankNum_EditText = (EditText) findViewById(R.id.userBankNum);

                username = username_EditText.getText().toString();
                userPhone = userPhone_EditText.getText().toString();
                userBankName = userBankName_EditText.getText().toString();
                userBanknum = userBankNum_EditText.getText().toString();

                FirstLoginPage_PopUp popupDialog = new FirstLoginPage_PopUp(thisContext, username, userPhone, userBankName, userBanknum, KakaoId);

                popupDialog.setPopupListener(new FirstLoginPopupDialogListener() {
                    public void userSelectedAValue(String value) {
                        Log.d(log, "this is value from dialog = " + value);
                        //use value

                        if (value.equals("success")) {
                            //만약에 popUp 창에서 돌아온 응답이 success 라면, 집어넣는다
                            try {
                                String data = URLEncoder.encode("userName_in", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                                data += "&" + URLEncoder.encode("userId_in", "UTF-8") + "=" + URLEncoder.encode(KakaoId, "UTF-8");
                                data += "&" + URLEncoder.encode("userPhone_in", "UTF-8") + "=" + URLEncoder.encode(userPhone, "UTF-8");
                                data += "&" + URLEncoder.encode("userBankName_in", "UTF-8") + "=" + URLEncoder.encode(userBankName, "UTF-8");
                                data += "&" + URLEncoder.encode("userBanknum_in", "UTF-8") + "=" + URLEncoder.encode(userBanknum, "UTF-8");

                                new InsertUserInfoToDB().execute(data);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //앱에다 자기의 정보를 저장한다. MasterInfo 안에다 저장한다.
                            masterinfo.setMasterID(KakaoId);
                            masterinfo.setMasterName(username);
                            masterinfo.setMasterPhoneNum(userPhone);
                            masterinfo.setMasterBankName(userBankName);
                            masterinfo.setMasterBankNum(userBanknum);
                            setResult(RESULT_OK);

                            finish();

                        } else {


                        }
                    }

                });


                popupDialog.show();


//


            }
        });


    }


    private class InsertUserInfoToDB extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                //실제 userInfo를 넣는 link
                String PHPlink = "http://jjunest.cafe24.com/DB/insert_userInfo_into_registerDB.php";
                insertUserInfo = new Insert_UserInfo_DB();
                String UserInfo = (String) params[0];
                insertUserInfo.insert_userInfo_To_DB(UserInfo, PHPlink);

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

}
