package edu.elon.cs.customlistexample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by crowcasso on 11/19/15.
 */
public class EventView extends RelativeLayout {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private ImageView mImageView;

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_view_children, this, true);
        setupChildren();
    }

    public static EventView inflate(ViewGroup parent) {
        EventView eventView = (EventView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return eventView;
    }

    private void setupChildren() {
        mTitleTextView = (TextView) findViewById(R.id.item_titleTextView);
        mDescriptionTextView = (TextView) findViewById(R.id.item_descriptionTextView);
        mImageView = (ImageView) findViewById(R.id.item_imageView);
    }

    public void setEvent(Event event) {
        mTitleTextView.setText(event.mTitle);
        mDescriptionTextView.setText(event.mDescription);
        // not setup imageview
    }
}
