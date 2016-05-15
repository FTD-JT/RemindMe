package com.valiro.remindme.Activities;

import android.content.Intent;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.valiro.remindme.Fragments.DatePickerFragment;
import com.valiro.remindme.Icons;
import com.valiro.remindme.Provider;
import com.valiro.remindme.R;
import com.valiro.remindme.RemindMeAdapter;
import com.valiro.remindme.WearableListActivity;

/**
 * Created by valir on 02.01.2016.
 */
public class PersonListActivity extends WearableListActivity {
    int index = 0;
    @Override
    public void init (final WearableListView listView, final CircularButton circularButton, final TextView textView) {
        super.init(listView, circularButton, textView);

        final int request_code = getIntent().getIntExtra("REQUEST_CODE", 1);
        RemindMeAdapter remindMeAdapter = new RemindMeAdapter(this, new String[]{"Invalid request code"}, Icons.getFromIntArray(new int[]{1}));
        if (request_code == 1)
            remindMeAdapter = new RemindMeAdapter(getApplicationContext(), Provider.contactsNames, Provider.contactsPhotos);
        else if (request_code == 2)
            remindMeAdapter = new RemindMeAdapter(getApplicationContext(), Provider.emailsNames, Provider.emailsPhotos);
        listView.setAdapter(remindMeAdapter);
        listView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                if (request_code == 1) {
                    if (Provider.contactsPhones.get(index).size() > 1) {
                        Intent intent = new Intent(getApplicationContext(), MultiplePersonsResolverActivity.class);
                        intent.putExtra("Person", index);
                        startActivityForResult(intent, request_code);
                    } else {
                        Intent data = new Intent();
                        data.putExtra("Person", index);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                } else {
                    if (Provider.emailsAddresses.get(index).size() > 1) {
                        Intent intent = new Intent(getApplicationContext(), MultiplePersonsResolverActivity.class);
                        intent.putExtra("Person", index);
                        startActivityForResult(intent, request_code);
                    } else {
                        Intent data = new Intent();
                        data.putExtra("Person", index);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
        index = 0;

        listView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                index = i;
            }
        });

        circularButton.setVisibility(View.INVISIBLE);
        circularButton.setEnabled(false);
        textView.setText("INVALID REQUEST CODE");
        if (request_code == 1)
            textView.setText("Pick a contact");
        else if (request_code == 2)
            textView.setText("Pick an email");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int choice = data.getIntExtra("Choice", 0);

        Intent intent = new Intent();
        data.putExtra("Person", index);
        data.putExtra("Person choice", choice);
        setResult(RESULT_OK, intent);
    }
}
