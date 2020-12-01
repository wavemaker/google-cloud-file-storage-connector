package com.wavemaker.connector.exception;


/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

public class GoogleObjectDownloadException extends RuntimeException {

    public GoogleObjectDownloadException() {
        super();
    }

    public GoogleObjectDownloadException(String s) {
        super(s);
    }

    public GoogleObjectDownloadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public GoogleObjectDownloadException(Throwable throwable) {
        super(throwable);
    }

    protected GoogleObjectDownloadException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
