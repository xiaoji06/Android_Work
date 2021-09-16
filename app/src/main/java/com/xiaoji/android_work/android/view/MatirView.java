package com.xiaoji.android_work.android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.R;


public class MatirView extends View {

    private Paint mPaint;
    private Matrix matrix;
    private Camera mCamera;
    private Bitmap bitmap;
    private Path mPath;
    private int radius = 150;
    private int width;
    private int height;
    private int dx;
    private int d;

    public MatirView(Context context) {
        this(context,null);
    }

    public MatirView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MatirView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(15);
        matrix = new Matrix();
        mPath = new Path();

        mCamera = new Camera();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aaa);
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(0, 90);
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                d = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        canvas.save();
        mCamera.save();


        //mCamera.translate(width/2,-height/2,0);
        mCamera.rotateX(d);
//        mCamera.getMatrix(matrix);
//        matrix.preTranslate(-300-width/2,-300-height/2);
//        matrix.postTranslate(300+width/2,300+height/2);
        //canvas.setMatrix(matrix);
        mCamera.applyToCanvas(canvas);
        mCamera.restore();

        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.restore();
        //画线
//        canvas.drawLine(0,0,200,0,mPaint);
//        canvas.drawLine(0,0,0,200,mPaint);
//
//        matrix.reset();
//
//        matrix.setScale(2,2);
//        matrix.setTranslate(100,100);
//        canvas.setMatrix(matrix);
//
//        canvas.drawLine(0,0,200,0,mPaint);
//        canvas.drawLine(0,0,0,200,mPaint);
//
//        mPaint.setColor(Color.BLACK);
//        canvas.drawCircle(0,0,30,mPaint);
    }
}
