package com.innovest.lcd.gab.jjunest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.innovest.lcd.gab.R;


/**
 * Created by Administrator on 2016-01-11.
 */
public class test_getmyphonenumber extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_getmyphonenum);
        System.out.println("this is phonenumber start");
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telManager.getLine1Number();
        System.out.println("this is phonenumber :"+ phoneNumber);

    }
}
