package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.model.ListManagementException;

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