package com.tale.flowframework;

/**
 * Author tale. Created on 6/22/15.
 */
public class Asserts {

    /**
     * Convenience method help to verify the object not be null. A {@link NullPointerException} will
     * be throw when the object is <code>null</code>.
     *
     * @param obj The target object to verify.
     * @throws NullPointerException exception throwed when obj is null.
     * @see Asserts#assertNotNull(Object, String)
     */
    public static void assertNotNull(Object obj) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException("object must not be null");
        }
    }

    /**
     * Convenience method help to verify the object not be null. A {@link NullPointerException} will
     * be throw when the object is <code>null</code>.
     *
     * @param obj The target object to verify.
     * @param msg the detail message for this exception.
     * @throws NullPointerException exception throwed when obj is null.
     */
    public static void assertNotNull(Object obj, String msg) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }

}
