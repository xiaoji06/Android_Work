package com.xiaoji.android_work.android.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;


public class View3 extends View {
    private int downX;
    private int downY;

    public View3(Context context) {
        super(context);
    }

    public View3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GestureDetector mGestureDetector;
    private View mView;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onAttachedToWindow() {
        Log.e("touch","view3:onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("touch","view3:onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        Log.e("touch","view3:onFinishInflate");
        super.onFinishInflate();
        if (null == mGestureDetector){
            mGestureDetector = new GestureDetector(getContext(), new MyOnGestureListener());
        }
        if (null == mScroller){
            mScroller = new Scroller(getContext());
        }
    }

    public class MyOnGestureListener implements GestureDetector.OnGestureListener {

        private float x = 0;
        private float y = 0;

        FlingAnimation flingXAnimation;
        FlingAnimation flingYAnimation;

        @Override
        public boolean onDown(MotionEvent e) {
            //Log.e("aaa","onDown");
            if (null != flingXAnimation){
                flingXAnimation.cancel();
            }
            if (null != flingYAnimation){
                flingYAnimation.cancel();
            }
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            //Log.e("aaa","onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // Log.e("aaa","onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.e("aaa","onScroll" + "\n  e1:"+e1.toString()
//                    + "\n  e2:"+e2.toString()
//                    + "\n  distanceX:"+distanceX
//                    + "  distanceY:"+distanceY
//                    + "  getScrollX:"+getScrollX()
//                    + "  getScrollY:"+getScrollY());
           // method1((int)distanceX,(int)distanceY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //Log.e("aaa","onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("aaa","onFling");
            //当手指立刻屏幕时，获得速度，作为fling的初始速度     mVelocityTracker.computeCurrentVelocity(1000,mMaxFlingSpeed);
            // 由于坐标轴正方向问题，要加负号。
//            mScroller.fling(mScroller.getCurrX(),mScroller.getCurrY(), -(int) velocityX,-(int) velocityY,-500,1000,-500,1000);
//            invalidate();
//            float velocity = Math.abs(velocityX);
//            if (Math.abs(velocityX) <= Math.abs(velocityY)){
//                velocity = Math.abs(velocityY);
//            }
//            flingXAnimation = new FlingAnimation(View3.this, DynamicAnimation.TRANSLATION_X);
//            flingXAnimation.setStartVelocity(velocityX>0?velocity:-velocity);
//            flingXAnimation.setMinValue(0f);
//            flingXAnimation.setMaxValue(600);
//            flingXAnimation.setFriction(1.3f);
//            flingXAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
//                @Override
//                public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
//
//                }
//            });
            float velocity = (float) Math.sqrt(Math.pow(velocityX,2)+Math.pow(velocityY,2));
            flingYAnimation = new FlingAnimation(new FloatValueHolder());
            flingYAnimation.setStartVelocity(velocity);
            flingYAnimation.setMinValue(-600);
            flingYAnimation.setMaxValue(600);
            flingYAnimation.setFriction(1.6f);
            flingYAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                double lastXValue = 0;
                double lastYValue = 0;
                @Override
                public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                    double o=Math.atan(velocityY/velocityX);
                    double valueY = Math.abs(Math.sin(o)*value);
                    double valueX = Math.abs(Math.cos(o)*value);
//                    if (velocityY < 0){ valueY = -valueY; }
//                    if (velocityX < 0){ valueX = -valueX; }
//                    valueY = -valueY;
//                    valueX = -valueX;
                    if (velocityY<0){
                        valueY = -valueY;
                    }
                    if (velocityX<0){
                        valueX = -valueX;
                    }
                    Log.e("aa","value:" + value +"   velocity:" + velocity   +"     velocityY：" +velocityY+"     velocityX：" +velocityX +"   o:"+o
                            +"  valueY:"+valueY +"  valueX:"+  valueX
                            +"  Math.sin(o):"+Math.sin(o) +"  Math.cos(o):"+  Math.cos(o));
                    double dX = valueX - lastXValue;
                    double dY = valueY - lastYValue;
                    if ((dX+getX())<=0){
                        setX(0);
                        valueX = 0;
                    }else {
                        setX((float) (getX() + dX));
                    }
                    if ((dY+getY()<=0)){
                        setY(0);
                        valueY = 0;
                    }else {
                        setY((float) (getY() + dY));
                    }
                    if (velocity == 0){
                        lastXValue =  0;
                        lastYValue =  0;
                    }else {
                        lastXValue =  valueX;
                        lastYValue =  valueY;
                    }
                }
            });

//            flingXAnimation.start();
            flingYAnimation.start();
            return false;
        }
    }

    private void method1(int translationX, int translationY) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.leftMargin = getLeft() + translationX;
        params.topMargin = getTop() + translationY;
        setLayoutParams(params);
        Log.e("aaaaaa","getScrollX:"+((View)getParent()).getScrollX()+"    getScrollY:"+((View)getParent()).getScrollY());
        Log.e("aaaaaa","child getScrollX:"+getScrollX()+"   child getScrollY:"+getScrollY());
    }

    //方式2，使用view的layout方法重新设置自己的位置
    private void method2(int translationX, int translationY) {
        layout(getLeft() + translationX, getTop() + translationY, getRight() + translationX, getBottom() + translationY);
    }

    //方式3，使用scrollBy方法移动
    private void method3(int translationX, int translationY) {
        ((View) getParent()).scrollBy(-translationX, -translationY);
        Log.e("aaaaaa","child getTop:"+getTop()+"   child getLeft:"+getLeft());
    }

    //方式4，系统封装的方法
    private void method4(int translationX, int translationY) {
        offsetLeftAndRight(translationX);
        offsetTopAndBottom(translationY);
    }

    private void doFling(int speed) {
        if (mScroller == null) {
            return;
        }
        mScroller.fling((int) mView.getX(), (int) mView.getY(),speed,speed,0,0,-500,10000);
        invalidate();
    }
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Log.e("touch","view3:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        int x = (int) event.getX();  //获取x坐标
        int y = (int) event.getY();  //获取y坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当手指触碰view的时候，记下初始位置坐标downX和downY
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动过程，将手指的实时坐标减去初始坐标就是滑动的距离
                int translationX = x - downX;
                int translationY = y - downY;
                //执行滑动
                method1(translationX, translationY); //方式1
                //method2(translationX, translationY); //方式2
                //method3(translationX, translationY); //方式3
                //method4(translationX, translationY); //方式4
                break;
            case MotionEvent.ACTION_UP:
                //滑动的方式5，这不是跟着手指滑动了，是手指离开屏幕后View返回原位置
//                mScroller.startScroll(((View) getParent()).getScrollX(), ((View) getParent()).getScrollY(), -((View) getParent()).getScrollX(), -((View) getParent()).getScrollY(), 500);
//                invalidate();
                break;
        }
        return true;
    }
}
