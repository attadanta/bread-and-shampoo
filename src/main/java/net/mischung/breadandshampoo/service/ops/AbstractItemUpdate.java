package net.mischung.breadandshampoo.service.ops;

import net.mischung.breadandshampoo.model.ListItem;
import net.mischung.breadandshampoo.service.ItemDoesNotExistException;
import net.mischung.breadandshampoo.service.ManagedListItem;
import net.mischung.breadandshampoo.service.WrongItemOwnerException;

import java.util.function.BiFunction;

abstract class AbstractItemUpdate implements BiFunction<Integer, ManagedListItem, ManagedListItem> {

    final String owner;

    AbstractItemUpdate(String owner) {
        this.owner = owner;
    }

    ListItem verifyOwnership(Integer id, ManagedListItem managedListItem) {
        if (managedListItem == null) {
            throw new ItemDoesNotExistException("No found");
        } else if (managedListItem.getOwner().equals(owner)) {
            return managedListItem.getData();
        } else {
            throw new WrongItemOwnerException("Access denied");
        }
    }

}
