package com.maersk.mdm.taskdata.userexit;

/**
 * Used for business validation errors
 */
public class MaerskValidationException extends Exception {

    public MaerskValidationException(String message) {
        super(message);
    }

}
