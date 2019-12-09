package com.fx.nettykotlin.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BezierView extends View implements View.OnClickListener {
    float cx, cy;
    Paint mPaint;

    Path path;

    ValueAnimator animator;

    PathMeasure p ;
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    float offsent ;

    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        path = new Path();
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(animation -> {
            offsent = (float)animation.getAnimatedValue();
            invalidate();
        });
        setOnClickListener(this);
        p = new PathMeasure();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        canvas.drawCircle(cx / 2, cy, 50, mPaint);
        canvas.drawCircle(cx+offsent*300, cy, 50, mPaint);
//        canvas.drawLine(cx / 2, cy - 50, cx / 2, cy + 50, mPaint);
//        canvas.drawLine(cx +offsent*300, cy - 50, cx, cy + 50, mPaint);


        path.moveTo(cx / 2, cy - 50);
        path.quadTo(cx / 4 * 3+offsent*300/2, cy+offsent*300, cx+offsent*300, cy - 50);
        path.lineTo(cx+offsent*300, cy + 50);
        path.quadTo(cx / 4 * 3+offsent*300/2, cy-offsent*300, cx / 2, cy + 50);
//
//        path.close();
//        mPaint.setStyle(Paint.Style.STROKE);
        p.setPath(path,false);
        p.getPosTan(p.getLength()*0.4f,pos,tan);
        if (p.getPosTan(p.getLength()*0.2f,pos,tan)&&pos[1]<cy){
            canvas.drawPath(path, mPaint);
        }

        canvas.drawCircle(cx / 4 * 3, cy, 2, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = w / 2;
        cy = h / 2;
    }

    @Override
    public void onClick(View v) {
        animator.start();
    }
}
