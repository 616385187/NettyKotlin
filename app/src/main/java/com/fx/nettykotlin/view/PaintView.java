package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintView extends View {


    private  LinearGradient linearGradient;
    private Paint paint;

    private  int pro = 0;
    public PaintView(Context context) {
        super(context);
    }

    /**
     * Paint.ANTI_ALIAS_FLAG ：抗锯齿标志
     * Paint.FILTER_BITMAP_FLAG : 使位图过滤的位掩码标志
     * Paint.DITHER_FLAG : 使位图进行有利的抖动的位掩码标志
     * Paint.UNDERLINE_TEXT_FLAG : 下划线
     * Paint.STRIKE_THRU_TEXT_FLAG : 中划线
     * Paint.FAKE_BOLD_TEXT_FLAG : 加粗
     * Paint.LINEAR_TEXT_FLAG : 使文本平滑线性扩展的油漆标志
     * Paint.SUBPIXEL_TEXT_FLAG : 使文本的亚像素定位的绘图标志
     * Paint.EMBEDDED_BITMAP_TEXT_FLAG : 绘制文本时允许使用位图字体的绘图标志
     * ————————————————
     * @param context
     * @param attrs
     */

    /**
     * Create a shader that draws a linear gradient along a line.
     *
     * @param x0       The x-coordinate for the start of the gradient line
     * @param y0       The y-coordinate for the start of the gradient line
     * @param x1       The x-coordinate for the end of the gradient line
     * @param y1       The y-coordinate for the end of the gradient line
     * @param color0   The color at the start of the gradient line.
     * @param color1   The color at the end of the gradient line.
     * @param tile     The Shader tiling mode
     */
    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint  = new Paint();
        paint.setColor(Color.BLUE);

        linearGradient = new LinearGradient(0,0,800,800,Color.DKGRAY,Color.BLUE, Shader.TileMode.CLAMP);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * <p>
     * Measure the view and its content to determine the measured width and the
     * measured height. This method is invoked by {@link #measure(int, int)} and
     * should be overridden by subclasses to provide accurate and efficient
     * measurement of their contents.
     * </p>
     *
     * <p>
     * <strong>CONTRACT:</strong> When overriding this method, you
     * <em>must</em> call {@link #setMeasuredDimension(int, int)} to store the
     * measured width and height of this view. Failure to do so will trigger an
     * <code>IllegalStateException</code>, thrown by
     * {@link #measure(int, int)}. Calling the superclass'
     * {@link #onMeasure(int, int)} is a valid use.
     * </p>
     *
     * <p>
     * The base class implementation of measure defaults to the background size,
     * unless a larger size is allowed by the MeasureSpec. Subclasses should
     * override {@link #onMeasure(int, int)} to provide better measurements of
     * their content.
     * </p>
     *
     * <p>
     * If this method is overridden, it is the subclass's responsibility to make
     * sure the measured height and width are at least the view's minimum height
     * and width ({@link #getSuggestedMinimumHeight()} and
     * {@link #getSuggestedMinimumWidth()}).
     * </p>
     *
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent.
     *                          The requirements are encoded with
     *                          {@link MeasureSpec}.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *                          The requirements are encoded with
     *                          {@link MeasureSpec}.
     * @see #getMeasuredWidth()
     * @see #getMeasuredHeight()
     * @see #setMeasuredDimension(int, int)
     * @see #getSuggestedMinimumHeight()
     * @see #getSuggestedMinimumWidth()
     * @see MeasureSpec#getMode(int)
     * @see MeasureSpec#getSize(int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.reset();
        canvas.drawCircle(50,50,50,paint);

        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(250,50,50,paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(20);
        canvas.drawCircle(450,50,50,paint);

        paint.reset();
        paint.setTextSize(30);
        String text = "abcdefg";
        Paint.FontMetrics metrics = paint.getFontMetrics();

        paint.setColor(Color.GREEN);
        canvas.drawLine(0,metrics.top+200,500,metrics.top+200,paint);
        paint.setColor(Color.RED);
        canvas.drawLine(0,metrics.ascent+200,500,metrics.ascent+200,paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(0,200,500,200,paint);
        paint.setColor(Color.GRAY);
        canvas.drawLine(0,metrics.descent+200,500,metrics.descent+200,paint);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(0,metrics.bottom+200,500,metrics.bottom+200,paint);


        canvas.drawText(text+"一代宗师",0,200,paint);
        Rect rect = new Rect();
        paint.getTextBounds(text+"一代宗师",0,text.length()+4,rect);

        rect.offset(0,200);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,paint);

        Log.i("fx_metrics","top-->"+metrics.top+"ascent-->"+metrics.ascent+"descent-->"+metrics.descent+"bottom-->"+metrics.bottom+rect);

        paint.reset();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawCircle(getWidth()/2,getHeight()/2,50,paint);
        paint.setColor(Color.RED);
//        canvas.drawCircle(getWidth()/2,getHeight()/2,50,paint);


        RectF rectF = new RectF(getWidth()/2-50,getHeight()/2-50,getWidth()/2+50,getHeight()/2+50);
        canvas.drawArc(rectF,0,360*pro/100,false,paint);
        paint.setShader(linearGradient);

        canvas.drawRect(rectF,paint);
        pro+=1;
        invalidate();
        if (pro==100){
            pro = 0;
        }
    }
}
