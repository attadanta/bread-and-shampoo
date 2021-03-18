package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.model.ListManagementException;

import java.util.List;

/**
 * A store of list items.
 */
public interface ManagedListItemRepository {

    List<ManagedListItem> listUserItems(String owner) throws ListManagementException;

    ManagedListItem insertItem(String owner, String item) throws ListManagementException;

    ManagedListItem deleteItem(String owner, int itemId) throws ListManagementException;

    ManagedListItem updateItem(String owner, int itemId, String item) throws ListManagementException;

}
