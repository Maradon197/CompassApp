package com.example.compassapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CompassView extends android.view.View
{

    private Paint paint;
    private Paint textPaint;
    private float azimuth = 0;

    public CompassView(Context context)
    {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
    }

    public void setAzimuth(float azimuth)
    {
        this.azimuth = azimuth;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas c)
    {
        super.onDraw(c);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) / 2;

        //ring
        c.drawCircle(centerX, centerY, radius, paint);

        c.drawText("N", centerX - 15, centerY - radius - 10, textPaint);
        c.drawText("S", centerX - 15, centerY + radius + 40, textPaint);
        c.drawText("E", centerX + radius + 10, centerY, textPaint);
        c.drawText("W", centerX - radius - 40, centerY, textPaint);

        c.save();
        c.rotate(-azimuth, centerX, centerY);

        //compass needle
        c.drawLine(centerX, centerY - radius, centerX, centerY, paint);

        c.restore();
    }
}
