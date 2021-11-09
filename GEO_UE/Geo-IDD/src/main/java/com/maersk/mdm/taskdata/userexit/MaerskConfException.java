package com.maersk.mdm.taskdata.userexit;

/**
 * Used for all kind of configuration and invocation issues
 */
public class MaerskConfException extends Exception {
    
    public MaerskConfException(String message) {
        super(message);
    }

    public MaerskConfException(String message, Throwable cause) {
        super(message, cause);
    }
}
