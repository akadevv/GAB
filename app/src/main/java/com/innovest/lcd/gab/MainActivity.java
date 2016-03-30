package com.innovest.lcd.gab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.innovest.lcd.gab.KakaoLogIn.LogInPage;
import com.innovest.lcd.gab.MainPage.MainPage;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

/**
 * Created by LCD on 2016-01-07.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);

            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.innovest.lcd.gab.jjunest.registerMain.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.innovest.lcd.gab.jjunest.test_getmyphonenumber.class);
                startActivity(intent);
            }
        });

        //로그아웃 버튼 클릭시, 카카오톡 로그아웃
        Button logoutBT = (Button)findViewById(R.id.logout_button);
        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        redirectLoginActivity();
                    }
                });

            }
        });

    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LogInPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }



}
