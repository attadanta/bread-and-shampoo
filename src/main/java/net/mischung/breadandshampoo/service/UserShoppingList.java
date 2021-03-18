package net.mischung.breadandshampoo.service;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.model.ListManagementException;
import net.mischung.breadandshampoo.model.ShoppingList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A shopping list belonging to a particular user.
 *
 * <p>This implementation is backed by a {@link ManagedListItemRepository}</p>
 */
public class UserShoppingList implements ShoppingList {

    private final String owner;
    private final ManagedListItemRepository repository;

    public UserShoppingList(ManagedListItemRepository repository, String owner) {
        this.owner = owner;
        this.repository = repository;
    }

    @Override
    public List<ListItem> getItems() {
        return repository.listUserItems(owner).stream()
                .map(ManagedListItem::getData)
                .collect(Collectors.toList());
    }

    @Override
    public ListItem updateItem(int itemId, String item) throws ListManagementException {
        return repository.updateItem(owner, itemId, item).getData();
    }

    @Override
    public ListItem insertItem(String item) throws ListManagementException {
        return repository.insertItem(owner, item).getData();
    }

    @Override
    public void deleteItem(int itemId) throws ListManagementException {
        repository.deleteItem(owner, itemId);
    }

}
