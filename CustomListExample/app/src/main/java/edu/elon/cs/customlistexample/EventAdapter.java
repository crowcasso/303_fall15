package edu.elon.cs.customlistexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by crowcasso on 11/19/15.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventView eventView = (EventView) convertView;
        if (null == eventView) {
            eventView = EventView.inflate(parent);
        }
        eventView.setEvent(getItem(position));
        return eventView;
    }
}
