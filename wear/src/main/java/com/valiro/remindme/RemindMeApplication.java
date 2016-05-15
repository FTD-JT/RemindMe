package com.valiro.remindme;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by valir on 04.01.2016.
 */
public class RemindMeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Icons.read(getApplicationContext());
        for (int i = 0; i < Action.defaultActions.length; i++)
            Action.arrayActionList.add(Action.defaultActions[i]);

        Intent providerIntent = new Intent(this, Provider.class);
        startService(providerIntent);
        Log.d("WearableListenerService", "Service started!");
    }
}
