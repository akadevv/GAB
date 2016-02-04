package com.example.lcd.gab.CommonListener;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016-02-03.
 */
public class ImageViewTouchListener implements View.OnTouchListener{
    String log = "jjunest";
    @Override
    //이미지뷰 클릭시에 바탕을 회색으로 바꾸어주는 Listener
    // 클릭 시, 클릭 여부를
    public boolean onTouch(View v, MotionEvent event) {

        ImageView touchedImageView = (ImageView)v;
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            Log.d(log, "MotionEvent.Action_Down in ImageViewTouchListener.java");
            touchedImageView.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);

           return false;
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.d(log, "MotionEvent.Action_UP in ImageViewTouchListener.java");
            touchedImageView.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
            return false;
        }
   return false;


    }
}
