package com.xiaoji.android_work.android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * 波浪
 */
public class VaweView extends View {

    public VaweView(Context context) {
        this(context,null);
    }

    public VaweView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VaweView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;
    private Path path;
    private int vaweLength = 1200;
    private int vaweHLength = 600;
    private int top = 600;
    private int dx = 0;
    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#B0E2FF"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        path = new Path();

        startVawe();
    }

    private void startVawe() {
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0,vaweLength);
        animator.setDuration(1600);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        //屏幕左右都铺满一个波形
        path.moveTo(-vaweLength + dx,top);
        for(int i = -vaweLength ; i <= getWidth() + vaweLength ; i += vaweLength){
            path.rQuadTo(vaweHLength,-100,vaweLength,0);
            path.rQuadTo(vaweHLength,100,vaweLength,0);
        }
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();
        canvas.drawPath(path,paint);
    }
}
