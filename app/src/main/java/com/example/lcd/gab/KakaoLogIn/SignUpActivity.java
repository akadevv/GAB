package com.example.lcd.gab.KakaoLogIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lcd.gab.MainActivity;
import com.example.lcd.gab.MasterInfo.MasterInfo;
import com.example.lcd.gab.R;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * Created by Administrator on 2016-02-04.
 */
public class SignUpActivity extends Activity {

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

            @Override
            public void onSuccess(UserProfile userProfile) {
                String log = "jjunest";
                Log.d(log, "in requestME()");
                Log.d(log, "UserProfile : " + userProfile);
                Log.d(log,"UserId :" +userProfile.getId());
                Log.d(log,"UserState : " +userProfile.getProperty("nickname"));
                Log.d(log,"Created : " +userProfile.getProperty("created"));
                Log.d(log, "nickName : "+userProfile.getNickname());
//                masterinfo.setMasterID(String.valueOf(userProfile.getId()));
                masterinfo.setMasterID("lce6292");
                masterinfo.setMasterPhoneNum("010-3754-6292");
                redirectMainActivity();
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
    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    protected void showSignup(){
        setContentView(R.layout.layout_usermgmt_signup);
//        final ExtraUserPropertyLayout extraUserPropertyLayout = (ExtraUserPropertyLayout) findViewById(R.id.extra_user_property);
//        Button signupButton = (Button) findViewById(R.id.buttonSignup);
//        signupButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                requestSignUp(extraUserPropertyLayout.getProperties());
//            }
//        });
    }


}
