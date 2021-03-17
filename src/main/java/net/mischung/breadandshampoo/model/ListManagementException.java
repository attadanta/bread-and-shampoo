package net.mischung.breadandshampoo.model;

public class ListManagementException extends RuntimeException {
    public ListManagementException(String message) {
        super(message);
    }

    public ListManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListManagementException(Throwable cause) {
        super(cause);
    }
}
