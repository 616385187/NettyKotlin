package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.fx.nettykotlin.R;

public class CanvasView extends View {
    private Bitmap mBitmap;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.saber);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.translate(100,100);
//        canvas.drawBitmap(mBitmap, 0, 0, null);
//        canvas.translate(-100,-100);
//        canvas.drawRect(0,0,100,100,new Paint());

        canvas.drawBitmap(mBitmap, 0, 0, null);
//        canvas.translate(100,100);
//        canvas.drawRect(0,0,100,100,new Paint());
    }
}
