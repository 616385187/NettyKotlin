package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PathTestView extends View {
    Path path;
    Path pathsub;
    Paint p;
    RectF rectF;
    RectF rectFtem;
    PathMeasure pathMeasure;

    public PathTestView(Context context) {
        super(context);
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        pathsub = new Path();
        rectF = new RectF();
        rectFtem = new RectF();
        p = new Paint();
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.STROKE);
        pathMeasure = new PathMeasure();


    }

    public PathTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawPath(path, p);
//        canvas.drawPath(pathsub, p);
//
        p.setStyle(Paint.Style.STROKE);
        p.setShader(null);
        canvas.drawPath(path, p);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        pathsub.computeBounds(rectFtem,true);
        canvas.drawRect(rectFtem,p);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawPath(pathsub, p);


    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.left = 10;
        rectF.top = 10;
        rectF.right = 310;
        rectF.bottom = 310;


        path.moveTo(10, 10);
        path.lineTo(310, 10);
        path.lineTo(310, 310);
        path.lineTo(10, 310);
        path.lineTo(10, 10);
        pathMeasure.setPath(path, false);

        pathsub.moveTo(rectF.centerX(), rectF.centerY());
        pathsub.lineTo(rectF.centerX() + 300, rectF.centerY());
        pathsub.lineTo(rectF.centerX() + 300, rectF.centerY() + 300);
        pathsub.lineTo(rectF.centerX(), rectF.centerY() + 300);
        pathsub.lineTo(rectF.centerX(), rectF.centerY());

//        LinearGradient a = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom, Color.WHITE, Color.BLUE, Shader.TileMode.CLAMP);
        rectF.offset(rectF.width() / 2, rectF.height() / 2);
//        LinearGradient b = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom, Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
//
//        ComposeShader c = new ComposeShader(a, b, PorterDuff.Mode.OVERLAY);
//        p.setShader(c);
//        p.setStyle(Paint.Style.FILL);
    }
}
