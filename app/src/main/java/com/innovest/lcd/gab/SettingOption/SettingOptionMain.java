package com.innovest.lcd.gab.SettingOption;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.innovest.lcd.gab.KakaoLogIn.LogInPage;
import com.innovest.lcd.gab.R;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

/**
 * Created by Administrator on 2016-02-27.
 */
public class SettingOptionMain extends Activity {

    private String GAB_Nanum_Bold = "NanumGothicBold.ttf";
    private String GAB_Nanum_Pen = "NanumPen.ttf";
    private String GAB_Nanum_ExtraBold = "NanumGothicExtraBold.ttf";
Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_option_main);

        // 정보 수정 버튼

        TextView change_masterInfo = (TextView) findViewById(R.id.change_masterInfo);
        change_masterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder ad=new AlertDialog.Builder(thisContext);



                ad.setTitle("공지사항").setMessage("정보 수정 버튼 작업중입니다").setNeutralButton("확인",new DialogInterface.OnClickListener() {



                    public void onClick(DialogInterface dialog, int which) {



                        Toast.makeText(getApplicationContext(), "불편을 드려 죄송합니다", Toast.LENGTH_SHORT).show();



                    }



                }).create().show();



            }
        });


        //로그아웃 버튼
        TextView logout = (TextView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃 시에 카카오톡 로그아웃
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
