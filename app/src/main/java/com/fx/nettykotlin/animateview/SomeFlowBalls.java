package com.fx.nettykotlin.animateview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;


/**
 * 1.小球碰撞动画
 * 2.颜色渐变
 * 3.小球靠近会有黏连的感觉
 */
public class SomeFlowBalls extends View implements View.OnClickListener {
    //可自定义属性 AttributeSet
    private int ballCount;
    //属性
    int w, h;
    Paint paint;
    private float angle;
    private float[][] ballPos;//保存小球位置
    private int[] mColors = {Color.parseColor("#fecfef"), Color.parseColor("#fecfef")};
    private LinearGradient[] linearGradient;//渐变
    private Path[] paths;//运动路径

    PathMeasure pathMeasure;
    private Path dst = new Path();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private float sballradius;

    private int step = 1;

    private ValueAnimator animator;
    private ValueAnimator animator1;

    Path flowPath;
    Path workPath;

    public SomeFlowBalls(Context context) {
        super(context);
    }

    public SomeFlowBalls(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ballCount = 5;
        paint = new Paint();
        ballPos = new float[ballCount + 1][2];
        linearGradient = new LinearGradient[ballCount + 2];
        paths = new Path[ballCount + 1];
        paint.setAntiAlias(true);
        pathMeasure = new PathMeasure();
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1500);
        animator1 = ValueAnimator.ofFloat(0, 1);
        animator1.setDuration(1500);
        animator.addUpdateListener(v -> {
            offset = (float) v.getAnimatedValue();
            if (offset > 0.4 && !animator1.isRunning()) {
                animator1.start();
            }
            invalidate();
        });
        animator1.addUpdateListener(v -> {
            offset1 = (float) v.getAnimatedValue();
            invalidate();
        });
        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator a) {
                offset1 = 0;
                step += 1;
                step %= (ballCount + 1);
//                step = 0;
                if (!animator.isRunning())
                    animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        setOnClickListener(this);
        flowPath = new Path();
        workPath = new Path();
    }

    public SomeFlowBalls(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float offset;
    float offset1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //小球分布圆
        paint.reset();
        flowPath.reset();
        workPath.reset();
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(w / 2, h / 2, w / 3, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(linearGradient[linearGradient.length - 1]);
        //中心小圆
        canvas.drawCircle(ballPos[ballPos.length - 1][0], ballPos[ballPos.length - 1][1], sballradius, paint);
        //画小球
        for (int i = 0; i < ballCount; i++) {
            float x = ballPos[i][0];
            float y = ballPos[i][1];
            paint.setShader(linearGradient[i]);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, sballradius, paint);
//            if (i < paths.length) {
//                paint.setShader(null);
//                paint.setStyle(Paint.Style.STROKE);
//                canvas.drawPath(paths[i], paint);
//            }
        }
        //开始动画
        float l;
        switch (step) {
            case 0:
                pathMeasure.setPath(paths[0], false);
                l = pathMeasure.getLength();
                pathMeasure.getPosTan(l * offset, pos, tan);
                ballPos[0][0] = pos[0];
                ballPos[0][1] = pos[1];
                pathMeasure.setPath(paths[ballPos.length - 1], false);
                l = pathMeasure.getLength();
                pathMeasure.getPosTan(l * (1 - offset1), pos, tan);
                ballPos[ballPos.length - 1][0] = pos[0];
                ballPos[ballPos.length - 1][1] = pos[1];
//                Log.e("fx","l-->"+l+"pos"+Arrays.toString(pos)+"offset-->"+offset1);

                workPath.moveTo(ballPos[0][0], ballPos[0][1]);
                workPath.lineTo(ballPos[ballPos.length - 1][0], ballPos[ballPos.length - 1][1]);
                pathMeasure.setPath(workPath, false);
                if (pathMeasure.getPosTan(pathMeasure.getLength() * 0.5f, pos, tan)) {
                    flowPath.moveTo(ballPos[0][0], ballPos[0][1] - sballradius);
                    flowPath.quadTo(pos[0], pos[1], ballPos[ballPos.length - 1][0], ballPos[ballPos.length - 1][1] - sballradius);
                    flowPath.lineTo(ballPos[ballPos.length - 1][0], ballPos[ballPos.length - 1][1] + sballradius);
                    flowPath.quadTo(pos[0], pos[1], ballPos[0][0], ballPos[0][1] + sballradius);
                    flowPath.close();
//                    canvas.drawCircle(pos[0], pos[1], 10, paint);
                }
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setStrokeWidth(5);
                canvas.drawPath(flowPath, paint);
//                if (offset > 0.4 && offset1 < 0.6)
//                    canvas.drawPath(flowPath, paint);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                pathMeasure.setPath(paths[step], false);
                l = pathMeasure.getLength();
                pathMeasure.getPosTan(l * offset, pos, tan);
                ballPos[step][0] = pos[0];
                ballPos[step][1] = pos[1];
                pathMeasure.setPath(paths[step - 1], false);
                l = pathMeasure.getLength();
                pathMeasure.getPosTan(l * (1 - offset1), pos, tan);
                ballPos[step - 1][0] = pos[0];
                ballPos[step - 1][1] = pos[1];

                workPath.moveTo(ballPos[step][0], ballPos[step][1]);
                workPath.lineTo(ballPos[step-1][0], ballPos[step-1][1]);
                pathMeasure.setPath(workPath, false);
                if (pathMeasure.getPosTan(pathMeasure.getLength() * 0.5f, pos, tan)) {
                    flowPath.moveTo(ballPos[step][0], ballPos[step][1] - sballradius);
                    flowPath.quadTo(pos[0], pos[1], ballPos[step-1][0], ballPos[step-1][1] - sballradius);
                    flowPath.lineTo(ballPos[step - 1][0], ballPos[step - 1][1] + sballradius);
                    flowPath.quadTo(pos[0], pos[1], ballPos[step][0], ballPos[step][1] + sballradius);
                    flowPath.close();
                }
                canvas.drawPath(flowPath, paint);
//                canvas.drawPath(workPath, paint);
//                break;
//            case 6:
//                pathMeasure.setPath(paths[step], false);
//                l = pathMeasure.getLength();
//                pathMeasure.getPosTan(l * offset, pos, tan);
//                ballPos[step][0] = pos[0];
//                ballPos[step][1] = pos[1];
//                pathMeasure.setPath(paths[0], false);
//                l = pathMeasure.getLength();
//                pathMeasure.getPosTan(l * (1 - offset1), pos, tan);
//                ballPos[0][1] = pos[1];
//                break;


        }
//
        Log.i("fx_ondraw_" + (ballPos.length - 1), Arrays.toString(ballPos[ballPos.length - 1]));
        Log.i("fx_ondraw_0", Arrays.toString(ballPos[0]));
        Log.i("fx_ondraw_1", Arrays.toString(ballPos[1]) + "step-->" + step);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //默认再屏幕中间绘制,
        this.w = getWidth();
        this.h = getHeight();
        angle = 360 / (ballCount + 1);
        sballradius = w / 20f;
        for (int i = 0; i < ballCount + 1; i++) {
            float x = getXYByAngle(angle * i)[0];
            float y = getXYByAngle(angle * i)[1];
            ballPos[i][0] = x;
            ballPos[i][1] = y;
            linearGradient[i] = new LinearGradient(ballPos[i][0] - sballradius, ballPos[i][1] - sballradius, ballPos[i][0] + sballradius, ballPos[i][1] + sballradius, mColors, null, Shader.TileMode.CLAMP);
            if (i < paths.length) {
                paths[i] = new Path();
                paths[i].moveTo(x, y);
                paths[i].quadTo(x + 120, y + 100, w / 2, h / 2);
            }
        }
        //中心圆
        linearGradient[linearGradient.length - 1] = new LinearGradient(w / 2 - sballradius, h / 2 - sballradius, w / 2 + sballradius, h / 2 + sballradius, mColors, null, Shader.TileMode.CLAMP);
        ballPos[ballPos.length - 1][0] = w / 2;
        ballPos[ballPos.length - 1][1] = h / 2;

        Log.i("fx", "onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    //        x1   =   x0   +   r   *   cos(a   *   PI   /180  )
    //        y1   =   y0   +   r   *   sin(a   *   PI  /180   ) 这个不会我也没ban法，我也不会。。
    public float[] getXYByAngle(float angle) {
        float args[] = new float[2];
        args[0] = w / 2f + w / 3f * (float) Math.cos(angle * Math.PI / 180);
        args[1] = h / 2f + w / 3f * (float) Math.sin(angle * Math.PI / 180);
        return args;
    }

    @Override
    public void onClick(View v) {
//        if (animator.isRunning()||animator1.isRunning()){
//            return;
//        }
        animator.start();
        step = 0;
    }
}
