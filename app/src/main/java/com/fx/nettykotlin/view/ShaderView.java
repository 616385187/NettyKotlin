package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShaderView extends View {

    private int[] mColors = {Color.BLACK, Color.WHITE};
    private Paint p ;
    public ShaderView(Context context) {
        super(context);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawCircle(900f,777f,45f,p);

//        canvas.drawRect(900.0f-45f,777.0f-45f ,900.0f+45f ,777.0f+45f,p);

        LinearGradient l =
                new LinearGradient(900.0f-45f,777.0f-45f ,900.0f+45f ,777.0f+45f ,mColors,null, Shader.TileMode.CLAMP);

        p.setShader(l);
//        p.setColor(Color.BLUE);
//        canvas.drawRect(0,0,getWidth(),getHeight(),p);
//        canvas.drawRect(0,0,800,800,p);
        p.reset();
        p.setShader(l);
//        canvas.drawRect(400,400,800,800,p);

//        canvas.drawRect(900.0f-45f,777.0f-45f ,900.0f+45f ,777.0f+45f,p);
        canvas.drawCircle(900f,777f,45f,p);

        canvas.drawPoint(900-45f,777-45f,p);
        canvas.drawPoint(900.0f+45f ,777.0f+45f,p);

    }
}
