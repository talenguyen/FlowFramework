package com.tale.flowframework;

import android.app.Application;

import java.util.Hashtable;
import java.util.Map;

/**
 * Author tale. Created on 6/2/15.
 */
public class FlowApp extends Application {

    private Map<String, Flow<?>> flowMap;

    public void addFlow(String key, Flow<?> flow) {
        if (flowMap == null) {
            flowMap = new Hashtable<>();
        }
        flowMap.put(key, flow);
    }

    public <Data> Flow<Data> getFlow(String key) {
        if (flowMap == null || flowMap.size() == 0) {
            return null;
        }
        final Flow<?> flow = flowMap.get(key);
        return flow == null ? null : (Flow<Data>) flow;
    }
}
