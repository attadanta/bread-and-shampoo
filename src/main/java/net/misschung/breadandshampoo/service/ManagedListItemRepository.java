package net.misschung.breadandshampoo.service;

import net.misschung.breadandshampoo.model.ListManagementException;

import java.util.List;

public interface ManagedListItemRepository {

    List<ManagedListItem> listUserItems(String owner) throws ListManagementException;

    ManagedListItem insertItem(String owner, String item) throws ListManagementException;

    ManagedListItem deleteItem(String owner, int itemId) throws ListManagementException;

    ManagedListItem updateItem(String owner, int itemId, String item) throws ListManagementException;

}
