package com.valiro.remindme;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by valir on 03.01.2016.
 */
public class WearableListActivity extends WearableActivity {
    WearableListView listView;
    CircularButton circularButton;
    MainPager mainPagerActivity;
    TextView textView;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity);
        listView = (WearableListView) findViewById(R.id.main_list);
        circularButton = (CircularButton) findViewById(R.id.main_button);
        textView = (TextView) findViewById(R.id.main_upper_text_view);
        init(listView, circularButton, textView);
    }

    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
    }
}
