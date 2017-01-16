package com.anudeepsamaiya.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
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

    private float strokeWidth = 5;

    private Path path = new Path();
    private Paint paint;

    public ProgressView(Context context) {
        super(context);
        setupPaint();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupPaint();
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setAntiAlias(true); // enable anti aliasing
        paint.setColor(getColor()); // set default color to white
        paint.setDither(true); // enable dithering
        paint.setStyle(Paint.Style.STROKE); // set to STOKE
        paint.setStrokeJoin(Paint.Join.ROUND); // set the join to round you want
        paint.setStrokeCap(Paint.Cap.ROUND);  // set the paint cap to round too
        paint.setPathEffect(new CornerPathEffect(getStrokeWidth())); // set the path effect
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public int getColor() {
        return color;
    }
}
