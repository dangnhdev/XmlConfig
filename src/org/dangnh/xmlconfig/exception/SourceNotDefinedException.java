package org.dangnh.xmlconfig.exception;

/**
 * Created by DangNH on 5/4/2016.
 */
public class SourceNotDefinedException extends RuntimeException{
    public SourceNotDefinedException() {
        super("Source not defined in config class");
    }

    public SourceNotDefinedException(String message) {
        super(message);
    }
}
