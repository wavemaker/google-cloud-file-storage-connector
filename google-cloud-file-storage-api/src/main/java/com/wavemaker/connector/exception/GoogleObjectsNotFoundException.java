package com.wavemaker.connector.exception;

/**
 * @author <a href="mailto:karthik.peerlagudem@wavemaker.com">Karthik Peerlagudem</a>
 * @since 25/11/20
 */

public class GoogleObjectsNotFoundException extends RuntimeException {
    public GoogleObjectsNotFoundException() {
        super();
    }

    public GoogleObjectsNotFoundException(String s) {
        super(s);
    }

    public GoogleObjectsNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public GoogleObjectsNotFoundException(Throwable throwable) {
        super(throwable);
    }

    protected GoogleObjectsNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
