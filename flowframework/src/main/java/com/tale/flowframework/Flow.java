package com.tale.flowframework;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Author tale. Created on 6/2/15.
 */
public abstract class Flow<Data> {

    public static final int RE_CREATE_ACTIVITY_DURATION = 500;
    private final Handler handler = new Handler();
    private WeakReference<Callback> stopCallbackWeakReference;
    private WeakReference<Callback> startCallbackWeakReference;
    private Runnable delayJob;
    private WeakReference<ResultHandler> resultHandlerWeakReference;
    private Result<Data> result;
    private boolean isCanceling;
    private boolean isRunning;

    /**
     * Call to connect view.
     * <p>
     * 1. Cache view to use later.<br/>
     * 2. If there is result already then show result.<br/>
     * 3. If canceling then stop cancel.
     * </p>
     *
     * @param view The view which will render result.
     */
    public void connect(ResultHandler view) {
        // S1. Cache view to use later.
        resultHandlerWeakReference = new WeakReference<>(view);
        // If there is result already then show result.
        if (result != null) {
            view.onResult(result);
            result = null;
        }
        // If canceling
        if (isCanceling) {
            stopCanceling();
        }
    }

    /**
     * Disconnect view from Flow.
     */
    public void disconnect() {
        // Clear references
        resultHandlerWeakReference.clear();
        resultHandlerWeakReference = null;
    }

    /**
     * Start logic.
     * <p>
     * If running then stop cancel else call onStart
     * </p>
     */
    public void start(Callback callback) {
        if (isCanceling) {
            stopCanceling();
        } else {
            onStart();
            startCallbackWeakReference = new WeakReference<>(callback);
            isRunning = true;
        }
    }

    /**
     * Stop canceling
     * <p>
     * 1. set canceling to false. <br/>
     * 2. Remove delay cancel.
     * </p>
     */
    private void stopCanceling() {
        isCanceling = false;
        clearDelayJobs();
    }

    /**
     * Cancel the running job.
     * <p>
     * 1. Set <b>canceling = true</b>.<br/>
     * 2. Post a delay runnable which will involve: <br/>
     * - Set <b>canceling = false</b><br/>
     * - Call onCancel abstract method.<br/>
     * - if callback is not null then call callback method.<br/>
     * </p>
     *
     * @param callback the callback which will be called after job canceled.
     */
    public void cancel(Callback callback) {
        if (callback != null) {
            stopCallbackWeakReference = new WeakReference<>(callback);
        }
        isCanceling = true;
        postJobDelay(new Runnable() {
            @Override
            public void run() {
                isCanceling = false;
                isRunning = false;
                onCancel();
                final Callback cb = stopCallbackWeakReference == null ? null : stopCallbackWeakReference.get();
                if (cb != null) {
                    cb.onCallback();
                }
                delayJob = null;
                result = null;
            }
        }, RE_CREATE_ACTIVITY_DURATION);
    }

    /**
     * @param result The {@link Result} object which will be handle by {@link ResultHandler} class.
     */
    protected void publishResult(Result<Data> result) {
        clearDelayJobs();
        final ResultHandler view = resultHandlerWeakReference == null ? null : resultHandlerWeakReference.get();
        if (view == null) {
            this.result = result;
        } else {
            view.onResult(result);
        }
        isRunning = false;
        Callback cb = startCallbackWeakReference == null ? null : startCallbackWeakReference.get();
        if (cb != null) {
            cb.onCallback();
        }
    }

    protected void onStart() {

    }

    protected void onCancel() {

    }

    private void postJobDelay(Runnable runnable, long duration) {
        if (delayJob != null) {
            clearDelayJobs();
        }
        delayJob = runnable;
        handler.postDelayed(delayJob, duration);
    }

    private void clearDelayJobs() {
        handler.removeCallbacks(delayJob);
        delayJob = null;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
