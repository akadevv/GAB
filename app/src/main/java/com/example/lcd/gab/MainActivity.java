package com.example.lcd.gab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lcd.gab.MainPage.MainPager;
import com.example.lcd.gab.MasterInfo.MasterInfo;

/**
 * Created by LCD on 2016-01-07.
 */
public class MainActivity extends Activity {

    private static MasterInfo masterInfo = new MasterInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterInfo.setUserId("lce6292");
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
    }

    public static MasterInfo getMasterInfo(){
        return masterInfo;
    }
}
