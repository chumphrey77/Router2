package com.goose77.router2.support;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * Class for displaying any Exceptions that may occur in the application
 */
public class LabException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Calls the parent Exception class's constructor to display the exception message
     * @param errorMessage the error message
     */
    public LabException(String errorMessage){
        super(errorMessage);
    }
}
