package com.valiro.remindme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valir on 24.12.2015.
 */
public class Icons {
    static final ArrayList<Drawable> iconResID = new ArrayList<Drawable>();

    static void read (Context context) {
        try {
            ArrayList<String> iconList = new ArrayList<String>();
            BufferedInputStream fin = new BufferedInputStream(context.getAssets().open("DefaultIcons"));
            char c;
            int input;
            String s = "";
            input = fin.read();
            while (input != -1) {
                c = (char) input;
                if (c == '\n') {
                    iconList.add(s);
                    s = "";
                }
                else if (c != '\r')
                    s += c;
                input = fin.read();
            }

            if (s != "")
                iconList.add(s);

            for (int i = 0; i < iconList.size(); i++)
                iconResID.add(context.getDrawable(context.getResources().getIdentifier(iconList.get(i), "drawable", context.getPackageName())));
        }
        catch(IOException e) {
            return;
        }
    }

    public static Drawable[] getFromIntArray (int[] icons) {
        Drawable d[] = new Drawable[icons.length];
        for (int i = 0; i < icons.length; i++) {
            d[i] = null;
            if (!(icons[i] >= iconResID.size() || icons[i] < 0 ))
                d[i] = iconResID.get(icons[i]);

        }

        return d;
    }
}
