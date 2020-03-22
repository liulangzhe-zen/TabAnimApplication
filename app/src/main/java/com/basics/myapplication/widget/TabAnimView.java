package com.basics.myapplication.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.basics.myapplication.R;
import com.basics.myapplication.utils.DensityUtils;

public class TabAnimView extends View {

    private static final String TAG = "TabAnimView";

    private static final int COLOR_BG_WHITE = Color.parseColor("#FFFFFF");
    private static final int COLOR_BG_BITMAP= Color.parseColor("#A3A3A3");

    private int width;
    private int height;
    //内容绘制大小
    private int contentSize;
    /**基准线 外部线距离顶上得距离*/
    private int lineToTop;
//   private  Rect rect;
    private  RectF rectF;
    private Bitmap bpSel;
    private int bitmapPadding;
    private Paint bitmapPaint;
    private Paint bitmapBgPaint;
    private Paint paintBg;
    private Paint paintBgStroke;

    /**
     * anim
     */
    private int startValue = 0;
    private int halfValue = -55;
    private int endValue = -35;
    private static final int ANIM_TIME = 300;
    private int value = 0;
    private ValueAnimator animator;


    public TabAnimView(Context context) {
        super(context);
        init(context);
    }

    public TabAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //绘制内容区域
        contentSize = DensityUtils.dp2px(context, 50);
        //线距离顶部距离
        lineToTop = DensityUtils.dp2px(context, 50);
        bitmapPadding = DensityUtils.dp2px(context, 5);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);

        bitmapBgPaint = new Paint();
        bitmapBgPaint.setColor(COLOR_BG_BITMAP);
        bitmapBgPaint.setAntiAlias(true);
        bitmapBgPaint.setStyle(Paint.Style.FILL);

        paintBg = new Paint();
        paintBg.setColor(COLOR_BG_WHITE);
        paintBg.setAntiAlias(true);
        paintBg.setStyle(Paint.Style.FILL);

        paintBgStroke = new Paint();
        paintBgStroke.setColor(context.getResources().getColor(R.color.colorAccent));
        paintBgStroke.setAntiAlias(true);
        paintBgStroke.setStyle(Paint.Style.STROKE);

        halfValue = -DensityUtils.dp2px(context, 35);
        endValue = -DensityUtils.dp2px(context, 25);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF = new RectF((((float)width - (float)contentSize) / 2),
                lineToTop + value,
                (float) width - ((float)width - (float)contentSize) / 2,
                height + value);

//        canvas.drawOval(rectF, paintBg);
        if (value < 0 ) {
            Path path = new Path();
            //绘制第一段圆弧
            path.moveTo(rectF.left - 20, lineToTop);
            float firstCubicHight = ((float) lineToTop - rectF.top) / 4;
            float end = (float) lineToTop - firstCubicHight;
            path.cubicTo(
                    rectF.left - 10,
                    (float) lineToTop - firstCubicHight / 16,
                    rectF.left,
                    (float) lineToTop - firstCubicHight / 12,
                    rectF.left + 10,
                    end
            );

            //绘制第二段圆弧
//        path.moveTo((float) rect.left + 10, end);
            path.quadTo(
                    rectF.left + contentSize / 2,
                    rectF.top * 0.9f,
                    rectF.right - 10,
                    end
            );
            //绘制第三段圆弧，和第一段对称
//        path.moveTo((float) rect.right - 10, end);
            path.cubicTo(
                    rectF.right,
                    (float) lineToTop - firstCubicHight / 12,
                    rectF.right + 10,
                    (float) lineToTop - firstCubicHight / 16,
                    rectF.right + 20,
                    lineToTop
            );

            Path pathBg = new Path();
            pathBg.addPath(path);
//        path.moveTo((float) rect.right + 20, lineToTop);
            pathBg.lineTo(rectF.right + 20, height);
            pathBg.lineTo(rectF.left - 20, height);
            pathBg.lineTo(rectF.left - 20, lineToTop);
            canvas.drawPath(pathBg, paintBg);//绘制白色背景

            canvas.drawPath(path, paintBgStroke);//绘制曲线

        }

//        float v = (float) ( Math.asin(((float) contentSize / 2 + value) / ((float) contentSize / 2))  * 180 / Math.PI);
//        Log.e(TAG, "onDraw: --->>" + v);
//        canvas.drawArc(rectF, 180 + v, 180 - 2 * v, false, paintBgStroke);

        if (bpSel != null) {
            int bpSize = height - lineToTop;

            rectF = new RectF((((float) width - bpSize) / 2) + bitmapPadding,
                     (float)lineToTop + (float) value * 0.6f + bitmapPadding,
                    (float)width - ((float) width - bpSize) / 2 - bitmapPadding,
                    (float)lineToTop + bpSize + (float) value * 0.6f - bitmapPadding);
            canvas.drawOval(rectF, bitmapBgPaint);

            canvas.drawBitmap(bpSel,
                    new Rect(0, 0, bpSel.getWidth(), bpSel.getHeight()),
                    new RectF(rectF.left + bitmapPadding,
                            rectF.top + bitmapPadding,
                            rectF.right - bitmapPadding,
                            rectF.bottom - bitmapPadding),
                    bitmapPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width == 0) {
            width = getMeasuredWidth();
        }
        if (height == 0) {
            height = getMeasuredHeight();
        }
    }

    private void initAnim() {
        value = startValue;
        animator = ValueAnimator.ofInt(startValue, halfValue, endValue);
//        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(ANIM_TIME);
        animator.start();
    }

    public void startAnim() {
        initAnim();
    }

    public void resetAnim() {
        if (animator != null && animator.isRunning()) {
            animator.end();
        }
        value = 0;
        invalidate();
    }

    public void setBitmapRes(int drawableRes) {
        bpSel = BitmapFactory.decodeResource(getContext().getResources(), drawableRes);
        invalidate();
    }
}
