package edu.elon.cs.dotpainter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Draw dots to the screen on touches.
 *
 * @author J. Hollingsworth and CSC 303 - Fall 2015
 */
public class DoodleView extends View {

    public final static int DEFAULT_WIDTH = 25;

    private ArrayList<Dot> theDots;
    private int penWidth = DEFAULT_WIDTH;

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        theDots = new ArrayList<Dot>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        theDots.add(new Dot(event.getX(), event.getY(), penWidth));
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (Dot dot : theDots) {
            dot.draw(canvas);
        }
        invalidate();
    }

    public void setPenWidth(int penWidth) {
        this.penWidth = penWidth;
    }

    public int getPenWidth() {
        return penWidth;
    }
}