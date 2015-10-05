package edu.elon.cs.multitouch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Shows all touches to the screen.
 *
 * @author J. Hollingsworth
 */
public class TouchView extends View {

    private ArrayList<Touch> theTouches;

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        theTouches = new ArrayList<Touch>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);

        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN: {
                Touch touch = new Touch(event.getX(pointerIndex), event.getY(pointerIndex), pointerID);
                theTouches.add(touch);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int i = 0; i < event.getPointerCount(); i++) {
                    for (Touch touch : theTouches) {
                        if (touch.pointerID == event.getPointerId(i)) {
                            touch.x = event.getX(i);
                            touch.y = event.getY(i);
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                for (int i = 0; i < theTouches.size(); i++) {
                    if (theTouches.get(i).pointerID == pointerID) {
                        theTouches.remove(i);
                    }
                }
                break;
            }
        }

        invalidate();

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Touch touch : theTouches) {
            touch.onDraw(canvas);
        }
    }
}
