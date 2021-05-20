package com.fx.nettykotlin.animateview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class STBView extends View implements View.OnClickListener {

    private Path path;
    private Path pathDoor;
    private Paint paint;
    private float baseLineY;
    private float baseLineMarginX;
    private RectF door;
    private RectF temp;

    //上海
    private Path bgPath;
    private Path building;
    private Path towerPath;
    private Path outerPath;

    //开始动画
    PathMeasure pathMeasure;
    ValueAnimator animator;
    Path dst = new Path();
    Path cache = new Path();
    float offset;
    int step = 0;
    int pathCount = 0;

    float baseTime;
    float[] pos = new float[2];
    float[] tan = new float[2];

    public STBView(Context context) {
        super(context);
    }

    public STBView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        path = new Path();
        pathDoor = new Path();
        setOnClickListener(this);
        bgPath = new Path();
        building = new Path();
        towerPath = new Path();
        outerPath = new Path();
        temp = new RectF();
        pathMeasure = new PathMeasure();
        animator = ValueAnimator.ofFloat(0.01f, 1);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (pathMeasure.nextContour()) {
                    animator.start();
                    pathCount += 1;
                    if (step == 1 && pathCount == 1) {
                        baseTime = pathMeasure.getLength();
                    }
                    Log.i("fx_pathCount", "step-->" + step + "\tcount-->" + pathCount + "\tpathMeasure-->" + pathMeasure.getLength());
                } else {
                    step += 1;
                    step %= 7;
                    pathCount = 0;
                    if (step == 1) {
                        pathMeasure.setPath(pathDoor, false);
                        animator.start();
                    } else if (step == 2) {
                        pathMeasure.setPath(cache, false);
                        animator.start();
                    } else if (step == 3) {
                        pathMeasure.setPath(bgPath, false);
                        animator.start();
                    } else if (step == 4) {
                        pathMeasure.setPath(building, false);
                        animator.start();
                    } else if (step == 5) {
                        pathMeasure.setPath(outerPath, false);
                        animator.start();
                    } else if (step == 6) {
                        pathMeasure.setPath(towerPath, false);
                        animator.start();
                    } else if (step == 0) {
                        pathMeasure.setPath(path, false);
                        animator.start();
                    } else {
                        animator.cancel();
                        step = 0;
                    }
                }
            }
        });
        animator.addUpdateListener(animation -> {
            offset = (float) animation.getAnimatedValue();
            Log.i("fx_addUpdateListener", offset + "step-->" + step);
            if (step == 2) {
                scrollTo(0, (int) (getHeight() / 2 * offset));
            }
            invalidate();
        });


    }

    public STBView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        canvas.drawLine(2, 0, 2, getHeight() / 2, paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(2, getHeight() / 2, 2, getHeight(), paint);
        Log.i("fx_onDraw", pathMeasure.getLength() + "");
//        cache.reset();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10);
//        canvas.drawPath(path, paint);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        canvas.drawPath(pathDoor, paint);
        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(pathDoor, paint);
        if (step == 0) {
//            pathMeasure.setPath(path,false);
//            while (pathMeasure.nextContour())
            canvas.save();
            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                canvas.drawPath(dst, paint);
            }
        }

        canvas.save();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawRect(door, paint);
        if (step == 1) {
            int id = canvas.save();
            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLUE);
                dst.computeBounds(temp, false);
                canvas.drawRect(temp, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(10);
                canvas.drawPath(dst, paint);
            }

        }
        if (step == 2) {
            canvas.save();
//            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setStrokeWidth(10);
//                canvas.drawCircle(pos[0],pos[1],10, paint);
////                canvas.drawPath(dst, paint);
//            }
        }
        if (step == 3) {
            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                canvas.drawPath(dst, paint);
            }
        }
        if (step == 4) {
//            pathMeasure.setPath(building,false);
//            while (pathMeasure.nextContour())
            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                paint.setColor(Color.BLACK);
                canvas.drawPath(dst, paint);
            }
        }
        if (step == 5) {
            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                canvas.drawPath(dst, paint);
            }
        }
        if (step == 6) {
            if (pathMeasure.getSegment(0, offset * pathMeasure.getLength(), dst, true)) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                canvas.drawPath(dst, paint);
            }
        }
//        canvas.drawPath(bgPath, paint);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        canvas.drawPath(building, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.BLACK);
//        canvas.drawPath(building, paint);
//
//        canvas.drawPath(towerPath, paint);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        canvas.drawPath(outerPath, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.BLACK);
//        canvas.drawPath(outerPath, paint);

        Log.i("fx_canvas", "canvas" + canvas.getSaveCount());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h * 2, oldw, oldh);
        baseLineY = h / 2 / 4 * 3;
        baseLineMarginX = w / 10;

        path.moveTo(baseLineMarginX, baseLineY);
        path.lineTo(w - baseLineMarginX, baseLineY);
        cache.moveTo(w - baseLineMarginX, baseLineY);
        cache.lineTo(w / 2, baseLineY);


        path.moveTo(baseLineMarginX + 100, baseLineY);
        path.lineTo(baseLineMarginX + 100, baseLineY - 600);
        path.lineTo(baseLineMarginX + 250, baseLineY - 620);
        path.lineTo(baseLineMarginX + 250, baseLineY);

        for (int i = 1; i <= 10; i++) {
            RectF rect = new RectF((int) baseLineMarginX + 100, (int) baseLineY - 560 + i * 40,
                    (int) baseLineMarginX + 200, (int) baseLineY - 560 + i * 40 + 20);
            path.addRect(rect, Path.Direction.CCW);
        }

        RectF right = new RectF(w - baseLineMarginX - 260, baseLineY - 400, w - baseLineMarginX - 100, baseLineY);
        path.addRect(right, Path.Direction.CCW);
        RectF rightMiddle = new RectF(w - baseLineMarginX - 260 + 20, baseLineY - 400 - 40,
                w - baseLineMarginX - 100 - 20, baseLineY - 400);
        path.addRect(rightMiddle, Path.Direction.CCW);
        RectF rightTop = new RectF(rightMiddle.centerX(), rightMiddle.centerY() - 80,
                rightMiddle.centerX(), rightMiddle.centerY() - 20);
        path.addRect(rightTop, Path.Direction.CCW);
        for (int i = 1; i <= 5; i++) {
            RectF f = new RectF(w - baseLineMarginX - 260 + i * 26, baseLineY - 400 + 40,
                    w - baseLineMarginX - 260 + i * 26, baseLineY);
            path.addRect(f, Path.Direction.CCW);
        }

        path.moveTo(baseLineMarginX + 400, baseLineY - 380);
        path.lineTo(w - baseLineMarginX - 420, baseLineY - 380);
        path.quadTo(w - baseLineMarginX - 410, baseLineY - 340, w - baseLineMarginX - 360, baseLineY - 320);
        path.lineTo(baseLineMarginX + 340, baseLineY - 320);
        path.quadTo(baseLineMarginX + 410, baseLineY - 340, baseLineMarginX + 400, baseLineY - 380);

        RectF rectF = new RectF(baseLineMarginX + 380, baseLineY - 320,
                w - baseLineMarginX - 400, baseLineY - 290);
        path.addRect(rectF, Path.Direction.CCW);
        path.moveTo(rectF.left, rectF.bottom);
        path.lineTo(rectF.right, rectF.bottom);
        path.quadTo(rectF.right, rectF.bottom + 20, rectF.right + 80, rectF.bottom + 30);
        path.lineTo(rectF.left - 80, rectF.bottom + 30);
        path.quadTo(rectF.left, rectF.bottom + 20, rectF.left, rectF.bottom);

        RectF rect = new RectF(baseLineMarginX + 380 - 30, baseLineY - 320 + 60,
                w - baseLineMarginX - 400 + 30, baseLineY - 290 + 60);
        path.addRect(rect, Path.Direction.CCW);
        RectF rectNext = new RectF(rect);
        rectNext.bottom = rectNext.bottom + 20;
        rectNext.offset(0, 30);
        path.addRect(rectNext, Path.Direction.CCW);
        door = new RectF(baseLineMarginX + 230, rectNext.bottom,
                w - baseLineMarginX - 225, baseLineY);
        pathDoor.addRect(door, Path.Direction.CCW);
        pathDoor.moveTo(door.left, door.top + 20);
        pathDoor.lineTo(door.right, door.top + 20);
        pathDoor.moveTo(door.centerX() + 40, baseLineY);
        pathDoor.lineTo(door.centerX() + 40, baseLineY - 90);
        pathDoor.quadTo(door.centerX(), baseLineY - 140, door.centerX() - 40, baseLineY - 90);
        pathDoor.lineTo(door.centerX() - 40, baseLineY);

        pathDoor.moveTo(door.centerX() + 100 + 60, baseLineY);
        pathDoor.lineTo(door.centerX() + 100 + 60, baseLineY - 70);
        pathDoor.quadTo(door.centerX() + 100 + 30, baseLineY - 120,
                door.centerX() + 100, baseLineY - 70);
        pathDoor.lineTo(door.centerX() + 100, baseLineY);
        pathDoor.moveTo(door.centerX() - 100 - 60, baseLineY);
        pathDoor.lineTo(door.centerX() - 100 - 60, baseLineY - 70);
        pathDoor.quadTo(door.centerX() - 100 - 30, baseLineY - 120,
                door.centerX() - 100, baseLineY - 70);
        pathDoor.lineTo(door.centerX() - 100, baseLineY);

        pathDoor.moveTo(door.centerX() + 210 + 60, baseLineY);
        pathDoor.lineTo(door.centerX() + 210 + 60, baseLineY - 70);
        pathDoor.quadTo(door.centerX() + 210 + 30, baseLineY - 120,
                door.centerX() + 210, baseLineY - 70);
        pathDoor.lineTo(door.centerX() + 210, baseLineY);
        pathDoor.moveTo(door.centerX() - 210 - 60, baseLineY);
        pathDoor.lineTo(door.centerX() - 210 - 60, baseLineY - 70);
        pathDoor.quadTo(door.centerX() - 210 - 30, baseLineY - 120,
                door.centerX() - 210, baseLineY - 70);
        pathDoor.lineTo(door.centerX() - 210, baseLineY);

        float sh_base_line = baseLineY + h / 2f;

        cache.quadTo(baseLineMarginX - h / 8f, baseLineY + h / 8f, w / 2, baseLineY + h / 4f);
//        cache.lineTo(w - baseLineMarginX, baseLineY+h/4f);
        cache.quadTo(w - baseLineMarginX + h / 8f, sh_base_line - h / 8f, w / 2, sh_base_line);
//        cache.lineTo(baseLineMarginX, sh_base_line);

        bgPath.moveTo(baseLineMarginX, sh_base_line);
        bgPath.lineTo(w - baseLineMarginX, sh_base_line);

        bgPath.moveTo(baseLineMarginX + 100, sh_base_line);
        bgPath.lineTo(baseLineMarginX + 100, sh_base_line - 100);
        bgPath.lineTo(baseLineMarginX + 100 + 40, sh_base_line - 100);
        bgPath.lineTo(baseLineMarginX + 100 + 40, sh_base_line - 120);
        bgPath.lineTo(baseLineMarginX + 100 + 100, sh_base_line - 120);
        bgPath.lineTo(baseLineMarginX + 100 + 300, sh_base_line - 120);
        bgPath.lineTo(baseLineMarginX + 100 + 300, sh_base_line - 60);
        bgPath.lineTo(baseLineMarginX + 100 + 400, sh_base_line - 60);
        bgPath.lineTo(baseLineMarginX + 100 + 400, sh_base_line - 80);
        bgPath.lineTo(baseLineMarginX + 100 + 480, sh_base_line - 80);
        bgPath.lineTo(baseLineMarginX + 100 + 480, sh_base_line - 200);
        bgPath.lineTo(baseLineMarginX + 100 + 580, sh_base_line - 200);
        bgPath.lineTo(baseLineMarginX + 100 + 580, sh_base_line - 180);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100, sh_base_line - 180);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100, sh_base_line - 180);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100, sh_base_line - 230);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30, sh_base_line - 230);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30, sh_base_line - 130);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180, sh_base_line - 130);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180, sh_base_line - 100);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100, sh_base_line - 100);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100, sh_base_line - 130);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100 + 100, sh_base_line - 130);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100 + 100, sh_base_line - 100);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100 + 100 + 80, sh_base_line - 100);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100 + 100 + 80, sh_base_line - 120);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100 + 100 + 80 + 70, sh_base_line - 120);
        bgPath.lineTo(baseLineMarginX + 100 + 580 + 100 + 30 + 180 + 100 + 100 + 80 + 70, sh_base_line);

        RectF one = new RectF(baseLineMarginX + 200, sh_base_line - 400, baseLineMarginX + 360, sh_base_line);
        building.addRect(one, Path.Direction.CCW);
        RectF two = new RectF(baseLineMarginX + 400, sh_base_line - 600, baseLineMarginX + 540, sh_base_line);
        building.addRect(two, Path.Direction.CW);
        RectF three = new RectF(w - baseLineMarginX - 400, sh_base_line - 500, w - baseLineMarginX - 560, sh_base_line);
        building.addRect(three, Path.Direction.CCW);
        RectF four = new RectF(w - baseLineMarginX - 200, sh_base_line - 260, w - baseLineMarginX - 320, sh_base_line);
        building.addRect(four, Path.Direction.CCW);

        building.moveTo(one.left, one.top);
        building.lineTo(one.centerX(), one.top + 40);
        building.lineTo(one.right, one.top);
        building.moveTo(one.left, one.top + 20);
        building.lineTo(one.centerX(), one.top + 40 + 20);
        building.lineTo(one.right, one.top + 20);
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 2; j++) {
                building.moveTo(one.left + 45 * j + 15, one.top + 80 + 40 * i);
                building.lineTo(one.left + 45 * j + 45, one.top + 80 + 40 * i);
            }
        }
        building.moveTo(two.left + 20, two.top + 20);
        building.lineTo(two.right - 20, two.top + 20);
        building.lineTo(two.centerX(), two.top + 260);
        building.lineTo(two.left + 20, two.top + 20);

        building.moveTo(two.centerX() - 35, two.top + 60);
        building.lineTo(two.centerX() + 35, two.top + 60);

        building.moveTo(two.centerX(), two.top + 260);
        building.lineTo(two.centerX(), two.bottom);

        for (int i = 0; i <= 10; i++) {
            building.moveTo(two.left + 20, two.top + 160 + 40 * i);
            building.lineTo(two.right - 20, two.top + 160 + 40 * i);
        }
        outerPath.moveTo(three.right + 20, three.top + 40);
        for (int i = 0; i <= 2; i++) {
            building.moveTo(three.right + 40 + 40 * i, three.top + 160);
            building.lineTo(three.right + 40 + 40 * i, three.bottom - 20);
            outerPath.addRect(three.right - 20, three.top + 40 + i * 40,
                    three.left - 60, three.top + 60 + i * 40, Path.Direction.CW);
        }
        for (int i = 0; i <= 5; i++) {
            building.moveTo(four.right + 20, four.top + 80 + 30 * i);
            building.lineTo(four.left - 20, four.top + 80 + 30 * i);
        }
        outerPath.addRoundRect(four.right + 20, four.top - 60, four.left - 20, four.top + 70, new float[]{40, 40, 40, 40, 40, 40, 40, 40}, Path.Direction.CCW);

        outerPath.moveTo(four.right, four.top - 40);
        outerPath.lineTo(four.left, four.top - 40);

        outerPath.moveTo(four.right + 20, four.top + 40);
        outerPath.lineTo(four.left - 20, four.top + 40);
        outerPath.moveTo(four.centerX(), four.top - 60);
        outerPath.lineTo(four.centerX(), four.top - 120);

        towerPath.moveTo(getWidth() / 2 - 10, sh_base_line);
        towerPath.lineTo(getWidth() / 2 - 10, sh_base_line - 80);
        towerPath.moveTo(getWidth() / 2 + 10, sh_base_line);
        towerPath.lineTo(getWidth() / 2 + 10, sh_base_line - 80);
        towerPath.addCircle(getWidth() / 2, sh_base_line - 120, 40, Path.Direction.CCW);
        towerPath.moveTo(getWidth() / 2 - 35, sh_base_line - 120 - 10);
        towerPath.lineTo(getWidth() / 2 + 35, sh_base_line - 120 - 10);
        towerPath.moveTo(getWidth() / 2 - 35, sh_base_line - 120 + 10);
        towerPath.lineTo(getWidth() / 2 + 35, sh_base_line - 120 + 10);

        towerPath.moveTo(getWidth() / 2 + 90, sh_base_line);
        towerPath.lineTo(getWidth() / 2 + 20, sh_base_line - 120 + 30);
        towerPath.moveTo(getWidth() / 2 + 110, sh_base_line);
        towerPath.lineTo(getWidth() / 2 + 30, sh_base_line - 120 + 20);

        towerPath.moveTo(getWidth() / 2 - 90, sh_base_line);
        towerPath.lineTo(getWidth() / 2 - 20, sh_base_line - 120 + 30);
        towerPath.moveTo(getWidth() / 2 - 110, sh_base_line);
        towerPath.lineTo(getWidth() / 2 - 30, sh_base_line - 120 + 20);

        for (int i = 0; i <= 3; i++) {
            int offset = 8;
            if (i == 0 || i == 3) {
                offset = 8;
            } else {
                offset = 0;
            }
            towerPath.moveTo(getWidth() / 2 - 30 + i * 20, sh_base_line - 120 - 40 + offset);
            towerPath.lineTo(getWidth() / 2 - 30 + i * 20, sh_base_line - 120 - 40 - 300 - offset * 2);
        }
        towerPath.addCircle(getWidth() / 2, sh_base_line - 120 - 340 - 40, 40, Path.Direction.CCW);
        towerPath.moveTo(getWidth() / 2 - 35, sh_base_line - 120 - 340 - 40 - 10);
        towerPath.lineTo(getWidth() / 2 + 35, sh_base_line - 120 - 340 - 40 - 10);
        towerPath.moveTo(getWidth() / 2 - 35, sh_base_line - 120 - 340 - 40 + 10);
        towerPath.lineTo(getWidth() / 2 + 35, sh_base_line - 120 - 340 - 40 + 10);
        for (int i = 0; i <= 1; i++) {
            towerPath.moveTo(getWidth() / 2 - 10 + i * 20, sh_base_line - 120 - 340 - 40 - 40);
            towerPath.lineTo(getWidth() / 2 - 10 + i * 20, sh_base_line - 120 - 340 - 40 - 40 - 60);
        }
        towerPath.addCircle(getWidth() / 2, sh_base_line - 120 - 340 - 40 - 40 - 60 - 20, 20, Path.Direction.CCW);
        towerPath.moveTo(getWidth() / 2, sh_base_line - 120 - 340 - 40 - 40 - 60 - 20 - 20);
        towerPath.lineTo(getWidth() / 2, sh_base_line - 120 - 340 - 40 - 40 - 60 - 20 - 20 - 50);

        pathMeasure.setPath(path, false);
        Log.i("fx_onsizechange", pathMeasure.getLength() + "");
    }

    @Override
    public void onClick(View v) {
        Log.i("fx", "getHeight()-->" + getHeight() + "getWidth()-->" + getWidth());
//        scrollTo(0, getHeight() / 2);
        if (animator.isRunning()) {
            return;
        }
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        setMeasuredDimension(widthSize, heightSize * 2);
    }
}