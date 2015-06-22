package com.tale.flowframework;

/**
 * Author tale. Created on 6/22/15.
 */
public interface Publisher<Data> {

    void publish(Result<Data> result);

}
