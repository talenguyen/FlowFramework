package com.tale.flowframeworkdemo.model;

import android.os.SystemClock;

import java.text.DateFormat;
import java.util.Date;

/**
 * Author tale. Created on 6/2/15.
 */
public class MessageModel {

    public Message getMessage() {
        SystemClock.sleep(2000);
        return new Message("Message - " + DateFormat.getDateInstance().format(new Date()));
    }
}
