package com.example.thinkpad.icompetition.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.thinkpad.icompetition.R;

/**
 * Created by a'su's on 2018/7/12.
 */

public class BottomImageView extends AppCompatImageView {
    private Paint mPaint;
    private Bitmap mSelectedIcon;
    private Bitmap mNormalIcon;
    private Rect mSelectedRect;
    private Rect mNormalRect;
    private int mSelectedAlpha = 0;

    public BottomImageView(Context context) {
        super(context);
    }

    public BottomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public final void setImages(int normal, int selected) {
        this.mNormalIcon = createBitmap(normal);                            // 拿到原图
        this.mSelectedIcon = createBitmap(selected);
        int width = (int) getResources().getDimension(R.dimen.tab_image_weith);
        int heigh = (int) getResources().getDimension(R.dimen.tab_image_heigh);
        this.mNormalRect = new Rect(0, 0, width, heigh);                   //拿到画板的大小
        this.mSelectedRect = new Rect(0, 0, width, heigh);
        this.mPaint = new Paint();                                         // 拿到画笔
    }

    private Bitmap createBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaint == null) {
            return;
        }
        this.mPaint.setAlpha(255 - this.mSelectedAlpha);
        canvas.drawBitmap(this.mNormalIcon, null, this.mNormalRect, this.mPaint); //开始在画板上画原图
        // 也就是在这个控件上画bitmap
        this.mPaint.setAlpha(this.mSelectedAlpha);
        canvas.drawBitmap(this.mSelectedIcon, null, this.mSelectedRect, this.mPaint);
    }

    public final void changeSelectedAlpha(int alpha) {

    }

    /**
     * 当滑动的时候来调用此方法
     *
     * @param offset
     */
    public final void transformPage(float offset) {
        this.mSelectedAlpha = (int) (255 * (1 - offset));
        invalidate();  // 此方法调用就会从新走 onDraw方法
    }
}
