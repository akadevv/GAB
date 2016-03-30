package com.innovest.lcd.gab.PayRoom;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;

/**
 * Created by Administrator on 2016-01-30.
 */
public class DragLongClickListener implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {

        // 태그 생성
        ClipData.Item item = new ClipData.Item(
                (CharSequence) v.getTag());

        String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
        ClipData data = new ClipData(v.getTag().toString(),
                mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                v);

        v.startDrag(data, // data to be dragged
                shadowBuilder, // drag shadow
                v, // 드래그 드랍할  Vew
                0 // 필요없은 플래그
        );

        //   v.setVisibility(View.INVISIBLE);
        return true;

    }
}
