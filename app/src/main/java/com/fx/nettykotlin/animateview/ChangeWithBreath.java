package com.fx.nettykotlin.animateview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 1.圆怎么变成方形
 * 2.想要圆润
 * 3.圆->方 ->三角->方->圆
 */

public class ChangeWithBreath extends View implements View.OnClickListener {
    public static final float distance = 200;
    public static float distanceOffset = 0;
    public static final float finalRadio = 0.85f;
    public static float radio = 0.85f;
    private Paint paint;

    private int breathColor;

    int red = 120, green = 120, blue = 200;
    int step = 0;
    ValueAnimator animator;
    int oldValue;

    Path path;
    float[][] points;
    int changeStep;
    ValueAnimator changeAnimator;

    Matrix matrix;

    public ChangeWithBreath(Context context) {
        super(context);
    }

    public ChangeWithBreath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        path = new Path();
        matrix = new Matrix();
        breathColor = Color.rgb(red, green, blue);
        paint.setColor(breathColor);
        animator = ValueAnimator.ofInt(0, 100);
        points = new float[4][2];
        animator.setDuration(5000);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            if (value == oldValue) {
                return;
            }
            switch (step) {
                case 0:
                    if (red < 220) {
                        red += 1;
                    }
                    if (blue < 200) {
                        blue += 1;
                    }
                    break;
                case 1:
                    if (red > 120) {
                        red -= 1;
                    }
                    if (green < 220) {
                        green += 1;
                    }
                    break;
                case 2:
                    if (green > 120) {
                        green -= 1;
                    }
                    if (blue > distance) {
                        blue -= 1;
                    }
                    break;
            }
            breathColor = Color.rgb(red, green, blue);
            paint.setColor(breathColor);
            oldValue = value;
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                step += 1;
                step = step % 3;
                animator.start();
            }
        });
        changeAnimator = ValueAnimator.ofInt(0, 100);
        changeAnimator.setDuration(5000);
        changeAnimator.addUpdateListener(v -> {
            float progress = ((int) v.getAnimatedValue()) / 100f;
            if (radio > 0 && changeStep == 0)
                radio = finalRadio * (1f - progress);

            if (radio < finalRadio && changeStep == 3)
                radio = finalRadio * progress;
            if (changeStep == 1 || changeStep == 2) {
                distanceOffset = distance * progress;
                if (changeStep == 1)
                matrix.setRotate(progress * 360+180);
                if (changeStep == 2)
                matrix.setRotate(progress * 360+180);
            }
            invalidate();
            Log.i("fx_" + changeStep, "distanceOffset-->" + distanceOffset + "progress--->" + progress + "changeStep-->" + changeStep);
        });
        changeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                changeStep += 1;
                distanceOffset = 0;
                changeStep %= 4;
                animation.setStartDelay(1000);
                animation.start();
            }
        });
        setOnClickListener(this);
    }

    public ChangeWithBreath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        if (changeStep == 0 || changeStep == 3) {
            path.moveTo(points[0][0], points[0][1]);
            path.quadTo(points[0][0] + distance, points[0][1] - distance * radio, points[1][0], points[1][1]);
            path.quadTo(points[1][0] + distance * radio, points[1][1] + distance, points[2][0], points[2][1]);
            path.quadTo(points[2][0] - distance, points[2][1] + distance * radio, points[3][0], points[3][1]);
            path.quadTo(points[3][0] - distance * radio, points[3][1] - distance, points[0][0], points[0][1]);
        }
        if (changeStep == 2 || changeStep == 1) {
            if (changeStep == 1) {
                points[0][0] = points[3][0] + distanceOffset;
                points[1][0] = points[2][0] - distanceOffset;
            }
            if (changeStep == 2) {
                points[0][0] = points[3][0] - distanceOffset + distance;
                points[1][0] = points[2][0] + distanceOffset - distance;
            }
            path.moveTo(points[0][0], points[0][1]);
            path.lineTo(points[1][0], points[1][1]);
            path.lineTo(points[2][0], points[2][1]);
            path.lineTo(points[3][0], points[3][1]);
            path.lineTo(points[0][0], points[0][1]);
        }
        canvas.save();
        canvas.translate(getWidth()/2f,getHeight()/2f);
        canvas.concat(matrix);
        canvas.translate(-getWidth()/2f,-getHeight()/2f);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    @Override
    public void onClick(View v) {
        animator.start();
        changeAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        points[0][0] = w / 2 - distance;
        points[0][1] = h / 2 - distance;
        points[1][0] = w / 2 + distance;
        points[1][1] = h / 2 - distance;
        points[2][0] = w / 2 + distance;
        points[2][1] = h / 2 + distance;
        points[3][0] = w / 2 - distance;
        points[3][1] = h / 2 + distance;
    }
}