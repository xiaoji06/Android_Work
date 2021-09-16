package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySrufaceView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isRunning;

    private Paint paint;
    private int minR;
    private int maxR;
    private int curR;
    private int flag;


    public MySrufaceView(Context context) {
        super(context);
        initView();
    }

    public MySrufaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySrufaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        SurfaceHolder surfaceHolder = getHolder();

        surfaceHolder.addCallback(new SurfaceHolder.Callback2() {
            @Override
            public void surfaceRedrawNeeded(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                isRunning = true;
                thread = new Thread(MySrufaceView.this);
                thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isRunning = false;
            }
        });

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        maxR = Math.min(w, h) / 2 - 20;
        curR = minR = 30;

    }

    @Override
    public void run() {
        while (isRunning) {
            drawSelf();
        }
    }

    private void drawSelf() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            if (canvas != null) {
                drawCircle(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, curR, paint);
        if (curR >= maxR) {
            flag = -6;
        } else if (curR <= minR) {
            flag = 6;
        }
        curR += flag;
    }
}
