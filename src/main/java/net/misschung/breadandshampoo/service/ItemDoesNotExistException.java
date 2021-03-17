package net.misschung.breadandshampoo.service;

import net.misschung.breadandshampoo.model.ListManagementException;

public class ItemDoesNotExistException extends ListManagementException {

    public ItemDoesNotExistException(String message) {
        super(message);
    }

    public ItemDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemDoesNotExistException(Throwable cause) {
        super(cause);
    }

}