package edu.elon.cs.scrabblecheck;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Controller for the Scrabble Check app.
 *
 * @author J. Hollingsworth and CSC303 - Fall 2015
 */

public class ScrabbleCheckActivity extends Activity {

    // instance variables
    private EditText editText;
    private TextView textView;
    private Dictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrabble_check);

        // create a new dictionary -- need context to find assets folder
        dictionary = new Dictionary(getBaseContext());

        // get references to UI widgets
        editText = (EditText) findViewById(R.id.word);
        textView = (TextView) findViewById(R.id.answer);
    }

    // read the word from the user and check against the dictionary
    public void checkMatch(View View) {
        String w = editText.getText().toString();
        if (dictionary.isMatch(w)) {
            textView.setText(R.string.yes_answer);
        } else {
            textView.setText(R.string.no_answer);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrabble_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}