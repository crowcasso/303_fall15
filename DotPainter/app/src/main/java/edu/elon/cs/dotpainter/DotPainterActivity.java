package edu.elon.cs.dotpainter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Main activity for the Dot Painter.
 *
 * @author J. Hollingsworth
 */
public class DotPainterActivity extends Activity {

    public final static int WIDTH_DIALOG = 1;

    private DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_painter);

        doodleView = (DoodleView) findViewById(R.id.doodleview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dot_painter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setwidth) {
            // start the Set Width dialog - pass the current pen width
            Intent intent = new Intent(this, SetWidthDialogActivity.class);
            intent.putExtra("width", doodleView.getPenWidth());
            startActivityForResult(intent, WIDTH_DIALOG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WIDTH_DIALOG) {
            if (resultCode == RESULT_OK) {
                // get the new pen width and tell the DoodleView
                int width = data.getIntExtra("width", doodleView.getPenWidth());
                doodleView.setPenWidth(width);
            }
        }
    }
}
