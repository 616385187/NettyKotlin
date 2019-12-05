package com.fx.nettykotlin.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import static android.animation.ValueAnimator.INFINITE;

public class PathView extends View implements View.OnClickListener {
    private PathMeasure pathMeasure;
    private Paint mPaint;

    private Path path;

    private float[] pos = new float[2];
    private float[] tan = new float[2];
    Path dst = new Path();
    //波浪流动X轴偏移量
    private float mOffsetX;
    private ValueAnimator animator;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        path = new Path();
        pathMeasure = new PathMeasure(dst, false);
        setOnClickListener(this);
        mOffsetX = 0;
        animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration(2000L);
        animator.setRepeatCount(INFINITE);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        dst.reset();
        path.moveTo(10, getHeight() / 2);
        dst.moveTo(10, getHeight() / 2);
//        path.setFillType(Path.FillType.INVERSE_WINDING);
//        dst.setFillType(Path.FillType.INVERSE_WINDING);
        for (int i = 0; i < 5; i++) {
            path.rQuadTo(100, -200, 200, 0);
            path.rQuadTo(100, 200, 200, 0);
        }

        float l = pathMeasure.getLength();

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        pathMeasure.setPath(path, false);
        boolean flag = pathMeasure.getSegment(0, mOffsetX * l, dst, false);
        if (flag){
            canvas.drawPath(dst, mPaint);
        }
        Log.i("fx", flag + "");
        mPaint.reset();
//        canvas.save();
//        canvas.drawPath(path, mPaint);

        boolean is = pathMeasure.getPosTan(mOffsetX * l ,pos,tan);
        if (is){
            canvas.drawCircle(pos[0],pos[1],10,mPaint);
        }
//        canvas.restore();
        Log.i("fx", path.getFillType() + "");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        path.moveTo(10+mOffsetX,getHeight()/2);
//
//        for (int i= 0;i<5; i++ ){
//            path.rQuadTo(100,-200,200,0);
//            path.rQuadTo(100,200,200,0);
//        }
    }

    @Override
    public void onClick(View v) {
        animator.addUpdateListener(a -> {
            mOffsetX = (float) a.getAnimatedValue();

            invalidate();
            Log.i("fx", mOffsetX + "");
        });

        if (animator.isRunning()) {
            animator.pause();
        } else {
            animator.start();
        }
    }
}
