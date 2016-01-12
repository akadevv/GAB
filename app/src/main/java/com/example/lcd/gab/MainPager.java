package com.example.lcd.gab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by LCD on 2016-01-11.
 */
public class MainPager extends Activity {
    private final int MAX_PAGE = 3;
    Fragment cur_fragement = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_pager);



    }
}
