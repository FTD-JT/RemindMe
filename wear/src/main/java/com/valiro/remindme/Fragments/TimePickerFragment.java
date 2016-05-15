package com.valiro.remindme.Fragments;

import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.valiro.remindme.R;
import com.valiro.remindme.RemindMeAdapter;
import com.valiro.remindme.WearableListFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by valir on 30.12.2015.
 */
public class TimePickerFragment extends WearableListFragment {
    ArrayList<String> timeArray;
    public String selectedTime;


    public void setSelectedTime () {
        String h, m;
        int hour, minute;
        h = selectedTime.substring(0, 2);
        m = selectedTime.substring(3, 5);
        if (h.charAt(0) == '0')
            hour = Integer.valueOf(h.charAt(1)) - 48;
        else
            hour = Integer.valueOf(h);

        if (m.charAt(0) == '0')
            minute = Integer.valueOf(m.charAt(1)) - 48;
        else
            minute = Integer.valueOf(m);

        getMainPagerActivity().reminder.calendar.set(Calendar.HOUR, hour);
        getMainPagerActivity().reminder.calendar.set(Calendar.MINUTE, minute);
        getMainPagerActivity().reminder.calendar.set(Calendar.SECOND, 0);
        getMainPagerActivity().reminder.calendar.set(Calendar.MILLISECOND, 0);
    }

    public void initTimeArray () {
        timeArray = new ArrayList<String>();
        for (int i = 0; i < 24; i++)
            for (int j = 0; j < 60; j += 15) {
                String s = "";
                if (i < 10)
                    s += '0';
                s += String.valueOf(i);
                s += ':';
                if (j < 10)
                    s += '0';
                s += String.valueOf(j);

                timeArray.add(s);
            }
    }

    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
        super.init(listView, circularButton, textView);

        circularButton.setImageResource(R.drawable.ic_check_white_48dp);

        initTimeArray();
        RemindMeAdapter adapter = new RemindMeAdapter(getActivity().getApplicationContext(), timeArray, R.drawable.ic_alarm_black_48dp);
        adapter.setUnselectedTextSize(10);
        listView.setAdapter(adapter);
        listView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                selectedTime = timeArray.get(i);
                setSelectedTime();
            }
        });

        listView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                getMainPagerActivity().next();
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        circularButton.setOnClickListener(new CircularButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainPagerActivity().next();
            }
        });

        selectedTime = timeArray.get(0);
        setSelectedTime();
    }
}
