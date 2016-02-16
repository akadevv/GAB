package com.example.lcd.gab.KakaoLogIn;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.lcd.gab.ACCESS_TO_DB.Check_FirstLogin;
import com.example.lcd.gab.ACCESS_TO_DB.Get_UserInfo_RegisterDB;
import com.example.lcd.gab.ACCESS_TO_DB.Insert_UserInfo_DB;
import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.MasterInfo.MasterInfo;
import com.example.lcd.gab.R;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-02-04.
 */
public class SignUpActivity extends Activity {

    Insert_UserInfo_DB insertUserId;
    Check_FirstLogin checkUserId;
    private String log = "jjunest";
    public static final int FirstLoginREQUEST_CODE = 2003;
    private MasterInfo masterinfo = MasterInfo.getMasterInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jjunest", "SampleSignUpActivity");

        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
//                    KakaoToast.makeToast(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }


            //로그인 성공시 , DB에 정보 저장 하기
            @Override
            public void onSuccess(UserProfile userProfile) {
                String log = "jjunest";
                Log.d(log, "in requestME()");
                Log.d(log, "UserProfile : " + userProfile);
                Log.d(log, "UserId :" + userProfile.getId());
                Log.d(log, "UserState : " + userProfile.getProperty("nickname"));
                Log.d(log, "Created : " + userProfile.getProperty("created"));
                Log.d(log, "nickName : " + userProfile.getNickname());
//                masterinfo.setMasterID(String.valueOf(userProfile.getId()));

                String userId = String.valueOf(userProfile.getId());

                boolean FirstLogin = false;
                try {
                    FirstLogin = new CheckFirstLogin().execute(userId).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //만약에 접속이 처음이라면? -> 이름 / 전화번호 / 은행이름(선택) / 계좌번호 (선택) 정보 입력 받는 화면 //FirstLoginPage에서 MasterInfo 설정
                if (FirstLogin) {
                    Log.d(log, "this is FirstLogin();");
                    Intent FirstLoginIntent = new Intent(getApplicationContext(), FirstLoginPage.class);
                    FirstLoginIntent.putExtra("kakao_Userid", String.valueOf(userProfile.getId()));
                    startActivityForResult(FirstLoginIntent, FirstLoginREQUEST_CODE);

                }
                //만약에 이미 DB에 아이디가 있으면? 1. DB에서 UserInfo를 받아와서 MasterInfo를 설정  2. MainActivity.java 바로 접속
                else {
                    Log.d(log, "this is NOT FirstLogin();");
                    new GetUserInfoFromDB().execute(String.valueOf(userProfile.getId()));
                    //정보를 받아올떄까지 잠시 멈추고 (추후에 progressbar로 만들자) 그 후에 MainActivity로 이동한다.
                   try{
                    Thread.sleep(1000);}catch(Exception e){
                       e.printStackTrace();
                   }
                    redirectMainActivity();
                }


            }

            @Override
            public void onNotSignedUp() {
                showSignup();
            }
        });
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LogInPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    //로그인 성공시 이동하는 페이지
    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    protected void showSignup() {
        setContentView(R.layout.layout_usermgmt_signup);
//        final ExtraUserPropertyLayout extraUserPropertyLayout = (ExtraUserPropertyLayout) findViewById(R.id.extra_user_property);
//        Button signupButton = (Button) findViewById(R.id.buttonSignup);
//        signupButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                requestSignUp(extraUserPropertyLayout.getProperties());
//            }
//        });
    }

    //for insertDBFull DATA using AsyncTask
    //첫 인수, doinbackground로 넘어가는 인수, 두 번째 인수, 그 중에 들어가는 인수, 세 번쨰 인수 결과값으로 끝나는 인수
    private class InsertUserIDToDB extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                //실제 userId를 넣는 link
                String PHPlink = "http://jjunest.cafe24.com/DB/insert_userid_into_registerDB.php";
                insertUserId = new Insert_UserInfo_DB();
                String newUserId = (String) params[0];
                Log.d(log, "InsertUserID to DB () userId=" + newUserId);
                insertUserId.insert_userid_To_DB(newUserId, PHPlink);

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

    private class CheckFirstLogin extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            boolean Firstlogin = false;
            try {

                //실제 userId를 넣는 link
                String PHPlink = "http://jjunest.cafe24.com/DB/check_firstLogin_registerDB.php";
                checkUserId = new Check_FirstLogin();
                String newUserId = (String) params[0];
                Firstlogin = checkUserId.check_FirstLogin(newUserId, PHPlink);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Firstlogin;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    private class GetUserInfoFromDB extends AsyncTask<String, String, String> {
        private MasterInfo masterinfo = MasterInfo.getMasterInfo();
        Get_UserInfo_RegisterDB GetUserInfo;
        String response = null;

        @Override
        protected String doInBackground(String... params) {
            //KakaoId 가 param으로 들어온다.
            try {
                //실제 userId를 넣는 link
                String PHPlink = "http://jjunest.cafe24.com/DB/get_userInfo_registerDB.php";
                GetUserInfo = new Get_UserInfo_RegisterDB();
                String UserId = (String) params[0];
                // 모든 응답
                response = GetUserInfo.getUserInfoFromRegisterDB(UserId, PHPlink);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            //masterInfo 정보 세팅해준다.
            try {
                JSONObject jsonObj = new JSONObject(aVoid);
                JSONArray UserInfoJson = jsonObj.getJSONArray("results");

                for(int i =0; i<UserInfoJson.length(); i++){
                    JSONObject c = UserInfoJson.getJSONObject(i);
                    String userid = c.getString("userid");
                    String userphonenum = c.getString("userphonenum");
                    String user_name = c.getString("user_name");
                    String bank_name = c.getString("bank_name");
                    String account_number = c.getString("account_number");

                    System.out.println("user id : "+userid);
                    System.out.println("user phonenum :"+userphonenum);
                    System.out.println("user name :"+user_name);
                    masterinfo.setMasterID(userid);
                    masterinfo.setMasterName(user_name);
                    masterinfo.setMasterPhoneNum(userphonenum);
                    masterinfo.setMasterBankName(bank_name);
                    masterinfo.setMasterBankNum(account_number);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(log, "this is onActivityResult 1.1 ");
        //firstLogin 페이지에서 돌아온 경우
        if (requestCode == FirstLoginREQUEST_CODE) {
            Log.d(log, "this is onActivityResult 1.2 ");
            Log.d(log, "this is resultCode : " + resultCode);
            if (resultCode == RESULT_OK) {
                Log.d(log, "this is onActivityResult Result_OK");
                redirectMainActivity();
                Log.d(log, "this is AfterRedirectMainActivity");
                finish();
            }

        }

    }
}
