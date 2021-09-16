package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ViewPagerScroller extends ViewGroup {

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public ViewPagerScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为每个子view测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 让每个子控件都是屏幕宽度，并且水平布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
        }
    }

    private float firstX;
    private int sumX = 0;
    private Scroller scroller;
    private int currItem;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                firstX = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                currItem = Math.round((float) getScrollX() / getWidth());
                int dx = (int) (currItem * getWidth() - getScrollX());
                scroller.startScroll(getScrollX(), 0, dx, 0);

                Log.e("ACTION_UP","ACTION_UP  getScrollX():"+getScrollX()
                        +" currItem:"+currItem+"  dx:"+dx);
                // 记录总偏移量
                sumX = (int) (currItem * getWidth());

                invalidate();

                break;

            case MotionEvent.ACTION_MOVE:

                int x = (int) event.getX();
                int scrollX = sumX + (int) firstX - x;

                // 限制每个页面的边界
                if (scrollX < 0) {//限制首页
                    scrollX=0;
                } else if (scrollX > (getChildCount() - 1) * getWidth()) {//限制尾页
                    scrollX=(getChildCount() - 1) * getWidth();
                }

                scrollTo(scrollX, 0);

                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();
            scrollTo(currX, 0);
        }
    }
}
