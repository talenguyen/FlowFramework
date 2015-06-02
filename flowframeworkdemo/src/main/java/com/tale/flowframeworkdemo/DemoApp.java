package com.tale.flowframeworkdemo;

import com.tale.flowframework.FlowApp;
import com.tale.flowframeworkdemo.flow.MessageFlow;
import com.tale.flowframeworkdemo.model.MessageModel;

/**
 * Author tale. Created on 6/2/15.
 */
public class DemoApp extends FlowApp {

    public static final String MESSAGE_FLOW = "MessageFlow";
    private MessageFlow messageFlow;

    public MessageFlow getMessageFlow() {
        if (messageFlow == null) {
            messageFlow = new MessageFlow(new MessageModel());
        }
        return messageFlow;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addFlow(MESSAGE_FLOW, getMessageFlow());
    }
}
