package com.example.lcd.gab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lcd.gab.KakaoLogIn.LogInPage;
import com.example.lcd.gab.MainPage.MainPager;
import com.example.lcd.gab.MasterInfo.MasterInfo;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

/**
 * Created by LCD on 2016-01-07.
 */
public class MainActivity extends Activity {

    private static MasterInfo masterInfo=MasterInfo.getMasterInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainPager.class);
                startActivity(intent);

            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.lcd.gab.jjunest.registerMain.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.lcd.gab.jjunest.test_getmyphonenumber.class);
                startActivity(intent);
            }
        });

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

    public static MasterInfo getMasterInfo(){
        return masterInfo;
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LogInPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }



}
