package com.fx.nettykotlin.animateviewgroup;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ForceSizeChangeViewGroup extends ViewGroup implements View.OnClickListener {

    private List<View> views;
    Path path;
    Path[] paths;
    Paint paint;
    ValueAnimator animator;
    PathMeasure pathMeasure;
    Rect rectTemp;
    float offset;
    int pading = 10;
    int left = 0, top = 0, right = 0, bottom = 0;
    float[] pos = new float[2];
    float[] tan = new float[2];
    float[] tem = new float[2];

    public ForceSizeChangeViewGroup(Context context) {
        super(context);
    }

    public ForceSizeChangeViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        views = new ArrayList<>();
        path = new Path();
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        rectTemp = new Rect();
        pathMeasure = new PathMeasure();
        paths = new Path[]{new Path(), new Path(), new Path(), new Path()};
        setOnClickListener(this);
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            offset = (float) animation.getAnimatedValue();
            invalidate();
        });
    }

    public ForceSizeChangeViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.i("fx", "onMeasure");
        measureChildren(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("fx", "onSizeChanged->" + getChildCount());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("fx", "onLayout-->" + "l-->" + l + "t-->" + t + "r-->" + r + "b-->" + b);
        views.clear();
        // 预留
        for (int i = 0; i < getChildCount(); i++) {
            View cView = getChildAt(i);
            views.add(cView);
            Log.i("fx_before", "cView-->" + "l-->" + cView.getLeft() + "\tt-->" + cView.getTop() + "\tr-->" + cView.getRight() + "\tb-->" + cView.getBottom() + "\tw-->" + cView.getWidth() + "\th-->" + cView.getHeight());
            Log.i("fx", "cView-->" + "\tw-->" + cView.getMeasuredWidth() + "\th-->" + cView.getMeasuredHeight());

            left = right + pading * 2;
            top = pading * 2;
            right = right + cView.getMeasuredWidth() + pading * 2;
            bottom = cView.getMeasuredHeight();
            cView.layout(left, top, right, bottom);
            Log.i("fx_after", "cView-->" + "l-->" + left + "\tt-->" + top + "\tr-->" + right + "\tb-->" + bottom + "\tw-->" + cView.getWidth() + "\th-->" + cView.getHeight());
            Log.i("fx_after", "cView-->" + "l-->" + cView.getLeft() + "\tt-->" + cView.getTop() + "\tr-->" + cView.getRight() + "\tb-->" + cView.getBottom() + "\tw-->" + cView.getWidth() + "\th-->" + cView.getHeight());
            if (i == 0) {
                movePathToPos(0);
                initPath(0);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.i("fx", "dispatchDraw");
//        canvas.drawPath(path, paint);
//        path.reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("fx", "onDraw");
        canvas.drawPath(path, paint);
        path.reset();
        float a = 0, b = 0, c = 0, d = 0;
        for (int i = 0; i < paths.length; i++) {
            pathMeasure.setPath(paths[i], false);
            pathMeasure.getPosTan(pathMeasure.getLength() * offset, pos, tan);
            switch (i) {
                case 0:
                    path.moveTo(pos[0], pos[1]);
                    tem[0] = pos[0];
                    tem[1] = pos[1];
                    a = pos[0];
                    b = pos[1];
                    break;
                case 1:
                case 2:
                    c = pos[0];
                    d = pos[1];
                case 3:
//                    path.lineTo(pos[0], pos[1]);
                    break;
            }
            path.addRect(a, b, c, d, Path.Direction.CW);
//            path.close();
//            canvas.drawCircle(tem[0],tem[1],20,paint);
//            canvas.drawPath(paths[i],paint);
        }
    }

    public void movePathToPos(int pos) {
        //绘制path
        path.moveTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getTop() - pading);
        path.lineTo((float) views.get(pos).getRight() + pading, (float) views.get(pos).getTop() - pading);
        path.lineTo((float) views.get(pos).getRight() + pading, (float) views.get(pos).getBottom() + pading);
        path.lineTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getBottom() + pading);
        path.lineTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getTop() - pading);
    }

    private void initPath(int pos) {
        //动画路径
        paths[0].reset();
        paths[1].reset();
        paths[2].reset();
        paths[3].reset();
        paths[0].moveTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getTop() - pading);
        paths[1].moveTo((float) views.get(pos).getRight() + pading, (float) views.get(pos).getTop() - pading);
        paths[2].moveTo((float) views.get(pos).getRight() + pading, (float) views.get(pos).getBottom() + pading);
        paths[3].moveTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getBottom() + pading);
    }

    private void movePath(int pos) {
        //动画路径
        paths[0].lineTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getTop() - pading);
        paths[1].lineTo((float) views.get(pos).getRight() + pading, (float) views.get(pos).getTop() - pading);
        paths[2].lineTo((float) views.get(pos).getRight() + pading, (float) views.get(pos).getBottom() + pading);
        paths[3].lineTo((float) views.get(pos).getLeft() - pading, (float) views.get(pos).getBottom() + pading);
    }

    @Override
    public void onClick(View v) {
        movePath(1);
        if (!animator.isRunning()) {
            animator.start();
        }
    }
}
