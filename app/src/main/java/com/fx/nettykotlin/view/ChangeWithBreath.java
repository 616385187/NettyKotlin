package com.fx.nettykotlin.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 1.圆怎么变成方形
 */

public class ChangeWithBreath extends View implements View.OnClickListener {
    private Paint paint;

    private int breathColor;

    int red = 120, green = 120, blue = 200;
    int step = 0;
    ValueAnimator animator;
    int oldValue;

    public ChangeWithBreath(Context context) {
        super(context);
    }

    public ChangeWithBreath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        breathColor = Color.rgb(red, green, blue);
        paint.setColor(breathColor);
        animator = ValueAnimator.ofInt(0, 100);
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
                    if (green<220) {
                        green += 1;
                    }
                    break;
                case 2:
                    if (green > 120) {
                        green -= 1;
                    }
                    if (blue>100) {
                        blue -= 1;
                    }
                    break;
            }
            breathColor = Color.rgb(red, green, blue);
            paint.setColor(breathColor);
            oldValue = value;
            Log.i("fx", "red-->" + red + "green-->" + green + "blue-->" + blue + "animation-->" + animation.getAnimatedValue());
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                Log.i("fx", "onAnimationEnd");
                step += 1;
                step = step % 3;
                animator.start();
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

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
    }

    @Override
    public void onClick(View v) {
        animator.start();
    }
}
