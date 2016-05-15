package com.valiro.remindme;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.gms.wearable.Asset;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by valir on 22.12.2015.
 */
public class RemindMeAdapter extends WearableListView.Adapter {
    private final Context context;
    private final String[] values;
    private final LayoutInflater inflater;
    private final Drawable[] icons;
    private int icon;
    private int selectedTextSize = 16, unselectedTextSize = 15;

    public RemindMeAdapter(Context context, String[] values, Drawable[] icons) {
        this.context = context;
        this.values = values;
        this.icons = icons;
        inflater = LayoutInflater.from(context);
        icon = 0;
    }

    public RemindMeAdapter(Context context, String[] values, int icon) {
        this.context = context;
        this.values = values;
        this.icon = icon;
        this.icons = new Drawable[0];
        inflater = LayoutInflater.from(context);
    }

    public RemindMeAdapter(Context context, ArrayList<String> values, int icon) {
        this.context = context;
        this.values = values.toArray(new String[values.size()]);
        this.icon = icon;
        this.icons = new Drawable[0];
        inflater = LayoutInflater.from(context);
    }

    public RemindMeAdapter(Context context, ArrayList<String> values, ArrayList<Drawable> icons) {
        this.context = context;
        this.values = values.toArray(new String[values.size()]);
        this.icons = icons.toArray(new Drawable[icons.size()]);
        inflater = LayoutInflater.from(context);
        icon = 0;
    }

    public int getItemCount() {
        return values.length;
    }

    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.select_list_item, viewGroup, false);
        WearableListViewLayout layout = (WearableListViewLayout) view.findViewById(R.id.wearable_list_view_layout);
        layout.selectedTextSize = selectedTextSize;
        layout.unselectedTextSize = unselectedTextSize;
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        itemViewHolder.textView.setText(values[position]);

        if (position == 0 && values[position].startsWith("Speak")) {
            itemViewHolder.circledImageView.setBackground(context.getResources().getDrawable(R.drawable.ic_microphone_black_48dp));
            return;
        }

        if (icon > 0)
            itemViewHolder.circledImageView.setBackground(context.getResources().getDrawable(icon));
        else
            itemViewHolder.circledImageView.setBackground(icons[position]);
    }

    public void setSelectedTextSize (int size) {
        selectedTextSize = size;
    }

    public void setUnselectedTextSize (int size) {
        unselectedTextSize = size;
    }
}

