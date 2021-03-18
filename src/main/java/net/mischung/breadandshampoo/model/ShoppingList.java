package net.mischung.breadandshampoo.model;

import java.util.List;

/**
 * A shopping list.
 */
public interface ShoppingList {

    /**
     * Enumerates the entries of this list.
     *
     * @return a view of this list's entries.
     */
    List<ListItem> getItems();

    /**
     * Updates an item in this list.
     *
     * @param itemId the id of the item to update.
     * @param item   the shopping item to set.
     * @return the updated item.
     * @throws ListManagementException if the operation cannot be executed.
     */
    ListItem updateItem(int itemId, String item);

    /**
     * Adds an item to this list..
     *
     * @param item the shopping item to enter.
     * @return the inserted item.
     * @throws ListManagementException if the operation cannot be executed.
     */
    ListItem insertItem(String item);


    /**
     * Removes an item from this list.
     *
     * @param itemId the id of the item to remove.
     * @throws ListManagementException if the operation cannot be executed.
     */
    void deleteItem(int itemId);

}
