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
import com.valiro.remindme.WearableListFragment;

/**
 * Created by valir on 03.01.2016.
 */
public class MultiplePersonsResolverActivity extends WearableListActivity {
    int choice = 0;
    @Override
    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
        super.init(listView, circularButton, textView);
        int index = getIntent().getIntExtra("Person", 0);
        int request_code = getIntent().getIntExtra("REQUEST_CODE", 0);
        textView.setText("REQUEST_CODE INVALID");
        if (request_code == 1)
            textView.setText(Provider.contactsNames.get(index));
        else if (request_code == 2)
            textView.setText(Provider.emailsNames.get(index));

        circularButton.setEnabled(true);
        circularButton.setVisibility(View.VISIBLE);

        RemindMeAdapter remindMeAdapter = new RemindMeAdapter(this, new String[]{"Invalid request code"}, Icons.getFromIntArray(new int[]{1}));
        if (request_code == 1)
            remindMeAdapter = new RemindMeAdapter(this, Provider.contactsPhones.get(index), R.drawable.ic_contact_phone_black_48dp);
        else if (request_code == 2)
            remindMeAdapter = new RemindMeAdapter(this, Provider.emailsAddresses.get(index), R.drawable.ic_email_black_48dp);
        listView.setAdapter(remindMeAdapter);

        choice = 0;
        listView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
            @Override
            public void onCentralPositionChanged(int i) {
                choice = i;
            }
        });

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnChoice();
            }
        });

        circularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnChoice ();
            }
        });
    }

    void returnChoice () {
        Intent data = new Intent();
        data.putExtra("Choice", choice);
        setResult(RESULT_OK, data);
    }
}
