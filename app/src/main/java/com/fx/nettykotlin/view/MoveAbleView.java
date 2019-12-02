package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.Nullable;

import com.fx.nettykotlin.R;


public class MoveAbleView extends View  {

    private  ScaleGestureDetector scaleGestureDetector;
    private Bitmap bitmap;
    private Matrix matrix;
    private Paint paint;

    private boolean isonScale = false;
    public MoveAbleView(Context context) {
        super(context);
    }

    float sx = 1f,sy = 1f;
    public MoveAbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        matrix = new Matrix();
        paint = new Paint();
        scaleGestureDetector = new ScaleGestureDetector(getContext(),new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.i("fx_onScale","detector.getFocusX()->"+detector.getFocusX());
                Log.i("fx_onScale","detector.getFocusY()->"+detector.getFocusY());
                Log.i("fx_onScale","detector.getCurrentSpanX()->"+detector.getCurrentSpanX());
                Log.i("fx_onScale","detector.getCurrentSpanY()->"+detector.getCurrentSpanY());
                Log.i("fx_onScale","detector.getScaleFactor()->"+detector.getScaleFactor());
                matrix.preTranslate(detector.getFocusX(),detector.getFocusY());
                matrix.setScale(detector.getScaleFactor(), detector.getScaleFactor());
                matrix.preTranslate(-detector.getFocusX(),-detector.getFocusY());
                return super.onScale(detector);
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                isonScale = true;
                return super.onScaleBegin(detector);
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                isonScale =false;
                super.onScaleEnd(detector);
            }
        });

    }

    public MoveAbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MoveAbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, matrix, paint);
        Log.i("fx_onDraw", matrix.toString());
    }

    float x;
    float y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
//        Log.i("fx_onTouchEvent", "event.getAction()-->" + event.getAction() + "x-->" + event.getX() + "y-->" + event.getY());
        if (isonScale){
            return true;
        }
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                Log.i("fx_ACTION_DOWN", "x-->" + x + "y-->" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isonScale){
                    return true;
                }
                float dx = event.getX() - x;
                float dy = event.getY() - y;

                x = event.getX();
                y = event.getY();
                matrix.postTranslate(dx, dy);
                Log.i("fx_ACTION_MOVE", "dx-->" + dx + "dy-->" + dy);
                invalidate();
                break;
        }
        return true;
    }
    public void destory(){
        bitmap.recycle();
    }



}
