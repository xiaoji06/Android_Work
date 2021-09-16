package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaoji.android_work.R;


public class PathView extends View {

    private Path circlePath;
    private Paint circlePaint;
    private Paint carPaint;
    private Paint xPaint;
    private Paint yPaint;
    private Matrix matrix;
    private Bitmap car;
    private int width;
    private int height;
    private PathMeasure pathMeasure;
    private float pathLength;
    private float progress;
    private float distance;
    private float[] pos=new float[2];
    private float[] tan=new float[2];
    private Camera camera;
    private Matrix cameraMatrix;

//    public Handler handler=new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            PathView.this.invalidate();
//            sendEmptyMessageDelayed(0,10);
//        }
//    };

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circlePath=new Path();
        circlePath.addCircle(0,0,400,Path.Direction.CW);
        pathMeasure=new PathMeasure();
        pathMeasure.setPath(circlePath,false);
        pathLength=pathMeasure.getLength();
        car= BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
        matrix=new Matrix();
        //matrix.postScale(0.5f,0.5f);
        circlePaint=new Paint();
        circlePaint.setColor(getResources().getColor(R.color.colorAccent));
        circlePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        carPaint=new Paint();

        xPaint=new Paint();
        xPaint.setColor(getResources().getColor(R.color.colorPrimary));
        xPaint.setStrokeWidth(10);
        xPaint.setStyle(Paint.Style.STROKE);
        xPaint.setAntiAlias(true);

        yPaint=new Paint();
        yPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        yPaint.setStrokeWidth(10);
        yPaint.setStyle(Paint.Style.STROKE);
        yPaint.setAntiAlias(true);

        camera=new Camera();
        cameraMatrix=new Matrix();
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=getWidth();
        height=getHeight();
    }
    private float degreeX;
    private float degreeY;
    private void setCamera(Canvas canvas) {
        cameraMatrix.reset();
        camera.save();
        //camera.rotateZ(degree);
        camera.rotateY(degreeY);
        camera.rotateX(degreeX);
        camera.getMatrix(cameraMatrix);
        camera.restore();
        //camera在view左上角那个点，故旋转默认是以左上角为中心旋转
        //故在动作之前pre将matrix向左移动getWidth()/2长度，向上移动getHeight()/2长度
        cameraMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        //在动作之后post再回到原位
        cameraMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
        canvas.concat(cameraMatrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                float rotateX = -(event.getY() - getHeight() / 2);
                float rotateY = (event.getX() - getWidth() / 2);
                float percentX = rotateX / getWidth() / 2;
                float percentY = rotateY / getHeight() / 2;
                degreeY=percentY*60f;
                degreeX=percentX*60f;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置镜头
        setCamera(canvas);

        matrix.reset();
        canvas.translate(width/2,height/2);

        canvas.drawLine(-getWidth()/2,0,getWidth()/2,0,xPaint);
        canvas.drawLine(0,-getHeight()/2,0,getHeight()/2,yPaint);
        progress += 0.006;
        if (progress>1){
            progress=0;
        }
        distance=progress*pathLength;
        pathMeasure.getPosTan(distance,pos,tan);

        matrix.postRotate((float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI),car.getWidth()/2,car.getHeight()/2);
        matrix.postScale((progress+1f),(progress+1f),car.getWidth()/2,car.getHeight()/2);
        matrix.postTranslate(pos[0]-car.getWidth()/2,pos[1]-car.getHeight()/2);

        canvas.drawPath(circlePath,circlePaint);
        canvas.drawBitmap(car,matrix,carPaint);

        //handler.sendEmptyMessage(0);
        invalidate();
        Log.e("xxx","xxxxxx progress："+progress+"  car.getWidth():"+car.getWidth()+"  car.getHeight():"+car.getHeight());

    }
}
