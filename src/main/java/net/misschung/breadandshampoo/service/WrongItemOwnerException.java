package net.misschung.breadandshampoo.service;

import net.misschung.breadandshampoo.model.ListManagementException;

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
