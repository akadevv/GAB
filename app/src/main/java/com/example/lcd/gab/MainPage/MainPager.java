package com.example.lcd.gab.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcd.gab.PayRoom.PayRoomMain;
import com.example.lcd.gab.R;
import com.example.lcd.gab.TransferRoom.TransferRoomMain;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * Created by LCD on 2016-01-11.
 */
public class MainPager extends FragmentActivity {

    private boolean menuOnOff = false;
    private boolean payRoom;
    private ViewPager viewPager;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button menuButton;
    private RelativeLayout shadowLayout;
    private RelativeLayout originalLayout;
    private TextView payRoomText;
    private TextView transferRoomText;
    private Animation menuRotate;
    private Animation menuOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        button1 = (Button) findViewById(R.id.B1);
        button2 = (Button) findViewById(R.id.B2);
        button3 = (Button) findViewById(R.id.B3);
        button4 = (Button) findViewById(R.id.B4);
        menuButton = (Button) findViewById(R.id.menu_anim_button);
        shadowLayout = (RelativeLayout) findViewById(R.id.shadow_layout);
        originalLayout = (RelativeLayout) findViewById(R.id.original_layout);
        payRoomText = (TextView) findViewById(R.id.pay_room_text);
        transferRoomText = (TextView) findViewById(R.id.transfer_room_text);
        menuRotate = AnimationUtils.loadAnimation(this, R.anim.menu_rotate);
        menuOrigin = AnimationUtils.loadAnimation(this, R.anim.menu_origin);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        UnderlinePageIndicator underlinePageIndicator = (UnderlinePageIndicator) findViewById(R.id.title);
        underlinePageIndicator.setViewPager(viewPager);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuOnOff) {
                    shadowLayout.bringToFront();
                    menuButton.bringToFront();

                    menuButton.setAnimation(menuRotate);

                    payRoomText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), PayRoomMain.class);
                            startActivity(intent);

                        }
                    });
                    transferRoomText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), TransferRoomMain.class);
                            startActivity(intent);
                        }
                    });

                    shadowLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            originalLayout.bringToFront();
                            menuButton.bringToFront();
                            menuOnOff = false;
                        }
                    });

                    menuOnOff = true;
                } else {
                    originalLayout.bringToFront();
                    menuButton.bringToFront();
                    menuButton.setAnimation(menuOrigin);

                    menuOnOff = false;
                }
            }
        });
    }
}
