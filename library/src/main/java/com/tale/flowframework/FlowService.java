package com.tale.flowframework;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

/**
 * Author tale. Created on 6/2/15.
 */
public class FlowService extends Service {

    public static final String TAG = "FlowService";
    private static final int ACTION_START = 0x001;
    private static final String EXTRA_ACTION = "FlowService:Action";
    private static final String EXTRA_KEY = "FlowService:FlowKey";
    private Callback stopCallback = new Callback() {
        @Override
        public void onCallback() {
            stopSelf();
        }
    };
    private String key;
    private boolean isRunning = false;

    public static void start(Context context, String flowKey) {
        final Intent intent = new Intent(context, FlowService.class);
        intent.putExtra(EXTRA_ACTION, ACTION_START);
        intent.putExtra(EXTRA_KEY, flowKey);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int action = intent.getIntExtra(EXTRA_ACTION, 0);
        switch (action) {
            case ACTION_START:
                if (!isRunning) {
                    key = intent.getStringExtra(EXTRA_KEY);
                    if (!TextUtils.isEmpty(key)) {
                        final Flow<?> flow = ((FlowApp) getApplication()).getFlow(key);
                        if (flow != null) {
                            flow.start(stopCallback);
                            isRunning = true;
                        } else {
                            Log.d(TAG, "There is no flow for the given key: " + key);
                        }
                    }
                }
                break;
        }
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        if (!TextUtils.isEmpty(key)) {
            final Flow<?> flow = ((FlowApp) getApplication()).getFlow(key);
            if (flow != null) {
                flow.cancel(null);
            } else {
                Log.d(TAG, "There is no flow for the given key: " + key);
            }
        }
    }
}
