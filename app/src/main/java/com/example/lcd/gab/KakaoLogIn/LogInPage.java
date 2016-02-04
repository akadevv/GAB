package com.example.lcd.gab.KakaoLogIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.example.lcd.gab.R;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2016-02-04.
 */
public class LogInPage extends AppCompatActivity {
    String log = "jjunest";
    Activity TopActivity;
    Context TopContext;
    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        TopActivity = LogInPage.this;
        TopContext = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakaologinlayout);


        getAppKeyHash();
        final KakaoLink kakaoLink;
        final LoginButton kakaoButton;
        callback = new SessionCallback();
        Log.d(log, " Before KAKAO SDK initialization ");
        Log.d(log, "KakaoSDK adapter : " + KakaoSDK.getAdapter());
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(new KakaoSDKAdapter());
        }
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }


    public class KakaoSDKAdapter extends KakaoAdapter {


        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                //현재 최 상단에 위치 하고 있는 TOP activity 를 return 해준다.
                public Activity getTopActivity() {
                    return TopActivity;
                }

                @Override
                public Context getApplicationContext() {
                    return TopContext;
                }
            };

        }
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }

            setContentView(R.layout.kakaologinlayout);
        }
    }

    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LogInPage.class);
        startActivity(intent);
        finish();
    }
}
