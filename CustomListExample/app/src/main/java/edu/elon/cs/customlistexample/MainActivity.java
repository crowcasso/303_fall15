package edu.elon.cs.customlistexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<Event> events;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        events = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Event event = new Event("Name" + i, "Date" + i, "Cost" + i);
            events.add(event);
        }

        listView.setAdapter(new EventAdapter(this, events));
    }
}
