package edu.elon.cs.flying;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Contains both the surface to draw to and the game loop.
 *
 * @author J. Hollingsworth
 */

public class GameLoopView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread thread;
    private SurfaceHolder surfaceHolder;
    private Context context;

    // touch location for the bird
    private float touchX, touchY;

    // TextView for the FPS value
    private TextView textView;

    public GameLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // remember the context for finding resources
        this.context = context;

        // want to know when the surface changes
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // game loop thread -- add a handler to update the TextView
        thread = new GameLoopThread(msgHandler);
    }

    // set the overlay textview
    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    // Handler callback
    Handler msgHandler = new Handler() {
        public void handleMessage(Message m) {
            textView.setText(m.getData().getString("data"));
        }
    };


    // SurfaceHolder.Callback methods:
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // thread exists, but is in terminated state
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new GameLoopThread(msgHandler);
        }

        // start the game loop
        thread.setIsRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setIsRunning(false);

        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


    // touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // remember the last touch point
        touchX = event.getX();
        touchY = event.getY();

        return true;
    }

    // Game Loop Thread
    private class GameLoopThread extends Thread {

        private boolean isRunning = false;
        private long lastTime;

        // the bird sprite
        private Bird bird;

        // the clouds
        private final int NUM_CLOUDS = 3;
        private ArrayList<Cloud> clouds;

        // frames per second calculation
        private int frames;
        private long nextUpdate;

        // the handler for updates to the TextView
        private Handler handler;

        public GameLoopThread(Handler handler) {

            this.handler = handler;

            bird = new Bird(context);

            clouds = new ArrayList<Cloud>();
            for (int i = 0; i < NUM_CLOUDS; i++) {
                clouds.add(new Cloud(context));
            }

            touchX = bird.x;
            touchY = bird.y;
        }

        public void setIsRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        // the main loop
        @Override
        public void run() {

            lastTime = System.currentTimeMillis();

            while (isRunning) {

                // grab hold of the canvas
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas == null) {
                    // trouble -- exit nicely
                    isRunning = false;
                    continue;
                }

                synchronized (surfaceHolder) {

                    // compute how much time since last time around
                    long now = System.currentTimeMillis();
                    double elapsed = (now - lastTime) / 1000.0;
                    lastTime = now;

                    // update/draw
                    doUpdate(elapsed);
                    doDraw(canvas);

                    // compute and show FPS
                    updateFPS(now);

                }

                // release the canvas
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void sendMessage(String message) {
            Message msg = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("data", message);
            msg.setData(b);
            handler.sendMessage(msg);
        }

        // an approximate frames per second calculation
        private void updateFPS(long now) {
            float fps = 0.0f;
            ++frames;
            float overtime = now - nextUpdate;
            if (overtime > 0) {
                fps = frames / (1 + overtime/1000.0f);
                frames = 0;
                nextUpdate = System.currentTimeMillis() + 1000;
                sendMessage((int)fps + "");
            }
        }

        /* THE GAME */

        // move all objects in the game
        private void doUpdate(double elapsed) {

            for (Cloud cloud : clouds) {
                cloud.doUpdate(elapsed);
            }

            bird.doUpdate(elapsed, touchX, touchY);
        }

        // draw all objects in the game
        private void doDraw(Canvas canvas) {

            // draw the background
            canvas.drawColor(Color.argb(255, 126, 192, 238));

            for (Cloud cloud : clouds) {
                cloud.doDraw(canvas);
            }


            bird.doDraw(canvas);
        }
    }
}