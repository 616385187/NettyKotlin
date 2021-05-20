package com.fx.nettykotlin.animateview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.fx.nettykotlin.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageView extends View {
    private List<Bitmap> bitmaps;

    private Path clipPath;
    private Paint paint;
    private GestureDetector gestureDetector;

    float pagePointX, pagePointY;
    Matrix matrix = new Matrix();

    Matrix[] matrices = new Matrix[3];

    float src[] = new float[8];
    float dst[] = new float[8];

    public PageView(Context context) {
        super(context);
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmaps = new ArrayList<>();
        clipPath = new Path();
        paint = new Paint();

        matrices [0] = new Matrix();
        matrices [1] = new Matrix();
        matrices [2] = new Matrix();

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                pagePointX = pagePointX - distanceX;
                pagePointY = pagePointY - distanceY;
                Log.i("fx_onScroll", "distanceX-->" + pagePointX + "\tdistanceY-->" + pagePointY+"getWidth() - pagePointX->"+(getWidth() - pagePointX));
                invalidate();
                dst[4] = pagePointX;
                dst[5] = pagePointY;
                dst[6] = pagePointX;
                clipPath.moveTo(dst[4], dst[5]);
                clipPath.quadTo(pagePointX / 2 ,getHeight()-getWidth()+pagePointX,0,getHeight());
                matrix.setPolyToPoly(src, 0, dst, 0, 4);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("fx_onFling", "velocityX-->" + velocityX + "\tvelocityY-->" + velocityY);
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onContextClick(MotionEvent e) {
                Log.i("fx", "onContextClick");
                return super.onContextClick(e);
            }

        });
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i = 0; i < 3; i++) {
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            if (i == 0)
                bitmap.eraseColor(Color.GREEN);
            if (i == 1)
                bitmap.eraseColor(Color.YELLOW);
            if (i == 2)
                bitmap.eraseColor(Color.GRAY);
            bitmaps.add(bitmap);
        }
        src[0] = 0;
        src[1] = 0;

        src[2] = 0;
        src[3] = getHeight();

        src[4] = getWidth();
        src[5] = getHeight();

        src[6] = getWidth();
        src[7] = 0;
        dst[0] = src[0];
        dst[1] = src[1];
        dst[2] = src[2];
        dst[3] = src[3];
        dst[4] = src[4];
        dst[5] = src[5];
        dst[6] = src[6];
        dst[7] = src[7];
        pagePointX = getWidth();
        pagePointY = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.restore();
        canvas.drawBitmap(bitmaps.get(0), 0, 0, null);
        canvas.drawBitmap(bitmaps.get(1), 0, 0, null);
        canvas.save();
        canvas.clipPath(clipPath, Region.Op.DIFFERENCE);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmaps.get(2), 0, 0, null);
        canvas.restore();

        canvas.drawCircle(pagePointX, pagePointY, 10, paint);

//        canvas.drawPath(clipPath,paint);
        clipPath.reset();

//        canvas.clipPath()
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gestureDetector.onTouchEvent(event);
        } else {
            gestureDetector.onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i("fx", "onSingleTapUp");
            pagePointX = getWidth();
            pagePointY = getHeight();
            dst[4] = pagePointX;
            dst[5] = pagePointY;
            dst[6] = pagePointX;
            Log.i("fx_src",Arrays.toString(src));
            Log.i("fx_dst",Arrays.toString(dst));
            clipPath.reset();
            matrix.setPolyToPoly(src, 0, dst, 0, 4);
            invalidate();
        }
        return true;
    }
}
