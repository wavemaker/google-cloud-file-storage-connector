package com.wavemaker.connector.exception;

/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

public class ServiceAccountCredentialsCreationException extends RuntimeException {
    public ServiceAccountCredentialsCreationException() {
        super();
    }

    public ServiceAccountCredentialsCreationException(String s) {
        super(s);
    }

    public ServiceAccountCredentialsCreationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceAccountCredentialsCreationException(Throwable throwable) {
        super(throwable);
    }

    protected ServiceAccountCredentialsCreationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
