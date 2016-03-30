package com.innovest.lcd.gab.Common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016-02-22.
 */
public class CustomImageViewCircleShape extends ImageView {

//you can change the radius to modify the circlur shape into oval or rounded rectangle

    public static float radius = 100.0f;

    public CustomImageViewCircleShape(Context context) {
        super(context);
    }

    public CustomImageViewCircleShape(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageViewCircleShape(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 36.0f;
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
