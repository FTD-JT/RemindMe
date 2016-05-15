package com.valiro.remindme;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CircularButton;
import android.util.Log;
import android.widget.TextView;

import com.valiro.remindme.Fragments.DatePickerFragment;
import com.valiro.remindme.Fragments.PersonPickerFragment;
import com.valiro.remindme.Fragments.TimePickerFragment;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by valir on 01.01.2016.
 */
public class MainPager extends FragmentActivity {
    public Reminder reminder = new Reminder();
    CircularButton circularButton;
    TextView textView;
    ViewPager viewPager;
    WearableListFragment nextFragment;
    public int contactIndex = -1, emailIndex = -1;

    @Override
    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);


        setContentView(R.layout.main_pager_view);
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        circularButton = (CircularButton) findViewById(R.id.main_button);
        textView = (TextView) findViewById(R.id.main_upper_text_view);
        reminder = new Reminder();
        reminder.action = Action.arrayActionList.get(getIntent().getIntExtra("Action", 0));
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), this, circularButton, textView));
    }

    public static String toDate (Calendar c) {
        int day = c.get(Calendar.DAY_OF_MONTH);
        String s = "";
        if (day < 10)
            s += '0';
        s += String.valueOf(day);
        s += ' ';
        s += c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        s += ' ';
        s += String.valueOf(c.get(Calendar.YEAR));

        return s;
    }


    public static String toTime (Calendar c) {
        String s = "";
        int h = c.get(Calendar.HOUR), m = c.get(Calendar.MINUTE);
        if (h < 10)
            s += '0';
        s += String.valueOf(h);
        s += ':';
        if (m < 10)
            s += '0';
        s += String.valueOf(m);
        return s;
    }

    public void next () {
        int index = viewPager.getCurrentItem();
        if (index < MyAdapter.NUM_ITEMS - 1)
            viewPager.setCurrentItem(index + 1, true);
        else
            finish ();
    }

    public void startSpeechRecognition () {
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        static final int NUM_ITEMS = 3;
        MainPager mainPagerActivity;
        CircularButton circularButton;
        TextView textView;
        public MyAdapter(FragmentManager fm, MainPager mainPagerActivity, CircularButton circularButton, TextView textView) {
            super(fm);
            this.mainPagerActivity = mainPagerActivity;
            this.circularButton = circularButton;
            this.textView = textView;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                PersonPickerFragment p = new PersonPickerFragment();
                p.set (mainPagerActivity, circularButton, textView);
                return p;
            }
            else if (position == 1) {
                DatePickerFragment d = new DatePickerFragment();
                d.set (mainPagerActivity, circularButton, textView);
                return d;
            }
            else {
                TimePickerFragment t = new TimePickerFragment();
                t.set (mainPagerActivity, circularButton, textView);
                return t;
            }
        }
    }

}
