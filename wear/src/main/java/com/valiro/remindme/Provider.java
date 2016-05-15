package com.valiro.remindme;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by valir on 02.01.2016.
 */
public class Provider extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient googleApiClient;
    Node peer;
    public boolean isConnectedToPeer = false;
    public static ArrayList<String> contactsNames, emailsNames;
    public static ArrayList<ArrayList<String> > contactsPhones, emailsAddresses;
    public static ArrayList<Drawable> contactsPhotos, emailsPhotos;

    void swap (Object[] objects, int i, int j) {
        Object aux = objects[i];
        objects[i] = objects[j];
        objects[j] = aux;
    }

    void UpdateContacts () {
        if (googleApiClient.isConnected()) {
            Wearable.DataApi.getDataItems(googleApiClient).setResultCallback(new ResultCallback<DataItemBuffer>() {
                @Override
                public void onResult(DataItemBuffer dataItems) {
                    for (DataItem item : dataItems) {
                        Uri uri = item.getUri();
                        String path = uri == null ? null : uri.getPath();
                        if (path != "CONTACTS")
                            continue;

                        DataMap map = DataMapItem.fromDataItem(item).getDataMap();

                        String names[] = map.getStringArray("Names");
                        String phones[] = map.getStringArray("Phones");
                        String emails[] = map.getStringArray("Emails");
                        Asset assets[] = new Asset[names.length];

                        for (int i = 0; i < names.length; i++)
                            assets[i] = map.getAsset(Integer.toString(i));

                        for (int i = 0; i < names.length; i++)
                            for (int j = i + 1; j < names.length; j++) {
                                if (names[i].compareTo(names[j]) > 0) {
                                    swap (names, i, j);
                                    swap (phones, i, j);
                                    swap (emails, i, j);
                                    swap (assets, i, j);
                                }
                            }

                        contactsNames.clear();
                        contactsPhones.clear();
                        contactsPhotos.clear();
                        emailsNames.clear();
                        emailsAddresses.clear();
                        emailsPhotos.clear();

                        for (int i = 0; i < names.length; i++) {
                            if (phones[i] != null) {
                                if (contactsNames.get(contactsNames.size() - 1).equals(names[i])) {
                                    contactsPhones.get(contactsPhones.size() - 1).add(phones[i]);
                                    if (contactsPhotos.get(contactsPhotos.size() - 1) == null)
                                        contactsPhotos.set(contactsPhotos.size() - 1, toDrawable(assets[i]));
                                }
                                else {
                                    contactsNames.add(names[i]);
                                    contactsPhones.add (new ArrayList<String>());
                                    contactsPhones.get(contactsPhones.size() - 1).add(phones[i]);
                                    contactsPhotos.add(toDrawable(assets[i]));
                                }
                            }

                            if (emails[i] != null) if (emailsNames.get(emailsNames.size() - 1).equals(names[i])) {
                                emailsAddresses.get(emailsAddresses.size() - 1).add(emails[i]);
                                if (emailsPhotos.get(emailsPhotos.size() - 1) == null)
                                    emailsPhotos.set(emailsPhotos.size() - 1, toDrawable(assets[i]));
                            }
                            else {
                                emailsNames.add(names[i]);
                                emailsAddresses.add(new ArrayList<String>());
                                emailsAddresses.get(emailsAddresses.size() - 1).add(emails[i]);
                                emailsPhotos.add(toDrawable(assets[i]));
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().equals("/CONTACTS") && isConnectedToPeer)
            UpdateContacts();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (isConnectedToPeer)
           UpdateContacts();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        this.peer = peer;
        isConnectedToPeer = true;

        UpdateContacts();
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        isConnectedToPeer = false;
    }

    @Override
    public void onCreate () {
        contactsNames = new ArrayList<>();
        contactsPhones = new ArrayList<>();
        contactsPhotos = new ArrayList<>();

        emailsNames = new ArrayList<>();
        emailsAddresses = new ArrayList<>();
        emailsPhotos = new ArrayList<>();

        googleApiClient = new GoogleApiClient.Builder(this).addApiIfAvailable(Wearable.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
    }

    Drawable toDrawable (Asset asset) {
        byte[] bytes = asset.getData();
        return new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }
}
