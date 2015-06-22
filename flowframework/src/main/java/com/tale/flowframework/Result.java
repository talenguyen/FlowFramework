package com.tale.flowframework;

/**
 * Author tale. Created on 6/2/15.
 */
public class Result<Data> {

    public final boolean success;
    public final Data data;
    public final Object error;

    public Result(boolean success, Data data, Object error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

}
