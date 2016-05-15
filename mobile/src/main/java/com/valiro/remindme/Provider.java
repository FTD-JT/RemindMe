package com.valiro.remindme;

import android.database.Cursor;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by valir on 04.01.2016.
 */
public class Provider extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient googleApiClient;
    PutDataMapRequest putRequest;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        if (messageEvent.getPath()=="CONTACTS") {
            Cursor contacts = getContentResolver().query(null, null, null, null, null);

            ArrayList<String> contactsNameList = new ArrayList<>(), photoUriList = new ArrayList<>(), phoneList = new ArrayList<>(), emailList = new ArrayList<>();

            contacts.moveToFirst();
            while (contacts.moveToNext()) {
                String name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        phone = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                        email = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)),
                        photoUri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                contactsNameList.add(name);
                if (photoUri == null)
                    photoUri = "";
                emailList.add(email);
                phoneList.add(phone);
                photoUriList.add(photoUri);
                contacts.moveToNext();
            }
            contacts.close();

            putRequest = PutDataMapRequest.create("CONTACTS");
            DataMap map = putRequest.getDataMap();
            map.putStringArrayList("Contacts", contactsNameList);
            map.putStringArrayList("Phones", phoneList);
            map.putStringArrayList("Emails", emailList);
            map.putStringArrayList("Contacts PhotoUri", photoUriList);
            for (int i = 0; i < photoUriList.size(); i++) {
                Asset asset;
                if (photoUriList.get(i) != "") {
                    try {
                        asset = createAssetFromBitmap(photoUriList.get(i));
                        map.putAsset(photoUriList.get(i), asset);
                    }
                    catch (FileNotFoundException e) {
                        photoUriList.set(i, "");
                    }
                }
            }

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApiIfAvailable(Wearable.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.putDataItem(googleApiClient, putRequest.asPutDataRequest());
    }

    @Override
    public void onPeerConnected (Node peer) {
        super.onPeerConnected(peer);
        Wearable.NodeApi.getConnectedNodes(googleApiClient);
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private static Asset createAssetFromBitmap(String imagePath) throws FileNotFoundException {
        // creating from Uri doesn't work: gives a ASSET_UNAVAILABLE error
        //return Asset.createFromUri(Uri.parse(imagePath));

        final File file = new File(imagePath);
        final ParcelFileDescriptor fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

        return Asset.createFromFd(fd);
    }
}
