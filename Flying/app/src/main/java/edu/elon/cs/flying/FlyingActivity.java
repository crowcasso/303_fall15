package edu.elon.cs.flying;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Main activity for Flying!
 *
 * @author J. Hollingsworth
 */

public class FlyingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flying);

        // give the GameLoopView access to the TextView
        TextView textView = (TextView) findViewById(R.id.textview);
        GameLoopView gameLoopView = (GameLoopView) findViewById(R.id.gameloopview);
        gameLoopView.setTextView(textView);
    }
}
