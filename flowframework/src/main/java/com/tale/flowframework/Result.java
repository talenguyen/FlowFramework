package com.tale.flowframework;

/**
 * Author tale. Created on 6/2/15.
 */
public class Result<Data> {

    public final int id;
    public final boolean success;
    public final Data data;
    public final Object error;

    public Result(int id, boolean success, Data data, Object error) {
        this.id = id;
        this.success = success;
        this.data = data;
        this.error = error;
    }

}
