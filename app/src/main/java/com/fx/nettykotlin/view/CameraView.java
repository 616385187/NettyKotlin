package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.fx.nettykotlin.R;

public class CameraView extends View {
    private Camera mCamera;
    private Bitmap mBitmap;

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nobody);
        mCamera = new Camera();

    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int p = -8;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(110,110);
        mCamera.save();
        mCamera.rotateX(45);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-110,-110);
        mCamera.restore();
        canvas.drawRect(20,20,200,200,new Paint());

        canvas.restore();
        Log.i("fx",mCamera.getLocationZ()+"");

//        canvas.save();
//        canvas.translate(mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        mCamera.save();
//        mCamera.rotateX(45);
//        mCamera.applyToCanvas(canvas);
//        canvas.translate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);
//        mCamera.restore();
//        canvas.drawRect(320,320,500,500,new Paint());
////
//        canvas.restore();


//        canvas.save();
//        mCamera.save();
//        mCamera.translate(mBitmap.getWidth()/2+500,mBitmap.getHeight()/2+800,200);
//        mCamera.rotateX(45);
//        mCamera.rotateZ(30);
//        mCamera.applyToCanvas(canvas);
//        mCamera.restore();
//        canvas.drawBitmap(mBitmap,500,800,null);
//        canvas.restore();
//        p -- ;
//        mCamera.setLocation(0,0,p);
//        invalidate();
//        if (p == -100){
//            p = -8;
//        }

    }
}
