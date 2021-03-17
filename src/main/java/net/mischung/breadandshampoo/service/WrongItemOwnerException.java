package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.model.ListManagementException;

public class WrongItemOwnerException extends ListManagementException {

    public WrongItemOwnerException(String message) {
        super(message);
    }

    public WrongItemOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongItemOwnerException(Throwable cause) {
        super(cause);
    }

}
