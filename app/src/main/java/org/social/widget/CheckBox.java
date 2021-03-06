package org.social.widget;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by caowenhua on 2015/11/1.
 */
public class CheckBox extends View implements View.OnClickListener{

    private int tickColor;
    private int circleUncheckColor;
    private int circleCheckColor;
    private int defaultSize;
    private float density;
    private Paint paint;
    private int devWidth;
    private int width;
    private int height;
    private boolean isChecked;


    public CheckBox(Context context) {
        super(context);
    }

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width  = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        devWidth = ((w > h)? w:h - Math.round(5 * density)) / 2;
    }

    private void init(){
        density = Resources.getSystem().getDisplayMetrics().density;
        defaultSize = Math.round(40 * density);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        tickColor = Color.parseColor("#02c069");
        circleCheckColor = Color.parseColor("#e1e1e1");
        circleUncheckColor = Color.parseColor("#f9f9f9");
    }

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureSize(int measureSpec){
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    public void onClick(View v) {

    }

    public void setTickColor(int tickColor) {
        this.tickColor = tickColor;
    }

    public void setCircleUncheckColor(int circleUncheckColor) {
        this.circleUncheckColor = circleUncheckColor;
    }

    public void setCircleCheckColor(int circleCheckColor) {
        this.circleCheckColor = circleCheckColor;
    }


    private void startAnimation(){
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator colorAnim = ValueAnimator.ofObject(new ColorEvalutor(), "#068bcf", "#02c069");
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        colorAnim.setDuration(1000);
        colorAnim.setRepeatCount(-1);
        colorAnim.start();

    }

    private class ColorEvalutor implements TypeEvaluator {
        private int mCurrentRed = -1;
        private int mCurrentGreen = -1;
        private int mCurrentBlue = -1;
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            String startColor = (String) startValue;
            String endColor = (String) endValue;
            int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
            int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
            int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
            int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
            int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
            int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
            if (mCurrentRed == -1) {
                mCurrentRed = startRed;
            }
            if (mCurrentGreen == -1) {
                mCurrentGreen = startGreen;
            }
            if (mCurrentBlue == -1) {
                mCurrentBlue = startBlue;
            }
            // the diff of color
            int redDiff = Math.abs(startRed - endRed);
            int greenDiff = Math.abs(startGreen - endGreen);
            int blueDiff = Math.abs(startBlue - endBlue);
            int colorDiff = redDiff + greenDiff + blueDiff;
            if (mCurrentRed != endRed) {
                mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                        fraction);
            } else if (mCurrentGreen != endGreen) {
                mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                        redDiff, fraction);
            } else if (mCurrentBlue != endBlue) {
                mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                        redDiff + greenDiff, fraction);
            }

            // compute the color and return String
            String currentColor = "#" + getHexString(mCurrentRed)
                    + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
            return currentColor;
        }

        private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                    int offset, float fraction) {
            int currentColor;
            if (startColor > endColor) {
                currentColor = (int) (startColor - (fraction * colorDiff - offset));
                if (currentColor < endColor) {
                    currentColor = endColor;
                }
            } else {
                currentColor = (int) (startColor + (fraction * colorDiff - offset));
                if (currentColor > endColor) {
                    currentColor = endColor;
                }
            }
            return currentColor;
        }

        /**
         * 10 -> 16
         */
        private String getHexString(int value) {
            String hexString = Integer.toHexString(value);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            return hexString;
        }
    }

    private class Arc{
        float radius;
        float width;
        float startDegree;
        float sweepDegree;
        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getStartDegree() {
            return startDegree;
        }

        public float getSweepDegree() {
            return sweepDegree;
        }

        public void setStartDegree(float startDegree) {
            this.startDegree = startDegree;
        }

        public void setSweepDegree(float sweepDegree) {
            this.sweepDegree = sweepDegree;
        }
    }
}
