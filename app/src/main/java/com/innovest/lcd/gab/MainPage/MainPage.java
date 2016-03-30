package com.innovest.lcd.gab.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovest.lcd.gab.BackPressCloseHandler;
import com.innovest.lcd.gab.FastCalculator.FastCalculatorMainPage;
import com.innovest.lcd.gab.PayRoom.PayRoomMakingPage;
import com.innovest.lcd.gab.R;
import com.innovest.lcd.gab.SettingOption.SettingOptionMain;
import com.innovest.lcd.gab.SoftKeyboardDectectorView;
import com.innovest.lcd.gab.TransferRoom.TransferRoomMain;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * Created by LCD on 2016-01-11.
 */
public class MainPage extends FragmentActivity {

    private boolean menuOnOff = false;
    private ViewPager viewPager;

    private RelativeLayout friendListPage;
    private ImageView friendListUnselected;
    private ImageView friendListSelected;

    private RelativeLayout roomListPage;
    private ImageView roomListUnselected;
    private ImageView roomListSelected;

    private RelativeLayout receivableListPage;
    private ImageView receivableListUnselected;
    private ImageView receivableListSelected;

    private RelativeLayout owedListPage;
    private ImageView owedListUnselected;
    private ImageView owedListSelected;

    private LinearLayout tabLayout;
    private Button menuButton;
    private RelativeLayout shadowLayout;
    private LinearLayout originalLayout;
    private TextView payRoomText;
    private TextView transferRoomText;
    private TextView fastCalculatorRoomText;
    private TextView setting_text;
    private Animation menuRotate;
    private Animation menuOrigin;
    private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


        friendListPage = (RelativeLayout) findViewById(R.id.friend_list_page);
        friendListUnselected = (ImageView) findViewById(R.id.friend_list_icon_unselected);
        friendListSelected = (ImageView) findViewById(R.id.friend_list_icon_selected);

        roomListPage = (RelativeLayout) findViewById(R.id.room_list_page);
        roomListUnselected = (ImageView) findViewById(R.id.room_list_icon_unselected);
        roomListSelected = (ImageView) findViewById(R.id.room_list_icon_selected);

        receivableListPage = (RelativeLayout) findViewById(R.id.receivable_list_page);
        receivableListUnselected = (ImageView) findViewById(R.id.receivable_list_icon_unselected);
        receivableListSelected = (ImageView) findViewById(R.id.receivable_list_icon_selected);

        owedListPage = (RelativeLayout) findViewById(R.id.owed_list_page);
        owedListUnselected = (ImageView) findViewById(R.id.owed_list_icon_unselected);
        owedListSelected = (ImageView) findViewById(R.id.owed_list_icon_selected);

        tabLayout = (LinearLayout) findViewById(R.id.tab_Layout);
        menuButton = (Button) findViewById(R.id.menu_anim_button);
        shadowLayout = (RelativeLayout) findViewById(R.id.shadow_layout);
        originalLayout = (LinearLayout) findViewById(R.id.original_layout);
        payRoomText = (TextView) findViewById(R.id.pay_room_text);
        transferRoomText = (TextView) findViewById(R.id.transfer_room_text);
        fastCalculatorRoomText = (TextView)findViewById(R.id.FastCalculator_room_text);
        setting_text = (TextView)findViewById(R.id.setting_text);

        menuRotate = AnimationUtils.loadAnimation(this, R.anim.menu_rotate);
        menuOrigin = AnimationUtils.loadAnimation(this, R.anim.menu_origin);



        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), tabLayout.getContext()));

        //만약에 pagerCurrent 라는 데이터가 설정되어 넘어온다면, viewPager의 setcurrentitem을 바꾸어준다.
        Intent intent = getIntent();
        if(intent!=null){
            if(intent.hasExtra("currentPager")) {

                int currentPage = intent.getExtras().getInt("currentPager");
                viewPager.setCurrentItem(currentPage);
            }

        }


        backPressCloseHandler = new BackPressCloseHandler(this);

        friendListPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);

                friendListUnselected.setVisibility(View.GONE);
                friendListSelected.setVisibility(View.VISIBLE);

                roomListUnselected.setVisibility(View.VISIBLE);
                roomListSelected.setVisibility(View.GONE);

                receivableListUnselected.setVisibility(View.VISIBLE);
                receivableListSelected.setVisibility(View.GONE);

                owedListUnselected.setVisibility(View.VISIBLE);
                owedListSelected.setVisibility(View.GONE);

            }
        });

            roomListPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);

                friendListUnselected.setVisibility(View.VISIBLE);
                friendListSelected.setVisibility(View.GONE);

                roomListUnselected.setVisibility(View.GONE);
                roomListSelected.setVisibility(View.VISIBLE);

                receivableListUnselected.setVisibility(View.VISIBLE);
                receivableListSelected.setVisibility(View.GONE);

                owedListUnselected.setVisibility(View.VISIBLE);
                owedListSelected.setVisibility(View.GONE);

            }
        });

        receivableListPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);

                friendListUnselected.setVisibility(View.VISIBLE);
                friendListSelected.setVisibility(View.GONE);

                roomListUnselected.setVisibility(View.VISIBLE);
                roomListSelected.setVisibility(View.GONE);

                receivableListUnselected.setVisibility(View.GONE);
                receivableListSelected.setVisibility(View.VISIBLE);

                owedListUnselected.setVisibility(View.VISIBLE);
                owedListSelected.setVisibility(View.GONE);
            }
        });

        owedListPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);

                friendListUnselected.setVisibility(View.VISIBLE);
                friendListSelected.setVisibility(View.GONE);

                roomListUnselected.setVisibility(View.VISIBLE);
                roomListSelected.setVisibility(View.GONE);

                receivableListUnselected.setVisibility(View.VISIBLE);
                receivableListSelected.setVisibility(View.GONE);

                owedListUnselected.setVisibility(View.GONE);
                owedListSelected.setVisibility(View.VISIBLE);
            }
        });

        UnderlinePageIndicator underlinePageIndicator = (UnderlinePageIndicator) findViewById(R.id.page_indicator);
        underlinePageIndicator.setViewPager(viewPager);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuOnOff) {
                    shadowLayout.bringToFront();
                    menuButton.bringToFront();
                    menuButton.startAnimation(menuRotate);

                    payRoomText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), PayRoomMakingPage.class);
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

                    fastCalculatorRoomText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), FastCalculatorMainPage.class);
                            startActivity(intent);
                        }
                    });

                    setting_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), SettingOptionMain.class);
                            startActivity(intent);

                        }
                    });

                    shadowLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            originalLayout.bringToFront();
                            menuButton.bringToFront();
                            menuButton.startAnimation(menuOrigin);
                            menuOnOff = false;
                        }
                    });



                    menuOnOff = true;
                } else {
                    originalLayout.bringToFront();
                    menuButton.bringToFront();
                    menuButton.startAnimation(menuOrigin);
                    menuOnOff = false;
                }
            }
        });

        final SoftKeyboardDectectorView softKeyboardDectector = new SoftKeyboardDectectorView(this);
        addContentView(softKeyboardDectector, new FrameLayout.LayoutParams(-1, -1));

        softKeyboardDectector.setOnShownKeyboard(new SoftKeyboardDectectorView.OnShownKeyboardListener() {
            @Override
            public void onShowSoftKeyboard() {
                menuButton.setVisibility(View.GONE);
            }
        });

        softKeyboardDectector.setOnHiddenKeyboard(new SoftKeyboardDectectorView.OnHiddenKeyboardListener() {
            @Override
            public void onHiddenSoftKeyboard() {
                menuButton.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(menuOnOff){
                    originalLayout.bringToFront();
                    menuButton.bringToFront();
                    menuButton.startAnimation(menuOrigin);
                    menuOnOff = false;
                    return true;
                }
        }
        return super.onKeyDown(keyCode, event);
    }

}
