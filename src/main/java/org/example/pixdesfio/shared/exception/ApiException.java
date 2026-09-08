package org.example.pixdesfio.shared.exception;

public class ApiException extends RuntimeException  {

    public ApiException(String message) {
        super(message);
    }
    public ApiException() {
        super();
    }
}
