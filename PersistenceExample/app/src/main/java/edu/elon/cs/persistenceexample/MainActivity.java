package edu.elon.cs.persistenceexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class MainActivity extends Activity {

    private final String filename = "myoutput.txt";
    private EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = (EditText) findViewById(R.id.edittext);
    }

    private void getPersistentData() throws IOException {
        Context context = getBaseContext();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            edittext.setText(line);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void putPersistentData() throws IOException {
        Context context = getBaseContext();
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(edittext.getText().toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            try {
                putPersistentData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return true;
        } else if (id == R.id.action_load) {
            try {
                getPersistentData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
