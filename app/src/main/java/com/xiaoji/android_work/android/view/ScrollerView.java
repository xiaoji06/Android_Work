package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ScrollerView extends View {

    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private boolean isfling = false;

    public ScrollerView(Context context) {
        super(context);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.scrollTo(100, 100);
//            }
//        });
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 将这个view的大小固定为 500x500
        setMeasuredDimension(500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 将该view的背景设置为红色
        canvas.drawColor(Color.RED);

        // 在view中间绘制一个半径为50的圆，默认paint，所以这个圆是个黑色的
        canvas.drawCircle(50, 50, 50, new Paint());

    }
    // 记录手指刚按下时的坐标
    private float firstX;
    private float firstY;

    // 记录总偏移量
    private int sumX;
    private int sumY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                firstX = event.getX();
                firstY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(500, Float.MAX_VALUE);
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();

                sumX += firstX - event.getX();
                sumY += firstY - event.getY();

                scroller.fling(sumX, sumY, -xVelocity, -yVelocity,
                        -Integer.MAX_VALUE, Integer.MAX_VALUE, -Integer.MAX_VALUE, Integer.MAX_VALUE);

                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                // 在上次移动的基础上再次移动
                scrollTo(sumX + (int) firstX - x, sumY + (int) firstY - y);

                break;

        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (scroller.computeScrollOffset()) {

            isfling = true;

            int currX = scroller.getCurrX();
            int currY = scroller.getCurrY();

            scrollTo(currX, currY);
            //invalidate();

        } else {

            if (isfling) {
                sumX = getScrollX();
                sumY = getScrollY();
            }
            isfling = false;

        }

    }

}
