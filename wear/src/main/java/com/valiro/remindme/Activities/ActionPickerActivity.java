package com.valiro.remindme.Activities;

import android.content.Intent;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;

import com.valiro.remindme.Action;
import com.valiro.remindme.Icons;
import com.valiro.remindme.MainPager;
import com.valiro.remindme.RemindMeAdapter;
import com.valiro.remindme.WearableListActivity;

/**
 * Created by valir on 02.01.2016.
 */
public class ActionPickerActivity extends WearableListActivity {
    Action selectedAction;
    private static final int ACTION_DETAILS_REQUEST = 1;

    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
        super.init(listView, circularButton, textView);

        final RemindMeAdapter selectActionAdapter = new RemindMeAdapter(getApplicationContext(), Action.getActionsNames(), Icons.getFromIntArray(Action.getActionsIcons()));
        listView.setAdapter(selectActionAdapter);

        listView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                selectedAction = Action.arrayActionList.get(i);
            }
        });

        listView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder v) {
                Intent intent = new Intent(getApplicationContext(), MainPager.class);
                intent.putExtra ("Action", selectedAction);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_DETAILS_REQUEST)
            if (resultCode == RESULT_OK)
                finish ();
    }
}