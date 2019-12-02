package com.fx.nettykotlin.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class LayerView extends View {


    private  Camera camera;
    private  LinearGradient linearGradient;
    private Paint paint;

    private Point centerPoint ;
    private  int pro = 0;
    public LayerView(Context context) {
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

    public LayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint  = new Paint();
        paint.setColor(Color.BLUE);
        camera = new Camera();
    }

    public LayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Log.i("fx_onMeasure","width-->"+MeasureSpec.getSize(widthMeasureSpec)+"height-->"+MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        RectF rect1 = new RectF(centerPoint.x-100,centerPoint.x-100,centerPoint.x+100,centerPoint.x+100);
        RectF rect2 = new RectF(centerPoint.x-50,centerPoint.x-50,centerPoint.x+50,centerPoint.x+50);

//        RectF rect3 = new RectF(100,100,300,500);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect1,paint);
        paint.reset();
        paint.setColor(Color.parseColor("#FFBDCB"));
        canvas.saveLayer(0,0,getWidth(),getHeight(),null);
        canvas.translate(30,0);
        canvas.drawRect(rect1,paint);
        canvas.rotate(45,centerPoint.x,centerPoint.x);
        paint.setColor(Color.parseColor("#C7254E"));
        canvas.drawRect(rect1,paint);

        canvas.restore();

        canvas.save();
        paint.setColor(Color.YELLOW);
        canvas.rotate(45,centerPoint.x,centerPoint.x);
        canvas.drawRect(rect1,paint);
        canvas.translate(30,0);
        paint.setColor(Color.parseColor("#E56C57"));
        canvas.drawRect(rect1,paint);
//        canvas.saveLayer(rect2,null);
        canvas.restore();
        paint.setColor(Color.parseColor("#72CFFF"));
        canvas.drawRect(rect2,paint);

        float x = getWidth() / 2f;
        float y = getHeight()/3f*2f;

        RectF rect3 = new RectF(x-100f,y-100f,x+100f,y+100f);
        RectF rect4 = new RectF(x-50,y-50,x+50,y+50);



        canvas.save();
        Matrix m = new Matrix();
        m.postRotate(45,x,y);
        m.preTranslate(30,0);
        canvas.concat(m);
        paint.setColor(Color.parseColor("#45494A"));
        canvas.drawRect(rect4,paint);
        canvas.restore();

        canvas.save();
        Matrix m1 = new Matrix();
        m.preTranslate(30,0);
        m.postRotate(45,x,y);
        canvas.concat(m1);
        paint.setColor(Color.parseColor("#45494A"));
        canvas.drawRect(rect3,paint);
        canvas.restore();


        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect3,paint);
        paint.setColor(Color.parseColor("#FFFF00"));
        canvas.drawRect(rect4,paint);

        paint.reset();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPoint = new Point(w/2,h/2);
        Log.i("fx_onSizeChanged","w-->"+w+"h-->"+h+"oldw-->"+oldw+"oldh-->"+oldh);
    }
}
