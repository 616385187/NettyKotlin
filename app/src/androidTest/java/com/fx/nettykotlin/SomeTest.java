package com.fx.nettykotlin;


import android.graphics.Matrix;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SomeTest {

    @Test
    public void Matrix (){
//        Matrix matrix = new Matrix();
//        Log.i("fx",matrix.toString());
//        matrix.preTranslate(100,100);
//        Log.e("fx",matrix.toString());
//        matrix.postScale(10,10);
//        Log.e("fx",matrix.toString());
//
//
//        Matrix matrix2 = new Matrix();
//        matrix2.postScale(10,10);
//        Log.e("fx_1",matrix2.toString());
//        matrix2.preTranslate(100,100);
//        Log.e("fx_1",matrix2.toString());




        Matrix matrix3 = new Matrix();
        Log.e("fx_1",matrix3.toString());
        matrix3.preTranslate(100,100);
        Log.e("fx_1",matrix3.toString());
        matrix3.setScale(10,10);
//        Log.e("fx_1",matrix3.toString());
//        matrix3.postScale(10,10);
        Log.e("fx_1",matrix3.toString());
        matrix3.preTranslate(-100,-100);
        Log.e("fx_1",matrix3.toString());
        assert ("1".equals("2"));

    }
}
