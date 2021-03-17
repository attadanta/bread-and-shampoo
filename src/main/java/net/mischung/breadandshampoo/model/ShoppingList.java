package net.mischung.breadandshampoo.model;

import java.util.List;

/**
 * A shopping list.
 */
public interface ShoppingList {
    List<ListItem> getItems();

    ListItem updateItem(int itemId, String item) throws ListManagementException;

    ListItem insertItem(String item) throws ListManagementException;

    void deleteItem(int itemId) throws ListManagementException;
}
