package com.anudeepsamaiya.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anudeepsamaiya on 16/1/17.
 */

public class ProgressView extends View {

    // setup initial color
    private int color = Color.BLACK;

    private Paint progressPaint;

    private int progress;
    private int goal;

    private boolean isGoalReached;

    // height of the goal indicator
    private float goalIndicatorHeight;

    // thickness of the goal indicator
    private float goalIndicatorWidth;

    // bar color when the goal has been reached
    private int goalReachedColor;

    // bar color when the goal has not been reached
    private int goalNotReachedColor;

    // bar color for the unfilled section (remaining progress)
    private int unfilledSectionColor;

    // height of the progress bar
    private float barHeight;
    private float goalIndicatorThickness;
    private int barThickness;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint();

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.GoalProgressBar, 0, 0);
        try {
            setGoalIndicatorHeight(typedArray.getDimensionPixelSize(R.styleable.GoalProgressBar_goalIndicatorHeight, 10));
            setGoalIndicatorThickness(typedArray.getDimensionPixelSize(R.styleable.GoalProgressBar_goalIndicatorThickness, 5));
            setGoalReachedColor(typedArray.getColor(R.styleable.GoalProgressBar_goalReachedColor, Color.BLUE));
            setGoalNotReachedColor(typedArray.getColor(R.styleable.GoalProgressBar_goalNotReachedColor, Color.BLACK));
            setUnfilledSectionColor(typedArray.getColor(R.styleable.GoalProgressBar_unfilledSectionColor, Color.RED));
            setBarThickness(typedArray.getDimensionPixelOffset(R.styleable.GoalProgressBar_barThickness, 4));

        } finally {
            typedArray.recycle();
        }

        progressPaint.setAntiAlias(true); // enable anti aliasing
        progressPaint.setColor(getColor()); // set default color to white
        progressPaint.setDither(true); // enable dithering
        progressPaint.setStyle(Paint.Style.STROKE); // set to STOKE
        progressPaint.setStrokeJoin(Paint.Join.ROUND); // set the join to round you want
        progressPaint.setStrokeCap(Paint.Cap.ROUND);  // set the progressPaint cap to round too
        progressPaint.setPathEffect(new CornerPathEffect(getBarThickness())); // set the path effect
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        int halfWidth = getWidth() / 2;
        int progressEndX = (int) (getWidth() * progress / 100f);


        Path dashPath = new Path();
        dashPath.moveTo(0, halfHeight);
        dashPath.quadTo(halfWidth, halfHeight, getWidth(), halfHeight);
        Paint dashPaint = new Paint();
        dashPaint.setARGB(255, 0, 0, 0);
        dashPaint.setStrokeWidth(barThickness / 12);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setPathEffect(new DashPathEffect(new float[]{5, 5, 5, 5}, 0));
        canvas.drawPath(dashPath, dashPaint);

        /*
        // draw the unfilled portion of the bar
        progressPaint.setColor(unfilledSectionColor);
        canvas.drawLine(progressEndX, halfHeight, getWidth(), halfHeight, progressPaint);

        */

        // draw the filled portion of the bar
        progressPaint.setStrokeWidth(barThickness);
        int color = (progress >= goal) ? goalReachedColor : goalNotReachedColor;
        progressPaint.setColor(color);
        canvas.drawLine(0, halfHeight, progressEndX, halfHeight, progressPaint);
        /*
        // draw goal indicator
        int indicatorPosition = (int) (getWidth() * goal / 100f);
        progressPaint.setColor(goalReachedColor);
        progressPaint.setStrokeWidth(goalIndicatorThickness);

        canvas.drawLine(
                indicatorPosition,
                halfHeight - (goalIndicatorHeight / 2),
                indicatorPosition,
                halfHeight + (goalIndicatorHeight / 2),
                progressPaint);*/

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // set width
        int width = MeasureSpec.getSize(widthMeasureSpec);

        // set height
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                // we must be exactly the given size
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                // we can not be bigger than the specified height
                height = (int) Math.min(goalIndicatorHeight, heightSize);
                break;
            default:
                // we can be whatever height want
                height = (int) goalIndicatorHeight;
                break;
        }

        setMeasuredDimension(width, height);
    }

    public float getBarThickness() {
        return barThickness;
    }

    public int getColor() {
        return color;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        updateGoalReached();
        invalidate();
    }

    private void updateGoalReached() {
        isGoalReached = progress >= goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
        postInvalidate();
    }

    public void setGoalIndicatorHeight(float goalIndicatorHeight) {
        this.goalIndicatorHeight = goalIndicatorHeight;
        postInvalidate();
    }

    public void setGoalIndicatorThickness(float goalIndicatorThickness) {
        this.goalIndicatorThickness = goalIndicatorThickness;
        postInvalidate();
    }

    public void setGoalReachedColor(int goalReachedColor) {
        this.goalReachedColor = goalReachedColor;
        postInvalidate();
    }

    public void setGoalNotReachedColor(int goalNotReachedColor) {
        this.goalNotReachedColor = goalNotReachedColor;
        postInvalidate();
    }

    public void setUnfilledSectionColor(int unfilledSectionColor) {
        this.unfilledSectionColor = unfilledSectionColor;
        postInvalidate();
    }

    public void setBarThickness(int barThickness) {
        this.barThickness = barThickness;
        postInvalidate();
    }
}
