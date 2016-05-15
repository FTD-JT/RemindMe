package com.valiro.remindme;

import android.content.Context;
import android.graphics.Color;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.wearable.Wearable;

/**
 * Created by valir on 23.12.2015.
 */
public class WearableListViewLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {
    private final int unselectedTextColor, selectedTextColor;
    private TextView textView;
    public int selectedTextSize = 16, unselectedTextSize = 15;

    public WearableListViewLayout(Context context) {
        this(context, null);
    }

    public WearableListViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListViewLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);

        unselectedTextColor = getResources().getColor(R.color.grey);
        selectedTextColor = getResources().getColor(R.color.black);
    }

    @Override
    protected void onFinishInflate () {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.select_list_label);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        textView.setTextSize(selectedTextSize);
        textView.setTextColor(selectedTextColor);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        textView.setTextSize(unselectedTextSize);
        textView.setTextColor(unselectedTextColor);
    }
}
