package com.valiro.remindme.Fragments;

import android.os.Bundle;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;

import com.valiro.remindme.MainPager;
import com.valiro.remindme.R;
import com.valiro.remindme.RemindMeAdapter;
import com.valiro.remindme.WearableListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by valir on 30.12.2015.
 */
public class DatePickerFragment extends WearableListFragment {
    ArrayList<String> dateArray;
    public String selectedDate;
    final int advanceDates = 20;

    public void setSelectedDate () {
        String d, m, y;
        int day;
        if (selectedDate.startsWith("Speak"))
            return;

        d = selectedDate.substring(0, 2);
        m = selectedDate.substring(4, selectedDate.length() - 5);
        y = selectedDate.substring(selectedDate.length() - 4, selectedDate.length());
        if (d.charAt(0) == '0')
            day = Integer.valueOf(d.charAt(1)) - 48;
        else
            day = Integer.valueOf(d);

        for (int i = 1; i <= 12; i++) {
            getMainPagerActivity().reminder.calendar.set(Calendar.MONTH, i);
            if (m.equals(getMainPagerActivity().reminder.calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)))
                break;
        }


        getMainPagerActivity().reminder.calendar.set(Calendar.DAY_OF_MONTH, day);
        getMainPagerActivity().reminder.calendar.set(Calendar.YEAR, Integer.valueOf(y));
    }

    public void initDateArray () {
        dateArray = new ArrayList<String>();
        dateArray.add ("Speak!");
        Calendar c = Calendar.getInstance();
        int days = 0;
        while (days < advanceDates) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            days++;
            dateArray.add(MainPager.toDate(c));
        }
    }

    @Override
    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
        super.init(listView, circularButton, textView);

        initDateArray();
        RemindMeAdapter adapter = new RemindMeAdapter(getActivity().getApplicationContext(), dateArray, R.drawable.ic_alarm_black_48dp);
        adapter.setUnselectedTextSize(10);
        adapter.setSelectedTextSize(18);
        listView.setAdapter(adapter);
        listView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                if (i > 0) {
                    selectedDate = dateArray.get(i);
                    setSelectedDate();
                } else {
                    getMainPagerActivity().startSpeechRecognition();
                }
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

        selectedDate = dateArray.get(1);
        setSelectedDate();
    }
}
