package com.wavemaker.connector.exception;

/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

public class ServiceAccountCredentialsFileNotFoundException extends RuntimeException{

    public ServiceAccountCredentialsFileNotFoundException() {
        super();
    }

    public ServiceAccountCredentialsFileNotFoundException(String s) {
        super(s);
    }

    public ServiceAccountCredentialsFileNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceAccountCredentialsFileNotFoundException(Throwable throwable) {
        super(throwable);
    }

    protected ServiceAccountCredentialsFileNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
