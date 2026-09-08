package org.example.pixdesfio.shared.exception;

public class ValidationException extends ApiException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super();
    }
}
