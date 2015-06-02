package com.tale.flowframeworkdemo;

import android.app.Application;

import com.tale.flowframeworkdemo.flow.MessageFlow;
import com.tale.flowframeworkdemo.model.MessageModel;

/**
 * Author tale. Created on 6/2/15.
 */
public class DemoApp extends Application {

    private MessageFlow messageFlow;

    public MessageFlow getMessageFlow() {
        if (messageFlow == null) {
            messageFlow = new MessageFlow(new MessageModel());
        }
        return messageFlow;
    }
}
