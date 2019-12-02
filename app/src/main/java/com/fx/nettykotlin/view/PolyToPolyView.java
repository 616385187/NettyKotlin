package com.fx.nettykotlin.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.fx.nettykotlin.R;

import java.util.Arrays;

public class PolyToPolyView extends View {

    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg1);
    private Matrix matrix = new Matrix();
    private int mNum = 10;
    private Matrix[] matrices = new Matrix[mNum];
    int value = 40;
    public PolyToPolyView(Context context) {
        super(context);
    }

    public PolyToPolyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//       final ValueAnimator animator =  ValueAnimator.ofInt(40,0);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                value = (int)animation.getAnimatedValue();
//            }
//        });
//        this.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animator.start();
//            }
//        },2000);
//        float[] src = {0, 0,
//                bitmap.getWidth(), 0,
//                bitmap.getWidth(), bitmap.getHeight(),
//                0, bitmap.getHeight()
//        };
//
//        float[] dst = {0, 0,
//                bitmap.getWidth(), 400,
//                bitmap.getWidth(), bitmap.getHeight()-200,
//                0, bitmap.getHeight()
//        };
//
//        matrix.setPolyToPoly(src,0,dst,0,src.length>>1);
//        matrix.postScale(0.8f,0.8f);
//        matrix.preTranslate(0,100);
        doSomething();

    }

    private void doSomething() {
        int subW = bitmap.getWidth() / mNum;
        int bh = bitmap.getHeight();
        for (int i = 0; i < mNum; i++) {
            matrices[i] = new Matrix();
            float[] src = {i * subW , 0,
                    subW *(1 + i), 0,
                    subW *(1 + i), bh ,
                    i * subW, bitmap.getHeight()
            };
            boolean flag = i %2 == 0;
            float[] dst = {  i * subW ,flag ? 0 : value,
                    subW *(1 + i), !flag ?  0 : value,
                    subW *(1 + i), !flag ? bh : bh - value ,
                    i * subW, flag ? bh : bh - value
            };
            matrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);

            Log.i("fx",matrices[i].toString());
            Log.i("fx", Arrays.toString(src));
            Log.i("fx", Arrays.toString(dst));
        }
    }

    public PolyToPolyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PolyToPolyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int j = 0; j < mNum; j++) {
            canvas.save();
            canvas.concat(matrices[j]);
            canvas.clipRect(bitmap.getWidth()/mNum * j,0,(1+j)*bitmap.getWidth()/mNum,bitmap.getHeight());
            canvas.drawBitmap(bitmap,0,0, null);
            Log.i("fx_onDraw",matrices[j].toString());
            canvas.restore();
        }

//        if (value == 0 ){
//            value = 40;
//        }else {
//            value -= 0.1;
//        }
//        doSomething();
//        invalidate();
    }
}
