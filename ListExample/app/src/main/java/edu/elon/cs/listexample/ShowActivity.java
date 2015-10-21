package edu.elon.cs.listexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        textView = (TextView) findViewById(R.id.textview);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("item"));
    }


}
