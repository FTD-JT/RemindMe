package com.valiro.remindme;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by valir on 02.01.2016.
 */
public class Action implements Serializable{
    public static ArrayList<Action> arrayActionList = new ArrayList<Action>();
    public static Action[] defaultActions = new Action[]{new Action("Call", 0, 1, 1),
                                    new Action("Write an email", 1, 2, 2),
                                    new Action("Write message", 2, 1, 2),
                                    new Action("Meet", 3, 1, 0),
                                    new Action("Work", 4, 0, 0)};

    public String name;
    int iconID;
    public int selectContactOrEmail, callOrMessage;

    public Action (String name, int iconID, int selectContactOrEmail, int callOrMessage) {
        this.name = name;
        this.iconID = iconID;
        this.selectContactOrEmail = selectContactOrEmail;
        this.callOrMessage = callOrMessage;
    }

    public static Action getActionByString (String name) {
        for (int i = 0; i < arrayActionList.size(); i++)
            if (arrayActionList.get(i).name == name)
                return arrayActionList.get(i);
        return null;
    }

    public static String[] getActionsNames () {
        String[] names = new String[arrayActionList.size()];
        for (int i = 0; i < arrayActionList.size(); i++)
            names[i] = arrayActionList.get(i).name;
        return names;
    }

    public static int[] getActionsIcons () {
        int[] icons = new int[arrayActionList.size()];
        for (int i = 0; i < arrayActionList.size(); i++)
            icons[i] = arrayActionList.get(i).iconID;
        return icons;
    }
}