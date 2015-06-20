package com.tale.flowframeworkdemo.flow;

import android.os.AsyncTask;
import android.util.Log;

import com.tale.flowframework.Flow;
import com.tale.flowframework.Result;
import com.tale.flowframeworkdemo.model.Message;
import com.tale.flowframeworkdemo.model.MessageModel;

/**
 * Author tale. Created on 6/2/15.
 */
public class MessageFlow extends Flow<Message> {

    public static final String TAG = "MessageFlow";
    private final MessageModel model;
    private AsyncTask<Void, Void, Message> asyncTask;

    public MessageFlow(MessageModel model) {
        this.model = model;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        asyncTask = new AsyncTask<Void, Void, Message>() {
            @Override
            protected Message doInBackground(Void... voids) {
                return model.getMessage();
            }

            @Override
            protected void onPostExecute(Message message) {
                super.onPostExecute(message);
                if (!isCancelled()) {
                    publishResult(new Result<>(0, message != null, message, null));
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    protected void onCancel() {
        Log.d(TAG, "onCancel");
        asyncTask.cancel(true);
    }
}
