package com.valiro.remindme;

import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by valir on 24.12.2015.
 */
public class ItemViewHolder extends WearableListView.ViewHolder {
    public TextView textView;
    public CircledImageView circledImageView;

    public ItemViewHolder (View item) {
        super(item);
        textView = (TextView) item.findViewById(R.id.select_list_label);
        circledImageView = (CircledImageView) item.findViewById(R.id.select_list_icon);
    }
}
