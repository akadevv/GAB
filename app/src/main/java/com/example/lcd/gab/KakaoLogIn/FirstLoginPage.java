package com.example.lcd.gab.KakaoLogIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lcd.gab.ACCESS_TO_DB.Insert_UserInfo_DB;
import com.example.lcd.gab.MasterInfo.MasterInfo;
import com.example.lcd.gab.R;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-02-15.
 */
public class FirstLoginPage extends Activity {
    private MasterInfo masterinfo = MasterInfo.getMasterInfo();
    private String log = "jjunest";
    private Insert_UserInfo_DB insertUserInfo;
    String KakaoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_getinfomation);

        Intent receivedIntent = getIntent();
        KakaoId = receivedIntent.getStringExtra("kakao_Userid");

        //내 전화번호 받아오기
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telManager.getLine1Number();
        System.out.println("this is phonenumber :"+ phoneNumber);
        EditText userPhone_EditText = (EditText) findViewById(R.id.userPhoneNum);

        if(phoneNumber!=null){
            System.out.println("phonenumber exist");
            userPhone_EditText.setHint(phoneNumber);
        }

        Button newRegisterBT = (Button)findViewById(R.id.firstLoginBT);
        newRegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_EditText = (EditText) findViewById(R.id.userName);
                EditText userPhone_EditText = (EditText) findViewById(R.id.userPhoneNum);
                EditText userBankName_EditText = (EditText) findViewById(R.id.userBankName);
                EditText userBankNum_EditText = (EditText) findViewById(R.id.userBankNum);

                String username = username_EditText.getText().toString();
                String userPhone = userPhone_EditText.getText().toString();
                String userBankName = userBankName_EditText.getText().toString();
                String userBanknum = userBankNum_EditText.getText().toString();

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
