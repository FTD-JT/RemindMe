package com.valiro.remindme;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by valir on 01.01.2016.
 */
public class WearableListFragment extends Fragment {
    WearableListView listView;
    CircularButton circularButton;
    MainPager mainPagerActivity;
    TextView textView;

    public WearableListFragment () {
    }

    public WearableListFragment (MainPager mainPagerActivity, CircularButton circularButton, TextView textView) {
        this.mainPagerActivity = mainPagerActivity;
        this.circularButton = circularButton;
        this.textView = textView;
    }

    public void set (MainPager mainPagerActivity, CircularButton circularButton, TextView textView) {
        this.mainPagerActivity = mainPagerActivity;
        this.circularButton = circularButton;
        this.textView = textView;
    }

    @Override
    public View onCreateView (LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, parent, savedInstanceState);

        listView = new WearableListView(getActivity().getApplicationContext());

        init(listView, circularButton, textView);

        return listView;
    }

    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
    }

    public MainPager getMainPagerActivity() {
        return mainPagerActivity;
    }
}
