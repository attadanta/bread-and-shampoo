package net.mischung.breadandshampoo.service.ops;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.ItemDoesNotExistException;
import net.mischung.breadandshampoo.service.ManagedListItem;
import net.mischung.breadandshampoo.service.WrongItemOwnerException;

import java.util.Objects;
import java.util.function.BiFunction;

abstract class AbstractItemUpdate implements BiFunction<Integer, ManagedListItem, ManagedListItem> {

    final String owner;

    AbstractItemUpdate(String owner) {
        this.owner = Objects.requireNonNull(owner);
    }

    @Override
    public ManagedListItem apply(Integer itemId, ManagedListItem managedListItem) {
        ListItem itemData = verifyDataExistenceAndOwnership(itemId, managedListItem);
        return doApply(itemId, managedListItem, itemData);
    }

    ListItem verifyDataExistenceAndOwnership(Integer id, ManagedListItem managedListItem) {
        if (managedListItem == null) {
            String message = String.format("Item not found: %d", id);
            ItemDoesNotExistException itemDoesNotExistException = new ItemDoesNotExistException(message);
            itemDoesNotExistException.setItemId(id);
            throw itemDoesNotExistException;
        } else if (!managedListItem.getOwner().equals(owner)) {
            String message = String.format("Access denied: user %s does not own item %d", owner, id);
            WrongItemOwnerException accessDenied = new WrongItemOwnerException(message);
            accessDenied.setItemId(id);
            throw accessDenied;
        } else {
            return managedListItem.getData();
        }
    }

    abstract ManagedListItem doApply(Integer integer, ManagedListItem managedListItem, ListItem originalItemData);

}
