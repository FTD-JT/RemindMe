package com.valiro.remindme;

import android.graphics.drawable.Drawable;
import android.location.Location;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by valir on 24.12.2015.
 */
public class Reminder implements Serializable {
    public static class Contact implements Serializable{
        String name;
        String phone;
        Drawable photo;
        boolean fromPicker;

        public Contact () {
            this("", "", false);
        }

        public Contact (String name, String phone, boolean fromPicker) {
            this.name = name;
            this.phone = phone;
            this.fromPicker = fromPicker;
            this.photo = null;
        }

        public Contact (String name, String phone, Drawable photo, boolean fromPicker) {
            this.name = name;
            this.phone = phone;
            this.fromPicker = fromPicker;
            this.photo = photo;
        }
    }

    public static class Email implements Serializable{
        String name;
        String email;
        Drawable photo;
        boolean fromPicker;

        public Email () {
            this("", "",false);
        }

        public Email (String name, String email, boolean fromPicker) {
            this.name = name;
            this.email = email;
            this.fromPicker = fromPicker;
            this.photo = null;
        }

        public Email (String name, String email, Drawable photo, boolean fromPicker) {
            this.name = name;
            this.email = email;
            this.fromPicker = fromPicker;
            this.photo = photo;
        }
    }

    public Contact contact;
    public Email email;
    public Calendar calendar;
    public Location location;
    public Action action;

    Reminder () {
        contact = null;
        email = null;
        calendar = Calendar.getInstance();
        location = null;
        action = null;
    }

    Reminder (Contact contact, Calendar date, Location location, Action action) {
        this.contact = contact;
        this.calendar = date;
        this.location = location;
        this.action = action;
    }
}
