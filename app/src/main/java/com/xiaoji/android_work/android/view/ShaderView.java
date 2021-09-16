package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

public class ShaderView extends View {
    public ShaderView(Context context) {
        super(context);
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;
    private float radius = 600f;

    private float centerX = 0f;
    private float centerY = 0f;


    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.save();
        canvas.translate(getWidth()/2,getHeight()/2);
        drawBubble(canvas);
        canvas.restore();
    }

    private void drawBubble(Canvas canvas) {
        final float radius = 200;
        paint.setShader(getGradient(radius,0.5f,0.05f));
        canvas.drawCircle(centerX,centerY,radius,paint);
        paint.setShader(getGradient(radius,0.7f,0.08f));
        canvas.drawCircle(centerX,centerY,radius,paint);
        paint.setShader(getGradient(radius,0.8f,0.1f));
        canvas.drawCircle(centerX,centerY,radius,paint);
    }

    private Shader getGradient(float radius, float start, float alpha) {
        final int endColor = ColorUtils.setAlphaComponent(Color.WHITE,(int)(0xff * alpha));

        return new RadialGradient(centerX,centerY,radius,
                new int[]{0x00ffffff,endColor},
                new float[]{start,1f},
                Shader.TileMode.CLAMP);
    }
}
