package com.tale.flowframework;

/**
 * Author tale. Created on 6/2/15.
 */
public interface IView<Data> {

    void renderResult(Result<Data> data);

}
