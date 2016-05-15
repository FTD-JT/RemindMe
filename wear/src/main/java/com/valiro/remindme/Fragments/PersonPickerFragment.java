package com.valiro.remindme.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.valiro.remindme.Activities.PersonListActivity;
import com.valiro.remindme.Icons;
import com.valiro.remindme.ItemViewHolder;
import com.valiro.remindme.MainPager;
import com.valiro.remindme.Provider;
import com.valiro.remindme.R;
import com.valiro.remindme.RemindMeAdapter;
import com.valiro.remindme.Reminder;
import com.valiro.remindme.WearableListFragment;

/**
 * Created by valir on 02.01.2016.
 */
public class PersonPickerFragment extends WearableListFragment {
    int contactOrEmail;

    @Override
    public void init (WearableListView listView, CircularButton circularButton, TextView textView) {
        RemindMeAdapter selectAdapter = new RemindMeAdapter(getActivity().getApplicationContext(), new String[]{"Do not specify", "Speak!", "Select Contact"}, Icons.getFromIntArray(new int[]{7, 5, 6}));
        contactOrEmail = getMainPagerActivity().reminder.action.selectContactOrEmail;
        textView.setText("Pick method!");
        circularButton.setEnabled(false);
        circularButton.setVisibility(View.INVISIBLE);
        listView.setAdapter(selectAdapter);
        listView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder v) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) v;
                String s = itemViewHolder.textView.getText().toString();

                if (s == "Do not specify") {
                    if (contactOrEmail == 1)
                        getMainPagerActivity().reminder.contact = new Reminder.Contact();
                    else
                        getMainPagerActivity().reminder.email = new Reminder.Email();

                    getMainPagerActivity().next();
                } else if (s == "Speak!")
                    getMainPagerActivity().startSpeechRecognition();
                else if (s.startsWith("Select")) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), PersonListActivity.class);
                    intent.putExtra("REQUEST_CODE", contactOrEmail);
                    startActivityForResult(intent, contactOrEmail);
                }
            }

            @Override
            public void onTopEmptyRegionClick() {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == WearableActivity.RESULT_OK)
            if (requestCode == contactOrEmail) {
                if (contactOrEmail == 1) {
                    int index = data.getIntExtra("Person", 0), choice = data.getIntExtra("Choice", 0);
                    getMainPagerActivity().reminder.contact = new Reminder.Contact(Provider.contactsNames.get(index), Provider.contactsPhones.get(index).get(choice), Provider.contactsPhotos.get(index), true);
                }
                else if (contactOrEmail == 2) {
                    int index = data.getIntExtra("Person", 0), choice = data.getIntExtra("Choice", 0);
                    getMainPagerActivity().reminder.email = new Reminder.Email(Provider.emailsNames.get(index), Provider.emailsAddresses.get(index).get(choice), Provider.emailsPhotos.get(index), true);
                }
            }
    }
}
