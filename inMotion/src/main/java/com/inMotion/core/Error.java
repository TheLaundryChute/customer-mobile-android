package com.inMotion.core;

/**
 * Created by sfbechtold on 9/6/15.
 */
public class Error {

    private int code = -1;
    private String message = null;
    private String context = null;

    public Error(int code, String message, String context) {
        this.code = code;
        this.message = message;
        this.context = context;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getContext() {
        return context;
    }


    public static  Error error(int code, String message, String context) {
        return  new Error(code, message, context);
    }
}
