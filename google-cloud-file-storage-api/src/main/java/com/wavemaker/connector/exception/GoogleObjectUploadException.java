package com.wavemaker.connector.exception;

/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

public class GoogleObjectUploadException extends RuntimeException {
    public GoogleObjectUploadException() {
        super();
    }

    public GoogleObjectUploadException(String s) {
        super(s);
    }

    public GoogleObjectUploadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public GoogleObjectUploadException(Throwable throwable) {
        super(throwable);
    }

    protected GoogleObjectUploadException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
